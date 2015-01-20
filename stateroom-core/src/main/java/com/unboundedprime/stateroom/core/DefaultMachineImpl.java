package com.unboundedprime.stateroom.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

import com.unboundedprime.stateroom.core.discriminator.DefaultStateDiscriminator;
import com.unboundedprime.stateroom.core.enums.Status;
import com.unboundedprime.stateroom.core.exception.DefinitionFailedException;
import com.unboundedprime.stateroom.core.exception.DuplicateStateException;
import com.unboundedprime.stateroom.core.exception.EndStateNotFoundException;
import com.unboundedprime.stateroom.core.exception.InvalidContextException;
import com.unboundedprime.stateroom.core.exception.StartStateNotFoundException;
import com.unboundedprime.stateroom.core.exception.UnknownStateException;
import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.MachineDefinitionControls;
import com.unboundedprime.stateroom.core.interfaces.MachineStrategy;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;
import com.unboundedprime.stateroom.core.interfaces.StateDiscriminator;

/**
 * Default machine implementation.
 * @param <M> Model implementation used by the machine to determine information about it
 */
class DefaultMachineImpl<M extends Model> {
	
	/**
	 * Machine strategy instance used to define machine instance states, transitions and initialize contexts.
	 */
	private final MachineStrategy<M> _strategy;
	
	/**
	 * Whether or not the machine should use an atomic context.
	 */
	private boolean _contextAtomicityRequired;
	
	/**
	 * Constructs a new DefaultMachineImpl based on the definition in the provided MachineStrategy.
	 * @param strategy Machine strategy used to define the machine and initialize contexts
	 * @param discriminator Discriminator to determine state based on interrogation of the context/model
	 */
	public DefaultMachineImpl(final MachineStrategy<M> strategy, final StateDiscriminator<M> discriminator) {
		requireNonNull(strategy, "strategy may not be null");
		requireNonNull(discriminator, "discriminator may not be null");
		
		this._strategy = strategy;
	}
	
	/**
	 * Attempts to define the machine.
	 * @throws DefinitionFailedException If the machine fails to be defined due to error
	 */
	public final void attemptMachineDefinition() throws DefinitionFailedException {
		try {
			this._strategy.defineMachine(this);
		} catch (final UnknownStateException ex) {
			throw new DefinitionFailedException("Unknown state error: " + ex.getMessage(), ex);
		} catch (final DuplicateStateException ex) {
			throw new DefinitionFailedException("Duplicate state error: " + ex.getMessage(), ex);
		}
		
		this._contextAtomicityRequired = this._strategy.contextAtomicityRequired();
	}
	
	/**
	 * Constructs a new DefaultMachineImpl based on the definition in the provided MachineStrategy.
	 * @param strategy Machine strategy used to define the machine and initialize contexts
	 * @throws DefinitionFailedException If the strategy failed to define the machine
	 */
	public DefaultMachineImpl(final MachineStrategy<M> strategy) throws DefinitionFailedException {
		this(strategy, new DefaultStateDiscriminator<M>());
	}
	/**
	 * Association of the unique state to its transition container instance.
	 */
	private final Map<State<M>, StateTransitionsContainer<M>> _stateTransitionsContainer = new HashMap<State<M>, StateTransitionsContainer<M>>();
	
	/**
	 * Adds a state to the machine.
	 * @param state State to be added
	 * @param acceptState Whether the state being added is an accept state
	 * @throws DuplicateStateException If a duplicate state is attempted to be added
	 */
	public final void addState(final State<M> state, final boolean acceptState) throws DuplicateStateException {
		
		if (this._stateTransitionsContainer.containsKey(state)) {
			throw new DuplicateStateException("Duplicate state may not be added.");
		}
		
		this._stateTransitionsContainer.put(state, new StateTransitionsContainer<M>(state, acceptState));
	}
	
