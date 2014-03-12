import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class frameTest extends JFrame {
    private JButton joinButton, leaveButton;
    private JTextField textField;
    private static JTextArea textArea;
    private final String JOIN = "join";
    private final Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private Player activePlayer;

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

        
      //create the grid layout for the cards panel 
        //Number of cards: can change this to 12 or 15 etc. 
        //Might need to implement differently, since only need to 3 add cards, 
        //not replace everything...but for now. 
        int N=15; 
        int rows=N/3;
        
        int cols=3;
        int hgap=3;
        int vgap=3;
        JPanel panel = new JPanel(new GridLayout(rows,cols,hgap,vgap)); 
        //Place N=12 cards from the deck and places them onto panel
        Deck deck = new Deck();
    	placeCards(deck,panel,N);
    	
    	GridBagConstraints constraints = new GridBagConstraints();
        
        ButtonPane buttonPane = new ButtonPane();
        TextPane textPane = new TextPane();

        background.setLayout(new GridBagLayout());

        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;

        buttonPane.setOpaque(false);
        background.add(buttonPane, constraints);

        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 2;

        textPane.setOpaque(false);
        background.add(textPane, constraints);

        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
    	background.add(panel, constraints);
        
        pack();
        setVisible(true);       
    }
    
  //Methods for the cards panel 
    //Draws N cards and adds their images to the panel 
    //(Cards are not click-able or anything yet)
    public static void placeCards(Deck deck, JPanel panel, int N){
    	ArrayList<Card> cardSet = new ArrayList<Card>();
    	for (int i=0; i<N; i++){
    		//draws N cards from the deck
    		cardSet.add(deck.distributeCard());
    	
    		//converts each card to its image name
    		String aCard = (cardSet.get(i)).getImageName();
    		//System.out.println(aCard+"");
    	
    		//and adds each image to the panel
    		JLabel l = makeImage(aCard);
    		panel.add(l);
    	}
    }
        
    //Adds image to panel along with image properties
    //Can add in other properties of image here 
    public static JLabel makeImage(String name){
    	JLabel l = new JLabel(new ImageIcon(name), JLabel.CENTER);
    	l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	//l.setFont(l.getFont().deriveFont(20f)); 
    	return l;
    }

    public class ButtonPane extends JPanel implements ActionListener {
        public ButtonPane() {
            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            Dimension buttonDimension = new Dimension(120, 50);

            // Parameters for the layout of the submitButton
            joinButton = new JButton("Submit Set");
            joinButton.setPreferredSize(buttonDimension);
            joinButton.setMnemonic(KeyEvent.VK_D);
            joinButton.setActionCommand(JOIN);
            joinButton.addActionListener(this);

            constraints.insets = new Insets(0, 5, 0, 5);
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(joinButton, constraints);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOIN.equals(e.getActionCommand())) {
                String text = textField.getText();
                if (text.trim().length() == 0) {
                    textField.setText("");
                } else {
                    activePlayer = new Player(text);
                    GameSet.client.playerLobby(activePlayer);
                    leaveButton.setEnabled(true);
                    joinButton.setEnabled(false);
                }
            } else {
                GameSet.client.leaveGame(activePlayer);
                leaveButton.setEnabled(false);
                joinButton.setEnabled(true);
            }
        }
    }

    public class TextPane extends JPanel implements ActionListener {

        public TextPane() {
            setLayout(new GridBagLayout());
            textField = new JTextField(20);
            textField.addActionListener(this);
            textField.setEditable(false);

            //Add Components to this panel.
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;

            constraints.fill = GridBagConstraints.HORIZONTAL;
            add(textField, constraints);
        }

        public void actionPerformed(ActionEvent evt) {
            String text = textField.getText();
            textField.selectAll();
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

}
