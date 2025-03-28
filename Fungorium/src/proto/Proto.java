package proto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Proto {
    static HashMap<String, Object> gameObjects = new HashMap<>();

    public static void main(String[] args) {

    }

    private static void tectonsMenu(String[] params) {

    }

    private static void neighborsMenu(String[] params) {

    }

    private static void mushroomsMenu(String[] params) {

    }

    private static void myceliaMenu(String[] params) {

    }

    private static void sporesMenu(String[] params) {

    }

    // Main menu
    private static void buildPhase() {
        String input;
        
        do {
            input = System.console().readLine();
            if (input.isEmpty())
                continue;

            var tokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
            switch (tokens.get(0).toLowerCase()) {
                    case "tectons":
                        // Tekton létrehozása almenü
                        tectonsMenu(tokens.subList(1, tokens.size()).toArray(new String[0]));
                        break;
                    case "neighbors":
                        // Szomszédok hozzáadása
                        neighborsMenu(tokens.subList(1, tokens.size()).toArray(new String[0]));
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
                    case "insects":
                        // rovarok hozzáadása
                        break;
                    default:
                        System.out.println("Invalid command");
                }
        } while (!input.toLowerCase().endsWith("end"));
    }

    // there is no static class in java
    /**
     * private Proto() { }
     */
}