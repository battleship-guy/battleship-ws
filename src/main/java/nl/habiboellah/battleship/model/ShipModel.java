package nl.habiboellah.battleship.model;

public class ShipModel {
    private final int id;
    private final String name;
    private final String length;

    public ShipModel(int id, String name, String length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLength() {
        return length;
    }
}
