package model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Kolónia</h3>
 * 
 * Egy rovarász által irányított rovarok összessége. Nyilván tartja a rovarok listáját és a rovarász pontszámát.
 */
public class Colony {
    // #region ASSOCIATIONS
    /** A rovarászhoz tartozó rovarok listája */
    private List<Insect> insects = new ArrayList<>();
    // #endregion

    // #region ATTRIBUTES
    /** A kolónia pontszáma */
    private int score;
    // #endregion

    // #region GETTERS-SETTERS
    /**
     * Rovarok lekérdezése.
     * 
     * @return a kolónia rovarainak listája
     */
    public List<Insect> getInsects() {
        return insects;
    }

    /**
     * Rovarász pontszámának lekérdezése.
     * 
     * @return a rovarász pontszáma
     */
    public int getScore() {
        return score;
    }

    /**
     * Rovarász pontszámának növelése.
     */
    public void addScore() {
        this.score++;
    }
    // #endregion

    // #region FUNCTIONS
    /**
     * Rovar felvétel a kolóniába.
     * 
     * @param insect új rovar
     */
    public void born(Insect insect) {
        insects.add(insect);
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
