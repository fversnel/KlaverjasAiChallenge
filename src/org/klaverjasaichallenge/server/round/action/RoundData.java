package org.klaverjasaichallenge.server.round.action;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.server.round.Hand;

class RoundData {
	private Table table;
	private RuleSet ruleSet;
	private Map<Player, Hand> playersHands;

	public RoundData(final Table table, final RuleSet ruleSet) {
		this.table = table;
		this.ruleSet = ruleSet;

		this.playersHands = new HashMap<Player, Hand>();
	}

	public Table getTable() {
		return this.table;
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

}
