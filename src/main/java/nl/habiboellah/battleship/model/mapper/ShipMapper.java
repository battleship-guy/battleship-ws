package nl.habiboellah.battleship.model.mapper;

import nl.habiboellah.battleship.game.board.Ship;
import nl.habiboellah.battleship.model.ShipModel;
import org.springframework.stereotype.Component;

@Component
public class ShipMapper {
    public ShipModel fromShip(Ship ship) {
        return new ShipModel(ship.ordinal(), ship.getName(), ship.getLength().toString());
    }
}
