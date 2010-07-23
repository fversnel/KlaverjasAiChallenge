package org.klaverjasaichallenge;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.PersonalGameState;

public class FakePlayer implements Player {

	public FakePlayer() {
	}

	public Action respond(PersonalGameState personalGameState) {
		return null;
	}

}
