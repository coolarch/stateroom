/*
 * QueueInputProvider.java
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
package com.unboundedprime.stateroom.core.inputprovider;

import static java.util.Objects.requireNonNull;

import java.util.Queue;
import java.util.function.Supplier;

/**
 * Implementation of the InputProvider interface that uses a queue data structure for it's data source.
 * @param <T> Type used to represent the input alphabet of the input provider
 */
public class QueueInputProvider<T> implements Supplier<T> {
	
	/**
	 * Queue from which object instances are obtained.
	 */
	private final Queue<T> _queue;
	
	/**
	 * Constructs a new QueueInputProvider based on the provided queue instance.
	 * @param queue Queue instance to use as a serial object source
	 */
	public QueueInputProvider(final Queue<T> queue) {
		requireNonNull(queue, "inputQueue may not be null");
		
		_queue = queue;
	}
	
	/**
	 * Getter for the inputQueue.
	 * @return the inputQueue
	 */
	public final Queue<T> getInputQueue() {
		return _queue;
	}
	
	@Override
	public T get() {
		return _queue.poll();
	}
}
