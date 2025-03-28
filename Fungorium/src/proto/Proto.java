package proto;

import java.util.HashMap;

public class Proto {
    static HashMap<String, Object> gameObjects = new HashMap<>();

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