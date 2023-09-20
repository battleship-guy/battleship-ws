package nl.habiboellah.battleship.game.board;

import java.util.Arrays;

public enum Direction {
    HORIZONTAL("H"),
    VERTICAL("V");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public static Direction getDirectionByValue(String value) {
        return Arrays.stream(Direction.values())
                .filter(d -> d.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }

    public String getValue() {
        return value;
    }
}