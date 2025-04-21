package controller;

import java.util.*;

import model.IActive;
import model.Tecton;

/**
 * A modell működtetéséért felelős. Eseményeket kezel és indít el.
 */
public class Controller {
    /** Azon objektumokra tartalmaz hivatkozást, melyekre hat az idő múlása. */
    private static List<IActive> activeObjects = new ArrayList<>();
    /** A létező tektonokra tartalmaz hivatkozást, ezzel könnyítve a modellhez való hozzáférést. */
    private static List<Tecton> tectons = new ArrayList<>();

    /** Az aktív objetumok regisztrálását kezelő függvényekre tartalmaz hivatkozásokat. */
    private static List<ObjectEventHandler> objectEventHandlers = new ArrayList<>();

    /** A futás közben megszűnt (modellből eltávolított) aktív objektumokat tartalmazza. */
    private static Set<IActive> removedStuff = new HashSet<>();

    /** Tektont regisztrál a kontrollerben. Tekton létrehozásakor hívandó. */
    public static void registerTecton(Tecton t) {
        if (!tectons.contains(t)) {
            tectons.add(t);

        }
    }

    /** Tekton regisztrációját szünteti meg. Tekton töréskor hívandó. */
    public static void unregisterTecton(Tecton t) {
        tectons.remove(t);
        for (var handler : objectEventHandlers) {
            handler.handle(t, false);
        }
    }

    /**
     * Új eseménykezelőt regisztrál, mely meg lesz hívva, mikor egy aktív objektum hozzáadásra vagy eltávolításra kerül
     * a kontrollerből.
     */
    public static void addObjectEventHandler(ObjectEventHandler handler) {
        objectEventHandlers.add(handler);
    }

    // #region IActive related stuff
    /** Aktív objektumot regisztrál a kontollerben - létrehozáskor. */
    public static void registerActiveObject(IActive object) {
        if (!activeObjects.contains(object)) {
            activeObjects.add(object);
            for (var handler : objectEventHandlers) {
                handler.handle(object, true);
            }
        }
    }

    /** Aktív objektumot távolít el a kontrollerből - megszűnéskor. */
    public static void unregisterActiveObject(IActive object) {
        removedStuff.add(object);
        activeObjects.remove(object);
        for (var handler : objectEventHandlers) {
            handler.handle(object, false);
        }
    }

    /**
     * Az óra - vagy más - ezen függvényen keresztül tudja jelezni a kontrollerenek az idő múlását, másodpercben
     * megadva.
     */
    public static void onTimeElapsed(double dT) {
        var aOCopy = activeObjects.stream().toList();
        for (var e : aOCopy) {
            if (!removedStuff.contains(e)) {
                e.tick(dT);
            }
        }
    }

    // #endregion
    /** Alaphelyzetbe állítja a kontrollert. Minden aktív objektumot töröl. */
    public static void reset() {
        activeObjects.clear();
        tectons.clear();
        removedStuff.clear();
    }

    /** Az osztály nem példányosítható. */
    private Controller() {
    }
}
