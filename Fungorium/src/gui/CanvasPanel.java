package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import view.*;

/**
 * A CanvasPanel osztály a játék területét reprezentálja, ahol a játékosok interakcióba léphetnek a
 * gombatestekkel és a gombafonalakkal. A panel lehetővé teszi a zoomolást és a mozgatást, valamint
 * kezelni tudja a kattintásokat és a húzást.
 */
public class CanvasPanel extends JPanel {
    /** A nagyítás mértéke */
    private double scale = 0.1;

    /** Az utolsó egérkattintás X koordinátája */
    private int lastX = 0;

    /** Az utolsó egérkattintás Y koordinátája */
    private int lastY = 0;

    /** Az X koordináta eltolása */
    private int offsetX = Cell.SIZE / 10;

    /** Az Y koordináta eltolása */
    private int offsetY = Cell.SIZE / 10;

    /** Az egér húzása közben beállított boolean */
    private boolean dragging = false;

    /**
     * Konstruktor, amely beállítja a panel háttérszínét, engedélyezi a fókuszálást és a dupla
     * pufferelést. Hozzáadja az egér eseménykezelőket a kattintások, húzások és görgetések kezelésére.
     */
    public CanvasPanel() {
        setBackground(View.getBackgroundColor());
        setFocusable(true);
        setDoubleBuffered(true);
        var mouseHandler = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    View.click((int) ((evt.getX() - offsetX) / scale), (int) ((evt.getY() - offsetY) / scale));
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    dragging = true;
                    lastX = evt.getX();
                    lastY = evt.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    dragging = false;
                }
            }

            @Override
            public void mouseDragged(MouseEvent evt) {
                if (dragging) {
                    int dx = evt.getX() - lastX;
                    int dy = evt.getY() - lastY;
                    offsetX += dx;
                    offsetY += dy;
                    lastX = evt.getX();
                    lastY = evt.getY();
                    repaint();
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent evt) {
                double change = Math.pow(1.1, -evt.getPreciseWheelRotation());
                scale *= change;
                offsetX = (int) ((offsetX - evt.getX()) * change + evt.getX());
                offsetY = (int) ((offsetY - evt.getY()) * change + evt.getY());
                repaint();
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
        setBackground(View.getBackgroundColor());
    }

    /**
     * Felelős a panel újrarajzolásáért. Beállítja a grafikus
     * kontextust, alkalmazza a nagyítást és az eltolást, majd meghívja a View::redraw metódust.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
        g2d.scale(scale, scale);
        g2d.translate(offsetX / scale, offsetY / scale);
        View.redraw(g2d);
    }
}