package org.klaverjasaichallenge.human;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.util.Console;

public class CommandLinePlayer implements Player {
	
	private Console console;
	
	private Hand cardsInHand;
	
	public CommandLinePlayer() {
		this.console = new Console(System.in, System.out);
	}

	@Override
	public void notifyRuleset(RuleSet ruleSet) {
		this.console.printf("We're playing %s today\n", ruleSet);
	}

	@Override
	public void receiveCards(Hand cards) {
		this.cardsInHand = cards;
		
		this.console.printf("You receive these %s cards\n", cards);
	}

	@Override
	public boolean playsOnTrump(Suit trump, int turn) {
		return Boolean.valueOf(console.readLine("Do you want to play on trump %s?", trump, turn));
	}

	@Override
	public void notifyStartOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1Id, int enemy2Id) {
		console.printf("playing on trump %s", trump);
	}

	@Override
	public Card playCard(Trick trick) {
		int cardIndex = Integer.valueOf(console.readLine("play a card from your hand: %s", cardsInHand));
		Card cardToPlay = cardsInHand.getCards().get(cardIndex);
		cardsInHand.drawCard(cardToPlay);
		return cardToPlay;
	}

	@Override
	public void notifyEndOfTrick(Trick trick) {
		// Do nothing
	}
	
	@Override
	public String toString() {
		return "Command-line hero (" + this.hashCode() + ")";
	}

}
