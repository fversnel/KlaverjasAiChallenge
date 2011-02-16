package org.klaverjasaichallenge.server.round.action.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.server.round.action.*;
import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.server.Team;

public class TestAccumulateScore {
	private SampleRoundData testData;
	private RoundData roundData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		this.roundData = this.testData.getRoundData();
		this.roundData.setTrump(mock(Suit.class));
		this.roundData.setTrumpPlayer(this.testData.getPlayerOne());

		new InformPlayersRuleSet(roundData).execute();
		new DealCards(roundData).execute();
		new PlayRound(roundData).execute();

		RoundAction accumulateScore = new AccumulateScore(roundData);
		accumulateScore.execute();
	}

	@Test
	public void testScoreBothTeams() {
		Score scoreBothTeams = new Score();
		for(final Team team : this.roundData.getTeams()) {
			Score teamScore = this.roundData.getRoundScore(team);
			scoreBothTeams = Score.plus(scoreBothTeams, teamScore);
		}

		Points stockScoreBothTeams = scoreBothTeams.getStockScore();
		assertTrue(stockScoreBothTeams.equals(Score.MAXIMUM_STOCK_SCORE));
	}

}
