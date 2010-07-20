
package org.klaverjasaichallenge.score;

import java.util.List;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;

class MarchScore {
	private static final Points MARCH = new Points(100);

	private static final int ALL_TRICKS = 8;

	public static Points calculateScore(final List<Trick> tricks) {
		Points score = new Points();

		final int amountTricks = tricks.size();
		if(amountTricks == ALL_TRICKS) {
			score = MARCH;
		}

		return score;
	}

}
