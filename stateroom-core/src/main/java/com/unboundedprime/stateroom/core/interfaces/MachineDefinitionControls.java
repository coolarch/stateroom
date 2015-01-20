/*
 * MachineDefinitionControls.java
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
package com.unboundedprime.stateroom.core.interfaces;

import java.util.function.Predicate;

import com.unboundedprime.stateroom.core.exception.DuplicateStateException;
import com.unboundedprime.stateroom.core.exception.UnknownStateException;

/**
 * Control interface for a MachineStrategy to request that states and transitions be added to a machine.
 * @param <M> Type used to represent the machine model
 */
public interface MachineDefinitionControls<M extends Model> {
	
	/**
	 * Adds a state to the machine.
	 * @param state State to add to the machine
	 * @param acceptState Truth of whether the state is an accept state
	 * @throws DuplicateStateException If a state that has previously been added is attempted to be added a second time
	 */
	void addState(final State<M> state, final boolean acceptState) throws DuplicateStateException;
	
	/**
	 * Adds a transition between two states with a predicate that indicates whether the transition should be followed.
	 * @param start State from which the transition exits
	 * @param end State to which the transition enters
	 * @param predicate Predicate instance used to determine the truth of whether the transition should be followed
	 * @throws UnknownStateException If an unknown state it referenced
	 */
	void addTransition(final State<M> start, final State<M> end, final Predicate<Context<M>> predicate) throws UnknownStateException;
}
