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
            String message = null;
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
                    case GlobalConstants.SEND_MESSAGE:
                        message = (String) input.readObject();
                        intermediate = (Player) input.readObject();
                        break;
                }
                Server.commands.add(new Command(eventHandler, intermediate, message));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                System.err.println("ServerThread: run: ClassNotFoundException");
            }
        }
    }
}

