package model;

/**
 * <h3>Spóra</h3>
 * 
 * Saját gombafajának és hatásának nyilvántartása.
 */
public class Spore {
    // #region ASSOCIATIONS
    /** A spórához tartozó gombafaj. */
    private final Fungus species;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * Inicializálja a spórát.
     * 
     * @param fungus a spórához tartozó gombafaj
     */
    public Spore(Fungus fungus) {
        this.species = fungus;
    }
    // #endregion

    // #region GETTERS-SETTERS
    /**
     * Visszaadja a spórához tartozó effektet.
     * Meghívja az InsectEffect osztály statikus createEffect() metódusát.
     * 
     * @return a spórához tartozó effekt
     */
    public InsectEffect getEffect() {
        return InsectEffect.createEffect();
    }

    /**
     * Visszaadja a spórához tartozó gombafajt.
     * 
     * @return a spórához tartozó gombafaj.
     */
    public Fungus getSpecies() {
        return species;
    }
    // #endregion
}
