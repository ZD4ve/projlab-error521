package view;

import java.awt.Color;

/**
 * Általános játékost reprezentáló osztály. A játékos színét tárolja, és lehetővé teszi a játékos kiválasztását.
 */
public abstract class VPlayer {
    /** Játékos színe */
    private Color color;

    /**
     * Létrehoz egy új VPlayer példányt a megadott színnel.
     *
     * @param color a játékos színe
     */
    protected VPlayer(Color color) {
        this.color = color;
    }

    /**
     * Kiválasztja a játékost, vagyis beállítja a nézetben a kiválasztott játékosra.
     */
    public void selectPlayer() {
        View.setSelectedPlayer(this);
    }

    // #region GETTERS-SETTERS
    public Color getColor() {
        return color;
    }

    public abstract int getScore();
    // #endregion
}