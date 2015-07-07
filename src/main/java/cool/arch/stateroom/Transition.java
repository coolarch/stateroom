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

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transition<M> {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup()
		.lookupClass()
		.toString());

	private BiPredicate<State<M>, M> predicate;

	private State<M> targetState;

	private BiFunction<State<M>, M, M> modelTransform = (state, model) -> model;

	public M onEnter(final State<M> state, final M model) {
		LOGGER.log(Level.FINE,
			() -> String.format("Entering Transition: %s, Model before state: %s", Transition.this, model));
		final M resultingModel = modelTransform.apply(state, model);
		LOGGER.log(Level.FINE, () -> "Model after state: " + resultingModel);

		return resultingModel;
	}

	public BiFunction<State<M>, M, M> getModelTransform() {
		return modelTransform;
	}

	public BiPredicate<State<M>, M> getPredicate() {
		return predicate;
	}

	public State<M> getTargetState() {
		return targetState;
	}

	void setPredicate(BiPredicate<State<M>, M> predicate) {
		this.predicate = predicate;
	}

	void setTargetState(State<M> targetState) {
		this.targetState = targetState;
	}

	void setModelTransform(BiFunction<State<M>, M, M> modelTransform) {
		this.modelTransform = modelTransform;
	}

	@Override
	public String toString() {
		return String.format("{'targetState':%s}", targetState);
	}

	public static <T> TransitionBuilder<T> builder() {
		return new TransitionBuilderImpl<>();
	}
}
