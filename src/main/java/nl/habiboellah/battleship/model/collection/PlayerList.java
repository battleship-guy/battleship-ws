package nl.habiboellah.battleship.model.collection;

import nl.habiboellah.battleship.model.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerList extends ArrayList<Player> {
    public PlayerList(Player... players) {
        this.addAll(Arrays.asList(players));
    }

    public boolean contains(Player player) {
        return this.stream()
                .map(p -> p.equals(player))
                .reduce(Boolean::logicalOr)
                .orElse(Boolean.FALSE);
    }

    public boolean add(Player player) {
        if(this.contains(player)) {
            return false;
        }
        return super.add(player);
    }
}
