package gui;

import controller.Controller;
import java.awt.*;
import javax.swing.*;
import view.VPlayer;
import view.View;

public class PlayFrame extends JFrame {

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
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fungusLabel = new JLabel("Gombászok:");
        fungusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(fungusLabel);
        sidePanel.add(Box.createVerticalStrut(5));
        for (VPlayer player : view.View.getAllFungi()) {
            sidePanel.add(new ColorPanel(player));
        }

        sidePanel.add(Box.createVerticalStrut(20));

        colonyLabel = new JLabel("Rovarászok:");
        colonyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(colonyLabel);
        sidePanel.add(Box.createVerticalStrut(5));
        for (VPlayer player : view.View.getAllColonies()) {
            sidePanel.add(new ColorPanel(player));
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

        view.View.getAllFungi().get(0).selectPlayer();
        ColorPanel.updateAll();
    }

    private void refresh() {
        Controller.onTimeElapsed(UPDATE_INTERVAL / 1000d);
        canvas.repaint();
        ColorPanel.updateAll();
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
