package view;

import java.awt.Color;

public abstract class VPlayer {
    public Color getColor() {
        // TODO @Panni
        return Color.RED;
    }

    public void selectPlayer() {
        View.setSelectedPlayer(this);
    }
}