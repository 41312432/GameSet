import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5122);

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String message;

            output.writeBytes("This is my message");

//            while (true) {
////                Scanner scanner = new Scanner(System.in);
////                message = scanner.next();
//                message = "Hello how are you";
//                output.writeBytes(message);
//                if (message.equals("break")) {
//                    break;
//                }
//            }

            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Connection not established!");
        }
    }
}
