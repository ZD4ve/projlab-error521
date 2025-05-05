package view;

import java.awt.Color;

public abstract class VPlayer {
    public Color getColor() {
        // TODO @Panni
        return null;
    }

    public void selectPlayer() {
        View.setSelectedPlayer(this);
    }
}