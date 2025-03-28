package proto;

import static proto.Proto.*;

public class Interact {
    private static void interactPhase() {
        String input;
        do {
            input = System.console().readLine();
            if (input.length() == 0) {
                continue;
            }
            if (input.equals("end")) {
                break;
            }

            if (input.startsWith("ls")) {
                for (var e : gameObjects.keySet()) {
                    System.out.println(e);
                }
                continue;
            }

        } while (true);
    }
}
