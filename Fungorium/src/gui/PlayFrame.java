package gui;

import controller.Controller;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import view.VColony;
import view.VFungus;
import view.View;


public class PlayFrame extends JFrame {

    private List<ColorPanel> colorPanels;
    JPanel sidePanel;
    JLabel fungusLabel;
    JLabel colonyLabel;
    JLabel timeLabel;
    JPanel canvas;
    Timer gameTimer;
    static final int UPDATE_INTERVAL = 100;
    int playTime = 5 * 60 * 1000; // 5 mins

    public PlayFrame() {
        super("Fungorium");
        iniFrame();
        sidePanel = new JPanel();
        colorPanels = new ArrayList<>();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fungusLabel = new JLabel("Gombászok:");
        fungusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(fungusLabel);
        sidePanel.add(Box.createVerticalStrut(5));

        for (VFungus fungus : view.View.getAllFungi()) {
            ColorPanel colorPanel = new ColorPanel(fungus);
            colorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        for (ColorPanel colorPanel : colorPanels) {
                            colorPanel.colorBox.setBorder(BorderFactory.createEmptyBorder());
                        }
                        fungus.selectPlayer();
                        colorPanel.colorBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.orange));

                    }
                }
            });
            colorPanels.add(colorPanel);
            sidePanel.add(colorPanel);
        }

        sidePanel.add(Box.createVerticalStrut(20));

        colonyLabel = new JLabel("Rovarászok:");
        colonyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(colonyLabel);
        sidePanel.add(Box.createVerticalStrut(5));

        for (VColony colony : view.View.getAllColonies()) {

            ColorPanel colorPanel = new ColorPanel(colony);
            colorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        for (ColorPanel colorPanel : colorPanels) {
                            colorPanel.colorBox.setBorder(BorderFactory.createEmptyBorder());
                        }
                        colony.selectPlayer();
                        colorPanel.colorBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.orange));
                    }
                }
            });
            colorPanels.add(colorPanel);
            sidePanel.add(colorPanel);
        }

        sidePanel.add(Box.createVerticalGlue());

        timeLabel = new JLabel("Hátralévő idő: ");
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(timeLabel);

        add(sidePanel, BorderLayout.WEST);
        canvas = new CanvasPanel();
        canvas.setPreferredSize(new Dimension(1500, 1000));
        add(canvas, BorderLayout.CENTER);

        pack();
        setVisible(true);

        gameTimer = new Timer(UPDATE_INTERVAL, e -> refresh());
        gameTimer.start();
    }

    private void refresh() {
        Controller.onTimeElapsed(UPDATE_INTERVAL / 1000d);
        canvas.repaint();
        for (ColorPanel colorPanel : colorPanels) {
            colorPanel.update();
        }
        playTime -= UPDATE_INTERVAL;
        int mins = playTime / 1000 / 60;
        int secs = playTime / 1000 % 60;
        timeLabel.setText(
                "Hátralévő idő: " + mins + ":" + (secs >= 10 ? Integer.toString(secs) : "0" + Integer.toString(secs)));
        if (playTime <= 0) {
            timeLabel.setText("Lejárt az idő!");
            timeLabel.setForeground(Color.red);
            gameTimer.stop();
            View.endGame();
        }
    }

    public final void iniFrame() {
        setLocation(200, 200);
        setMinimumSize(new Dimension(600, 500));
        setSize(600, 500);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
