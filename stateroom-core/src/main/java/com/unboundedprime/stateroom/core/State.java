package com.unboundedprime.stateroom.core;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

import com.unboundedprime.stateroom.core.interfaces.Context;

public class State<M> {
	
	private static final AtomicLong COUNTER = new AtomicLong(-1);
	
	private String name = "State " + COUNTER.incrementAndGet();
	
	private boolean acceptState = false;

	/**
	 * 
	 */
	private BiFunction<State<M>, Context<M>, M> modelTransform = (state, context) -> context.getModel();
	
	public Context<M> onEnter(final Context<M> context) {
		final M model = modelTransform.apply(this, context);
		
		return context.derive(model);
	}

	public String getName() {
		return name;
	}

	public boolean isAcceptState() {
		return acceptState;
	}

	public BiFunction<State<M>, Context<M>, M> getModelTransform() {
		return modelTransform;
	}
	
	public static <T> Builder<T> builder() {
		return new BuilderImpl<>();
	}

	public interface Builder<M> extends AbstractBuilder<State<M>> {
		
		Builder<M> withName(String name);
		
		Builder<M> withModelTransform(BiFunction<State<M>, Context<M>, M> modelTransform);
		
		Builder<M> withAcceptState(boolean acceptState);
		
	}
	
	static class BuilderImpl<M> extends AbstractBuilderImpl<State<M>> implements Builder<M> {

		protected BuilderImpl() {
			super(new State<>());
		}

		@Override
		protected Set<String> validate(Set<String> errors, State<M> instance) {
			return errors;
		}

		@Override
		public Builder<M> withName(String name) {
			getInstance().name = requireNonNull(name, "name shall not be null");
			
			return this;
		}

		@Override
		public Builder<M> withModelTransform(BiFunction<State<M>,Context<M>, M> modelTransform) {
			getInstance().modelTransform = requireNonNull(modelTransform, "modelTransform shall not be null");
			
			return this;
		}

		@Override
		public Builder<M> withAcceptState(boolean acceptState) {
			getInstance().acceptState = acceptState;
			
			return this;
		}
	}
}
