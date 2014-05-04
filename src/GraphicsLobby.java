import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphicsLobby extends JFrame {
    private final Dimension PREFERRED_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private GridBagConstraints constraints = new GridBagConstraints();
    private static JTextArea textArea = new JTextArea(5, 20);
    private static Client client = new Client();
    private MainLobby mainLobby;
    private LoginPane loginPane;
    public static JPanel background;
    public static CardLayout cardLayout = new CardLayout();
    public static Player player;

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
        setContentPane(new JLabel(new ImageIcon("images/gameSetBackground.jpg")));
        setPreferredSize(PREFERRED_SIZE);
        setLayout(new GridBagLayout());

        background = new JPanel(cardLayout);

        loginPane = new LoginPane();
        mainLobby = new MainLobby();

        background.add(loginPane, "login");
        background.add(mainLobby, "main");
        background.setBackground(new Color(0, 0, 0, 0));

        add(background);
        pack();
        setVisible(true);
    }

    public class LoginPane extends JPanel {

        JLabel label = new JLabel("Login");
        JLabel secondLabel = new JLabel("If you don't have a profile, click below to create one!");
        JTextField userName;
        JPasswordField password;
        JButton create = new JButton("Create Profile");

        public LoginPane() {
            setLayout(new GridBagLayout());

            label.setFont(new Font("Serif", Font.PLAIN, 40));

            userName = new JTextField(20);
            password = new JPasswordField(20);

            constraints.insets = new Insets(2, 2, 2, 2);
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(label, constraints);

            constraints.gridy = 1;
            add(userName, constraints);

            constraints.gridy = 2;
            add(password, constraints);

            constraints.gridy = 3;
            add(secondLabel, constraints);

            constraints.gridy = 4;
            add(create, constraints);

            userName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    password.requestFocus();
                }
            });

            password.addActionListener(new ActionListener() {
                // TODO: This is where all the Client Sever communication will happen to search SQL.
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!userName.getText().equals("")) {
                        player = new Player(userName.getText());
                        mainLobby.setPlayer();
                        cardLayout.show(background, "main");
                    }
                }
            });


            create.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Add a new entry to the SQL database.
                	
                }
            });
        }
    }

    class MainLobby extends JPanel {

        boolean joined = false;
        JLabel label = new JLabel();
        JButton toggleEnter = new JButton("Join Game");

        public MainLobby() {
            setLayout(new GridBagLayout());
            textArea.setEditable(false);

            constraints.gridy = 0;
            add(label, constraints);

            constraints.gridy = 1;
            add(toggleEnter, constraints);

            constraints.insets = new Insets(0, 20, 0, 20);
            constraints.gridx = 1;
            add(textArea, constraints);

            toggleEnter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!joined) {
                        toggleEnter.setText("Leave Game");
                        client.joinGame(player);
                        joined = true;
                    } else {
                        toggleEnter.setText("Join Game");
                        client.leaveGame(player);
                        joined = false;
                    }
                }
            });
        }

        public void setPlayer() {
            label.setText("Logged in as " + player.getPlayerName()); // Can also display some player stats here
            client.requestPlayers();
        }
    }

    public static void updateLobby(ArrayList<Player> playersInGame) {
        textArea.setText("Players In Game\n\t");
        for (Player players : playersInGame) {
            textArea.append(players.getPlayerName() + "\n\t");
        }
    }

    public static void startGame(Deck deck) {
        GraphicsGame game = new GraphicsGame(client, player, deck);
        background.add(game, "game");
        cardLayout.show(background, "game");
    }
}
