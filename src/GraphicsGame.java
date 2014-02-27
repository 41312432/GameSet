import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GraphicsGame extends JFrame {

    private final Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private Player activePlayer;

    public GraphicsGame() {
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
        JLabel background = new JLabel(new ImageIcon(""));
        background.setPreferredSize(PREFERRED_SIZE);
        add(background);

        //ImagePanel panel1 = new ImagePanel(new ImageIcon("images/01_Green_DIamond_Empty.gif").getImage());
        ImagePanel panel2 = new ImagePanel(new ImageIcon("images/02_Green_DIamond_Empty.gif").getImage());
        //background.add(panel1);
        background.add(panel2);
        
        pack();
        setVisible(true);
    }

  
/*import java.awt.Dimension;  
import java.awt.Graphics;  
import java.awt.Image;  
  
import javax.swing.ImageIcon;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
  

public class GraphicsGame extends JFrame {
	
	Image imageTest = new ImageIcon("images/01_Green_DIamond_Empty.gif").getImage();

   public static void main(String[] args) {  
    ImagePanel panel1 = new ImagePanel(new ImageIcon("images/01_Green_DIamond_Empty.gif").getImage());  
    ImagePanel panel2 = new ImagePanel(new ImageIcon("images/01_Green_DIamond_Solid.gif").getImage());  
    JFrame frame = new JFrame();  
    //frame.getContentPane().add(panel1);  
    //frame.getContentPane().add(panel2);  
    frame.pack();  
    frame.setVisible(true);  
  }  
}  
  */
  
class ImagePanel extends JPanel {  
  
  private Image img;  
  
  public ImagePanel(String img) {  
    this(new ImageIcon(img).getImage());  
  }  
  
  public ImagePanel(Image img) {  
    this.img = img;  
    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));  
    setPreferredSize(size);  
    setMinimumSize(size);  
    setMaximumSize(size);  
    setSize(size);  
    setLayout(null);  
  }  
  
  public void paintComponent(Graphics g) {  
    g.drawImage(img, 0, 0, null);  
  }  
  
}
}