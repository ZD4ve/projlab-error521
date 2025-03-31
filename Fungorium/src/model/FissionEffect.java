package model;

/**
 * <h3>Osztódást előidéző hatás</h3>
 * 
 * Olyan InsectEffect, amely osztódásra készteti a rovart.
 */
public class FissionEffect extends InsectEffect {

    /**
     * A paraméterben kapott rovar osztódását idézi elő.
     * 
     * @param insect a rovar, ami osztódik
     */
    @Override
    public void applyTo(Insect insect) {
        insect.split();
        this.insect = insect;
    }

    /**
     * Mellékhatás nélküli függvény, hiszen az osztódás azonnali hatású.
     */
    @Override
    protected void remove() {
        // Nem kell semmit csinálnia, csak azonnali hatása van
    }

}
