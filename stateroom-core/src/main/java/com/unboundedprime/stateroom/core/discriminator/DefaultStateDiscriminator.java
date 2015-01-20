package com.unboundedprime.stateroom.core.discriminator;

import com.unboundedprime.stateroom.core.interfaces.Context;
import com.unboundedprime.stateroom.core.interfaces.Model;
import com.unboundedprime.stateroom.core.interfaces.State;
import com.unboundedprime.stateroom.core.interfaces.StateDiscriminator;

/**
 * Default implementation of the StateDiscriminator interface.
 * <p>
 * This implementation simply proxies to the context to get the last defined state.
 * </p>
 * @param <M> Model in the machine context used to obtain information about the machine
 */
public class DefaultStateDiscriminator<M extends Model> implements StateDiscriminator<M> {
	@Override
	public final State<M> getCurrentState(final Context<M> context) {
		return context.getCurrentState();
	}
}
