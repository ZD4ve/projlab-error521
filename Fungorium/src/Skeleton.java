import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class Skeleton {
    private Skeleton() {
    } // Skeleton ne legyen példányosítható

    static int tabulation = 0;
    static boolean printOn = true;

    static Map<Object, String> objNames = new HashMap<>();

    static List<Runnable> useCases = new ArrayList<>();
    static Map<Runnable, String> useCaseNames = new HashMap<>();

    static String getObjName(Object obj) {
        if (obj == null) {
            return "null";
        } else if (objNames.containsKey(obj)) {
            return objNames.get(obj);
        } else {
            return obj.toString();
        }
    }

    static void addUseCase(Runnable f, String name) {
        useCases.add(f);
        useCaseNames.put(f, name);
    }

    // Térképek létrehozása
    // --------------------------------------------------------------------------------

    // Use case-ek
    // --------------------------------------------------------------------------------

    static void iAmAUseCase() {
        // create map
        Object t = new Tecton();
        objNames.put(t, "t");
        // start sequence
        printCall(t, "test", List.of(1, 2, t));
        printReturn(null);
    }

    // Be és kimeneti függvények
    // --------------------------------------------------------------------------------

    public static void printCall(Object obj, String fName, List<Object> params) {
        if (printOn) {
            tabulation++;
            String paramStr = "";
            if (!params.isEmpty()) {
                paramStr = params.get(0).toString();
                for (int i = 1; i < params.size(); i++) {
                    paramStr += ", " + getObjName(params.get(i)); // NOSONAR: nem kell StringBuilder
                }
            }
            System.out.println("  ".repeat(tabulation) + getObjName(obj) + "." + fName + "(" + paramStr + ")");
        }
    }

    // ha a függvénynél nincs visszatérési érték, akkor a ret legyen üres string
    public static void printReturn(Object ret) {
        if (printOn) {
            System.out.println("  ".repeat(tabulation) + "return " + getObjName(ret));
            tabulation--;
        }
    }

    public static boolean ask(String question) {
        System.out.println(question + " (Y/n)");
        String answer = System.console().readLine();
        return !answer.equals("n");
    }

    static int useCaseChooser() {
        for (int i = 0; i < useCases.size(); i++) {
            System.out.println(i + 1 + ": " + useCaseNames.get(useCases.get(i)));
        }
        while (true) {
            System.out.print(">");
            try {
                String inp = System.console().readLine();
                if (inp.equals("exit")) {
                    System.exit(0);
                }
                int choice = Integer.parseInt(inp) - 1;
                if (choice >= 0 && choice < useCases.size()) {
                    return choice;
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    public static void main(String[] args) {
        addUseCase(Skeleton::iAmAUseCase, "demoUseCase");
        while (true) {
            int choice = useCaseChooser();
            useCases.get(choice).run();
        }
    }
}