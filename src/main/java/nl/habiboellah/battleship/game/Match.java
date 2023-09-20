package nl.habiboellah.battleship.game;

import nl.habiboellah.battleship.model.Player;
import nl.habiboellah.battleship.model.collection.PlayerList;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Match {
    private final Player playerOne;
    private final Player playerTwo;
    private final String id;

    private Player playerAtTurn;

    private Phase matchPhase;

    public Match(Player playerOne, Player playerTwo) {
        this.id = UUID.randomUUID().toString();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.matchPhase = Phase.SETUP;
    }

    public enum Phase {
        SETUP,
        WAR,
        VICTORY
    }

    public PlayerList getPlayers() {
        return new PlayerList(playerOne, playerTwo);
    }

    public boolean containsPlayer(Player player) {
        return playerOne.equals(player) || playerTwo.equals(player);
    }

    public Optional<Player> getOpponent(Player player) {
        if (!containsPlayer(player)) {
            return Optional.empty();
        }
        if (playerOne.equals(player)) {
            return Optional.of(playerTwo);
        } else {
            return Optional.of(playerOne);
        }
    }

    public String getId() {
        return id;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Phase getMatchPhase() {
        return matchPhase;
    }

    public void setMatchPhase(Phase matchPhase) {
        this.matchPhase = matchPhase;
    }

    public Player getPlayerAtTurn() {
        return playerAtTurn;
    }

    public void setPlayerAtTurn(Player playerAtTurn) {
        this.playerAtTurn = playerAtTurn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Match) obj;
        return this.containsPlayer(that.getPlayerOne()) && this.containsPlayer(that.getPlayerTwo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerOne, playerTwo);
    }

    @Override
    public String toString() {
        return "Match[" +
                "playerOne=" + playerOne + ", " +
                "playerTwo=" + playerTwo + ']';
    }
}
