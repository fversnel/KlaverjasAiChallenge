package org.klaverjasaichallenge.engine.round.action;

import static org.mockito.Mockito.*;

import org.klaverjasaichallenge.ai.StupidButLegalAI;
import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.ruleset.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

/**
 *
 * @author Frank Versnel
 */
class SampleRoundData {
	private final Player playerOne;
	private final Player playerTwo;
	private final Player playerThree;
	private final Player playerFour;

	private final Team teamOne;
	private final Team teamTwo;

	private final Table table;
	private final RuleSet rotterdamRuleSet;

	public SampleRoundData() {
		this.playerOne = spy(new StupidButLegalAI());
		this.playerTwo = spy(new StupidButLegalAI());
		this.playerThree = spy(new StupidButLegalAI());
		this.playerFour = spy(new StupidButLegalAI());

		this.teamOne = new Team(getPlayerOne(), getPlayerThree());
		this.teamTwo = new Team(getPlayerTwo(), getPlayerFour());

		this.table = new Table(getTeamOne(), getTeamTwo());
		this.rotterdamRuleSet = new RotterdamRuleSet();
	}

	public Table getTable() {
		return this.table;
	}

	public Player getPlayerOne() {
		return this.playerOne;
	}

	public Player getPlayerTwo() {
		return this.playerTwo;
	}

	public Player getPlayerThree() {
		return this.playerThree;
	}

	public Player getPlayerFour() {
		return this.playerFour;
	}


	public Team getTeamOne() {
		return this.teamOne;
	}

	public Team getTeamTwo() {
		return this.teamTwo;
	}

	public RuleSet getRotterdamRuleSet() {
		return this.rotterdamRuleSet;
	}

}
