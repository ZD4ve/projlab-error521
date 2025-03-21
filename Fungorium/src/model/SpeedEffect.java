package model;

import java.util.List;
import helper.Skeleton;

public class SpeedEffect extends InsectEffect {
    private double multiplier;

    public SpeedEffect() {
        Skeleton.printCall(this.getClass());
        this.multiplier = 1;
        Skeleton.printReturn(this);
    }

    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, List.of(insect));
        double speed = insect.getSpeed();
        insect.setSpeed(speed * multiplier);
        this.insect = insect;
        Skeleton.printReturn();
    }

    @Override
    public void remove() {
        Skeleton.printCall(this);
        double speed = insect.getSpeed();
        insect.setSpeed(speed / multiplier);
        Skeleton.printReturn();
    }
}