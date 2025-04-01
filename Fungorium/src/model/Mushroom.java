package model;

/**
 * <h3>Gombatest</h3>
 * 
 * Felelőssége a saját fejlődésének kezelése, illetve a saját halálának feltételeinek beteljesülésének ellenőrzése.
 * Halálakor értesíti a megfelelő objektumokat, hogy ők is le tudják kezelni a halálának következményeit. Felelőssége
 * továbbá a spóraszórás kezdeményezése, illetve az ehhez szükséges ellenőrzések elvégzése.
 */
public class Mushroom implements IActive {

    // #region ASSOCIATIONS
    /**
     * Gombatesthez tartozó faj.
     */
    private final Fungus species;

    /**
     * Az a tekton amelyen a gombatest található.
     */
    private Tecton location;
    // #endregion

    // #region ATTRIBUTES
    /**
     * Mekkora a gomba hatótávja.
     */
    private int range;

    /**
     * Fejlett-e a gombatest.
     */
    private boolean isGrown;

    /**
     * Mennyi időnek kell még legalább eltelnie, hogy ismét szórhasson spórát.
     */
    private double cooldown;

    /**
     * Mennyi időnek kell eltelnie, hogy a gomba fejletté váljon.
     */
    private double growCooldown;

    /**
     * Mennyi spórát szórt eddig a gombafej.
     */
    private int burstCount;
    // #endregion

    // #region CONSTANTS
    /**
     * Mennyi spóra kell egy gombafej növesztéséhez.
     */
    public static final int GROW_SPORES_REQUIRED = 3;

    /**
     * Mennyi spórát tud szórni a gombafej.
     */
    public static final int MAX_SPORE_BURSTS = 10;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy fungus fajú gombatestet a location tektonon
     * 
     * @param fungus   a gombatesthez tartozó gombafaj.
     * @param location a tekton, amin található
     */
    public Mushroom(Fungus fungus, Tecton location) {
        this.species = fungus;
        this.location = location;
        fungus.addMushroom(this);
        location.setMushroom(this);
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

    public boolean getIsGrown() {
        return isGrown;
    }

    public void setIsGrown(boolean isGrown) {
        this.isGrown = isGrown;
    }
    // #endregion

    // #region FUNCTIONS
    /**
     * spóraszórást kezdeményező metódus. Hatására a paraméterben kapott tektonra spórát szór a gomba, amennyiben éppen
     * spóraszórásra alkalmas állapotban van, és a céltekton a gombatest hatókörében található. A spóraszórás hatására a
     * gombatest meg is halhat, ha már kiszórta minden spóráját. A visszatérési érték a spóraszórás sikeressége.
     * 
     * @param target a céltekton, ahova a spóra kerül
     */
    public boolean burstSpore(Tecton target) {

        if (cooldown == 0) {
            int distance = location.distanceTo(target);
            range = isGrown ? 2 : 1;
            if (distance <= range) {
                Spore spo = new Spore(species);
                target.addSpore(spo);
                burstCount++;
                if (burstCount > MAX_SPORE_BURSTS) {
                    location.removeMushroom();
                    species.removeMushroom(this);
                }
                return true;
            }
        }
        return false;
    }

    // TODO DOC
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
        }
    }
    // #endregion

}
