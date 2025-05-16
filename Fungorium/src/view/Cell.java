package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell {
    // #region ASSOCIATIONS
    /** Becsomagolt modell objektum a Cellán */
    private IIcon item;
    /** Melyik Tektonhoz tartozik a Cella */
    private VTecton tecton;
    // #endregion

    // #region ATTRIBUTES
    /** X koordináta a képernyőn */
    private int x;
    /** Y koordináta a képernyőn */
    private int y;
    // #endregion

    // #region CONSTANTS
    /** Cellák mérete a képernyőn */
    public static final int SIZE = 256;
    // #endregion

    // #region CONSTRUCTOR
    /** Konstruál egy Cellát a megadott képernyőbeli koordinátákkal. */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // #endregion

    // #region FUNCTIONS
    /** Segédfüggvény a cella határainak kirajzolására */
    private void drawEdge(Graphics2D g, Cell n) {
        if (n.getTecton() == tecton)
            return;
        int dx = n.getX() - x;
        int dy = n.getY() - y;
        if (dx == 0) {
            if (dy > 0) // down
                g.drawLine(x, y + SIZE, x + SIZE, y + SIZE);
            else // up
                g.drawLine(x, y, x + SIZE, y);
        } else {
            if (dx > 0) // right
                g.drawLine(x + SIZE, y, x + SIZE, y + SIZE);
            else // left
                g.drawLine(x, y, x, y + SIZE);
        }
    }

    /** Kirajzolja a cellát a megadott Graphics2D objektumra a megfelelő helyre */
    public void draw(Graphics2D g) {
        Color background = tecton.getColor();
        g.setColor(background);
        g.fillRect(x, y, SIZE, SIZE);
        g.setStroke(new BasicStroke(SIZE * 0.02f));
        g.setColor(new Color(220, 220, 220, 255));
        g.drawRect(x, y, SIZE, SIZE);
        if (item != null) {
            BufferedImage icon = item.getIcon();
            if (icon == null) {
                item = null;
            } else {
                g.drawImage(item.getIcon(), x, y, SIZE, SIZE, null);
            }
        }
        java.util.List<Cell> neighbors = View.getMap().getNeighbors(this);
        g.setColor(View.getBackgroundColor());
        for (Cell neighbor : neighbors) {
            drawEdge(g, neighbor);
        }
        int strokeSize = (int) (SIZE * 0.05f);
        g.setStroke(new BasicStroke(strokeSize));
        if (View.getSelected() == this) {
            g.setColor(Color.BLACK);
            g.drawRect(x + strokeSize / 2, y + strokeSize / 2, SIZE - strokeSize, SIZE - strokeSize);
        }
    }
    // #endregion

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