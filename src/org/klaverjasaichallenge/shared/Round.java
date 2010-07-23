//package org.klaverjasaichallenge;
//
//import java.util.List;
//import java.util.LinkedList;
//
//import org.klaverjasaichallenge.shared.Action;
//import org.klaverjasaichallenge.shared.Trick;
//
//public class Round {
//	private static final int AMOUNT_OF_TRICKS = 8;
//
//	private final List<Action> actions;
//	
//	private final Map<Player, Hand> hands;
//	private final List<Trick> tricksPlayed;
//
//	private final Suit trump;
//
//	public Round() {
//		this.actions = this.createActions();
//		this.tricksPlayed = new LinkedList<Trick>();
//	}
//
//	public void play() {
//		//for(Action action : actions) {
//		//	action.perform();
//		//}
//	}
//
//	private List<Action> createActions() {
//		List<Action> actions = new LinkedList<Action>();
//
//		// These are ideas for the actions in a round:
//		// Give all players their cards:
//		//actions.add(new CreateHandsAction());
//		//
//		// In this round the trump is drawn, players are allowed to choose
//		// trump. if all players refuse, a new trump is drawn.
//		// The ending state of this round is: a trump and a team that accepts
//		// it.
//		//actions.add(new DrawTrumpAction());
//		//
//		// After that eight tricks will be played.
//		//for(int i = 1; i <= AMOUNT_OF_TRICKS; i++) {
//		//	actions.add(new TrickAction());
//		//}
//		//
//		// A total score will be calculated to define the winner.
//		//actions.add(new RoundEndsAction());
//
//		return actions;
//	}
//
//}
