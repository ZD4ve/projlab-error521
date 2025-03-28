package proto;

import static proto.Proto.*;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import model.Fungus;
import model.IActive;
import model.Insect;
import model.InsectEffect;
import model.Mushroom;
import model.Mycelium;
import model.Tecton;

public class Interact {
    private static Set<String> activeTypes = Set.of("in", "te", "my", "mu", "ie");
    private static Map<String, Integer> randConst = Map.of("antichew", 1, "paralysing", 2, "speed", 3, "fission", 4, "break", Integer.MAX_VALUE);

    public static void interactPhase() {
        String[] input;
        do {
            input = System.console().readLine().split(" ");
            if (input.length == 0) {
                continue;
            }
            if (input[0].equals("end")) {
                break;
            }

            if (input[0].equals("ls")) {
                // will depend on output lang
                System.out.println("Objects:");
                for (var e : gameObjects.keySet()) {
                    System.out.println(e);
                }
                continue;
            }

            if (input[0].equals("tick")) {
                if (input.length < 2) {
                    System.out.println("Not enough args.");
                    continue;
                }
                float dT = Float.parseFloat(input[1]);
                for (var o : gameObjects.entrySet()) {
                    if (activeTypes.contains(o.getKey().substring(0, 2))) {
                        IActive element = (IActive) o.getValue();
                        element.tick(dT);
                    }
                }
            }

            if (input[0].equals("nextrand")) {
                if (input.length < 2) {
                    System.out.println("Not enough args.");
                    continue;
                }

                int next = randConst.getOrDefault(input[1], Integer.parseInt(input[1]));
                RandomProvider.addNext(next);
            }

            if (input[0].startsWith(names.get(Fungus.class))) {
                if (input.length < 3) {
                    System.out.println("Not enough args.");
                }
                Fungus f = (Fungus) gameObjects.get(input[0]);
                if (input[1].equals("growmushroom")) {
                    if (!input[2].startsWith(names.get(Tecton.class))) {
                        System.out.println("3rd arg must be a tecton.");
                        continue;
                    }
                    Tecton target = (Tecton) gameObjects.get(input[2]);
                    if (!f.growMushroom(target)) {
                        System.out.println("Mushroom can't be grown there.");
                    } else {
                        gameObjects.put(String.format(names.get(Mushroom.class) + "%02d", gameObjects.keySet().stream().filter(x -> x.startsWith(names.get(Mushroom.class))).map(x -> Integer.parseInt(x.substring(2))).max((x, y) -> x > y ? 1 : 0).orElse(0) + 1), target.getMushroom());
                    }
                } else if (input[1].equals("growmycelium")) {
                    if (input.length < 4) {
                        System.out.println("4 args needed.");
                    }
                    if (!input[2].startsWith(names.get(Tecton.class)) || !input[3].startsWith(names.get(Tecton.class))) {
                        System.out.println("3rd arg must be a tecton.");
                        continue;
                    }
                    Tecton t1 = (Tecton) gameObjects.get(input[2]);
                    Tecton t2 = (Tecton) gameObjects.get(input[3]);
                    if (!f.growMycelium(t1, t2)) {
                        System.out.println("Mycelium can't be grown there.");
                    } else {
                        gameObjects.put(String.format(names.get(Mycelium.class) + "%02d", gameObjects.keySet().stream().filter(x -> x.startsWith(names.get(Mycelium.class))).map(x -> Integer.parseInt(x.substring(2))).max((x, y) -> x > y ? 1 : 0).orElse(0) + 1), t1.getMycelia().get(t1.getMycelia().size() - 1));
                    }
                }
            }

            if (input[0].startsWith(names.get(Mushroom.class))) {
                Mushroom m = (Mushroom) gameObjects.get(input[0]);
                if (input.length < 3) {
                    System.out.println("3 args needed.");
                    continue;
                }
                if (!input[1].equals("burst") || !input[2].startsWith(names.get(Tecton.class))) {
                    System.out.println("op not supported");
                    continue;
                }
                Tecton target = (Tecton) gameObjects.get(input[2]);
                if (!m.burstSpore(target)) {
                    System.out.println("Couldn't burst there.");
                    continue;
                }
                if (m.getLocation().getMushroom() != m) {
                    gameObjects.remove(input[0]);
                    System.out.println("Mushroom " + input[0] + "has died.");
                }
            }

            if (input[0].startsWith(names.get(Insect.class))) {
                if (input.length < 2) {
                    System.out.println("Need at least args.");
                    continue;
                }

                Insect in = (Insect) gameObjects.get(input[0]);

                if (input[1].equals("move")) {
                    if (input.length < 3) {
                        System.out.println("Need 3 args.");
                        continue;
                    }

                    Tecton target = (Tecton) gameObjects.get(input[2]);
                    if (!in.moveTo(target)) {
                        System.out.println("Can't go there.");
                    }
                } else if (input[1].equals("chew")) {
                    if (input.length < 3) {
                        System.out.println("Need 3 args.");
                        continue;
                    }
                    Mycelium target = (Mycelium) gameObjects.get(input[2]);
                    if (!in.chewMycelium(target)) {
                        System.out.println("Can't chew that.");
                    }
                } else if (input[1].equals("eat")) {
                    if (!in.eatSpore()) {
                        System.out.println("Can't eat there.");
                    } else {
                        gameObjects.put(String.format(names.get(InsectEffect.class) + "%02d", gameObjects.keySet().stream().filter(x -> x.startsWith(names.get(InsectEffect.class))).map(x -> Integer.parseInt(x.substring(2))).max((x, y) -> x > y ? 1 : 0).orElse(0) + 1), in.getActiveEffects().get(in.getActiveEffects().size() - 1));
                    }
                }
            }

        } while (true);
    }
}
