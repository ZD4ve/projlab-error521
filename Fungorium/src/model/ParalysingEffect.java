package model;

import java.util.List;
import helper.Skeleton;

public class ParalysingEffect extends InsectEffect {

    public ParalysingEffect() {
        Skeleton.printCall(this.getClass());
        Skeleton.printReturn(this);
    }

    @Override
    public void applyTo(Insect insect) {
        Skeleton.printCall(this, List.of(insect));
        insect.setIsParalysed(true);
        this.insect = insect;
        Skeleton.printReturn();
    }

    @Override
    public void remove() {
        Skeleton.printCall(this);
        insect.setIsParalysed(true);
        Skeleton.printReturn();
    }
}