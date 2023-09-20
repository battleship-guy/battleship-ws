package nl.habiboellah.battleship.model;

import nl.habiboellah.battleship.game.MatchSetupStatus;

import java.util.List;

public class MatchSetupResponse {
    private final String playerName;

    private final String opponentName;

    private final String matchId;

    private final MatchSetupStatus status;

    private final List<ShipModel> unplacedShips;

    public MatchSetupResponse(String playerName, String opponentName, String matchId, MatchSetupStatus status, List<ShipModel> unplacedShips) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.matchId = matchId;
        this.status = status;
        this.unplacedShips = unplacedShips;
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

    public List<ShipModel> getUnplacedShips() {
        return unplacedShips;
    }
}
