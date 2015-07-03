package cool.arch.stateroom;

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

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

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
	public TransitionBuilder<M> when(BiPredicate<State<M>, M> predicate) {
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
