import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    private Player intermediate;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    private boolean connectionOpen = true;
    public boolean receivedDeck = false;

    public ServerThread(Socket socket) {
    	 	
        try {
            this.socket = socket;
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("ServerThread Constructor. IOException.");
        }
        
        // Not sure where to put this...
        // Establishing a connection to the MySQL database
//        Connection con;
//        try {
//        	Class.forName("com.mysql.jdbc.Driver").newInstance();
//        	con = DriverManager.getConnection("jdbc:mysql://199.98.20.119/SetDatabase","TDguest","TDpass");
//        	if(!con.isClosed())
//        		System.out.println("Successfully connected to MySql server");
//        	
//        	Statement s = con.createStatement();
//        	s.executeUpdate("DROP TABLE IF EXISTS main");
//        	s.executeUpdate(
//        			"CREATE TABLE main ("
//        			+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
//        			+ "PRIMARY KEY (id),"
//        			+ "name CHAR(40), password CHAR(40), wins integer NOT NULL, losses integer NOT NULL)");
//        } catch (Exception e){
//        	System.err.println("Exception: " + e.getMessage());
//        }  
    }

    public void run() {
        while (connectionOpen) {
            // Here are all the things the Server receives from the Client during a protocol
            Integer eventHandler = 0;
            String message = null;
            int[] triplet = new int[3];
            try {
                eventHandler = (Integer) input.readObject();
                switch (eventHandler) {
                    case GlobalConstants.JOIN_GAME:
                        intermediate = (Player) input.readObject();
                        Server.playersInGame.add(intermediate);
                        break;
                    case GlobalConstants.LEAVE_GAME:
                        intermediate = (Player) input.readObject();
                        Server.playersInGame.remove(intermediate);
                        break;
                    case GlobalConstants.SEND_MESSAGE:
                        message = (String) input.readObject();
                        intermediate = (Player) input.readObject();
                        break;
                    case GlobalConstants.SUBMIT_SET:
                        triplet = (int []) input.readObject();
                        intermediate = (Player) input.readObject();
                }
                Server.commands.add(new Command(eventHandler, intermediate, message, triplet));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                System.err.println("ServerThread: run: ClassNotFoundException");
            }
        }
    }
}

