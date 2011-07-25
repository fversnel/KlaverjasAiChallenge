package org.klaverjasaichallenge.engine.round.action.test;

import org.junit.*;
import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

import static org.mockito.Mockito.*;

/**
 *
 * @author Frank Versnel
 */
public class TestInformPlayersRuleSet {
	private SampleRoundData testData;
	
	@Before
	public void setUp() {
		this.testData = new SampleRoundData();
		
		new InformPlayersRuleSet(this.testData.getRotterdamRuleSet(), this.testData.getTable()).execute();
	}
	/**
	 * Verify that on each player the method {@link Player#notifyRuleset(RuleSet)}
	 * is called.
	 */
	@Test
	public void verifyGiveRuleSet() {
		for(Player player : this.testData.getTable()) {
			verify(player).notifyRuleset(any(RuleSet.class));
		}
	}

}
