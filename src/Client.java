import javax.swing.*;
import java.awt.*;
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

    private final String HOST_NAME = "199.98.20.119"; // 199.98.20.119 <-- IP Address for our VM
    private final int PORT_NUMBER = 9090;

    private Socket socket; // Use the Socket to send information from Client to Server
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public static ArrayList<Player> playersInGame = new ArrayList<Player>();
    public static Deck deck;
    public ArrayList<Integer> triplet;
    public Player player;
    public int PiG = 1; //players in game

    private GraphicsGame game;

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

    // SETUP: Write a method that goes like the ones below. It will send some constant, this will tell
    // the Server what to expect. Then it sends the necessary data. Server will read data in a similar way.
    // See waitForResponse() method below for more.
    
    // Send username and password (How to obtain password text?)
    public void sendInformation(Player player){
    	try{
    		String username = player.getPlayerName();
    		output.writeObject(GlobalConstants.SEND_INFO);
    		output.writeObject(username);
    		//output.writeObject(password);
    	}
    	catch (IOException e){
    		System.err.println("Not valid format");
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

    public void startGame() {
        try {
            output.writeObject(GlobalConstants.START_GAME);
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

    public void sendMessage(String message, Player player) {
        try {
            output.writeObject(GlobalConstants.SEND_MESSAGE);
            output.writeObject(message);
            output.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitSet(ArrayList<Integer> triplet, Player clientPlayer) {
        try {
            output.writeObject(GlobalConstants.SUBMIT_SET);
            output.writeObject(triplet);
            output.writeObject(clientPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitError(Player player) {
        try {
            output.writeObject(GlobalConstants.SUBMIT_ERROR);
            output.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForResponse() {
        // Client is constantly listening for response from Server on a separate Thread.
        // This is where global responses from Server take place.

        // Because of how the Server is set up, the Client will never be reading more than one message at a time.Oh
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
                                if (playersInGame.size() == PiG) {
                                    startGame();
                                }
                                break;
                            case GlobalConstants.LEAVE_GAME:
                                Player leaving = (Player) input.readObject();
                                playersInGame.remove(leaving);
                                GraphicsLobby.updateLobby(playersInGame);
                                break;
                            case GlobalConstants.REQUEST_PLAYERS:
                                playersInGame = (ArrayList<Player>) input.readObject();
                                GraphicsLobby.updateLobby(playersInGame);
                                break;
                            case GlobalConstants.START_GAME:
                                deck = (Deck) input.readObject();
                                GraphicsLobby.startGame(deck);
                                break;
                            case GlobalConstants.SEND_MESSAGE:
                                String message = (String) input.readObject();
                                player = (Player) input.readObject();
                                game.updateTextArea(message, player);
                                break;
                            case GlobalConstants.SUBMIT_SET:
                                triplet = (ArrayList<Integer>) input.readObject();
                                player = (Player) input.readObject();
                                game.correctSetUpdate(triplet, player);
                                break;
                            case GlobalConstants.SUBMIT_ERROR:
                                player = (Player) input.readObject();
                                game.wrongSet(player);
                            case GlobalConstants.SEND_INFO:
//                            	player = (Player) input.readObject();
                            	//Don't know what else to put here
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    } catch (ClassNotFoundException e) {
                        System.err.println("Client: waitForResponse. ClassNotFoundException.");
                    }
                }
            }
        };

        thread.start();
    }

    public void setGame(GraphicsGame game) {
        this.game = game;
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
