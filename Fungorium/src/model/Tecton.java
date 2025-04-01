package model;

import controller.Controller;
import controller.RandomProvider;
import java.util.*;

/**
 * <h3>Tekton</h3>
 * 
 * Felelőssége a saját szomszédainak, valamint a rajta található további objektumok nyilvántartása. Felelőssége a
 * gombatestek, gombafonalak növesztéséhez szükséges ellenőrzések elvégzése, valamint ezek halálakor azon objektumok
 * törlése. Felelőssége a rovarokkal való kapcsolatának karbantartása, azok hozzáadása, valamint törlése, spóra
 * hozzáadásának kezelése, spóra átadása az azt megevő rovarnak. Felelőssége a tekton törésének kezdeményezése és
 * kezelése, ezalatt a rajta található objektumok elosztása, valamint saját tulajdonságainak lemásolása a keletkező új
 * tektonokra.
 */
public class Tecton implements IActive {
    // #region CONSTANTS
    /** Annak az esélye, hogy egy tekton egy adott másodperc alatt eltörik. */
    public static final double BREAK_CHANCE_PER_SEC = 0.001;
    // #endregion

    // #region ASSOCIATIONS
    /** A tektonhoz tartozó szomszédokat tárolja el. */
    private List<Tecton> neighbors;
    /** A tektonhoz tartozó gombafonalakat tárolja. */
    protected List<Mycelium> mycelia;
    /** A tektonon található spórákat tárolja. */
    private List<Spore> spores;
    /** A tektonon található spórákat tárolja. */
    private List<Insect> insects;
    /** A tektonon található gombatestet tárolja. */
    protected Mushroom mushroom;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public Tecton() {
        neighbors = new ArrayList<>();
        mycelia = new ArrayList<>();
        spores = new ArrayList<>();
        insects = new ArrayList<>();
        mushroom = null;

        Controller.registerActiveObject(this);
        Controller.registerTecton(this);
    }
    // #endregion

    // #region GETTERS-SETTERS

    /**
     * Visszadja a tektonon található spórák listáját.
     */
    public List<Spore> getSpores() {
        return spores;
    }

    /**
     * Hozzáad egy szomszédot a tektonhoz
     * 
     * @param tecton az új szomszéd
     */
    public void addNeighbor(Tecton tecton) {
        if (!neighbors.contains(tecton))
            neighbors.add(tecton);
    }

    /**
     * Lekéri a tekton szomszédait
     * 
     * @return a szomszédok egy listában
     */
    public List<Tecton> getNeighbors() {
        return neighbors;
    }

    /**
     * Eltávolítja a tekton egy szomszédját
     * 
     * @param tecton az eltávolítandó tekton
     */
    public void removeNeighbor(Tecton tecton) {
        neighbors.remove(tecton);
    }

    /**
     * Hozzáad a tektonhoz egy gombafonalat.
     * 
     * @param mycelium a hozzáadandó gombafonal.
     */
    public void addMycelium(Mycelium mycelium) {
        mycelia.add(mycelium);
    }

    /**
     * Lekéri a tektonhoz csatlakozó gombafonalakat.
     * 
     * @return a gombafonalak egy listában.
     */
    public List<Mycelium> getMycelia() {
        return mycelia;
    }

    /**
     * Hozzáadja a tektonhoz a paraméterként kapott rovart.
     *
     * @param insect a hozzáadandó rovar.
     */
    public void addInsect(Insect insect) {
        insects.add(insect);
    }

    /**
     * Eltávolítja a tektonról a paraméterként kapott rovart.
     * 
     * @param insect az eltávolítandó rovar.
     */
    public void removeInsect(Insect insect) {
        insects.remove(insect);
    }

    /**
     * Eltávolítja a tektonról a gombatestet.
     */
    public void removeMushroom() {
        mushroom = null;
    }

