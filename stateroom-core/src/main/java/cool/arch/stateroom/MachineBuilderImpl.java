package cool.arch.stateroom;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class MachineBuilderImpl<M> extends AbstractBuilderImpl<Machine<M>> implements MachineBuilder<M> {

	protected MachineBuilderImpl() {
		super(new Machine<>());
	}

	@Override
	protected Set<String> validate(Set<String> errors, Machine<M> instance) {
		final Set<State<M>> states = instance.getTransitions().keySet();

		final String invalidStates = instance.getTransitions().values()
			.stream()
			.flatMap(t -> t.findInvalidStateNames(states)
				.stream())
			.collect(Collectors.joining(", "));

		if (!"".equals(invalidStates)) {
			errors.add("Invalid states: " + invalidStates);
		}

		if (!states.contains(instance.getStartState())) {
			errors.add("Invalid start state: " + instance.getStartState().getName());
		}

		return errors;
	}

	public MachineBuilder<M> withState(State<M> state, Transitions<M> transitions) {
		getInstance().getTransitions().put(state, transitions);

		return this;
	}

	@Override
	public MachineBuilder<M> withModelSupplier(Supplier<M> modelSupplier) {
		getInstance().setModelSupplier(requireNonNull(modelSupplier, "modelSupplier shall not be null"));

		return this;
	}

	@Override
	public MachineBuilder<M> withStartState(State<M> startState) {
		getInstance().setStartState(requireNonNull(startState, "startState shall not be null"));

		return this;
	}

	@Override
	public MachineBuilder<M> withPreEvaluationTransform(BiFunction<State<M>, M, M> preEvaluationTransform) {
		getInstance().setPreEvaluationTransform(requireNonNull(preEvaluationTransform, "preEvaluationTransform shall not be null"));

		return this;
	}

	@Override
	public MachineBuilder<M> haltWhen(BiPredicate<State<M>, M> predicate) {
		getInstance().setHaltPredicate(requireNonNull(predicate, "predicate shall not be null"));

		return this;
	}

	@Override
	public StateBuilderFacade<M> withState(State<M> state) {
		return new StateBuilderFacadeImpl<>(this, state);
	}
}
