package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * <h3>Spóra</h3>
 * 
 * Saját gombafajának és hatásának nyilvántartása.
 */
public class Spore {
    /**
     * A spórához tartozó gombafaj.
     */
    private final Fungus species;

    /**
     * Inicializálja a spórát.
     * 
     * @param fungus a spórához tartozó gombafaj
     */
    public Spore(Fungus fungus) {
        this.species = fungus;
    }

    /**
     * Visszaadja a spórához tartozó effektet
     * 
     * @return a spórához tartozó effekt
     */
    public InsectEffect getEffect() {
        InsectEffect effect = InsectEffect.createEffect();
        return effect;
    }

    /**
     * Visszaadja a spórához tartozó gombafajt.
     * 
     * @return a spórához tartozó gombafaj.
     */
    public Fungus getSpecies() {
        return species;
    }
}
