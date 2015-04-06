package cool.arch.stateroom;

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
