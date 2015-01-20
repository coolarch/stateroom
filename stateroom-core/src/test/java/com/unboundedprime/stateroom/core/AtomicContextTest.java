/*
 * AtomicContextTest.java
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

import org.junit.Test;

import com.unboundedprime.stateroom.core.enums.Status;
import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;

/**
 * Test of the AtomicContext class.
 */
public class AtomicContextTest {
	
	/**
	 * Test of setCurrentState and getCurrentState methods, of class AtomicContext.
	 */
	@Test
	public void testSetCurrentStateAndGetCurrentState() {
		System.out.println("setCurrentStateAndGetCurrentState");
		
		final Context<Model> instance = new AtomicContext<Model>();
		final State<Model> expResult = new SimpleState<Model>("StateOne");
		
		instance.setCurrentState(expResult);
		final Object result = instance.getCurrentState();
		
		assertSame("getter must return same object as provided via setter", expResult, result);
	}
	
	/**
	 * Test of setStatus and getStatus methods, of class AtomicContext.
	 */
	@Test
	public void testSetStatusAndGetStatus() {
		System.out.println("setStatusAndGetStatus");
		
		final Context<Model> instance = new AtomicContext<Model>();
		final Status expResult = Status.ACCEPTED;
		
		instance.setStatus(expResult);
		final Status result = instance.getStatus();
		
		assertSame("getter must return same object as provided via setter", expResult, result);
	}
	
	/**
	 * Test of setModel and getModel methods, of class AtomicContext.
	 */
	@Test
	public void testSetModelAndGetModel() {
		System.out.println("setModelAndGetModel");
		
		final Context<Model> instance = new AtomicContext<Model>();
		final Model expResult = new TestModel();
		
		instance.setModel(expResult);
		final Model result = instance.getModel();
		
		assertSame("getter must return same object as provided via setter", expResult, result);
	}
	
	/**
	 * Model that always indicates that the model is at an end.
	 */
	private class TestModel implements Model {
		
		@Override
		public boolean isEnd() {
			return true;
		}
	}
}
