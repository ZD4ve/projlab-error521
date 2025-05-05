package view;

import model.Colony;

public class VColony extends VPlayer {

    public VColony(Colony colony) {
        super();
    }

    @Override
    public Class<?> getType() {
        return Colony.class;
    }

    @Override
    public void selectPlayer() {
        // Implementation for selecting a colony player
    }
}