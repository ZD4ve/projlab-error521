package model;

/**
 * <h3>Gombatest nélküli tekton</h3>
 * 
 * Olyan speciális tekton, ami nem engedélyezi gombatest növesztését.
 */
public class NoMushroomTecton extends Tecton {
    /**
     * Létrehoz egy új példányt a tektonból.
     * 
     * @return az új NoMushroomTecton példány
     */
    @Override
    public NoMushroomTecton newMe() {
        return new NoMushroomTecton();
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ezen a tektonon nem lehetséges gombatestet növeszteni, ezért mindig {@code false} a visszatérési érték.
     * 
     * @return {@code false}
     */
    @Override
    public boolean growMushroom(Fungus fungus) {
        return false;
    }
}