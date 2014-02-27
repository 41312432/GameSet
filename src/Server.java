import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5122);
            Socket socket = serverSocket.accept();

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String message;

            while (true) {
                message = input.readLine();
                System.out.println(message);
                if(message.equals("break")){
                    break;
                }
            }

            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Server failure!");
        }
    }
}
