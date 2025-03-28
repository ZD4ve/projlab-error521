package proto;

import java.util.HashMap;
import java.util.Map;

import model.*;

public class Proto {
    static HashMap<String, Object> gameObjects = new HashMap<>();
    static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu", Mycelium.class, "my", Spore.class, "sp", InsectEffect.class, "ie", Insect.class, "in");

    public static void main(String[] args) {

    }

    private static void buildPhase() {
        String input;

        while (true) {
            input = System.console().readLine();
            if (input.length() == 0)
                continue;
            if (input.toLowerCase().endsWith("end"))
                break;

            String[] tokens = input.split(" ");

            switch (tokens[0].toLowerCase()) {
            case "tectons":
                // Tekton létrehozása almenü
                break;
            case "neighbors":
                // Szomszédok hozzáadása
                break;
            case "fungi":
                // Gombafajok hozzáadása
                break;
            case "colonies":
                // kolóniák hozzáadása
                break;
            case "mushrooms":
                // gombatestek hozzáadása
                break;
            case "mycelia":
                // gombatestek hozzáadása
                break;
            default:
                System.out.println("Invalid command");

            }
        }
    }

    // there is no static class in java
    /**
     * private Proto() { }
     */
}