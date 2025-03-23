package model;

import helper.Skeleton;
import java.util.Arrays;

/**
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
        Skeleton.printCall(this.getClass());
        this.multiplier = 1;
        Skeleton.printReturn(this);
    }

    /**
     * A paraméterben kapott rovar sebességét állítja.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, Arrays.asList(insect));
        double speed = insect.getSpeed();
        insect.setSpeed(speed * multiplier);
        this.insect = insect;
        Skeleton.printReturn();
    }

    /**
     * Visszaállítja a rovar sebességét.
    */
    @Override
    public void remove() {
        Skeleton.printCall(this);
        double speed = insect.getSpeed();
        insect.setSpeed(speed / multiplier);
        Skeleton.printReturn();
    }
}