    /**
     * Eltávolítja a tektonról a paraméterként kapott gombafonalat.
     * 
     * @param myc az eltávolítandó gombafonal.
     */
    public void removeMycelium(Mycelium myc) {
        mycelia.remove(myc);
    }

    /**
     * A paraméterként kapott spórát hozzáadja a tektonhoz.
     * 
     * @param spore a hozzáadandó rovar.
     */
    public void addSpore(Spore spore) {
        spores.add(spore);
    }

    /**
     * Eltávolítja és visszaadja a legfelső spórát (ha van).
     * 
     * @return az eltávolított spóra vagy null.
     */

    public Spore takeSpore() {
        return spores.isEmpty() ? null : spores.remove(spores.size() - 1);
    }

    /**
     * Beállítja a tektonon lévő gombatestet.
     * 
     * @param mushroom a gombatest.
     */
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    /**
     * Visszaadja a tektonon található gombatestet.
     * 
     * @return a tektonon található {@ref Mushroom}, vagy {@code null}.
     */
    public Mushroom getMushroom() {
        return mushroom;
    }

    /**
     * Megadja, hogy a tekton életben tartja-e a gombatesthez nem csatlakozó gombafonalakat.
     */
    public boolean keepsMyceliumAlive() {
        return false;
    }

    // #endregion
    // #region FUNCTIONS

