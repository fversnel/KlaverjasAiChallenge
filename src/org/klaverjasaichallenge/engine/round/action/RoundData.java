package org.klaverjasaichallenge.engine.round.action;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class RoundData {
	private Map<Player, Hand> playersHands = new HashMap<Player, Hand>();
	private List<EngineTrick> tricksPlayed = new LinkedList<EngineTrick>();
	
	private Map<Team, Score> roundScores = new HashMap<Team, Score>();
	
	private Table table;
	private final RuleSet ruleSet;
	
	private Suit trump;
	private Player trumpPlayer;

	public RoundData(final Table table, final RuleSet ruleSet) {
		this.table = table;
		this.ruleSet = ruleSet;
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

	public void savePlayersHand(final Player player, final Hand playersHand) {
		this.playersHands.put(player, playersHand);
	}

	public List<EngineTrick> getTricksPlayed() {
		return new LinkedList<EngineTrick>(this.tricksPlayed);
	}

	public void addPlayedTrick(final EngineTrick playedTrick) {
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

}
