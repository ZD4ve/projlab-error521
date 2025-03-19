package model;

public class SingleMyceliumTecton extends Tecton {

    @Override
    public SingleMyceliumTecton newMe() {
        return null;
    }

    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        return false;
    }
}