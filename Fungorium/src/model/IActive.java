package model;

/**
 * <h3>Aktív interfész</h3>
 * 
 * Olyan objektumok közös kezelésére szolgál, melyekre hatással van az idő.
 */
public interface IActive {
    /**
     * A rendszer ezen a függvényen keresztül értesíti az aktív objektumokat az eltelt időről.
     * 
     * @param dT az eltelt idő másodpercben.
     */
    void tick(double dT);
}