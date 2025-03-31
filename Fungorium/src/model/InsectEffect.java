package model;

import helper.Skeleton;
import java.util.Arrays;

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

        if (Skeleton.ask("Paralysing effektet hozzak létre?"))
            newEffect = new ParalysingEffect();
        else if (Skeleton.ask("Speed effektet hozzak létre?"))
            newEffect = new SpeedEffect();
        else if (Skeleton.ask("AntiChew effektet hozzak létre?"))
            newEffect = new AntiChewEffect();

        if (newEffect != null)
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
    public abstract void remove();

    /**
     * Hívásakor csökkentjük a hatás hátralévő idejét.
     */
    @Override
    public void tick(double dT) {
        timeLeft -= dT;
        if (timeLeft <= 0) {
            remove();
            insect.removeEffect(this);
        }
    }
}
