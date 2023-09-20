package nl.habiboellah.battleship.model.mapper;

import nl.habiboellah.battleship.model.JoinRequest;
import nl.habiboellah.battleship.model.Player;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class PlayerMapper {
    public Player fromJoinRequest(JoinRequest joinRequest, Principal principal) {
        return new Player(joinRequest.getPlayerName(), principal.getName());
    }
}
