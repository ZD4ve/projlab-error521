package model;

import java.util.List;
import helper.Skeleton;

public class AntiChewEffect extends InsectEffect {
    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, List.of(insect));
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