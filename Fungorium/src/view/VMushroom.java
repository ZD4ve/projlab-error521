package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import model.Mushroom;
import model.Tecton;

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
    /** A gombatesthez tartozó ikon */
    private BufferedImage cachedIcon;

    /** Ellenőrző boolean, hogy a gombatest fejlett állapotban van-e */
    private boolean isCachedGrown = false;
    // #endregion

    // #region ICON GENERATION

    /**
     * Visszaadja a gombatest színét a gombatest típusának megfelelően.
     *
     * @return a gombatest színe
     * @throws IllegalStateException Ha a gombatest típusa nem található
     */
    private Color getColor() {
        for (VFungus vf : View.getAllFungi()) {
            if (vf.getFungus() == mushroom.getSpecies()) {
                return vf.getColor();
            }
        }
        throw new IllegalStateException("Fungus not found");
    }

    /**
     * Megrajzolja a fejletlen gombatest ikonját.
     *
     * @return a fejletlen gombatest ikonja
     */
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

    /** 
     * Megrajzolja a fejlett gombatest ikonját.
     *
     * @return a fejlett gombatest ikonja
     */
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

    /**
     * Visszaadja a gombatest ikonját. Ha a gombatestnek nincs helye, akkor null-t ad vissza.
     * 
     * @return a gombatest ikonját, vagy null-t, ha a gombatestnek nincs helye
     */
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

    /**
     * Visszaadja a gombatestet.
     * 
     * @return a gombatest
     */
    public Mushroom getMushroom() {
        return mushroom;
    }

    /**
     * Visszaadja a cellát, amin a gombatest található.
     * @return a cella
     */
    public Cell getCell() {
        return cell;
    }
    // #endregion
}