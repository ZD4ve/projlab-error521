package model;

/**
 * <h3>Elszakadt gombafonal életben tartó tekton</h3>
 * 
 * Olyan speciális tekton, amely gombatesthez nem csatlakoztatható gombafonalakat is életben tart.
 */
public class MyceliumKeepingTecton extends Tecton {
    @Override
    public Tecton newMe() {
        return new MyceliumKeepingTecton();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Mindig igaz, hiszen a tekton életben tartja a rajta található gombafonalakat.
     */
    @Override
    public boolean keepsMyceliumAlive() {
        return true;
    }
}
