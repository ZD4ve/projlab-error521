package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * <h3>Spóra</h3>
 * 
 * Saját gombafajának és hatásának nyílvántartása.
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
        Skeleton.printCall(this.getClass(), Arrays.asList(fungus));
        this.species = fungus;
        Skeleton.printReturn(this);
    }

    /**
     * Visszaadja a spórához tartozó effektet
     * 
     * @return a spórához tartozó effekt
     */
    public InsectEffect getEffect() {
        Skeleton.printCall(this);
        InsectEffect effect = InsectEffect.createEffect();
        Skeleton.printReturn(effect);
        return effect;
    }

    /**
     * Visszaadja a spórához tartozó gombafajt.
     * 
     * @return a spórához tartozó gombafaj.
     */
    public Fungus getSpecies() {
        Skeleton.printCall(this);
        Skeleton.printReturn(species);
        return species;
    }
}
