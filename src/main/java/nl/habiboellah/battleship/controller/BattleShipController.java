package nl.habiboellah.battleship.controller;

import nl.habiboellah.battleship.WebSocketConfig;
import nl.habiboellah.battleship.model.JoinRequest;
import nl.habiboellah.battleship.model.mapper.PlayerMapper;
import nl.habiboellah.battleship.service.BattleShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Objects;

@Controller
public class BattleShipController {
    private final Logger LOG = LoggerFactory.getLogger(BattleShipController.class);

    private final BattleShipService service;

    private final PlayerMapper playerMapper;

    @Autowired
    public BattleShipController(BattleShipService service, PlayerMapper playerMapper) {
        this.service = service;
        this.playerMapper = playerMapper;
    }

    @MessageMapping("/join")
    public void requestJoin(@RequestBody final JoinRequest joinRequest, Principal principal) throws Exception {
        service.processPlayerJoinRequest(playerMapper.fromJoinRequest(joinRequest, principal));
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String userId = Objects.requireNonNull(sha.getUser()).getName();
        service.processPlayerDisconnect(userId);
        LOG.info("User with ID '{}' closed the connection", userId);
    }
}
