import java.util.ArrayList;
import java.util.List;

public class GameSet {

    public static Client client = new Client();

    public static void main(String[] args) {
        //GraphicsLobby graphics = new GraphicsLobby();

        //just doing some testing
        frameTest plswrk = new frameTest();
    	//frameTest2 plswrk = new frameTest2();
        //GraphicsGame plswrk2 = new GraphicsGame();     
    }

    public static boolean noSetsOnBoard(List<GraphicCard> cardsInPlay) {
        //If there are no sets return true
        boolean containsNoSet = true;
        List<GraphicCard> cardSet;
        for (int i = cardsInPlay.size() - 1; i > -1; i--) {
            for (int j = cardsInPlay.size() - 1; j > -1; j--) {
                for (int k = cardsInPlay.size() - 1; k > -1; k--) {
                    //all cards should be different
                    if (i != k && i != j && k != j) {
                        cardSet = new ArrayList<GraphicCard>();
                        cardSet.add(cardsInPlay.get(i));
                        cardSet.add(cardsInPlay.get(j));
                        cardSet.add(cardsInPlay.get(k));
                        if (Player.confirmCards(cardSet)) {
                            containsNoSet = false;
                        }
                    }
                }
            }
        }
        return containsNoSet;
    }

    public static boolean extraCards(List<Card> cardsInPlay) {
        if (cardsInPlay.size() > 12) {
            return true;
        } else {
            return false;
        }
    }
}