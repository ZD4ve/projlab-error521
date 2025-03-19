package model;

public class MyceliumAbsorbingTecton extends Tecton {
    private double absorbtionTimer;

    @Override
    public MyceliumAbsorbingTecton newMe() {
        return new MyceliumAbsorbingTecton();
    }

    @Override
    public void tick(double dT) {
        absorbtionTimer = Math.max(absorbtionTimer - dT, 0);
        if (absorbtionTimer == 0) {
            // TODO: absorb
        }
    }
}