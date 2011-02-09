package org.klaverjasaichallenge.server.round.action;

import static org.junit.Assert.*;

import org.junit.*;

public class TestDrawTrump {
	private RoundData testData;
	
	private RoundAction drawTrump;
	
	@Before
	public void setUp() {
		this.testData = new SampleRoundData().getRoundData();
		
		RoundAction drawTrump = new DrawTrump(testData);
		drawTrump.execute();
	}
	
	@Test
	public void testIfTrumpExists() {
		assertNotNull(this.testData.getTrump());
	}
	
	@Test
	public void testIfTrumpPlayerExists() {
		assertNotNull(this.testData.getTrumpPlayer());
	}

}
