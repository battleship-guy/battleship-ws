package nl.habiboellah.battleship.service;

import nl.habiboellah.battleship.game.Lobby;
import nl.habiboellah.battleship.game.Match;
import nl.habiboellah.battleship.game.board.ShipPlacement;
import nl.habiboellah.battleship.model.*;
import nl.habiboellah.battleship.model.mapper.MatchSetupResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static nl.habiboellah.battleship.game.Lobby.JoinStatus.PLAYER_ENTERED;
import static nl.habiboellah.battleship.game.Lobby.JoinStatus.PLAYER_LEFT;
import static nl.habiboellah.battleship.game.MatchSetupStatus.*;
import static nl.habiboellah.battleship.model.ControlMessage.EventType.*;

@Service
public class BattleShipService {
    @Autowired
    private ControlMessageService controlMessageService;

    @Autowired
    private MatchSetupResponseMapper matchSetupResponseMapper;

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
                MatchSetupResponse response = matchSetupResponseMapper.fromMatch(match, player, MATCH_STARTED);
                controlMessageService.sendSystemMessageToUser(player.getId(), new ControlMessage(MATCH_SETUP_UPDATE, response));
                Player opponent = match.getOpponent(player).orElseThrow();
                MatchSetupResponse opponentResponse = matchSetupResponseMapper.fromMatch(match, opponent, MATCH_STARTED);
                controlMessageService.sendSystemMessageToUser(opponent.getId(), new ControlMessage(MATCH_SETUP_UPDATE, opponentResponse));
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

    public void processShipPlacementRequest(String matchId, String playerId, ShipPlacement shipPlacement) {
        Lobby lobby = Lobby.getInstance();
        Player player = lobby.getPlayerByUserId(playerId);
        Match matchSpecifiedInRequest;
        try {
            matchSpecifiedInRequest = lobby.findMatch(matchId).orElseThrow();
            if(!matchSpecifiedInRequest.containsPlayer(player)) {
                controlMessageService.sendSystemMessageToUser(playerId, new ControlMessage(ERROR, new SimpleResponse("Player " + player.getName() + " not found in specified match")));
                return;
            }
        } catch (NoSuchElementException e) {
            controlMessageService.sendSystemMessageToUser(playerId, new ControlMessage(ERROR, new SimpleResponse("Match does not exist (any longer)")));
            return;
        }

        boolean placedShip = matchSpecifiedInRequest.getBoard().placeShip(player, shipPlacement);

        MatchSetupResponse response = matchSetupResponseMapper.fromMatch(matchSpecifiedInRequest, player, placedShip ? VALID_SHIP_PLACEMENT : INVALID_SHIP_PLACEMENT);
        controlMessageService.sendSystemMessageToUser(player.getId(), new ControlMessage(MATCH_SETUP_UPDATE, response));
    }
}
