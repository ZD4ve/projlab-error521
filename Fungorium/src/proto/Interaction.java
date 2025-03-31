package proto;

import controller.Controller;
import controller.RandomProvider;
import model.Fungus;
import model.Insect;
import model.InsectEffect;
import model.Mushroom;
import model.Mycelium;
import model.Tecton;

import static proto.Prototype.*;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class Interaction {
    private static final String SYNTAX_ERROR = "Syntax error";
    private static final String NOT_ENOUGH_ARGS = "Syntax error: not enough args";
    private static final String INVALID_ARG = "Syntax error: invalid argument";

    private Interaction() {
    }

    private static void printState() {
        // will depend on output lang
        System.out.println("Objects:");
        for (var e : namedObjects.keySet()) {
            System.out.println(e);
        }
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

        if (!input[2].startsWith(Prototype.names.get(Tecton.class)) || !input[3].startsWith(Prototype.names.get(Tecton.class))) {
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
        } else if (input[1].equals("eat")) {
            if (!in.eatSpore()) {
                System.out.println("eat failed");
            } else {
                var effect = in.getActiveEffects().get(in.getActiveEffects().size() - 1);
                Prototype.registerNamedObject(InsectEffect.class, effect);
            }
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

        double next = RandomProvider.RandomConstants.getOrDefault(input[1], Double.parseDouble(input[1]));
        RandomProvider.addNext(next);
    }

    public static void handleInteractions() {
        String[] input;
        do {
            input = System.console().readLine().split(" ");

            if (input.length == 0) {
                // we ignore empty line without using continue
            } else if (input[0].equals("end")) {
                break;
            } else if (input[0].equals("printstate")) {
                printState();
            } else if (input[0].equals("tick")) {
                handleTick(input);

            } else if (input[0].equals("nextrand")) {
                handleNextRand(input);
            } else if (input[0].startsWith(Prototype.names.get(Mushroom.class))) {
                handleMushroom(input);
            } else if (input[0].startsWith(Prototype.names.get(Insect.class))) {
                handleInsect(input);
            } else if (input[0].startsWith(Prototype.names.get(Fungus.class))) {
                handleFungus(input);
            }
        } while (true);
    }
}
