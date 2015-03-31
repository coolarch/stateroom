/**
 * 
 */
package com.unboundedprime.stateroom.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import com.unboundedprime.stateroom.core.interfaces.Context;

/**
 * 
 */
public class StateTest {

	@SuppressWarnings("unchecked")
	@Test
	public final void test() {
		final Queue<String> input = new LinkedList<>();
		input.add("A");
		input.add("B");
		input.add("B");
		input.add("A");
		input.add("B");
		input.add("A");
		input.add("B");
		input.add("A");
		
		final State<String> evenEven = State.<String>builder().withName("Even Even").withAcceptState(true).build();
		final State<String> evenOdd = State.<String>builder().withName("Even Odd").build();
		final State<String> oddEven = State.<String>builder().withName("Odd Even").build();
		final State<String> oddOdd = State.<String>builder().withName("Odd Odd").build();
		
		final Machine<String> machine = Machine.<String>builder()
			.withModelSupplier(input::poll)
			.withStartState(evenEven)
			.withPreEvaluationTransform(c -> input.poll())
			.withContinuePredicate(c -> c.getModel() != null)
			.withState(evenEven,
				 Transition.<String>builder().withTargetState(oddEven).withPredicate(c -> "A".equals(c.getModel())).build(),
				Transition.<String>builder().withTargetState(evenOdd).withPredicate(c -> "B".equals(c.getModel())).build())
			.withState(oddEven,
				 Transition.<String>builder().withTargetState(evenEven).withPredicate(c -> "A".equals(c.getModel())).build(),
				Transition.<String>builder().withTargetState(oddOdd).withPredicate(c -> "B".equals(c.getModel())).build())
			.withState(evenOdd,
				Transition.<String>builder().withTargetState(oddOdd).withPredicate(c -> "A".equals(c.getModel())).build(),
				Transition.<String>builder().withTargetState(evenEven).withPredicate(c -> "B".equals(c.getModel())).build())
			.withState(oddOdd,
				Transition.<String>builder().withTargetState(evenOdd).withPredicate(c -> "A".equals(c.getModel())).build(),
				Transition.<String>builder().withTargetState(oddEven).withPredicate(c -> "B".equals(c.getModel())).build())
			.build();

		final Context<String> context = machine.createContext();
		final Context<String> newContext = machine.evaluateUntilHalted(context);
		final State<String> resultingState = newContext.getState();
		
		assertSame(evenEven, resultingState);
		assertEquals("Even Even", resultingState.getName());
		assertTrue(resultingState.isAcceptState());
	}
}
