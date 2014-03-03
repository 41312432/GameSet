import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    private ArrayList<Player> playersInGame = new ArrayList<Player>();

    private final Integer EVENT = 0; // TODO: List all of the events that can go through the handler here

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            Integer eventHandler = (Integer) input.readObject();

            Player player = (Player) input.readObject();
            if (!playersInGame.contains(player)) {
                playersInGame.add(player);
                output.writeObject(new Boolean(true));
            } else {
                output.writeObject(new Boolean(false));
            }

//            while (true) {
//                try {
//                    Player player = (Player) input.readObject();
//                    if (!playersInGame.contains(player)) {
//                        playersInGame.add(player);
//                        output.writeBoolean(true);
//                    } else {
//                        output.writeBoolean(false);
//                    }
//                } catch (EOFException e) {
//                    break;
//                }
//            } This is a useful construct for later

            output.close();
            input.close();
            socket.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }
}

