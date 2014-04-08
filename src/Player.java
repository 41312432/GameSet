import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    int points = 0;
    private String playerName;

    Player(String playerName) {
        this.playerName = playerName;
    }

    public void addPoint() {
        points += 1;
    }

    public void subPoint() {
        points -= 1;
    }

    public int getPoints() {

        return points;
    }

    public String getPlayerName() {
        return playerName;
    }
}
