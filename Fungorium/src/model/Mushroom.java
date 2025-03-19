package model;

public class Mushroom implements IActive {
    private int range;
    private double cooldown;
    private Fungus species;
    private Tecton location;

    public Mushroom(Fungus fungus, Tecton location) {
        this.species = fungus;
        this.location = location;
    }

    public void burstSpore(Tecton target) {
    }

    @Override
    public void tick(double dT) {
    }
}
