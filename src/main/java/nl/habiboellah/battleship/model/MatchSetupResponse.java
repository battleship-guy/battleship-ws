package nl.habiboellah.battleship.model;

import nl.habiboellah.battleship.game.MatchSetupStatus;

public class MatchSetupResponse {
    private final String playerName;

    private final String opponentName;

    private final String matchId;

    private final MatchSetupStatus status;

    public MatchSetupResponse(String playerName, String opponentName, String matchId, MatchSetupStatus status) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.matchId = matchId;
        this.status = status;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getMatchId() {
        return matchId;
    }

    public MatchSetupStatus getStatus() {
        return status;
    }
}
