package model;

import java.util.List;

import helper.Skeleton;

public class Spore {
    private Fungus species;

    public Spore(Fungus fungus) {
        Skeleton.printCall(this.getClass(), List.of(fungus));
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
