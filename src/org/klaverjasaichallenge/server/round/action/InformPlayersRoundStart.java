package org.klaverjasaichallenge.server.round.action;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.shared.Player;

public class InformPlayersRoundStart extends RoundAction {

	public InformPlayersRoundStart(final RoundData roundData) {
		super(roundData);
	}

	@Override
	public RoundAction execute() {
		final int trumpPlayerId = this.roundData.getTrumpPlayer().hashCode();
		Table roundTable = this.roundData.getTable();
		for(Player currentPlayer : roundTable) {
			final Team playerTeam = roundTable.getTeamFromPlayer(currentPlayer);
			final Team otherTeam = roundTable.getOtherTeam(currentPlayer);
			final int currentPlayerId = currentPlayer.hashCode();
			final int teamMateId = playerTeam.getOtherPlayer(currentPlayer).hashCode();
			final int enemy1Id = otherTeam.getFirstPlayer().hashCode();
			final int enemy2Id = otherTeam.getSecondPlayer().hashCode();

			currentPlayer.startOfRound(trumpPlayerId, this.roundData.getTrump(),
					currentPlayerId, teamMateId, enemy1Id, enemy2Id);
		}

		return new PlayRound(this.roundData);
	}

}
