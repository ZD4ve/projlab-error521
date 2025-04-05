package model;

import controller.RandomProvider;

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
     * 
     * A sebesség szorzója egy véletlenszerű 0.5 és 2.0 közötti lebegőpontos szám. Annak az esélye, hogy 0.5 és 1,
     * illetve hogy 1 és 2 között legyen egyenlő, ezen két intervallumon belül pedig egyenletes eloszlású az szorzó
     * értéke.
     */
    public SpeedEffect() {
        double m = RandomProvider.nextRand();
        this.multiplier = m > 0.5 ? 0.5 : 1 + 2 * m;
    }
    // #endregion

    // #region getters-setters
    /**
     * Visszaadja a sebesség szorzóját.
     * 
     * @return a sebesség szorzója
     */
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
     * A beállítandó sebességet az alábbi képlet alapján határozza meg: [rovar jelenlegi sebessége] * multiplier.
     * Beállítja a saját rovarát.
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
     * 
     * A beállítandó sebességet az alábbi képlet alapján határozza meg: [rovar jelenlegi sebessége] / multiplier.
     */
    @Override
    protected void remove() {
        double speed = insect.getSpeed();
        insect.setSpeed(speed / multiplier);
    }
    // #endregion
}