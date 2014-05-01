
public class Command {
    private Integer eventHandler;
    private Player intermediate;

    public Command(Integer eventHandler, Player intermediate) {
        this.eventHandler = eventHandler;
        this.intermediate = intermediate;
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
}
