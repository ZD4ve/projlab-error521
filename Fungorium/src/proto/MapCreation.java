package proto;

import java.util.ArrayList;
import java.util.Arrays;
import model.*;
import static proto.Prototype.*;

/**
 * <h3>MapCreation</h3>
 * 
 * A térkép létrehozásáért felelős osztály. A felhasználó a parancssorban adhatja meg a tektonok, gombafonalak, gombatestek,
 * spórák, kolóniák és rovarok létrehozásához szükséges adatokat.
 */
@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class MapCreation {
    private MapCreation() {}

    /** Gyakran használt hiba üzenetek, amik a kód többi részében is előfordulnak. */
    private static final String SYNTAX_ERROR = "Syntax error";
    private static final String NOT_ENOUGH_ARGS = "Syntax error: not enough args";
    private static final String INVALID_ARG = "Syntax error: invalid argument";
    private static final String FUNGUS_INVALID = ": invalid fungus name";
    private static final String TECTON_INVALID = ": invalid tecton name";
    private static final String SPORE_INVALID = ": invalid spore count";

    /**
     * Egy új tekton létrehozása. A tekton típusa a paraméter alapján kerül meghatározásra.
     * Ha nem megfelelő a tekton típusa, akkor hibaüzenetet ír ki.
     * 
     * @param tecton a tekton típusa
     */
    private static void handleTecton(String tecton) {
        switch (tecton) {
            case "tect" -> registerNamedObject(Tecton.class, new Tecton());
            case "nomu" -> registerNamedObject(Tecton.class, new NoMushroomTecton());
            case "simy" -> registerNamedObject(Tecton.class, new SingleMyceliumTecton());
            case "myab" -> registerNamedObject(Tecton.class, new MyceliumAbsorbingTecton());
            case "myke" -> registerNamedObject(Tecton.class, new MyceliumKeepingTecton());
            default -> System.out.println(SYNTAX_ERROR + ": invalid tecton type");
        }
    }

    /**
     * A tektonok létrehozásáért felelős almenüt megvalósító metódus. 
     * A felhasználó a parancssorban adhatja meg a tekton típusát.
     * 
     * @param input a felhasználó által megadott bemenet
     */
    private static void tectonsMenu() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                return;

            handleTecton(input);
        } while (true);
    }

    /**
     * Két tekton szomszédosságának létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a második paraméter az előírtaknak megfelelő-e, továbbá léteznek-e a tektonok, és ha igen, akkor létrehozza a szomszédosságot.
     * 
     * @param t1    a bal oldali tekton neve
     * @param check a szomszédság jelölését ellenőrző szöveg
     * @param t2    a jobb oldali tekton neve
     */
    private static void handleNeighbor(String t1, String check, String t2) {
        if (!check.equals("--")) {
            System.out.println(SYNTAX_ERROR + ": invalid neighbor definition");
            return;
        }

        var left = (Tecton) namedObjects.get(t1);
        if (left == null) {
            System.out.println(INVALID_ARG + ": invalid left tecton name");
            return;
        }

        var right = (Tecton) namedObjects.get(t2);
        if (right == null) {
            System.out.println(INVALID_ARG + ": invalid right tecton name");
            return;
        }

        left.addNeighbor(right);
        right.addNeighbor(left);
    }

    /**
     * A tektonok szomszédosságának beállításáért felelős almenüt megvalósító metódus.
     * A felhasználó a parancssorban adhatja meg a tektonok nevét az előírt szintaktika alapján.
     */
    private static void neighborsMenu() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
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

    /**
     * Gombatest létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a gombafaj és a tekton létezik-e, és ha igen, akkor létrehozza a gombatestet,
     * illetve beállítja az opcionális paraméteret, ha azok adottak.
     * 
     * @param f        a gombafaj neve
     * @param t        a tekton neve
     * @param isGrown  a gombatest növekedésének állapota
     * @param sporeCount a gombatest spóráinak száma
     */
    private static void handleMushroom(String f, String t, String isGrown, String sporeCount) { // NOSONAR
        boolean isGrownBoolean = false;
        if (isGrown.equals("true")) {
            isGrownBoolean = true;
        } else if (!isGrown.equals("false")) {
            System.out.println(SYNTAX_ERROR + ": invalid mushroom growth status");
            return;
        }

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

        if (sporeCount == null) {
            var mushroom = new Mushroom(fungus, tecton);
            mushroom.setIsGrown(isGrownBoolean);
            Prototype.registerNamedObject(Mushroom.class, mushroom);
            return;
        }

        int sporeCountInt;
        try {
            sporeCountInt = Integer.parseInt(sporeCount);
            if (sporeCountInt < 0) {
                System.out.println(INVALID_ARG + SPORE_INVALID);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(SYNTAX_ERROR + SPORE_INVALID);
            return;
        }

        var mushroom = new Mushroom(fungus, tecton, sporeCountInt);
        mushroom.setIsGrown(isGrownBoolean);
        Prototype.registerNamedObject(Mushroom.class, mushroom);
    }
    
    /**
     * A gombatestek létrehozásáért felelős almenüt megvalósító metódus.
     * A felhasználó a parancssorban adhatja meg a gombafaj nevét, a tekton nevét, a gombatest növekedésének állapotát és az opcionális spóraszámot.
     */
    private static void mushroomsMenu() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            if (params.size() > 2 && params.size() < 4) {
                handleMushroom(params.get(0), params.get(1), params.get(2), null);
            } else if (params.size() > 3) {
                handleMushroom(params.get(0), params.get(1), params.get(2), params.get(3));
            } else {
                handleMushroom(params.get(0), params.get(1), "false", null);
            }
        } while (true);
    }

    /**
     * Gombafonal létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a gombafonalak és a gombatest léteznek-e, és ha igen, akkor létrehozza a gombafonalat.
     * 
     * @param t1      a bal oldali tekton neve
     * @param check1  a tektonok közötti kapcsolat jelölése
     * @param t2      a jobb oldali tekton neve
     * @param check2  a gombafajhoz való tartozás jelölése
     * @param f       a gombafaj neve
     */
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

        var mycelium = new Mycelium(fungus, left, right);
        mycelium.tick(25);
        registerNamedObject(Mycelium.class, mycelium);

    }

    /**
     * A gombafonalak létrehozásáért felelős almenüt megvalósító metódus.
     * A felhasználó a parancssorban adhatja meg a tektonok nevét, a gombafaj nevét és az opcionális paramétereket.
     */
    private static void myceliaMenu() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
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

    /**
     * Spóra létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a gombafaj és a tekton léteznek-e, és ha igen, akkor létrehozza a spórát,
     * illetve spórákat, ha darabszám is meg lett adva.
     * 
     * @param f a gombafaj neve
     * @param t a tekton neve
     * @param db a létrehozandó spórák száma
     */
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
                System.out.println(SYNTAX_ERROR + SPORE_INVALID);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(SYNTAX_ERROR + SPORE_INVALID);
            return;
        }

        for (int i = 0; i < dbInt; i++) {
            tecton.addSpore(new Spore(fungus));
        }
    }

    /**
     * A spórák létrehozásáért felelős almenüt megvalósító metódus.
     * A felhasználó a parancssorban adhatja meg a gombafaj nevét, a tekton nevét és az opcionális darabszámot.
     */
    private static void sporesMenu() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                return;

            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            if (params.size() > 3) {
                handleSpore(params.get(0), params.get(1), params.get(2));
            } else {
                handleSpore(params.get(0), params.get(1), "1");
            }
        } while (true);
    }

    /**
     * Rovar létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a kolónia és a tekton léteznek-e, és ha igen, akkor létrehozza a rovart,
     * illetve beállítja a sebességét.
     * 
     * @param c a kolónia neve
     * @param t a tekton neve
     * @param s a rovar sebessége
     */
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

    /**
     * A rovarok létrehozásáért felelős almenüt megvalósító metódus.
     * A felhasználó a parancssorban adhatja meg a kolónia nevét, a tekton nevét és az opcionális sebességet.
     */
    private static void insectsMenu() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                return;
            var params = new ArrayList<>(Arrays.asList(input.split(" ")));
            if (params.size() < 2) {
                System.out.println(NOT_ENOUGH_ARGS);
                continue;
            }

            if (params.size() > 3) {
                handleInsect(params.get(0), params.get(1), params.get(2));
            } else {
                handleInsect(params.get(0), params.get(1), "1.0");
            }
        } while (true);
    }

    /**
     * Gombafaj létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a darabszám helyes formátumú-e,
     * és ha igen, akkor létrehozz cnt darabszámú a gombafajt,
     * 
     * @param cnt a darabszám
     */
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

    /**
     * Kolónia létrehozásáért felelős metódus.
     * A paraméterek alapján ellenőrzi, hogy a darabszám helyes formátumú-e,
     * és ha igen, akkor létrehozz cnt darabszámú kolóniát.
     * 
     * @param cnt a darabszám
     */
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

    /**
     * A főmenüt megvalósító metódus.
     * A felhasználó a parancssorban adhatja meg az előírt szintaktika alapján a parancsokat.
     */
    public static void createMap() {
        do {
            if (!scanner.hasNextLine())
                return;
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                continue;

            var tokens = new ArrayList<>(Arrays.asList(input.split(" ")));
            var command = tokens.get(0).toLowerCase();
            var params = new ArrayList<>(tokens.subList(1, tokens.size()));

            switch (command) {
                case "tectons" -> tectonsMenu();
                case "neighbors" -> neighborsMenu();
                case "fungi" -> {
                    if (!params.isEmpty()) {
                        handleFungi(params.get(0));
                    } else {
                        System.out.println(NOT_ENOUGH_ARGS);
                    }
                }
                case "colonies" -> {
                    if (!params.isEmpty()) {
                        handleColonies(params.get(0));
                    } else {
                        System.out.println(NOT_ENOUGH_ARGS);
                    }
                }
                case "mushrooms" -> mushroomsMenu();
                case "mycelia" -> myceliaMenu();
                case "insects" -> insectsMenu();
                case "spores" -> sporesMenu();
                case "end" -> { return; }
                default -> System.out.println(SYNTAX_ERROR + ": invalid command");
            }
        } while (true);
    }
}