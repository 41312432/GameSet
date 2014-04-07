import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class frameTest extends JFrame {
    private JButton submitButton;
    private JTextArea textArea;
    private JTextArea textArea2;
    private Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<GraphicCard> cardSet = new ArrayList<GraphicCard>();
    private ArrayList<Card> triplet = new ArrayList<Card>();
    private int score1;
    private String player1 = "player";
    private int N = 3;
    private Deck deck = new Deck();
    private CardPanel cardPanel;
    GridLayout gridLayout = new GridLayout(N / 3, 3, 5, 5);

    public frameTest() {
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
            //Place N cards from the deck and places them onto panel
            //deck = new Deck();

            placeCards(N);
            textArea = new JTextArea(5, 30);
            //textArea.append(deck.getCard(0) + "\n");
        }

        public void placeCards(int numCards) {
            for (int i = 0; i < numCards; i++) {
                //draws M cards from the deck
                Card card = deck.distributeCard();

                //converts each card to its image name
                String aCard = card.getImageName();
                //and adds each image to the panel
                JLabel jLabel = makeImage(aCard);
                cardSet.add(new GraphicCard(card, jLabel));

                add(jLabel);
                jLabel.addMouseListener(this);
                addMouseListener(this);
            }

            if (GameSet.noSetsOnBoard(cardSet)) {
                gridLayout.setRows(gridLayout.getRows() + 1);
                placeCards(3);
            }

        }

        public void removeCards(int i) {
            cardPanel.remove((Component) cardSet.get(i).getJLabel());
            //cardSet.remove(i);
            //cardPanel.placeCards(deck,1);
        }

        //Adds image to panel along with image properties, can add in other properties of image here
        public JLabel makeImage(String name) {
            JLabel l = new JLabel(new ImageIcon(name), JLabel.CENTER);
            l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            l.setBorder(BorderFactory.createLineBorder(Color.white));
            return l;
        }

        //check of clicked card has already been selected, if so, remove card from selection
        boolean isDuplicate(ArrayList cardSet, MouseEvent e, int i) {
            for (int j = 0; j < triplet.size(); j++) {
                if (triplet.get(j) == cardSet.get(i)) {
                    triplet.remove(j);
                    //((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    return true;
                }
            }
            return false;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < N; i++) {
                if (cardSet.get(i).getJLabel() == e.getSource()) {
                    //card selected
                    ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.blue));
                    //different cases
                    //always add card if nothing added yet
                    if (triplet.size() < 1) {
                        triplet.add(cardSet.get(i).getCard());
                    }
                    // if less than three cards have been selected,
                    // remove duplicate if exists, otherwise add card
                    else if (triplet.size() < 3) {
                        if (isDuplicate(cardSet, e, i)) {
                            //triplet.remove(j);
                            ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                            ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.white));
                        } else {
                            triplet.add(cardSet.get(i).getCard());
                        }
                    }
                    //if three cards selected
                    //if duplicate exists, remove duplicate
                    //otherwise, remove the first card selected
                    else {
                        if (isDuplicate(cardSet, e, i)) {
                            ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                            ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.white));
                        } else {
                            for (int k = 0; k < N; k++) {
                                if (cardSet.get(k).getCard() == triplet.get(0)) {
                                    ((JComponent) cardSet.get(k).getJLabel()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                                    ((JComponent) cardSet.get(k).getJLabel()).setBorder(BorderFactory.createLineBorder(Color.white));
                                }
                            }
                            triplet.remove(0);
                            triplet.add(cardSet.get(i).getCard());
                        }
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
    }

    //submit button
    public class ButtonPane extends JPanel implements MouseListener {
        public ButtonPane() {
            setLayout(new GridBagLayout());
            score1 = 0;

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

        @Override
        public void mouseClicked(MouseEvent e) {
            if (triplet.size() < 3) {
                textArea.append("Select 3 Cards. \n");
            } else {
                Boolean isSet = Player.confirmCards(triplet);
                textArea.append(Boolean.toString(isSet) + "\n");
                if (isSet == true) {
                    score1++;
                    textArea2.setText("Player: " + player1 + "\n" + "Score:" + score1 + "\n");

                    for (GraphicCard graphicCard : cardSet) {
                        if (triplet.contains(graphicCard.getCard())) {
                            cardPanel.remove(graphicCard.getJLabel());
                            triplet.remove(graphicCard.getCard());
                        }
                    }

                    cardPanel.placeCards(3);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
    }

    //text area that displays users and scores
    //have to modify when we actually add users and scores 
    public class DisplayScore extends JPanel {
        public DisplayScore() {

            setLayout(new GridBagLayout());
            textArea2 = new JTextArea();
            textArea2.setEditable(false);
            textArea2.setPreferredSize(new Dimension(200, 300));

            textArea2.append("Player: " + player1 + "\n");
            textArea2.append("Score: " + 0 + "\n");

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            add(textArea2, constraints);
        }
    }

    //chat box 
    public class Chat extends JPanel {
        public Chat() {
            // textArea = new JTextArea(5, 30);
            // We put the TextArea object in a Scrollable Pane
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // Text Field for user input
            // text area stays on the last line as subsequent lines are
            // added and auto-scrolls
            final JTextField userInputField = new JTextField(20);
            userInputField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String fromUser = userInputField.getText();
                    if (fromUser != null) {
                        textArea.append("Player: " + fromUser + "\n");
                        //The pane auto-scrolls with each new response added
                        textArea.setCaretPosition(textArea.getDocument().getLength());
                        //We reset our text field to "" each time the user presses Enter
                        userInputField.setText("");
                    }
                }
            });

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.fill = GridBagConstraints.HORIZONTAL;

            this.setLayout(new GridBagLayout());
            //adds and centers the text field to the frame
            constraints.gridx = 0;
            constraints.gridy = 1;
            this.add(userInputField, constraints);
            //adds and centers the scroll pane to the frame
            constraints.gridx = 0;
            constraints.gridy = 0;
            this.add(scrollPane, constraints);
        }
    }
}
