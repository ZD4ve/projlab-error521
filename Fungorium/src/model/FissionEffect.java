package model;

/**
 * <h3>Osztódást előidéző hatás</h3>
 * 
 * Olyan InsectEffect, amely osztódásra készteti a rovart.
 */
public class FissionEffect extends InsectEffect {
    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * A paraméterben kapott rovar osztódását idézi elő az Insect::split metódus meghívásával. Beállítja a saját
     * rovarát.
     * 
     * @param insect a rovar, ami osztódik
     */
    @Override
    public void applyTo(Insect insect) {
        insect.split();
        this.insect = insect;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Mellékhatás nélküli függvény, hiszen az osztódás azonnali hatású.
     */
    @Override
    protected void remove() {
        // Nem kell semmit csinálnia, csak azonnali hatása van
    }

}
