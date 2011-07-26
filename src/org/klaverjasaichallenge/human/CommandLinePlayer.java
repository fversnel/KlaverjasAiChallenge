package org.klaverjasaichallenge.human;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.util.Console;

public class CommandLinePlayer implements Player {
	
	private Console console = new Console(System.in, System.out);
	
	private Hand cardsInHand;
	
	private int trickCount = 1;

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
		String trumpAcceptancePrompt = String.format("Do you want to play on trump %s in turn %d?", trump, turn);
		return this.console.yesNoPrompt(trumpAcceptancePrompt);
	}

	@Override
	public void notifyStartOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1Id, int enemy2Id) {
		this.console.printf("playing on trump %s\n", trump);
	}

	@Override
	public Card playCard(Trick trick) {
		this.console.printf("Cards played before you in trick %d: %s", this.trickCount, trick);
		int cardIndex = Integer.valueOf(console.readLine("play a card from your hand:\n %s\n", cardsInHand));
		Card cardToPlay = cardsInHand.getCards().get(cardIndex);
		cardsInHand.drawCard(cardToPlay);
		return cardToPlay;
	}

	@Override
	public void notifyEndOfTrick(Trick trick) {
		this.console.printf("The following trick was played: %s", trick);
		this.trickCount++;
	}
	
	@Override
	public String toString() {
		return "Command-line hero (" + this.hashCode() + ")";
	}

}
