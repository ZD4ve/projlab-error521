package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * <h3>Gombafonal rágás tiltó hatás</h3>
 * 
 * Olyan InsectEffect, amely megakadályozza, hogy a rovar tudjon gombafonalat
 * rágni.
 */
public class AntiChewEffect extends InsectEffect {
    /**
     * Inicializálja az effektet.
     */
    public AntiChewEffect() {
    }

    /**
     * A paraméterben kapott rovar fonal elrágási képességét megszünteti.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount + 1);
        this.insect = insect;
    }

    /**
     * Visszaadja a rovar a fonal elrágási képességét.
     */
    @Override
    public void remove() {
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount - 1);
    }
}