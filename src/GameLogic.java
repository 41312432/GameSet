import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    public static boolean noSetsOnBoard(java.util.List<GraphicCard> cardsInPlay) {
        //If there are no sets return true
        boolean containsNoSet = true;
        java.util.List<GraphicCard> cardSet;
        for (int i = cardsInPlay.size() - 1; i > -1; i--) {
            for (int j = cardsInPlay.size() - 1; j > -1; j--) {
                for (int k = cardsInPlay.size() - 1; k > -1; k--) {
                    //all cards should be different
                    if (i != k && i != j && k != j) {
                        cardSet = new ArrayList<GraphicCard>();
                        cardSet.add(cardsInPlay.get(i));
                        cardSet.add(cardsInPlay.get(j));
                        cardSet.add(cardsInPlay.get(k));
                        if (confirmCards(cardSet)) {
                            containsNoSet = false;
                        }
                    }
                }
            }
        }
        return containsNoSet;
    }

    // This method takes as an input a List<Card> and confirms whether or not it's a set
    public static boolean confirmCards(List<GraphicCard> cardTriplet) {
        // First check to be certain that three cards have been selected
        if (cardTriplet.size() != 3) {
            // TODO: Here it will print a message saying that an invalid selection has been made
            return false; // This ends the method without running the rest of it
        }

        // If we do in fact have exactly 3 cards, the below code is valid.
        // Retrieve first the 3 cards, then the features of each card as an Array.
        // See Card class for more info.

        Card firstCard = cardTriplet.get(0).getCard();
        Card secondCard = cardTriplet.get(1).getCard();
        Card thirdCard = cardTriplet.get(2).getCard();

        int[] firstFeat = firstCard.getFeatures();
        int[] secFeat = secondCard.getFeatures();
        int[] thirdFeat = thirdCard.getFeatures();

        boolean confirmation = true; // We will set this TRUE if it is a SET and FALSE otherwise.

        for (int feature = 0; feature < firstFeat.length; feature++) {
            // Case where all 3 features are equal to each other
            boolean allFeatEqual = firstFeat[feature] == secFeat[feature] && firstFeat[feature] == thirdFeat[feature];
            boolean allFeatUnequal = firstFeat[feature] != secFeat[feature] && firstFeat[feature] !=
                    thirdFeat[feature] && secFeat[feature] != thirdFeat[feature];

            if (!(allFeatEqual || allFeatUnequal)) {
                confirmation = false;
            }
        }
        return confirmation;
    }
}
