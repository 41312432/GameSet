import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    /* The Client class: create instances of this class and pass appropriate
     * arguments for the Server to compare between players. There are multiple methods
     * to handle both ends for various parts of the game that the Server needs to
     * handle e.g. the Lobby, the Chat, the Game. */

    private final String HOST_NAME = "localhost"; // 199.98.20.119 <-- IP Address for our VM
    private final int PORT_NUMBER = 9090;

    private Socket socket; // Use the Socket to send information from Client to Server
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client() {
        // Set up the connection when Client is created, ideally one Client object per user.
        try {
            socket = new Socket(HOST_NAME, PORT_NUMBER);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            waitForResponse();
        } catch (UnknownHostException e) {
            System.err.println("Client Constructor. Unknown host name: " + HOST_NAME);
        } catch (IOException e) {
            System.err.println("Client Constructor. Bad port number: " + PORT_NUMBER);
        }
    }

    public boolean playerLobby(Player player) {

        Boolean newPlayer = false;
        try {
            output.writeObject(new Integer(GlobalConstants.ADD_PLAYER));
            output.writeObject(player);
        } catch (IOException e) {
            System.err.println("Client: playerLobby. Bad port number: " + PORT_NUMBER);
        }

        return newPlayer;
    }

    private void waitForResponse() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Integer eventHandler = (Integer) input.readObject();
                        switch (eventHandler){
                            case GlobalConstants.ADD_PLAYER:
                                GlobalVariables.gamePlayers = (ArrayList<Player>) input.readObject();
                                System.out.println("Works to this point");
                                break;
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
