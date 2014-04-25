import java.util.ArrayList;

public class GameSet {

<<<<<<< HEAD
    //public static Client client = new Client();
	 public static TestClient client = new TestClient();
    
    public static void main(String[] args) {
        //GraphicsLobby graphics = new GraphicsLobby();
        //GraphicsGame graphicsGame = new GraphicsGame();
=======
    public static void main(String[] args) {
        Client client = new Client();

        GraphicsLobby graphics = new GraphicsLobby(client);

//        Player player = new Player("Mike");
//
//        // All of these things need to be found on the Server within GraphicsLobby. Then game can start.
//        Deck deck = new Deck();
//        ArrayList<Player> playersInGame = new ArrayList<Player>();
//        playersInGame.add(player);
//        playersInGame.add(new Player("Lauren"));
//        playersInGame.add(new Player("Joe"));
//        playersInGame.add(new Player("Jennifer"));
//
//        GraphicsGame graphicsGame = new GraphicsGame(client, playersInGame, player, deck);
>>>>>>> 50f820bf6ed060518e0cb41e1e455eb6a6355153
    }
}