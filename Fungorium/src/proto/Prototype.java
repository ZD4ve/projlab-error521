package proto;

import java.util.HashMap;
import java.util.Map;

import model.*;

@SuppressWarnings("rawtypes")
public class Prototype {

    protected static Map<String, Object> namedObjects = new HashMap<>();

    protected static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu",
            Mycelium.class, "my", Spore.class, "sp", InsectEffect.class, "ie", Insect.class, "in", Colony.class, "co");
    protected static Map<Class, String> tectonTypes = Map.of(Tecton.class, "tect", NoMushroomTecton.class, "nomu",
            SingleMyceliumTecton.class, "simy", MyceliumAbsorbingTecton.class, "myab", MyceliumKeepingTecton.class,
            "myke");

    protected static Map<Class, String> effecTypes = Map.of(AntiChewEffect.class, "anti", FissionEffect.class, "fiss",
            ParalysingEffect.class, "para", SpeedEffect.class, "sped");

    public static void registerNamedObject(Class cls, Object obj) {
        namedObjects.put(String.format("%s%02d", Prototype.names.get(cls),
                namedObjects.keySet().stream().filter(x -> x.startsWith(Prototype.names.get(cls)))
                        .mapToInt(x -> Integer.parseInt(x.substring(2))).max().orElse(0) + 1),
                obj);
    }

    public static void main(String[] args) {
        boolean reset;
        do {
            MapCreation.createMap();
            reset = Interaction.handleInteractions();
        } while (reset);

    }

    private Prototype() {
    }
}
