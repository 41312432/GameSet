import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    /* The Client class: create isntances of this class and pass appropriate
     * arguments for the Server to compare between players. */

    public static void main(String[] args) {
        final String HOST_NAME = "localhost"; // Change this to I think the IP Address later
        final int PORT_NUMBER = 9090;

        Card card = new Card(0, 0, 0, 0);

        try {
            Socket socket = new Socket(HOST_NAME, PORT_NUMBER);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(card);

            card = new Card(1, 1, 1, 1);
            output.writeObject(card);

            output.flush();

            output.close();
            input.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Can not recognize: " + HOST_NAME);
        } catch (IOException e) {
            System.err.println("Bad port number: " + PORT_NUMBER);
        }
    }
}
