package nl.habiboellah.battleship.game.board;

public class ShipPlacementGrid extends Grid<Cell> {
    public ShipPlacementGrid() {
        super(Cell.WATER);
    }
    public ShipPlacementGrid(int width, int height, Cell defaultContent) {
        super(width, height, defaultContent);
    }

    public boolean placeShip(ShipPlacement shipPlacement) {
        Coordinate coordinate = shipPlacement.getCoordinate();
        return this.placeShip(coordinate.getX(), coordinate.getY(), shipPlacement.getDirection(), shipPlacement.getShip().getLength());
    }

    public boolean placeShip(int x, int y, Direction direction, int length) {
        if (gridHasRoomForShip(x, y, direction, length)) {
            markShipInGrid(x, y, direction, length);
            return true;
        }
        return false;
    }

    private void markShipInGrid(int x, int y, Direction direction, int length) {
        int counter = 0;
        while(counter< length) {
            if (direction == Direction.HORIZONTAL) {
                setCell(x + counter, y, Cell.PIECE_OF_SHIP);
            } else {
                setCell(x, y + counter, Cell.PIECE_OF_SHIP);
            }
            counter++;
        }
    }

    private boolean gridHasRoomForShip(int x, int y, Direction direction, int length) {
        boolean gridHasRoomForShip = true;
        int counter = 0;
        while(counter<length) {
            if (direction == Direction.HORIZONTAL) {
                gridHasRoomForShip = gridHasRoomForShip && cellHasRoomForShip(x + counter, y);
            } else {
                gridHasRoomForShip = gridHasRoomForShip && cellHasRoomForShip(x, y + counter);
            }
            counter++;
        }
        return gridHasRoomForShip;
    }

    private boolean cellHasRoomForShip(int x, int y) {
        boolean isOk = x>=0 && x<getWidth() && y >=0 && y<getHeight();
        for (int checkX=x-1; checkX<=x+1; checkX++) {
            for (int checkY=y-1; checkY<=y+1; checkY++) {
                isOk = isOk &&
                        ( checkX < 0
                        || checkY < 0
                        || checkX == getWidth()
                        || checkY == getHeight()
                        || getCell(checkX,checkY).equals(Cell.WATER)
                        );
            }
        }
        return isOk;
    }
}
