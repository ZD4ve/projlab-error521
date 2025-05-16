package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import view.VColony;
import view.VFungus;


public class PlayFrame extends JFrame {

    List<ColorPanel> fungusColorPanels, colonyColorPanels;
    JPanel sidePanel;
    JLabel fungusLabel, colonyLabel, timeLabel;

    public PlayFrame() {
        super("Fungorium");
        initFrame();
        sidePanel = new JPanel();
        fungusColorPanels = new ArrayList<>();
        colonyColorPanels = new ArrayList<>();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fungusLabel = new JLabel("Gombászok:");
        fungusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(fungusLabel);
        sidePanel.add(Box.createVerticalStrut(5));

        for (VFungus fungus : view.View.getAllFungi()) {
            ColorPanel colorPanel = new ColorPanel(fungus.getColor() , "pont: "+fungus.getFungus().getScore());
            colorPanel.addMouseListener( new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        for (ColorPanel colorPanel : fungusColorPanels) {
                            colorPanel.colorBox.setBorder(BorderFactory.createEmptyBorder());
                        }
                        fungus.selectPlayer();
                        colorPanel.colorBox.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
                        
                    }
                }
            });
            fungusColorPanels.add(colorPanel);
            sidePanel.add(colorPanel);
        }

        sidePanel.add(Box.createVerticalStrut(20));

        colonyLabel = new JLabel("Rovarászok:");
        colonyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(colonyLabel);
        sidePanel.add(Box.createVerticalStrut(5));

        for (VColony colony : view.View.getAllColonies()) {
            
            ColorPanel colorPanel = new ColorPanel(colony.getColor() , "pont: "+colony.getColony().getScore());
            colorPanel.addMouseListener( new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        for (ColorPanel colorPanel : colonyColorPanels) {
                            colorPanel.colorBox.setBorder(BorderFactory.createEmptyBorder());
                        }
                        colony.selectPlayer();
                        colorPanel.colorBox.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
                    }
                }
            });
            colonyColorPanels.add(colorPanel);
            sidePanel.add(colorPanel);
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

    public void initFrame() {
        setLocation(200, 200);
        setMinimumSize(new Dimension(600, 500));
        setSize(600, 500);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
