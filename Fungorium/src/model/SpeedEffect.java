package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * <h3>Sebesség változtató hatás</h3>
 * 
 * Olyan InsectEffect, amely a rovar sebességét befolyásolja.
 */
public class SpeedEffect extends InsectEffect {
    /**
     * Lebegőpontos szám, ezzel szorozza a rovar sebességét.
     */
    private double multiplier;

    /**
     * Inicializálja az effektet.
     */
    public SpeedEffect() {
        this.multiplier = 0.65;
    }

    /**
     * A paraméterben kapott rovar sebességét állítja.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        double speed = insect.getSpeed();
        insect.setSpeed(speed * multiplier);
        this.insect = insect;
    }

    /**
     * Visszaállítja a rovar sebességét.
     */
    @Override
    public void remove() {
        double speed = insect.getSpeed();
        insect.setSpeed(speed / multiplier);
    }
}