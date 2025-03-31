package model;

import controller.RandomProvider;

/**
 * <h3>Rovar hatás</h3>
 * 
 * A hatását kifejti a rovarra, annak időbeli hosszát számon tartja, valamint
 * idő lejárta után negálja a saját hatását.
 */
public abstract class InsectEffect implements IActive {
    /**
     * A hatás hátralévő ideje.
     */
    protected double timeLeft = 0.5;

    /**
     * A rovar, ami a hatást szenvedi.
     */
    protected Insect insect;

    /**
     * véletlenszerűen választ egy hatást és létrehoz belőle egyet.
     * 
     * @return a létrehozott effekt
     */
    public static InsectEffect createEffect() {
        InsectEffect newEffect = null;
        int ran = (int) (RandomProvider.nextRand() * 5);
        if (ran == 0)
            newEffect = new ParalysingEffect();
        else if (ran == 1)
            newEffect = new SpeedEffect();
        else if (ran == 2)
            newEffect = new AntiChewEffect();
        else if (ran == 3)
            newEffect = new FissionEffect();
        return newEffect;
    }

    /**
     * A paraméterben kapott rovarra helyezi el hatását.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    public abstract void applyTo(Insect insect);

    /**
     * Leveszi a rovarról a hatását.
     */
    protected abstract void remove();

    public void wearOff() {
        remove();
        insect.removeEffect(this);
        // TODO unregister from clock
    }

    /**
     * Hívásakor csökkentjük a hatás hátralévő idejét.
     */
    @Override
    public void tick(double dT) {
        timeLeft -= dT;
        if (timeLeft <= 0) {
            wearOff();
        }
    }
}
