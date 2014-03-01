import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    static boolean listening = true;

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (listening) {
            new ServerThread(serverSocket.accept()).start();
        }
    }
}