import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static Socket socket;
    static ObjectInputStream input;
    static ObjectOutputStream output;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9090);
        try {
            while (true) {
                socket = serverSocket.accept();
                try {
                    input = new ObjectInputStream(socket.getInputStream());
                    output = new ObjectOutputStream(socket.getOutputStream());

                    while (true) {
                        System.out.println("Before read object");
                        try {
                            Card card = (Card) input.readObject();
                            System.out.println("After read object");
                            for (int feature : card.getFeatures()) {
                                System.out.println(feature + " ");
                            }
                        } finally {
                            System.out.println("Couldn't read the card");
                        }
                    }
                } finally {
                    socket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }
}