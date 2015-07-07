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

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import cool.arch.stateroom.enums.Status;

public class Machine<M> {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup()
		.lookupClass()
		.getName());

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

		LOGGER.log(
			Level.FINE,
			() -> String.format("Evaluating machine with State: %s, Status: %s, Model: %s", context.getState(),
				context.getStatus(), context.getModel()));

		final M transformedModel = preEvaluationTransform.apply(state, model);

		LOGGER.log(Level.FINE, () -> String.format("After preevaluation transform. Model: %s", transformedModel));

		M resultingModel = transformedModel;

		final Transitions<M> stateTransitions = transitions.get(context.getState());

		if (stateTransitions != null) {
			for (final Transition<M> transition : stateTransitions) {
				LOGGER.log(Level.FINE, () -> String.format("Evaluating transition %s", transition));

				final BiPredicate<State<M>, M> predicate = transition.getPredicate();

				if (predicate.test(state, transformedModel)) {
					LOGGER.log(Level.FINE, "Predicate accepted");

					state = transition.getTargetState();
					resultingModel = transition.onEnter(state, resultingModel);
					resultingModel = state.onEnter(resultingModel);

					final State<M> currentState = state;
					final M currentModel = resultingModel;

					LOGGER.log(Level.FINE,
						() -> String.format("Resulting state: %s, Model: %s", currentState, currentModel));
					break;
				}
			}
		}

		final boolean shouldHalt = haltPredicate.test(state, resultingModel);

		LOGGER.log(Level.FINE, () -> String.format("Appropriate to halt: %s", Boolean.valueOf(shouldHalt)));

		if (READY.equals(status) && shouldHalt) {
			if (state.isAcceptState()) {
				status = Status.ACCEPTED;
				LOGGER.log(Level.FINE, "Setting status ACCEPTED");
			} else {
				status = Status.CRASHED;
				LOGGER.log(Level.FINE, "Setting status CRASHED");
			}
		}

		final Context<M> resultingContext = new Context<>(state, status, resultingModel);

		LOGGER.log(Level.FINE,
			() -> String.format("Completing evaluation with resulting context: %s", resultingContext));

		return resultingContext;
	}

	public Context<M> evaluateUntilHalted(final Context<M> context) {
		Context<M> currentContext = context;

		LOGGER.log(Level.FINE, "Evaluating until halted");

		while (READY.equals(currentContext.getStatus())) {
			currentContext = evaluate(currentContext);
		}

		LOGGER.log(Level.FINE, "Completed evaluation");

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
