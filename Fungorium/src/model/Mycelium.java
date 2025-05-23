package model;

import controller.Controller;

/**
 * <h3>Gombafonal</h3>
 * 
 * A gombafonalak bizonyos feltételeket teljesítő tektonokon nőhetnek, felelőssége ezen feltételek ellenőrzése, valamint
 * a létrejövés utáni vastagodás elvégzése, ennek sikerességéről a gombafaj tájékoztatása. Felelőssége továbbá a
 * bármilyen okból történő megsemmisülésének kezelése, erről fajának értesítése, továbbá saját gombafajának
 * nyilvántartása.
 */
public class Mycelium implements IActive {
    // #region CONSTANTS
    /** Egy gombafonal növekedéséhez szükséges idő másodpercben. */
    private static final double GROWTH_TIME = 10;
    /** * Egy gombafonal elrágásához szükséges idő másodpercben. */
    private static final double CHEW_TIME = 3;
    // #endregion

    // #region ASSOCIATIONS
    /** A gombafaj, amelyhez a gombafonal tartozik. */
    private final Fungus species;
    /** A gombafonal két végpontja. */
    private final Tecton[] ends;
    // #endregion

    // #region ATTRIBUTES
    /** A gombafonal lehetséges állapotait leíró enumeráció */
    private enum State {
        GROWING, NORMAL, CHEWED
    }

    /** A gombafonal jelenlegi állapotát tárolja */
    private State state;
    /** A gombafonal következő állapotváltásáig hátralévő idő másodpercben. */
    private double cooldown;
    // #endregion

    // #region CONSTRUCTORS

    /**
     * Konstruktor, létrehozza a paraméterként kapott gombafajhoz tartozó gombafonalat, a két paraméterként kapott
     * tekton közé. Beállítja a faját és két végét. A két tektonhoz és a gombafajhoz hozzáadja magát
     * (Tecton::addMycelium, Fungus:: addMycelium), beállítja az állapotát növekvőbe és ennek megfelelően állítja be a
     * cooldownt. Regisztrálja magát az aktív objektumok közé.
     * 
     * @param fungus a gombafonalhoz tartozó gombafaj.
     * @param end1   tekton, a gombafonal egyik vége
     * @param end2   tekton, a gombafonal másik vége
     */
    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };

        end1.addMycelium(this);
        end2.addMycelium(this);
        fungus.addMycelium(this);

        state = State.GROWING;
        cooldown = GROWTH_TIME;

        Controller.registerActiveObject(this);
    }
    // #endregion

    // #region GETTERS-SETTERS

    /**
     * Visszaadja a gombafonal két végpontját.
     * 
     * @return a gombafonal végpontjainak tektonjai.
     */
    public Tecton[] getEnds() {
        return ends;
    }

    /**
     * Visszaadja a gombafajt, amelyhez a gombafonal tartozik.
     * 
     * @return a gombfonal gombafaja.
     */
    public Fungus getSpecies() {
        return species;
    }

    // #endregion
    // #region FUNCTIONS

    /**
     * Megszünteti az egyedet, erről értesíti a gombafaját és végpontjait. Ha éppen vastagodott, akkor a vastagodás
     * befejeződik (értesíti a gombafajt) (Fungus::myceliumGrowthComplete). Eltávolítja magát a tektonokról
     * (Tecton::removeMycelium), valamint a gombafajból (Fungus::removeMycelium). Leiratkozik az aktív objektumok közül.
     */
    public void die() {
        if (state == State.GROWING) {
            species.myceliumGrowthComplete();
        }
        for (Tecton tecton : ends) {
            tecton.removeMycelium(this);
        }
        ends[0] = null;
        ends[1] = null;
        species.removeMycelium(this);
        Controller.unregisterActiveObject(this);
    }

    /**
     * Elindítja a gombafonal elrágásának folyamatát. Amennyiben még vastagodott, a vastagodás befejeződik
     * (Fungus::myceliumGrowthComplete). Beállítja az állapotát elrágottra és beállítja a cooldownt az elrágási időre.
     */
    public void chew() {
        if (state == State.GROWING) {
            species.myceliumGrowthComplete();
        }
        state = State.CHEWED;
        cooldown = CHEW_TIME;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * A cooldown csökkentése. Amennyiben a cooldown lejárt, növekvőből normál állapotba kerül, erről értesíti a
     * gombafajt is (Fungus::myceliumGrowthComplete). Ha a fonal elrágott volt és a cooldown lejárt, meghal
     * (Mycelium::die).
     */
    @Override
    public void tick(double dT) {
        if (cooldown > 0) {
            cooldown -= dT;
        }
        if (cooldown <= 0) {
            if (state == State.GROWING) {
                state = State.NORMAL;
                species.myceliumGrowthComplete();
            }
            if (state == State.CHEWED) {
                die();
            }
        }

    }
    // #endregion
}
