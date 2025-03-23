package model;

import java.util.Arrays;

import helper.Skeleton;

/**
 * Olyan speciális tekton, ami nem engedélyezi gombatest növesztését.
 */
public class NoMushroomTecton extends Tecton {
    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public NoMushroomTecton() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    @Override
    public NoMushroomTecton newMe() {
        Skeleton.printCall(this);
        var ret = new NoMushroomTecton();
        Skeleton.addObject(ret, "tecNoMush");
        Skeleton.printReturn(ret);
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
        Skeleton.printCall(this, Arrays.asList(fungus));
        Skeleton.printReturn(null);
        return null;
    }
}