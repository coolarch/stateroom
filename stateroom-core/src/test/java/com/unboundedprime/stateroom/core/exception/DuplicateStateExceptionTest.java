/*
 * DuplicateStateExceptionTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Test of the DuplicateStateException class. */
public class DuplicateStateExceptionTest {
	
	/** Message text to use when testing the exception constructors. */
	private String _expectedMessageResult = "This is a test";
	
	/** Throwable object instance to use when testing the exception constructors. */
	private Throwable _expectedThrowable = new Exception();
	
	/** Test of the default constructor in the DuplicateStateException class. */
	@Test
	public void testConstructionNoArgs() {
		System.out.println("testConstructionNoArgs");
		
		// Create an instance
		final DuplicateStateException instance = new DuplicateStateException();
		
		// Assert true, because if we got this far, nothing blew up
		assertTrue(true);
	}
	
	/** Test of the message text constructor in the UnknownStateException class. */
	@Test
	public void testConstructionOnlyMessage() {
		System.out.println("testConstructionOnlyMessage");
		
		// Create an instance
		final DuplicateStateException instance = new DuplicateStateException(this._expectedMessageResult);
		// Get the message from the exception instance
		final String messageResult = instance.getMessage();
		
		// Assert that the same message is retained in the exception
		assertEquals(messageResult, this._expectedMessageResult);
	}
	
	/** Test of the message text constructor in the UnknownStateException class. */
	@Test
	public void testConstructionMessageAndThrowable() {
		System.out.println("testConstructionMessageAndThrowable");
		
		// Create an instance
		final DuplicateStateException instance = new DuplicateStateException(this._expectedMessageResult, this._expectedThrowable);
		// Get the message from the exception instance
		final String messageResult = instance.getMessage();
		// Get the cause from the exception instance
		final Throwable throwableResult = instance.getCause();
		
		// Assert that the same message is retained in the exception
		assertEquals(messageResult, this._expectedMessageResult);
		// Assert that the same exception is retained in the exception
		assertSame(throwableResult, this._expectedThrowable);
	}
	
	/** Test of the message text constructor in the UnknownStateException class. */
	@Test
	public void testConstructionOnlyThrowable() {
		System.out.println("testConstructionOnlyThrowable");
		
		// Create an instance
		final DuplicateStateException instance = new DuplicateStateException(this._expectedThrowable);
		// Get the cause from the exception instance
		final Throwable throwableResult = instance.getCause();
		
		// Assert that the same exception is retained in the exception
		assertSame(throwableResult, this._expectedThrowable);
	}
}
