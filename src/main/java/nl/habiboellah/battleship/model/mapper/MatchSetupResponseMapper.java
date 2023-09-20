package nl.habiboellah.battleship.model.mapper;

import nl.habiboellah.battleship.game.Match;
import nl.habiboellah.battleship.game.MatchSetupStatus;
import nl.habiboellah.battleship.model.MatchSetupResponse;
import nl.habiboellah.battleship.model.Player;
import org.springframework.stereotype.Component;

@Component
public class MatchSetupResponseMapper {
    public MatchSetupResponse fromMatch(Match match, Player addressee, MatchSetupStatus status) {
        if(!match.containsPlayer(addressee)) {
            throw new RuntimeException("Player " + addressee.getName() + " is not taking part in given match.");
        }
        return new MatchSetupResponse(addressee.getName(), match.getOpponent(addressee).orElseThrow().getName(), match.getId(), status);
    }
}
