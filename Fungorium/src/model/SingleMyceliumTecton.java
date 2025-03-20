package model;

import helper.Skeleton;

public class SingleMyceliumTecton extends Tecton {

    @Override
    public SingleMyceliumTecton newMe() {
        Skeleton.printCall(this);
        var ret = new SingleMyceliumTecton();
        Skeleton.printReturn(ret);
        return ret;
    }

    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        for (Mycelium m : mycelia) {
            if (m.getSpecies() != fungus) {
                return false;
            }
        }
        if (mushroom == null || mushroom.getSpecies() != fungus) {
            return false;
        }
        return true;
    }
}