package model;

import java.util.List;

import helper.Skeleton;

public class MyceliumAbsorbingTecton extends Tecton {
    private double absorptionTimer;

    @Override
    public MyceliumAbsorbingTecton newMe() {
        Skeleton.printCall(this);
        var ret = new MyceliumAbsorbingTecton();
        Skeleton.printReturn(ret);
        return ret;
    }

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, List.of(dT));
        absorptionTimer = Math.max(absorptionTimer - dT, 0);
        if (absorptionTimer == 0) {
            // TODO: absorb
        }
        Skeleton.printReturn();
    }
}