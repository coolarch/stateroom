/*
 * DefinitionFailedException.java
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
 * Exception used to indicate that a failure took place while attempting to define the machine.
 */
public class DefinitionFailedException extends AbstractStateroomException {
	
	/**
	 * Serialization version id.
	 */
	private static final long serialVersionUID = 5892660387701024734L;
	
	/**
	 * Constructs a new instance with the message and throwable as null.
	 */
	public DefinitionFailedException() {
		super();
	}
	
	/**
	 * Constructs a new instance with a message and throwable as null.
	 * @param message text of the message
	 */
	public DefinitionFailedException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a new instance with a message and a Throwable cause.
	 * @param message text of the message
	 * @param cause Throwable that caused this exception to be thrown
	 */
	public DefinitionFailedException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a new instance with a Throwable cause.
	 * @param cause Throwable that caused this exception to be thrown
	 */
	public DefinitionFailedException(final Throwable cause) {
		super(cause);
	}
}
