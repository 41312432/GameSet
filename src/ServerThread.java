import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//import com.mysql.jdbc.Connection;

public class ServerThread extends Thread {

    /* Worker thread, handles communication between a single
     * player and the Server. */

    private Socket socket = null;
    private Player intermediate;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    private boolean connectionOpen = true;
    public boolean receivedDeck = false;
    public java.sql.Connection con = Database.getConnection();

    public ServerThread(Socket socket) {

        try {
            this.socket = socket;
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("ServerThread Constructor. IOException.");
        }
    }

    public void run() {
        while (connectionOpen) {
        	//maybe we have to add this in server too? 
        	//I added this here. I hope I am doing things right. 
        	Database.setupTable(con);
            // Here are all the things the Server receives from the Client during a protocol
            Integer eventHandler = 0;
            String message = null;
            ArrayList<Integer> triplet = new ArrayList<Integer>();
            try {
                eventHandler = (Integer) input.readObject();
                switch (eventHandler) {
                    case GlobalConstants.JOIN_GAME:
                        intermediate = (Player) input.readObject();
                        Server.playersInGame.add(intermediate);
                        //This is Jennifer. I'm adding things to places I think are where things should be? Wat? 
                        //actually, this shouldn't be here...I'll change it later. Let's see if this doesn't break first. 
                        Database.addPlayer(con, intermediate);
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
                        triplet = (ArrayList<Integer>) input.readObject();
                        intermediate = (Player) input.readObject();
                        break;
                    case GlobalConstants.SUBMIT_ERROR:                    
                        intermediate = (Player) input.readObject();
                        break;
                    case GlobalConstants.SEND_INFO:
                    	intermediate = (Player) input.readObject();
                    	//add something for username
                    	//add something for password
                    	//update server commands below
                    	break;
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

