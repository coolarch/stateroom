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

import static cool.arch.stateroom.enums.Status.READY;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import cool.arch.stateroom.enums.Status;

public class Machine<M> {

	private Supplier<M> modelSupplier;

	private BiFunction<State<M>, M, M> preEvaluationTransform = (state, model) -> model;

	private State<M> startState;

	private BiPredicate<State<M>, M> haltPredicate = (state, model) -> false;

	private final Map<State<M>, Transitions<M>> transitions = new HashMap<>();

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{'startState':");
		sb.append(startState);
		sb.append(", 'transitions':{");

		if (transitions != null) {
			final String transitionsString = transitions.entrySet()
				.stream()
				.map(e -> String.format("'%s':%s", e.getKey()
					.getName(), e.getValue()))
				.collect(Collectors.joining(","));

			sb.append(transitionsString);
		}

		sb.append("},'states':[");

		sb.append(transitions.keySet()
			.stream()
			.map(e -> e.toString())
			.collect(Collectors.joining(",")));

		sb.append("]}");

		return sb.toString();
	}

	public static <T> MachineBuilder<T> builder(Class<T> modelType) {
		return new MachineBuilderImpl<>();
	}

	public Context<M> create() {
		return new Context<>(startState, READY, modelSupplier.get());
	}

	public Context<M> evaluate(final Context<M> context) {
		State<M> state = context.getState();
		Status status = context.getStatus();
		final M model = context.getModel();
		final M transformedModel = preEvaluationTransform.apply(state, model);
		M resultingModel = transformedModel;

		final Transitions<M> stateTransitions = transitions.get(context.getState());

		if (stateTransitions != null) {
			for (final Transition<M> transition : stateTransitions) {
				final BiPredicate<State<M>, M> predicate = transition.getPredicate();

				if (predicate.test(state, transformedModel)) {
					state = transition.getTargetState();
					final BiFunction<State<M>, M, M> modelTransform = transition.getModelTransform();
					resultingModel = modelTransform.apply(state, transformedModel);
					break;
				}
			}
		}

		if (READY.equals(status) && haltPredicate.test(state, model)) {
			if (state.isAcceptState()) {
				status = Status.ACCEPTED;
			} else {
				status = Status.CRASHED;
			}
		}

		return new Context<>(state, status, resultingModel);
	}

	public Context<M> evaluateUntilHalted(final Context<M> context) {
		Context<M> currentContext = context;

		while (READY.equals(currentContext.getStatus())) {
			currentContext = evaluate(currentContext);
		}

		return currentContext;
	}

	Supplier<M> getModelSupplier() {
		return modelSupplier;
	}

	BiFunction<State<M>, M, M> getPreEvaluationTransform() {
		return preEvaluationTransform;
	}

	State<M> getStartState() {
		return startState;
	}

	BiPredicate<State<M>, M> getHaltPredicate() {
		return haltPredicate;
	}

	Map<State<M>, Transitions<M>> getTransitions() {
		return transitions;
	}

	void setModelSupplier(Supplier<M> modelSupplier) {
		this.modelSupplier = modelSupplier;
	}

	void setPreEvaluationTransform(BiFunction<State<M>, M, M> preEvaluationTransform) {
		this.preEvaluationTransform = preEvaluationTransform;
	}

	void setStartState(State<M> startState) {
		this.startState = startState;
	}

	void setHaltPredicate(BiPredicate<State<M>, M> haltPredicate) {
		this.haltPredicate = haltPredicate;
	}
}
