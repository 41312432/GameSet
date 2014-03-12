import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.ArrayList; 

public class GraphicsGame{
	private static Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static String Submit = "submit";
    private JTextField textField;
    private static JTextArea textArea;
    
	public static void main(String[] args) {
        final JFrame f = new JFrame("Graphics Game");
        
        //content contains all the panels to be added to JFrame
        JPanel content = new JPanel(new GridBagLayout()); 
        GridBagConstraints constraints = new GridBagConstraints();

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
    	
    	//create a submit button
    	JPanel submitButton = new JPanel();
        submitButton.setOpaque(false);
        JButton submit = new JButton("Submit Set");
        
        submit = new JButton("Submit Set");
        submit.setMnemonic(KeyEvent.VK_D);
        //submit.setActionCommand(JOIN);
        //submit.addActionListener(this);
        
        
        
        submitButton.add(submit, constraints);
        
        //create a textbox
        JPanel textBox = new JPanel();
        JTextField textField= new JTextField(20);
        //textField.addActionListener(this);
        textField.setEditable(false);

        //Add Components to this panel.
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        textBox.add(textField, constraints);

    	//adding panels to various locations in content panel
        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
    	content.add(panel, constraints);
    
        constraints.gridy = 1;
    	content.add(submitButton,constraints);

        constraints.gridy = 2;
    	content.add(textBox,constraints);
        
        //Overall JFrame properties 
        //f.setContentPane(panel);
        f.getContentPane().add(content);
        f.setSize(PREFERRED_SIZE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
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
}
