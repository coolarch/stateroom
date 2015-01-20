/*
 * DefaultStateDiscriminatorTest.java
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
package com.unboundedprime.stateroom.core.discriminator;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.unboundedprime.stateroom.core.SimpleContext;
import com.unboundedprime.stateroom.core.SimpleState;
import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;

/**
 * Test of the DefaultStateDiscriminator class.
 */
public class DefaultStateDiscriminatorTest {
	
	/**
	 * State instance to assist in testing.
	 */
	private static final State<SimpleModel> STATE = new SimpleState<SimpleModel>("Simple State");
	
	/**
	 * Test of getCurrentState method, of class DefaultStateDiscriminator.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetCurrentStateNullContext() {
		
		System.out.println("getCurrentStateNullContext");
		
		final Context<SimpleModel> context = null;
		final DefaultStateDiscriminator instance = new DefaultStateDiscriminator();
		
		instance.getCurrentState(context);
	}
	
	/**
	 * Test of getCurrentState method, of class DefaultStateDiscriminator.
	 */
	@Test
	public void testGetCurrentStateNonNullContext() {
		
		System.out.println("getCurrentStateNonNullContext");
		
		final Context<SimpleModel> context = new SimpleContext<SimpleModel>();
		final DefaultStateDiscriminator instance = new DefaultStateDiscriminator();
		final State expResult = STATE;
		context.setCurrentState(STATE);
		final State result = instance.getCurrentState(context);
		
		assertSame("states must be the same object", expResult, result);
	}
	
	/**
	 * Simple model to assist in creating a context.
	 */
	private class SimpleModel implements Model {
		@Override
		public boolean isEnd() {
			return true;
		}
	}
}
