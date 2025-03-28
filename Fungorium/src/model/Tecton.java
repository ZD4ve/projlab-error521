package model;

import helper.Skeleton;
import static helper.Skeleton.printCall;
import static helper.Skeleton.printReturn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <h3>Tekton</h3>
 * 
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
    /**
     * A tektonhoz tartozó szomszédokat tárolja el.
     */
    private List<Tecton> neighbors;

    /**
     * A tektonhoz tartozó gombafonalakat tárolja.
     */
    protected List<Mycelium> mycelia;

    /**
     * A tektonon található spórákat tárolja.
     */
    private List<Spore> spores;

    /**
     * A tektonon található spórákat tárolja.
     */
    private List<Insect> insects;

    /**
     * A tektonon található gombatestet tárolja.
     */
    protected Mushroom mushroom;

    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public Tecton() {
        printCall(this.getClass());
        neighbors = new ArrayList<>();
        mycelia = new ArrayList<>();
        spores = new ArrayList<>();
        insects = new ArrayList<>();
        mushroom = null;
        printReturn(this);
    }

    // GETTERS-SETTERS--------------------------------------------------------------

    /**
     * Hozzáad egy szomszédot a tektonhoz
     * 
     * @param tecton az új szomszéd
     */
    public void addNeighbor(Tecton tecton) {
        Skeleton.printCall(this, Arrays.asList(tecton));
        if (!neighbors.contains(tecton))
            neighbors.add(tecton);
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
        Skeleton.printCall(this, Arrays.asList(tecton));
        neighbors.remove(tecton);
        Skeleton.printReturn();
    }

    /**
     * Hozzáad a tektonhoz egy gombafonalat.
     * 
     * @param mycelium a hozzáadandó gombafonal.
     */
    public void addMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, Arrays.asList(mycelium));
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
        Skeleton.printCall(this, Arrays.asList(insect));
        insects.add(insect);
        Skeleton.printReturn();
    }

    /**
     * Eltávolítja a tektonról a paraméterként kapott rovart.
     * 
     * @param insect az eltávolítandó rovar.
     */
    public void removeInsect(Insect insect) {
        Skeleton.printCall(this, Arrays.asList(insect));
        insects.remove(insect);
        Skeleton.printReturn();
    }

    /**
     * Eltávolítja a tektonról a gombatestet.
     */
    public void removeMushroom() {
        Skeleton.printCall(this);
        mushroom = null;
        Skeleton.printReturn();
    }

    /**
     * Eltávolítja a tektonról a paraméterként kapott gombafonalat.
     * 
     * @param myc az eltávolítandó gombafonal.
     */
    public void removeMycelium(Mycelium myc) {
        Skeleton.printCall(this, Arrays.asList(myc));
        mycelia.remove(myc);
        Skeleton.printReturn();
    }

    /**
     * A paraméterként kapott spórát hozzáadja a tektonhoz.
     * 
     * @param spore a hozzáadandó rovar.
     */
    public void addSpore(Spore spore) {
        Skeleton.printCall(this, Arrays.asList(spore));
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
        Spore ret = spores.isEmpty() ? null : spores.remove(spores.size() - 1);
        Skeleton.printReturn(ret);
        return ret;
    }

    /**
     * Beállítja a tektonon lévő gombatestet.
     * 
     * @param mushroom a gombatest.
     */
    public void setMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, Arrays.asList(mushroom));
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
        Skeleton.printCall(this, Arrays.asList(spores, mushroom, insects, neighbors));
        this.spores.addAll(spores);
        this.insects.addAll(insects);
        insects.forEach(x -> x.setLocation(this));
        this.mushroom = mushroom;
        if (mushroom != null)
            mushroom.setLocation(this);
        this.neighbors.addAll(neighbors);
        neighbors.forEach(x -> x.addNeighbor(this));
        Skeleton.printReturn();

    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott tektonra vezet-e gombafonal.
     * 
     * @param tecton a cél tekton, amihez képest ellenőrzünk.
     * @return az ellenőrzés eredménye.
     */
    public boolean hasMyceliumTo(Tecton tecton) {
        Skeleton.printCall(this, Arrays.asList(tecton));
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
     *         újabb gombafonalat növeszteni a gombafajtól.
     */
    public boolean canGrowMyceliumFrom(Fungus fungus) { // NOSONAR this param is needed in the specialized classes
        Skeleton.printCall(this, Arrays.asList(fungus));
        Skeleton.printReturn(true);
        return true;
    }

    /**
     * Kiszámolja, hogy a paraméterként kapott tekton mekkora távolságra van.
     * 
     * @param tecton a távolság szempontból vizsgált tekton.
     * @return a távolság ugrásszámban, vagy {@link Integer.MAX_VALUE} ha nem
     *         elérhető.
     */
    public int distanceTo(Tecton tecton) {
        Skeleton.printCall(this, Arrays.asList(tecton));
        if (tecton == this) {
            Skeleton.printReturn(0);
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
                    Skeleton.printReturn(dst);
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
        Skeleton.addObject(nt, "tec");
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
        Skeleton.printCall(this, Arrays.asList(fungus));
        if (mushroom == null) {
            List<Spore> speciesSpores = getSporesForSpecies(fungus);
            boolean hasMycelium = mycelia.stream().anyMatch(x -> x.getSpecies() == fungus);
            // TODO: use actual spores needed
            int sporesNeeded = 1;
            if (speciesSpores.size() >= sporesNeeded && hasMycelium) {
                spores.removeAll(speciesSpores.subList(0, sporesNeeded));
                mushroom = new Mushroom(fungus, this);
                Skeleton.addObject(mushroom, "mush");
                Skeleton.printReturn(mushroom);
                return mushroom;
            }
        }
        Skeleton.printReturn(null);
        return null;
    }

    /**
     * Gombafonalat növeszt a megadott cél tektonra, ha lehetséges.
     * 
     * @param fungus a gombafonalhoz tartozó faj.
     * @param target a cél tekton.
     * @return a keletkezett gombafonal vagy null.
     */
    public Mycelium growMycelium(Fungus fungus, Tecton target) {
        Skeleton.printCall(this, Arrays.asList(fungus, target));
        if (canGrowMyceliumFrom(fungus) && target.canGrowMyceliumFrom(fungus) && ((mushroom != null && mushroom.getSpecies() == fungus) || (mycelia.stream().anyMatch(x -> x.getSpecies() == fungus))) && neighbors.contains(target)) {
            Mycelium mycelium = new Mycelium(fungus, this, target);
            Skeleton.addObject(mycelium, "mycelium");
            Skeleton.printReturn(mycelium);
            return mycelium;
        }
        Skeleton.printReturn(null);
        return null;
    }

    /**
     * Levezényli a tekton törési folyamatát.
     */
    private void tectonBreak() {
        Skeleton.printCall(this);
        while (!mycelia.isEmpty()) {
            mycelia.get(0).die();
        }
        for (Tecton n : neighbors) {
            n.removeNeighbor(this);
        }
        var t1 = newMe();
        var t2 = newMe();

        Skeleton.addObject(t1, "t1");
        Skeleton.addObject(t2, "t2");

        var t1Neighbors = new ArrayList<>(neighbors.subList(0, neighbors.size() / 2));
        t1Neighbors.add(t2);
        t1.fillWithStuff(spores.subList(0, Math.min(spores.size(), Math.max(spores.size() / 2, 1))), mushroom, insects.subList(0, insects.size() / 2), t1Neighbors);

        var t2Neighbors = new ArrayList<>(neighbors.subList(neighbors.size() / 2, neighbors.size()));
        t2Neighbors.add(t1);
        t2.fillWithStuff(spores.subList(Math.min(spores.size(), Math.max(spores.size() / 2, 1)), spores.size()), null, insects.subList(insects.size() / 2, insects.size()), t2Neighbors);
        Skeleton.printReturn();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Időnként a tekton eltörik.
     */
    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, Arrays.asList(dT));
        if (dT >= 1) {
            tectonBreak();
        }
        Skeleton.printReturn();
    }
}