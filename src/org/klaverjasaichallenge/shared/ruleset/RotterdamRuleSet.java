/**
 *
 */
package org.klaverjasaichallenge.shared.ruleset;

import java.util.List;

import org.klaverjasaichallenge.shared.ruleset.RuleSet;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class RotterdamRuleSet implements RuleSet {
	private static final int NUMBER_OF_ROUNDS = 16;
	
	 @Override
     public boolean isLegalCard(final Trick trick, final Card cardToCheck,
                     final Hand playerHand) {
             boolean result = true;

             final Suit cardSuit = cardToCheck.getSuit();
             final Suit trump = trick.getTrump();
             final Suit leadingSuit = trick.getLeadingSuit();
             if(leadingSuit == null) {
                     result = true;
             } else if(Card.hasSuit(leadingSuit, playerHand.getCards())) {
                     result = cardSuit.equals(leadingSuit);

                     // If the leading suit is trump we need to perform an extra
                     // check.
                     if(leadingSuit.equals(trump)) {
                             result = this.playsTrumpRight(trick, playerHand, cardToCheck);
                     }
             }
             // Player doesn't have the leading suit but might be able to play
             // trump. If he does have trump, he has to play it.
             else if(Card.hasSuit(trump, playerHand.getCards())) {
                     result = this.playsTrumpRight(trick, playerHand, cardToCheck);
             }

             return result;
     }

     @Override
     public int getNumberOfRounds() {
             return NUMBER_OF_ROUNDS;
     }

     private boolean playsTrumpRight(final Trick trick,
                     final Hand playerHand, final Card cardToCheck) {
         boolean result = true;
         final Suit trump = trick.getTrump();

         if(Card.hasSuit(trump, playerHand.getCards())) {
                 if(this.playerCanOverTrump(trick, playerHand)) {
                         result = cardToCheck.isHigherThan(trick,
                                         trick.getHighestCard());
                 } else {
                         final Suit cardSuit = cardToCheck.getSuit();
                         result = cardSuit.equals(trump);
                 }
         }

         return result;
     }

     /**
      * 
      * @param trick
      * @param playerHand
      * @return
      * @deprecated Use the canOverTrump method of Hand instead. However, this method still contains a bug, so fix that first.
      */
     @Deprecated
     private boolean playerCanOverTrump(final Trick trick,
                     final Hand playerHand) {
         boolean result = false;

         final Card highestTrumpTrick = trick.getHighestTrump();

         final Suit trump = trick.getTrump();
         final Card highestTrumpPlayer = Card.highestTrump(trump, playerHand.getCards());

         if(highestTrumpTrick != null && highestTrumpPlayer != null) {
                 result = highestTrumpPlayer.isHigherThan(trick,
                                 highestTrumpTrick);
         }

         return result;
     }

}

