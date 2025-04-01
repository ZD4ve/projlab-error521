package model;

/**
 * <h3>Gombafonal</h3>
 * 
 * A gombafonalak bizonyos feltételeket teljesítő tektonokon nőhetnek, felelőssége ezen feltételek ellenőrzése, valamint
 * a létrejövés utáni vastagodás elvégzése, ennek sikerességéről a gombafaj tájékoztatása. Felelőssége továbbá a
 * bármilyen okból történő megsemmisülésének kezelése, erről fajának értesítése, továbbá saját gombafajának
 * nyilvántartása.
 */
public class Mycelium implements IActive {
    private static final double GROWTH_TIME = 10;
    private static final double CHEW_TIME = 3;

    private final Fungus species;
    private final Tecton[] ends;

    private enum State {
        GROWING, NORMAL, CHEWED
    }

    private State state;
    private double cooldown;

    /**
     * Konstruktor, létrehozza a paraméterként kapott gombafajhoz tartozó gombafonalat, a két paraméterként kapott
     * tekton közé.
     */
    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };

        end1.addMycelium(this);
        end2.addMycelium(this);
        fungus.addMycelium(this);

        state = State.GROWING;
        cooldown = GROWTH_TIME;
    }

    // #region GETTERS-SETTERS

    /**
     * Visszaadja a gombafonal két végpontját.
     */
    public Tecton[] getEnds() {
        return ends;
    }

    /**
     * Visszaadja a gombafajt, amelyhez a gombafonal tartozik.
     */
    public Fungus getSpecies() {
        return species;
    }

    // #endregion
    // #region FUNCTIONS

    /**
     * Megszünteti az egyedet, erről értesíti a gombafaját és végpontjait.
     */
    public void die() {
        if (state == State.GROWING) {
            species.myceliumGrowthComplete();
        }
        for (Tecton tecton : ends) {
            tecton.removeMycelium(this);
        }
        species.removeMycelium(this);
    }

    /**
     * Elindítja a gombafonal elrágásának folyamatát.
     */
    public void chew() {
        if (state == State.GROWING) {
            species.myceliumGrowthComplete();
        }
        state = State.CHEWED;
        cooldown = CHEW_TIME;
    }

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
