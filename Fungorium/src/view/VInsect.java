package view;

import model.AntiChewEffect;
import model.Insect;
import model.InsectEffect;
import model.ParalysingEffect;
import model.SpeedEffect;

import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VInsect implements IIcon {
    private Insect insect;
    private Cell cell;
    private BufferedImage cachedIcon;
    private BufferedImage antiChewImage;
    private BufferedImage paraImage;
    private BufferedImage speedImage;
    private BufferedImage slowImage;

    // #region GETTERS SETTERS

    public Insect getInsect() {
        return insect;
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

    private void generateImages() {
        Color color = getColor();
        int size = Cell.getSize();
        cachedIcon = insectIcon(size, color);
        antiChewImage = antiChewIcon(size / 4, color);
        paraImage = paraIcon(size / 4, color);
        speedImage = speedIcon(size / 4, color);
        slowImage = slowIcon(size / 4, color);
    }

    @Override
    public BufferedImage getIcon() {
        if (insect.getLocation() == null)
            return null;
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

    public VInsect(Cell cell, Insect insect) {
        this.cell = cell;
        this.insect = insect;
        cell.setItem(this);
        generateImages();
    }

    // #region ACTIONS

    public void move(Cell target) {
        boolean success = insect.moveTo(target.getTecton().getTecton());
        if (success) {
            cell.setItem(null);
            target.setItem(this);
        } else {
            View.notifyUser();
        }
    }

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

    public void chew(Cell target) {
        IIcon item = target.getItem();
        boolean success = insect.chewMycelium(((VMycelium) item).getMycelium());
        if (!success) {
            View.notifyUser();
        }

    }

    // #endregion
}