package view;

import model.AntiChewEffect;
import model.Insect;
import model.InsectEffect;
import model.ParalysingEffect;
import model.SpeedEffect;

import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Becsomagol egy rovart, nyilvántartja annak a helyét és kezeli kirajzolását. A rovar akcióit elérhetővé teszi cellákat
 * használva.
 */
public class VInsect implements IIcon {
    // #region ASSOCIATIONS
    /** Becsomagolt rovar */
    private Insect insect;
    /** Cella, amin a rovar tartózkodik */
    private Cell cell;
    // #endregion

    // #region ATTRIBUTES
    private BufferedImage cachedIcon;
    private BufferedImage antiChewImage;
    private BufferedImage paraImage;
    private BufferedImage speedImage;
    private BufferedImage slowImage;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új VInsect példányt, beállítja a cellát és a rovar referenciát. A cella tartalmát beállítja a
     * VInsect példányra.
     *
     * @param cell   cella, amin a rovar tartózkodik
     * @param insect rovar
     */
    public VInsect(Cell cell, Insect insect) {
        this.cell = cell;
        this.insect = insect;
        cell.setItem(this);
        generateImages();
    }
    // #endregion

    // #region ICON GENERATION
    private Color getColor() {
        for (VColony vc : View.getAllColonies()) {
            if (vc.getColony() == insect.getColony()) {
                return vc.getColor();
            }
        }
        throw new IllegalStateException("Colony not found");
    }

    /**
     * Megrajzolja a rovar ikonját.
     *
     * @param size  az ikon mérete
     * @param color az ikon színe
     */
    private BufferedImage insectIcon(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int d = size / 4;
        int y = size / 2 - d / 2;
        int startX = d / 2;
        g.fillOval(startX, y, d, d); // left
        g.fillOval(startX + d, y, d, d); // middle
        g.fillOval(startX + 2 * d, y, d, d); // right
        g.dispose();
        return img;
    }

    /**
     * Megrajzolja a rágást tiltó hatás ikonját.
     *
     * @param size  az ikon mérete
     * @param color az ikon színe
     */
    private BufferedImage antiChewIcon(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setStroke(new BasicStroke(size * 0.05f));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double angle = Math.PI / 8;
        g.scale(1.8, 1.8);
        g.translate(-size / 4.0, -size / 1000.0);
        g.rotate(-Math.PI / 2 - angle, size / 2.0, size / 2.0);
        g.fillArc(size / 2, size / 2 - size / 8, size / 2, size / 4, 0, 180);
        g.rotate(Math.PI + 2 * angle, size / 2.0, size / 2.0);
        g.fillArc(0, size / 2 - size / 8, size / 2, size / 4, 0, 180);
        g.rotate(-Math.PI / 2 - angle, size / 2.0, size / 2.0);
        g.translate(size / 4.0, size / 1000.0);
        g.scale(1 / 1.8, 1 / 1.8);
        g.setStroke(new BasicStroke(size * 0.08f));
        g.drawLine(0, size * 4 / 5, size, 0); // cross-out
        g.dispose();
        return img;
    }

