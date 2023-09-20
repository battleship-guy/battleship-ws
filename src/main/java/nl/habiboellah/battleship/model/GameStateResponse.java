package nl.habiboellah.battleship.model;

import nl.habiboellah.battleship.game.Match;

public class GameStateResponse {
    private final String playerName;

    private final String opponentName;

    private final boolean yourTurn;

    private final Match.Phase matchPhase;

    public GameStateResponse(String playerName, String opponentName, boolean yourTurn, Match.Phase matchPhase) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.yourTurn = yourTurn;
        this.matchPhase = matchPhase;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public Match.Phase getMatchPhase() {
        return matchPhase;
    }
}
