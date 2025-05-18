package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import view.VPlayer;

public class ColorPanel extends JPanel {

    JPanel colorBox;
    JLabel label;
    transient VPlayer player;
    static final List<ColorPanel> instances = new ArrayList<>();

    public ColorPanel(VPlayer player) {
        super(new FlowLayout(FlowLayout.LEFT, 10, 0));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(200, 50));

        colorBox = new JPanel();
        colorBox.setBackground(player.getColor());
        colorBox.setPreferredSize(new Dimension(50, 30));

        label = new JLabel("pont: " + player.getScore());

        this.player = player;

        add(colorBox);
        add(label);
        instances.add(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                player.selectPlayer();
                updateAll();
            }
        });
    }

    public static void updateAll() {
        for (ColorPanel cp : instances) {
            cp.update();
        }
    }

    private void update() {
        label.setText("pont: " + player.getScore());
        colorBox.setBorder(player.isSelected() ? BorderFactory.createMatteBorder(3, 3, 3, 3, Color.orange)
                : BorderFactory.createEmptyBorder());
    }
}