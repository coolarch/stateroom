/*
 * EndStateNotFoundException.java
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
package com.unboundedprime.stateroom.core.exception;

/**
 * Exception to indicate that a non-existent end state was referenced.
 */
public class EndStateNotFoundException extends UnknownStateException {
	
	/**
	 * Serialization version id.
	 */
	private static final long serialVersionUID = 7742611312268924827L;
	
	/**
	 * Constructs a new instance with the message and throwable as null.
	 */
	public EndStateNotFoundException() {
		super();
	}
	
	/**
	 * Constructs a new instance with a message and throwable as null.
	 * @param message text of the message
	 */
	public EndStateNotFoundException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a new instance with a message and a Throwable cause.
	 * @param message text of the message
	 * @param cause Throwable that caused this exception to be thrown
	 */
	public EndStateNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a new instance with a Throwable cause.
	 * @param cause Throwable that caused this exception to be thrown
	 */
	public EndStateNotFoundException(final Throwable cause) {
		super(cause);
	}
}
