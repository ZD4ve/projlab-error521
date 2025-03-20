package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Fungus {
    private final List<Mushroom> mushrooms;
    private final List<Mycelium> mycelia;
    private int growingMycelia;

    public Fungus() {
        mushrooms = new ArrayList<>();
        mycelia = new ArrayList<>();
        growingMycelia = 0;
    }

    public List<Tecton> getPotentialMyceliumSources() {
        HashSet<Tecton> potentialSources = new HashSet<>();
        
        for (Mycelium mycelium : mycelia) {
            Tecton[] ends = mycelium.getEnds();

            potentialSources.add(ends[0]);
            potentialSources.add(ends[1]);
        }

        for (Mushroom mushroom : mushrooms) {
            Tecton location = mushroom.getLocation();

            potentialSources.add(location);
        }

        return (List<Tecton>)potentialSources;
    }

    public List<Tecton> getTectonsWithMycelia() {
        HashSet<Tecton> potentialSources = new HashSet<>();
        
        for (Mycelium mycelium : mycelia) {
            Tecton[] ends = mycelium.getEnds();

            potentialSources.add(ends[0]);
            potentialSources.add(ends[1]);
        }

        return (List<Tecton>)potentialSources;
    }

    public boolean canGrowMycelium() {
        return growingMycelia < mushrooms.size();
    }

    public void myceliumGrowthComplete() {
        growingMycelia--;
    }

    public void growMushroom(Tecton tecton) {
    }

    public void growMycelium(Tecton source, Tecton target) {
        boolean ready = canGrowMycelium();

        if (ready) {
            Mycelium mycelium = source.growMycelium(this, target);

            if (mycelium != null) {
                mycelia.add(mycelium);
                growingMycelia++;
            }
        }
    }
}
