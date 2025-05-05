package view;

import model.Mushroom;
import model.Tecton;

public class VMushroom implements IIcon {
    private Mushroom mushroom;
    private Cell cell;

    public VMushroom(Cell cell, Mushroom mushroom) {
        this.mushroom = mushroom;
        this.cell = cell;
        cell.setItem(this);
    }

    public void burst(Cell target) {
        Tecton tecton = target.getTecton().getTecton();
        boolean success = mushroom.burstSpore(tecton);
        if (success) {
            new VSpore(target, tecton.getSpores().get(tecton.getSpores().size() - 1));
        } else {
            View.notifyUser();
        }
    }

    @Override
    public Object getIcon() {
        // TODO @Panni
        return null;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }
}