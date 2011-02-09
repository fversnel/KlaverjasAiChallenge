package org.klaverjasaichallenge.server.round.action;

import static org.junit.Assert.*;
import org.junit.*;

import org.klaverjasaichallenge.shared.Player;

public class TestDealCards {
	private SampleRoundData testData;
	
	@Before
	public void setUp() {
		this.testData = new SampleRoundData();
	}
	
	@Test
	public void testHandsExists() {
		RoundData roundData = this.testData.getRoundData();
		
		RoundAction dealCardsAction = new DealCards(roundData);
		dealCardsAction.execute();

		for(Player player : roundData.getPlayers()) {
			assertNotNull(roundData.getPlayersHand(player));
		}
	}

}
