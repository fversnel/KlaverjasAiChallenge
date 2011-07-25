package org.klaverjasaichallenge.engine.round.data;

import java.util.HashMap;
import java.util.Map;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;

/**
 * 
 * @author Frank Versnel
 */
public class CardsDealt {
	private final Map<Player, Hand> handsDealt = new HashMap<Player, Hand>();

	public void put(final Player player, final Hand playersHand) {
		this.handsDealt.put(player, playersHand);
	}
	
	public Hand get(final Player player) {
		return this.handsDealt.get(player);
	}
	
}
