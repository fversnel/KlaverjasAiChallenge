
package org.klaverjasaichallenge.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.Table;
import org.klaverjasaichallenge.Team;
import org.klaverjasaichallenge.shared.Player;

public class TestTable {
	private Player playerOne;
	private Player playerTwo;
	private Player playerThree;
	private Player playerFour;

	private Team teamOne;
	private Team teamTwo;

	private Table table;

	@Before
	public void setUp() {
		this.playerOne = mock(Player.class);
		this.playerTwo = mock(Player.class);
		this.playerThree = mock(Player.class);
		this.playerFour = mock(Player.class);

		this.teamOne = new Team(this.playerOne, this.playerTwo);
		this.teamTwo = new Team(this.playerThree, this.playerFour);

		this.table = new Table(this.teamOne, this.teamTwo);
	}

	@Test
	public void testInitialActivePlayer() {
		Player expected = this.playerOne;
		Player actual = this.table.getActivePlayer();

		assertEquals(expected, actual);
	}

	@Test
	public void testNextPlayer() {
		Player expected = this.playerThree;
		Player actual = this.table.nextPlayer().getActivePlayer();

		assertEquals(expected, actual);
	}

	@Test
	public void testNextTrick() {
		Player expected = this.playerOne;
		Player actual = this.table.nextTrick(expected).getActivePlayer();

		assertEquals(expected, actual);
	}

	@Test
	public void testNextPlayerAfterNextTrick() {
		Player winner = this.playerFour;

		Player expected = this.playerOne;
		Player actual = this.table.nextTrick(winner).nextPlayer().getActivePlayer();

		assertEquals(expected, actual);
	}

}
