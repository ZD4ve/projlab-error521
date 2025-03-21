package model;

import static helper.Skeleton.printCall;
import static helper.Skeleton.printReturn;

import java.util.List;

public class Mushroom implements IActive {
    private int range;
    private double cooldown;
    private Fungus species;
    private Tecton location;

    public Mushroom(Fungus fungus, Tecton location) {
        printCall(this.getClass(), List.of(fungus, location));
        this.species = fungus;
        this.location = location;
        location.setMushroom(this);
        printReturn(this);
    }

    public void burstSpore(Tecton target) {
    }

    @Override
    public void tick(double dT) {
    }

    public Fungus getSpecies() {
        return species;
    }

    public Tecton getLocation() {
        return location;
    }
}
