package model;

import controller.Controller;
import java.util.*;

/**
 * <h3>Rovar</h3>
 * 
 * Kezeli a felhasználó által kezdeményezett spóra evés, mozgás és fonal elrágás akciókat, illetve ezek során kommunikál
 * a többi objektummal. A rovar csak bizonyos időközönként végezhet akciókat, ezt is követi és ellenőrzi.
 */
public class Insect implements IActive {
    // #region CONSTANTS
    /** Az akciók végrehajtásának ideje másodpercben. */
    private static final double ACTION_DURATION = 3;
    // #endregion

    // #region ASSOCIATIONS
    /** Ezen a tektonon áll éppen a rovar. */
    private Tecton location;
    /** A rovarra jelenleg ható hatások. */
    private final List<InsectEffect> activeEffects = new ArrayList<>();
    /** A rovar kolóniája. */
    private final Colony colony;
    // #endregion

    // #region ATTRIBUTES
    /** Mennyi idő múlva végezhető a következő akció. */
    private double cooldown;
    /** A rovar sebesség módosítója: az aktuális és alapértelmezett sebesség hányadosa. */
    private double speed = 1;
    /** Hány darab fonalrágás tiltó hatás alatt van a rovar. */
    private int antiChewCount;
    /** A rovar bénító hatás alatt van-e. */
    private boolean isParalysed;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Rovar létrehozása és elhelyezése egy tektonon.
     * 
     * Beállítja a rovar kezdeti pozícióját és kolóniáját, majd a tektonhoz és a kolóniához is hozzáadja a rovart, a
     * Tecton::addInsect és a Colony::born metódusok segítségével. Feliratkozik az aktív objektumok közé.
     * 
     * @param location a rovar kezdeti helye
     * @param colony   a rovar kolóniája
     */
    public Insect(Tecton location, Colony colony) {
        this.location = location;
        location.addInsect(this);
        this.colony = colony;
        colony.born(this);
        Controller.registerActiveObject(this);
    }

    /**
     * @deprecated ONLY FOR SKELETON BACKWARD COMPATIBILITY
     */
    @Deprecated(since = "proto", forRemoval = false)
    public Insect(Tecton location) { // NOSONAR skeleton működéshez szükséges
        this(location, new Colony());
    }

    // #endregion
    // #region GETTERS-SETTERS

    /**
     * Visszaadja a rovarra jelenleg ható hatásokat.
     * 
     * @return a hatások listája
     */
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
     * Visszaadja, hány fonalrágás tiltó hatás alatt áll a rovar. Ha >0, akkor a rovar nem tud fonalat rágni.
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
     * A rovar bénaságának lekérdezése.
     * 
     * @return bénaság állapota
     */
    public boolean isParalysed() {
        return isParalysed;
    }

    /**
     * A rovar bénító hatás beállítása.
     */
    public void setIsParalysed(boolean isParalysed) {
        this.isParalysed = isParalysed;
    }

    /**
     * Új rovarhatás hozzáadása.
     * 
     * @param effect az új hatás
     */
    public void addEffect(InsectEffect effect) {
        activeEffects.add(effect);
    }

    /**
     * Rovarhatás eltávolítása.
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
     * A rovar helyének lekérdezése.
     * 
     * @return a Tecton, ahol a rovar áll
     */
    public Tecton getLocation() {
        return location;
    }

    // TODO DOC
    public Colony getColony() {
        return colony;
    }

    /**
     * A rovar kész-e az akcióra.
     * 
     * @return lejárt-e a várakozási idő
     */
    private boolean ready() {
        return cooldown <= 0;
    }

    /**
     * Megkeresi azokat a fonal, amelyeket a rovar elrághat, azaz a jelenlegi tektonjáról induló fonalakat.
     * 
     * @return a lehetséges célpontok listája
     */
    public List<Mycelium> getPotentialChewTargets() {
        return location.getMycelia();
    }

    // #endregion
    // #region FUNCTIONS

    /**
     * Megkeresi azokat a tektonokat, melyre a rovar képes átlépni, vagyis vezet oda gombafonal a jelenlegi
     * pozíciójáról.
     * 
     * @return a lehetséges célpontok listája
     */
    public List<Tecton> getPotentialMoveTargets() {
        return location.getNeighbors().stream().filter(t -> location.hasMyceliumTo(t)).toList();
    }

