package model;

import helper.Skeleton;
import static helper.Skeleton.printCall;
import static helper.Skeleton.printReturn;
import java.util.Arrays;

public class Mycelium implements IActive {
    private final Fungus species;
    private final Tecton[] ends;

    // TODO: ez még nincs kész, csak beletrollkodtam (by: David)
    // Én is (by: Márton)
    // Most már én is (by: Panni)
    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        Skeleton.printCall(this.getClass(), Arrays.asList(fungus, end1, end2));
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };

        end1.addMycelium(this);
        end2.addMycelium(this);
        Skeleton.printReturn(this);
    }

    public void die() {
        printCall(this);
        for (Tecton tecton : ends) {
            tecton.removeMycelium(this);
        }
        species.removeMycelium(this);
        printReturn();
    }

    public Tecton[] getEnds() {
        Skeleton.printCall(this);
        Skeleton.printReturn(ends);
        return ends;
    }

    public Fungus getSpecies() {
        Skeleton.printCall(this);
        Skeleton.printReturn(species);
        return species;
    }

    @Override
    public void tick(double dT) {
        printCall(this, Arrays.asList(dT));
        boolean thickened = Skeleton.ask("Megvastagodott a gombafonal?");
        if (thickened)
            species.myceliumGrowthComplete();
        printReturn();
    }
}
