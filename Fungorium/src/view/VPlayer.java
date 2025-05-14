package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class VPlayer {
    private Color color;

    protected VPlayer(Color color) {
        this.color = color;
    }

    public void selectPlayer() {
        View.setSelectedPlayer(this);
    }

    public static List<Color> generateColors(int playerCount) {
        List<Color> colors = new ArrayList<>();
        final float saturation = 0.9f;
        final float brightness = 0.6f;
        for (int i = 0; i < playerCount; i++) {
            float hue = (float) i / playerCount;
            colors.add(Color.getHSBColor(hue, saturation, brightness));
        }
        return colors;
    }

    public Color getColor() {
        return color;
    }
}