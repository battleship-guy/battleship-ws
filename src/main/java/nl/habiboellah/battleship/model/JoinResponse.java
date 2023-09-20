package nl.habiboellah.battleship.model;

import nl.habiboellah.battleship.game.Lobby;

public class JoinResponse {
    private final Lobby.JoinStatus status;
    private final String playerName;

    public JoinResponse(Lobby.JoinStatus status, String playerName) {
        this.status = status;
        this.playerName = playerName;
    }

    public Lobby.JoinStatus getStatus() {
        return status;
    }

    public String getPlayerName() {
        return playerName;
    }
}
