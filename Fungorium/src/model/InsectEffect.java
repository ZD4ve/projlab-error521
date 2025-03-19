package model;

public abstract class InsectEffect implements IActive {
    protected double timeLeft;

    public static InsectEffect createEffect() {
        return null;
    }

    public abstract void applyTo(Insect insect);

    public abstract void remove();

    @Override
    public void tick(double dT) {
    }
}
