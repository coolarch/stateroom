/**
 * 
 */
package cool.arch.stateroom;

/*
 * #%L cool.arch.stateroom:stateroom %% Copyright (C) 2015 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

import static cool.arch.stateroom.StateTest.Alphabet.A;
import static cool.arch.stateroom.StateTest.Alphabet.B;
import static cool.arch.stateroom.enums.Status.ACCEPTED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.junit.Test;

import cool.arch.stateroom.Context;
import cool.arch.stateroom.Machine;
import cool.arch.stateroom.State;

/**
 * 
 */
public class StateTest {

	@Test
	public final void test() {
		final Queue<Alphabet> input = new LinkedList<>();
		input.add(A);
		input.add(B);
		input.add(B);
		input.add(A);
		input.add(B);
		input.add(A);
		input.add(B);
		input.add(A);

		final State<Alphabet> evenEven = State.of("Even Even", true);
		final State<Alphabet> evenOdd = State.of("Even Odd");
		final State<Alphabet> oddEven = State.of("Odd Even");
		final State<Alphabet> oddOdd = State.of("Odd Odd");

		final Machine<Alphabet> machine = Machine.builder(Alphabet.class)
			.withModelSupplier(input::poll)
			.withStartState(evenEven)
			.withPreEvaluationTransform((s, m) -> input.poll())
			.haltWhen((state, model) -> model == null)
			.withState(evenEven)
			.to(oddEven, is(A))
			.to(evenOdd, is(B))
			.withState(oddEven)
			.to(evenEven, is(A))
			.to(oddOdd, is(B))
			.withState(evenOdd)
			.to(oddOdd, is(A))
			.to(evenEven, is(B))
			.withState(oddOdd)
			.to(evenOdd, is(A))
			.to(oddEven, is(B))
			.build();

		final Context<Alphabet> context = machine.create();
		final Context<Alphabet> newContext = machine.evaluateUntilHalted(context);
		final State<Alphabet> resultingState = newContext.getState();

		assertSame(evenEven, resultingState);
		assertEquals("Even Even", resultingState.getName());
		assertEquals(ACCEPTED, newContext.getStatus());
	}

	BiPredicate<State<Alphabet>, Alphabet> is(final Alphabet value) {
		return (state, model) -> value.equals(model);
	}

	public static enum Alphabet {
			A,
			B
	}
}
