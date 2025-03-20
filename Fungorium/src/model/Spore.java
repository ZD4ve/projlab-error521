package model;

public class Spore {
    private Fungus species;

    public Spore(Fungus fungus) {
        this.species = fungus;
    }

    public InsectEffect getEffect() {
        return null;
    }

    public Fungus getSpecies() {
        return species;
    }
}
