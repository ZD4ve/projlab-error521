package proto;

import controller.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.*;

/**
 * <h3>Prototype</h3>
 * 
 * A pálya készítés belépési pontja. Pálya létrehozásáért és a pályán történt események kezeléséért felelős osztályokat összetartó osztály.
 */
@SuppressWarnings("rawtypes")
public class Prototype {

    /** A fontosabb (azonosítóval rendelkező) objektumok tárolásához használt változó. */
    protected static Map<String, Object> namedObjects = new HashMap<>();

    /** A különböző azonosítojú osztályokhoz tartozó rövidítéseket tároló változó. */
    protected static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu",
            Mycelium.class, "my", Spore.class, "sp", InsectEffect.class, "ie", Insect.class, "in", Colony.class, "co");

    /** A különböző típusú tekton osztályokhoz tartozó rövidítéseket tároló változó. */
    protected static Map<Class, String> tectonTypes = Map.of(Tecton.class, "tect", NoMushroomTecton.class, "nomu",
            SingleMyceliumTecton.class, "simy", MyceliumAbsorbingTecton.class, "myab", MyceliumKeepingTecton.class,
            "myke");

    /** A különböző típusú rovar hatások osztályokhoz tartozó rövidítéseket tároló változó. */
    protected static Map<Class, String> effectTypes = Map.of(AntiChewEffect.class, "anti", FissionEffect.class, "fiss",
            ParalysingEffect.class, "para", SpeedEffect.class, "sped");

    /** A különböző típusú osztályokból következőnek létrejövő objektumokhoz tárolja a keletkezendő azonosító szám részét. */
    private static final Map<Class, Integer> objIds = new HashMap<>();

    /**
     * Objektum regisztrálásáért felelős metódus. Bejegyzi a paraméterül kapott objektumot, melynek előállít egy azonosítót.
     */
    public static void registerNamedObject(Class cls, Object obj) {
        namedObjects.put(String.format("%s%02d", Prototype.names.get(cls), objIds.merge(cls, 1, Integer::sum)), obj);

    }

    /** A felhasználó parancssori bementét olvasó változó. */
    protected static Scanner scanner;

    /**
     * A prototípus belépési pontja.
     * Pálya készítésért és interakcióért felelős osztályokat hozza össze.
     */
    public static void main(String[] args) {
        Controller.addObjectEventHandler(Interaction::objectEvent);
        scanner = new Scanner(System.in);
        boolean reset;
        do {
            Interaction.ignoreEvents = true;
            MapCreation.createMap();
            Interaction.ignoreEvents = false;
            reset = Interaction.handleInteractions();
        } while (reset);
        scanner.close();
    }

    private Prototype() {}
}
