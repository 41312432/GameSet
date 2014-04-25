import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public void playerLobby(Player player) {
        // Add a new Player to GraphicsLobby. Notify all Clients.
        try {
            output.writeObject(new Integer(GlobalConstants.ADD_PLAYER));
            output.writeObject(player);
        } catch (IOException e) {
            System.err.println("Client: playerLobby. Bad port number: " + PORT_NUMBER);
        }
    }

    public void leaveGame(Player player) {
        // Leave the game, remove player from the GraphicsLobby.
        try {
            output.writeObject(new Integer(GlobalConstants.LEAVE_GAME));
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
                        Boolean countdown;
                        switch (eventHandler){
                            case GlobalConstants.ADD_PLAYER:
                                Player newPlayer = (Player) input.readObject();
                                GlobalVariables.gamePlayers.add(newPlayer);
                                System.out.println(newPlayer.getPlayerName() + " has joined the game!");
                                countdown = (Boolean) input.readObject();
                                GraphicsLobby.updateLobby(countdown);
                                break;
                            case GlobalConstants.LEAVE_GAME:
                                Player leavingPlayer = (Player) input.readObject();
                                GlobalVariables.gamePlayers.remove(leavingPlayer);
                                System.out.println(leavingPlayer.getPlayerName() + " has left the game!");
                                countdown = (Boolean) input.readObject();
                                GraphicsLobby.updateLobby(countdown);
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
