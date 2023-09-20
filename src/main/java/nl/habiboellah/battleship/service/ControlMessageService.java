package nl.habiboellah.battleship.service;

import nl.habiboellah.battleship.model.ControlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ControlMessageService {
    public static final String TOPIC = "/topic/control-messages";
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ControlMessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastSystemMessage(ControlMessage msg) {
        messagingTemplate.convertAndSend(TOPIC, msg);
    }

    public void sendSystemMessageToUser(String userId, ControlMessage msg) {
        messagingTemplate.convertAndSendToUser(userId, TOPIC, msg);
    }
}
