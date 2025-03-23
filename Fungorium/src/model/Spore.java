package model;

import java.util.Arrays;

import helper.Skeleton;

public class Spore {
    private final Fungus species;

    public Spore(Fungus fungus) {
        Skeleton.printCall(this.getClass(), Arrays.asList(fungus));
        this.species = fungus;
        Skeleton.printReturn(this);
    }

    public InsectEffect getEffect() {
        Skeleton.printCall(this);
        InsectEffect effect = InsectEffect.createEffect();
        Skeleton.printReturn(effect);
        return effect;
    }

    public Fungus getSpecies() {
        Skeleton.printCall(this);
        Skeleton.printReturn(species);
        return species;
    }
}
