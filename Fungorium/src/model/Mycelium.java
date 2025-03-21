package model;

import java.util.List;

import helper.Skeleton;

public class Mycelium implements IActive {
    private final Fungus species;
    private final Tecton[] ends;

    // TODO: ez még nincs kész, csak beletrollkodtam (by: David)
    // Én is (by: Márton)
    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        Skeleton.addObject(this, "my1");
        Skeleton.printCall(this, List.of(fungus, end1, end2));
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };

        end1.addMycelium(this);
        end2.addMycelium(this);
        Skeleton.printReturn();
    }

    public void die() {
    }

    public Tecton[] getEnds() {
        return ends;
    }

    public Fungus getSpecies() {
        return species;
    }

    @Override
    public void tick(double dT) {

    }
}
