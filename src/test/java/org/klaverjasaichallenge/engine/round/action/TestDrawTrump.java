package org.klaverjasaichallenge.engine.round.action;

import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;

/**
 *
 * @author Frank Versnel
 */
public class TestDrawTrump {
	private SampleRoundData testData;

	private TrumpPlayer trumpPlayer;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		this.trumpPlayer = new DrawTrump(this.testData.getTable()).execute();
	}

	@Test
	public void testIfTrumpExists() {
		assertNotNull(this.trumpPlayer.getTrump());
	}

	@Test
	public void testIfTrumpPlayerExists() {
		assertNotNull(this.trumpPlayer.getPlayer());
	}

}
