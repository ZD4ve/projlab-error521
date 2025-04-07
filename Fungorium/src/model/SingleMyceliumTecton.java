package model;

/**
 * <h3>Egy gombafonalat engedő tekton</h3>
 * 
 * Olyan speciális tekton, melyen csak csak egy gombafajnak lehet gombafonala.
 */
public class SingleMyceliumTecton extends Tecton {
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
        return mycelia.stream().noneMatch(x -> x.getSpecies() != fungus);
    }
}