package view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A térképet reprezentáló osztály, amely cellákból áll és kezeli azokat.
 */
public class VMap {
    // #region ASSOCIATIONS
    /** Két-dimenziós lista a cellák tárolására */
    private List<List<Cell>> cells;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Konstruktor, amely létrehozza a megadott méretű térképet és inicializálja a cellákat.
     *
     * @param n sorok száma
     * @param m oszlopok száma
     * @throws IllegalStateException Ha a cellák mérete még nincs beállítva
     */
    public VMap(int n, int m) {
        if (Cell.getSize() == -1) {
            throw new IllegalStateException("Cell size must be set before creating a map.");
        }
        cells = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                row.add(new Cell(i * Cell.getSize(), j * Cell.getSize()));
            }
            cells.add(row);
        }
    }
    // #endregion

    // #region FUNCTIONS
    /**
     * Kirajzolja a térképet a megadott Graphics2D objektumra.
     *
     * @param g a Graphics2D objektum, amelyre a térképet kirajzoljuk
     */
    public void draw(Graphics2D g) {
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                cell.draw(g);
            }
        }
    }

    /**
     * Visszaadja egy cella szomszédait. Akkor szomszédos két cella, ha van közös élük.
     *
     * @param cell a cella, amelynek szomszédait keressük
     * @return a cella szomszédai
     */
    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX() / Cell.getSize();
        int y = cell.getY() / Cell.getSize();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (Math.abs(i) == Math.abs(j))
                    continue;
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < cells.size() && newY >= 0 && newY < cells.get(0).size()) {
                    neighbors.add(cells.get(newX).get(newY));
                }
            }
        }
        return neighbors;
    }

    /**
     * Visszaadja a cellát ami a megadott képernyő koordinátákon található.
     *
     * @param x pont x koordinátája
     * @param y pont y koordinátája
     * @return a cella, ha van ezen a ponton, különben null
     */
    public Cell cellAt(int x, int y) {
        int size = Cell.getSize();
        x /= size;
        y /= size;
        if (x >= 0 && x < cells.size() && y >= 0 && y < cells.get(0).size()) {
            return getCellByIndex(x, y);
        }
        return null;
    }

    // #endregion
    // #region GETTERS-SETTERS
    public Cell getCellByIndex(int col, int row) {
        return cells.get(col).get(row);
    }
    // #endregion
}