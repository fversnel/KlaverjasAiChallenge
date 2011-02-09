package org.klaverjasaichallenge.server.round;

import java.util.Map;

import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.server.score.Score;

public class RoundResult {
	// TODO Create a nice interface for this map.
	private Map<Team, Score> teamScores;

	public RoundResult(final Map<Team, Score> teamScores) {
		this.teamScores = teamScores;
	}

}