    /**
     * Feltölti a tektont a paraméterként kapott objektumokkal.
     * 
     * @param spores    a tekton spórái.
     * @param mushroom  a tektonon található gombatest (vagy null).
     * @param insects   a tektonon álló rovarok.
     * @param neighbors a tekton szomszédai.
     */
    public void fillWithStuff(List<Spore> spores, Mushroom mushroom, List<Insect> insects, List<Tecton> neighbors) {
        this.spores.addAll(spores);

        this.insects.addAll(insects);
        insects.forEach(x -> x.setLocation(this));

        this.mushroom = mushroom;
        if (mushroom != null)
            mushroom.setLocation(this);

        this.neighbors.addAll(neighbors);
        neighbors.forEach(x -> x.addNeighbor(this));

    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott tektonra vezet-e gombafonal.
     * 
     * @param tecton a cél tekton, amihez képest ellenőrzünk.
     * @return az ellenőrzés eredménye.
     */
    public boolean hasMyceliumTo(Tecton tecton) {
        for (Mycelium m : mycelia) {
            Tecton[] ends = m.getEnds();
            if (ends[0] == tecton || ends[1] == tecton) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy a kapott gombafaj tud-e gombafonalat növeszteni a tektonról vagy a tektonra.
     * 
     * @param fungus a szóban forgó gombafaj.
     * @return Igazat ad vissza, amennyiben a paraméterként kapott gombafajhoz lehet újabb gombafonalat növeszteni a
     *         gombafajtól.
     */
    public boolean canGrowMyceliumFrom(Fungus fungus) { // NOSONAR this param is needed in the specialized classes
        return true;
    }

    /**
     * Kiszámolja, hogy a paraméterként kapott tekton mekkora távolságra van.
     * 
     * @param tecton a távolság szempontból vizsgált tekton.
     * @return a távolság ugrásszámban, vagy {@link Integer.MAX_VALUE} ha nem elérhető.
     */
    public int distanceTo(Tecton tecton) {
        if (tecton == this) {
            return 0;
        }
        HashSet<Tecton> done = new HashSet<>();
        Queue<Tecton> toVisit = new LinkedList<>(neighbors);
        done.add(this);
        int dst = 1;
        while (!toVisit.isEmpty()) {
            HashSet<Tecton> newNeighbors = new HashSet<>();
            for (Tecton t : toVisit) {
                if (t == tecton) {
                    return dst;
                }
                for (Tecton neighbor : t.neighbors) {
                    if (!done.contains(neighbor)) {
                        done.add(neighbor);
                        newNeighbors.add(neighbor);
                    }
                }
            }
            toVisit.addAll(newNeighbors);
            dst++;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Létrehoz egy új példányt a tektonból. Azért kell, hogy a speciális tektonok duplikálhatók legyenek töréskor.
     * 
     * @return az új példány.
     */
    public Tecton newMe() {
        return new Tecton();
    }

    /**
     * Kiválogatja az adott fajhoz tartozó spórákat
     */
    private List<Spore> getSporesForSpecies(Fungus species) {
        return spores.stream().filter(x -> x.getSpecies() == species).toList();
    }

    /**
     * Lehetőség szerint növeszt egy gombatestet a tektonon.
     * 
     * @param fungus a gombatestet növesztő gombafaj.
     * @return az új gombatest, vagy null ha nem lehetséges a művelet.
     */
    public boolean growMushroom(Fungus fungus) {
        if (mushroom == null && mycelia.stream().anyMatch(x -> x.getSpecies() == fungus)) {
            var paralysed = insects.stream().filter(Insect::isParalysed).findFirst();
            if (paralysed.isPresent()) {
                paralysed.get().die();
            }
            var speciesSpores = getSporesForSpecies(fungus);
            if (speciesSpores.size() >= Mushroom.GROW_SPORES_REQUIRED) {
                spores.removeAll(speciesSpores.subList(0, Mushroom.GROW_SPORES_REQUIRED));
            }
            if (paralysed.isPresent() || speciesSpores.size() >= Mushroom.GROW_SPORES_REQUIRED) {
                mushroom = new Mushroom(fungus, this);
                return true;
            }
        }
        return false;
    }

    /**
     * Gombafonalat növeszt a megadott cél tektonra, ha lehetséges.
     * 
     * @param fungus a gombafonalhoz tartozó faj.
     * @param target a cél tekton.
     * @return a keletkezett gombafonal vagy null.
     */
    public boolean growMycelium(Fungus fungus, Tecton target) {
        if (canGrowMyceliumFrom(fungus) && target.canGrowMyceliumFrom(fungus)
                && ((mushroom != null && mushroom.getSpecies() == fungus)
                        || (mycelia.stream().anyMatch(x -> x.getSpecies() == fungus)))
                && neighbors.contains(target)) {
            new Mycelium(fungus, this, target);
            return true;
        }
        return false;
    }

    /**
     * Levezényli a tekton törési folyamatát.
     */
    private void tectonBreak() {
        Controller.unregisterActiveObject(this);
        Controller.unregisterTecton(this);

        while (!mycelia.isEmpty()) {
            mycelia.get(0).die();
        }
        for (Tecton n : neighbors) {
            n.removeNeighbor(this);
        }
        var t1 = newMe();
        var t2 = newMe();

        // weird solution to handle the case when there is only 1 element
        int sporeMid = Math.min(spores.size(), Math.max(spores.size() / 2, 1)); // NOSONAR this would throw an exception
                                                                                // if it was a clamp
        int insectMid = Math.min(insects.size(), Math.max(insects.size() / 2, 1)); // NOSONAR this would throw an
                                                                                   // exception if it was a clamp
        int neighborMid = Math.min(neighbors.size(), Math.max(neighbors.size() / 2, 1)); // NOSONAR this would throw an
                                                                                         // exception if it was a clamp

        var t1Neighbors = new ArrayList<>(neighbors.subList(0, neighborMid));
        t1Neighbors.add(t2);
        t1.fillWithStuff(spores.subList(0, sporeMid), null, insects.subList(0, insectMid), t1Neighbors);

        var t2Neighbors = new ArrayList<>(neighbors.subList(neighborMid, neighbors.size()));
        t2Neighbors.add(t1);
        t2.fillWithStuff(spores.subList(sporeMid, spores.size()), mushroom, insects.subList(insectMid, insects.size()),
                t2Neighbors);
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Időnként a tekton eltörik.
     */
    @Override
    public void tick(double dT) {
        if (RandomProvider.nextRand() > 1.0 - dT * BREAK_CHANCE_PER_SEC) {
            tectonBreak();
        }
    }
    // #endregion
}