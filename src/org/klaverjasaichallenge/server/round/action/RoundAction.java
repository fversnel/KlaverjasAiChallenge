package org.klaverjasaichallenge.server.round.action;

import org.klaverjasaichallenge.server.round.RoundResult;

abstract class RoundAction {
	protected final RoundData roundData;

	public RoundAction(final RoundData roundData) {
		this.roundData = roundData;
	}

	/**
	 * TODO Loop through all the actions like this:
	 * while(actionToPerform != null) {
	 * 		actionToPerform = actionToPerform.execute();
	 * }
	 */
	public abstract RoundAction execute();

}
