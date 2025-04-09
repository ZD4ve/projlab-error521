package model;

/**
 * <h3>Egy gombafonalat engedő tekton</h3>
 * 
 * Olyan speciális tekton, melyen csak csak egy gombafajnak lehet gombafonala és gombateste.
 */
public class SingleMyceliumTecton extends Tecton {
    /**
     * Létrehoz egy új példányt a tektonból.
     * 
     * @return az új SingleMyceliumTecton példány
     */
    @Override
    public SingleMyceliumTecton newMe() {
        return new SingleMyceliumTecton();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ez akkor teljesül, ha nincs másik fajnak gombateste (Mushroom::getSpecies) vagy gombafonala
     * (Mycelium::getSpecies) a tektonon.
     * 
     * @return igaz, ha nincs másik gombafajnak gombateste vagy gombafonala a tektonon, hamis különben.
     */
    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        return mycelia.stream().noneMatch(x -> x.getSpecies() != fungus)
                && (mushroom == null || mushroom.getSpecies() == fungus);
    }
}