package view;

import model.Spore;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VSpore implements IIcon {
    private Spore spore;
    private Cell cell;
    private BufferedImage cachedIcon;

    // #region GETTERS SETTERS

    public Spore getSpore() {
        return spore;
    }

    // #endregion

    // #region ICON GENERATION
    private Color getColor() {
        for (VFungus vf : View.getAllFungi()) {
            if (vf.getFungus() == spore.getSpecies()) {
                return vf.getColor();
            }
        }
        throw new IllegalStateException("Fungus not found");
    }

    private BufferedImage sporeIcon() {
        int size = Cell.getSize();
        Color color = getColor();
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        int sporeSize = size / 5;
        int startPos = size / 2 - sporeSize / 2;
        g.setColor(color);
        g.setStroke(new BasicStroke(size * 0.05f));
        g.drawOval(startPos, startPos, sporeSize, sporeSize);
        g.dispose();
        return img;
    }

    @Override
    public Object getIcon() {
        return cachedIcon;
    }

    // #endregion

    public VSpore(Cell cell, Spore spore) {
        this.spore = spore;
        this.cell = cell;
        cell.setItem(this);
        cachedIcon = sporeIcon();
    }

}