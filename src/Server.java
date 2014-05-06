import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    /* This is the Server class. Whenever a Client connects to the Server
     * it spawns a new thread that handles communication for that particular
     * player. */

    public static boolean listening = true;
    public static ArrayList<ServerThread> connections = new ArrayList<ServerThread>();
    public static ArrayList<Player> playersInGame = new ArrayList<Player>();
    public static Deck deck = new Deck();
    public static ConcurrentLinkedQueue<Command> commands = new ConcurrentLinkedQueue<Command>();

    public static void main(String args[]) throws IOException {

        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!commands.isEmpty()) {
                        Command command = commands.poll();
                        Integer eventHandler = command.getEventHandler();
                        Player intermediate = command.getIntermediate();

                        for (ServerThread client : Server.connections) {
                            try {
                                client.output.writeObject(eventHandler);
                                switch (eventHandler) {
                                    case GlobalConstants.JOIN_GAME:
                                        client.output.writeObject(intermediate);
                                        break;
                                    case GlobalConstants.LEAVE_GAME:
                                        client.output.writeObject(intermediate);
                                        break;
                                    case GlobalConstants.REQUEST_PLAYERS:
                                        client.output.writeObject(playersInGame);
                                        break;
                                    case GlobalConstants.START_GAME:
                                        client.output.writeObject(Server.deck);
                                        client.receivedDeck = true;
                                        break;
                                    case GlobalConstants.SEND_MESSAGE:
                                        client.output.writeObject(command.getMessage());
                                        client.output.writeObject(command.getIntermediate());
                                        break;
                                    case GlobalConstants.SUBMIT_SET:
                                        client.output.writeObject(command.getTriplet());
                                        client.output.writeObject(command.getIntermediate());
                                        break;
                                    case GlobalConstants.SUBMIT_ERROR:
                                        client.output.writeObject(command.getIntermediate());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (eventHandler == GlobalConstants.START_GAME) {
                            deck = new Deck();
                        }
                    }
                }
            }
        })).start();

        ServerSocket serverSocket = new ServerSocket(9090);
        while (listening) {
            ServerThread serverThread = new ServerThread(serverSocket.accept());
            connections.add(serverThread);
            serverThread.start();
        }
    }
}