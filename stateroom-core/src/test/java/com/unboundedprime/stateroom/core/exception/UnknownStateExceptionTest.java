/*
 * UnknownStateExceptionTest.java
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Test of the UnknownStateException class.
 */
public class UnknownStateExceptionTest {
	
	/**
	 * Message text to use when testing the exception constructors.
	 */
	private static final String EXPECTED_MESSAGE_RESULT = "This is a test";
	
	/**
	 * Throwable object instance to use when testing the exception constructors.
	 */
	private static final Throwable EXPECTED_THROWABLE = new Exception();
	
	/**
	 * Test of the default constructor in the UnknownStateException class.
	 */
	@Test
	public void testConstructionNoArgs() {
		System.out.println("testConstructionNoArgs");
		
		final UnknownStateException instance = new UnknownStateException();
		
		assertNotNull("instance may not be null", instance);
	}
	
	/**
	 * Test of the message text constructor in the UnknownStateException class.
	 */
	@Test
	public void testConstructionOnlyMessage() {
		System.out.println("testConstructionOnlyMessage");
		
		final UnknownStateException instance = new UnknownStateException(EXPECTED_MESSAGE_RESULT);
		
		final String messageResult = instance.getMessage();
		
		assertEquals("getMessage must return the same text string as was provided during UnknownStateException instantiation", messageResult, EXPECTED_MESSAGE_RESULT);
	}
	
	/**
	 * Test of the message text constructor in the UnknownStateException class.
	 */
	@Test
	public void testConstructionMessageAndThrowable() {
		System.out.println("testConstructionMessageAndThrowable");
		
		final UnknownStateException instance = new UnknownStateException(EXPECTED_MESSAGE_RESULT, EXPECTED_THROWABLE);
		
		final String messageResult = instance.getMessage();
		final Throwable throwableResult = instance.getCause();
		
		assertEquals("getMessage must return the same text string as was provided during UnknownStateException instantiation", messageResult, EXPECTED_MESSAGE_RESULT);
		assertSame("getCause must return the same Throwable instance as was provided during UnknownStateException instantiation", throwableResult, EXPECTED_THROWABLE);
	}
	
	/**
	 * Test of the message text constructor in the UnknownStateException class.
	 */
	@Test
	public void testConstructionOnlyThrowable() {
		System.out.println("testConstructionOnlyThrowable");
		
		final UnknownStateException instance = new UnknownStateException(this.EXPECTED_THROWABLE);
		
		final Throwable throwableResult = instance.getCause();
		
		assertSame("getCause must return the same Throwable instance as was provided during UnknownStateException instantiation", throwableResult, EXPECTED_THROWABLE);
	}
}
