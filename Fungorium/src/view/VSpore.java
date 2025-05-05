package view;

import model.Spore;

public class VSpore implements IIcon {
    private Spore spore;

    public VSpore(Spore spore) {
        this.spore = spore;
    }

    public VSpore(Cell cell, Spore spore) {
        // Constructor implementation
    }

    @Override
    public Object getIcon() {
        // Return the icon representation of the spore
        return null;
    }

    @Override
    public Class<?> getType() {
        return Spore.class;
    }
}