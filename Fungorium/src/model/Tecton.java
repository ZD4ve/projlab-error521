package model;

import helper.Skeleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Felelőssége a saját szomszédainak, valamint a rajta található további
 * objektumok nyilvántartása. Felelőssége a gombatestek, gombafonalak
 * növesztéséhez szükséges ellenőrzések elvégzése, valamint ezek halálakor azon
 * objektumok törlése. Felelőssége a rovarokkal való kapcsolatának
 * karbantartása, azok hozzáadása, valamint törlése, spóra hozzáadásának
 * kezelése, spóra átadása az azt megevő rovarnak. Felelőssége a tekton
 * törésének kezdeményezése és kezelése, ezalatt a rajta található objektumok
 * elosztása, valamint saját tulajdonságainak lemásolása a keletkező új
 * tektonokra.
 */
public class Tecton implements IActive {
    private List<Tecton> neighbors;
    protected List<Mycelium> mycelia;
    private List<Spore> spores;
    private List<Insect> insects;
    protected Mushroom mushroom;

    /**
     * Inicializálja az új tektont.
     *
     */
    public Tecton() {
        neighbors = new ArrayList<>();
        mycelia = new ArrayList<>();
        spores = new ArrayList<>();
        insects = new ArrayList<>();
        mushroom = null;
    }

    // GETTERS-SETTERS--------------------------------------------------------------

    /**
     * Hozzáad egy szomszédot a tektonhoz
     * 
     * @param tecton az új szomszéd
     */
    public void addNeighbor(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        neighbors.addLast(tecton);
        Skeleton.printReturn();
    }

    /**
     * Lekéri a tekton szomszédait
     * 
     * @return a szomszédok egy listában
     */
    public List<Tecton> getNeighbors() {
        Skeleton.printCall(this);
        Skeleton.printReturn(neighbors);
        return neighbors;
    }

    /**
     * Eltávolítja a tekton egy szomszédját
     * 
     * @param tecton az eltávolítandó tekton
     */
    public void removeNeighbor(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        neighbors.remove(tecton);
        Skeleton.printReturn();
    }

