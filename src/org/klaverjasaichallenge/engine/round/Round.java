package org.klaverjasaichallenge.engine.round;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.round.action.DealCards;
import org.klaverjasaichallenge.engine.round.action.DrawTrump;
import org.klaverjasaichallenge.engine.round.action.InformPlayersRoundStart;
import org.klaverjasaichallenge.engine.round.action.InformPlayersRuleSet;
import org.klaverjasaichallenge.engine.round.action.PlayRound;
import org.klaverjasaichallenge.engine.round.data.CardsDealt;
import org.klaverjasaichallenge.engine.round.data.PlayedTricks;
import org.klaverjasaichallenge.engine.round.data.RoundResult;
import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

public class Round {
	private final Table table;
	private final RuleSet ruleSet;

	public Round(final Table table, final RuleSet ruleSet) {
		this.table = table;
		this.ruleSet = ruleSet;
	}

	public RoundResult play() {
		new InformPlayersRuleSet(this.ruleSet, this.table).execute();
		
		final CardsDealt cardsDealt = new DealCards(this.table).execute();
		
		final TrumpPlayer trumpPlayer = new DrawTrump(this.table).execute();
		
		new InformPlayersRoundStart(this.table, trumpPlayer).execute();
		
		final PlayedTricks playedTricks = new PlayRound(this.ruleSet, this.table, trumpPlayer, cardsDealt).execute();
		
		return new RoundResult(this.table, trumpPlayer, playedTricks);
	}

}
