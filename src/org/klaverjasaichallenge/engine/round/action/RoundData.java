package org.klaverjasaichallenge.engine.round.action;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.round.Hand;
import org.klaverjasaichallenge.engine.round.Trick;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class RoundData {
	private Table table;
	private final RuleSet ruleSet;
	private Map<Player, Hand> playersHands;
	private Suit trump;
	private Player trumpPlayer;
	private List<Trick> tricksPlayed;
	private Map<Team, Score> roundScores;

	public RoundData(final Table table, final RuleSet ruleSet) {
		this.table = table;
		this.ruleSet = ruleSet;

		this.playersHands = new HashMap<Player, Hand>();
		this.tricksPlayed = new LinkedList<Trick>();
		this.roundScores = this.initializeRoundScores(table.getTeams());
	}

	public Table getTable() {
		return this.table;
	}

	public List<Team> getTeams() {
		return this.table.getTeams();
	}

	public Suit getTrump() {
		return this.trump;
	}

	public void setTrump(final Player trumpPlayer, final Suit trump) {
		this.trumpPlayer = trumpPlayer;
		this.trump = trump;
	}

	public Player getTrumpPlayer() {
		return this.trumpPlayer;
	}

	public RuleSet getRuleSet() {
		return this.ruleSet;
	}

	public Hand getPlayersHand(final Player player) {
		return this.playersHands.get(player);
	}

	public void putPlayersHand(final Player player, final Hand playersHand) {
		this.playersHands.put(player, playersHand);
	}

	public List<Trick> getTricksPlayed() {
		return new LinkedList<Trick>(this.tricksPlayed);
	}

	public void addPlayedTrick(final Trick playedTrick) {
		this.tricksPlayed.add(playedTrick);
	}

	public void addRoundScore(final Team team, final Score teamScore) {
		this.roundScores.put(team, teamScore);
	}

	public Score getRoundScore(final Team team) {
		return this.roundScores.get(team);
	}

	public Map<Team, Score> getRoundScores() {
		return this.roundScores;
	}

	private Map<Team, Score> initializeRoundScores(final List<Team> teams) {
		final Map<Team, Score> roundScores = new HashMap<Team, Score>();
		for (final Team team : teams) {
			roundScores.put(team, new Score());
		}

		return roundScores;
	}

}
