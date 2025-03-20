package model;

import helper.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Insect implements IActive {
    private double cooldown;
    private double speed = 1;
    private int antiChewCount;
    private boolean isParalysed;
    private int score;
    private Tecton location;
    private List<InsectEffect> activeEffects;

    private static final double actionDuration = 10;

    public Insect(Tecton location) {
        Skeleton.printCall(this, List.of(location));
        this.location = location;
        this.activeEffects = new ArrayList<>();
        Skeleton.printReturn(this);
    }

    private void setCooldown(double cooldown) {
        Skeleton.printCall(this, List.of(cooldown));
        this.cooldown = cooldown;
        Skeleton.printReturn();
    }

    public void eatSpore() {
        Skeleton.printCall(this);
        if (isParalysed || cooldown > 0) {
            Skeleton.printReturn();
            return;
        }
        Spore sporeTaken = location.takeSpore();
        if (sporeTaken == null) {
            Skeleton.printReturn();
            return;
        }
        InsectEffect effect = sporeTaken.getEffect();
        if (effect == null) {
            Skeleton.printReturn();
            return;
        }
        effect.applyTo(this);
        setCooldown(actionDuration);
        score++;
        Skeleton.printReturn();
    }

    // TODO: nem követi seq diagramot, mert az szar XD
    public List<Tecton> getPotentialMoveTargets() {
        Skeleton.printCall(this);
        var ret = location.getNeighbors().stream().filter(t -> location.hasMyceliumTo(t)).toList();
        Skeleton.printReturn(ret);
        return ret;
    }

    public List<Mycelium> getPotentialChewTargets() {
        Skeleton.printCall(this);
        var ret = location.getMycelia();
        Skeleton.printReturn(ret);
        return ret;
    }

    public void removeEffect(InsectEffect effect) {
        Skeleton.printCall(this, List.of(effect));
        activeEffects.remove(effect);
        Skeleton.printReturn();
    }

    public void moveTo(Tecton target) {
        Skeleton.printCall(this, List.of(target));
        if (isParalysed || cooldown > 0) {
            Skeleton.printReturn();
            return;
        }
        if (location.hasMyceliumTo(target)) {
            location.removeInsect(this);
            location = target;
            location.addInsect(this);
            setCooldown(actionDuration);
        }
        Skeleton.printReturn();
    }

    public void chewMycelium(Mycelium mycelium) {
        Skeleton.printCall(this, List.of(mycelium));
        if (isParalysed || antiChewCount > 0 || cooldown > 0) {
            Skeleton.printReturn();
            return;
        }
        // TODO: check mycelium valid-e, seq-en nem szerepel
        mycelium.die();
        setCooldown(actionDuration);
        Skeleton.printReturn();
    }

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, List.of(dT));
        if (cooldown > 0)
            cooldown -= dT * speed;
        Skeleton.printReturn();
    }
}
