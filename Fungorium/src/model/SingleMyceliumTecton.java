package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * <h3>Egy gombafonalat engedő tekton</h3>
 * 
 * Olyan speciális tekton, melyen csak csak egy gombafajnak lehet gombafonala.
 */
public class SingleMyceliumTecton extends Tecton {
    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public SingleMyceliumTecton() {
    }

    @Override
    public SingleMyceliumTecton newMe() {
        var ret = new SingleMyceliumTecton();
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
        for (Mycelium m : mycelia) {
            if (m.getSpecies() != fungus) {
                return false;
            }
        }
        if (mushroom != null && mushroom.getSpecies() != fungus) {
            return false;
        }
        return true;
    }
}