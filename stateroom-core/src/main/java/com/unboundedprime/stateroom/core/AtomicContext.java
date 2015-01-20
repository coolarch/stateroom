/*
 * AtomicContext.java
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

import java.util.concurrent.atomic.AtomicReference;

import com.unboundedprime.stateroom.core.enums.Status;
import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;

/**
 * Container for contextual information specific to an individual machine run instance.
 * @param <M> Type used to represent the machine model
 */
public class AtomicContext<M extends Model> implements Context<M> {
	
	/**
	 * Current state of the context.
	 */
	private AtomicReference<State<M>> _currentState = new AtomicReference<State<M>>();
	
	/**
	 * Current status of the context.
	 */
	private AtomicReference<Status> _status = new AtomicReference<Status>();
	
	/**
	 * Model used by the context.
	 */
	private AtomicReference<M> _model = new AtomicReference<M>();
	
	/**
	 * Gets the current state in this machine context after the most recent evaluation cycle.
	 * @return Current state in this run context
	 */
	@Override
	public final State<M> getCurrentState() {
		return this._currentState.get();
	}
	
	/**
	 * Sets the current state in this machine context after the most recent evaluation cycle.
	 * @param currentState State to be set as the current state in this run context
	 */
	@Override
	public final void setCurrentState(final State<M> currentState) {
		this._currentState.set(currentState);
	}
	
	/**
	 * Gets the current status of the machine after the most recent evaluation cycle.
	 * @return Status of the machine in this run context
	 */
	@Override
	public final Status getStatus() {
		return this._status.get();
	}
	
	/**
	 * Sets the current status of the machine after the most recent evaluation cycle.
	 * @param status Status of the machine in this run context
	 */
	@Override
	public final void setStatus(final Status status) {
		this._status.set(status);
	}
	
	/**
	 * Gets the model used by the context.
	 * @return the model
	 */
	@Override
	public final M getModel() {
		return this._model.get();
	}
	
	/**
	 * Sets the model used by the context.
	 * @param model the model to set
	 */
	@Override
	public final void setModel(final M model) {
		this._model.set(model);
	}
}
