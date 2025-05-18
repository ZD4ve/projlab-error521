package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import model.Mycelium;
import model.Tecton;

/**
 * Becsomagol egy gombafonalnak egyik felét, nyilvántartja annak a helyét és kezeli kirajzolását.
 */
public class VMycelium implements IIcon {
    // #region ASSOCIATIONS
    /** Becsomagolt gombafonal */
    private Mycelium mycelium;
    /** Cella, amin a gombafonal ez a vége található */
    private Cell cell;
    // #endregion

    // #region ATTRIBUTES
    /** Az ikon, amely a gombafonalat reprezentálja */
    private BufferedImage cachedIcon;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új VMycelium példányt, beállítja a cellát és a mycelium referenciát. A cella tartalmát beállítja a
     * VMycelium példányra.
     *
     * @param cell     cella, amin a fonal egyik vége található
     * @param mycelium mycelium
     * @param neighbor szomszédos cella, ahol a fonal másik vége található
     */
    public VMycelium(Cell cell, Mycelium mycelium, Cell neighbor) {
        this.cell = cell;
        this.mycelium = mycelium;
        cell.setItem(this);
        generateIcon(neighbor);
    }
    // #endregion

    /**
     * Visszaadja a gombafonal színét a gombafonal típusának megfelelően.
     * 
     * @return a gombafonal színe
     * @throws IllegalStateException Ha a gombafonal típusa nem található
     */
    private Color getColor() {
        for (VFungus vf : View.getAllFungi()) {
            if (vf.getFungus() == mycelium.getSpecies()) {
                return vf.getColor();
            }
        }
        throw new IllegalStateException("Fungus not found");
    }

    /**
     * Legenerálja a gombafonal ikonját a szomszédos cella alapján.
     * 
     * @param neighbor szomszédos cella
     */
    private void generateIcon(Cell neighbor) {
        int dx = neighbor.getX() - cell.getX();
        int dy = neighbor.getY() - cell.getY();
        int orientation = (dx == 0) ? (dy > 0 ? 1 : 3) : (dx > 0 ? 0 : 2); // NOSONAR right, down, left, up
        int size = Cell.SIZE;
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

    /**
     * Visszaadja a gombafonal ikonját. Ha a gombafonalnak nincs vége, akkor null-t ad vissza.
     * @return a gombafonal ikonját, vagy null-t, ha az egyik vége hiányzik
     */
    @Override
    public BufferedImage getIcon() {
        Tecton[] ends = mycelium.getEnds();
        if (ends[0] == null && ends[1] == null)
            return null;
        return cachedIcon;
    }

    // #region GETTERS-SETTERS

    /**
     * Visszaadja a becsomagolt gombafonalat.
     * 
     * @return a hozzá tartozó, becsomagolt gombafonal
     */
    public Mycelium getMycelium() {
        return mycelium;
    }
    // #endregion

}