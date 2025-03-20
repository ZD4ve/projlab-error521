package model;

import helper.Skeleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tecton implements IActive {
    private List<Tecton> neighbors;
    protected List<Mycelium> mycelia;
    private List<Spore> spores;
    private List<Insect> insects;
    protected Mushroom mushroom;

    public Tecton() {
        neighbors = new ArrayList<>();
        mycelia = new ArrayList<>();
        spores = new ArrayList<>();
        insects = new ArrayList<>();
        mushroom = null;
    }

    // GETTERS-SETTERS--------------------------------------------------------------

    public void addNeighbor(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        neighbors.addLast(tecton);
        Skeleton.printReturn();
    }

    public List<Tecton> getNeighbors() {
        Skeleton.printCall(this);
        Skeleton.printReturn(neighbors);
        return neighbors;
    }

    public void removeNeighbor(Tecton tecton) {
        Skeleton.printCall(this, List.of(tecton));
        neighbors.remove(tecton);
        Skeleton.printReturn();
    }

    public List<Mycelium> getMycelia() {
        Skeleton.printCall(this);
        Skeleton.printReturn(mycelia);
        return mycelia;
    }

    public void addInsect(Insect insect) {
        Skeleton.printCall(this, List.of(insect));
        insects.add(insect);
        Skeleton.printReturn();
    }

    public void removeInsect(Insect insect) {
        Skeleton.printCall(this);
        insects.remove(insect);
        Skeleton.printReturn();
    }

    public void addSpore(Spore spore) {
        Skeleton.printCall(this, List.of(spore));
        spores.add(spore);
        Skeleton.printReturn();
    }

    public Spore takeSpore() {
        Skeleton.printCall(this);
        var ret = spores.isEmpty() ? null : spores.removeLast();
        Skeleton.printReturn(ret);
        return ret;
    }

    public void setMushroom(Mushroom mushroom) {
        Skeleton.printCall(this, List.of(mushroom));
        this.mushroom = mushroom;
        Skeleton.printReturn();
    }

    // -----------------------------------------------------------------------------

    public void fillWithStuff(List<Spore> spores, Mushroom mushroom, List<Insect> insects, List<Tecton> neighbors) {
        Skeleton.printCall(this, List.of(spores, mushroom, insects, neighbors));
        this.spores.addAll(spores);
        this.mushroom = mushroom;
        this.insects.addAll(insects);
        this.neighbors.addAll(neighbors);
        Skeleton.printReturn();
    }

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

    public boolean canGrowMyceliumFrom(Fungus fungus) { // NOSONAR this param is needed in the specialized classes
        Skeleton.printCall(this, List.of(fungus));
        Skeleton.printReturn(true);
        return true;
    }

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

    public Tecton newMe() {
        Skeleton.printCall(this);
        var nt = new Tecton();
        Skeleton.printReturn(nt);
        return nt;
    }

    public Mushroom growMushroom(Fungus fungus) {
        Skeleton.printCall(this);
        if (mushroom == null) {
            mushroom = new Mushroom(fungus, this);
            Skeleton.printReturn(mushroom);
            return mushroom;
        }
        Skeleton.printReturn(null);
        return null;
    }

    private boolean myceliumExists(Fungus fungus, Tecton t1, Tecton t2) {
        Skeleton.printCall(this, List.of(fungus, t1, t2));
        for (Mycelium m : mycelia) {
            if (m.getSpecies() == fungus && (Arrays.equals(m.getEnds(), new Tecton[] { t1, t2 })
                    || Arrays.equals(m.getEnds(), new Tecton[] { t2, t1 }))) {
                Skeleton.printReturn(true);
                return true;
            }
        }
        Skeleton.printReturn(false);
        return false;
    }

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
        Skeleton.printCall(this);
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

        var t1Neighbors = neighbors.subList(0, neighbors.size() / 2 - 1);
        t1Neighbors.add(t2);
        t1.fillWithStuff(spores.subList(0, spores.size() / 2 - 1),
                mushroom, insects.subList(0, insects.size() / 2 - 1),
                t1Neighbors);

        var t2Neighbors = neighbors.subList(0, neighbors.size() / 2 - 1);
        t2Neighbors.add(t1);
        t2.fillWithStuff(spores.subList(spores.size() / 2, spores.size() - 1), null,
                insects.subList(insects.size() / 2, insects.size() - 1),
                t2Neighbors);

        Skeleton.printReturn();
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