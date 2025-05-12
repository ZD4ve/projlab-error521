package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell {
    // #region ASSOCIATIONS
    private IIcon item;
    private VTecton tecton;
    // #endregion

    // #region ATTRIBUTES
    private int x;
    private int y;
    private static int size = -1;
    // #endregion

    // #region CONSTRUCTOR
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // #endregion

    // #region FUNCTIONS
    private void drawEdge(Graphics2D g, Cell n) {
        if (n.getTecton() == tecton)
            return;
        int dx = n.getX() - x;
        int dy = n.getY() - y;
        if (dx == 0) {
            if (dy > 0) // down
                g.drawLine(x, y + size, x + size, y + size);
            else // up
                g.drawLine(x, y, x + size, y);
        } else {
            if (dx > 0) // right
                g.drawLine(x + size, y, x + size, y + size);
            else // left
                g.drawLine(x, y, x, y + size);
        }
    }

    public void draw(Graphics2D g) {
        Color background = tecton.getColor();
        g.setColor(background);
        g.fillRect(x, y, size, size);
        if (item != null) {
            BufferedImage icon = item.getIcon();
            if (icon == null) {
                item = null; // TODO: @Panni return null when dead
            } else {
                g.drawImage(item.getIcon(), x, y, size, size, null);
            }
        }
        java.util.List<Cell> neighbors = View.getMap().getNeighbors(this);
        g.setColor(Color.BLUE); // TODO @Panni background color
        for (Cell neighbor : neighbors) {
            drawEdge(g, neighbor);
        }
        if (View.getSelected() == this) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, size, size);
        }
    }
    // #endregion

    // #region GETTERS-SETTERS
    public static int getSize() {
        return size;
    }

    public static void setSize(int cellSize) {
        if (size != -1)
            throw new IllegalStateException("Cell size is already set.");
        size = cellSize;
    }

    public IIcon getItem() {
        return item;
    }

    public void setItem(IIcon item) {
        this.item = item;
    }

    public VTecton getTecton() {
        return tecton;
    }

    public void setTecton(VTecton tecton) {
        this.tecton = tecton;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    // #endregion
}