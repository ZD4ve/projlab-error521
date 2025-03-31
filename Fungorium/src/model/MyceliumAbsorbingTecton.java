package model;

import helper.Skeleton;
import static helper.Skeleton.ask;
import java.util.Arrays;

/**
 * <h3>Gombafonal felszívó tekton</h3>
 * 
 * Olyan speciális tekton, ami időnként felszívja a rajta található
 * gombafonalakat.
 */
public class MyceliumAbsorbingTecton extends Tecton {
    /**
     * A gombafonalak felszívódásáig hátralévő idő másodpercben.
     */
    private double timeUntilAbsorption;

    /**
     * Létrehoz egy új példányt alapértelmezett beállításokkal.
     */
    public MyceliumAbsorbingTecton() {
        // TODO: use actual time
        timeUntilAbsorption = 1;
    }

    @Override
    public MyceliumAbsorbingTecton newMe() {
        var ret = new MyceliumAbsorbingTecton();
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
        timeUntilAbsorption = Math.max(timeUntilAbsorption - dT, 0);
        if (ask("Felszívódjanak a gombafonalak a tektonról?") && (true || timeUntilAbsorption == 0)) {
            while (!mycelia.isEmpty()) {
                mycelia.get(0).die();
            }
            // TODO: reset timer
        }
        super.tick(dT);
    }
}