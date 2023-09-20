package nl.habiboellah.battleship.game;

import nl.habiboellah.battleship.model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MatchListTest {
    @Test
    public void testGetPlayers() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("John", "2");
        Player playerThree = new Player("Doe", "3");
        Player playerFour = new Player("Skippy", "4");

        Match matchOne = new Match(playerOne, playerTwo);
        Match matchTwo = new Match(playerThree, playerFour);

        MatchList list = new MatchList();
        list.addAll(List.of(matchOne, matchTwo));
        assertEquals(
                Stream.of(playerOne, playerTwo, playerThree, playerFour).collect(Collectors.toList()),
                list.getPlayers().collect(Collectors.toList())
        );
    }

    @Test
    public void testFindOpponent() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("John", "2");
        Player playerThree = new Player("Doe", "3");
        Player playerFour = new Player("Skippy", "4");
        Player playerFive = new Player("Doodle", "5");

        Match matchOne = new Match(playerOne, playerTwo);
        Match matchTwo = new Match(playerThree, playerFour);

        MatchList list = new MatchList();
        list.addAll(List.of(matchOne, matchTwo));

        assertEquals(Optional.of(playerOne), list.findOpponent(playerTwo));
        assertNotEquals(Optional.of(playerTwo), list.findOpponent(playerTwo));
        assertNotEquals(Optional.of(playerThree), list.findOpponent(playerTwo));
        assertNotEquals(Optional.of(playerFour), list.findOpponent(playerTwo));
        assertNotEquals(Optional.of(playerFive), list.findOpponent(playerTwo));

        assertEquals(Optional.of(playerFour), list.findOpponent(playerThree));

        assertEquals(Optional.empty(), list.findOpponent(playerFive));
    }

    @Test
    public void testFindMatch() {
        Player playerOne = new Player("Bob", "1");
        Player playerTwo = new Player("John", "2");
        Player playerThree = new Player("Doe", "3");
        Player playerFour = new Player("Skippy", "4");
        Player playerFive = new Player("Doodle", "5");

        Match matchOne = new Match(playerOne, playerTwo);
        Match matchTwo = new Match(playerThree, playerFour);

        MatchList list = new MatchList();
        list.addAll(List.of(matchOne, matchTwo));

        // Find match by match id
        assertEquals(Optional.of(matchOne), list.findMatch(matchOne.getId()));
        assertNotEquals(Optional.of(matchTwo), list.findMatch(matchOne.getId()));
        assertEquals(Optional.empty(), list.findMatch("gobble gobble"));

        // Find match by player
        assertEquals(Optional.of(matchOne), list.findMatch(playerOne));
        assertEquals(Optional.of(matchOne), list.findMatch(playerTwo));
        assertEquals(Optional.of(matchTwo), list.findMatch(playerThree));
        assertEquals(Optional.of(matchTwo), list.findMatch(playerFour));
        assertEquals(Optional.empty(), list.findMatch(playerFive));
    }
}
