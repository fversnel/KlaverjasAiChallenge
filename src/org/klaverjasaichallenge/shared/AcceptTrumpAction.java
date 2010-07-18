/**
 * 
 */
package org.klaverjasaichallenge.shared;


/**
 * Use this action tell whether you accept or dont accept a certain trump
 */
public class AcceptTrumpAction implements Action {
	private final boolean trumpAccepted;

	public AcceptTrumpAction(boolean trumpAccepted) {
		this.trumpAccepted = trumpAccepted;
	}

	public boolean isTrumpAccepted() {
		return this.trumpAccepted;
	}

}
