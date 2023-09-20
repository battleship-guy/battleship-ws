package nl.habiboellah.battleship.game.board;

import org.junit.jupiter.api.Test;

import static nl.habiboellah.battleship.game.board.Direction.HORIZONTAL;
import static nl.habiboellah.battleship.game.board.Direction.VERTICAL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipPlacementGridTest {
    @Test
    public void testPlaceShip() {
        ShipPlacementGrid grid = new ShipPlacementGrid();

        assertFalse(grid.placeShip(-1, 0, HORIZONTAL, 2));
        assertFalse(grid.placeShip(0, -1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(9, 0, HORIZONTAL, 2));
        assertFalse(grid.placeShip(0, 9, VERTICAL, 2));
        assertTrue(grid.placeShip(0, 0, HORIZONTAL, 2));
        assertFalse(grid.placeShip(1, 0, HORIZONTAL, 2));
        assertFalse(grid.placeShip(2, 0, HORIZONTAL, 2));
        assertTrue(grid.placeShip(3, 0, HORIZONTAL, 2));
        assertFalse(grid.placeShip(0, 1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(1, 1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(2, 1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(3, 1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(4, 1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(5, 1, HORIZONTAL, 2));
        assertTrue(grid.placeShip(6, 1, HORIZONTAL, 2));
        assertFalse(grid.placeShip(0, 0, VERTICAL, 5));
        assertFalse(grid.placeShip(0, 1, VERTICAL, 5));
        assertTrue(grid.placeShip(0, 2, VERTICAL, 5));
    }
}
