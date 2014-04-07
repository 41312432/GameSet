import javax.swing.*;

public class GraphicCard {
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

    public void setGraphicCard(Card card, JLabel jLabel) {
        this.card = card;
        this.jLabel = jLabel;
    }
}
