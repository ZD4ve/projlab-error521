package view;

import java.util.List;

public class Map {
    private Cell[][] cells;

    public Map(int n, int m) {
        cells = new Cell[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void draw() {
        // Implementation for drawing the map
    }

    public List<Cell> getNeighbors(Cell cell) {
        // Implementation for retrieving neighbors of a cell
        return null;
    }

    public Cell cellAt(int x, int y) {
        if (x >= 0 && x < cells.length && y >= 0 && y < cells[0].length) {
            return cells[x][y];
        }
        return null;
    }
}