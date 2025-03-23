package model;

import helper.Skeleton;
import java.util.Arrays;

/**
 * Olyan InsectEffect, amely a rovar mozgását tudja korlátozni.
 */
public class ParalysingEffect extends InsectEffect {
    /**
     * Inicializálja az effektet.
     */
    public ParalysingEffect() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    /**
     * Mozgásképtelenné teszi a paraméterben kapott rovart.
     *
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, Arrays.asList(insect));
        insect.setIsParalysed(true);
        this.insect = insect;
        Skeleton.printReturn();
    }

    /**
     * Visszaadja a rovarnak a mozgás képességét.
     */
    @Override
    public void remove() {
        Skeleton.printCall(this);
        insect.setIsParalysed(true);
        Skeleton.printReturn();
    }
}