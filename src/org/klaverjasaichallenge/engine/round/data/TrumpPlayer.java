package org.klaverjasaichallenge.engine.round.data;

import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 * 
 * @author Frank Versnel
 */
public class TrumpPlayer {
	private Player trumpPlayer;
	private Suit trump;
	
	public TrumpPlayer(final Player trumpPlayer, final Suit trump) {
		this.trumpPlayer = trumpPlayer;
		this.trump = trump;
	}
	
	public Player getPlayer() {
		return this.trumpPlayer;
	}
	
	public Suit getTrump() {
		return this.trump;
	}
	
	public boolean isOfTeam(final Team team) {
		return team.hasPlayer(this.trumpPlayer);
	}

}
