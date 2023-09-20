package nl.habiboellah.battleship.game.board;

import nl.habiboellah.battleship.model.Player;

import java.util.*;

public class Board {
    Map<Player, ShipPlacementGrid> shipPlacementGrids;
    Map<Player, List<Ship>> shipsToBePlaced;

    public Board(Player playerOne, Player playerTwo) {
        shipPlacementGrids = new HashMap<>();
        shipPlacementGrids.put(playerOne, new ShipPlacementGrid());
        shipPlacementGrids.put(playerTwo, new ShipPlacementGrid());

        shipsToBePlaced = new HashMap<>();
        shipsToBePlaced.put(playerOne, Arrays.asList(Ship.values()));
        shipsToBePlaced.put(playerTwo, Arrays.asList(Ship.values()));
    }

    public List<Ship> getShipsToBePlacedByPlayer(Player player) {
        return shipsToBePlaced.get(player);
    }

    public boolean placeShip(Player player, ShipPlacement shipPlacement) {
        List<Ship> shipsToBePlacedByPlayer = getShipsToBePlacedByPlayer(player);
        Optional<Ship> ship = shipsToBePlacedByPlayer
                .stream()
                .filter(s -> shipPlacement.getShip().equals(s))
                .findFirst();

        if (ship.isPresent()) {
            boolean placedShip = shipPlacementGrids.get(player).placeShip(shipPlacement);
            if (placedShip) {
                List<Ship> updatedShipList = shipsToBePlacedByPlayer.stream()
                        .filter(s -> !s.equals(ship.get()))
                        .toList();
                shipsToBePlaced.put(player, updatedShipList);
            }
            return placedShip;
        } else {
            return false;
        }
    }
}
