package model;

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
        // this is for documentation purposes
    }

    @Override
    public NoMushroomTecton newMe() {
        return new NoMushroomTecton();
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
    public boolean growMushroom(Fungus fungus) {
        return false;
    }
}