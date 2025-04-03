package model;

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
 * Felelőssége összekötni az egy fajhoz tartozó spórákat, gombafonalakat, valamint gombatesteket. Felelőssége továbbá
 * annak ellenőrzése, hogy a gombafaj tud-e gombatestet növeszteni. Felelőssége fonal növesztésének kezdeményezése,
 * fonalak összekötöttségének ellenőrzése, gombatest növesztésének kezdeményezése.
 */
public class Fungus {
    // #region ASSOCIATIONS
    /** A gombafajhoz tartozó gombafonalak számontartása. */
    private final List<Mycelium> mycelia = new ArrayList<>();
    /** A gombafajhoz tartozó gombatestek számontartása. */
    private final List<Mushroom> mushrooms = new ArrayList<>();
    // #endregion

    // #region ATTRIBUTES
    /** A gombafajhoz tartozó növekedő gombafonalak száma. */
    private int growingMycelia = 0;
    /** Éppen fut-e összekötöttség ellenőrzése. */
    private boolean checkConnectivityRunning = false;
    /** A gombafajhoz tartozó gombász pontszáma. */
    private int score = 0;
    // #endregion

    // #region GETTERS-SETTERS

    /**
     * Lekéri a gombafaj pontszámát.
     */
    public int getScore() {
        return score;
    }

    /**
     * Hozzáadja a paraméterként kapott gombatestet a gombafajban tároltakhoz.
     * 
     * @param mushroom az új gombatest
     */
    public void addMushroom(Mushroom mushroom) {
        mushrooms.add(mushroom);
        score++;
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

    /**
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van gombateste vagy gombafonala, vagyis tud onnan gombafonalat
     * növeszteni.
     * 
     * @return a lehetséges gombafonal növesztési források egy listában
     */
    public List<Tecton> getPotentialMyceliumSources() {
        HashSet<Tecton> potentialSources = new HashSet<>();

        potentialSources.addAll(mushrooms.stream().map(x -> x.getLocation()).toList());
        potentialSources.addAll(mycelia.stream().flatMap(x -> Arrays.stream(x.getEnds())).toList());

        return new ArrayList<>(potentialSources);
    }

    /**
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van fonala, vagyis lehet gombatestet növeszteni rajta.
     * 
     * @return Minden olyan tekton, ahova lehet gombafonalat növeszteni, listában
     */
    public List<Tecton> getTectonsWithMycelia() {
        HashSet<Tecton> potentialSources = new HashSet<>();
        potentialSources.addAll(mycelia.stream().flatMap(x -> Arrays.stream(x.getEnds())).toList());
        return new ArrayList<>(potentialSources);
    }

    /**
     * Megvizsgálja, hogy az adott gombafaj éppen hány gombafonalat növeszt.
     * 
     * @return Ha ez a szám kisebb mint a fajhoz tartozó gombafejek száma akkor igazat, különben hamisat ad vissza.
     */
    public boolean canGrowMycelium() {
        return growingMycelia < mushrooms.size();
    }

    // #endregion
    // #region FUNCTIONS

    /**
     * Ellenőrzi, hogy a gombafajhoz tartozó gombafonalak közül melyek vannak izolálva, és törli azokat.
     */
    private void checkConnectivity() {
        if (checkConnectivityRunning)
            return;
        checkConnectivityRunning = true;

        HashMap<Tecton, HashSet<Tecton>> adjacencyList = new HashMap<>();
        HashMap<Mycelium, Tecton[]> myceliaEnds = new HashMap<>();
        
        for (Mycelium mycelium : mycelia) {
            var ends = mycelium.getEnds();
            myceliaEnds.put(mycelium, ends);
            adjacencyList.putIfAbsent(ends[0], new HashSet<>());
            adjacencyList.putIfAbsent(ends[1], new HashSet<>());
            adjacencyList.get(ends[0]).add(ends[1]);
            adjacencyList.get(ends[1]).add(ends[0]);
        }
        
        HashSet<Tecton> tectons = new HashSet<>(myceliaEnds.entrySet().stream()
                .flatMap(entry -> Arrays.stream(entry.getValue())).filter(t -> t.keepsMyceliumAlive(this)).toList());
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
                var neighbor = adjacencyList.get(stack.peek()).stream().filter(nb -> !forest.contains(nb)).findFirst();
                if (neighbor.isPresent()) {
                    forest.add(neighbor.get());
                    stack.push(neighbor.get());
                } else {
                    stack.pop();
                }
            }
        }

        mycelia.stream().filter(myc -> {
            var ends = myceliaEnds.get(myc);
            return (!forest.contains(ends[0]) && !forest.contains(ends[1]));
        }).toList().stream().forEach(Mycelium::die);
        checkConnectivityRunning = false;
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
        if (canGrowMycelium()) {
            boolean success = source.growMycelium(this, target);
            if (success) {
                growingMycelia++;
                return true;
            }
        }
        return false;
    }
    // #endregion
}
