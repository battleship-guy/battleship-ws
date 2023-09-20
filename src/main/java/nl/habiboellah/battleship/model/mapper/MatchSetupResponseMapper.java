package nl.habiboellah.battleship.model.mapper;

import nl.habiboellah.battleship.game.Match;
import nl.habiboellah.battleship.game.MatchSetupStatus;
import nl.habiboellah.battleship.game.board.Ship;
import nl.habiboellah.battleship.model.MatchSetupResponse;
import nl.habiboellah.battleship.model.Player;
import nl.habiboellah.battleship.model.ShipModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchSetupResponseMapper {
    @Autowired
    private ShipMapper shipMapper;

    public MatchSetupResponse fromMatch(Match match, Player addressee, MatchSetupStatus status) {
        if(!match.containsPlayer(addressee)) {
            throw new RuntimeException("Player " + addressee.getName() + " is not taking part in given match.");
        }
        List<ShipModel> ships = Arrays.stream(Ship.values())
                .map(shipMapper::fromShip)
                .collect(Collectors.toList());

        return new MatchSetupResponse(addressee.getName(), match.getOpponent(addressee).orElseThrow().getName(), match.getId(), status, ships);
    }
}
