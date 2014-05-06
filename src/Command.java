import java.util.ArrayList;

public class Command {
    private Integer eventHandler;
    private Player intermediate;
    private String message;
    private ArrayList<Integer> triplet;


    public Command(Integer eventHandler, Player intermediate, String message, ArrayList<Integer> triplet) {
        this.eventHandler = eventHandler;
        this.intermediate = intermediate;
        this.message = message;
        this.triplet = triplet;
    }

    public Integer getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(Integer eventHandler) {
        this.eventHandler = eventHandler;
    }

    public Player getIntermediate() {
        return intermediate;
    }

    public void setIntermediate(Player intermediate) {
        this.intermediate = intermediate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Integer> getTriplet() {
        return triplet;
    }

    public void setTriplet(ArrayList<Integer> triplet) {
        this.triplet = triplet;
    }
}
