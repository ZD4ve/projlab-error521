package model;

import helper.Skeleton;
import java.util.Arrays;

/*
 * Olyan InsectEffect, amely megakadályozza, hogy a rovar tudjon gombafonalat rágni.
 */
public class AntiChewEffect extends InsectEffect {

    /*
     * Inicializálja az effektet.
     */
    public AntiChewEffect() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    /*
     * A paraméterben kapott rovar fonal elrágási képességét megszünteti.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, Arrays.asList(insect));
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount + 1);
        this.insect = insect;
        Skeleton.printReturn();
    }

    /*
     * Visszaadja a rovar a fonal elrágási képességét.
     */
    @Override
    public void remove() {
        Skeleton.printCall(this);
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount - 1);
        Skeleton.printReturn();
    }
}