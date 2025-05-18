package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import view.VPlayer;

/**
 * <h3>ColorPanel</h3>
 * 
 * A játékosokhoz tartozó színes téglalap, valamint pontszám megjelenítéséért felelő osztály.
 */
public class ColorPanel extends JPanel {

    // #region ATTRIBUTES
    /** A játékos színét tartalmazó panel. */
    JPanel colorBox;
    /** A játékos pontszámát megjelenítő label. */
    JLabel label;
    /** Az a játékos, amelyhez tartozó ColorPanelt készítjük. */
    transient VPlayer player;
    /** A ColorPanelek példányait tároló lista. */
    static final List<ColorPanel> instances = new ArrayList<>();
    // #endregion

    // #region CONSTRUCTOR
    /**
     * Konstruktor, amely létrehozza a ColorPanelt. Beállítja a panel színét és a pontszámot. Kezeli a játékos
     * választását gombnyomáskor.
     * 
     * @param player Az a játékos, amelyhez a ColorPanel tartozik.
     */
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

    // #region FUNCTIONS
    /** Frissíti a ColorPanelek tartalmát. */
    public static void updateAll() {
        for (ColorPanel cp : instances) {
            cp.update();
        }
    }

    /** A Pontszámok frissítését, valamint a kiválasztott panel frissítését megvalósító metódus. */
    private void update() {
        label.setText("pont: " + player.getScore());
        colorBox.setBorder(player.isSelected() ? BorderFactory.createMatteBorder(3, 3, 3, 3, Color.orange)
                : BorderFactory.createEmptyBorder());
    }

    // #endregion
}