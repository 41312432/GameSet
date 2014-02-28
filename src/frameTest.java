import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;


public class frameTest {

    public static void main(String[] args) {
        final JFrame f = new JFrame("Frame Test");

        JPanel panel = new JPanel(new GridLayout(4, 4, 3, 3));

        //for (int i = 0; i < 12; i++) {
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            JLabel l = new JLabel(new ImageIcon("images/01_Green_Diamond_Empty.gif"), JLabel.CENTER);
            l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            l.setFont(l.getFont().deriveFont(20f));
            panel.add(l);
        
        JLabel l2 = new JLabel(new ImageIcon("images/02_Green_Diamond_Empty.gif"), JLabel.CENTER);
        l2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        l2.setFont(l2.getFont().deriveFont(20f));
        panel.add(l2);

        JLabel l3 = new JLabel(new ImageIcon("images/03_Green_Diamond_Empty.gif"), JLabel.CENTER);
        l3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        l3.setFont(l3.getFont().deriveFont(20f));
        panel.add(l3);
        
        f.setContentPane(panel);
        f.setSize(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}