package cool.arch.stateroom;

import java.util.function.BiFunction;

public interface StateBuilder<M> extends AbstractBuilder<State<M>> {

	StateBuilder<M> withName(String name);

	StateBuilder<M> withModelTransform(BiFunction<State<M>, Context<M>, M> modelTransform);

	StateBuilder<M> withAcceptState(boolean acceptState);

}
