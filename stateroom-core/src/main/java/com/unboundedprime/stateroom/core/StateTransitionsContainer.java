/*
 * StateTransitionsContainer.java
 *
 * Stateroom - Java framework library for finite state machines
 * Copyright (C) 2009 by Matthew Werny
 * All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.unboundedprime.stateroom.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;

/**
 * Contains a state and it's associate transitions.
 * @param <M> Type used to represent the machine model
 */
class StateTransitionsContainer<M extends Model> {
	
	/**
	 * Transition mappings that originate from the state being represented.
	 */
	private final List<TransitionMapping<M>> _transitions = new ArrayList<TransitionMapping<M>>();
	
	/**
	 * State object instance associated to the transition mappings.
	 */
	private final State<M> _state;
	
	/**
	 * Indicates the truth of the state being contained is an accept state.
	 */
	private final boolean _acceptState;
	
	/**
	 * Constructs a new StateTransitionsContainer instance.
	 * @param state State instance for this state container
	 * @param acceptState Whether the state in this container is an accept state
	 */
	public StateTransitionsContainer(final State<M> state, final boolean acceptState) {
		requireNonNull(state, "state may not be null.");
		
		_acceptState = acceptState;
		_state = state;
	}
	
	/**
	 * Adds a transition to a target state.
	 * @param targetState State to which the machine should transition, if the predicate evaluates to true
	 * @param predicate Predicate used to determine if the current transition under consideration should be followed
	 */
	public void addTransition(final State<M> targetState, final Predicate<Context<M>> predicate) {
		_transitions.add(new TransitionMapping<M>(predicate, targetState));
	}
	
	/**
	 * Determines the next state to which the machine should transition the context.
	 * @param context Context and it's contained model that is used by predicate when determining if they are met
	 * @return Resolved state, null if not found
	 */
	public State<M> resolveNextState(final Context<M> context) {
		State<M> foundState = null;
		
		for (final TransitionMapping<M> mapping : this._transitions) {
			if (mapping.getPredicate().test(context)) {
				foundState = mapping.getTargetState();
				break;
			}
		}
		
		return foundState;
	}
	
	/**
	 * Gets whether the contained state is an accept state.
	 * @return the truth as to whether this is an accept state
	 */
	public boolean isAcceptState() {
		return _acceptState;
	}
	
	/**
	 * Gets the state instance.
	 * @return the state
	 */
	public State<M> getState() {
		return _state;
	}
}
