package model;

/**
 * <h3>Gombafonal felszívó tekton</h3>
 * 
 * Olyan speciális tekton, ami időnként felszívja a rajta található gombafonalakat.
 */
public class MyceliumAbsorbingTecton extends Tecton {
    // #region CONSTANTS
    /** A tektonon található gombafonalak felszívásához szükséges idő másodpercben. */
    public static final double ABSORPTION_TIME_SECONDS = 60;
    // #endregion

    // #region ATTRIBUTES
    /** A gombafonalak felszívódásáig hátralévő idő másodpercben. */
    private double timeUntilAbsorption;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public MyceliumAbsorbingTecton() {
        timeUntilAbsorption = ABSORPTION_TIME_SECONDS;
    }
    // #endregion

    // #region FUNCTIONS
    @Override
    public MyceliumAbsorbingTecton newMe() {
        return new MyceliumAbsorbingTecton();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ha eltelt adott mennyiségű idő, felszívódnak a fonalak a tektonon.
     */
    @Override
    public void tick(double dT) {
        timeUntilAbsorption -= dT;
        if (timeUntilAbsorption <= 0) {
            while (!mycelia.isEmpty()) {
                mycelia.get(0).die();
            }
            timeUntilAbsorption = ABSORPTION_TIME_SECONDS;
        }
        super.tick(dT);
    }
    // #endregion
}