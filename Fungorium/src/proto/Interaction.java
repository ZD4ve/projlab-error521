package proto;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import model.*;
import controller.*;
import static proto.Prototype.*;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class Interaction {
    private static final String SYNTAX_ERROR = "Syntax error";
    private static final String NOT_ENOUGH_ARGS = "Syntax error: not enough args";
    private static final String INVALID_ARG = "Syntax error: invalid argument";

    private Interaction() {
    }

    private static void printTectonNamesVertically(List<SimpleEntry<String, Tecton>> tectons) {
        for (int i = 0; i < 4; i++) {
            for (Entry<String, Tecton> t : tectons) {
                System.out.print(t.getKey().charAt(i));
            }
            System.out.println();
        }
    }

    private static void printNeighborMatrix(List<SimpleEntry<String, Tecton>> tectons) {

        // header
        printTectonNamesVertically(tectons);
        // matrix
        for (Entry<String, Tecton> trow : tectons) {
            for (Entry<String, Tecton> tcol : tectons) {
                if (trow.getValue().getNeighbors().contains(tcol.getValue())) {
                    System.out.print('X');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.printf("|%s %s%n", trow.getKey(), Prototype.tectonTypes.get(trow.getValue().getClass()));
        }
    }

    private static void printMyceliumMatrix(List<SimpleEntry<String, Tecton>> tectons, SimpleEntry<String, Fungus> f) {
        System.out.printf("%s %d%n", f.getKey(), f.getValue().getScore());
        // header
        printTectonNamesVertically(tectons);
        // matrix
        for (Entry<String, Tecton> trow : tectons) {
            for (Entry<String, Tecton> tcol : tectons) {
                long myCount = trow.getValue().getMycelia().stream()
                        .filter(x -> Arrays.asList(x.getEnds()).stream().filter(y -> y != trow.getValue())
                                .anyMatch(y -> y == tcol.getValue()) && x.getSpecies() == f.getValue())
                        .count();
                // we only have 1 char of space :(
                System.out.print(myCount % 10);
            }
            long spores = trow.getValue().getSpores().stream().filter(x -> x.getSpecies() == f.getValue()).count();
            var mushroom = trow.getValue().getMushroom();
            char muState = ' ';
            if (mushroom != null) {
                muState = mushroom.getIsGrown() ? 'M' : 'm';
            }
            System.out.printf("|%s %d %c%n", trow.getKey(), spores, muState);
        }
    }

    private static void printInsects(Map<String, Object> map) {
        var colonies = namedObjects.entrySet().stream().filter(x -> x.getKey().startsWith(names.get(Colony.class)))
                .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), (Colony) x.getValue()))
                .sorted((x, y) -> x.getKey().compareTo(y.getKey())).toList();

        for (SimpleEntry<String, Colony> colony : colonies) {
            System.out.printf("%s %d%n", colony.getKey(), colony.getValue().getScore());
            var insects = namedObjects.entrySet().stream().filter(x -> x.getKey().startsWith(names.get(Insect.class)))
                    .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), (Insect) x.getValue()))
                    .filter(x -> colony.getValue().getInsects().contains(x.getValue()))
                    .sorted((x, y) -> x.getKey().compareTo(y.getKey())).toList();
            for (SimpleEntry<String, Insect> insect : insects) {
                System.out.printf("  %s%n", insect.getKey());
                var loctec = insect.getValue().getLocation();
                var tectEntry = map.entrySet().stream().filter(x -> x.getKey().startsWith(names.get(Tecton.class)))
                        .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), (Tecton) x.getValue()))
                        .filter(x -> x.getValue() == loctec).findFirst();
                if (tectEntry.isPresent()) {
                    System.out.printf("    %s%n", tectEntry.get().getKey());
                } else {
                    System.out.println();
                }

                for (InsectEffect effect : insect.getValue().getActiveEffects()) {
                    String effectType = Prototype.effectTypes.get(effect.getClass());
                    System.out.printf("    %s", effectType);
                    if (effectType.equals(Prototype.effectTypes.get(SpeedEffect.class))) {
                        System.out.printf("-%.1f", ((SpeedEffect) effect).getMultiplier());
                    }
                    System.out.print(' ');
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void printState() {
        var tectons = namedObjects.entrySet().stream().filter(x -> x.getKey().startsWith(names.get(Tecton.class)))
                .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), (Tecton) x.getValue()))
                .sorted((x, y) -> x.getKey().compareTo(y.getKey())).toList();
        printNeighborMatrix(tectons);
        System.out.println();
        var fungi = namedObjects.entrySet().stream().filter(x -> x.getKey().startsWith(names.get(Fungus.class)))
                .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), (Fungus) x.getValue()))
                .sorted((x, y) -> x.getKey().compareTo(y.getKey())).toList();
        for (SimpleEntry<String, Fungus> fungus : fungi) {
            printMyceliumMatrix(tectons, fungus);
            System.out.println();
        }
        printInsects(namedObjects);
    }

    private static void handleFungus(String[] input) {
        if (input.length < 3) {
            System.out.println(NOT_ENOUGH_ARGS);
            return;
        }

        Fungus f = (Fungus) namedObjects.get(input[0]);
        if (f == null) {
            System.out.println(INVALID_ARG);
            return;
        }

        if (input[1].equals("growmushroom")) {
            if (!input[2].startsWith(Prototype.names.get(Tecton.class))) {
                System.out.println(SYNTAX_ERROR + ": 3rd arg must be a tecton");
                return;
            }

            Tecton target = (Tecton) namedObjects.get(input[2]);
            if (target == null) {
                System.out.println(INVALID_ARG);
                return;
            }

            if (!f.growMushroom(target)) {
                System.out.println("growmushroom failed");
            } else {
                Prototype.registerNamedObject(Mushroom.class, target.getMushroom());
            }
        } else if (input[1].equals("growmycelium")) {
            handleFungusGrowMycelium(f, input);
        }
    }

    private static void handleFungusGrowMycelium(Fungus f, String[] input) {
        if (input.length < 4) {
            System.out.println(SYNTAX_ERROR + ": 4 args needed.");
            return;
        }

        if (!input[2].startsWith(Prototype.names.get(Tecton.class))
                || !input[3].startsWith(Prototype.names.get(Tecton.class))) {
            System.out.println(SYNTAX_ERROR + ": 3rd arg must be a tecton.");
            return;
        }
        Tecton t1 = (Tecton) namedObjects.get(input[2]);
        Tecton t2 = (Tecton) namedObjects.get(input[3]);
        if (t1 == null || t2 == null) {
            System.out.println(INVALID_ARG);
            return;
        }

        if (!f.growMycelium(t1, t2)) {
            System.out.println("growmycelium failed");
        } else {
            var mycelia = t1.getMycelia().get(t1.getMycelia().size() - 1);
            Prototype.registerNamedObject(Mycelium.class, mycelia);
        }
    }

    private static void handleMushroom(String[] input) {
        Mushroom m = (Mushroom) namedObjects.get(input[0]);
        if (m == null) {
            System.out.println(INVALID_ARG);
            return;
        }

        if (input.length < 3) {
            System.out.println(NOT_ENOUGH_ARGS);
            return;
        }

        if (!input[1].equals("burst") || !input[2].startsWith(Prototype.names.get(Tecton.class))) {
            System.out.println(INVALID_ARG);
            return;
        }

        Tecton target = (Tecton) namedObjects.get(input[2]);
        if (target == null) {
            System.out.println(INVALID_ARG);
            return;
        }

        if (!m.burstSpore(target)) {
            System.out.println("burst failed");
        }

        // handle mushroom died
        else if (m.getLocation().getMushroom() == null) {
            namedObjects.remove(input[0]);
        }
    }

    private static void handleInsect(String[] input) {
        if (input.length < 2) {
            System.out.println(NOT_ENOUGH_ARGS);
            return;
        }

        Insect in = (Insect) namedObjects.get(input[0]);
        if (in == null) {
            System.out.println(INVALID_ARG);
            return;
        }

        if (input[1].equals("move")) {
            if (input.length < 3) {
                System.out.println(NOT_ENOUGH_ARGS);
                return;
            }

            Tecton target = (Tecton) namedObjects.get(input[2]);
            if (target == null) {
                System.out.println(INVALID_ARG);
                return;
            }
            if (!in.moveTo(target)) {
                System.out.println("move failed");
            }
        } else if (input[1].equals("chew")) {
            handleInsectChew(in, input);
        } else if (input[1].equals("eat") && !in.eatSpore()) {
            System.out.println("eat failed");
        }
    }

    private static void handleInsectChew(Insect in, String[] input) {
        if (input.length < 3) {
            System.out.println(NOT_ENOUGH_ARGS);
            return;
        }
        Mycelium target = (Mycelium) namedObjects.get(input[2]);
        if (target == null) {
            System.out.println(INVALID_ARG);
            return;
        }
        if (!in.chewMycelium(target)) {
            System.out.println("chew failed");
        } else {
            namedObjects.remove(input[2]);
        }
    }

    private static void handleTick(String[] input) {
        if (input.length < 2) {
            System.out.println(SYNTAX_ERROR);
            return;
        }
        double dT = Double.parseDouble(input[1]);
        Controller.onTimeElapsed(dT);
    }

    private static void handleNextRand(String[] input) {
        if (input.length < 2) {
            System.out.println(SYNTAX_ERROR);
            return;
        }

        int n = 1;
        if (input.length > 2) {
            try {
                n = Integer.parseInt(input[2]);
                if (n <= 0) {
                    System.out.println(SYNTAX_ERROR);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(SYNTAX_ERROR);
                return;
            }
        }

        for (int i = 0; i < n; i++) {
            double next = RandomProvider.RandomConstants.getOrDefault(input[1], Double.parseDouble(input[1]));
            RandomProvider.addNext(next);
        }
    }

    private static void handleReset() {
        Controller.reset();
        namedObjects.clear();
    }

    public static boolean handleInteractions() {
        String[] input;
        do {
            if (scanner.hasNextLine()) {
                input = scanner.nextLine().split(" ");
            } else {
                input = new String[] { "exit" };
            }

            if (input.length == 0) {
                // we ignore empty line without using continue
            } else if (input[0].equals("exit")) {
                return false;
            } else if (input[0].equals("printstate")) {
                printState();
            } else if (input[0].equals("tick")) {
                handleTick(input);
            } else if (input[0].equals("reset")) {
                handleReset();
                return true;
            } else if (input[0].equals("nextrand")) {
                handleNextRand(input);
            } else if (input[0].startsWith(Prototype.names.get(Mushroom.class))) {
                handleMushroom(input);
            } else if (input[0].startsWith(Prototype.names.get(Insect.class))) {
                handleInsect(input);
            } else if (input[0].startsWith(Prototype.names.get(Fungus.class))) {
                handleFungus(input);
            } else {
                System.out.println(SYNTAX_ERROR);
            }
        } while (true);
    }
}
