package proto;

import java.util.HashMap;
import java.util.Map;

import model.*;

public class Prototype {

    protected static Map<String, Object> namedObjects = new HashMap<>();

    @SuppressWarnings("rawtypes")
    protected static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu", Mycelium.class, "my", Spore.class, "sp", InsectEffect.class, "ie", Insect.class, "in", Colony.class, "co");

    public static void registerNamedObject(@SuppressWarnings("rawtypes") Class cls, Object obj) {
        namedObjects.put(String.format("%s%02d", Prototype.names.get(cls), namedObjects.keySet().stream().filter(x -> x.startsWith(Prototype.names.get(cls))).map(x -> Integer.parseInt(x.substring(2))).max((x, y) -> x > y ? 1 : 0).orElse(0) + 1), obj);
    }

    public static void main(String[] args) {
        MapCreation.createMap();
        Interaction.handleInteractions();
    }

    private Prototype() {
    }
}
