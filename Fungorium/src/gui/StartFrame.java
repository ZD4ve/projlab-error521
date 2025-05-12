package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame{

    JPanel topPanel, bottomPanel, middlePanel, mid1, mid2, mid3;
    JButton start;
    JLabel label1,label2,label3,titleLabel;
    JSpinner spinner1, spinner2, spinner3;

    public StartFrame() {

        super("Fungorium");
        initFrame();

        topPanel = new JPanel();
        middlePanel = new JPanel();
        mid1 = new JPanel();
        mid2 = new JPanel();
        mid3 = new JPanel();
        bottomPanel = new JPanel();

        middlePanel.add(mid1);
        middlePanel.add(mid2);
        middlePanel.add(mid3);
        middlePanel.setPreferredSize(new Dimension(150, 100));
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        bottomPanel.setPreferredSize(new Dimension(150, 50));
        topPanel.setPreferredSize(new Dimension(150, 40));

        spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spinner3 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spinner1.setPreferredSize(new Dimension(50, 20));
        spinner2.setPreferredSize(new Dimension(50, 20));
        spinner3.setPreferredSize(new Dimension(50, 20));

        titleLabel = new JLabel("Új játék");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        label1 = new JLabel("Tektonok:");
        label2 = new JLabel("Gombászok:");
        label3 = new JLabel("Rovarászok:");

        start = new JButton("Kezdés");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                PlayFrame pf = new PlayFrame();
            }
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
        setMinimumSize(new Dimension(320,250));
        setSize(320, 250);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}