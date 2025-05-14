package gui;

import view.View;
import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    public StartFrame() {
        super("Fungorium");
        initFrame();

        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel mid1 = new JPanel();
        JPanel mid2 = new JPanel();
        JPanel mid3 = new JPanel();
        JPanel bottomPanel = new JPanel();

        middlePanel.add(mid1);
        middlePanel.add(mid2);
        middlePanel.add(mid3);
        middlePanel.setPreferredSize(new Dimension(150, 100));
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        bottomPanel.setPreferredSize(new Dimension(150, 50));
        topPanel.setPreferredSize(new Dimension(150, 40));

        JSpinner spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JSpinner spinner2 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JSpinner spinner3 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spinner1.setPreferredSize(new Dimension(50, 20));
        spinner2.setPreferredSize(new Dimension(50, 20));
        spinner3.setPreferredSize(new Dimension(50, 20));

        JLabel titleLabel = new JLabel("Új játék");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel label1 = new JLabel("Tektonok:");
        JLabel label2 = new JLabel("Gombászok:");
        JLabel label3 = new JLabel("Rovarászok:");

        JButton start = new JButton("Kezdés");
        start.addActionListener(e -> {
            View.create((Integer) spinner1.getValue(), (Integer) spinner2.getValue(), (Integer) spinner3.getValue());
            new PlayFrame();
        });

        mid1.add(label1);
        mid1.add(spinner1);
        mid2.add(label2);
        mid2.add(spinner2);
        mid3.add(label3);
        mid3.add(spinner3);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        bottomPanel.add(start);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initFrame() {
        setLocation(200, 200);
        setMinimumSize(new Dimension(320, 250));
        setSize(320, 250);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}