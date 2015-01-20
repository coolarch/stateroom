/*
 * TransitionMapping.java
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

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;

/**
 * Provides a structured mapping between a single target state and an instance of the Predicate implementation.
 * <p>
 * The predicate indicates the instantaneous truth as to whether the target state should become the new state of the machine for
 * the next evaluation of the machine.
 * </p>
 * @param <M> Type used to represent the machine model
 */
class TransitionMapping<M extends Model> {
	
	/**
	 * Predicate used to determine if this state transition should be taken.
	 */
	private final Predicate<Context<M>> _predicate;
	
	/**
	 * Target state to transition to, if the predicate evaluates to true, such that a transition should take place.
	 */
	private final State<M> _targetState;
	
	/**
	 * Constructs a new TransitionMapping.
	 * @param predicate Predicate instance used to determine if this state transition should be taken
	 * @param targetState target state to which to transition, if the predicate evaluates to true
	 */
	public TransitionMapping(final Predicate<Context<M>> predicate, final State<M> targetState) {
		requireNonNull(predicate, "predicate may not be null");
		requireNonNull(targetState, "targetState may not be null");
		
		this._predicate = predicate;
		this._targetState = targetState;
	}
	
	/**
	 * Getter for the predicate field.
	 * @return Predicate instance used to determine if this state transition should be followed
	 */
	public Predicate<Context<M>> getPredicate() {
		return this._predicate;
	}
	
	/**
	 * Getter for the targetState field.
	 * @return target state that should be transitioned to, if the predicate evaluates to true
	 */
	public State<M> getTargetState() {
		return this._targetState;
	}
}
