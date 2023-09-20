package nl.habiboellah.battleship.game;

import nl.habiboellah.battleship.model.Player;
import nl.habiboellah.battleship.model.collection.PlayerList;

import java.util.Optional;

public class Lobby {
    public static final int MAX_PLAYERS = 4;

    private static Lobby lobby;
    private final PlayerList players;

    private Lobby(PlayerList players) {
        this.players = players;
    }

    public static Lobby getInstance() {
        if (lobby == null) {
            lobby = new Lobby(new PlayerList());
        }
        return lobby;
    }

    public Player leave(String userId) {
        Player player = players.getPlayerByUserId(userId);
        players.remove(player);
        return player;
    }

    public enum JoinStatus {
        PLAYER_ENTERED,
        PLAYER_LEFT,
        PLAYER_ALREADY_EXISTS,
        LOBBY_FULL;
    }

    public JoinStatus join(Player player) {
        if (players.contains(player)) {
            return JoinStatus.PLAYER_ALREADY_EXISTS;
        } else if(players.size() >= MAX_PLAYERS) {
            return JoinStatus.LOBBY_FULL;
        } else {
            players.add(player);
            return JoinStatus.PLAYER_ENTERED;
        }
    }
}
