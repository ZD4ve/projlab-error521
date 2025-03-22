package model;

import java.util.List;
import helper.Skeleton;
import java.util.Arrays;

public abstract class InsectEffect implements IActive {
    protected double timeLeft = 0.5;
    protected Insect insect;

    public static InsectEffect createEffect() {
        Skeleton.printCall("InsectEffect");
        InsectEffect newEffect = null;

        if (Skeleton.ask("Paralysing effektet hozzak létre?"))
            newEffect = new ParalysingEffect();
        else if (Skeleton.ask("Speed effektet hozzak létre?"))
            newEffect = new SpeedEffect();
        else if (Skeleton.ask("AntiChew effektet hozzak létre?"))
            newEffect = new AntiChewEffect();

        Skeleton.addObject(newEffect, "eff");
        Skeleton.printReturn(newEffect);
        return newEffect;
    }

    public abstract void applyTo(Insect insect);

    public abstract void remove();

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, Arrays.asList(dT));
        timeLeft -= dT;
        if (timeLeft <= 0) {
            remove();
        }
        Skeleton.printReturn();
    }
}
