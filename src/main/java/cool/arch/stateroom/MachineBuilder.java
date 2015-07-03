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

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public interface MachineBuilder<M> extends AbstractBuilder<Machine<M>> {

	StateBuilderFacade<M> withState(State<M> state);

	MachineBuilder<M> withModelSupplier(Supplier<M> modelSupplier);

	MachineBuilder<M> withStartState(State<M> startState);

	MachineBuilder<M> haltWhen(BiPredicate<State<M>, M> predicate);

	MachineBuilder<M> withPreEvaluationTransform(BiFunction<State<M>, M, M> preEvaluationTransform);

}
