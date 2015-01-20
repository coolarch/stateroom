/*
 * Machine.java
 *
 * Stateroom - Java framework library for finite state machines
 * Copyright (C) 2009 by Matthew Werny
 * All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.unboundedprime.stateroom.core;

import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.unboundedprime.stateroom.core.exception.DefinitionFailedException;
import com.unboundedprime.stateroom.core.interfaces.Context;

/**
 * Finite state machine container to allow the running of machine strategies.
 * @param <M> Type used to represent the machine model
 */
public final class Machine<M> {
	
	/**
	 * Prevents external instantiation.
	 */
	private Machine() {
		
	}
	
	/**
	 * Evaluates a context until it either crashes or enters an accept state.
	 * <p>
	 * WARNING: If your machine strategy defines a machine that is Turing complete, or is otherwise capable of looping forever,
	 * this method is also capable of looping forever and not returning. Approach usage of this method with caution.
	 * </p>
	 * @param context Context to evaluate
	 */
	public void evaluateUntilHalted(final Context<M> context) {
		
	}
	
	/**
	 * Evaluates a context for one evaluation cycle, performs the appropriate state update and returns.
	 * @param context Context to evaluate
	 */
	public void evaluate(final Context<M> context) {
		
	}
	
	/**
	 * Creates a newly initialized context representing a specific machine instance.
	 * @return Newly create context
	 */
	public Context<M> createContext() {
		return null;
	}
	
	public interface StateFactory<T> {
		
		StateFactory<T> with(final Class<?> state);
		
		TransitionFactory<T> from(final Class<?> state);
		
	}
	
	public interface AndFactory<T> {
		
		TransitionFactory<T> from(final Class<?> state);
		
	}
	
	public interface TransitionFactory<T> {
		
		TransitionFactory<T> transition(final Class<?> state, final Predicate<Context<T>> predicate);
		
		TransitionFactory<T> transition(final Class<?> state, final Predicate<Context<T>> predicate, Function<T,T> modelFransform);
		
		AndFactory<T> and();
		
		ModelFactoryFactory<T> modelFactory(final Supplier<T> supplier);
		
	}
	
	public interface ModelFactoryFactory<T> {
		
		Factory<T> startWith(final Function<Context<T>, Class<?>> supplier);
		
	}
	
	public interface Factory<T> {
		
		Factory<T> haltOn(final Predicate<Context<T>> predicate);
		
		Factory<T> acceptOn(final Predicate<Context<T>> predicate);
		
		Machine<T> build() throws DefinitionFailedException;
	}
	
	/**
	 * Factory for constructing new implementation instances of the Machine interface.
	 */
	static final class FactoryImpl<T> implements StateFactory<T>, Factory<T>, TransitionFactory<T>, AndFactory<T>, ModelFactoryFactory<T> {
		
		private Map<Class<?>, Map<Class<?>, Tuple2<Predicate<Context<T>>, Function<T, T>>>> _transitions = new HashMap<>();
		
		private Class<?> _currentState;
		
		private Predicate<Context<T>> _haltOnPredicate = (c) -> false;
		
		private Predicate<Context<T>> _acceptOnPredicate = (c) -> false;
		
		private Function<Context<T>, Class<?>> _startStateFn;
		
		private Supplier<T> _modelFactorySupplier;
		
		/**
		 * Intentionally does nothing else but prevent instantiation of this factory class.
		 */
		FactoryImpl() {
			throw new UnsupportedOperationException("Instantiation not allowed.");
		}
		
		public StateFactory<T> with(final Class<?> state) {
			requireNonNull(state, "state shall not be null");
			
			_transitions.getOrDefault(state, new HashMap<>());
			
			return this;
		}
		
		public TransitionFactory<T> from(final Class<?> state) {
			_currentState = requireNonNull(state, "state shall not be null");
			
			return this;
		}
		
		public TransitionFactory<T> transition(final Class<?> state, final Predicate<Context<T>> predicate) {
			return transition(state, predicate, Function.identity());
		}
		
		@Override
		public TransitionFactory<T> transition(Class<?> state, Predicate<Context<T>> predicate, Function<T, T> modelFransform) {
			requireNonNull(state, "state shall not be null");
			requireNonNull(predicate, "predicate shall not be null");
			requireNonNull(modelFransform, "modelFransform shall not be null");
			
			_transitions.getOrDefault(_currentState, new HashMap<>()).put(state, new Tuple2<>(predicate, modelFransform));
			
			return this;
		}
		
		@Override
		public AndFactory<T> and() {
			return this;
		}
		
		public Factory<T> haltOn(final Predicate<Context<T>> predicate) {
			_haltOnPredicate = requireNonNull(predicate, "predicate shall not be null");
			
			return this;
		}
		
		public Factory<T> acceptOn(final Predicate<Context<T>> predicate) {
			_acceptOnPredicate = requireNonNull(predicate, "predicate shall not be null");
			
			return this;
		}
		
		public Factory<T> startWith(final Function<Context<T>, Class<?>> startStateFn) {
			_startStateFn = requireNonNull(startStateFn, "startStateFn shall not be null");
			
			return this;
		}
		
		public ModelFactoryFactory<T> modelFactory(final Supplier<T> supplier) {
			_modelFactorySupplier = requireNonNull(supplier, "supplier shall not be null");
			
			return this;
		}
		
		/**
		 * Builds a new implementation of the Machine interface using the definition in the provided strategy and uses the provided discriminator.
		 * @param <T> Model type used by the machine
		 * @param strategy Machine definition strategy
		 * @param discriminator State discriminator to be used by the machine
		 * @return Newly built machine
		 * @throws DefinitionFailedException If the machine fails to be defined without error
		 */
		public Machine<T> build() throws DefinitionFailedException {
			return null;
		}
	}
	
	public static <T> StateFactory<T> factory() {
		return new FactoryImpl<>();
	}
}
