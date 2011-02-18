package org.klaverjasaichallenge.server.round.action.test;

import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.server.round.action.*;
import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.shared.card.suit.*;

public class TestAccumulateTrickScore {
	private SampleRoundData testData;
	private RoundData roundData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		this.roundData = this.testData.getRoundData();
		this.roundData.setTrump(this.testData.getPlayerOne(), new Hearts());

		new InformPlayersRuleSet(this.roundData).execute();
		new DealCards(this.roundData).execute();
		new PlayRound(this.roundData).execute();

		RoundAction accumulateScore = new AccumulateTrickScore(this.roundData);
		accumulateScore.execute();
	}

	@Test
	public void testScoreBothTeams() {
		Score scoreBothTeams = new Score();
		for(final Team team : this.roundData.getTeams()) {
			Score teamScore = this.roundData.getRoundScore(team);
			System.out.println(team + " " +teamScore);
			scoreBothTeams = Score.plus(scoreBothTeams, teamScore);
		}

		Points stockScoreBothTeams = scoreBothTeams.getStockScore();
		assertTrue(stockScoreBothTeams.equals(Score.MAXIMUM_STOCK_SCORE));
	}

}
