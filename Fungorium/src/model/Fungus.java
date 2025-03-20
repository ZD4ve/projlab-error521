package model;

import helper.Skeleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Fungus {
    private final List<Mushroom> mushrooms;
    private final List<Mycelium> mycelia;
    private int growingMycelia;

    // TODO: éllista felépítése a mycelia osztályból
    private void checkConnectivity() {
        List<Tecton> mushroomTectons = new ArrayList<>(mushrooms.stream().map(x -> x.getLocation()).toList());
        HashSet<Tecton> visited = new HashSet<>();
        Stack<Tecton> stack = new Stack<>();

        for (Tecton tecton : mushroomTectons) {
            stack.add(tecton);
            while (!stack.empty()) {
                Tecton active = stack.peek();
                boolean notFound = true;

                visited.add(active);
                for (Tecton neighbor : active.getNeighbors()) {
                    if (!visited.contains(neighbor)) {
                        stack.add(neighbor);
                        notFound = false;
                        break;
                    }
                }
                if (notFound)
                    stack.pop();
            }
        }
    }

    public Fungus() {
        mushrooms = new ArrayList<>();
        mycelia = new ArrayList<>();
        growingMycelia = 0;
    }

    // GETTERS-SETTERS--------------------------------------------------------------

    public void addMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, List.of(mushroom));
        mushrooms.add(mushroom);
        Skeleton.printReturn();
    }

    public void removeMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, List.of(mushroom));
        Tecton location = mushroom.getLocation();
        

        mushrooms.remove(mushroom);
        Skeleton.printReturn();
    }

    public void addMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, List.of(mycelium));
        mycelia.add(mycelium);
        Skeleton.printReturn();
    }

    // PUBLIC MEMBER FUNCTIONS--------------------------------------------------------------

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
        return new ArrayList<>(potentialSources);
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
        return new ArrayList<>(potentialSources);
    }

    public boolean canGrowMycelium() {
        Skeleton.printCall(this);
        // TODO: restore
        // boolean canGrow = growingMycelia < mushrooms.size();
        boolean canGrow = Skeleton.ask("Tud még gombafonalat növeszteni a gombafaj?");

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
