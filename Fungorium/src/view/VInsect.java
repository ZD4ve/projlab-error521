package view;

import model.Insect;
import model.Colony;
import model.Tecton;

public class VInsect implements IIcon {
    private Insect insect;

    public VInsect(Insect insect) {
        this.insect = insect;
    }

    public VInsect(Cell cell, Insect insect) {
        // Constructor implementation
    }

    public void move(Cell target) {
        Tecton vtecton = target.getTecton();
        boolean success = insect.moveTo(vtecton);
        if (success) {
            cell.setItem(null);
            target.setItem(this);
        } else {
            View.notifyUser();
        }
    }

    public void eat(Cell target) {
        Object vtecton1 = cell.getTecton();
        Object vtecton2 = target.getTecton();
        if (vtecton1.equals(vtecton2)) {
            Colony colony = insect.getColony();
            boolean success = insect.eatSpore();
            if (success) {
                // Handle new insect creation logic
            } else {
                View.notifyUser();
            }
        } else {
            View.notifyUser();
        }
    }

    public void chew(Cell target) {
        IIcon item = target.getItem();
        if (item instanceof VMycelium) {
            boolean success = insect.chewMycelium(((VMycelium) item).getMycelium());
            if (!success) {
                View.notifyUser();
            }
        }
    }

    @Override
    public Object getIcon() {
        // Return the icon representation of the insect
        return null;
    }

    @Override
    public Class<?> getType() {
        return Insect.class;
    }
}