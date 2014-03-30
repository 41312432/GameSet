import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    /* Deck Object: represents a collection of all the cards necessary to play the game. */

    final int COLORS = 3, SHAPES = 3, SHADES = 3, QUANTITIES = 3;

    static List<Card> cardsInDeck = new ArrayList<Card>();

    public Deck() {
        // Populate the Deck.
        for (int color = 0; color < COLORS; color++) {
            for (int shape = 0; shape < SHAPES; shape++) {
                for (int shade = 0; shade < SHADES; shade++) {
                    for (int quantity = 0; quantity < QUANTITIES; quantity++) {
                        cardsInDeck.add(new Card(color, shape, shade, quantity));
                    }
                }
            }
        }
        shuffleDeck();
    }

    // This method retrieves a particular card in the deck
    public Card getCard(int index) {
        return cardsInDeck.get(index);
    }

    public int deckSize() {
        return cardsInDeck.size();
    }

    public void shuffleDeck() {
        Random random = new Random();
        for (int i = cardsInDeck.size() - 1; i > 0; i--){
            int index = random.nextInt(i+1);
            Card placeholder = cardsInDeck.get(index);
            cardsInDeck.set(index, cardsInDeck.get(i));
            cardsInDeck.set(i, placeholder);
        }
    }

    // Returns a Card and removes that Card from the deck.
    public Card distributeCard(){
        Card drawnCard = cardsInDeck.get(0);
        cardsInDeck.remove(0);
        return drawnCard;
    }
    
    public void removeCard(Card card){
    	cardsInDeck.remove(card);
    }
}
