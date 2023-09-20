package nl.habiboellah.battleship.game;

import nl.habiboellah.battleship.model.Player;
import nl.habiboellah.battleship.model.collection.PlayerList;

import java.util.Optional;

public class Lobby {
    public static final int MAX_PLAYERS = 4;

    private static Lobby lobby;
    private final PlayerList players;
    private final MatchList matches;

    private Lobby(PlayerList players, MatchList matches) {
        this.players = players;
        this.matches = matches;
    }

    public static Lobby getInstance() {
        if (lobby == null) {
            lobby = new Lobby(new PlayerList(), new MatchList());
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
        PLAYER_MATCHED,
        PLAYER_ALREADY_EXISTS,
        LOBBY_FULL;
    }

    public JoinStatus join(Player player) {
        if (players.contains(player)) {
            return JoinStatus.PLAYER_ALREADY_EXISTS;
        } else if(players.size() >= MAX_PLAYERS) {
            return JoinStatus.LOBBY_FULL;
        } else {
            Optional<Player> unmatchedPlayer = findUnmatchedPlayer();
            players.add(player);
            if (unmatchedPlayer.isPresent()) {
                matches.add(new Match(unmatchedPlayer.get(), player));
                return JoinStatus.PLAYER_MATCHED;
            } else {
                return JoinStatus.PLAYER_ENTERED;
            }
        }
    }

    private Optional<Player> findUnmatchedPlayer() {
        return players.stream().filter(p -> !matches.getPlayers().toList().contains(p)).findFirst();
    }

    public Optional<Match> findMatch(Player player) {
        return matches.findMatch(player);
    }
    public Optional<Match> findMatch(String matchId) {
        return matches.findMatch(matchId);
    }

    public Player getPlayerByUserId(String userId) {
        return players.getPlayerByUserId(userId);
    }
}
