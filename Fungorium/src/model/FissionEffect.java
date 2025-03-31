package model;

// TODO DOC
public class FissionEffect extends InsectEffect {

    @Override
    public void applyTo(Insect insect) {
        insect.split();
        this.insect = insect;
    }

    @Override
    public void remove() {
        // Nem kell semmit csinálnia, csak azonnali hatása van
    }

}
