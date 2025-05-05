package view;

public class Cell {
    private int x;
    private int y;
    private static int size;
    private IIcon item;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        // Implementation for drawing the cell
    }

    public static int getSize() {
        return size;
    }

    public IIcon getItem() {
        return item;
    }

    public void setItem(IIcon item) {
        this.item = item;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}