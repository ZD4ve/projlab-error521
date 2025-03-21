package model;

import helper.Skeleton;

import java.util.ArrayList;
import java.util.List;

// TODO: ASK hívások
public class Insect implements IActive {
    private double cooldown;
    private double speed = 1;
    private int antiChewCount;
    private boolean isParalysed;
    private int score;
    private Tecton location;
    private final List<InsectEffect> activeEffects = new ArrayList<>();

    private static final double ACTION_DURATION = 1;

    public Insect(Tecton location) {
        Skeleton.printCall(this, List.of(location));
        this.location = location;
        Skeleton.printReturn(this);
    }

    // #region GETTERS-SETTERS

    private void setCooldown(double cooldown) {
        Skeleton.printCall(this, List.of(cooldown));
        this.cooldown = cooldown;
        Skeleton.printReturn();
    }

    public int getAntiChewCount() {
        Skeleton.printCall(this);
        Skeleton.printReturn(antiChewCount);
        return antiChewCount;
    }

    public void setAntiChewCount(int antiChewCount) {
        Skeleton.printCall(this, List.of(antiChewCount));
        this.antiChewCount = antiChewCount;
        Skeleton.printReturn();
    }

    public void setIsParalysed(boolean isParalysed) {
        Skeleton.printCall(this, List.of(isParalysed));
        this.isParalysed = isParalysed;
        Skeleton.printReturn();
    }

    public double getSpeed() {
        Skeleton.printCall(this);
        Skeleton.printReturn(speed);
        return speed;
    }

    public void setSpeed(double speed) {
        Skeleton.printCall(this, List.of(speed));
        this.speed = speed;
        Skeleton.printReturn();
    }

    // nem követi seq diagramot, mert az szar XD
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

    public void addEffect(InsectEffect effect) {
        Skeleton.printCall(this, List.of(effect));
        activeEffects.add(effect);
        Skeleton.printReturn();
    }

    public void removeEffect(InsectEffect effect) {
        Skeleton.printCall(this, List.of(effect));
        activeEffects.remove(effect);
        Skeleton.printReturn();
    }

    public int getScore() {
        Skeleton.printCall(this);
        Skeleton.printReturn(score);
        return score;
    }

    // #endregion

    // #region FUNCTIONS

    public void eatSpore() {
        Skeleton.printCall(this);
        if (!isParalysed && cooldown <= 0) {
            Spore sporeTaken = location.takeSpore();
            if (sporeTaken != null) {
                score++;
                InsectEffect effect = sporeTaken.getEffect();
                if (effect != null) {
                    effect.applyTo(this);
                }
                setCooldown(ACTION_DURATION);
            }
        }
        Skeleton.printReturn();
    }

    public void moveTo(Tecton target) {
        Skeleton.printCall(this, List.of(target));
        if (!isParalysed && cooldown <= 0) {
            boolean moveValid = location.hasMyceliumTo(target);
            if (moveValid) {
                location.removeInsect(this);
                location = target;
                location.addInsect(this);
                setCooldown(ACTION_DURATION);
            }
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
        setCooldown(ACTION_DURATION);
        Skeleton.printReturn();
    }

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, List.of(dT));
        if (cooldown > 0)
            cooldown -= dT * speed;
        Skeleton.printReturn();
    }
    // #endregion
}
