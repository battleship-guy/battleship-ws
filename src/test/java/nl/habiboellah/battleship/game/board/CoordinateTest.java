package nl.habiboellah.battleship.game.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoordinateTest {
    @Test
    public void testCoordinate() {
        testCoordinateHelper("A1", 0, 0);
        testCoordinateHelper("A4", 0, 3);
        testCoordinateHelper("A10", 0, 9);
        testCoordinateHelper("J1", 9, 0);
        testCoordinateHelper("J4", 9, 3);
        testCoordinateHelper("J10", 9, 9);

        assertThrows(IllegalArgumentException.class, () -> {
            new Coordinate("A11");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordinate("K1");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordinate("K11");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordinate("A0");
        });
        assertThrows(NumberFormatException.class, () -> {
            new Coordinate("AA");
        });
        assertThrows(NumberFormatException.class, () -> {
            new Coordinate("A");
        });
        assertThrows(NumberFormatException.class, () -> {
            new Coordinate("..");
        });
        assertThrows(StringIndexOutOfBoundsException.class, () -> {
            new Coordinate("");
        });
    }

    private void testCoordinateHelper(String coordinate, int expectedX, int expectedY) {
        Coordinate c = new Coordinate(coordinate);
        assertEquals(expectedX, c.getX());
        assertEquals(expectedY, c.getY());
        assertEquals(coordinate, c.toString());
    }
}
