package helper;

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

    public static void addObject(Object obj, String name) {
        objNames.put(obj, name);
    }

    static String getObjName(Object obj) {
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

            // Válasz: Szerintem akkor is legyen neki saját neve ha futás közben jön létre,
            // mivel a toString belerakja a pointert és az nem szép. Csináltam is egy
            // publikus felvevő függvényt amit majd a tesztekben lehet használni.

            // Kérdés: És azt ki hívja? Pl a tecton ctor-ban nem lehet, és az
            // implementációkban sem tudom a "szép nevét", ebben a file-ban pedig nem látod
            // mikor és ki jön létre.
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
        printCall(t, List.of(1, 2, t));
        printCall(t, List.of());
        boolean a = ask("Should I return true?");
        printReturn(a);
        printReturn(null);
    }

    static void burstSporeDist1() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t1 = (Tecton) getObjByName("t1");
            m1.burstSpore(t1);
            // TODO: Kérdés: itt hogy akarjuk? A visszakasztolás elég csúnya, de más ötletem
            // nincs mivel a hashmap mindenképp egy Object-et tárol

            // Vélemény: Ez elég gányolásnak néz ki
            // Szerintem possible solution:
            // Minden Map egy class, és az ahhoz tartozó use-case-ek
            // a classban függények, így elérik a privát változókat, amik a cuccok a mapon.
            // Vagy minden static, és van egy init map, vagy ctor-ban építünk fel mindent.
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void burstSporeDist2() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t2 = (Tecton) getObjByName("t2");
            m1.burstSpore(t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void burstSporeDist3() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t3 = (Tecton) getObjByName("t3");
            m1.burstSpore(t3);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    // Be és kimeneti függvények
    // --------------------------------------------------------------------------------

    public static void printCall(Object obj, List<Object> params) {
        if (printOn) {
            String paramStr = "";
            if (params != null && !params.isEmpty()) {
                // TODO: ez itt biztos így akart lenni?
                paramStr = params.get(0).toString();
                for (int i = 1; i < params.size(); i++) {
                    paramStr += ", " + getObjName(params.get(i)); // NOSONAR: nem kell StringBuilder
                }
            }
            var frame = StackWalker.getInstance().walk(s -> s.skip(1).findFirst());
            String fName = frame.isPresent() ? frame.get().getMethodName() : "func";
            if (fName.equals("printCall")) {
                var frame2 = StackWalker.getInstance().walk(s -> s.skip(2).findFirst());
                fName = frame2.isPresent() ? frame2.get().getMethodName() : "func";
            }
            System.out.println("  ".repeat(tabulation) + getObjName(obj) + "." + fName + "(" + paramStr + ")");
            tabulation++;
        }
    }

    public static void printCall(Object obj) {
        printCall(obj, null);
    }

    public static void printCall(Object obj) {
        printCall(obj, null);
    }

    // ha a függvény void visszatérésű, akkor a returnValue legyen üres string
    public static void printReturn(Object returnValue) {
        if (printOn) {
            tabulation--;
            System.out.println("  ".repeat(tabulation) + "return " + getObjName(returnValue));
        }
    }

    public static void printReturn() {
        printReturn(" ");
    }

    public static boolean ask(String question) {
        System.out.println(question + " (Y/n)");
        System.out.print(">");
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
            } catch (NumberFormatException e) { // NOSONAR: szándékosan üres a kezelés, a ciklus újraindul
            }
        }
    }

    public static void main(String[] args) {
        addUseCase(Skeleton::iAmAUseCase, "demoUseCase");
        addUseCase(Skeleton::burstSporeDist1, "Spóraszórás 1 távolságra");
        addUseCase(Skeleton::burstSporeDist2, "Spóraszórás 2 távolságra");
        addUseCase(Skeleton::burstSporeDist3, "Spóraszórás 3 távolságra");
        while (true) {
            int choice = useCaseChooser();
            useCases.get(choice).run();
        }
    }
}