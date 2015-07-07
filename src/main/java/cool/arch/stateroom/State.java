package cool.arch.stateroom;

import java.lang.invoke.MethodHandles;

/*
 * #%L cool.arch.stateroom:stateroom %% Copyright (C) 2015 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class State<M> {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup()
		.lookupClass()
		.toString());

	private static final AtomicLong COUNTER = new AtomicLong(-1);

	private String name = "State " + COUNTER.incrementAndGet();

	private boolean acceptState = false;

	@Override
	public String toString() {
		return String.format("{'name':'%s', 'acceptState':%s}", name, Boolean.toString(acceptState));
	}

	/**
	 * 
	 */
	private BiFunction<State<M>, M, M> modelTransform = (state, model) -> model;

	public M onEnter(final M model) {
		LOGGER.log(Level.FINE, () -> String.format("Entering State: %s, Model before state: %s", State.this, model));
		final M resultingModel = modelTransform.apply(this, model);
		LOGGER.log(Level.FINE, () -> "Model after state: " + resultingModel);

		return resultingModel;
	}

	public String getName() {
		return name;
	}

	public boolean isAcceptState() {
		return acceptState;
	}

	public BiFunction<State<M>, M, M> getModelTransform() {
		return modelTransform;
	}

	void setName(String name) {
		this.name = name;
	}

	void setAcceptState(boolean acceptState) {
		this.acceptState = acceptState;
	}

	void setModelTransform(BiFunction<State<M>, M, M> modelTransform) {
		this.modelTransform = modelTransform;
	}

	public static <T> StateBuilder<T> builder() {
		return new StateBuilderImpl<>();
	}

	public static <T> State<T> of(final String name) {
		return State.<T> builder()
			.withName(name)
			.build();
	}

	public static <T> State<T> of(final String name, final boolean acceptState) {
		return State.<T> builder()
			.withName(name)
			.withAcceptState(acceptState)
			.build();
	}

	public static <T> State<T> of(final String name, final boolean acceptState,
		BiFunction<State<T>, T, T> modelTransform) {
		return State.<T> builder()
			.withName(name)
			.withAcceptState(acceptState)
			.withModelTransform(modelTransform)
			.build();
	}
}
