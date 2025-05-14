package view;

import model.Fungus;
import model.Mycelium;
import model.Tecton;

import java.awt.Color;
import java.util.List;

public class VFungus extends VPlayer {
    private Fungus fungus;

    public VFungus(Fungus fungus, Color color) {
        super(color);
        this.fungus = fungus;
    }

    public void growMushroom(Cell cell) {
        Tecton tecton = cell.getTecton().getTecton();
        boolean success = fungus.growMushroom(tecton);
        if (success) {
            new VMushroom(cell, tecton.getMushroom());
        } else {
            View.notifyUser();
        }
    }

    public void growMycelium(Cell cellA, Cell cellB) {
        Map map = View.getMap();
        List<Cell> neighbors = map.getNeighbors(cellA);
        if (neighbors.contains(cellB)) {
            Tecton tecton1 = cellA.getTecton().getTecton();
            Tecton tecton2 = cellB.getTecton().getTecton();
            boolean success = fungus.growMycelium(tecton1, tecton2);
            if (success) {
                Mycelium mycelium = fungus.getMycelia().get(fungus.getMycelia().size() - 1);
                new VMycelium(cellA, mycelium, cellB);
                new VMycelium(cellB, mycelium, cellA);
            } else {
                View.notifyUser();
            }
        } else {
            View.notifyUser();
        }
    }

    public Fungus getFungus() {
        return fungus;
    }
}