    /**
     * Hozzáad a tektonhoz egy gombafonalat.
     * @param mycelium a hozzáadandó gombafonal.
     */
    public void addMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, List.of(mycelium));
        mycelia.add(mycelium);
        Skeleton.printReturn();
    }

    /**
     * Lekéri a tektonhoz csatlakozó gombafonalakat.
     * 
     * @return a gombafonalak egy listában.
     */
    public List<Mycelium> getMycelia() {
        Skeleton.printCall(this);
        Skeleton.printReturn(mycelia);
        return mycelia;
    }

    /**
     * Hozzáadja a tektonhoz a paraméterként kapott rovart.
     *
     * @param insect a hozzáadandó rovar.
     */
    public void addInsect(Insect insect) {
        Skeleton.printCall(this, List.of(insect));
        insects.add(insect);
        Skeleton.printReturn();
    }

    /**
     * Eltávolítja a tektonról a paraméterként kapott rovart.
     * 
     * @param insect az eltávolítandó rovar.
     */
    public void removeInsect(Insect insect) {
        Skeleton.printCall(this);
        insects.remove(insect);
        Skeleton.printReturn();
    }

    /**
     * A paraméterként kapott spórát hozzáadja a tektonhoz.
     * 
     * @param spore a hozzáadandó rovar.
     */
    public void addSpore(Spore spore) {
        Skeleton.printCall(this, List.of(spore));
        spores.add(spore);
        Skeleton.printReturn();
    }

    /**
     * Eltávolítja és visszaadja a legfelső spórát (ha van).
     * 
     * @return az eltávolított spóra vagy null.
     */

    public Spore takeSpore() {
        Skeleton.printCall(this);
        var ret = spores.isEmpty() ? null : spores.removeLast();
        Skeleton.printReturn(ret);
        return ret;
    }

    /**
     * Beállítja a tektonon lévő gombatestet.
     * 
     * @param mushroom a gombatest.
     */
    public void setMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, List.of(mushroom));
        this.mushroom = mushroom;
        Skeleton.printReturn();
    }

    // -----------------------------------------------------------------------------

    /**
     * Feltölti a tektont a paraméterként kapott objektumokkal.
     * 
     * @param spores    a tekton spórái.
     * @param mushroom  a tektonon található gombatest (vagy null).
     * @param insects   a tektonon álló rovarok.
     * @param neighbors a tekton szomszédai.
     */
    public void fillWithStuff(List<Spore> spores, Mushroom mushroom, List<Insect> insects, List<Tecton> neighbors) {
        Skeleton.printCall(this, List.of(spores, mushroom, insects, neighbors));
        this.spores.addAll(spores);
        this.mushroom = mushroom;
        this.insects.addAll(insects);
        this.neighbors.addAll(neighbors);
        Skeleton.printReturn();
    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott tektonra vezet-e gombafonal.
     * 
     * @param tecton a cél tekton, amihez képest ellenőrzünk.
     * @return az ellenőrzés eredménye.
     */
    public boolean hasMyceliumTo(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        for (Mycelium m : mycelia) {
            Tecton[] ends = m.getEnds();
            if (ends[0] == tecton || ends[1] == tecton) {
                Skeleton.printReturn(true);
                return true;
            }
        }
        Skeleton.printReturn(false);
        return false;
    }

    /**
     * Ellenőrzi, hogy a kapott gombafaj tud-e gombafonalat növeszteni a tektonról
     * vagy a tektonra.
     * 
     * @param fungus a szóban forgó gombafaj.
     * @return Igazat ad vissza, amennyiben a paraméterként kapott gombafajhoz lehet
     *         újabb gombafonalat növeszteni és a tektonon található gombafonal
     *         vagy gombatest a paraméterként kapott gombafajtól.
     */
    public boolean canGrowMyceliumFrom(Fungus fungus) { // NOSONAR this param is needed in the specialized classes
        Skeleton.printCall(this, List.of(fungus));
        Skeleton.printReturn(true);
        return true;
    }

    /**
     * Visszaadja, hogy a paraméterként kapott tekton mekkora távolságra van.
     * 
     * @param tecton a távolság szempontból vizsgált tekton.
     * @return a távolság ugrásszámban, vagy {@link Integer.MAX_VALUE} ha nem
     *         elérhető.
     */
    public int distanceTo(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        if (tecton == this) {
            Skeleton.printReturn(0);
            return 0;
        }
        HashSet<Tecton> done = new HashSet<>();
        Queue<Tecton> toVisit = new LinkedList<>();
        done.add(this);
        int dst = 1;
        while (!toVisit.isEmpty()) {
            HashSet<Tecton> nN = new HashSet<>();
            for (Tecton t : toVisit) {
                for (Tecton neighbor : t.neighbors) {
                    if (neighbor == tecton) {
                        Skeleton.printReturn(dst);
                        return dst;
                    }
                    if (!done.contains(neighbor)) {
                        done.add(neighbor);
                        nN.add(neighbor);
                    }
                }
            }
            toVisit.addAll(nN);
            dst++;
        }
        Skeleton.printReturn(Integer.MAX_VALUE);
        return Integer.MAX_VALUE;
    }

    /**
     * Létrehoz egy új példányt a tektonból. Azért kell, hogy a speciális tektonok
     * duplikálhatók legyenek töréskor.
     * 
     * @return az új példány.
     */
    public Tecton newMe() {
        Skeleton.printCall(this);
        var nt = new Tecton();
        Skeleton.printReturn(nt);
        return nt;
    }

    private List<Spore> getSporesForSpecies(Fungus species) {
        return spores.stream().filter(x -> x.getSpecies() == species).toList();
    }

    /**
     * Lehetőség szerint növeszt egy gombatestet a tektonon.
     * 
     * @param fungus a gombatestet növesztő gombafaj.
     * @return az új gombatest, vagy null ha nem lehetséges a művelet.
     */
    public Mushroom growMushroom(Fungus fungus) {
        Skeleton.printCall(this);
        if (mushroom == null) {
            List<Spore> speciesSpores = getSporesForSpecies(fungus);
            boolean hasMycelium = mycelia.stream().anyMatch(x -> x.getSpecies() == fungus);
            // TODO: use actual spores needed
            int sporesNeeded = 1;
            if (speciesSpores.size() >= sporesNeeded && hasMycelium) {
                spores.removeAll(speciesSpores.subList(0, sporesNeeded));
                mushroom = new Mushroom(fungus, this);
                Skeleton.printReturn(null);
                return mushroom;
            }
        }
        Skeleton.printReturn(null);
        return null;
    }

    private boolean myceliumExists(Fungus fungus, Tecton t1, Tecton t2) {
        for (Mycelium m : mycelia) {
            if (m.getSpecies() == fungus && (Arrays.equals(m.getEnds(), new Tecton[] { t1, t2 })
                    || Arrays.equals(m.getEnds(), new Tecton[] { t2, t1 }))) {
                Skeleton.printReturn(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Gombafonalat növeszt a megadott cél tektonra, ha lehetséges.
     * @param fungus a gombafonalhoz tartozó faj.
     * @param target a cél tekton.
     * @return a keletkezett gombafonal vagy null.
     */
    public Mycelium growMycelium(Fungus fungus, Tecton target) {
        Skeleton.printCall(this, List.of(fungus, target));
        if (canGrowMyceliumFrom(fungus) && target.canGrowMyceliumFrom(fungus)
                && !myceliumExists(fungus, this, target)) {
            Mycelium mycelium = new Mycelium(fungus, this, target);
            Skeleton.addObject(mycelium, "my1");
            Skeleton.printReturn(mycelium);
            return mycelium;
        }
        Skeleton.printReturn(null);
        return null;
    }

    private void tectonBreak() {
        for (Tecton n : neighbors) {
            n.removeNeighbor(this);
        }
        for (Mycelium m : mycelia) {
            m.die();
        }
        var t1 = newMe();
        var t2 = newMe();

        Skeleton.addObject(t1, "t1");
        Skeleton.addObject(t2, "t2");

        var t1Neighbors = neighbors.subList(0, neighbors.size() / 2);
        t1Neighbors.add(t2);
        t1.fillWithStuff(spores.subList(0, spores.size() / 2),
                mushroom, insects.subList(0, insects.size() / 2),
                t1Neighbors);

        var t2Neighbors = neighbors.subList(neighbors.size() / 2, neighbors.size() - 1);
        t2Neighbors.add(t1);
        t2.fillWithStuff(spores.subList(spores.size() / 2, spores.size() - 1), null,
                insects.subList(insects.size() / 2, insects.size() - 1),
                t2Neighbors);
    }

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, List.of(dT));
        if (dT >= 1) {
            tectonBreak();
        }
        Skeleton.printReturn();
    }
}