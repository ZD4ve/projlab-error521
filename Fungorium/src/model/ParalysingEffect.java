package model;

/**
 * <h3>Bénító hatás</h3>
 * 
 * Olyan InsectEffect, amely a rovar mozgását tudja korlátozni.
 */
public class ParalysingEffect extends InsectEffect {
    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Mozgásképtelenné teszi a paraméterben kapott rovart.
     *
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        insect.setIsParalysed(true);
        this.insect = insect;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Visszaadja a rovarnak a mozgás képességét.
     */
    @Override
    protected void remove() {
        insect.setIsParalysed(true);
    }
}