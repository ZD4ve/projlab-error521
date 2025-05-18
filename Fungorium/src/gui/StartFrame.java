package gui;

import java.awt.*;
import javax.swing.*;
import view.View;

/**
 * <h3>StartFrame</h3>
 * 
 * A játék indításához szükséges ablak osztálya.
 */
public class StartFrame extends JFrame {

    // #region ATTRIBUTES
    /** A képernyőn a címhez tartozó panel. */
    JPanel topPanel;
    /** Az a panel, amin a labelek és a spinnerek találhatóak. */
    JPanel middlePanel;
    /** Az tektonok megadásához tartozó panel. */
    JPanel mid1;
    /** A gombászok megadásához tartozó panel. */
    JPanel mid2;
    /** A rovarászok megadásához tartozó panel. */
    JPanel mid3;
    /** A gombhoz tartozó alsó panel. */
    JPanel bottomPanel;

    /** Az tektonok megadásához tartozó spinner. Minimum értéke 1. */
    JSpinner spinner1;
    /** A gombászok megadásához tartozó spinner. Minimum értéke 1. */
    JSpinner spinner2;
    /** A rovarászok megadásához tartozó spinner. Minimum értéke 1. */
    JSpinner spinner3;

    /** A címhez tartozó label */
    JLabel titleLabel;
    /** A "Tektonok" label. */
    JLabel label1;
    /** A "Gombászok" label. */
    JLabel label2;
    /** A "Rovarászok" label. */
    JLabel label3;

    /** A kezdéshez tartozó gomb. Akkor indítható a játék, amennyiben több tekton van, mint játékos. */
    JButton start;
    // #endregion

    // #region CONSTRUCTOR
    /**
     * Konstruktor, amely létrehozza a StartFrame ablakot. Beállítja a paneleket, spinnereket, labeleket és a kezdés
     * gombot.
     */
    public StartFrame() {
        super("Fungorium");
        initFrame();

        initPanels();

        initSpinners();

        initLabels();

        initButton();

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
    // #endregion

    // #region FUNCTIONS
    /** Metódus, amely létrehozza az ablakon található paneleket, beállítja azok tulajdonságait. */
    private void initPanels() {
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
    }

    /** Metódus, amely létrehozza az ablakon található spinnereket, beállítja azok tulajdonságait. */
    private void initSpinners() {

        spinner1 = new JSpinner(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(4, 1, Integer.MAX_VALUE, 1));
        spinner3 = new JSpinner(new SpinnerNumberModel(4, 1, Integer.MAX_VALUE, 1));
        spinner1.setPreferredSize(new Dimension(50, 20));
        spinner2.setPreferredSize(new Dimension(50, 20));
        spinner3.setPreferredSize(new Dimension(50, 20));
    }

    /** Metódus, amely létrehozza az ablakon található labeleket, beállítja azok tulajdonságait. */
    private void initLabels() {
        titleLabel = new JLabel("Új játék");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        label1 = new JLabel("Tektonok:");
        label2 = new JLabel("Gombászok:");
        label3 = new JLabel("Rovarászok:");
    }

    /**
     * Metódus, amely létrehozza az ablakon található gombot, beállítja annak tulajdonságait, valamint a hozzá tartozó
     * ActionListenert.
     */
    private void initButton() {
        start = new JButton("Kezdés");
        start.addActionListener(e -> {
            int sp1 = (Integer) spinner1.getValue();
            int sp2 = (Integer) spinner2.getValue();
            int sp3 = (Integer) spinner3.getValue();
            if (sp1 < sp2 + sp3) {
                JOptionPane.showMessageDialog(null, "Több a játékos, mint a tektonok száma!");
                return;
            }

            this.setVisible(false);
            View.create(sp1, sp2, sp3);
            new PlayFrame();
            View.playStartSound();
        });
    }

    /** Metódus, amely létrehozza az ablakot, beállítja annak tulajdonságait. */
    public void initFrame() {
        setLocation(200, 200);
        setMinimumSize(new Dimension(320, 250));
        setSize(320, 250);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // #endregion
}