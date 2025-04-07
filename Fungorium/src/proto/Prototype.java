package proto;

import controller.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.*;

@SuppressWarnings("rawtypes")
public class Prototype {

    protected static Map<String, Object> namedObjects = new HashMap<>();

    protected static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu",
            Mycelium.class, "my", Spore.class, "sp", InsectEffect.class, "ie", Insect.class, "in", Colony.class, "co");
    protected static Map<Class, String> tectonTypes = Map.of(Tecton.class, "tect", NoMushroomTecton.class, "nomu",
            SingleMyceliumTecton.class, "simy", MyceliumAbsorbingTecton.class, "myab", MyceliumKeepingTecton.class,
            "myke");

    protected static Map<Class, String> effectTypes = Map.of(AntiChewEffect.class, "anti", FissionEffect.class, "fiss",
            ParalysingEffect.class, "para", SpeedEffect.class, "sped");

    private static final Map<Class, Integer> objIds = new HashMap<>();

    public static void registerNamedObject(Class cls, Object obj) {
        namedObjects.put(String.format("%s%02d", Prototype.names.get(cls), objIds.merge(cls, 1, Integer::sum)), obj);

    }

    protected static Scanner scanner;

    public static void main(String[] args) {
        Controller.addTectonEventHandler(Interaction::objectEvent);
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

    private Prototype() {
    }
}
