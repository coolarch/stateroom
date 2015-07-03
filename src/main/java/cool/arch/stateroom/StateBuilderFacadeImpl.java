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

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

final class StateBuilderFacadeImpl<M> implements StateBuilderFacade<M> {

	private final MachineBuilderImpl<M> builder;

	private final State<M> state;

	private final Queue<Transition<M>> builtTransitions;

	StateBuilderFacadeImpl(MachineBuilderImpl<M> builder, State<M> state) {
		this(builder, state, new LinkedList<>());
	}

	StateBuilderFacadeImpl(MachineBuilderImpl<M> builder, State<M> state, Queue<Transition<M>> builtTransitions) {
		this.builder = builder;
		this.state = state;
		this.builtTransitions = builtTransitions;
	}

	@Override
	public StateBuilderFacade<M> withState(State<M> state) {
		builder.withState(this.state, new Transitions<>(builtTransitions));

		return new StateBuilderFacadeImpl<>(builder, state);
	}

	@Override
	public Machine<M> build() {
		builder.withState(state, new Transitions<>(builtTransitions));

		return builder.build();
	}

	@Override
	public StateBuilderFacade<M> to(State<M> targetState, BiPredicate<State<M>, M> predicate) {
		final Transition<M> transition = Transition.<M> builder()
			.to(targetState)
			.when(predicate)
			.build();

		builtTransitions.add(transition);

		return this;
	}

	@Override
	public StateBuilderFacade<M> to(State<M> targetState, BiPredicate<State<M>, M> predicate,
		BiFunction<State<M>, M, M> modelTransform) {
		final Transition<M> transition = Transition.<M> builder()
			.to(targetState)
			.when(predicate)
			.transformModelWith(modelTransform)
			.build();

		builtTransitions.add(transition);

		return this;
	}
}
