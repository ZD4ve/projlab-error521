package view;

import model.Spore;

import java.awt.*;
import java.awt.image.BufferedImage;

//TODO DOC maradék (get-set kívételével) @Panni

public class VSpore implements IIcon {
    // #region ASSOCIATIONS
    /** Becsomagolt spóra */
    private Spore spore;
    /** Cella, amin a spóra tartózkodik */
    private Cell cell;
    // #endregion

    // #region ATTRIBUTES
    private BufferedImage cachedIcon;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új VSpore példányt, beállítja a cellát és a spóra referenciát. A cella tartalmát beállítja a VSpore
     * példányra.
     *
     * @param cell  cella, amire a spórát tesszük
     * @param spore spóra
     */
    public VSpore(Cell cell, Spore spore) {
        this.spore = spore;
        this.cell = cell;
        cell.setItem(this);
        cachedIcon = sporeIcon();
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
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.setStroke(new BasicStroke(size * 0.05f));
        g.drawOval(startPos, startPos, sporeSize, sporeSize);
        g.dispose();
        return img;
    }

    @Override
    public BufferedImage getIcon() {
        if (!cell.getTecton().getTecton().getSpores().contains(spore)) {
            return null;
        }
        return cachedIcon;
    }
    // #endregion

    // #region GETTERS SETTERS
    public Spore getSpore() {
        return spore;
    }
    // #endregion
}