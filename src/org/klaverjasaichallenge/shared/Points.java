package org.klaverjasaichallenge.shared;

public class Points {
	private static final int DEFAULT_AMOUNT_POINTS = 0;

	private int points;

	public Points() {
		this.points = DEFAULT_AMOUNT_POINTS;
	}
	
	public Points(final int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return this.points;
	}

	public static Points plus(final Points leftHandSide, final Points rightHandSide) {
		return new Points(leftHandSide.getPoints() + rightHandSide.getPoints());
	}
}
