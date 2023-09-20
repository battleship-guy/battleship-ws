package nl.habiboellah.battleship.model;

public class JoinRequest {
    private String playerName;

    public JoinRequest() {

    }

    public JoinRequest(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}