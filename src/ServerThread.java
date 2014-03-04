import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    ObjectInputStream input;
    ObjectOutputStream output;
    private boolean connectionOpen = true;

    private static final int SECOND = 1000;
    private Player newPlayer;

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
        try {
            while (connectionOpen) {
                Integer eventHandler = (Integer) input.readObject(); // Listen for an event

                // This switch() ... case statement handles what happens whenever a Client
                // sends a particular eventHandler to the Server.
                switch (eventHandler) {
                    case GlobalConstants.ADD_PLAYER:
                        newPlayer = (Player) input.readObject();
                        break;
                    case GlobalConstants.BREAK_CONNECTION:
                        connectionOpen = false;
                        break;
                }
                globalResponse(eventHandler);
            }

            output.close();
            input.close();
            socket.close();
        } catch (ClassNotFoundException e) {
            System.err.println("ServerThread: run. ClassNotFoundException.");
        } catch (IOException e) {
            System.err.println("ServerThread: run. IOException. ");
        }
    }

    private void globalResponse(Integer eventHandler) {
        // Whenever the Server receives a message, check event handler and
        // broadcast an appropriate response to all Clients.

        // TODO: This may be ineffective. If a ServerThread is responding to all Clients, other ServerThreads have to wait.
        while (Server.respondingToClient){
            try {
                Thread.sleep(SECOND); // Wait one second, and try again.
            } catch (InterruptedException e) {
                System.err.println("ServerThread: respondingToClient. IOException.");
            }
        }

        Server.respondingToClient = true;

        for (ServerThread client : Server.connections) {
            try {
                client.output.writeObject(eventHandler);
                switch (eventHandler) {
                    case GlobalConstants.ADD_PLAYER:
                        client.output.writeObject(newPlayer);
                        break;
                    case GlobalConstants.BREAK_CONNECTION:
                        break;
                }
            } catch (IOException e) {
                System.err.println("ServerThread: globalResponse. IOException.");
            }
        }

        Server.respondingToClient = false;
    }
}

