/*
 * SimpleStateTest.java
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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;

import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.TransitionedInDelegate;
import com.unboundedprime.stateroom.core.interfaces.TransitionedOutDelegate;

/**
 * Test of the SimpleState class.
 */
@RunWith(MockitoJUnit44Runner.class)
public class SimpleStateTest {
	
	/**
	 * Name of the state to be used in the various tests of this suite.
	 */
	private static final String TEST_STATE_NAME = "testState";
	
	/**
	 * Null valued field of type AtomicContext<Model> in order to facilitate testing using correct type.
	 */
	private static final Context<Model> NULL_MODEL_CONTEXT = null;
	
	/**
	 * Mock instance of a TransitionedInDelegate.
	 */
	@Mock
	private TransitionedInDelegate<Model> _transInDelegate;
	
	/**
	 * Mock instance of a TransitionedOutDelegate.
	 */
	@Mock
	private TransitionedOutDelegate<Model> _transOutDelegate;
	
	/**
	 * Test of stateTransitionedIn method, of class SimpleState.
	 */
	@Test
	public void testStateTransitionedIn() {
		System.out.println("stateTransitionedIn");
		
		final Context<Model> context = NULL_MODEL_CONTEXT;
		final SimpleState<Model> instance = new SimpleState<Model>(TEST_STATE_NAME);
		
		instance.stateTransitionedIn(context);
	}
	
	/**
	 * Test of stateTransitionedOut method, of class SimpleState.
	 */
	@Test
	public void testStateTransitionedOut() {
		System.out.println("stateTransitionedOut");
		
		final Context<Model> context = NULL_MODEL_CONTEXT;
		final SimpleState<Model> instance = new SimpleState<Model>(TEST_STATE_NAME);
		
		instance.stateTransitionedOut(context);
	}
	
	/**
	 * Test of getStateName method, of class SimpleState.
	 */
	@Test
	public void testGetStateName() {
		System.out.println("getStateName");
		
		final String expResult = TEST_STATE_NAME;
		final SimpleState<Model> instance = new SimpleState<Model>(expResult);
		
		final String result = instance.getStateName();
		
		assertSame("getter must return provided initialization object", expResult, result);
	}
	
	/**
	 * Test of the name and transitioned in delegate constructor, of class SimpleState.
	 */
	@Test
	public void testConstructorNameAndTransitionedInDelegate() {
		System.out.println("constructorNameAndTransitionedInDelegate");
		
		final String name = TEST_STATE_NAME;
		final Context<Model> context = new SimpleContext<Model>();
		
		final SimpleState<Model> instance = new SimpleState<Model>(name, this._transInDelegate);
		
		instance.stateTransitionedIn(context);
		
		// Verify that the delegate was proxied to using the same context
		verify(this._transInDelegate).handleStateTransitionedIn(context);
	}
	
	/**
	 * Test of the name and transitioned out delegate constructor, of class SimpleState.
	 */
	@Test
	public void testConstructorNameAndTransitionedOutDelegate() {
		System.out.println("constructorNameAndTransitionedOutDelegate");
		
		final String name = TEST_STATE_NAME;
		final Context<Model> context = new SimpleContext<Model>();
		
		final SimpleState<Model> instance = new SimpleState<Model>(name, this._transOutDelegate);
		
		instance.stateTransitionedOut(context);
		
		verify(this._transOutDelegate).handleStateTransitionedOut(context);
	}
	
	/**
	 * Test of the name and both transition delegates constructor, of class SimpleState.
	 */
	@Test
	public void testConstructorNameAndDelegates() {
		System.out.println("constructorNameAndDelegates");
		
		final String name = TEST_STATE_NAME;
		final Context<Model> context = new SimpleContext<Model>();
		
		final SimpleState<Model> instance = new SimpleState<Model>(name, this._transInDelegate, this._transOutDelegate);
		
		instance.stateTransitionedIn(context);
		instance.stateTransitionedOut(context);
		
		verify(this._transInDelegate).handleStateTransitionedIn(context);
		verify(this._transOutDelegate).handleStateTransitionedOut(context);
	}
}
