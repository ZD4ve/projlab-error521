package view;

import model.Mycelium;

public class VMycelium implements IIcon {
    private Mycelium mycelium;
    private Cell cell;

    public VMycelium(Cell cell, Mycelium mycelium, Cell neighbor) {
        this.cell = cell;
        this.mycelium = mycelium;
        cell.setItem(this);
        // neighbor az ikon miatt lasz majd hasznos
    }

    @Override
    public Object getIcon() {
        // TODO @Panni
        return null;
    }

    
    public Mycelium getMycelium() {
        return mycelium;
    }

}