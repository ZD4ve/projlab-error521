package view;

import model.Mushroom;
import model.Tecton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VMushroom implements IIcon {
    private Mushroom mushroom;
    private Cell cell;
    private BufferedImage cachedIcon;
    private boolean isCachedGrown = false;

    // #region GETTERS SETTERS

    public Mushroom getMushroom() {
        return mushroom;
    }

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
        int size = Cell.getSize();
        Color color = getColor();
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillArc(size / 8, size / 4, size * 3 / 4, size * 3 / 4, 0, 180); // cap
        g.fillRect(size * 3 / 8, 5 * size / 8, size / 4, size / 8); // small trunk
        g.dispose();
        return img;
    }

    private BufferedImage mushroomBigIcon() {
        int size = Cell.getSize();
        Color color = getColor();
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillArc(size / 8, size / 8, size * 3 / 4, size * 3 / 4, 0, 180); // cap
        g.fillRect(size * 3 / 8, size / 2, size / 4, size / 3); // large trunk
        g.dispose();
        return img;
    }

    @Override
    public Object getIcon() {
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

    public VMushroom(Cell cell, Mushroom mushroom) {
        this.mushroom = mushroom;
        this.cell = cell;
        cell.setItem(this);
        cachedIcon = mushroomSmallIcon();
    }

    public void burst(Cell target) {
        Tecton tecton = target.getTecton().getTecton();
        boolean success = mushroom.burstSpore(tecton);
        if (success) {
            new VSpore(target, tecton.getSpores().get(tecton.getSpores().size() - 1));
        } else {
            View.notifyUser();
        }
    }
}