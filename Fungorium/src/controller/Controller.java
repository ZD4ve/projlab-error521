package controller;

import java.util.*;

import model.IActive;
import model.Tecton;

public class Controller {
    private static List<IActive> activeObjects = new ArrayList<>();
    private static List<Tecton> tectons = new ArrayList<>();

    private static List<ObjectEventHandler> objectEventHandlers = new ArrayList<>();

    private static Set<IActive> removedStuff = new HashSet<>();

    public static void registerTecton(Tecton t) {
        if (!tectons.contains(t)) {
            tectons.add(t);

        }
    }

    public static void unregisterTecton(Tecton t) {
        tectons.remove(t);
        for (var handler : objectEventHandlers) {
            handler.handle(t, false);
        }
    }

    public static void addTectonEventHandler(ObjectEventHandler handler) {
        objectEventHandlers.add(handler);
    }

    // #region IActive related stuff

    public static void registerActiveObject(IActive object) {
        if (!activeObjects.contains(object)) {
            activeObjects.add(object);
            for (var handler : objectEventHandlers) {
                handler.handle(object, true);
            }
        }
    }

    public static void unregisterActiveObject(IActive object) {
        removedStuff.add(object);
        activeObjects.remove(object);
        for (var handler : objectEventHandlers) {
            handler.handle(object, false);
        }
    }

    public static void onTimeElapsed(double dT) {
        var aOCopy = activeObjects.stream().toList();
        for (var e : aOCopy) {
            if (!removedStuff.contains(e)) {
                e.tick(dT);
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
