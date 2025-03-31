package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * <h3>Gombatest nélküli tekton</h3>
 * 
 * Olyan speciális tekton, ami nem engedélyezi gombatest növesztését.
 */
public class NoMushroomTecton extends Tecton {
    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public NoMushroomTecton() {
    }

    @Override
    public NoMushroomTecton newMe() {
        var ret = new NoMushroomTecton();
        return ret;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ezen a tektonon nem lehetséges gombatestet növeszteni, ezért mindig
     * {@code null} a visszatérési érték.
     * 
     * @return {@code null}
     */

    @Override
    public Mushroom growMushroom(Fungus fungus) {
        return null;
    }
}