/*
 * Context.java
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
package cool.arch.stateroom;

import static java.util.Objects.requireNonNull;

/**
 * Container for contextual information specific to an individual machine run instance.
 * @param <M> Type used to represent the machine model
 */
public final class Context<M> {
	
	private final State<M> state;
	
	private final Status status;
	
	private final M model;
	
	Context(final State<M> state, final Status status, final M model) {
		System.out.println("Created context");
		this.state = requireNonNull(state, "state shall not be null");
		this.status = requireNonNull(status, "status shall not be null");
		this.model = model;
	}
	
	@Deprecated
	public Context<M> derive(State<M> state) {
		return new Context<>(state, status, model);
	}
	
	@Deprecated
	public Context<M> derive(Status status) {
		return new Context<>(state, status, model);
	}
	
	@Deprecated
	public Context<M> derive(M model) {
		return new Context<>(state, status, model);
	}
	
	/**
	 * Gets the current state in this machine context after the most recent evaluation cycle.
	 * @return Current state in this run context
	 */
	public State<M> getState() {
		return state;
	}
	
	/**
	 * Gets the current status of the machine after the most recent evaluation cycle.
	 * @return Status of the machine in this run context
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Gets the model used by the context.
	 * @return the model
	 */
	public M getModel() {
		return model;
	}
}
