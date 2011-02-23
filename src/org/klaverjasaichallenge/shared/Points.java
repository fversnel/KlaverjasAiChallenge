package org.klaverjasaichallenge.shared;

/**
 *
 * @author Frank Versnel
 */
public class Points {
	private static final int DEFAULT_POINTS = 0;

	private int points;

	public Points() {
		this.points = DEFAULT_POINTS;
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

	public static Points sub(final Points leftHandSide, final Points rightHandSide) {
		return new Points(leftHandSide.getPoints() - rightHandSide.getPoints());
	}

	public static Points divide(final Points leftHandSide, final Points rightHandSide) {
		return new Points(leftHandSide.getPoints() / rightHandSide.getPoints());
	}

	public static boolean biggerThan(final Points leftHandSide, final Points rightHandSide) {
		return leftHandSide.getPoints() > rightHandSide.getPoints() ? true : false;
	}

	public static boolean biggerThanOrEquals(final Points leftHandSide, final Points rightHandSide) {
		return leftHandSide.getPoints() >= rightHandSide.getPoints() ? true : false;
	}

	public boolean equals(Points points) {
		return this.points == points.getPoints();
	}

	@Override
	public String toString() {
		return Integer.toString(this.getPoints()) + " points";
	}

}
