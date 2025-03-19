package model;

import java.util.ArrayList;
import java.util.List;

public class Tecton implements IActive {
    private List<Tecton> neighbors;
    private List<Mycelium> mycelia;
    private List<Spore> spores;
    private List<Insect> insects;
    private Mushroom mushroom;

    public Tecton() {
        neighbors = new ArrayList<>();
        mycelia = new ArrayList<>();
        spores = new ArrayList<>();
        insects = new ArrayList<>();
    }

    public void addNeighbor(Tecton tecton) {
    }

    public void addInsect(Insect insect) {
    }

    public void addSpore(Spore spore) {
    }

    public void removeNeighbor(Tecton tecton) {
    }

    public void removeInsect(Insect insect) {
    }

    public void fillWithStuff(Spore[] spores, Mushroom mushroom, Insect[] insects, Tecton[] neighbors) {
    }

    public boolean hasMyceliumTo(Tecton tecton) {
        return false;
    }

    public boolean canGrowMyceliumFrom(Fungus fungus) {
        return false;
    }

    public int distanceTo(Tecton tecton) {
        return 0;
    }

    public Tecton newMe() {
        return null;
    }

    public void growMushroom(Fungus fungus) {
    }

    public void growMycelium(Fungus fungus, Tecton tecton) {
    }

    @Override
    public void tick(double dT) {
    }
}