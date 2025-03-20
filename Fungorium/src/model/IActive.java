package model;

public interface IActive {
    /**
     * A rendszer ezen a függvényen keresztül értesíti az aktív objektumokat az
     * eltelt időről.
     * 
     * @param dT az eltelt idő másodpercben.
     */
    void tick(double dT);
}