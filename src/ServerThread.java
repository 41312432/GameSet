import java.io.EOFException;
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

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    Player player = (Player) input.readObject();
                    if (!playersInGame.contains(player)) {
                        playersInGame.add(player);
                        output.writeBoolean(true);
                    } else {
                        output.writeBoolean(false);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

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

