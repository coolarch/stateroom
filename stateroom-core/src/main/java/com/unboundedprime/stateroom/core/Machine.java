package com.unboundedprime.stateroom.core;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.unboundedprime.stateroom.core.enums.Status;
import com.unboundedprime.stateroom.core.interfaces.Context;

public class Machine<M> {

	private Supplier<M> modelSupplier;
	
	private Function<Context<M>, M> preEvaluationTransform = c -> c.getModel();
	
	private State<M> startState;
	
	private Predicate<Context<M>> continuePredicate = c -> true;
	
	private final Map<State<M>, Transitions<M>> transitions = new HashMap<>();

	public static <T> Builder<T> builder() {
		return new BuilderImpl<>();
	}
	
	public Context<M> createContext() {
		final M model = modelSupplier.get();
		
		Context<M> context = new Context<>(startState, Status.READY, model);
		
		return context;
	}
	
	public Context<M> evaluate(final Context<M> context) {
		final M model = preEvaluationTransform.apply(context);
		final Context<M> evaluationContext = context.derive(model);
		
		Context<M> result = context;
		
		final Transitions<M> stateTransitions = transitions.get(context.getState());
		
		if (stateTransitions != null) {
			
			for (final Transition<M> transition : stateTransitions) {
				final Predicate<Context<M>> predicate = transition.getPredicate();
				
				if (predicate.test(context)) {
					final Context<M> transitionalContext = evaluationContext.derive(transition.getTargetState());
					final M finalModel = transition.getModelTransform().apply(transitionalContext);
					final Context<M> finalContext = transitionalContext.derive(finalModel);
					result = finalContext;
					break;
				}
			}
		}
		
		return result;
	}
	
	public Context<M> evaluateUntilHalted(final Context<M> context) {
		Context<M> currentContext = context;

		while (continuePredicate.test(currentContext)) {
			currentContext = evaluate(currentContext);
		}
		
		return currentContext;
	}

	public interface Builder<M> extends AbstractBuilder<Machine<M>> {
		
		@SuppressWarnings("unchecked")
		Builder<M> withState(State<M> state, Transition<M>... transitions);
		
		Builder<M> withModelSupplier(Supplier<M> modelSupplier);
		
		Builder<M> withStartState(State<M> startState);
		
		Builder<M> withContinuePredicate(Predicate<Context<M>> continuePredicate);
		
		Builder<M> withPreEvaluationTransform(Function<Context<M>, M> preEvaluationTransform);
		
	}
	
	static class BuilderImpl<M> extends AbstractBuilderImpl<Machine<M>> implements Builder<M> {

		protected BuilderImpl() {
			super(new Machine<>());
		}

		@Override
		protected Set<String> validate(Set<String> errors, Machine<M> instance) {
			final Set<State<M>> states = instance.transitions.keySet();
			
			final String invalidStates = instance.transitions.values()
				.stream()
				.flatMap(t -> t.findInvalidStateNames(states).stream())
				.collect(Collectors.joining(", "));
			
			if (!"".equals(invalidStates)) {
				errors.add("Invalid states: " + invalidStates);
			}
			
			if (!states.contains(instance.startState)) {
				errors.add("Invalid start state: " + instance.startState.getName());
			}
			
			return errors;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Builder<M> withState(State<M> state, Transition<M>... stateTransitions) {
			final Transitions<M> transitions = new Transitions<>(stateTransitions);
			
			getInstance().transitions.put(state, transitions);
			
			return this;
		}

		@Override
		public Builder<M> withModelSupplier(Supplier<M> modelSupplier) {
			getInstance().modelSupplier = requireNonNull(modelSupplier, "modelSupplier shall not be null");
			
			return this;
		}

		@Override
		public Builder<M> withStartState(State<M> startState) {
			getInstance().startState = requireNonNull(startState, "startState shall not be null");
			
			return this;
		}

		@Override
		public Builder<M> withPreEvaluationTransform(Function<Context<M>, M> preEvaluationTransform) {
			getInstance().preEvaluationTransform = requireNonNull(preEvaluationTransform, "preEvaluationTransform shall not be null");
			
			return this;
		}

		@Override
		public Builder<M> withContinuePredicate(Predicate<Context<M>> continuePredicate) {
			getInstance().continuePredicate = requireNonNull(continuePredicate, "continuePredicate shall not be null");
			
			return this;
		}
	}
}