	/**
	 * Adds a mono-directional transition between two states that is followed, if the predicate indicates true, when it is evaluated.
	 * @param start Start state from which the transition originates
	 * @param end End state to which the transition terminates
	 * @param predicate Predicate used to determine if the transition should be followed at the time of evaluation
	 * @throws UnknownStateException Thrown if either the start or end state do not exist in the set of states in the machine
	 */
	public final void addTransition(final State<M> start, final State<M> end, final Predicate<Context<M>> predicate) throws UnknownStateException {
		
		if (!this._stateTransitionsContainer.containsKey(start)) {
			throw new StartStateNotFoundException("Starting Vertex Not Found");
		}
		
		if (!this._stateTransitionsContainer.containsKey(end)) {
			throw new EndStateNotFoundException("Ending Vertex Not Found");
		}
		
		this._stateTransitionsContainer.get(start).addTransition(end, predicate);
	}
	
	/**
	 * Evaluates a context until it either crashes or enters an accept state.
	 * <p>
	 * WARNING: If your machine strategy defines a machine that is Turing complete, or is otherwise capable of looping forever,
	 * this method is also capable of looping forever and not returning. Approach usage of this method with caution.
	 * </p>
	 * @param context Context to evaluate
	 * @throws InvalidContextException If the provided context model is determined to be invalid
	 */
	public final void evaluateUntilHalted(final Context<M> context) throws InvalidContextException {
		while (context.getStatus() == Status.READY) {
			this.evaluate(context);
		}
	}
	
	/**
	 * Evaluates a context for one evaluation cycle, performs the appropriate state update and returns.
	 * @param context Context to evaluate
	 * @throws InvalidContextException If the provided context model is determined to be invalid
	 */
	public final void evaluate(final Context<M> context) throws InvalidContextException {
		final State<M> currentState = context.getCurrentState();
		
		if (!this._stateTransitionsContainer.containsKey(currentState)) {
			throw new InvalidContextException("Context contains a reference to an unknown state.");
		}
		
		final StateTransitionsContainer<M> currentStateContainer = this._stateTransitionsContainer.get(currentState);
		
		if (context.getModel().isEnd()) {
			evaluateInModelEnd(currentStateContainer, context);
		} else {
			evaluateNotInModelEnd(currentStateContainer, context, currentState);
		}
	}
	
	/**
	 * Handles the evaluation of the machine for cases where the model does not indicate that the machine is in an operative end condition.
	 * @param currentStateContainer Container from which to obtain the next state
	 * @param context Machine context instance
	 * @param currentState Current state of the machine
	 */
	private void evaluateNotInModelEnd(final StateTransitionsContainer<M> currentStateContainer, final Context<M> context, final State<M> currentState) {
		
		final State<M> nextState = currentStateContainer.resolveNextState(context);
		
		if (nextState != null) {
			currentState.stateTransitionedOut(context);
			context.setCurrentState(nextState);
			nextState.stateTransitionedIn(context);
		}
	}
	
	/**
	 * Handles the evaluation of the machine for cases where the model does not indicate that the machine is not in an operative end condition.
	 * @param currentStateContainer Container from which to obtain the next state
	 * @param context Machine context instance
	 */
	private void evaluateInModelEnd(final StateTransitionsContainer<M> currentStateContainer, final Context<M> context) {
		if (currentStateContainer.isAcceptState()) {
			context.setStatus(Status.ACCEPTED);
		} else {
			context.setStatus(Status.CRASHED);
		}
	}
	
	/**
	 * Creates a newly initialized context representing a specific machine instance.
	 * @return Newly create context
	 */
	public final Context<M> createContext() {
		Context<M> context = null;
		
		if (this._contextAtomicityRequired) {
			context = new AtomicContext<M>();
		} else {
			context = new SimpleContext<M>();
		}
		
		this._strategy.populateContext(context);
		this._strategy.populateStartState(context);
		context.setStatus(Status.READY);
		
		return context;
	}
}
