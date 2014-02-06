import java.util.ArrayList;
import java.util.List;
//jenifer was here
public class Player {
    int points = 0;
    String playerName;
    List<Card> cardsCalled = new ArrayList<Card>();

    Player(String playerName){
        this.playerName = playerName;
    }

    public void addPoint(){
        points += 1;
    }

    public void subPoint(){
        points -= 1;
    }

    public int getPoints(){

        return points;
    }

    public void addCard(Card card){
        cardsCalled.add(card);
    }

    public void removeCard(int index){
        cardsCalled.remove(index);
    }

    public void callSet(){
        boolean isSet = confirmCards(cardsCalled);
        if(isSet){
            addPoint();
        } else {
            subPoint();
            // TODO: Tell player why this is not a Set.
        }
    }

    // This method takes as an input a List<Card> and confirms whether or not it's a set
    public boolean confirmCards(List<Card> cardTriplet) {
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
