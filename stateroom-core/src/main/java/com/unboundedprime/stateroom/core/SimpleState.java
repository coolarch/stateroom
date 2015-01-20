/*
 * SimpleState.java
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

import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;
import com.unboundedprime.stateroom.core.interfaces.TransitionedInDelegate;
import com.unboundedprime.stateroom.core.interfaces.TransitionedOutDelegate;

import static java.util.Objects.requireNonNull;

/**
 * Simplified implementation of the State interface that allows delegates to be optionally plugged in.
 * @param <M> Type used to represent the machine model
 */
public class SimpleState<M extends Model> implements State<M> {
	
	/**
	 * Delegate to be called when transitioning into this state.
	 */
	private final TransitionedInDelegate<M> _transInDelegate;
	
	/**
	 * Delegate to be called when transitioning out of this state.
	 */
	private final TransitionedOutDelegate<M> _transOutDelegate;
	
	/**
	 * Name of the state instance.
	 */
	private final String _name;
	
	/**
	 * Constructs a new SimpleState instance without any delegates.
	 * @param name Name of the state
	 */
	public SimpleState(final String name) {
		this(name, null, null);
	}
	
	/**
	 * Constructs a new SimpleState instance with an inbound transition delegates.
	 * @param name Name of the state
	 * @param transInDelegate Delegate to be called when transitioning into the state
	 */
	public SimpleState(final String name, final TransitionedInDelegate<M> transInDelegate) {
		this(name, transInDelegate, null);
	}
	
	/**
	 * Constructs a new SimpleState instance with an inbound transition delegates.
	 * @param name Name of the state
	 * @param transOutDelegate Delegate to be called when transitioning out of the state
	 */
	public SimpleState(final String name, final TransitionedOutDelegate<M> transOutDelegate) {
		this(name, null, transOutDelegate);
	}
	
	/**
	 * Constructs a new SimpleState instance with both inbound and outbound transition delegates.
	 * @param name Name of the state
	 * @param transInDelegate Delegate to be called when transitioning into the state
	 * @param transOutDelegate Delegate to be called when transitioning out of the state
	 */
	public SimpleState(final String name, final TransitionedInDelegate<M> transInDelegate, final TransitionedOutDelegate<M> transOutDelegate) {
		requireNonNull(name, "name may not be null.");
		
		TransitionedInDelegate<M> determinedTransInDelegate = new NullDelegate();
		TransitionedOutDelegate<M> determinedTransOutDelegate = new NullDelegate();
		
		if (transInDelegate != null) {
			determinedTransInDelegate = transInDelegate;
		}
		
		if (transOutDelegate != null) {
			determinedTransOutDelegate = transOutDelegate;
		}
		
		this._name = name;
		this._transInDelegate = determinedTransInDelegate;
		this._transOutDelegate = determinedTransOutDelegate;
	}
	
	/**
	 * Handler for when transitioning into the state.
	 * @param context Machine context containing the machine instance under consideration
	 */
	@Override
	public final void stateTransitionedIn(final Context<M> context) {
		this._transInDelegate.handleStateTransitionedIn(context);
	}
	
	/**
	 * Handler for when transitioning out of the state.
	 * @param context Machine context containing the machine instance under consideration
	 */
	@Override
	public final void stateTransitionedOut(final Context<M> context) {
		this._transOutDelegate.handleStateTransitionedOut(context);
	}
	
	/**
	 * Getter for the name of the state.
	 * @return State name
	 */
	@Override
	public final String getStateName() {
		return this._name;
	}
	
	/**
	 * Class that implements both transition delegate interfaces and takes no action when called.
	 */
	private class NullDelegate implements TransitionedInDelegate<M>, TransitionedOutDelegate<M> {
		
		/**
		 * Handler for when transitioning into the state.
		 * @param context Machine context containing the machine instance under consideration
		 */
		@Override
		public void handleStateTransitionedIn(final Context<M> context) {
			// Intentionally do nothing
		}
		
		/**
		 * Handler for when transitioning out of the state.
		 * @param context Machine context containing the machine instance under consideration
		 */
		@Override
		public void handleStateTransitionedOut(final Context<M> context) {
			// Intentionally do nothing
		}
	}
}
