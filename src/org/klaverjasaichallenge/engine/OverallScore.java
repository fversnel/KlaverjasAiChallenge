package org.klaverjasaichallenge.engine;

import java.util.ArrayList;
import java.util.List;

import org.klaverjasaichallenge.engine.round.data.RoundResult;
import org.klaverjasaichallenge.engine.score.Score;

public class OverallScore {
	private final List<RoundResult> roundResults = new ArrayList<RoundResult>();

	public void add(final RoundResult roundResult) {
		this.roundResults.add(roundResult);
	}
	
	public Score getScore(final Team team) {
		Score totalScore = new Score();
		
		for(RoundResult roundResult : this.roundResults) {
			totalScore = totalScore.plus(roundResult.getScore(team));
		}
		
		return totalScore;
	}
	
	public int getNumberOfPlays(final Team team) {
		int numberOfPlays = 0;
		
		for(RoundResult roundResult : this.roundResults) {
			if(roundResult.plays(team)) {
				numberOfPlays++;
			}
		}
		
		return numberOfPlays;
	}
	
	public int getNumberOfWets(final Team team) {
		int numberOfWets = 0;
		
		for(RoundResult roundResult : this.roundResults) {
			if(roundResult.isWet(team)) {
				numberOfWets++;
			}
		}
		
		return numberOfWets;
	}
	
	public int getNumberOfMarchings(final Team team) {
		int numberOfMarchings = 0;
		
		for(RoundResult roundResult : this.roundResults) {
			if(roundResult.isMarching(team)) {
				numberOfMarchings++;
			}
		}
		
		return numberOfMarchings;
	}
	
	@Override
	public String toString() {
		return "";
	}

}
