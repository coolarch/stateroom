package cool.arch.stateroom;

/*
 * #%L cool.arch.stateroom:stateroom %% Copyright (C) 2015 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

import static java.util.Objects.requireNonNull;
import cool.arch.stateroom.enums.Status;

/**
 * Container for contextual information specific to an individual machine run instance.
 * @param <M> Type used to represent the machine model
 */
public final class Context<M> {

	private final State<M> state;

	private final Status status;

	private final M model;

	Context(final State<M> state, final Status status, final M model) {
		this.state = requireNonNull(state, "state shall not be null");
		this.status = requireNonNull(status, "status shall not be null");
		this.model = model;
	}

	Context<M> derive(M model) {
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
