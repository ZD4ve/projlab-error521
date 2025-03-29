package proto;

import helper.Skeleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import model.*;

import static proto.Interact.interactPhase;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class Proto {
    static HashMap<String, Object> gameObjects = new HashMap<>();
    static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu", Mycelium.class, "my", Spore.class, "sp", InsectEffect.class, "ie", Insect.class, "in", Colony.class, "co");

    private static int teId = 1;
    private static int fuId = 1;
    private static int coId = 1;
    private static int inId = 1;
    private static int muId = 1;
    private static int myId = 1;

    public static void main(String[] args) {
        Skeleton.setPrint(false);
        buildPhase();
        interactPhase();
    }

    private static void addTectonObject(String tecton) {
        String name = String.format(names.get(Tecton.class) + "%02d", teId);

        switch (tecton) {
        case "tect":
            gameObjects.put(name, new Tecton());
            break;
        case "nomu":
            gameObjects.put(name, new NoMushroomTecton());
            break;
        case "simy":
            gameObjects.put(name, new SingleMyceliumTecton());
            break;
        case "myab":
            gameObjects.put(name, new MyceliumAbsorbingTecton());
            break;
        case "myke":
            gameObjects.put(name, new MyceliumKeepingTecton());
            break;
        default:
            System.out.println("Error: invalid tecton type");
            return;
        }

        teId++;
    }

    private static void tectonsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            addTectonObject(input);
        } while (true);
    }

    private static void neighborsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 3) {
                System.out.println("Error: not enough args");
                continue;
            }

            String t1 = params.get(0);
            String check = params.get(1);
            String t2 = params.get(2);

            if (!check.equals("--")) {
                System.out.println("Error: invalid neighbor definition");
                continue;
            }

            var left = (Tecton) gameObjects.get(t1);
            if (t1 == null) {
                System.out.println("Error: invalid left tecton name");
                return;
            }

            var right = (Tecton) gameObjects.get(t2);
            if (t2 == null) {
                System.out.println("Error: invalid right tecton name");
                return;
            }

            left.addNeighbor(right);
            right.addNeighbor(left);
        } while (true);
    }

    private static void mushroomsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println("Not enough args.");
                continue;
            }
            String f = params.get(0);
            String t = params.get(1);

            var fungus = (Fungus) gameObjects.get(f);
            if (fungus == null) {
                System.out.println("Error: invalid fungus name");
                continue;
            }

            var tecton = (Tecton) gameObjects.get(t);
            if (tecton == null) {
                System.out.println("Error: invalid tecton name");
                continue;
            }

            gameObjects.put(String.format(names.get(Mushroom.class) + "%02d", muId++), new Mushroom(fungus, tecton));

        } while (true);
    }

    private static void myceliaMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 5) {
                System.out.println("Not enough args.");
                continue;
            }

            String t1 = params.get(0);
            String check1 = params.get(1);
            String t2 = params.get(2);
            String check2 = params.get(3);
            String f = params.get(4);

            if (!check1.equals("..") || !check2.equals(":")) {
                System.out.println("Error: invalid mycelium definition");
                continue;
            }

            Tecton left = (Tecton) gameObjects.get(t1);
            if (left == null) {
                System.out.println("Error: invalid left tecton name");
                continue;
            }

            Tecton right = (Tecton) gameObjects.get(t2);
            if (right == null) {
                System.out.println("Error: invalid right tecton name");
                continue;
            }

            var fungus = (Fungus) gameObjects.get(f);
            if (fungus == null) {
                System.out.println("Error: invalid fungus name");
                continue;
            }

            gameObjects.put(String.format(names.get(Mycelium.class) + "%02d", myId++), new Mycelium(fungus, left, right));
        } while (true);
    }

    private static void sporesMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println("Not enough args.");
                continue;
            }

            String f = params.get(0);
            String t = params.get(1);

            var fungus = (Fungus) gameObjects.get(f);
            if (fungus == null) {
                System.out.println("Error: invalid fungus name");
                continue;
            }

            var tecton = (Tecton) gameObjects.get(t);
            if (tecton == null) {
                System.out.println("Error: invalid tecton name");
                continue;
            }

            var spore = new Spore(fungus);

            tecton.addSpore(spore);
        } while (true);
    }

    private static void insectsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;
            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println("Not enough args.");
                continue;
            }

            String c = params.get(0);
            String t = params.get(1);
            String s = "1.0f";

            if (params.size() > 2) {
                s = params.get(2);
            }

            var colony = (Colony) gameObjects.get(c);
            if (colony == null) {
                System.out.println("Error: invalid colony name");
                continue;
            }

            var tecton = (Tecton) gameObjects.get(t);
            if (tecton == null) {
                System.out.println("Error: invalid tecton name");
                continue;
            }

            var insect = new Insect(tecton);

            // TODO: Colony implementation missing here, like addinsect, or we can just set
            // it at
            // the constructor

            float speed;
            try {
                speed = Float.parseFloat(s);
            } catch (NumberFormatException e) {
                speed = 1.0f;
            }

            insect.setSpeed(teId);
            gameObjects.put(String.format(names.get(Insect.class) + "%02d", inId++), new Insect(tecton));

        } while (true);
    }

    // Main menu
    private static void buildPhase() {
        do {
            String input = System.console().readLine();
            if (input.isEmpty())
                continue;

            var tokens = new ArrayList<>(Arrays.asList(input.split(" ")));
            var command = tokens.get(0).toLowerCase();
            var params = new ArrayList<>(tokens.subList(1, tokens.size())); // NotOkay

            switch (command) {
            case "tectons":
                tectonsMenu();
                break;
            case "neighbors":
                neighborsMenu();
                break;
            case "fungi":
                if (!params.isEmpty()) {
                    try {
                        int count = Integer.parseInt(params.get(0));
                        for (int i = 0; i < count; i++) {
                            gameObjects.put(String.format(names.get(Fungus.class) + "%02d", fuId++), new Fungus());
                        }
                    } catch (Exception e) {
                        System.out.println("Arg must be a valid integer.");
                    }
                }

                break;
            case "colonies":
                // kolóniák hozzáadása
                if (!params.isEmpty()) {
                    try {
                        int count = Integer.parseInt(params.get(0));
                        for (int i = 0; i < count; i++) {
                            gameObjects.put(String.format(names.get(Colony.class) + "%02d", coId++), new Colony());
                        }
                    } catch (Exception e) {
                        System.out.println("Arg must be a valid integer.");
                    }

                }
                break;
            case "mushrooms":
                mushroomsMenu();
                break;
            case "mycelia":
                myceliaMenu();
                break;
            case "insects":
                // rovarok hozzáadása
                insectsMenu();
                break;
            case "spores":
                sporesMenu();
                break;
            case "end":
                System.out.println("Entering interaction phase.");
                return;
            default:
                System.out.println("Error: invalid command");
            }
        } while (true);
    }

    // there is no static class in java
    /**
     * private Proto() { }
     */
}