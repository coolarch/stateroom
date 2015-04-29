package cool.arch.stateroom;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

final class StateBuilderFacadeImpl<M> implements StateBuilderFacade<M> {
	
	private final MachineBuilderImpl<M> builder;
	
	private final State<M> state;
	
	private final Queue<Transition<M>> builtTransitions;
	
	StateBuilderFacadeImpl(MachineBuilderImpl<M> builder, State<M> state) {
		this(builder, state, new LinkedList<>());
	}
	
	StateBuilderFacadeImpl(MachineBuilderImpl<M> builder, State<M> state, Queue<Transition<M>> builtTransitions) {
		this.builder = builder;
		this.state = state;
		this.builtTransitions = builtTransitions;
	}

	@Override
	public StateBuilderFacade<M> withState(State<M> state) {
		builder.withState(this.state, new Transitions<>(builtTransitions));

		return new StateBuilderFacadeImpl<>(builder, state);
	}

	@Override
	public Machine<M> build() {
		builder.withState(state, new Transitions<>(builtTransitions));
		
		return builder.build();
	}

	@Override
	public StateBuilderFacade<M> to(State<M> targetState, BiPredicate<State<M>, M> predicate) {
		final Transition<M> transition = Transition.<M>builder()
			.to(targetState)
			.when(predicate)
			.build();

		builtTransitions.add(transition);

		return this;
	}

	@Override
	public StateBuilderFacade<M> to(State<M> targetState, BiPredicate<State<M>, M> predicate,
		BiFunction<State<M>, M, M> modelTransform) {
		final Transition<M> transition = Transition.<M>builder()
			.to(targetState)
			.when(predicate)
			.transformModelWith(modelTransform)
			.build();

		builtTransitions.add(transition);

		return this;
	}
}
