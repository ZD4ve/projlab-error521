package model;

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

    public void addNeighbor(Tecton tecton) {
        neighbors.addLast(tecton);
    }

    public void addInsect(Insect insect) {
        insects.add(insect);
    }

    public void addSpore(Spore spore) {
        spores.add(spore);
    }

    public void removeNeighbor(Tecton tecton) {
        neighbors.remove(tecton);
    }

    public void removeInsect(Insect insect) {
        insects.remove(insect);
    }

    public void fillWithStuff(List<Spore> spores, Mushroom mushroom, List<Insect> insects, List<Tecton> neighbors) {
        this.spores.addAll(spores);
        this.mushroom = mushroom;
        this.insects.addAll(insects);
        this.neighbors.addAll(neighbors);
    }

    public boolean hasMyceliumTo(Tecton tecton) {
        for (Mycelium m : mycelia) {
            Tecton[] ends = m.getEnds();
            if (ends[0] == tecton || ends[1] == tecton) {
                return true;
            }
        }
        return false;
    }

    public boolean canGrowMyceliumFrom(Fungus fungus) { // NOSONAR this param is needed in the specialized classes
        return true;
    }

    public int distanceTo(Tecton tecton) {
        if (tecton == this) {
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
        return Integer.MAX_VALUE;
    }

    public Tecton newMe() {
        return new Tecton();
    }

    public void growMushroom(Fungus fungus) {
        if (mushroom == null) {
            mushroom = new Mushroom(fungus, this);
        }
    }

    private boolean myceliumExists(Fungus fungus, Tecton t1, Tecton t2) {
        for (Mycelium m : mycelia) {
            if (m.getSpecies() == fungus && (Arrays.equals(m.getEnds(), new Tecton[] { t1, t2 })
                    || Arrays.equals(m.getEnds(), new Tecton[] { t2, t1 }))) {
                return true;
            }
        }
        return false;
    }

    public void growMycelium(Fungus fungus, Tecton target) {
        if (canGrowMyceliumFrom(fungus) && target.canGrowMyceliumFrom(fungus)
                && !myceliumExists(fungus, this, target)) {
            new Mycelium(fungus, this, target);
        }
    }

    public void tectonBreak() {
        for (Tecton n : neighbors) {
            n.removeNeighbor(this);
        }
        for (Mycelium m : mycelia) {
            m.die();
        }
        var t1 = newMe();
        var t2 = newMe();

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
    }

    @Override
    public void tick(double dT) {
        // TODO: logic to detemine if tecton is breaking
        boolean breaking = false;
        if (breaking) {
            tectonBreak();
        }
    }
}