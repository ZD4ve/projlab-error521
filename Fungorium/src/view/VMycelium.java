package view;

import model.Mycelium;

public class VMycelium implements IIcon {

    public VMycelium(Mycelium mycelium) {
        // Constructor implementation
    }

    public VMycelium(Cell cell, Mycelium mycelium, Cell neighbor) {
        // Constructor implementation
    }

    @Override
    public Object getIcon() {
        // Return the icon representation of the mycelium
        return null;
    }

    @Override
    public Class<?> getType() {
        return Mycelium.class;
    }
}