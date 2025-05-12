package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.*;

import model.Mycelium;

public class VMycelium implements IIcon {
    private Mycelium mycelium;
    private Cell cell;
    private BufferedImage cachedIcon;

    public VMycelium(Cell cell, Mycelium mycelium, Cell neighbor) {
        this.cell = cell;
        this.mycelium = mycelium;
        cell.setItem(this);
        generateIcon(neighbor);
        // neighbor az ikon miatt lasz majd hasznos
    }

    private Color getColor() {
        for (VFungus vf : View.getAllFungi()) {
            if (vf.getFungus() == mycelium.getSpecies()) {
                return vf.getColor();
            }
        }
        throw new IllegalStateException("Fungus not found");
    }

    private void generateIcon(Cell neighbor) {
        int dx = neighbor.getX() - cell.getX();
        int dy = neighbor.getY() - cell.getY();
        int orientation = (dx == 0) ? (dy > 0 ? 1 : 3) : (dx > 0 ? 0 : 2); // right, down, left, up
        int size = Cell.getSize();
        Color color = getColor();
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.rotate(Math.PI / 2 * orientation, size / 2.0, size / 2.0);
        g.setStroke(new BasicStroke(size * 0.25f));
        g.drawLine(size / 2, size / 2, size, size / 2);
        g.rotate(-Math.PI / 2 * orientation, size / 2.0, size / 2.0);
        g.dispose();
        cachedIcon = img;
    }

    @Override
    public BufferedImage getIcon() {
        return cachedIcon;
    }

    public Mycelium getMycelium() {
        return mycelium;
    }

}