package view;

import model.Colony;
import java.awt.Color;

/**
 * Becsomagol egy kolóniát és a hozzá tartozó színt.
 */
public class VColony extends VPlayer {
    /** Becsomagolt kolónia */
    private Colony colony;

    /**
     * Konstruktor, amely létrehozza a VColony objektumot a megadott kolóniával és színnel.
     *
     * @param colony a kolónia, amelyet becsomagolunk
     * @param color  a kolónia színe
     */
    public VColony(Colony colony, Color color) {
        super(color);
        this.colony = colony;
    }

    // #region GETTERS-SETTERS
    public Colony getColony() {
        return colony;
    }

    @Override
    public int getScore() {
        return colony.getScore();
    }
    // #endregion
}