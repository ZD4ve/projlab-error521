package controller;

import java.util.*;

import model.IActive;

public class Controller {
    private static List<IActive> activeObjects = new ArrayList<>();

    // #region IActive related stuff
    
    public static void registerActiveObject(IActive object) {
        if (!activeObjects.contains(object)) {
            activeObjects.add(object);
        }
    }

    public static void unregisterActiveObject(IActive object) {
        activeObjects.remove(object);
    }

    public static void onTimeElapsed(double dT) {
        for (IActive a : activeObjects) {
            a.tick(dT);
        }
    }

    // #endregion

    private Controller() {
    }
}
