package model;

import helper.Skeleton;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * <h3>Gombafaj</h3>
 * 
 * Felelőssége összekötni az egy fajhoz tartozó spórákat, gombafonalakat,
 * valamint gombatesteket. Felelőssége továbbá annak ellenőrzése, hogy a
 * gombafaj tud-e gombatestet növeszteni. Felelőssége fonal növesztésének
 * kezdeményezése, fonalak összekötöttségének ellenőrzése, gombatest
 * növesztésének kezdeményezése.
 */
public class Fungus {
    /**
     * A gombafajhoz tartozó gombafonalak számontartása.
     */
    private final List<Mycelium> mycelia = new ArrayList<>();

    /**
     * A gombafajhoz tartozó gombatestek számontartása.
     */
    private final List<Mushroom> mushrooms = new ArrayList<>();

    private int growingMycelia = 0;
    private boolean checkConnectivityRunning = false;

    /**
     * Ellenőrzi, hogy a gombafajhoz tartozó gombafonalak közül melyek vannak
     * izolálva, és törli azokat.
     */
    private void checkConnectivity() {
        if (checkConnectivityRunning)
            return;
        checkConnectivityRunning = true;

        HashMap<Tecton, HashSet<Tecton>> adjacencyList = new HashMap<>();
        HashMap<Mycelium, Tecton[]> myceliaEnds = new HashMap<>();
        HashSet<Tecton> tectons = new HashSet<>(mushrooms.stream().map(x -> x.getLocation()).toList());
        tectons.addAll(myceliaEnds.entrySet().stream().flatMap(entry -> Arrays.stream(entry.getValue())).filter(Tecton::keepsMyceliumAlive).toList());

        for (Mycelium mycelium : mycelia) {
            var ends = mycelium.getEnds();
            myceliaEnds.put(mycelium, ends);
            if (!adjacencyList.containsKey(ends[0]))
                adjacencyList.put(ends[0], new HashSet<>());
            if (!adjacencyList.containsKey(ends[1]))
                adjacencyList.put(ends[1], new HashSet<>());

            adjacencyList.get(ends[0]).add(ends[1]);
            adjacencyList.get(ends[1]).add(ends[0]);
        }

        HashSet<Tecton> forest = new HashSet<>();
        var tectonIt = tectons.iterator();

        while (tectonIt.hasNext()) {
            Tecton tecton = tectonIt.next();

            if (!adjacencyList.containsKey(tecton))
                continue;

            Deque<Tecton> stack = new ArrayDeque<>();

            stack.add(tecton);
            forest.add(tecton);
            while (!stack.isEmpty()) {
                Tecton active = stack.peek();
                boolean notFound = true;

                for (Tecton neighbor : adjacencyList.get(active)) {
                    if (!forest.contains(neighbor)) {
                        forest.add(neighbor);
                        stack.push(neighbor);
                        notFound = false;

                        break;
                    }
                }
                if (notFound)
                    stack.pop();
            }
        }

        ArrayList<Mycelium> toKill = new ArrayList<>();
        for (Mycelium myc : mycelia) {
            var ends = myceliaEnds.get(myc);
            if (!forest.contains(ends[0]) && !forest.contains(ends[1]))
                toKill.add(myc);
        }
        for (Mycelium myc : toKill) {
            myc.die();
        }
        checkConnectivityRunning = false;
    }

    // GETTERS-SETTERS--------------------------------------------------------------
    /**
     * Hozzáadja a paraméterként kapott gombatestet a gombafajban tároltakhoz.
     * 
     * @param mushroom az új gombatest
     */
    public void addMushroom(Mushroom mushroom) {
        mushrooms.add(mushroom);
    }

    /**
     * Kiveszi a paraméterként kapott gombatestet a gombafajban tároltakból.
     * 
     * @param mushroom a gombatest, amelyet kiveszünk
     */
    public void removeMushroom(Mushroom mushroom) {
        mushrooms.remove(mushroom);
        checkConnectivity();
    }

    /**
     * Hozzáadja a paraméterként kapott gombafonalat a gombafajban tároltakhoz.
     * 
     * @param mycelium az új gombafonal
     */
    public void addMycelium(Mycelium mycelium) {
        mycelia.add(mycelium);
    }

    /**
     * Kiveszi a paraméterként kapott gombafonalat a gombafajban tároltakból.
     * 
     * @param mycelium a gombafonal, amelyet kiveszünk
     */
    public void removeMycelium(Mycelium mycelium) {
        mycelia.remove(mycelium);
        checkConnectivity();
    }

    // PUBLIC MEMBER
    // FUNCTIONS--------------------------------------------------------------

    /**
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van gombateste vagy
     * gombafonala, vagyis tud onnan gombafonalat növeszteni.
     * 
     * @return a lehetséges gombafonal növesztési források egy listában
     */
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
        return new ArrayList<>(potentialSources);
    }

    /**
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van fonala, vagyis lehet
     * gombatestet növeszteni rajta.
     * 
     * @return Minden olyan tekton, ahova lehet gombafonalat növeszteni, listában
     */
    public List<Tecton> getTectonsWithMycelia() {
        HashSet<Tecton> potentialSources = new HashSet<>();

        for (Mycelium mycelium : mycelia) {
            Tecton[] ends = mycelium.getEnds();

            potentialSources.add(ends[0]);
            potentialSources.add(ends[1]);
        }
        return new ArrayList<>(potentialSources);
    }

    /**
     * Megvizsgálja, hogy az adott gombafaj éppen hány gombafonalat növeszt.
     * 
     * @return Ha ez a szám kisebb mint a fajhoz tartozó gombafejek száma akkor
     *         igazat, különben hamisat ad vissza.
     */
    public boolean canGrowMycelium() {
        // TODO: restore
        // boolean canGrow = growingMycelia < mushrooms.size();
        boolean canGrow = Skeleton.ask("Tud még gombafonalat növeszteni a gombafaj?");
        return canGrow;
    }

    /**
     * Jelzi, hogy a gombafonál növekedése befejeződött, ezt kezeli le.
     */
    public void myceliumGrowthComplete() {
        growingMycelia--;
    }

    /**
     * A gombatest növesztését kezdeményezi a paraméterként kapott tektonra.
     * 
     * @param tecton az új gombatest tektonja
     * @return true ha a művelet sikeres, false egyébként
     */
    public boolean growMushroom(Tecton tecton) {
        return tecton.growMushroom(this);
    }

    /**
     * A paraméterként kapott két tekton közé gombafonal építését kezdeményezi.
     * 
     * @param source a forrástekton
     * @param target a céltekton
     * @return true ha a művelet sikeres, false egyébként
     */
    public boolean growMycelium(Tecton source, Tecton target) {
        boolean success = false;
        boolean ready = canGrowMycelium();
        if (ready) {
            success = source.growMycelium(this, target);
        }
        if (success) {
            growingMycelia++;
        }
        return success;
    }
}
