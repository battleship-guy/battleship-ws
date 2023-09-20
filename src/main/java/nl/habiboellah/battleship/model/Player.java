package nl.habiboellah.battleship.model;

public class Player {
    private final String name;
    private final String id;

    public Player(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Player player) {
        return name.equals(player.getName());
    }

    public String toString() {
        return getName();
    }

    public String getId() {
        return id;
    }
}
