import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    ObjectInputStream input;
    ObjectOutputStream output;
    private ArrayList<Player> playersInGame = new ArrayList<Player>();
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
        try {
            while (connectionOpen) {
                Integer eventHandler = (Integer) input.readObject(); // Listen for an event

                switch (eventHandler) {
                    case GlobalConstants.ADD_PLAYER:
                        Player player = (Player) input.readObject();
                        if (!playersInGame.contains(player)) {
                            playersInGame.add(player);
                        }
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
        for (ServerThread client : Server.connections) {
            try {
                client.output.writeObject(eventHandler);
                switch (eventHandler){
                    case GlobalConstants.ADD_PLAYER:
                        client.output.writeObject(playersInGame);
                        break;
                    case GlobalConstants.BREAK_CONNECTION:
                        break;
                }
            } catch (IOException e) {
                System.err.println("ServerThread: globalResponse. IOException.");
            }
        }
    }
}

