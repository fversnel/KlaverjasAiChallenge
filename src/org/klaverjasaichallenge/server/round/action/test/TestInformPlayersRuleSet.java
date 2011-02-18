package org.klaverjasaichallenge.server.round.action.test;

import org.junit.*;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.server.round.action.*;

import static org.mockito.Mockito.*;

public class TestInformPlayersRuleSet {
	private SampleRoundData testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();
	}

	/**
	 * Verify that on each player the method {@link Player#giveRuleSet(RuleSet)} 
	 * is called.
	 */
	@Test
	public void verifyGiveRuleSet() {
		RoundData roundData = this.testData.getRoundData();

		RoundAction informPlayersRuleSet = new InformPlayersRuleSet(roundData);
		informPlayersRuleSet.execute();

		for(Player player : this.testData.getTable()) {
			verify(player).giveRuleSet(any(RuleSet.class));
		}
	}

}
