package nl.habiboellah.battleship.game.board;

public class ShipPlacement {
    private final Ship ship;
    private final Coordinate coordinate;
    private final Direction direction;

    public ShipPlacement(Ship ship, Coordinate coordinate, Direction direction) {
        this.ship = ship;
        this.coordinate = coordinate;
        this.direction = direction;
    }

    public Ship getShip() {
        return ship;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }
}
