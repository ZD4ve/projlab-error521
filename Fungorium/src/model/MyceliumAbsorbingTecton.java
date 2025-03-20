package model;

public class MyceliumAbsorbingTecton extends Tecton {
    private double absorptionTimer;

    @Override
    public MyceliumAbsorbingTecton newMe() {
        return new MyceliumAbsorbingTecton();
    }

    @Override
    public void tick(double dT) {
        absorptionTimer = Math.max(absorptionTimer - dT, 0);
        if (absorptionTimer == 0) {
            // TODO: absorb
        }
    }
}