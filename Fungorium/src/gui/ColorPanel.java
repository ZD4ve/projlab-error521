package gui;

import java.awt.*;
import javax.swing.*;
import view.VPlayer;

public class ColorPanel extends JPanel {

    JPanel colorBox;
    JLabel label;
    transient VPlayer player;

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
    }

    public void update() {
        label.setText("pont: " + player.getScore());
    }
}