package model;

public class Mycelium implements IActive {
    private Fungus species;
    private Tecton[] ends;

    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };
    }

    public void die() {
    }

    @Override
    public void tick(double dT) {
    }
}
