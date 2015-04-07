package cool.arch.stateroom;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Transition<M> {

	private Predicate<Context<M>> predicate;

	private State<M> targetState;

	private BiFunction<State<M>, M, M> modelTransform = (state, model) -> model;

	public M onEnter(final State<M> state, final M model) {
		return modelTransform.apply(state, model);
	}

	public BiFunction<State<M>, M, M> getModelTransform() {
		return modelTransform;
	}

	public Predicate<Context<M>> getPredicate() {
		return predicate;
	}

	public State<M> getTargetState() {
		return targetState;
	}

	void setPredicate(Predicate<Context<M>> predicate) {
		this.predicate = predicate;
	}

	void setTargetState(State<M> targetState) {
		this.targetState = targetState;
	}

	void setModelTransform(BiFunction<State<M>, M, M> modelTransform) {
		this.modelTransform = modelTransform;
	}

	@Override
	public String toString() {
		return String.format("{'targetState':%s}", targetState);
	}

	public static <T> TransitionBuilder<T> builder() {
		return new TransitionBuilderImpl<>();
	}
}
