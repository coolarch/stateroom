/*
 * MachineStrategy.java
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

import com.unboundedprime.stateroom.core.exception.DuplicateStateException;
import com.unboundedprime.stateroom.core.exception.UnknownStateException;

/**
 * Interface for strategies that define machines.
 * @param <M> Type used to represent the machine model
 */
public interface MachineStrategy<M> {
	
	/**
	 * Causes to the definition strategy to define the machine using the provided controls.
	 * @param controls Controls with which to add states and transitions
	 * @throws UnknownStateException If an unknown state is referenced
	 * @throws DuplicateStateException If a state is attempted to be added twice
	 */
	void defineMachine(MachineDefinitionControls<M> controls) throws UnknownStateException, DuplicateStateException;
	
	/** Causes the start state to be populated into a context instance.
	 * @param context AtomicContext instance to populate
	 */
	void populateStartState(Context<M> context);
	
	/**
	 * Populates the context with any machine specific objects.
	 * @param context AtomicContext to be populated
	 */
	void populateContext(Context<M> context);
	
	/**
	 * Identifies whether atomicity is required.
	 * @return Whether atomicity is required
	 */
	boolean contextAtomicityRequired();
}
