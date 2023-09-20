package nl.habiboellah.battleship.model;

import nl.habiboellah.battleship.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    public void testEquals() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("Bob", "2");
        Player playerThree = new Player("Bonny", "1");

        assertTrue(playerOne.equals(playerTwo));
        assertFalse(playerOne.equals(playerThree));
    }

}
