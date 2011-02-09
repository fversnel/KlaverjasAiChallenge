package org.klaverjasaichallenge.server.round.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.RotterdamRuleSet;

public class TestDealCards {
	private RoundData roundData;

	@Before
	public void setUp() {
		Team teamOne = new Team(mock(Player.class), mock(Player.class));
		Team teamTwo = new Team(mock(Player.class), mock(Player.class));

		Table table = new Table(teamOne, teamTwo);
		RuleSet rotterdamRuleSet = new RotterdamRuleSet();

		this.roundData = new RoundData(table, rotterdamRuleSet);
	}

	@Test
	public void testHandsExists() {
		DealCards dealCardsAction = new DealCards(this.roundData);
		dealCardsAction.execute();

		for(Player player : this.roundData.getPlayers()) {
			assertNotNull(this.roundData.getPlayersHand(player));
		}
	}

}
