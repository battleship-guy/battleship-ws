package nl.habiboellah.battleship.model.mapper;

import nl.habiboellah.battleship.game.Match;
import nl.habiboellah.battleship.model.GameStateResponse;
import nl.habiboellah.battleship.model.Player;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GameStateResponseMapper {
    public GameStateResponse fromMatch(Match match, Player addressee) {
        if(!match.containsPlayer(addressee)) {
            throw new RuntimeException("Player " + addressee.getName() + " is not taking part in given match.");
        }
        boolean yourTurn;
        if (Objects.requireNonNull(match.getMatchPhase()) == Match.Phase.SETUP) {
            yourTurn = true;
        } else {
            yourTurn = addressee.equals(match.getPlayerAtTurn());
        }
        return new GameStateResponse(addressee.getName(), match.getOpponent(addressee).orElseThrow().getName(), yourTurn, match.getMatchPhase());
    }
}
