import javax.swing.*;
import java.io.Serializable;

public class GraphicCard implements Serializable {
    private Card card;
    private JLabel jLabel;

    public GraphicCard(Card card, JLabel jLabel) {
        this.card = card;
        this.jLabel = jLabel;
    }

    public Card getCard() {
        return card;
    }

    public JLabel getJLabel() {
        return jLabel;
    }
}
