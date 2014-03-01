import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    /* The Client class: create instances of this class and pass appropriate
     * arguments for the Server to compare between players. There are multiple methods
     * to handle both ends for various parts of the game that the Server needs to
     * handle e.g. the Lobby, the Chat, the Game. */

    public boolean playerLobby(Player player) {
        final String HOST_NAME = "localhost";
        final int PORT_NUMBER = 9090;
        Boolean newPlayer = false;

        try {
            Socket socket = new Socket(HOST_NAME, PORT_NUMBER);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(player);
            output.flush();

            newPlayer = (Boolean) input.readObject();

            output.close();
            input.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Can not recognize: " + HOST_NAME);
        } catch (IOException e) {
            System.err.println("Bad port number: " + PORT_NUMBER);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
        }

        System.out.println(newPlayer);

        return newPlayer;
    }
}
