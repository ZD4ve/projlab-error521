package gui;

import view.*;

import java.awt.Graphics;
import javax.swing.JPanel;

public class CanvasPanel extends JPanel {

    public CanvasPanel() {
        setBackground(View.getBackgroundColor());
        setFocusable(true);
        setDoubleBuffered(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                View.click(evt.getX(), evt.getY());
                repaint(); // TODO may need to be removed later
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        View.redraw();
        g.drawImage(View.getCanvas(), 0, 0, null);
    }
}