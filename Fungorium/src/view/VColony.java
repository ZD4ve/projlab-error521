package view;

import model.Colony;

public class VColony extends VPlayer {
    private Colony colony;

    public VColony(Colony colony) {
        this.colony = colony;
    }

    public Colony getColony() {
        return colony;
    }
}