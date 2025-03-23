package model;

import java.util.List;
import java.util.Arrays;

import helper.Skeleton;

/**
 * Olyan speciális tekton, melyen csak csak egy gombafajnak lehet gombafonala.
 */
public class SingleMyceliumTecton extends Tecton {
    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public SingleMyceliumTecton() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    @Override
    public SingleMyceliumTecton newMe() {
        Skeleton.printCall(this);
        var ret = new SingleMyceliumTecton();
        Skeleton.addObject(ret, "tecSinMyc");
        Skeleton.printReturn(ret);
        return ret;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ez akkor teljesül, ha nincs másik fajnak gombateste vagy gombafonala a
     * tektonon.
     */
    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        Skeleton.printCall(this, Arrays.asList(fungus));
        for (Mycelium m : mycelia) {
            if (m.getSpecies() != fungus) {
                Skeleton.printReturn(false);
                return false;
            }
        }
        if (mushroom != null && mushroom.getSpecies() != fungus) {
            Skeleton.printReturn(false);
            return false;
        }
        Skeleton.printReturn(true);
        return true;
    }
}