    /**
     * Megrajzolja a bénító hatás ikonját.
     *
     * @param size  az ikon mérete
     * @param color az ikon színe
     */
    private BufferedImage paraIcon(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("SansSerif", Font.BOLD, size * 2 / 3));
        g.drawString("Z", size / 5, size * 4 / 5);
        g.setFont(new Font("SansSerif", Font.BOLD, size / 2));
        g.drawString("Z", 2 * size / 3, size / 3);
        g.dispose();
        return img;
    }

    /**
     * Megrajzolja a gyorsító hatás ikonját.
     *
     * @param size  az ikon mérete
     * @param color az ikon színe
     */
    private BufferedImage speedIcon(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setStroke(new BasicStroke(size * 0.05f));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        int[] x = { size / 2, size / 5, size * 6 / 10, size * 2 / 5, size / 3, size * 2 / 5, size * 3 / 5 };
        int[] y = { 0, size * 3 / 5, size * 2 / 5, size, size * 7 / 10, size, size * 3 / 4 };
        g.drawPolyline(x, y, x.length);
        g.dispose();
        return img;
    }

    /**
     * Megrajzolja a lassító hatás ikonját.
     *
     * @param size  az ikon mérete
     * @param color az ikon színe
     */
    private BufferedImage slowIcon(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.translate(-size * 0.35, -size * 0.1);
        g.scale(1.5, 1.5);
        int headSize = size / 5;
        g.fillArc(size / 4, size / 4, size / 2, size / 2, 0, 180);
        g.fillArc(size * 3 / 4 - headSize / 4, size / 2 - headSize / 2, headSize, headSize, 0, 180);
        int legSize = size / 10;
        g.fillRect(size / 4, size / 2, legSize, legSize);
        g.fillRect(size * 3 / 4 - legSize, size / 2, legSize, legSize);
        g.scale(1 / 1.5, 1 / 1.5);
        g.translate(size * 0.35, size * 0.1);
        g.dispose();
        return img;
    }

    /**
     * Megrajzolja és eltárolja a rovar és a hatások ikonjait.
     *
     * @param size  az ikon mérete
     * @param color az ikon színe
     */
    private void generateImages() {
        Color color = getColor();
        cachedIcon = insectIcon(Cell.SIZE, color);
        antiChewImage = antiChewIcon(Cell.SIZE / 4, color);
        paraImage = paraIcon(Cell.SIZE / 4, color);
        speedImage = speedIcon(Cell.SIZE / 4, color);
        slowImage = slowIcon(Cell.SIZE / 4, color);
    }

    @Override
    public BufferedImage getIcon() {
        if (insect.getLocation() == null) {
            cell.setItem(null);
            return null;
        }

        List<InsectEffect> effs = insect.getActiveEffects();
        if (effs.isEmpty()) {
            return cachedIcon;
        }
        int n = effs.size();
        BufferedImage img = new BufferedImage(cachedIcon.getWidth(), cachedIcon.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(cachedIcon, 0, 0, null);
        int cellSize = cachedIcon.getWidth();
        int size = Math.min(cellSize / 3, cellSize / (n + 1));
        int x = (cellSize - n * size) / 2;
        int y = size / 4;
        for (int i = 0; i < n; i++) {
            InsectEffect effect = effs.get(i);
            BufferedImage icon = null;
            if (effect.getClass() == SpeedEffect.class) {
                if (((SpeedEffect) effect).getMultiplier() > 1) {
                    icon = speedImage;
                } else {
                    icon = slowImage;
                }
            } else if (effect.getClass() == AntiChewEffect.class) {
                icon = antiChewImage;
            } else if (effect.getClass() == ParalysingEffect.class) {
                icon = paraImage;
            }
            g.drawImage(icon, x, y, size, size, null);
            x += size;
        }
        return img;
    }
    // #endregion

    // #region ACTIONS
    /**
     * Megpróbálja a rovarral elmenni a megadott cellára. Ha sikerül, átállítja az eredeti és a célcella tartalmát. Ha
     * nem sikerül, értesíti a felhasználót.
     *
     * @param target célcella
     * @see Insect#moveTo(model.Tecton)
     */
    public void move(Cell target) {
        if (target.getTecton().getTecton() == insect.getLocation() || insect.moveTo(target.getTecton().getTecton())) {
            cell.setItem(null);
            target.setItem(this);
            cell = target;
        } else {
            View.notifyUser();
        }
    }

    /**
     * Megpróbálja a rovarral megenni a megadott cellán lévő spórát. Ellenőrzi, hogy a rovar és a célcella ugyanazon a
     * tektonon van. Ha sikerül, és a rovar osztódik, akkor létrehoz egy új csomagoló VInsect példányt a spóra helyén.
     * Ha nem sikerül, értesíti a felhasználót.
     *
     * @param target cél spóra cellája
     * @see Insect#eatSpore()
     */
    public void eat(Cell target) {
        if (cell.getTecton() == target.getTecton()) {
            List<Insect> allInsects = insect.getColony().getInsects();
            int numberOfInsects = allInsects.size();
            boolean success = insect.eatSpore();
            if (success) {
                if (numberOfInsects < allInsects.size()) {
                    Insect newInsect = allInsects.get(numberOfInsects);
                    var eaten = cell.getTecton().getCells().stream()
                            .filter(c -> c.getItem() != null && c.getItem().getIcon() == null).findFirst();
                    if (eaten.isEmpty())
                        throw new IllegalStateException("No eaten spore found");
                    new VInsect(eaten.get(), newInsect);
                }
            } else {
                View.notifyUser();
            }
        } else {
            View.notifyUser();
        }
    }

    /**
     * Megpróbálja elrágni a megadott cellán lévő fonalat. Ha nem sikerül, akkor értesíti a felhasználót.
     * 
     * @param target cél fonal cellája.
     * @see Insect#chewMycelium(model.Mycelium)
     */
    public void chew(Cell target) {
        IIcon item = target.getItem();
        boolean success = insect.chewMycelium(((VMycelium) item).getMycelium());
        if (!success) {
            View.notifyUser();
        }

    }
    // #endregion

    // #region GETTERS-SETTERS
    public Insect getInsect() {
        return insect;
    }
    // #endregion
}