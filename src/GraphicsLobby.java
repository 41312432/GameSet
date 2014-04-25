import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GraphicsLobby extends JFrame {
    private JButton joinButton, leaveButton;
    private static boolean countingDown = false;
    private static JTextArea textArea;
    private final String JOIN = "join";
    private final Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private Player activePlayer;
    private Client client;

    public GraphicsLobby(Client client) {
        this.client = client;
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

        LoginPane loginPane = new LoginPane();
        ButtonPane buttonPane = new ButtonPane();
        TextPane textPane = new TextPane();

        background.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(0, 5, 5, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;

        loginPane.setOpaque(false);
        background.add(loginPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;

        buttonPane.setOpaque(false);
        background.add(buttonPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;

        textPane.setOpaque(false);
        background.add(textPane, constraints);

//        ButtonPane secondPane = new ButtonPane();
//
//        constraints.insets = new Insets(0, 5, 5, 0);
//        constraints.gridx = 0;
//        constraints.gridy = 5;
//
//        secondPane.setOpaque(false);
//        background.add(secondPane, constraints);

        pack();
        setVisible(true);
    }

    public class LoginPane extends JPanel implements ActionListener {
        public LoginPane() {
            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            Dimension buttonDimension = new Dimension(120, 50);

            JTextField userName = new JTextField(20);
            JPasswordField password = new JPasswordField(20);

            constraints.insets = new Insets(0, 5, 0, 0);
            constraints.gridx = 0;
            constraints.gridy = 0;

            add(userName, constraints);

            constraints.gridx = 1;
            constraints.gridy = 0;

            add(password, constraints);

        }


        @Override
        public void actionPerformed(ActionEvent e) {

        }
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

            (new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (countingDown) {
                            int countdown = 5;
                            for (; countdown > 0; countdown--) {
                                textArea.append("Game starting in: " + (countdown));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!countingDown) break;
                            }
                            if (countdown == 0) {
                                System.out.println("Starting game.");
                            }

                        }
                    }
                }
            })).start();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            if (JOIN.equals(e.getActionCommand())) {
//                String text = textField.getText();
//                if (text.trim().length() == 0) {
//                    textField.setText("");
//                } else {
//                    activePlayer = new Player(text);
//                    client.playerLobby(activePlayer);
//                    leaveButton.setEnabled(true);
//                    joinButton.setEnabled(false);
//                }
//            } else {
//                client.leaveGame(activePlayer);
//                leaveButton.setEnabled(false);
//                joinButton.setEnabled(true);
//            }
        }
    }

    public class TextPane extends JPanel implements ActionListener {

        public TextPane() {
            setLayout(new GridBagLayout());

            textArea = new JTextArea(5, 20);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            //Add Components to this panel.
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            add(scrollPane, constraints);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static void updateLobby(Boolean countdown) {
        textArea.setText("");
        for (Player player : GlobalVariables.gamePlayers) {
            textArea.append(player.getPlayerName() + "\n");
        }
        countingDown = countdown;
    }
}
