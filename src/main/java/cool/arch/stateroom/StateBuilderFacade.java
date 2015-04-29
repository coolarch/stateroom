package cool.arch.stateroom;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public interface StateBuilderFacade<M> {

	StateBuilderFacade<M> to(State<M> targetState, BiPredicate<State<M>, M> predicate);

	StateBuilderFacade<M> to(State<M> targetState, BiPredicate<State<M>, M> predicate, BiFunction<State<M>, M, M> modelTransform);
	
	StateBuilderFacade<M> withState(State<M> state);
	
	Machine<M> build();
}
