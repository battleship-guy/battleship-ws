package nl.habiboellah.battleship.game.board;

public abstract class Grid<T> {
    protected static final int DEFAULT_WIDTH = 10;
    protected static final int DEFAULT_HEIGHT = 10;
    protected final int width;
    protected final int height;

    protected final T[][] grid;

    public Grid(T defaultContent) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, defaultContent);
    }
    @SuppressWarnings("unchecked")
    public Grid(int width, int height, T defaultContent) {
        this.width = width;
        this.height = height;
        this.grid = (T[][]) new Object[width][height];
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                grid[x][y] = defaultContent;
            }
        }
    }

    public void setCell(int x, int y, T value) {
        if (x<0 || y< 0 || x>=getWidth() || y >= getHeight()) {
            throw new IllegalArgumentException("Provided coordinate is out of bounds");
        }
        grid[x][y] = value;
    }


    public T getCell(int x, int y) {
        if (x<0 || y< 0 || x>=getWidth() || y >= getHeight()) {
            throw new IllegalArgumentException("Provided coordinate is out of bounds");
        }
        return grid[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
