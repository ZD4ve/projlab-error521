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

    public static String getObjName(Object obj) {
        if (obj == null) {
            return "null";
        } else if (objNames.containsKey(obj)) {
            return objNames.get(obj);
        } else {
            return obj.toString();
            // TODO: Kérdés, de igazából maradhat így is:
            // TODO: ha egy új példányt hozunk létre (pl. törés), akkor hozzunk létre egy új
            // TODO: sorszámozott nevet, pl Effect1 vagy maradjon a toString
            // TODO: illetve ekkor hogy döntjük el, hogy kell e új név (pl Mushroom) vagy
            // TODO: maradhat a toString (pl. Int)

            // Válasz: Szerintem akkor is legyen neki saját neve, mivel a toString belerakja
            // a pointert és az nem szép. Publikussá is tettem a getObjName metódust, hogy a
            // minden osztályból is elérhető legyen.
        }
    }

    static Object getObjByName(String name) {
        for (Map.Entry<Object, String> entry : objNames.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    static void addUseCase(Runnable f, String name) {
        useCases.add(f);
        useCaseNames.put(f, name);
    }

    // Térképek létrehozása
    // --------------------------------------------------------------------------------

    static void burstSporeMap() {
        printOn = false;
        objNames.clear();

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");
        Tecton t2 = new Tecton();
        objNames.put(t2, "t2");
        Tecton t3 = new Tecton();
        objNames.put(t3, "t3");
        Tecton t4 = new Tecton();
        objNames.put(t4, "t4");

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);
        t2.addNeighbor(t3);
        t3.addNeighbor(t2);
        t3.addNeighbor(t4);
        t4.addNeighbor(t3);

        Fungus s1 = new Fungus();
        objNames.put(s1, "s1");
        Mushroom m1 = new Mushroom(s1, t1);
        objNames.put(m1, "m1");

        printOn = true;
    }

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

    static void burstSporeDist1() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t1 = (Tecton) getObjByName("t1");
            m1.burstSpore(t1);
            // TODO: Kérdés: itt hogy akarjuk? A visszakasztolás elég csúnya, de más ötletem
            // nincs mivel a hashmap mindenképp egy Objectet tárol
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
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
        addUseCase(Skeleton::burstSporeDist1, "burst spore to dist 1");
        while (true) {
            int choice = useCaseChooser();
            useCases.get(choice).run();
        }
    }
}