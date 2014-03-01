import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.util.ArrayList; 

//Actually, this frameTest is modified enough to be the GraphicsGame
//(Copied and pasted it over)
public class frameTest {
	private static Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
    public static void main(String[] args) {
        final JFrame f = new JFrame("Frame Test");
        
        //create the grid layout
        //for now the grid takes up the whole window - will need to change
        int rows=4;
        int cols=3;
        int hgap=3;
        int vgap=3;
        JPanel panel = new JPanel(new GridLayout(rows,cols,hgap,vgap)); 
        
        //Place N=12 cards from the deck and places them onto panel
        Deck deck = new Deck();
    	int N=12;
    	placeCards(deck,panel,N);
    	
        //JFrame properties 
        f.setContentPane(panel);
        //f.setSize(200, 200);
        f.setSize(PREFERRED_SIZE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
 
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