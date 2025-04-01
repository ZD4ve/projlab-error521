package proto;

import java.util.ArrayList;
import java.util.Arrays;
import model.*;
import static proto.Prototype.*;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class MapCreation {

    private MapCreation() {
    }

    private static final String SYNTAX_ERROR = "Syntax error";
    private static final String NOT_ENOUGH_ARGS = "Syntax error: not enough args";
    private static final String INVALID_ARG = "Syntax error: invalid argument";
    private static final String FUNGUS_INVALID = ": invalid fungus name";
    private static final String TECTON_INVALID = ": invalid tecton name";

    private static void addTectonObject(String tecton) {
        switch (tecton) {
            case "tect" -> registerNamedObject(Tecton.class, new Tecton());
            case "nomu" -> registerNamedObject(Tecton.class, new NoMushroomTecton());
            case "simy" -> registerNamedObject(Tecton.class, new SingleMyceliumTecton());
            case "myab" -> registerNamedObject(Tecton.class, new MyceliumAbsorbingTecton());
            case "myke" -> registerNamedObject(Tecton.class, new MyceliumKeepingTecton());
            default -> System.out.println(SYNTAX_ERROR + ": invalid tecton type");
        }
    }

    private static void tectonsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            addTectonObject(input);
        } while (true);
    }

    private static void handleNeighbor(String t1, String check, String t2) {
        if (!check.equals("--")) {
            System.out.println(SYNTAX_ERROR + ": invalid neighbor definition");
            return;
        }

        var left = (Tecton) namedObjects.get(t1);
        if (t1 == null) {
            System.out.println(INVALID_ARG + ": invalid left tecton name");
            return;
        }

        var right = (Tecton) namedObjects.get(t2);
        if (t2 == null) {
            System.out.println(INVALID_ARG + ": invalid right tecton name");
            return;
        }

        left.addNeighbor(right);
        right.addNeighbor(left);
    }

    private static void neighborsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 3) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            handleNeighbor(params.get(0), params.get(1), params.get(2));
        } while (true);
    }

    private static void handleMushroom(String f, String t) {
        var fungus = (Fungus) namedObjects.get(f);
        if (fungus == null) {
            System.out.println(INVALID_ARG + FUNGUS_INVALID);
            return;
        }

        var tecton = (Tecton) namedObjects.get(t);
        if (tecton == null) {
            System.out.println(INVALID_ARG + TECTON_INVALID);
            return;
        }

        Prototype.registerNamedObject(Mushroom.class, new Mushroom(fungus, tecton));
    }

    private static void mushroomsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            handleMushroom(params.get(0), params.get(1));
        } while (true);
    }

    private static void handleMycelium(String t1, String check1, String t2, String check2, String f) {
        if (!check1.equals("..") || !check2.equals(":")) {
            System.out.println(SYNTAX_ERROR + ": invalid mycelium definition");
            return;
        }

        Tecton left = (Tecton) namedObjects.get(t1);
        if (left == null) {
            System.out.println(INVALID_ARG + ": invalid left tecton name");
            return;
        }

        Tecton right = (Tecton) namedObjects.get(t2);
        if (right == null) {
            System.out.println(INVALID_ARG + ": invalid right tecton name");
            return;
        }

        var fungus = (Fungus) namedObjects.get(f);
        if (fungus == null) {
            System.out.println(INVALID_ARG + FUNGUS_INVALID);
            return;
        }

        registerNamedObject(Mycelium.class, new Mycelium(fungus, left, right));
    }

    private static void myceliaMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 5) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            handleMycelium(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4));
        } while (true);
    }

    private static void handleSpore(String f, String t, String db) {
        var fungus = (Fungus) namedObjects.get(f);
        if (fungus == null) {
            System.out.println(INVALID_ARG + FUNGUS_INVALID);
            return;
        }

        var tecton = (Tecton) namedObjects.get(t);
        if (tecton == null) {
            System.out.println(INVALID_ARG + TECTON_INVALID);
            return;
        }

        int dbInt;
        try {
            dbInt = Integer.parseInt(db);
            if (dbInt < 0) {
                System.out.println(SYNTAX_ERROR + ": invalid spore count");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(SYNTAX_ERROR + ": invalid spore count");
            return;
        }

        for (int i = 0; i < dbInt; i++) {
            tecton.addSpore(new Spore(fungus));
        }
    }

    private static void sporesMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            if (params.size() > 3) {
                handleSpore(params.get(0), params.get(1), params.get(2));
            }
            else {
                handleSpore(params.get(0), params.get(1), "1");
            }
        } while (true);
    }

    private static void handleInsect(String c, String t, String s) {
        var colony = (Colony) namedObjects.get(c);
        if (colony == null) {
            System.out.println(INVALID_ARG + ": invalid colony name");
            return;
        }

        var tecton = (Tecton) namedObjects.get(t);
        if (tecton == null) {
            System.out.println(INVALID_ARG + TECTON_INVALID);
            return;
        }

        double speed;
        try {
            speed = Double.parseDouble(s);
            if (speed < 0) {
                System.out.println(SYNTAX_ERROR + ": invalid insect speed");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(SYNTAX_ERROR + ": invalid insect speed");
            return;
        }

        var insect = new Insect(tecton, colony);
        insect.setSpeed(speed);
        registerNamedObject(Insect.class, insect);
    }

    private static void insectsMenu() {
        do {
            String input = System.console().readLine().trim();
            if (input.isEmpty())
                return;
            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            if (params.size() > 3) {
                handleInsect(params.get(0), params.get(1), params.get(2));
            }
            else {
                handleInsect(params.get(0), params.get(1), "1.0");
            }
        } while (true);
    }

    private static void handleFungi(String cnt) {
        try {
            int count = Integer.parseInt(cnt);
            for (int i = 0; i < count; i++) {
                registerNamedObject(Fungus.class, new Fungus());
            }
        } catch (NumberFormatException e) {
            System.out.println(SYNTAX_ERROR + ": invalid fungus definition");
        }
    }

    private static void handleColonies(String cnt) {
        try {
            int count = Integer.parseInt(cnt);
            for (int i = 0; i < count; i++) {
                registerNamedObject(Colony.class, new Colony());
            }
        } catch (NumberFormatException e) {
            System.out.println(SYNTAX_ERROR + ": invalid colony definition");
        }
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
                case "tectons" -> tectonsMenu();
                case "neighbors" -> neighborsMenu();
                case "fungi" -> {
                    if (!params.isEmpty()) {
                        handleFungi(params.get(0));
                    }
                    else {
                        System.out.println(NOT_ENOUGH_ARGS);
                    }
                }
                case "colonies" -> {
                    if (!params.isEmpty()) {
                        handleColonies(params.get(0));
                    }
                    else {
                        System.out.println(NOT_ENOUGH_ARGS);
                    }
                }
                case "mushrooms" -> mushroomsMenu();
                case "mycelia" -> myceliaMenu();
                case "insects" -> insectsMenu();
                case "spores" -> sporesMenu();
                case "end" -> {
                    System.out.println("Entering interaction phase.");
                    return;
                }
                default -> System.out.println(SYNTAX_ERROR + ": invalid command");
            }
        } while (true);
    }
}