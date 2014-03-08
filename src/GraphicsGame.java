import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.ArrayList; 

public class GraphicsGame{
	private static Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
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
        
    	//creating a filler panel - will replace with submit cards button and such 
    	JPanel filler = new JPanel();
    	JLabel plsfill = new JLabel(new ImageIcon("images/01_Purple_Squiggle_Striped.gif"), JLabel.CENTER);
    	filler.add(plsfill);
    	
    	//adding panels to various locations in content panel
        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
    	content.add(panel, constraints);
    	
        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;
    	content.add(filler,constraints);
        
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
    //This part was just used for testing (keeping it just in case) 
    //String aCard = (cardSet.get(0)).getImageName();
    //System.out.println(aCard+"");
    //JLabel l1=makeImage(aCard);
    //panel.add(l1);
    
    
    //Adds image to panel along with image properties
    //Can add in other properties of image here 
    public static JLabel makeImage(String name){
    	JLabel l = new JLabel(new ImageIcon(name), JLabel.CENTER);
    	l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	//l.setFont(l.getFont().deriveFont(20f)); 
    	return l;
    }
}
