package cool.arch.stateroom;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface StateBuilderFacade<M> {

	StateBuilderFacade<M> to(State<M> targetState, Predicate<Context<M>> predicate);

	StateBuilderFacade<M> to(State<M> targetState, Predicate<Context<M>> predicate, BiFunction<State<M>, M, M> modelTransform);
	
	StateBuilderFacade<M> withState(State<M> state);
	
	Machine<M> build();
}
