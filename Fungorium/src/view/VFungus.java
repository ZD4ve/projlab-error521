package view;

import model.Fungus;
import model.Mycelium;
import model.Tecton;

import java.awt.Color;
import java.util.List;

/**
 * Becsomagol egy gombafajt és a hozzá tartozó színt. A gombafaj akcióit elérhetővé teszi cellákat használva.
 */
public class VFungus extends VPlayer {
    /** Becsomagolt gombafaj */
    private Fungus fungus;

    /**
     * Konstruktor, amely létrehozza a VFungus objektumot a megadott gombafajjal és színnel.
     *
     * @param fungus a gombafaj, amelyet becsomagolunk
     * @param color  a gombafaj színe
     */
    public VFungus(Fungus fungus, Color color) {
        super(color);
        this.fungus = fungus;
    }

    // #region ACTIONS
    /**
     * Gombatestet növeszt a megadott cellában. Ha a gombatest növesztése sikeres, akkor létrehoz egy új VMushroom
     * objektumot.
     * 
     * @param cell a cella, ahol a gombatestet növeszteni akarjuk
     * @see Fungus#growMushroom(Tecton)
     */
    public void growMushroom(Cell cell) {
        Tecton tecton = cell.getTecton().getTecton();
        boolean success = fungus.growMushroom(tecton);
        if (success) {
            new VMushroom(cell, tecton.getMushroom());
        } else {
            View.notifyUser();
        }
    }

    /**
     * Gombafonalat növeszt a megadott cellák között. Ellenőrzi, hogy szomszédos a két megadott cella. Ha a gombafonal
     * növesztése sikeres, akkor létrehoz két új VMycelium objektumot és elhelyezi őket a cellákban.
     * 
     * @param cellA az első cella
     * @param cellB a második cella
     * @see Fungus#growMycelium(Tecton, Tecton)
     */
    public void growMycelium(Cell cellA, Cell cellB) {
        VMap map = View.getMap();
        List<Cell> neighbors = map.getNeighbors(cellA);
        if (neighbors.contains(cellB)) {
            Tecton tecton1 = cellA.getTecton().getTecton();
            Tecton tecton2 = cellB.getTecton().getTecton();
            boolean success = fungus.growMycelium(tecton1, tecton2);
            if (success) {
                Mycelium mycelium = fungus.getMycelia().get(fungus.getMycelia().size() - 1);
                new VMycelium(cellA, mycelium, cellB);
                new VMycelium(cellB, mycelium, cellA);
            } else {
                View.notifyUser();
            }
        } else {
            View.notifyUser();
        }
    }
    // #endregion

    // #region GETTERS-SETTERS
    public Fungus getFungus() {
        return fungus;
    }

    @Override
    public int getScore() {
        return fungus.getScore();
    }
    // #endregion
}