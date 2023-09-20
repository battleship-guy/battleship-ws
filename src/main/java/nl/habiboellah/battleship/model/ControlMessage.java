package nl.habiboellah.battleship.model;

public class ControlMessage {
    public enum EventType {
        ERROR,
        LOBBY_UPDATE,
        MATCH_SETUP_UPDATE,
        OPPONENT_LEFT
    }

    private final EventType eventType;
    private final Object responseBody;

    public ControlMessage(EventType eventType, Object responseBody) {
        this.eventType = eventType;
        this.responseBody = responseBody;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Object getResponseBody() {
        return responseBody;
    }
}
