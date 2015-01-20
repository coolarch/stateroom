/*
 * State.java
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

/**
 * Interface for the minimal requirements for a state to be used in a machine.
 * @param <M> Type used to represent the machine model
 */
public interface State<M extends Model> {
	
	/**
	 * Implementation specific method called whenever a machine context is transitioned to the state.
	 * @param context Machine context for the specific machine instance that has transitioned to the state
	 */
	void stateTransitionedIn(final Context<M> context);
	
	/**
	 * Implementation specific method called whenever a machine context is transitioned out of the state.
	 * @param context Machine context for the specific machine instance that has transitioned to the state
	 */
	void stateTransitionedOut(final Context<M> context);
	
	/**
	 * Gets the unique name of the state.
	 * @return Name of the state
	 */
	String getStateName();
}
