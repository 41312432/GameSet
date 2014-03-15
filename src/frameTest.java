import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
        
        //Setting up the grid layout for the cards panel 
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
        Deck deck = new Deck(); //might have to change this
        
        //Creating the components 
        DisplayScore score=new DisplayScore();
    	placeCards(deck,panel,N);
        ButtonPane buttonPane = new ButtonPane();
        TextPane textPane = new TextPane();
        Chat chat=new Chat();

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
        panel.setOpaque(false);
    	background.add(panel, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 0;
        chat.setOpaque(false);
    	background.add(chat, constraints);
    	
        constraints.insets = new Insets(10, 0, 0, 0);
        constraints.gridx = 1;
        constraints.gridy = 1;
        buttonPane.setOpaque(false);
        background.add(buttonPane, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        textPane.setOpaque(false);
        background.add(textPane, constraints);
        
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
        
    //submit button
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

    //text pane - for testing, would display text upon button click 
    public class TextPane extends JPanel implements ActionListener {

        public TextPane() {
            setLayout(new GridBagLayout());
            textField = new JTextField(20);
            textField.addActionListener(this);
            textField.setEditable(false);

            //Add Component to this panel
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
    
    //text area that displays users and scores
    //have to modify when we actually add users and scores 
    public class DisplayScore extends JPanel {
        public DisplayScore() {
        	
            setLayout(new GridBagLayout());
            JTextArea textArea= new JTextArea();
            textArea.setEditable(false);
        	textArea.setPreferredSize(new Dimension(200, 300));
            
            String player1 = "Player";
            int score1 = 1000000000;
            
		    textArea.append("Player: " + player1 + "\n");
		    textArea.append("Score: " + score1 + "\n");  
		    
            //Add Component to this panel.
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            add(textArea, constraints);
        }
    }
    
    //chat box 
    public class Chat extends JPanel{
        public Chat() {
    	// We create a TextArea object
    	final JTextArea textArea = new JTextArea(5, 30);
    	// We put the TextArea object in a Scrollable Pane
    	JScrollPane scrollPane = new JScrollPane(textArea);

    	// In order to ensure the scroll Pane object appears in your window, 
    	// set a preferred size to it!
    	scrollPane.setPreferredSize(new Dimension(200, 300));

    	// Lines will be wrapped if they are too long to fit within the 
    	// allocated width
    	textArea.setLineWrap(true);

    	// Lines will be wrapped at word boundaries (whitespace) if they are 
    	// too long to fit within the allocated width
    	textArea.setWrapStyleWord(true);

    	// Assuming this is the chat client's window where we read text sent out 
    	// and received, we don't want our Text Area to be editable!
    	textArea.setEditable(false);

    	// We also want a vertical scroll bar on our pane, as text is added to it
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    	// Now let's just add a Text Field for user input, and make sure our 
    	// text area stays on the last line as subsequent lines are 
    	// added and auto-scrolls
    	final JTextField userInputField = new JTextField(20);
    	userInputField.addActionListener(new ActionListener(){
    	    public void actionPerformed(ActionEvent event){
    		//We get the text from the textfield
    		String fromUser = userInputField.getText();

    		if (fromUser != null) {
    		    //We append the text from the user
    		    textArea.append("User: " + fromUser + "\n");

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
