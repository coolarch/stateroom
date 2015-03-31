package com.unboundedprime.stateroom.core;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import com.unboundedprime.stateroom.core.interfaces.Context;

public class Transition<M> {

	private Predicate<Context<M>> predicate;

	private State<M> targetState;

	private Function<Context<M>, M> modelTransform = (context) -> context.getModel();

	public Context<M> onEnter(final Context<M> context) {
		final M model = modelTransform.apply(context);

		return context.derive(model);
	}

	public Function<Context<M>, M> getModelTransform() {
		return modelTransform;
	}

	public Predicate<Context<M>> getPredicate() {
		return predicate;
	}

	public State<M> getTargetState() {
		return targetState;
	}

	public static <T> Builder<T> builder() {
		return new BuilderImpl<>();
	}

	public interface Builder<M> extends AbstractBuilder<Transition<M>> {

		Builder<M> withPredicate(Predicate<Context<M>> predicate);

		Builder<M> withTargetState(State<M> targetState);

		Builder<M> withModelTransform(Function<Context<M>, M> modelTransform);

	}
	
	static class BuilderImpl<M> extends AbstractBuilderImpl<Transition<M>> implements Builder<M> {

		protected BuilderImpl() {
			super(new Transition<>());
		}

		@Override
		protected Set<String> validate(Set<String> errors, Transition<M> instance) {
			if (instance.predicate == null) {
				errors.add("predicate shall not be null");
			}

			if (instance.targetState == null) {
				errors.add("targetState shall not be null");
			}

			return errors;
		}

		@Override
		public Builder<M> withPredicate(Predicate<Context<M>> predicate) {
			getInstance().predicate = predicate;
			
			return this;
		}

		@Override
		public Builder<M> withTargetState(State<M> targetState) {
			getInstance().targetState = targetState;
			
			return this;
		}

		@Override
		public Builder<M> withModelTransform(Function<Context<M>, M> modelTransform) {
			getInstance().modelTransform = requireNonNull(modelTransform, "modelTransform shall not be null");
			
			return this;
		}
	}
}
