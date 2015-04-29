package cool.arch.stateroom;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public interface TransitionBuilder<M> extends AbstractBuilder<Transition<M>> {

	TransitionBuilder<M> when(BiPredicate<State<M>, M> predicate);

	TransitionBuilder<M> to(State<M> targetState);

	TransitionBuilder<M> transformModelWith(BiFunction<State<M>, M, M> modelTransform);

}
