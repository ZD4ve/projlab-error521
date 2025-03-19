package model;

import java.util.ArrayList;
import java.util.List;

public class Insect implements IActive {
    private double cooldown;
    private double speed;
    private int antiChewCount;
    private boolean isParalysed;
    private int score;
    private Tecton location;
    private List<InsectEffect> activeEffects;

    public Insect(Tecton location) {
        this.location = location;
        this.activeEffects = new ArrayList<>();
    }

    public void eatSpore() {
    }

    public Tecton[] getPotentialMoveTargets() {
        return null;
    }

    public Mycelium[] getPotentialChewTargets() {
        return null;
    }

    public void removeEffect(InsectEffect effect) {
    }

    public void moveTo(Tecton target) {
    }

    public void chewMycelium(Mycelium mycelium) {
    }

    @Override
    public void tick(double dT) {
    }
}
