import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    private Player intermediate;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    private boolean connectionOpen = true;
    public boolean receivedDeck = false;

    public ServerThread(Socket socket) {
        try {
            this.socket = socket;
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("ServerThread Constructor. IOException.");
        }
    }

    public void run() {
        while (connectionOpen) {
            // Here are all the things the Server receives from the Client during a protocol
            Integer eventHandler = 0;
            try {
                eventHandler = (Integer) input.readObject();
                switch (eventHandler) {
                    case GlobalConstants.JOIN_GAME:
                        intermediate = (Player) input.readObject();
                        Server.playersInGame.add(intermediate);
                        break;
                    case GlobalConstants.LEAVE_GAME:
                        intermediate = (Player) input.readObject();
                        Server.playersInGame.remove(intermediate);
                        break;
                }
                Server.commands.add(new Command(eventHandler, intermediate));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                System.err.println("ServerThread: run: ClassNotFoundException");
            }
        }
    }

    public void respondToClients(Integer eventHandler) {
        // Here are all the things that the server sends back to each Client

        for (ServerThread client : Server.connections) {
            try {
                client.output.writeObject(eventHandler);
                switch (eventHandler) {
                    case GlobalConstants.JOIN_GAME:
                        client.output.writeObject(intermediate);
                        break;
                    case GlobalConstants.LEAVE_GAME:
                        client.output.writeObject(intermediate);
                        break;
                    case GlobalConstants.REQUEST_PLAYERS:
                        client.output.writeObject(Server.playersInGame);
                        break;
                    case GlobalConstants.START_GAME:
                        client.output.writeObject(Server.deck);
                        receivedDeck = true;
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean allReceived = true;

        for (ServerThread client: Server.connections) {
            allReceived = client.receivedDeck && allReceived;
        }

        if (allReceived) {
            Server.deck = new Deck();
            for (ServerThread client: Server.connections) {
                client.receivedDeck = false;
            }
        }

        Server.respondingToClient = false;
    }
}