    /**
     * A rovar megpróbál elfogyasztani egy spórát azon a tektonon, amelyen áll. A visszatérési érték a művelet
     * sikerességét jelzi.
     * 
     * A művelet sikertelen, ha a rovar bénult, a tektonon nincs spóra, vagy ha a várakozási idő még nem járt le. Siker
     * esetén a rovar a spórát a Tecton::takeSpore metódus segítségével kapja meg, majd a kolónia pontszáma növekszik a
     * Colony::addScore metódus segítségével. A spóra hatását a Spore::getEffect metódussal lekéri, majd a kapott hatás
     * a rovarra kerül (ha a spórának van hatása), illetve meghívódik az InsectEffect::applyTo metódus is. Siker esetén
     * a rovar a várakozási idejét beállítja.
     * 
     * @return sikeresség
     */
    public boolean eatSpore() {
        if (!isParalysed && ready()) {
            Spore sporeTaken = location.takeSpore();
            if (sporeTaken != null) {
                colony.addScore();
                InsectEffect effect = sporeTaken.getEffect();
                if (effect != null) {
                    effect.applyTo(this);
                    activeEffects.add(effect);
                }
                setCooldown(ACTION_DURATION);
                return true;
            }
        }
        return false;
    }

    /**
     * A rovar megpróbál a target tektonra mozogni. A visszatérési érték a művelet sikerességét jelzi.
     * 
     * A művelet sikertelen, ha a rovar bénult, a cél tektonra nem vezet gombafonal, vagy ha a várakozási idő még nem
     * járt le. Siker esetén a rovar a forrás tektonról eltávolítja magát (Tecton::removeInsect), és hozzáadja magát
     * (Tecton::addInsect) a cél tektonhoz és frissíti a saját belső állapotát is. Siker esetén a rovar a várakozási
     * idejét beállítja.
     * 
     * @param target cél tekton
     * @return sikeresség
     */
    public boolean moveTo(Tecton target) {
        if (isParalysed || !ready())
            return false;
        if (!location.hasMyceliumTo(target))
            return false;
        location.removeInsect(this);
        location = target;
        location.addInsect(this);
        setCooldown(ACTION_DURATION);
        return true;
    }

    /**
     * A rovar megpróbálja elrágni a target gombafonalat. A visszatérési érték a művelet sikerességét jelzi.
     * 
     * A művelet sikertelen, ha a rovar bénult, a gombafonal nem a jelenlegi tektonon van, vagy ha a várakozási idő még
     * nem járt le. Azt, hogy a gombafonal a jelenlegi tektonon van-e, a Tecton::getMycelia metódus segítségével
     * határozza meg. Siker esetén a rovar a gombafonalat a Mycelium::chew metódus segítségével rágja el, majd rovar a
     * saját várakozási idejét beállítja.
     * 
     * @param mycelium a fonal, amit el akar rágni
     * @return sikeresség
     */
    public boolean chewMycelium(Mycelium mycelium) {
        if (isParalysed || antiChewCount > 0 || !ready() || !location.getMycelia().contains(mycelium))
            return false;
        mycelium.chew();
        setCooldown(ACTION_DURATION);
        return true;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Várakozási idő csökkentése a jelenlegi sebességgel.
     */
    @Override
    public void tick(double dT) {
        if (cooldown > 0)
            cooldown -= dT * speed;
    }

    /**
     * A rovart osztódásra kényszeríti: létrejön egy új rovar vele megegyező tektonon és kolóniában.
     */
    public void split() {
        new Insect(location, colony);
    }

    /**
     * A rovar elpusztul.
     * 
     * Kilép a kolóniából (Colony::died), eltűnik a tektonról (Tecton::removeInsect). Leiratkozik az aktív objektumok
     * közül. A rajta lévő hatásokat a InsectEffect::wearOff metódussal sorra megszünteti.
     */
    public void die() {
        Controller.unregisterActiveObject(this);
        colony.died(this);
        while (!activeEffects.isEmpty()) {
            activeEffects.get(0).wearOff();
        }
        location.removeInsect(this);
    }
    // #endregion
}
