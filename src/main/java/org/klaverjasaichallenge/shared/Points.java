package org.klaverjasaichallenge.shared;

/**
 *
 * @author Frank Versnel
 */
public class Points {
	protected static final int DEFAULT = 0;

	protected int points;

	public Points() {
		this.points = DEFAULT;
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

	public static Points subtract(final Points leftHandSide, final Points rightHandSide) {
		return new Points(leftHandSide.getPoints() - rightHandSide.getPoints());
	}

	public static Points divide(final Points leftHandSide, final Points rightHandSide) {
		return new Points(leftHandSide.getPoints() / rightHandSide.getPoints());
	}

	public static boolean biggerThan(final Points leftHandSide, final Points rightHandSide) {
		return leftHandSide.getPoints() > rightHandSide.getPoints();
	}

	public static boolean biggerThanOrEquals(final Points leftHandSide, final Points rightHandSide) {
		return leftHandSide.getPoints() >= rightHandSide.getPoints();
	}

	public static boolean smallerThan(final Points leftHandSide, final Points rightHandSide) {
		return leftHandSide.getPoints() < rightHandSide.getPoints();
	}

	@Override
	public boolean equals(Object o) {
		Points points = (Points)o;
		return this.points == points.getPoints();
	}

	@Override
	public String toString() {
		return Integer.toString(this.getPoints()) + " points";
	}

}
