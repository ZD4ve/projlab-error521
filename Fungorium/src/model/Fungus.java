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
     * Visszaadja a gombafajhoz tartozó pontszámot.
     * 
     * @return a pontszám
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
     * Kiveszi a paraméterként kapott gombatestet a gombafajban tároltakból. Ezt követően ellenőrzi a gombafonalak
     * összekötöttségét (Fungus::checkConnectivity).
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
        growingMycelia++;
    }

    /**
     * Kiveszi a paraméterként kapott gombafonalat a gombafajban tároltakból. Ezt követően ellenőrzi a gombafonalak
     * összekötöttségét (Fungus::checkConnectivity).
     * 
     * @param mycelium a gombafonal, amelyet kiveszünk
     */
    public void removeMycelium(Mycelium mycelium) {
        mycelia.remove(mycelium);
        checkConnectivity();
    }

    /**
     * Visszaadja a gombafajhoz tartozó gombafonalakat.
     * 
     * @return a gombafonalak listája
     */
    public List<Mycelium> getMycelia() {
        return mycelia;
    }

    /**
     * Megvizsgálja, hogy az adott gombafaj éppen hány gombafonalat növeszt.
     * 
     * @return Ha ez a szám kisebb mint a fajhoz tartozó gombafejek száma akkor igazat, különben hamisat ad vissza.
     */
    public boolean canGrowMycelium() {
        return growingMycelia < mushrooms.size();
    }

    /**
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van gombateste vagy gombafonala, vagyis tud onnan gombafonalat
     * növeszteni. A művelet során a függvény lekérdezi a fajhoz tartozó gombatestek tartózkodási helyét
     * (Mushroom::getLocation) és a gombafonalak végpontját (Mycelium::getEnds), majd egy listába összegyűjti ezeket.
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
     * Megkeresi azokat a tektonokat, ahol a gombafajnak van fonala, vagyis lehet gombatestet növeszteni rajta. A
     * műveletet a fajhoz tartozó gombafonalak végpontjainak lekérdezésével (Mycelium::getEnds), majd egy listába való
     * összegyűjtésével végzi el.
     * 
     * @return Minden olyan tekton, ahova lehet gombafonalat növeszteni, listában
     */
    public List<Tecton> getTectonsWithMycelia() {
        HashSet<Tecton> potentialSources = new HashSet<>();
        potentialSources.addAll(mycelia.stream().flatMap(x -> Arrays.stream(x.getEnds())).toList());
        return new ArrayList<>(potentialSources);
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

        for (Tecton tecton : tectons) {
            if (!adjacencyList.containsKey(tecton))
                continue;

            Deque<Tecton> stack = new ArrayDeque<>();

            stack.add(tecton);
            forest.add(tecton);
            while (!stack.isEmpty()) {
                var neighbor = adjacencyList.get(stack.peek()).stream().filter(nb -> !forest.contains(nb)).toList();
                forest.addAll(neighbor);
                neighbor.forEach(stack::push);
                if (neighbor.isEmpty())
                    stack.pop();
            }
        }

        mycelia.stream().filter(myc -> {
            var ends = myceliaEnds.get(myc);
            return (!forest.contains(ends[0]) && !forest.contains(ends[1]));
        }).toList().stream().forEach(Mycelium::die);
        checkConnectivityRunning = false;
    }

    /**
     * Jelzi, hogy a gombafonál növekedése befejeződött, csökkenti a fajhoz tartozó, növekvő gombafonalak számát.
     */
    public void myceliumGrowthComplete() {
        growingMycelia--;
        if (growingMycelia < 0) {
            throw new IllegalStateException("Growing mycelia count is negative!");
        }
    }

    /**
     * Egy, a fajhoz tartozó, gombatest növesztését kezdeményezi (Tecton::growMushroom) a céltektonra (tecton).
     * 
     * @param tecton az új gombatest tektonja
     * @return true ha a művelet sikeres, false egyébként
     */
    public boolean growMushroom(Tecton tecton) {
        return tecton.growMushroom(this);
    }

    /**
     * A paraméterként kapott két tekton közé gombafonal építését kezdeményezi. Ellenőrzi önmagán, hogy jelenleg
     * növeszthet-e gombafonalat (Fungus::canGrowMycelium). Ha igen, akkor a forrástektonon (source) elindítja a
     * gombafonal növesztését a céltektonra (target) (Tecton::growMycelium), és visszaadja a művelet sikerességét. Ha
     * sikertelen a művelet, akkor false-t ad vissza.
     * 
     * @param source a forrástekton
     * @param target a céltekton
     * @return true ha a művelet sikeres, false egyébként
     */
    public boolean growMycelium(Tecton source, Tecton target) {
        if (canGrowMycelium()) {
            return source.growMycelium(this, target);
        }
        return false;
    }
    // #endregion
}
