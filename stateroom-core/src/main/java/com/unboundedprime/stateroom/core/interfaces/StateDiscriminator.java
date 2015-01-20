package com.unboundedprime.stateroom.core.interfaces;

/**
 * Discriminates the current state based on interrogation of the model.
 * @param <M> Model contained in the context
 */
public interface StateDiscriminator<M extends Model> {
	
	/**
	 * Gets the current state of the machine based on the model.
	 * @param context Machine context from which to evaluate the current context
	 * @return Determined state of the context
	 */
	State<M> getCurrentState(Context<M> context);
}
