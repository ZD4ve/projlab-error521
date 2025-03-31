package model;

// TODO DOC
public class MyceliumKeepingTecton extends Tecton {
    @Override
    public Tecton newMe() {
        return new MyceliumKeepingTecton();
    }

    @Override
    public boolean keepsMyceliumAlive() {
        return true;
    }
}
