package gui;

import java.awt.*;
import javax.swing.*;

public class ColorPanel extends JPanel{

    JPanel colorBox;
    JLabel label;
    Color color;
    String text;
    public ColorPanel(Color c, String t) {
        super(new FlowLayout(FlowLayout.LEFT, 10, 0));
        color = c;
        text = t;
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(200, 50));

        colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(50, 30));

        label = new JLabel(text);

        add(colorBox);
        add(label);
    }
}