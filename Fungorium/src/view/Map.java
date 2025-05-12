package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<List<Cell>> cells;

    public Map(int n, int m, int cellSize) {
        Cell.setSize(cellSize);
        cells = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                row.add(new Cell(i * cellSize, j * cellSize));
            }
            cells.add(row);
        }
    }

    public void draw(BufferedImage canvas) {
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.RED); // TODO @Panni background color
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // TODO offset
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                cell.draw(g);
            }
        }
        g.dispose();
    }

    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX() / Cell.getSize();
        int y = cell.getY() / Cell.getSize();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
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

    public Cell cellAt(int x, int y) {
        int size = Cell.getSize();
        x /= size;
        y /= size;
        if (x >= 0 && x < cells.size() && y >= 0 && y < cells.get(0).size()) {
            return cells.get(x).get(y);
        }
        return null;
    }
}