package org.klaverjasaichallenge.server.round.action;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.round.Hand;
import org.klaverjasaichallenge.server.round.Trick;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

class RoundData {
	private Table table;
	private RuleSet ruleSet;
	private Map<Player, Hand> playersHands;
	private Suit trump;
	private Player trumpPlayer;
	private List<Trick> tricksPlayed;

	public RoundData(final Table table, final RuleSet ruleSet) {
		this.table = table;
		this.ruleSet = ruleSet;

		this.playersHands = new HashMap<Player, Hand>();
		this.tricksPlayed = new LinkedList<Trick>();
	}

	public Table getTable() {
		return this.table;
	}

	public void nextPlayer() {
		this.table = this.table.nextPlayer();
	}

	public Player getActivePlayer() {
		return this.table.getActivePlayer();
	}

	public Suit getTrump() {
		return this.trump;
	}

	public void setTrump(final Suit trump) {
		this.trump = trump;
	}

	public void setTrumpPlayer(final Player trumpPlayer) {
		this.trumpPlayer = trumpPlayer;
	}

	public Player getTrumpPlayer() {
		return this.trumpPlayer;
	}

	public RuleSet getRuleSet() {
		return this.ruleSet;
	}

	public List<Player> getPlayers() {
		return this.table.getPlayers();
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

}