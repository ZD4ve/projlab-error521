package view;

import model.Colony;
import java.awt.Color;

public class VColony extends VPlayer {
    private Colony colony;

    public VColony(Colony colony, Color color) {
        super(color);
        this.colony = colony;
    }

    public Colony getColony() {
        return colony;
    }
}