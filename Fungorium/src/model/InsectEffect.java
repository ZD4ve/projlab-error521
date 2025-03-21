package model;

import java.util.List;
import helper.Skeleton;

public abstract class InsectEffect implements IActive {
    protected double timeLeft = 0.5;
    protected Insect insect;

    public static InsectEffect createEffect() {
        Skeleton.printCall("InsectEffect");
        InsectEffect new_effect = null;

        if (Skeleton.ask("Paralysing effektet hozzak létre?"))
            new_effect = new ParalysingEffect();
        else if (Skeleton.ask("Speed effektet hozzak létre?"))
            new_effect = new SpeedEffect();
        else if (Skeleton.ask("AntiChew effektet hozzak létre?"))
            new_effect = new AntiChewEffect();

        Skeleton.addObject(new_effect, "eff");
        Skeleton.printReturn(new_effect);
        return new_effect;
    }

    public abstract void applyTo(Insect insect);

    public abstract void remove();

    @Override
    public void tick(double dT) {
        Skeleton.printCall(this, List.of(dT));
        timeLeft -= dT;
        if (timeLeft <= 0) {
            remove();
        }
        Skeleton.printReturn();
    }
}
