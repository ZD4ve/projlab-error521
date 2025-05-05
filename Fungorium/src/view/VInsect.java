package view;

import model.Insect;

import java.util.List;

public class VInsect implements IIcon {
    private Insect insect;
    private Cell cell;

    public VInsect(Cell cell, Insect insect) {
        this.cell = cell;
        this.insect = insect;
        cell.setItem(this);
    }

    public void move(Cell target) {
        boolean success = insect.moveTo(target.getTecton().getTecton());
        if (success) {
            cell.setItem(null);
            target.setItem(this);
        } else {
            View.notifyUser();
        }
    }

    public void eat(Cell target) {
        if (cell.getTecton() == target.getTecton()) {
            List<Insect> allInsects = insect.getColony().getInsects();
            int numberOfInsects = allInsects.size();
            boolean success = insect.eatSpore();
            if (success) {
                if (numberOfInsects < allInsects.size()) {
                    Insect newInsect = allInsects.get(numberOfInsects);
                    var eaten = cell.getTecton().getCells().stream()
                            .filter(c -> c.getItem() != null && c.getItem().getIcon() == null).findFirst();
                    if (eaten.isEmpty())
                        throw new IllegalStateException("No eaten spore found");
                    new VInsect(eaten.get(), newInsect);
                }
            } else {
                View.notifyUser();
            }
        } else {
            View.notifyUser();
        }
    }

    public void chew(Cell target) {
        IIcon item = target.getItem();
        boolean success = insect.chewMycelium(((VMycelium) item).getMycelium());
        if (!success) {
            View.notifyUser();
        }

    }

    @Override
    public Object getIcon() {
        // TODO @Panni
        return null;
    }

    public Insect getInsect() {
        return insect;
    }
}