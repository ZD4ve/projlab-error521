package model;

import java.util.ArrayList;
import java.util.List;

public class Fungus {
    private List<Mushroom> mushrooms;

    public Fungus() {
        mushrooms = new ArrayList<>();
    }

    public Tecton[] getPotentialMyceliumSources() {
        return null;
    }

    public Tecton[] getTectonsWithMycelia() {
        return null;
    }

    public boolean canGrowMycelium() {
        return false;
    }

    public void myceliumGrowthComplete() {
    }

    public void growMushroom(Tecton tecton) {
    }

    public void growMycelium(Tecton source, Tecton target) {
    }
}
