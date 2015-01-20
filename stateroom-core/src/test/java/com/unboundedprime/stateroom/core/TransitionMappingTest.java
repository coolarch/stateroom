/*
 * TransitionMappingTest.java
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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.function.Predicate;

import org.junit.Test;

import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;
import com.unboundedprime.stateroom.core.predicates.TautologyPredicate;

/**
 * Test of the TransitionMapping class.
 */
public class TransitionMappingTest {
	
	/**
	 * Predicate instance that always evaluates as true.
	 */
	private final Predicate<Context<Model>> _predicate = new TautologyPredicate<Model>();
	
	/**
	 * Test of getPredicate method, of class TransitionMapping.
	 */
	@Test
	public void testGetPredicate() {
		System.out.println("getPredicate");
		
		final TransitionMapping<Model> instance = new TransitionMapping<Model>(this._predicate, new SimpleState<Model>("testState"));
		
		final Predicate<Context<Model>> result = instance.getPredicate();
		
		assertSame("provided predicate must be the same one returned by getPredicate as was provided during TransitionMapping construction", this._predicate, result);
	}
	
	/**
	 * Test of getTargetState method, of class TransitionMapping.
	 */
	@Test
	public void testGetTargetState() {
		System.out.println("getTargetState");
		
		final State<Model> expResult = new SimpleState<Model>("testState");
		final TransitionMapping<Model> instance = new TransitionMapping<Model>(this._predicate, expResult);
		
		final State<Model> result = instance.getTargetState();
		
		assertSame("getTargetState must return the targetState provided during TransitionMapping instantiation", expResult, result);
	}
	
	/**
	 * Test of construction, of class TransitionMapping, for a null predicate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructionNullPredicate() {
		System.out.println("constructionNullPredicate");
		
		final TransitionMapping<Model> instance = new TransitionMapping<Model>(null, new SimpleState<Model>("testState"));
		
		assertNull("this assertion should never be reached", instance);
	}
	
	/**
	 * Test of construction, of class TransitionMapping, for a null target state.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructionNullTargetState() {
		System.out.println("constructionNullTargetState");
		
		final TransitionMapping<Model> instance = new TransitionMapping<Model>(this._predicate, null);
		
		assertNull("this assertion should never be reached", instance);
	}
}
