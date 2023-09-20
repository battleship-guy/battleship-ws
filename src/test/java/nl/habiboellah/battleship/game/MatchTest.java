package nl.habiboellah.battleship.game;

import nl.habiboellah.battleship.model.Player;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    @Test
    public void testContainsPlayer() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("John", "2");
        Player playerThree = new Player("Doe", "3");
        Match match = new Match(playerOne, playerTwo);

        assertTrue(match.containsPlayer(playerOne));
        assertTrue(match.containsPlayer(playerTwo));
        assertFalse(match.containsPlayer(playerThree));
    }

    @Test
    public void testGetOpponent() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("John", "2");
        Player playerThree = new Player("Doe", "3");
        Match match = new Match(playerOne, playerTwo);

        assertEquals(Optional.of(playerTwo), match.getOpponent(playerOne));
        assertEquals(Optional.of(playerOne), match.getOpponent(playerTwo));
        assertEquals(Optional.empty(), match.getOpponent(playerThree));
    }

    @Test
    public void testEquals() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("John", "2");
        Player playerThree = new Player("Doe", "3");

        Match matchOne = new Match(playerOne, playerTwo);
        Match matchTwo = new Match(playerTwo, playerOne);
        Match matchThree = new Match(playerTwo, playerThree);

        assertEquals(matchOne, matchTwo);
        assertEquals(matchTwo, matchOne);
        assertNotEquals(matchOne, matchThree);
        assertNotEquals(matchTwo, matchThree);
        assertNotEquals(matchThree, matchOne);
        assertNotEquals(matchThree, matchTwo);
    }
}
