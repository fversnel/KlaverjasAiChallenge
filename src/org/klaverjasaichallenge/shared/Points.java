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
	
	public Points(final Points points) {
		this.points = points.getPoints();
	}

	public int getPoints() {
		return this.points;
	}

	public static Points plus(final Points leftHandSide, final Points rightHandSide) {
		return new Points(leftHandSide.getPoints() + rightHandSide.getPoints());
	}
	
	public static boolean biggerThan(final Points leftHandSide, final Points rightHandSide) {
		return leftHandSide.getPoints() > rightHandSide.getPoints() ? true : false;
	}
	
	public String toString() {
		return this.getPoints()+"";
	}

}
