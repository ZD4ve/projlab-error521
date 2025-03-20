package model;

import helper.Skeleton;
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

    public void addMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, List.of(mushroom));
        mushrooms.add(mushroom);
        Skeleton.printReturn();
    }

    public List<Tecton> getPotentialMyceliumSources() {
        Skeleton.printCall(this);
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

        Skeleton.printReturn(potentialSources);
        return (List<Tecton>)potentialSources;
    }

    public List<Tecton> getTectonsWithMycelia() {
        Skeleton.printCall(this);
        HashSet<Tecton> potentialSources = new HashSet<>();
        
        for (Mycelium mycelium : mycelia) {
            Tecton[] ends = mycelium.getEnds();

            potentialSources.add(ends[0]);
            potentialSources.add(ends[1]);
        }

        Skeleton.printReturn(potentialSources);
        return (List<Tecton>)potentialSources;
    }

    public boolean canGrowMycelium() {
        Skeleton.printCall(this);
        boolean canGrow = growingMycelia < mushrooms.size();

        Skeleton.printReturn(canGrow);
        return canGrow;
    }

    public void myceliumGrowthComplete() {
        Skeleton.printCall(this);
        growingMycelia--;
        Skeleton.printReturn();
    }

    public void growMushroom(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        Mushroom mushroom = tecton.growMushroom(this);

        Skeleton.printReturn();
        mushrooms.add(mushroom);
    }

    public void growMycelium(Tecton source, Tecton target) {
        Skeleton.printCall(this, List.of(source, target));
        boolean ready = canGrowMycelium();

        if (ready) {
            Mycelium mycelium = source.growMycelium(this, target);

            if (mycelium != null) {
                mycelia.add(mycelium);
                growingMycelia++;
            }
        }

        Skeleton.printReturn();
    }
}
