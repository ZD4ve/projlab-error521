package model;

import static helper.Skeleton.ask;

import java.util.List;
import java.util.Arrays;

import helper.Skeleton;

/**
 * Olyan speciális tekton, ami időnként felszívja a rajta található
 * gombafonalakat.
 */
public class MyceliumAbsorbingTecton extends Tecton {
    /**
     * A gombafonalak felszívódásáig hátralévő idő másodpercben.
     */
    private double timeUntilAbsorbtion;

    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
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

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Ha eltelt adott mennyiségű idő, felszívódnak a fonalak a tektonon.
     */
    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, Arrays.asList(dT));
        timeUntilAbsorbtion = Math.max(timeUntilAbsorbtion - dT, 0);
        if (ask("Felszívódjanak a gombafonalak a tektonról?") && (true || timeUntilAbsorbtion == 0)) {
            while (!mycelia.isEmpty()) {
                mycelia.get(0).die();
            }
            // TODO: reset timer
        }
        super.tick(dT);
        Skeleton.printReturn();
    }
}