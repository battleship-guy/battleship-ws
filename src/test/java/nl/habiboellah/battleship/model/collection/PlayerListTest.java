package nl.habiboellah.battleship.model.collection;

import nl.habiboellah.battleship.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerListTest {
    @Test
    public void testAdd() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("Bob", "2");
        Player playerThree = new Player("Bonny", "3");

        PlayerList list = new PlayerList();
        assertTrue(list.add(playerOne));
        assertFalse(list.add(playerTwo));
        assertTrue(list.add(playerThree));
    }

    @Test
    public void testContains() {
        Player playerOne = new Player("Bob", "!");
        Player playerTwo = new Player("Bob", "2");
        Player playerThree = new Player("Bonny", "3");

        PlayerList list = new PlayerList();
        assertFalse(list.contains(playerOne));
        assertFalse(list.contains(playerTwo));
        assertFalse(list.contains(playerThree));
        list.add(playerOne);
        assertTrue(list.contains(playerOne));
        assertTrue(list.contains(playerTwo));
        assertFalse(list.contains(playerThree));
    }
}
