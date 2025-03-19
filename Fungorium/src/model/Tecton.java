package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public boolean canGrowMyceliumFrom(Fungus fungus) {
        return true;
    }

    public int distanceTo(Tecton tecton) {
        return 0;
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
            if (m.getSpecies() == fungus && (Arrays.equals(m.getEnds(), new Tecton[] {t1, t2})||Arrays.equals(m.getEnds(), new Tecton[] {t2, t1}))) {
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

    @Override
    public void tick(double dT) {
        // TODO: logic to determine if tecton is breaking
        boolean breaking = false;
        if (breaking) {
            
        }
    }
}