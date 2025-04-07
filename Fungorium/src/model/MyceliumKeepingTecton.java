package model;

/**
 * <h3>Elszakadt gombafonal életben tartó tekton</h3>
 * 
 * Olyan speciális tekton, amely gombatesthez nem csatlakoztatható gombafonalakat is életben tart.
 */
public class MyceliumKeepingTecton extends Tecton {
    /**
     * Létrehoz egy új példányt a tektonból.
     * 
     * @return az új MyceliumKeepingTecton példány
     */
    @Override
    public MyceliumKeepingTecton newMe() {
        return new MyceliumKeepingTecton();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Mindig igaz, hiszen a tekton életben tartja a rajta található gombafonalakat.
     * 
     * @return {@code true}
     */
    @Override
    public boolean keepsMyceliumAlive(Fungus species) {
        return true;
    }
}
