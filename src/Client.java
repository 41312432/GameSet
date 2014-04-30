import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    /* The Client class: contains methods that communicated with the Server.
     * On the Server end, messages are read in using EventHandlers, an integer
     * that tells an available Server thread what the Client is trying to
     * communicate. The Server then broadcasts that message to all Clients. */

    private final String HOST_NAME = "localhost"; // 199.98.20.119 <-- IP Address for our VM
    private final int PORT_NUMBER = 9090;

    private Socket socket; // Use the Socket to send information from Client to Server
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private ArrayList<Player> playersInGame = new ArrayList<Player>();


    public Client() {
        // Set up the connection when Client is created, ideally one Client object per user.
        try {
            socket = new Socket(HOST_NAME, PORT_NUMBER);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            waitForResponse();
        } catch (IOException e) {
            System.err.println("Could not connect to Server.");
        }
    }

    public void joinGame(Player player) {
        // Add a new Player to GraphicsLobby. Notify all Clients.
        try {
            output.writeObject(GlobalConstants.JOIN_GAME);
            output.writeObject(player);
        } catch (IOException e) {
            System.err.println("Client: playerLobby. Bad port number: " + PORT_NUMBER);
        }
    }

    public void requestPlayers() {
        try {
            output.writeObject(GlobalConstants.REQUEST_PLAYERS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leaveGame(Player player) {
        // Leave the game, remove player from the GraphicsLobby.
        try {
            output.writeObject(GlobalConstants.LEAVE_GAME);
            output.writeObject(player);
        } catch (IOException e) {
            System.err.println("Client: playerLobby. Bad port number: " + PORT_NUMBER);
        }
    }

    private void waitForResponse() {
        // Client is constantly listening for response from Server on a separate Thread.
        // This is where global responses from Server take place.
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Integer eventHandler = (Integer) input.readObject();
                        switch (eventHandler) {
                            case GlobalConstants.JOIN_GAME:
                                Player newPlayer = (Player) input.readObject();
                                playersInGame.add(newPlayer);
                                GraphicsLobby.updateLobby(playersInGame);
                                break;
                            case GlobalConstants.LEAVE_GAME:
                                Player leaving = (Player) input.readObject();
                                playersInGame.remove(leaving);
                                GraphicsLobby.updateLobby(playersInGame);
                                break;
                            case GlobalConstants.REQUEST_PLAYERS:
                                playersInGame = (ArrayList<Player>) input.readObject();
                                GraphicsLobby.updateLobby(playersInGame);
                        }
                    } catch (IOException e) {
                        System.err.println("Client: waitForResponse. IOException.");
                    } catch (ClassNotFoundException e) {
                        System.err.println("Client: waitForResponse. ClassNotFoundException.");
                    }
                }
            }
        };

        thread.start();
    }

    public void closeConnection() {
        // Close the connection to the Server when Client has left the game.
        try {
            output.writeObject(new Integer(GlobalConstants.BREAK_CONNECTION));
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Client: closeConnection. IOException.");
        }
    }
}
