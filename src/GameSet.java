import java.util.ArrayList;
import java.util.List;

public class GameSet {
    public static void main(String[] args) {
        Deck deck = new Deck(); // Create a Deck of 81 cards.
        deck.shuffleDeck();

        // TODO: Network interface, see how many players are in the game.
        int numPlayers = 0;
        List<Player> playersInGame = new ArrayList<Player>();

        for (int i=0; i < numPlayers; i++){
            playersInGame.add(new Player());
        }
    }

    // This method takes as an input a List<Card> and confirms whether or not it's a set
    public static boolean confirmCards(List<Card> cardTriplet) {
        // First check to be certain that three cards have been selected
        if (cardTriplet.size() != 3) {
            // TODO: Here it will print a message saying that an invalid selection has been made
            return false; // This ends the method without running the rest of it
        }

        // If we do in fact have exactly 3 cards, the below code is valid.
        // Retrieve first the 3 cards, then the features of each card as an Array.
        // See Card class for more info.

        Card firstCard = cardTriplet.get(0);
        Card secondCard = cardTriplet.get(1);
        Card thirdCard = cardTriplet.get(2);

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
