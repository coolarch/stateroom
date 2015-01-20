package com.unboundedprime.stateroom.core;

import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.unboundedprime.stateroom.core.interfaces.Context;

/**
 * 
 */
public class MachineTest {
	
	private Machine<Object> instance;
	
	/**
	 * @throws Exception If the machine 
	 */
	@Before
	public void setUp() throws Exception {
		instance = Machine.factory()
			.with(EE.class)
			.with(EO.class)
			.with(OE.class)
			.with(OO.class)
				.from(EE.class)
					.transition(EO.class, c -> true)
					.transition(OE.class,c -> true, m -> m.toString())
			.and()
				.from(EO.class)
					.transition(EE.class,c -> true)
					.transition(OO.class,c -> true)
			.and()
				.from(OE.class)
					.transition(EE.class,c -> true)
					.transition(OO.class,c -> true)
			.and()
				.from(OO.class)
					.transition(EE.class,c -> true)
					.transition(OO.class,c -> true)
			.modelFactory(() -> new Object() {})
			.startWith(c -> EE.class)
			.haltOn(c -> true)
			.acceptOn(c -> true)
			.build();
	}
	
	/**
	 * 
	 */
	@Test
	public void testEvaluateUntilHalted() {
		final Context<Object> context = instance.createContext();
		instance.evaluateUntilHalted(context);
	}
	
	/**
	 * 
	 */
	@Test
	public void testEvaluate() {
		final Context<Object> context = instance.createContext();
		instance.evaluateUntilHalted(context);
	}
	
	/**
	 * 
	 */
	@Test
	public void testCreateContext() {
		final Context<Object> context = instance.createContext();
		assertNotNull("", context);
	}
	
	public interface EE {}
	
	public interface EO {}
	
	public interface OE {}
	
	public interface OO {}
}
