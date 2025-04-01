package model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Kolónia</h3>
 * 
 * Egy rovarász által irányított rovarok összessége.
 */
public class Colony {
    private List<Insect> insects = new ArrayList<>();

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
}
