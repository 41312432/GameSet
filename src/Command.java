
public class Command {
    private Integer eventHandler;
    private Player intermediate;
    private String message;

    public Command(Integer eventHandler, Player intermediate, String message) {
        this.eventHandler = eventHandler;
        this.intermediate = intermediate;
        this.message = message;
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
}
