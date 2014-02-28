import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;


public class frameTest {

	//private static JPanel panel;
    public static void main(String[] args) {
        final JFrame f = new JFrame("Frame Test");

        JPanel panel = new JPanel(new GridLayout(4, 4, 3, 3));

        //for (int i = 0; i < 12; i++) {
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
        JLabel l1 = makeImage("images/01_Green_Diamond_Empty.gif");
        panel.add(l1);
        
        JLabel l2 = makeImage("images/02_Green_Diamond_Empty.gif");
        panel.add(l2);

        JLabel l3 = makeImage("images/03_Green_Diamond_Empty.gif");
        panel.add(l3);
        
        JLabel l4 = makeImage("images/01_Green_Squiggle_Solid.gif");
        panel.add(l4);
        
        JLabel l5 = makeImage("images/01_Green_Squiggle_Solid.gif");
        panel.add(l5);
        
        //Frame properties 
        f.setContentPane(panel);
        f.setSize(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }


public static JLabel makeImage(String name){
    JLabel l3 = new JLabel(new ImageIcon(name), JLabel.CENTER);
    l3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    l3.setFont(l3.getFont().deriveFont(20f));
    return l3;
}
}