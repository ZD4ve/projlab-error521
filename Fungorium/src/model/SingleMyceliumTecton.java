package model;

import java.util.List;
import java.util.Arrays;

import helper.Skeleton;

public class SingleMyceliumTecton extends Tecton {

    public SingleMyceliumTecton() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    @Override
    public SingleMyceliumTecton newMe() {
        Skeleton.printCall(this);
        var ret = new SingleMyceliumTecton();
        Skeleton.addObject(ret, "tecSinMyc");
        Skeleton.printReturn(ret);
        return ret;
    }

    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        Skeleton.printCall(this, Arrays.asList(fungus));
        for (Mycelium m : mycelia) {
            if (m.getSpecies() != fungus) {
                Skeleton.printReturn(false);
                return false;
            }
        }
        if (mushroom != null && mushroom.getSpecies() != fungus) {
            Skeleton.printReturn(false);
            return false;
        }
        Skeleton.printReturn(true);
        return true;
    }
}