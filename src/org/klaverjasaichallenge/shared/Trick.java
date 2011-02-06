package org.klaverjasaichallenge.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Trick {
	org.klaverjasaichallenge.server.Trick serverSideTrick;
	
	public Trick(org.klaverjasaichallenge.server.Trick serverSideTrick) {
		this.serverSideTrick = serverSideTrick;
	}
	
	public List<Card> getCards() {
		return this.serverSideTrick.getCards();
	}
	
	public Suit getTrump() {
		return this.serverSideTrick.getTrump();
	}
	
	public Suit getLeadingSuit() {
		return this.serverSideTrick.getLeadingSuit();
	}

	public Map<Card, Integer> getCardsWithPlayers() {
		final Map<Card, Player> serverSideCards = this.serverSideTrick.getCardsWithPlayers();
		
		final Map<Card, Integer> clientSideCards = new HashMap<Card, Integer>();
		for(final Card card : serverSideCards.keySet()) {
			clientSideCards.put(card, serverSideCards.get(card).hashCode());
		}

		return clientSideCards;
	}
	
	public Rank getHighestTrump() {
		return this.serverSideTrick.getHighestTrump();
	}

}
