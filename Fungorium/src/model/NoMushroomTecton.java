package model;

import java.util.Arrays;

import helper.Skeleton;

public class NoMushroomTecton extends Tecton {

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

    @Override
    public Mushroom growMushroom(Fungus fungus) {
        Skeleton.printCall(this, Arrays.asList(fungus));
        Skeleton.printReturn(null);
        return null;
    }
}