import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class GraphicsGame extends JPanel {
    private JButton submitButton;
    private static JTextArea chatBoxText;
    private JTextArea playerScores;
    private Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<GraphicCard> cardSet = new ArrayList<GraphicCard>();
    private ArrayList<GraphicCard> triplet = new ArrayList<GraphicCard>();
    private Player clientPlayer;
    public static int N = 12; //Default number of cards in play
    private Client client;
    private Deck deck;
    private CardPanel cardPanel;
    private GridLayout gridLayout = new GridLayout(N / 3, 3, 5, 5);

    public GraphicsGame(final Client client, Player clientPlayer, Deck deck) {
        this.client = client;
        this.clientPlayer = clientPlayer;
        this.deck = deck;

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

        public void updateCard(Integer location) {
            Card card = deck.distributeCard();
            String cardImageName = card.getImageName();

            JLabel jLabel = makeImage(cardImageName);
            cardSet.set(location, new GraphicCard(card, jLabel));

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
                        selectedCard.getJLabel().setBackground(Color.BLUE);
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
                        selectedCard.getJLabel().setBackground(Color.BLUE);
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
                chatBoxText.append("Select 3 Cards! \n");
            } else {
                if (GameLogic.confirmCards(triplet)) {
                    // A Client submits a correct Set. This is local to one machine. See below methods for what all Clients get soon after
                    ArrayList<Integer> thisTriplet = new ArrayList<Integer>();

                    for (int i = 0; i < cardSet.size(); i++) {
                        if (triplet.contains(cardSet.get(i))) {
                            thisTriplet.add(i);
                        }
                    }

                    client.submitSet(thisTriplet, clientPlayer);
                    triplet.clear();
                } else {
                    // Similar as above except bad Set
                    for (GraphicCard graphicCard : triplet) {
                        graphicCard.getJLabel().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                        graphicCard.getJLabel().setBackground(Color.WHITE);
                    }
                    triplet.clear();

                    client.submitError(clientPlayer);
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

    public void wrongSet(Player player) {
        // This is what all Clients call whenever a bad Set is called
    	if (player.getPoints()<0){
        player.subPoint();
    	}

        playerScores.setText("");

        for (Player players : client.playersInGame) {
            playerScores.append(players.getPlayerName() + "\t Score: " + players.getPoints() + "\n\n");
        }
    }

    public void correctSetUpdate(ArrayList<Integer> submittedTriplet, Player player) {
        // This is what all Clients call whenever a good Set is called by any player
        boolean removeRow = true;

        Collections.sort(submittedTriplet, Collections.reverseOrder());

        player.addPoint();
        playerScores.setText("");

        cardPanel.removeAll();

        for (Player players : client.playersInGame) {
            playerScores.append(players.getPlayerName() + "\t Score: " + players.getPoints() + "\n\n");
        }

        for (int cardLocation : submittedTriplet) {
            if (cardSet.size() > N) {
                cardSet.remove(cardLocation);
                if (removeRow) {
                    System.out.println("Got here");
                    gridLayout.setRows(gridLayout.getRows() - 1);
                    removeRow = false;
                }
            } else {
                if (deck.deckSize() != 0) {
                    cardPanel.updateCard(cardLocation);
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

        for (GraphicCard card: cardSet) {
            cardPanel.add(card.getJLabel());
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public class DisplayScore extends JPanel {
        public DisplayScore() {
            setLayout(new GridBagLayout());
            playerScores = new JTextArea();
            playerScores.setEditable(false);
            playerScores.setPreferredSize(new Dimension(200, 300));

            for (Player player : client.playersInGame) {
                playerScores.append(player.getPlayerName() + "\t Score: " + 0 + "\n\n");
            }

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
                        client.sendMessage(fromUser, clientPlayer);
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

    public void updateTextArea(String message, Player player) {
        chatBoxText.append(player.getPlayerName() + ": " + message + "\n");
        chatBoxText.setCaretPosition(chatBoxText.getDocument().getLength());
    }

    private JLabel makeImage(String name) {
        JLabel l = new JLabel(new ImageIcon(name), JLabel.CENTER);
        l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        return l;
    }

    public ArrayList<GraphicCard> getCardSet() {
        return cardSet;
    }

    public CardPanel getCardPanel() {
        return cardPanel;
    }

    public GridLayout getGridLayout() {
        return gridLayout;
    }

    public JTextArea getPlayerScores() {
        return playerScores;
    }

    public static void finishGame() {
    	chatBoxText.append("Game Completed");
    }
}
