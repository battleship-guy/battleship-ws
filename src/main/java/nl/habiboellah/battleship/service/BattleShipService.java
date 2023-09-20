package nl.habiboellah.battleship.service;

import nl.habiboellah.battleship.game.Lobby;
import nl.habiboellah.battleship.model.ControlMessage;
import nl.habiboellah.battleship.model.JoinResponse;
import nl.habiboellah.battleship.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static nl.habiboellah.battleship.model.ControlMessage.EventType.LOBBY_UPDATE;

@Service
public class BattleShipService {
    @Autowired
    private ControlMessageService controlMessageService;

    public void processPlayerJoinRequest(Player player) {
        Lobby lobby = Lobby.getInstance();
        Lobby.JoinStatus status = lobby.join(player);
        JoinResponse response = new JoinResponse(status, player.getName());
        switch (status) {
            case PLAYER_ENTERED -> {
                controlMessageService.broadcastSystemMessage(new ControlMessage(LOBBY_UPDATE, response));
            }
            case PLAYER_ALREADY_EXISTS, LOBBY_FULL -> {
                controlMessageService.sendSystemMessageToUser(player.getId(), new ControlMessage(LOBBY_UPDATE, response));
            }
        }
    }

}
