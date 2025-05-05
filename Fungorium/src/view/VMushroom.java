package view;

import model.Mushroom;
import model.Tecton;

public class VMushroom implements IIcon {
    private Mushroom mushroom;

    public VMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    public VMushroom(Cell cell, Mushroom mushroom) {
        // Constructor implementation
    }

    public void burst(Cell target) {
        Tecton tecton = target.getTecton();
        boolean success = mushroom.burstSpore(tecton);
        if (success) {
            // Handle spore creation logic
        } else {
            View.notifyUser();
        }
    }

    @Override
    public Object getIcon() {
        // Return the icon representation of the mushroom
        return null;
    }

    @Override
    public Class<?> getType() {
        return Mushroom.class;
    }
}