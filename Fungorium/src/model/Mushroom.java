package model;

import controller.Controller;

/**
 * <h3>Gombatest</h3>
 * 
 * Felelőssége a saját fejlődésének kezelése, illetve a saját halálának feltételeinek beteljesülésének ellenőrzése.
 * Halálakor értesíti a megfelelő objektumokat, hogy ők is le tudják kezelni a halálának következményeit. Felelőssége
 * továbbá a spóraszórás kezdeményezése, illetve az ehhez szükséges ellenőrzések elvégzése.
 */
public class Mushroom implements IActive {
    // #region CONSTANTS
    /** Mennyi spóra kell egy gombafej növesztéséhez. */
    public static final int GROW_SPORES_REQUIRED = 3;
    /** Mennyi spórát tud szórni a gombafej. */
    public static final int MAX_SPORE_BURSTS = 10;
    /** Mennyi ideig nem tud újra spórát szórni a gombatest. */
    public static final int SPORE_BURST_DELAY = 3;
    /** Mennyi idő alatt válik fejletté a gombatest */
    public static final int GROW_TIME = 30;

    // #endregion

    // #region ASSOCIATIONS
    /** Gombatesthez tartozó faj. */
    private final Fungus species;
    /** Az a tekton amelyen a gombatest található. */
    private Tecton location;
    // #endregion

    // #region ATTRIBUTES
    /** Mekkora a gomba hatótávja. */
    private int range;
    /** Fejlett-e a gombatest. */
    private boolean isGrown;
    /** Mennyi időnek kell még eltelnie, hogy ismét szórhasson spórát. */
    private double cooldown;
    /** Mennyi időnek kell eltelnie, hogy a gomba fejletté váljon. */
    private double growCooldown;
    /** Mennyi spórát szórt eddig a gombafej. */
    private int burstsLeft;

    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy fungus fajú gombatestet a location tektonon. Beállítja a gombatest gombafaját, helyét. Beállítja,
     * hogy a gombatest mennyi spórát tud lőni, spóraszórás után mennyi ideig nem tud lőni, mennyi idő fejletté válnia.
     * Hozzáadja a gombafajhoz és a tektonhoz a gombát (Fungus::addMushroom, Tecton::setMushroom). Feliratkozik az aktív
     * objektumok közé.
     * 
     * @param fungus   a gombatesthez tartozó gombafaj.
     * @param location a tekton, amin található.
     */
    public Mushroom(Fungus fungus, Tecton location) {
        this.species = fungus;
        this.location = location;
        burstsLeft = MAX_SPORE_BURSTS;
        cooldown = SPORE_BURST_DELAY;
        growCooldown = GROW_TIME;
        setIsGrown(false);
        fungus.addMushroom(this);
        location.setMushroom(this);
        Controller.registerActiveObject(this);
    }

    /**
     * Megegyezik a másik konstruktorral, de felülírja a gombatest lehetséges spóraszórásainak számát.
     * 
     * @param fungus     a gombatesthez tartozó gombafaj.
     * @param location   a tekton, amin található.
     * @param burstsLeft a lehetséges spóraszórások száma.
     */
    public Mushroom(Fungus fungus, Tecton location, int burstsLeft) {
        this.species = fungus;
        this.location = location;
        this.burstsLeft = burstsLeft;
        fungus.addMushroom(this);
        location.setMushroom(this);
        cooldown = SPORE_BURST_DELAY;
        growCooldown = GROW_TIME;
        setIsGrown(false);
        Controller.registerActiveObject(this);
    }
    // #endregion

    // #region GETTERS-SETTERS
    /**
     * Visszaadja a spóra faját.
     * 
     * @return a spóra faja
     */
    public Fungus getSpecies() {
        return species;
    }

    /**
     * Visszaadja azt a tektont, amin tartózkodik.
     * 
     * @return a tartózkodási tektonja
     */
    public Tecton getLocation() {
        return location;
    }

    /**
     * Beállítja a gomba helyét.
     * 
     * @param location a Tekton, amin a gomba van
     */
    public void setLocation(Tecton location) {
        this.location = location;
    }

    /**
     * Visszaadja, hogy fejlett-e a gomba.
     * 
     * @return igaz ha fejlett, hamis ha nem
     */
    public boolean getIsGrown() {
        return isGrown;
    }

    /**
     * Beállítja a gomba fejlettségét.
     * 
     * @param isGrown igaz ha fejlett, hamis ha nem
     */
    public void setIsGrown(boolean isGrown) {
        this.isGrown = isGrown;
        range = isGrown ? 2 : 1;
    }
    // #endregion

    // #region FUNCTIONS
    /**
     * Spóraszórást kezdeményező metódus. Hatására a paraméterben kapott tektonra spórát szór a gomba (Tecton::addSpore)
     * a gombatest gombafajával, amennyiben éppen spóraszórásra alkalmas állapotban van (a cooldown lejárt), és a
     * céltekton a gombatest hatókörében (Tecton::distanceTo) található. A spóraszórás hatására a gombatest meg is
     * halhat (Tecton::removeMushroom, Fungus::removeMushroom), ha már kiszórta minden spóráját, ilyenkor a gombafajból
     * és a tektonról is törlődik, leiratkozik az aktív objektumokról. A visszatérési érték a spóraszórás sikeressége.
     * 
     * @param target a céltekton, ahova a spóra kerül
     */
    public boolean burstSpore(Tecton target) {

        if (cooldown <= 0) {
            int distance = location.distanceTo(target);
            if (distance <= range) {
                Spore spo = new Spore(species);
                target.addSpore(spo);
                burstsLeft--;
                cooldown = SPORE_BURST_DELAY;
                if (burstsLeft <= 0) {
                    location.removeMushroom();
                    location = null;
                    species.removeMushroom(this);
                    Controller.unregisterActiveObject(this);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * A gombafaj időtől függő fejlődését kezelő metódus. Kezeli a gomba fejlődését, illetve spóraszórások között
     * eltelendő időt. Ha a gomba fejlődésének cooldownja lejárt, a gomba fejlett lesz, a hatótávja 2-re nő.
     */
    @Override
    public void tick(double dT) {
        if (cooldown > 0) {
            cooldown -= dT;
        }
        if (growCooldown > 0) {
            growCooldown -= dT;
        }
        if (growCooldown <= 0 && !isGrown) {
            isGrown = true;
            range = 2;
        }
    }
    // #endregion

}
