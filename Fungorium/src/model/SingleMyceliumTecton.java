package model;

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
        // this exists just so that we can document it :)
    }

    @Override
    public SingleMyceliumTecton newMe() {
        return new SingleMyceliumTecton();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ez akkor teljesül, ha nincs másik fajnak gombateste vagy gombafonala a tektonon.
     */
    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        boolean enemyMycelium = mycelia.stream().anyMatch(x -> x.getSpecies() != fungus);
        boolean enemyMushroom = mushroom != null && mushroom.getSpecies() != fungus;

        return enemyMycelium || enemyMushroom;
    }
}