package model;

public class Mycelium implements IActive {
    private final Fungus species;
    private final Tecton[] ends;

    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };
    }

    public void die() {
    }

    public Tecton[] getEnds() {
        return ends;
    }

    public Fungus getSpecies() {
        return species;
    }

    @Override
    public void tick(double dT) {
        
    }
}
