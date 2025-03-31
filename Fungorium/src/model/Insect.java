package model;

import helper.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * <h3>Rovar</h3>
 * 
 * Kezeli a felhasználó által kezdeményezett spóra evés, mozgás és fonal elrágás
 * akciókat, illetve ezek során kommunikál a többi objektummal. A rovar csak
 * bizonyos időközönként végezhet akciókat, ezt is követi és ellenőrzi.
 */
public class Insect implements IActive {
    // ASSOCIATIONS
    /** ezen a tektonon áll éppen a rovar. */
    private Tecton location;
    /** épp a rovarra ható hatások */
    private final List<InsectEffect> activeEffects = new ArrayList<>();

    // ATTRIBUTES
    /** mennyi idő múlva végezhető a következő akció */
    private double cooldown;
    /** a rovar sebessége a normálishoz képest */
    private double speed = 1;
    /** hány darab fonalrágás tiltó hatás alatt van a rovar */
    private int antiChewCount;
    /** a rovar bénító hatás alatt van-e */
    private boolean isParalysed;
    /** hány spórát evett meg a rovar */
    private int score;

    private static final double ACTION_DURATION = 1;

    /**
     * Rovar létrehozása és elhelyezése egy tektonon.
     * 
     * @param location a rovar kezdeti helye
     */
    public Insect(Tecton location) {
        this.location = location;
        location.addInsect(this);
    }

    // #region GETTERS-SETTERS

    public List<InsectEffect> getActiveEffects() {
        return activeEffects;
    }

    /**
     * Beállítja a rovar várakozási idejét.
     * 
     * @param cooldown a várakozási idő
     */
    private void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * A rovar sebesség módosító értékét adja vissza.
     * 
     * @return a sebesség módosító értéke
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Beállítja a rovar sebesség módosító értékét.
     * 
     * @param speed a sebesség módosító értéke
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Ha >0, akkor a rovar nem tud fonalat rágni.
     * 
     * @return hány darab fonalrágás tiltó hatás alatt van a rovar
     */
    public int getAntiChewCount() {
        return antiChewCount;
    }

    /**
     * Beállítja a rovar fonalrágás tiltó hatásának számát.
     * 
     * @param antiChewCount fonalrágás tiltó hatások száma
     */
    public void setAntiChewCount(int antiChewCount) {
        this.antiChewCount = antiChewCount;
    }

    /**
     * A rovar bénító hatás beállítása
     */
    public void setIsParalysed(boolean isParalysed) {
        this.isParalysed = isParalysed;
    }

    /**
     * Rovarhatás letárolása
     * 
     * @param effect az új hatás
     */
    public void addEffect(InsectEffect effect) {
        activeEffects.add(effect);
    }

    /**
     * Rovarhatás eltávolítása
     * 
     * @param effect a lejárt hatás
     */
    public void removeEffect(InsectEffect effect) {
        activeEffects.remove(effect);
    }

    /**
     * Beállítja a rovar helyét.
     * 
     * @param location a rovar új helye
     */
    public void setLocation(Tecton location) {
        this.location = location;
    }

    /**
     * A rovarász pontszámának lekérdezése.
     * 
     * @return rovarász jelenlegi pontszáma
     */
    public int getScore() {
        return score;
    }

    /**
     * A rovar kész-e az akcióra.
     * 
     * @return lejárt-e a várakozási idő
     */
    private boolean ready() {
        return Skeleton.ask("A rovarnak lejárt a várakozási ideje?");
        // return cooldown <= 0;
    }

    // #endregion

    // #region FUNCTIONS

    /**
     * Megkeresi azokat a tektonokat, melyre a rovar képes átlépni, vagyis vezet oda
     * gombafonal a jelenlegi pozíciójáról.
     * 
     * @return a lehetséges célpontok listája
     */
    public List<Tecton> getPotentialMoveTargets() {
        // nem követi seq diagramot, mert az szar XD
        var ret = location.getNeighbors().stream().filter(t -> location.hasMyceliumTo(t)).toList();
        return ret;
    }

    /**
     * Megkeresi azokat a fonal, amelyeket a rovar elrághat.
     * 
     * @return a lehetséges célpontok listája
     */
    public List<Mycelium> getPotentialChewTargets() {
        var ret = location.getMycelia();
        return ret;
    }

    /**
     * A rovar megpróbál elfogyasztani egy spórát azon a tektonon, amelyen áll. A
     * visszatérési érték a művelet sikerességét jelzi.
     * 
     * @return sikeresség
     */
    public boolean eatSpore() {
        boolean success = false;
        if (!isParalysed && ready()) {
            Spore sporeTaken = location.takeSpore();
            if (sporeTaken != null) {
                score++;
                success = true;
                InsectEffect effect = sporeTaken.getEffect();
                if (effect != null) {
                    effect.applyTo(this);
                }
                setCooldown(ACTION_DURATION);
            }
        }
        return success;
    }

    /**
     * A rovar megpróbál a target tektonra mozogni. A visszatérési érték a művelet
     * sikerességét jelzi.
     * 
     * @param target cél tekton
     * @return sikeresség
     */
    public boolean moveTo(Tecton target) {
        boolean success = false;
        if (!isParalysed && ready()) {
            boolean moveValid = location.hasMyceliumTo(target);
            if (moveValid) {
                success = true;
                location.removeInsect(this);
                location = target;
                location.addInsect(this);
                setCooldown(ACTION_DURATION);
            }
        }
        return success;
    }

    /**
     * A rovar megpróbálja elrágni a target gombafonalat. A visszatérési érték a
     * művelet sikerességét jelzi.
     * 
     * @param mycelium a fonal, amit el akar rágni
     * @return sikeresség
     */
    public boolean chewMycelium(Mycelium mycelium) {
        if (isParalysed || antiChewCount > 0 || !ready()) {
            return false;
        }
        mycelium.die();
        setCooldown(ACTION_DURATION);
        return true;
    }

    @Override
    public void tick(double dT) {
        if (cooldown > 0)
            cooldown -= dT * speed;
    }

    // TODO DOC
    public void split() {
        new Insect(location);
        // TODO: register to the same controller
    }
    // #endregion
}
