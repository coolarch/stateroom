package cool.arch.stateroom;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class Transition<M> {

	private BiPredicate<State<M>, M> predicate;

	private State<M> targetState;

	private BiFunction<State<M>, M, M> modelTransform = (state, model) -> model;

	public M onEnter(final State<M> state, final M model) {
		return modelTransform.apply(state, model);
	}

	public BiFunction<State<M>, M, M> getModelTransform() {
		return modelTransform;
	}

	public BiPredicate<State<M>, M> getPredicate() {
		return predicate;
	}

	public State<M> getTargetState() {
		return targetState;
	}

	void setPredicate(BiPredicate<State<M>, M> predicate) {
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
