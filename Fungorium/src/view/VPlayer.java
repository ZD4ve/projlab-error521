package view;

import java.awt.Color;

public abstract class VPlayer {
    public Color getColor() {
        // TODO @Panni
        // Ha @Márton @Vazul kész a generálással, akkor abba bele lehet majd építeni a szín generálást is
        // De ahhoz előbb a map generálásnak késznek kell lennie...
        return Color.RED;
    }

    public void selectPlayer() {
        View.setSelectedPlayer(this);
    }
}