import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GraphicsLobby extends JFrame {
    private JButton joinButton, leaveButton;
    private JTextField textField;
    private static JTextArea textArea;
    private final String JOIN = "join";
    private final Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private Player activePlayer;
    private Client client = new Client();

    public GraphicsLobby() {
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

        ButtonPane buttonPane = new ButtonPane();
        TextPane textPane = new TextPane();

        background.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;

        buttonPane.setOpaque(false);
        background.add(buttonPane, constraints);

        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 2;
        constraints.gridy = 0;

        textPane.setOpaque(false);
        background.add(textPane, constraints);

        pack();
        setVisible(true);
    }

    public class ButtonPane extends JPanel implements ActionListener {
        public ButtonPane() {
            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            Dimension buttonDimension = new Dimension(120, 50);

            // Parameters for the layout of the joinButton

            joinButton = new JButton("Join Game");
            joinButton.setPreferredSize(buttonDimension);
            joinButton.setMnemonic(KeyEvent.VK_D);
            joinButton.setActionCommand(JOIN);
            joinButton.addActionListener(this);

            constraints.insets = new Insets(0, 5, 0, 5);
            constraints.gridx = 0;
            constraints.gridy = 0;

            add(joinButton, constraints);

            // Parameters for the layout of the leaveButton

            leaveButton = new JButton("Leave Game");
            leaveButton.setPreferredSize(buttonDimension);
            leaveButton.setMnemonic(KeyEvent.VK_M);
            leaveButton.addActionListener(this);
            leaveButton.setEnabled(false);

            constraints.insets = new Insets(0, 0, 0, 0);
            constraints.gridx = 1;
            constraints.gridy = 0;

            add(leaveButton, constraints);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOIN.equals(e.getActionCommand())) {
                String text = textField.getText();
                if (text.trim().length() == 0) {
                    textField.setText("");
                } else {
                    activePlayer = new Player(text);
                    if (client.playerLobby(activePlayer)) {
                        textArea.append(text + "\n");
                        leaveButton.setEnabled(true);
                        joinButton.setEnabled(false);
                    }
                    textField.setText("");
                    // TODO: The textArea needs to get updated for everyone as new Players join
                }
            } else {
                leaveButton.setEnabled(false);
                joinButton.setEnabled(true);
                GlobalVariables.gamePlayers.remove(activePlayer);
                textArea.setText("");
                for (Player player : GlobalVariables.gamePlayers) {
                    textArea.append(player.getPlayerName());
                }
                // TODO: Leave game button
            }
        }
    }

    public class TextPane extends JPanel implements ActionListener {

        public TextPane() {
            setLayout(new GridBagLayout());

            textField = new JTextField(20);
            textField.addActionListener(this);

            textArea = new JTextArea(5, 20);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            //Add Components to this panel.
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;

            constraints.fill = GridBagConstraints.HORIZONTAL;
            add(textField, constraints);

            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            add(scrollPane, constraints);
        }

        public void actionPerformed(ActionEvent evt) {
            String text = textField.getText();
            textField.selectAll();
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    public static void updateLobby() {
        textArea.setText("");
        for (Player player : GlobalVariables.gamePlayers) {
            textArea.append(player.getPlayerName());
        }
    }
}
