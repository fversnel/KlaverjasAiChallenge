package org.klaverjasaichallenge.engine.round.action;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class InformPlayersRoundStart implements RoundAction<Void> {
	
	private Table table;
	private TrumpPlayer trumpPlayer;

	public InformPlayersRoundStart(final Table table, final TrumpPlayer trumpPlayer) {
		this.table = table;
		this.trumpPlayer = trumpPlayer;
	}

	@Override
	public Void execute() {
		final Suit trump = this.trumpPlayer.getTrump();
		final int trumpPlayerId = this.trumpPlayer.hashCode();
		
		for(Player currentPlayer : this.table) {
			final Team playerTeam = this.table.getTeamFromPlayer(currentPlayer);
			final Team otherTeam = this.table.getOtherTeam(currentPlayer);

			final int currentPlayerId = currentPlayer.hashCode();
			final int teamMateId = playerTeam.getOtherPlayer(currentPlayer).hashCode();
			final int enemy1Id = otherTeam.getFirstPlayer().hashCode();
			final int enemy2Id = otherTeam.getSecondPlayer().hashCode();

			currentPlayer.notifyStartOfRound(trumpPlayerId, trump,
					currentPlayerId, teamMateId, enemy1Id, enemy2Id);
		}

		return null;
	}

}
