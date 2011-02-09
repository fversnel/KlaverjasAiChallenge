package org.klaverjasaichallenge.server.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.shared.Player;

public class TestDealCards {
	private RoundData roundData;

	@Before
	public void setUp() {
		Team teamOne = new Team(mock(Player.class), mock(Player.class));
		Team teamTwo = new Team(mock(Player.class), mock(Player.class));

		Table table = new Table(this.teamOne, this.teamTwo);
		RuleSet rotterdamRuleSet = new RotterdamRuleSet();

		this.roundData = new RoundData(table, rotterdamRuleSet)
	}

	@Test
	public void testCorrectCardAmount() {
		DealCards dealCardsAction = new DealCards(this.roundData);
		dealCardsAction.execute();

	}

}
