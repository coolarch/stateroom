package cool.arch.stateroom;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public interface MachineBuilder<M> extends AbstractBuilder<Machine<M>> {

	StateBuilderFacade<M> withState(State<M> state);

	MachineBuilder<M> withModelSupplier(Supplier<M> modelSupplier);

	MachineBuilder<M> withStartState(State<M> startState);

	MachineBuilder<M> haltWhen(BiPredicate<State<M>, M> predicate);

	MachineBuilder<M> withPreEvaluationTransform(BiFunction<State<M>, M, M> preEvaluationTransform);

}
