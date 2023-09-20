package nl.habiboellah.battleship.game.board;

public class Coordinate {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 9;
    private static final int CHAR_A_OFFSET = 65;


    private final int x;
    private final int y;

    public Coordinate(String coordinate) {
        this((int) coordinate.charAt(0) - CHAR_A_OFFSET,
                Integer.parseInt(coordinate.substring(1)) - 1);
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;

        if (x< MIN_X || x> MAX_X || y< MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException("Invalid coordinate");
        }
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String toString() {
        char x = (char)(getX() + CHAR_A_OFFSET);
        String y = "" + (getY()+1);
        return x+y;
    }
}
