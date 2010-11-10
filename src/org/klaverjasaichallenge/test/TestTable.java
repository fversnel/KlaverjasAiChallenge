
package org.klaverjasaichallenge.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.Table;
import org.klaverjasaichallenge.Team;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.FakePlayer;

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
		this.playerOne = new FakePlayer();
		this.playerTwo = new FakePlayer();
		this.playerThree = new FakePlayer();
		this.playerFour = new FakePlayer();

		this.teamOne = new Team(playerOne, playerTwo);
		this.teamTwo = new Team(playerThree, playerFour);

		this.table = new Table(teamOne, teamTwo);
	}

	@Test
	public void testInitialActivePlayer() {
		Player expected = playerOne;
		Player actual = table.getActivePlayer();

		assertEquals(expected, actual);
	}

	@Test
	public void testNextPlayer() {
		Player expected = playerThree;
		Player actual = table.nextPlayer().getActivePlayer();

		assertEquals(expected, actual);
	}

	@Test
	public void testNextTrick() {
		Player expected = playerOne;
		Player actual = this.table.nextTrick(expected).getActivePlayer();

		assertEquals(expected, actual);
	}

	@Test
	public void testNextPlayerAfterNextTrick() {
		Player winner = playerFour;

		Player expected = playerOne;
		Player actual = this.table.nextTrick(winner).nextPlayer().getActivePlayer();

		assertEquals(expected, actual);
	}

}
