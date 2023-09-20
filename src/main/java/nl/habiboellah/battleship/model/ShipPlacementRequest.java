package nl.habiboellah.battleship.model;

public class ShipPlacementRequest {
    private final String matchId;
    private final String position;
    private final int shipId;
    private final String direction;

    public ShipPlacementRequest(String matchId, String position, int shipId, String direction) {
        this.matchId = matchId;
        this.position = position;
        this.shipId = shipId;
        this.direction = direction;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getPosition() {
        return position;
    }

    public int getShipId() {
        return shipId;
    }

    public String getDirection() {
        return direction;
    }
}
