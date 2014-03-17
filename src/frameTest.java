import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
	private static ArrayList<String> correspArrayImgName = new ArrayList<String>();
	private static ArrayList<Card> Triplet = new ArrayList<Card>();
	private static int score1;
	private static String player1 = "player";
	private static int N;
    
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
        cardPanel cardPanel = new cardPanel();
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
    //Number of cards: can change this to 12 or 15 etc. 
    //Might need to implement differently, since only need to 3 add cards, 
    //not replace everything...but for now. 
    public class cardPanel extends JPanel implements MouseListener {
    	public cardPanel(){
            //create the grid layout for the cards panel 
            //Number of cards: can change this to 12 or 15 etc. 
            //Might need to implement differently, since only need to 3 add cards, 
            //not replace everything...but for now. 
            N=15; 
            int rows=N/3;
            
            int cols=3;
            int hgap=3;
            int vgap=3;
            setLayout(new GridLayout(rows,cols,hgap,vgap)); 
            //Place N=12 cards from the deck and places them onto panel
            Deck deck = new Deck();
        	placeCards(deck,N);
        }
    	
    	//Methods for the cards panel 
        //Draws N cards and adds their images to the panel 
        //(Cards are not click-able or anything yet)
        public void placeCards(Deck deck, int N){
        	cardSet = new ArrayList<Card>();
            correspArray = new ArrayList<JLabel>();
            correspArrayImgName = new ArrayList<String>();
	        Triplet = new ArrayList<Card>(3);
        	for (int i=0; i<N; i++){
        		//draws N cards from the deck
        		cardSet.add(deck.distributeCard());
        	
        		//converts each card to its image name
        		String aCard = (cardSet.get(i)).getImageName();
        		//System.out.println(aCard+"");
        	
        		//and adds each image to the panel
        		JLabel l = makeImage(aCard);
        		correspArray.add(l);
        		correspArrayImgName.add(aCard);
        		add(l);
        		String name = Integer.toString(i);
        		l.setName(name);
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

        //Taken from the example. Might be useful later.
        /*void eventOutput(String eventDescription, MouseEvent e) {
            textArea.append(eventDescription + " detected on "
                    + e.getComponent().getClass().getName()
                    + "." + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }*/
        
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		for (int i = 0; i < N; i++){
    		       if (correspArray.get(i) == e.getSource())
    		        {
    		    	  //currently outputs to chat area for testing
    		          //textArea.append("Location " + i+"\n");
    		    	   ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    		          textArea.append(cardSet.get(i)+"\n");
    		          //textArea.append("Name " + correspArrayImgName.get(i)+"\n");
    		          
    		          //add selected cards to Triplet arraylist
    		          //don't want duplicate cards
    		    	  /*for (int j = 0; j < 3; j++){
    		    		  if (Triplet.get(j) == cardSet.get(i)){
    		    			  Triplet.remove(j);
    		    		  }
    		    	  } */
    		          //This doesn't work when I put it here ^
    		          
    		    	  //if (Triplet.size()<3){
    		          if (Triplet.size()<4){
    		        	  Triplet.add(cardSet.get(i));
    		        	  //textArea.append(Triplet+"\n");
    		          }
    		          else{
    		        	  Triplet.remove(0);
    		        	  //Somehow access the JLabel of element 0 here...so we can unhighlight it 
    		        	  //This will also be a problem when we try to remove cards 
    		        	  for (int k=0;k<N;k++){
    		        		  if(cardSet.get(k)==Triplet.get(0)){
    		        			  ((JComponent) correspArray.get(k)).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		        		  }
    		        		 }
    		        	  
    		        	  Triplet.add(cardSet.get(i));
    		        	  //textArea.append(Triplet+"\n");
    		          }
    		          //For some reason, this doesn't work when I put it before the if statements
    		          //So I have to check for duplicates after I add the card: hence, Triplet.size()-1
    		          for(int j=0; j<Triplet.size()-1; j++){
    		        	  if(Triplet.get(j) == cardSet.get(i)){ 
    		        		  Triplet.remove(j);
    		        		  Triplet.remove(Triplet.size()-1);//if you click a card again, you want to remove it
    		        		  ((JComponent) e.getSource()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		        		  //textArea.append(Triplet+"\n");
    		        	  }
    		          }
    		    }	
    		}
    	if (Triplet.size()==4){
    		Triplet.remove(0);
    	}
    	//textArea.append(Triplet+"\n");
    	//Also, have to actually take last three elements of Triplet, because there might be 4 elements.
        //I wouldn't have to do this if I could put the for statement in the beginning, but that doesn't work.
    	//I don't know why this happens, but this isn't too big of a hassle, so I'm just going to leave it. 
    	}

    	//Still need these here, even though we're not doing anything with these.
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

            // Parameters for the layout of the submitButton
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
    			if(isSet==false){
    				//String str1 = "Player: "+player1+"/n";
    				//String str = "Score: "+score1+"/n";
    				score1++;
    				//textArea.remove();
    				textArea2.setText("Player: " +player1+"\n"+"Score:" +score1+ "\n");
    			    //textArea2.replaceRange("Score:" + score1, str1.length()+1,str1.length()+str.length());
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
		    
            //Add Component to this panel.
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            add(textArea2, constraints);
        }
    }
    
    //chat box 
    public class Chat extends JPanel{
        public Chat() {
    	// We create a TextArea object
    	textArea = new JTextArea(5, 30);
    	// We put the TextArea object in a Scrollable Pane
    	JScrollPane scrollPane = new JScrollPane(textArea);

    	// In order to ensure the scroll Pane object appears in your window, 
    	// set a preferred size to it!
    	scrollPane.setPreferredSize(new Dimension(500, 300));

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
