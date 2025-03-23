package model;

import static helper.Skeleton.ask;

import java.util.List;
import java.util.Arrays;

import helper.Skeleton;

public class MyceliumAbsorbingTecton extends Tecton {
    private double timeUntilAbsorbtion;

    public MyceliumAbsorbingTecton() {
        Skeleton.printCall(this.getClass());
        // TODO: use actual time
        timeUntilAbsorbtion = 1;
        Skeleton.printReturn(this);
    }

    @Override
    public MyceliumAbsorbingTecton newMe() {
        Skeleton.printCall(this);
        var ret = new MyceliumAbsorbingTecton();
        Skeleton.addObject(ret, "tecMycAbs");
        Skeleton.printReturn(ret);
        return ret;
    }

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, Arrays.asList(dT));
        timeUntilAbsorbtion = Math.max(timeUntilAbsorbtion - dT, 0);
        if (ask("Felszívódjanak a gombafonalak a tektonról?") && (true || timeUntilAbsorbtion == 0)) {
            while (!mycelia.isEmpty()) {
                mycelia.getFirst().die();
            }
            // TODO: reset timer
        }
        super.tick(dT);
        Skeleton.printReturn();
    }
}