package org.klaverjasaichallenge.engine.round.action;

/**
 * An arbitrary action in a round of Klaverjas.
 *
 * @author Frank Versnel
 */
public interface RoundAction<T> {

	/**
	 * Execute the action.
	 * 
	 * @return the result of the executed action
	 */
	public abstract T execute();

}
