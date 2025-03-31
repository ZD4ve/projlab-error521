package proto;

import java.util.ArrayList;
import java.util.Arrays;
import model.*;
import static proto.Prototype.*;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class MapCreation {

    private MapCreation() {
    }

    private static int teId = 1;
    private static int fuId = 1;
    private static int coId = 1;
    private static int inId = 1;
    private static int muId = 1;
    private static int myId = 1;

    private static void addTectonObject(String tecton) {
        String name = String.format("%s%02d", Prototype.names.get(Tecton.class), teId);

        switch (tecton) {
        case "tect":
            namedObjects.put(name, new Tecton());
            break;
        case "nomu":
            namedObjects.put(name, new NoMushroomTecton());
            break;
        case "simy":
            namedObjects.put(name, new SingleMyceliumTecton());
            break;
        case "myab":
            namedObjects.put(name, new MyceliumAbsorbingTecton());
            break;
        case "myke":
            namedObjects.put(name, new MyceliumKeepingTecton());
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

            var left = (Tecton) namedObjects.get(t1);
            if (t1 == null) {
                System.out.println("Error: invalid left tecton name");
                return;
            }

            var right = (Tecton) namedObjects.get(t2);
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

            var fungus = (Fungus) namedObjects.get(f);
            if (fungus == null) {
                System.out.println("Error: invalid fungus name");
                continue;
            }

            var tecton = (Tecton) namedObjects.get(t);
            if (tecton == null) {
                System.out.println("Error: invalid tecton name");
                continue;
            }

            Prototype.registerNamedObject(Mushroom.class, new Mushroom(fungus, tecton));

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

            Tecton left = (Tecton) namedObjects.get(t1);
            if (left == null) {
                System.out.println("Error: invalid left tecton name");
                continue;
            }

            Tecton right = (Tecton) namedObjects.get(t2);
            if (right == null) {
                System.out.println("Error: invalid right tecton name");
                continue;
            }

            var fungus = (Fungus) namedObjects.get(f);
            if (fungus == null) {
                System.out.println("Error: invalid fungus name");
                continue;
            }

            namedObjects.put(String.format(Prototype.names.get(Mycelium.class) + "%02d", myId++), new Mycelium(fungus, left, right));
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

            var fungus = (Fungus) namedObjects.get(f);
            if (fungus == null) {
                System.out.println("Error: invalid fungus name");
                continue;
            }

            var tecton = (Tecton) namedObjects.get(t);
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

            var colony = (Colony) namedObjects.get(c);
            if (colony == null) {
                System.out.println("Error: invalid colony name");
                continue;
            }

            var tecton = (Tecton) namedObjects.get(t);
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
            namedObjects.put(String.format(Prototype.names.get(Insect.class) + "%02d", inId++), new Insect(tecton));

        } while (true);
    }

    // Main menu
    public static void createMap() {
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
                            namedObjects.put(String.format(Prototype.names.get(Fungus.class) + "%02d", fuId++), new Fungus());
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
                            namedObjects.put(String.format(Prototype.names.get(Colony.class) + "%02d", coId++), new Colony());
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