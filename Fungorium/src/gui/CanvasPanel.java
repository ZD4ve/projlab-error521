package gui;

import view.*;

import java.awt.Graphics;
import javax.swing.JPanel;

public class CanvasPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        View.redraw();
        g.drawImage(View.getCanvas(), 0, 0, null);
    }
}