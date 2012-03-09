package org.klaverjasaichallenge.shared;

/**
 *
 * @author Frank Versnel
 */
public class Points {

	protected final int points;

	public Points(final int points) {
		this.points = points;
	}

	public static Points ZERO() {
		return new Points(0);
	}

	public int getPoints() {
		return this.points;
	}

	public Points plus(final Points rightHandSide) {
		return new Points(this.points + rightHandSide.points);
	}

	public boolean biggerThan(final Points rightHandSide) {
		return this.points > rightHandSide.points;
	}

	public boolean biggerThanOrEquals(final Points rightHandSide) {
		return this.points >= rightHandSide.points;
	}

	public boolean smallerThan(final Points rightHandSide) {
		return this.points < rightHandSide.points;
	}

	@Override
	public boolean equals(Object o) {
		Points points = (Points)o;
		return this.points == points.getPoints();
	}

	@Override
	public String toString() {
		return Integer.toString(this.points) + " points";
	}

}
