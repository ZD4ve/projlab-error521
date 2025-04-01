package model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Kolónia</h3>
 * 
 * Egy rovarász által irányított rovarok összessége.
 */
public class Colony {
    // #region ASSOCIATIONS
    /** A rovarászhoz tartozó rovarok listája */
    private List<Insect> insects = new ArrayList<>();

    // #region FUNCTIONS
    /**
     * Rovar felvétel a kolóniába.
     * 
     * @param insect új rovar
     */
    public void born(Insect insect) {
        insects.add(insect);
    }

    public List<Insect> getInsects() {
        return insects;
    }

    public int getScore() {
        return insects.stream().mapToInt(Insect::getScore).sum();
    }

    /**
     * Rovar eltávolítása a kolóniából.
     * 
     * @param insect elpusztult rovar
     */
    public void died(Insect insect) {
        insects.remove(insect);
    }
    // #endregion
}
