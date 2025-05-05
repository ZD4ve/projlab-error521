package view;

import model.Fungus;
import model.Tecton;
import java.util.List;

public class VFungus extends VPlayer {
    private Fungus fungus;

    public VFungus(Fungus fungus) {
        super();
        this.fungus = fungus;
    }

    @Override
    public Class<?> getType() {
        return Fungus.class;
    }

    @Override
    public void selectPlayer() {
        // Implementation for selecting a fungus player
    }

    public void growMushroom(Cell cell) {
        // Implementation for growing a mushroom
    }

    public void growMycelium(Cell cellA, Cell cellB) {
        Map map = View.getMap();
        List<Cell> neighbors = map.getNeighbors(cellA);
        if (neighbors.contains(cellB)) {
            Tecton vtecton1 = cellA.getTecton();
            Tecton vtecton2 = cellB.getTecton();
            boolean success = fungus.growMycelium(vtecton1, vtecton2);
            if (success) {
                // Handle mycelium creation logic
            } else {
                View.notifyUser();
            }
        } else {
            View.notifyUser();
        }
    }
}