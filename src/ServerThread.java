import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    private Player intermediate;
    ObjectInputStream input;
    ObjectOutputStream output;
    private boolean connectionOpen = true;

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
                }
                respondToClients(eventHandler);
            } catch (IOException e) {
                System.err.println("ServerThread: run: IOException");
            } catch (ClassNotFoundException e) {
                System.err.println("ServerThread: run: ClassNotFoundException");
            }
        }
    }

    public void respondToClients(Integer eventHandler) {
        // Here are all the things that the server sends back to each Client

        while (Server.respondingToClient){
            try {
                Thread.sleep(250); // Wait one second, and try again.
            } catch (InterruptedException e) {
                System.err.println("ServerThread: respondingToClient. IOException.");
            }
        }

        Server.respondingToClient = true;

        for (ServerThread client : Server.connections) {
            try {
                client.output.writeObject(eventHandler);
                switch (eventHandler) {
                    case GlobalConstants.JOIN_GAME:
                        client.output.writeObject(intermediate);
                        break;
                    case GlobalConstants.BREAK_CONNECTION:
                        break;
                    case GlobalConstants.LEAVE_GAME:
                        client.output.writeObject(intermediate);
                        break;
                    case GlobalConstants.REQUEST_PLAYERS:
                        client.output.writeObject(Server.playersInGame);
                }
            } catch (IOException e) {
                System.err.println("ServerThread: globalResponse. IOException.");
            }
        }

        Server.respondingToClient = false;
    }
}

