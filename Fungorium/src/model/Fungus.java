package model;

import helper.Skeleton;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Fungus {
    private final List<Mushroom> mushrooms = new ArrayList<>();
    private final List<Mycelium> mycelia = new ArrayList<>();
    private int growingMycelia = 0;

    private boolean checkConnectivityRunning = false;

    private void checkConnectivity() {
        if (checkConnectivityRunning)
            return;
        checkConnectivityRunning = true;

        HashMap<Tecton, ArrayList<Tecton>> adjacencyList = new HashMap<>();
        HashMap<Mycelium, Tecton[]> myceliaEnds = new HashMap<>();
        HashSet<Tecton> tectons = new HashSet<>(mushrooms.stream().map(x -> x.getLocation()).toList());

        for (Mycelium mycelium : mycelia) {
            var ends = mycelium.getEnds();

            myceliaEnds.put(mycelium, ends);
            if (!adjacencyList.containsKey(ends[0]))
                adjacencyList.put(ends[0], new ArrayList<>());
            if (!adjacencyList.containsKey(ends[1]))
                adjacencyList.put(ends[1], new ArrayList<>());

            adjacencyList.get(ends[0]).add(ends[1]);
            adjacencyList.get(ends[1]).add(ends[0]);
        }

        HashSet<Tecton> forest = new HashSet<>();
        var tectonIt = tectons.iterator();
        
        while (!tectonIt.hasNext()) {
            Tecton tecton = tectonIt.next();

            if (!adjacencyList.containsKey(tecton))
                continue;
            
            HashSet<Tecton> visited = new HashSet<>();
            Deque<Tecton> stack = new ArrayDeque<>();

            stack.add(tecton);
            while (!stack.isEmpty()) {
                Tecton active = stack.peek();
                boolean notFound = true;

                for (Tecton neighbor : adjacencyList.get(active)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(active);
                        notFound = false;
                        stack.push(active);

                        break;
                    }
                }
                if (notFound)
                    stack.pop();
            }

            forest.addAll(visited);
        }

        var myceliumIt = mycelia.iterator();

        while (!myceliumIt.hasNext()) {
            var mycelium = myceliumIt.next();
            var ends = myceliaEnds.get(mycelium);

            if (!forest.contains(ends[0]) && !forest.contains(ends[1]))
                mycelium.die();
        }

        checkConnectivityRunning = false;
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

    public void removeMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, List.of(mycelium));
        mycelia.remove(mycelium);
        checkConnectivity();
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
