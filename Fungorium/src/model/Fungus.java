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
 * gombafaj tud-e gombatestet növeszteni. Felelőssége fonal növesztésénének
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
     * Ellenőrzi, hogy a gombafajhoz tartozó gombafonalak közül melyek vannak izolálva,
     * és törtli azokat.
     */
    private void checkConnectivity() {
        if (checkConnectivityRunning)
            return;
        checkConnectivityRunning = true;

        HashMap<Tecton, HashSet<Tecton>> adjacencyList = new HashMap<>();
        HashMap<Mycelium, Tecton[]> myceliaEnds = new HashMap<>();
        HashSet<Tecton> tectons = new HashSet<>(mushrooms.stream().map(x -> x.getLocation()).toList());

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
        Skeleton.printCall(this, Arrays.asList(mushroom));
        mushrooms.add(mushroom);
        Skeleton.printReturn();
    }

    /**
     * Kiveszi a paraméterként kapott gombatestet a gombafajban tároltakból.
     * 
     * @param mushroom a gombatest, amelyet kiveszünk
     */
    public void removeMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, Arrays.asList(mushroom));
        mushrooms.remove(mushroom);
        checkConnectivity();
        Skeleton.printReturn();
    }

    /**
     * Hozzáadja a paraméterként kapott gombafonalat a gombafajban tároltakhoz.
     * 
     * @param mycelium az új gombafonal
     */
    public void addMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, Arrays.asList(mycelium));
        mycelia.add(mycelium);
        Skeleton.printReturn();
    }

    /**
     * Kiveszi a paraméterként kapott gombafonalat a gombafajban tároltakból.
     * 
     * @param mycelium a gombafonal, amelyet kiveszünk
     */
    public void removeMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, Arrays.asList(mycelium));
        mycelia.remove(mycelium);
        checkConnectivity();
        Skeleton.printReturn();
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

    /**
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van fonala, vagyis lehet
     * gombatestet növeszteni rajta.
     * 
     * @return Minden olyan tekton, ahova lehet gombafonalat növeszteni, listában
     */
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

    /**
     * Megvizsgálja, hogy az adott gombafaj éppen hány gombafonalat növeszt.
     * 
     * @return Ha ez a szám kisebb mint a fajhoz tartozó gombafejek száma akkor
     *         igazat, különben hamisat ad vissza.
     */
    public boolean canGrowMycelium() {
        Skeleton.printCall(this);
        // TODO: restore
        // boolean canGrow = growingMycelia < mushrooms.size();
        boolean canGrow = Skeleton.ask("Tud még gombafonalat növeszteni a gombafaj?");

        Skeleton.printReturn(canGrow);
        return canGrow;
    }

    /**
     * Jelzi, hogy a gombafonál növekedése befejeződött, ezt kezeli le.
     */
    public void myceliumGrowthComplete() {
        Skeleton.printCall(this);
        growingMycelia--;
        Skeleton.printReturn();
    }

    /**
     * A gombatest növesztését kezdeményezi a paraméterként kapott tektonra.
     * 
     * @param tecton az új gombatest tektonja
     * @return true ha a művelet sikeres, false egyébként
     */
    public boolean growMushroom(Tecton tecton) {
        Skeleton.printCall(this, Arrays.asList(tecton));
        Mushroom mushroom = tecton.growMushroom(this);

        if (mushroom == null) {
            Skeleton.printReturn(false);
            return false;
        }

        mushrooms.add(mushroom);
        Skeleton.printReturn(true);
        return true;
    }

    /**
     * A paraméterként kapott két tekton közé gombafonal építését kezdeményezi.
     * 
     * @param source a forrástekton
     * @param target a céltekton
     * @return true ha a művelet sikeres, false egyébként
     */
    public boolean growMycelium(Tecton source, Tecton target) {
        Skeleton.printCall(this, Arrays.asList(source, target));
        boolean ready = canGrowMycelium();

        if (ready) {
            Mycelium mycelium = source.growMycelium(this, target);

            if (mycelium != null) {
                mycelia.add(mycelium);
                growingMycelia++;
                Skeleton.printReturn(true);
                return true;
            }
            Skeleton.printReturn(false);
            return false;
        }

        Skeleton.printReturn(false);
        return false;
    }
}
