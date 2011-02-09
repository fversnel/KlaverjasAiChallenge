package org.klaverjasaichallenge.server.round.action;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.RuleSet;

class SampleRoundData {
	private Player playerOne;
	private Player playerTwo;
	private Player playerThree;
	private Player playerFour;

	private Team teamOne;
	private Team teamTwo;

	private RoundData roundData;

	public SampleRoundData() {
		this.playerOne = mock(Player.class);
		this.playerTwo = mock(Player.class);
		this.playerThree = mock(Player.class);
		this.playerFour = mock(Player.class);

		this.teamOne = new Team(getPlayerOne(), getPlayerThree());
		this.teamTwo = new Team(getPlayerTwo(), getPlayerFour());

		Table table = new Table(getTeamOne(), getTeamTwo());
		RuleSet rotterdamRuleSet = new RotterdamRuleSet();

		this.roundData = new RoundData(table, rotterdamRuleSet);
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
	
	public List<Player> getPlayers() {
		return this.roundData.getPlayers();
	}

	public RoundData getRoundData() {
		return this.roundData;
	}

}
