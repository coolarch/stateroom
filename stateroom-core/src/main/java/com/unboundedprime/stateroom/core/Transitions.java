package com.unboundedprime.stateroom.core;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final class Transitions<M> implements Iterable<Transition<M>> {
	
	private final List<Transition<M>> transitions = new LinkedList<>();

	@Override
	public Iterator<Transition<M>> iterator() {
		return transitions.iterator();
	}
	
	Transitions(final Transition<M>[] transistions) {
		requireNonNull(transistions, "transistions shall not be null");
		
		stream(transistions).forEachOrdered(this.transitions::add);
	}
	
	public Set<String> findInvalidStateNames(final Set<State<M>> states) {
		return transitions.stream()
			.map(transition -> transition.getTargetState())
			.filter(state -> !states.contains(state))
			.map(state -> state.getName())
			.collect(Collectors.toSet());
	}
}
