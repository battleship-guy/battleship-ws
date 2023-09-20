package nl.habiboellah.battleship.service;

import nl.habiboellah.battleship.game.Lobby;
import nl.habiboellah.battleship.game.Match;
import nl.habiboellah.battleship.model.ControlMessage;
import nl.habiboellah.battleship.model.GameStateResponse;
import nl.habiboellah.battleship.model.JoinResponse;
import nl.habiboellah.battleship.model.Player;
import nl.habiboellah.battleship.model.mapper.GameStateResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static nl.habiboellah.battleship.game.Lobby.JoinStatus.PLAYER_ENTERED;
import static nl.habiboellah.battleship.game.Lobby.JoinStatus.PLAYER_LEFT;
import static nl.habiboellah.battleship.model.ControlMessage.EventType.GAME_STATE_UPDATE;
import static nl.habiboellah.battleship.model.ControlMessage.EventType.LOBBY_UPDATE;

@Service
public class BattleShipService {
    @Autowired
    private ControlMessageService controlMessageService;

    @Autowired
    private GameStateResponseMapper gameStateResponseMapper;

    public void processPlayerJoinRequest(Player player) {
        Lobby lobby = Lobby.getInstance();
        Lobby.JoinStatus status = lobby.join(player);
        switch (status) {
            case PLAYER_ENTERED -> {
                JoinResponse response = new JoinResponse(status, player.getName());
                controlMessageService.broadcastSystemMessage(new ControlMessage(LOBBY_UPDATE, response));
            }
            case PLAYER_MATCHED -> {
                JoinResponse joinNotice = new JoinResponse(PLAYER_ENTERED, player.getName());
                controlMessageService.broadcastSystemMessage(new ControlMessage(LOBBY_UPDATE, joinNotice));

                Match match = lobby.findMatch(player).orElseThrow();
                GameStateResponse response = gameStateResponseMapper.fromMatch(match, player);
                controlMessageService.sendSystemMessageToUser(player.getId(), new ControlMessage(GAME_STATE_UPDATE, response));
                Player opponent = match.getOpponent(player).orElseThrow();
                GameStateResponse opponentResponse = gameStateResponseMapper.fromMatch(match, opponent);
                controlMessageService.sendSystemMessageToUser(opponent.getId(), new ControlMessage(GAME_STATE_UPDATE, opponentResponse));
            }
            case PLAYER_ALREADY_EXISTS, LOBBY_FULL -> {
                JoinResponse response = new JoinResponse(status, player.getName());
                controlMessageService.sendSystemMessageToUser(player.getId(), new ControlMessage(LOBBY_UPDATE, response));
            }
        }
    }

    public void processPlayerDisconnect(String userId) {
        Lobby lobby = Lobby.getInstance();
        Player player = lobby.leave(userId);
        JoinResponse response = new JoinResponse(PLAYER_LEFT, player.getName());
        controlMessageService.broadcastSystemMessage(new ControlMessage(LOBBY_UPDATE, response));
    }
}
