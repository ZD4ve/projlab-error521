package model;

import java.util.List;

// TODO DOC
public class Colony {
    private List<Insect> insects;

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
