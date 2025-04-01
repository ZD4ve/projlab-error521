package controller;

import java.util.*;

import model.IActive;
import model.Tecton;

public class Controller {
    private static List<IActive> activeObjects = new ArrayList<>();
    private static List<Tecton> tectons = new ArrayList<>();

    public static void registerTecton(Tecton t) {
        if (!tectons.contains(t)) {
            tectons.add(t);
        }
    }

    public static void unregisterTecton(Tecton t) {
        tectons.remove(t);
    }

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

    public static void reset() {
        activeObjects.clear();
    }

    private Controller() {
    }
}
