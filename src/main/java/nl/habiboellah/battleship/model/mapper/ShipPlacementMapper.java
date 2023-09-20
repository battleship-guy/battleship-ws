package nl.habiboellah.battleship.model.mapper;

import nl.habiboellah.battleship.game.board.Coordinate;
import nl.habiboellah.battleship.game.board.Direction;
import nl.habiboellah.battleship.game.board.Ship;
import nl.habiboellah.battleship.game.board.ShipPlacement;
import nl.habiboellah.battleship.model.ShipPlacementRequest;
import org.springframework.stereotype.Component;

@Component
public class ShipPlacementMapper {
    public ShipPlacement fromShipPlacementRequest(ShipPlacementRequest request) {
        Ship ship =  Ship.getById(request.getShipId());
        Coordinate coordinate = new Coordinate(request.getPosition());
        Direction direction = Direction.getDirectionByValue(request.getDirection());

        return new ShipPlacement(ship, coordinate, direction);
    }
}
