package view;

import model.Spore;

public class VSpore implements IIcon {
    private Spore spore;
    private Cell cell;

    public VSpore(Cell cell, Spore spore) {
        this.spore = spore;
        this.cell = cell;
        cell.setItem(this);
    }

    @Override
    public Object getIcon() {
        // TODO @Panni
        return null;
    }

    public Spore getSpore() {
        return spore;
    }
}