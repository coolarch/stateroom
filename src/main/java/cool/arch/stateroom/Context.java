package cool.arch.stateroom;

import static java.util.Objects.requireNonNull;
import cool.arch.stateroom.enums.Status;

/**
 * Container for contextual information specific to an individual machine run instance.
 * @param <M> Type used to represent the machine model
 */
public final class Context<M> {
	
	private final State<M> state;
	
	private final Status status;
	
	private final M model;
	
	Context(final State<M> state, final Status status, final M model) {
		this.state = requireNonNull(state, "state shall not be null");
		this.status = requireNonNull(status, "status shall not be null");
		this.model = model;
	}
	
	Context<M> derive(M model) {
		return new Context<>(state, status, model);
	}
	
	/**
	 * Gets the current state in this machine context after the most recent evaluation cycle.
	 * @return Current state in this run context
	 */
	public State<M> getState() {
		return state;
	}
	
	/**
	 * Gets the current status of the machine after the most recent evaluation cycle.
	 * @return Status of the machine in this run context
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Gets the model used by the context.
	 * @return the model
	 */
	public M getModel() {
		return model;
	}
}
