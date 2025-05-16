package gui;

import java.awt.*;
import javax.swing.*;
import view.VColony;
import view.VFungus;


public class PlayFrame extends JFrame {

    
    JPanel sidePanel;
    JLabel fungusLabel, colonyLabel, timeLabel;

    public PlayFrame() {
        super("Fungorium");
        initFrame();

        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fungusLabel = new JLabel("Gombászok:");
        fungusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(fungusLabel);
        sidePanel.add(Box.createVerticalStrut(5));

        for (VFungus fungus : view.View.getAllFungi()) {
            sidePanel.add(createColorPointRow(fungus.getColor() , "pont: "+fungus.getFungus().getScore()));
            sidePanel.add(Box.createVerticalStrut(5));
        }

        sidePanel.add(Box.createVerticalStrut(20));

        colonyLabel = new JLabel("Rovarászok:");
        colonyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(colonyLabel);
        sidePanel.add(Box.createVerticalStrut(5));

        for (VColony colony : view.View.getAllColonies()) {
            
            sidePanel.add(createColorPointRow(colony.getColor() , "pont: "+colony.getColony().getScore()));
            sidePanel.add(Box.createVerticalStrut(5));
        }

        sidePanel.add(Box.createVerticalGlue());

        timeLabel = new JLabel("Hátralévő idő: ");
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(timeLabel);

        add(sidePanel, BorderLayout.WEST);

        JPanel canvas = new CanvasPanel();
        canvas.setPreferredSize(new Dimension(1500, 1000));
        add(canvas, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private static JPanel createColorPointRow(Color color, String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setPreferredSize(new Dimension(200, 50));
        row.setMaximumSize(new Dimension(200, 50));

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(50, 30));

        JLabel label = new JLabel(text);

        row.add(colorBox);
        row.add(label);
        return row;
    }

    public void initFrame() {
        setLocation(200, 200);
        setMinimumSize(new Dimension(600, 500));
        setSize(600, 500);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
