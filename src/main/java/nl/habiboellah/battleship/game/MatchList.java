package nl.habiboellah.battleship.game;

import nl.habiboellah.battleship.model.Player;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class MatchList extends ArrayList<Match> {
    public Stream<Player> getPlayers() {
        return this.stream().flatMap(m -> m.getPlayers().stream());
    }

    public Optional<Player> findOpponent(Player player) {
        return this.stream()
                .filter(match-> match.containsPlayer(player))
                .map(match -> match.getOpponent(player))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    public Optional<Match> findMatch(String matchId) {
        return this.stream()
                .filter(match -> match.getId().equals(matchId))
                .findFirst();
    }

    public Optional<Match> findMatch(Player player) {
        return this.stream()
                .filter(match -> match.containsPlayer(player))
                .findFirst();
    }
}
