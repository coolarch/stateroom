/*
 * StateTransitionsContainerTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.unboundedprime.stateroom.core.enums.Status;
import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;
import com.unboundedprime.stateroom.core.predicates.AbsurdityPredicate;
import com.unboundedprime.stateroom.core.predicates.TautologyPredicate;

/**
 * Test of the StateTransitionsContainer class.
 */
public class StateTransitionsContainerTest {
	
	/**
	 * Test of addTransition and resolveNextState methods, of class StateTransitionsContainer.
	 */
	@Test
	public void testAddTransitionAndResolveNextState() {
		System.out.println("addTransitionAndResolveNextState");
		
		final State<Model> startState = new SimpleState<Model>("startState");
		final State<Model> expResult = new SimpleState<Model>("expectedState");
		final Context<Model> context = new SimpleContext<Model>();
		context.setCurrentState(startState);
		context.setStatus(Status.READY);
		
		final StateTransitionsContainer<Model> instance = new StateTransitionsContainer<Model>(startState, false);
		instance.addTransition(expResult, new AbsurdityPredicate<Model>());
		instance.addTransition(expResult, new TautologyPredicate<Model>());
		
		final State<Model> result = instance.resolveNextState(context);
		
		// Assert that the expected target state is returned
		assertSame(expResult, result);
	}
	
	/**
	 * Test of resolveNextState method with no states added, of class StateTransitionsContainer.
	 */
	@Test
	public void testResolveNextStateNoStates() {
		System.out.println("resolveNextStateNoStates");
		
		final State<Model> startState = new SimpleState<Model>("startState");
		final Context<Model> context = new SimpleContext<Model>();
		context.setCurrentState(startState);
		context.setStatus(Status.READY);
		
		final StateTransitionsContainer<Model> instance = new StateTransitionsContainer<Model>(startState, false);
		
		final State<Model> result = instance.resolveNextState(context);
		
		// Assert that the expected target state is null
		assertNull(result);
	}
	
	/**
	 * Test of the constructor with a null state provided, of class StateTransitionsContainer.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructionNullState() {
		System.out.println("ConstructionNullState");
		
		final StateTransitionsContainer<Model> instance = new StateTransitionsContainer<Model>(null, false);
		
		assertNull("This assert should never have been reached", instance);
	}
	
	/**
	 * Test of isEndState method, of class StateTransitionsContainer, for true input.
	 */
	@Test
	public void testIsEndStateValueTrue() {
		System.out.println("isEndStateValueTrue");
		
		final boolean expResult = Boolean.TRUE;
		final StateTransitionsContainer<Model> instance = new StateTransitionsContainer<Model>(new SimpleState<Model>("testState"), expResult);
		
		final boolean result = instance.isAcceptState();
		
		assertEquals("must be true that the machine is in an accept state", expResult, result);
	}
	
	/**
	 * Test of isEndState method, of class StateTransitionsContainer, for false input.
	 */
	@Test
	public void testIsEndStateValueFalse() {
		System.out.println("isEndStateValueFalse");
		
		final boolean expResult = Boolean.FALSE;
		final StateTransitionsContainer<Model> instance = new StateTransitionsContainer<Model>(new SimpleState<Model>("testState"), expResult);
		
		final boolean result = instance.isAcceptState();
		
		assertEquals("must be false that the machine is in an accept state", expResult, result);
	}
	
	/**
	 * Test of getState method, of class StateTransitionsContainer.
	 */
	@Test
	public void testGetState() {
		System.out.println("getState");
		
		final State<Model> expResult = new SimpleState<Model>("testState");
		final StateTransitionsContainer<Model> instance = new StateTransitionsContainer<Model>(expResult, false);
		
		final State<Model> result = instance.getState();
		
		assertSame("returned state must be the same object as was provided during construction", expResult, result);
	}
}
