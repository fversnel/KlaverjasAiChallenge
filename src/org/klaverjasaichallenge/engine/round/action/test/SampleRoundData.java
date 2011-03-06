package org.klaverjasaichallenge.engine.round.action.test;

import static org.mockito.Mockito.*;

import org.klaverjasaichallenge.ai.StupidButLegalAI;
import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.ruleset.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

/**
 *
 * @author Frank Versnel
 */
class SampleRoundData {
	private Player playerOne;
	private Player playerTwo;
	private Player playerThree;
	private Player playerFour;

	private Team teamOne;
	private Team teamTwo;

	private RoundData roundData;

	public SampleRoundData() {
		this.playerOne = spy(new StupidButLegalAI());
		this.playerTwo = spy(new StupidButLegalAI());
		this.playerThree = spy(new StupidButLegalAI());
		this.playerFour = spy(new StupidButLegalAI());

		this.teamOne = new Team(getPlayerOne(), getPlayerThree());
		this.teamTwo = new Team(getPlayerTwo(), getPlayerFour());

		Table table = new Table(getTeamOne(), getTeamTwo());
		RuleSet rotterdamRuleSet = new RotterdamRuleSet();

		this.roundData = new RoundData(table, rotterdamRuleSet);
	}

	public Table getTable() {
		return this.roundData.getTable();
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerOne() {
		return this.playerOne;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public Player getPlayerTwo() {
		return this.playerTwo;
	}

	public void setPlayerThree(Player playerThree) {
		this.playerThree = playerThree;
	}

	public Player getPlayerThree() {
		return this.playerThree;
	}

	public void setPlayerFour(Player playerFour) {
		this.playerFour = playerFour;
	}

	public Player getPlayerFour() {
		return this.playerFour;
	}

	public void setTeamOne(Team teamOne) {
		this.teamOne = teamOne;
	}

	public Team getTeamOne() {
		return this.teamOne;
	}

	public void setTeamTwo(Team teamTwo) {
		this.teamTwo = teamTwo;
	}

	public Team getTeamTwo() {
		return this.teamTwo;
	}

	public RoundData getRoundData() {
		return this.roundData;
	}

}
