package model;

import helper.Skeleton;
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
        Skeleton.printCall(this, List.of(target));
        boolean isReady = Skeleton.ask("Készen áll m1 a spóra szórására?");
        int distance = location.distanceTo(target);

        if (isReady) {
            if (distance == 2) {
                boolean isGrown = Skeleton.ask("Fejlett-e a gomba?");
                if (isGrown)
                    range = 2;
            }
            if (distance <= range) {
                Spore spo = new Spore(species);
                Skeleton.addObject(spo, "spo");
                target.addSpore(spo);
                boolean alive = Skeleton.ask("Tud még spórát szórni a gomba?");
                if (!alive) {
                    location.removeMushroom();
                    species.removeMushroom(this);
                }
            }
        }
        Skeleton.printReturn();
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
