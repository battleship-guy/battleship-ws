package nl.habiboellah.battleship;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);
    @Override
    protected Principal determineUser(@NonNull ServerHttpRequest request, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        String randomId = UUID.randomUUID().toString();
        LOG.info("User with ID '{}' connected", randomId);

        return new UserPrincipal(randomId);
    }
}
