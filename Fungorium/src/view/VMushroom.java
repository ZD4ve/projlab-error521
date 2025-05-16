package view;

import model.Mushroom;
import model.Tecton;

import java.awt.*;
import java.awt.image.BufferedImage;

//TODO DOC maradék (get-set kívételével) @Panni

/**
 * Becsomagol egy gombatestet, nyilvántartja annak a helyét és kezeli kirajzolását. A gombatest akcióit elérhetővé teszi
 * cellákat használva.
 */
public class VMushroom implements IIcon {
    // #region ASSOCIATIONS
    /** Becsomagolt gombatest */
    private Mushroom mushroom;
    /** Cella, amin a gombatest tartózkodik */
    private Cell cell;
    // #endregion

    // #region ATTRIBUTES
    private BufferedImage cachedIcon;
    private boolean isCachedGrown = false;
    // #endregion

    // #region ICON GENERATION
    private Color getColor() {
        for (VFungus vf : View.getAllFungi()) {
            if (vf.getFungus() == mushroom.getSpecies()) {
                return vf.getColor();
            }
        }
        throw new IllegalStateException("Fungus not found");
    }

    private BufferedImage mushroomSmallIcon() {
        int size = Cell.SIZE;
        Color color = getColor();
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillArc(size / 8, size / 4, size * 3 / 4, size * 3 / 4, 0, 180); // cap
        g.fillRect(size * 3 / 8, 5 * size / 8 - 1, size / 4, size / 8); // small trunk
        // -1 at y coordinate is to avoid gap
        g.dispose();
        return img;
    }

    private BufferedImage mushroomBigIcon() {
        int size = Cell.SIZE;
        Color color = getColor();
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillArc(size / 8, size / 8, size * 3 / 4, size * 3 / 4, 0, 180); // cap
        g.fillRect(size * 3 / 8, size / 2 - 1, size / 4, size / 3); // large trunk
        // -1 at y coordinate is to avoid gap
        g.dispose();
        return img;
    }

    @Override
    public BufferedImage getIcon() {
        if (mushroom.getLocation() == null)
            return null;
        if (mushroom.getIsGrown() != isCachedGrown) {
            if (mushroom.getIsGrown()) {
                cachedIcon = mushroomBigIcon();
            } else {
                cachedIcon = mushroomSmallIcon();
            }
            isCachedGrown = mushroom.getIsGrown();
        }
        return cachedIcon;
    }
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új VMushroom példányt, beállítja a cellát és a gombatest referenciát. A cella tartalmát beállítja a
     * VMushroom példányra.
     *
     * @param cell   cella, amin a gombatest van
     * @param insect gombatest
     */
    public VMushroom(Cell cell, Mushroom mushroom) {
        this.mushroom = mushroom;
        this.cell = cell;
        cell.setItem(this);
        cachedIcon = mushroomSmallIcon();
    }
    // #endregion

    // #region ACTIONS
    /**
     * Megpróbál spórát szórni a gombatestből a cellára. Ha sikerül, létrehoz egy új VSpore példányt a cellán. Ha nem,
     * akkor értesíti a felhasználót.
     * 
     * @param target cella, amire a spórát szórni szeretnénk
     * @see Mushroom#burstSpore(Tecton)
     */
    public void burst(Cell target) {
        Tecton tecton = target.getTecton().getTecton();
        boolean success = mushroom.burstSpore(tecton);
        if (success) {
            new VSpore(target, tecton.getSpores().get(tecton.getSpores().size() - 1));
        } else {
            View.notifyUser();
        }
    }
    // #endregion

    // #region GETTERS SETTERS
    public Mushroom getMushroom() {
        return mushroom;
    }

    public Cell getCell() {
        return cell;
    }
    // #endregion
}