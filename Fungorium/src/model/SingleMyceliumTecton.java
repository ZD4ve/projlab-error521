package model;

public class SingleMyceliumTecton extends Tecton {

    @Override
    public SingleMyceliumTecton newMe() {
        return new SingleMyceliumTecton();
    }

    @Override
    public boolean canGrowMyceliumFrom(Fungus fungus) {
        for (Mycelium m : mycelia) {
            if (m.getSpecies() != fungus) {
                return false;
            }
        }
        if (mushroom == null || mushroom.getSpecies() != fungus) {
            return false;
        }
        return true;
    }
}