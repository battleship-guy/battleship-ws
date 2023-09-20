package nl.habiboellah.battleship.game.board;

public enum Ship {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    CRUISER("Cruiser", 3),
    SUBMARINE("Submarine", 3),
    DESTROYER("Destroyer", 2);

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    private final String name;
    private final Integer length;

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }
}
