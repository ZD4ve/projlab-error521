package model;

import controller.*;

/**
 * <h3>Rovar hatás</h3>
 * 
 * A hatását kifejti a rovarra, annak időbeli hosszát számon tartja, valamint idő lejárta után negálja a saját hatását.
 */
public abstract class InsectEffect implements IActive {
    // #region ASSOCIATIONS
    /** A rovar, ami a hatást szenvedi. */
    protected Insect insect;
    // #endregion

    // #region ATTRIBUTES
    /** A hatás hátralévő ideje. */
    protected double timeLeft = 15;
    // #endregion

    // #region CONSTRUCTORS
    /**
     * InsectEffect konstruktor. Regisztrálja az objektumot az aktív objektumok között.
     */
    protected InsectEffect() {
        Controller.registerActiveObject(this);
    }
    // #endregion

    // #region FUNCTIONS
    /**
     * Véletlenszerűen választ egy hatást és létrehoz belőle egyet.
     * 
     * Létrehozható hatások: ParalysingEffect, SpeedEffect, AntiChewEffect, FissionEffect vagy null (azaz hatás nélküli
     * hatás).
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
     * Visszavonja az adott hatás hatását.
     */
    protected abstract void remove();

    /**
     * A hatás megszűnésekor hívódik meg, eltávolítja a hatást a rovarról.
     * 
     * Visszavonja a hatás hatását és leiratkoztatja a hatást az aktív objektumok közül.
     */
    public void wearOff() {
        remove();
        insect.removeEffect(this);
        Controller.unregisterActiveObject(this);
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Hívásakor csökkentjük a hatás hátralévő idejét.
     * 
     * Csak akkor hajtódik végre, ha a rovar nem null. Amennyiben a hátralévő idő 0 vagy kevesebb, akkor a hatás
     * megszűnik.
     */
    @Override
    public void tick(double dT) {
        if (insect == null)
            return;
        timeLeft -= dT;
        if (timeLeft <= 0) {
            wearOff();
        }
    }
    // #endregion
}
