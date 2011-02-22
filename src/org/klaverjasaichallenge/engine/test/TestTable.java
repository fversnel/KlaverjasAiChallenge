package org.klaverjasaichallenge.engine.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
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

	/**
	 * TODO Implement tests for the Table class.
	 */
	@Test
	public void testTodo() {
		assertEquals(true, true);
	}

}
