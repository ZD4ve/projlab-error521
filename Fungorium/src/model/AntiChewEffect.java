package model;

import java.util.List;
import helper.Skeleton;
import java.util.Arrays;

public class AntiChewEffect extends InsectEffect {

    public AntiChewEffect() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, Arrays.asList(insect));
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount + 1);
        this.insect = insect;
        Skeleton.printReturn();
    }

    @Override
    public void remove() {
        Skeleton.printCall(this);
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount - 1);
        Skeleton.printReturn();
    }
}