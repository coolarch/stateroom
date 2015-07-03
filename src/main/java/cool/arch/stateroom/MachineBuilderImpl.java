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
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class MachineBuilderImpl<M> extends AbstractBuilderImpl<Machine<M>> implements MachineBuilder<M> {

	protected MachineBuilderImpl() {
		super(new Machine<>());
	}

	@Override
	protected Set<String> validate(Set<String> errors, Machine<M> instance) {
		final Set<State<M>> states = instance.getTransitions()
			.keySet();

		final String invalidStates = instance.getTransitions()
			.values()
			.stream()
			.flatMap(t -> t.findInvalidStateNames(states)
				.stream())
			.collect(Collectors.joining(", "));

		if (!"".equals(invalidStates)) {
			errors.add("Invalid states: " + invalidStates);
		}

		if (!states.contains(instance.getStartState())) {
			errors.add("Invalid start state: " + instance.getStartState()
				.getName());
		}

		return errors;
	}

	public MachineBuilder<M> withState(State<M> state, Transitions<M> transitions) {
		getInstance().getTransitions()
			.put(state, transitions);

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
		getInstance().setPreEvaluationTransform(
			requireNonNull(preEvaluationTransform, "preEvaluationTransform shall not be null"));

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
