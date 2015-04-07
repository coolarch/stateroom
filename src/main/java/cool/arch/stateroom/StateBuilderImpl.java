package cool.arch.stateroom;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiFunction;

final class StateBuilderImpl<M> extends AbstractBuilderImpl<State<M>> implements StateBuilder<M> {

	protected StateBuilderImpl() {
		super(new State<>());
	}

	@Override
	protected Set<String> validate(Set<String> errors, State<M> instance) {
		return errors;
	}

	@Override
	public StateBuilder<M> withName(String name) {
		getInstance().setName(requireNonNull(name, "name shall not be null"));

		return this;
	}

	@Override
	public StateBuilder<M> withModelTransform(BiFunction<State<M>, Context<M>, M> modelTransform) {
		getInstance().setModelTransform(requireNonNull(modelTransform, "modelTransform shall not be null"));

		return this;
	}

	@Override
	public StateBuilder<M> withAcceptState(boolean acceptState) {
		getInstance().setAcceptState(acceptState);

		return this;
	}
}
