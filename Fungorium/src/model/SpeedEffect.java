package model;

/**
 * <h3>Sebesség változtató hatás</h3>
 * 
 * Olyan InsectEffect, amely a rovar sebességét befolyásolja.
 */
public class SpeedEffect extends InsectEffect {
    // #region ATTRIBUTES
    /** Lebegőpontos szám, ezzel szorozza a rovar sebességét. */
    private double multiplier;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Inicializálja az effektet.
     */
    public SpeedEffect() {
        // TODO RANDOM
        this.multiplier = 0.65;
    }
    // #endregion

    // #region getters-setters
    public double getMultiplier() {
        return multiplier;
    }
    // #endregion

    // #region FUNCTIONS
    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * A paraméterben kapott rovar sebességét állítja.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        double speed = insect.getSpeed();
        insect.setSpeed(speed * multiplier);
        this.insect = insect;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Visszaállítja a rovar sebességét.
     */
    @Override
    protected void remove() {
        double speed = insect.getSpeed();
        insect.setSpeed(speed / multiplier);
    }
    // #endregion
}