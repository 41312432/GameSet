import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Component;



import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class frameTest extends JFrame {
    private JButton submitButton;
    private static JTextArea textArea;
    private static JTextArea textArea2;
    private final Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static ArrayList<JLabel> correspArray = new ArrayList<JLabel>();
	private static ArrayList<Card> cardSet = new ArrayList<Card>();
	private static ArrayList<Card> Triplet = new ArrayList<Card>();
	private static int score1;
	private static String player1 = "player";
	private static int N;
	private static cardPanel cardPanel;
    
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
        DisplayScore score=new DisplayScore();
        cardPanel = new cardPanel();
    	ButtonPane buttonPane = new ButtonPane();
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
    public class cardPanel extends JPanel implements MouseListener {
    	public cardPanel(){
            //Number of cards: can change this to 12 or 15 etc. 
    		//TODO:continuously check if are sets on panel when panel is full
    		//if not, increment N by 3.
            N=12; 
            int rows=N/3;  
            int cols=3;
            int hgap=3;
            int vgap=3;
            setLayout(new GridLayout(rows,cols,hgap,vgap)); 
            //Place N cards from the deck and places them onto panel
            Deck deck = new Deck();
        	placeCards(deck,N);
        }
    	
        //Draws N cards and adds their images to the panel 
        public void placeCards(Deck deck, int N){
        	cardSet = new ArrayList<Card>();
            correspArray = new ArrayList<JLabel>();
	        Triplet = new ArrayList<Card>(3);
        	for (int i=0; i<N; i++){
        		//draws N cards from the deck
        		cardSet.add(deck.distributeCard());
        		//converts each card to its image name
        		String aCard = (cardSet.get(i)).getImageName();        	
        		//and adds each image to the panel
        		JLabel l = makeImage(aCard);
        		correspArray.add(l);
        		add(l);
        		l.addMouseListener(this);
        		addMouseListener(this);
        	}
        }
            
        //Adds image to panel along with image properties
        //Can add in other properties of image here 
        public JLabel makeImage(String name){
        	JLabel l = new JLabel(new ImageIcon(name), JLabel.CENTER);
        	l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        	//l.setFont(l.getFont().deriveFont(20f)); 
        	return l;
        }
    
        //check of clicked card has already been selected, if so, remove card from selection
        boolean isDuplicate(ArrayList Triplet, ArrayList cardSet, MouseEvent e, int i) {
	    		  for (int j = 0; j < Triplet.size(); j++){ 
	    			  if (Triplet.get(j) == cardSet.get(i)){
	    				  Triplet.remove(j);
	    				  //((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    				  return true; 
	    			  }
	    		  } return false;
        }
        
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		for (int i = 0; i < N; i++){
    		       if (correspArray.get(i) == e.getSource())
    		        {
    		    	   //card selected 
     		    	  ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
     		    	  //Outputs to chat area for testing
     		    	  textArea.append(cardSet.get(i)+"\n");
    		   
    		    	  //different cases 
     		          //always add card if nothing added yet
     		          if(Triplet.size()<1){
     		        	 Triplet.add(cardSet.get(i));
     		          }
     		          //if less than three cards have been selected, 
     		          //remove duplicate if exists, otherwise add card
     		          else if (Triplet.size()<3){
    		        	  if (isDuplicate(Triplet, cardSet,e,i)){
    		        		  //Triplet.remove(j);
    	    				  ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		        	  }
    		        	  else{
    		        	  Triplet.add(cardSet.get(i));
    		        	  }
    		          }
     		          //if three cards selected
     		          //if duplicate exists, remove duplicate
     		          //otherwise, remove the first card selected 
    		          else{
    		        	  if(isDuplicate(Triplet, cardSet,e,i)){
    		        		  ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		        	  }
    		        	  else{
    		        	  for (int k=0;k<N;k++){
    		        		  if(cardSet.get(k)==Triplet.get(0)){ 
    		        			  ((JComponent) correspArray.get(k)).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		        		  }
    		        		 }
    		        	  Triplet.remove(0);
    		        	  Triplet.add(cardSet.get(i));  
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
            score1=0;

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
    		textArea.append(Triplet+"\n");
    		if(Triplet.size()<3){
    			textArea.append("Select 3 Cards. \n");
    		}
    		else{
    			Boolean isSet = Player.confirmCards(Triplet);
    			textArea.append(Boolean.toString(isSet)+"\n");
    			if(isSet==true){
    				score1++;
    				textArea2.setText("Player: " +player1+"\n"+"Score:" +score1+ "\n");
    				//TODO: remove set of cards from play and replace with new cards 
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
            textArea2= new JTextArea();
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
    public class Chat extends JPanel{
        public Chat() {
    	textArea = new JTextArea(5, 30);
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
    	userInputField.addActionListener(new ActionListener(){
    	    public void actionPerformed(ActionEvent event){
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
