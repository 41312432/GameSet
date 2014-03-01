import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    /* This is the Server class. Whenever a Client connects to the Server
     * it spawns a new thread that handles communication for that particular
     * player. */

    public static boolean listening = true;

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (listening) {
            new ServerThread(serverSocket.accept()).start();
        }
    }
}