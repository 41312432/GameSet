import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphicsGame extends JFrame {
    private JButton submitButton;
    private JTextArea chatBoxText;
    private JTextArea playerScores;
    private Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<GraphicCard> cardSet = new ArrayList<GraphicCard>();
    private ArrayList<GraphicCard> triplet = new ArrayList<GraphicCard>();
    private Player player = new Player("Mike");
    private final int N = 12;
    private Deck deck = new Deck();
    private CardPanel cardPanel;
    GridLayout gridLayout = new GridLayout(N / 3, 3, 5, 5);

    public GraphicsGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(PREFERRED_SIZE);
        setTitle("Game Set");

        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("images/gameSetBackground.jpg"));
        background.setPreferredSize(PREFERRED_SIZE);
        add(background);

        //Creating the components 
        DisplayScore score = new DisplayScore();
        cardPanel = new CardPanel();
        ButtonPane buttonPane = new ButtonPane();
        Chat chat = new Chat();

        //Placing components onto background
        background.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Insets(top,left,bottom,right)
        constraints.insets = new Insets(0, 50, 0, 50);
        constraints.gridx = 0;
        constraints.gridy = 0;
        score.setOpaque(false);
        background.add(score, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        cardPanel.setOpaque(false);
        background.add(cardPanel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        chat.setOpaque(false);
        background.add(chat, constraints);

        constraints.insets = new Insets(10, 0, 0, 0);
        constraints.gridx = 1;
        constraints.gridy = 1;
        buttonPane.setOpaque(false);
        background.add(buttonPane, constraints);

        pack();
        setVisible(true);
    }

    //Setting up the grid layout for the cards panel 
    public class CardPanel extends JPanel implements MouseListener {
        public CardPanel() {
            setLayout(gridLayout);

            placeCards(N);
            chatBoxText = new JTextArea(5, 30);
        }

        public void placeCards(int numCards) {
            for (int i = 0; i < numCards; i++) {
                Card card = deck.distributeCard();
                String cardImageName = card.getImageName();

                JLabel jLabel = makeImage(cardImageName);
                cardSet.add(new GraphicCard(card, jLabel));

                add(jLabel);
                jLabel.addMouseListener(this);
            }

            if (GameLogic.noSetsOnBoard(cardSet)) {
                gridLayout.setRows(gridLayout.getRows() + 1);
                placeCards(3);
            }
        }

        public void updateCard(GraphicCard graphicCard) {
            Card card = deck.distributeCard();
            String cardImageName = card.getImageName();

            JLabel jLabel = makeImage(cardImageName);
            int replaceIndex = cardSet.indexOf(graphicCard);
            cardSet.set(replaceIndex, new GraphicCard(card, jLabel));

            jLabel.addMouseListener(this);

        }

        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < cardSet.size(); i++) {
                // Iterate through all the cards
                GraphicCard selectedCard = cardSet.get(i);

                if (selectedCard.getJLabel() == e.getSource()) {
                    // Determine which card is selected one
                    if (triplet.contains(selectedCard)) {
                        // If the card is selected, un-select it
                        selectedCard.getJLabel().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                        selectedCard.getJLabel().setBackground(Color.WHITE);
                        triplet.remove(selectedCard);
                    } else if (triplet.size() < 3) {
                        selectedCard.getJLabel().setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                        selectedCard.getJLabel().setBackground(Color.YELLOW);
                        triplet.add(selectedCard);
                    } else {
                        // If 3 cards already selected, un-select first card and select the new card
                        for (GraphicCard graphicCard : cardSet) {
                            if (graphicCard.equals(triplet.get(0))) {
                                graphicCard.getJLabel().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                                graphicCard.getJLabel().setBackground(Color.WHITE);
                                triplet.remove(0);
                                break;
                            }
                        }

                        selectedCard.getJLabel().setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                        selectedCard.getJLabel().setBackground(Color.YELLOW);
                        triplet.add(selectedCard);
                    }

                    break;
                }
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }
    }

    public class ButtonPane extends JPanel implements MouseListener {
        public ButtonPane() {
            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            Dimension buttonDimension = new Dimension(120, 50);

            submitButton = new JButton("Submit Set");
            submitButton.setPreferredSize(buttonDimension);
            submitButton.setMnemonic(KeyEvent.VK_D);
            submitButton.addMouseListener(this);

            constraints.insets = new Insets(0, 5, 0, 5);
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(submitButton, constraints);
        }

        public void mouseClicked(MouseEvent e) {
            if (triplet.size() < 3) {
                chatBoxText.append("Select 3 Cards. \n");
            } else {
                if (GameLogic.confirmCards(triplet)) {
                    player.addPoint();
                    playerScores.setText("Player: " + player.getPlayerName() + "\n" + "Score:" + player.getPoints() + "\n");

                    boolean removeRow = true;

                    for (GraphicCard graphicsCard : triplet) {
                        if (cardSet.size() > N) {
                            cardSet.remove(graphicsCard);
                            if (removeRow) {
                                gridLayout.setRows(gridLayout.getRows() - 1);
                                removeRow = false;
                            }
                        } else {
                            if (deck.deckSize() != 0) {
                                cardPanel.updateCard(graphicsCard);
                            }
                        }
                    }

                    if (GameLogic.noSetsOnBoard(cardSet)) {
                        if (deck.deckSize() != 0) {
                            gridLayout.setRows(gridLayout.getRows() + 1);
                            cardPanel.placeCards(3);
                        } else {
                            finishGame();
                        }
                    }

                    cardPanel.removeAll();
                    for (GraphicCard updatedGraphic : cardSet) {
                        cardPanel.add(updatedGraphic.getJLabel());
                    }

                    triplet.clear();
                } else {
                    // If not set, un-select cards and deduct one point
                    for (GraphicCard graphicCard : triplet) {
                        graphicCard.getJLabel().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                        graphicCard.getJLabel().setBackground(Color.WHITE);
                    }
                    triplet.clear();

                    if (player.getPoints() > 0) {
                        player.subPoint();
                    }
                    playerScores.setText("Player: " + player.getPlayerName() + "\n" + "Score:" + player.getPoints() + "\n");
                }
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }
    }

    public class DisplayScore extends JPanel {
        public DisplayScore() {
            setLayout(new GridBagLayout());
            playerScores = new JTextArea();
            playerScores.setEditable(false);
            playerScores.setPreferredSize(new Dimension(200, 300));

            playerScores.append("Player: " + player.getPlayerName() + "\n");
            playerScores.append("Score: " + 0 + "\n");

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            add(playerScores, constraints);
        }
    }

    public class Chat extends JPanel {
        public Chat() {
            JScrollPane scrollPane = new JScrollPane(chatBoxText);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            chatBoxText.setLineWrap(true);
            chatBoxText.setWrapStyleWord(true);
            chatBoxText.setEditable(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            final JTextField userInputField = new JTextField(20);
            userInputField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String fromUser = userInputField.getText();
                    if (fromUser != null) {
                        chatBoxText.append("Player: " + fromUser + "\n");
                        chatBoxText.setCaretPosition(chatBoxText.getDocument().getLength());
                        userInputField.setText("");
                    }
                }
            });

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.fill = GridBagConstraints.HORIZONTAL;

            this.setLayout(new GridBagLayout());

            constraints.gridx = 0;
            constraints.gridy = 1;

            this.add(userInputField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 0;
            this.add(scrollPane, constraints);
        }
    }

    private JLabel makeImage(String name) {
        JLabel l = new JLabel(new ImageIcon(name), JLabel.CENTER);
        l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        return l;
    }

    public void finishGame() {

    }
}
