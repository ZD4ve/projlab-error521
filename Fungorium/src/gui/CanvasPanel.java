package gui;

import view.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;

public class CanvasPanel extends JPanel {
    private double scale = 0.1;
    private int lastX = 0;
    private int lastY = 0;
    private int offsetX = Cell.SIZE / 10;
    private int offsetY = Cell.SIZE / 10;
    private boolean dragging = false;

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