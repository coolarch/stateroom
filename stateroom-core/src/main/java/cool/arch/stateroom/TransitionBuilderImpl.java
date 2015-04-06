package cool.arch.stateroom;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;

final class TransitionBuilderImpl<M> extends AbstractBuilderImpl<Transition<M>> implements TransitionBuilder<M> {

	protected TransitionBuilderImpl() {
		super(new Transition<>());
	}

	@Override
	protected Set<String> validate(Set<String> errors, Transition<M> instance) {
		if (instance.getPredicate() == null) {
			errors.add("predicate shall not be null");
		}

		if (instance.getTargetState() == null) {
			errors.add("targetState shall not be null");
		}

		return errors;
	}

	@Override
	public TransitionBuilder<M> when(Predicate<Context<M>> predicate) {
		getInstance().setPredicate(predicate);
		
		return this;
	}

	@Override
	public TransitionBuilder<M> to(State<M> targetState) {
		getInstance().setTargetState(targetState);
		
		return this;
	}

	@Override
	public TransitionBuilder<M> transformModelWith(BiFunction<State<M>, M, M> modelTransform) {
		getInstance().setModelTransform(requireNonNull(modelTransform, "modelTransform shall not be null"));
		
		return this;
	}
}
