package org.klaverjasaichallenge.engine.round.action;

/**
 *
 * @author Frank Versnel
 */
public abstract class RoundAction {
	protected final RoundData roundData;

	public RoundAction(final RoundData roundData) {
		this.roundData = roundData;
	}

	public abstract RoundAction execute();

}
