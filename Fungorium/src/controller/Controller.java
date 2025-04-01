package controller;

import java.util.*;

import model.IActive;
import model.Tecton;

public class Controller {
    private static List<IActive> activeObjects = new ArrayList<>();
    private static List<Tecton> tectons = new ArrayList<>();

    private static IActive lastRemoved = null;

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
        lastRemoved = object;
        activeObjects.remove(object);
    }

    public static void onTimeElapsed(double dT) {
        for (int i = 0; i < activeObjects.size();) {
            var curr = activeObjects.get(i);
            curr.tick(dT);
            if (lastRemoved != curr) {
                i++;
            }
        }
    }

    // #endregion

    public static void reset() {
        activeObjects.clear();
    }

    private Controller() {
    }
}
