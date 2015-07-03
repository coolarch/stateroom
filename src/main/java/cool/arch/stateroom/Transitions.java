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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public final class Transitions<M> implements Iterable<Transition<M>> {

	private final List<Transition<M>> transitions;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{'transitions':[");

		if (transitions != null && !transitions.isEmpty()) {
			final Queue<Transition<M>> transitionQueue = new LinkedList<>(transitions);
			sb.append(transitionQueue.poll());

			while (!transitionQueue.isEmpty()) {
				sb.append(",");
				sb.append(transitionQueue.poll());
			}
		}

		sb.append("]}");

		return sb.toString();
	}

	@Override
	public Iterator<Transition<M>> iterator() {
		return transitions.iterator();
	}

	public Transitions(final Collection<Transition<M>> transitions) {
		requireNonNull(transitions, "transitions shall not be null");
		this.transitions = new LinkedList<>(transitions);
	}

	public Set<String> findInvalidStateNames(final Set<State<M>> states) {
		return transitions.stream()
			.map(transition -> transition.getTargetState())
			.filter(state -> !states.contains(state))
			.map(state -> state.getName())
			.collect(Collectors.toSet());
	}
}
