package tester;

import java.io.*;
import java.util.*;

/**
 * Tesztelő program, amely a tesztek futtatásáért és eredményeik kiértékeléséért felelős. A tesztek fájlokból kerülnek
 * beolvasásra, és a tesztelés eredményei a konzolra kerülnek kiírásra.
 */
@SuppressWarnings("java:S106")
public class Tester {
    /** Tesztek */
    private static List<Test> tests;

    private static boolean rerun = false;
    private static Scanner scanner;

    /**
     * Fő metódus, amely elindítja a tesztelést. Először lefordítja a Fungorium programot, majd beolvassa a teszteket,
     * és végrehajtja azokat. A tesztek eredményei a konzolra kerülnek kiírásra.
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        do {
            rerun = false;
            build();
            readTests();
            System.out.println("Running tests...");
            tests.parallelStream().forEach(Test::run);
            System.out.println("Tests finished!");
            sum();
            interact();
        } while (rerun);
        scanner.close();
    }

    /**
     * Lefordítja a Fungorium programot a megfelelő operációs rendszernek megfelelően. Windows esetén a build.bat fájlt,
     * míg Linux operációs rendszerek esetén a build.sh fájlt futtatja.
     */
    static void build() {
        System.out.println("Building...");
        String os = System.getProperty("os.name").toLowerCase();
        String command = os.contains("win") ? "build.bat" : "./build.sh";
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Beolvassa a teszteket a megadott könyvtárból. A tesztek fájlnevei alapján létrehozza a Test objektumokat, és
     * eltárolja őket egy listában.
     */
    static void readTests() {
        tests = new ArrayList<>();
        File testsDir = new File(Test.TESTS_DIR);
        for (File folder : testsDir.listFiles(File::isDirectory)) {
            for (File file : folder.listFiles(f -> f.getName().endsWith(".map"))) {
                try {
                    tests.add(new Test(folder.getName(), file.getName().substring(0, file.getName().length() - 4)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        tests.sort(Comparator.comparing(Test::getDir).thenComparing(Test::getName));
    }

    /**
     * Interaktív konzolt biztosít a felhasználónak, ahol különböző parancsokat adhat meg a tesztek kezelésére. A
     * parancsok közé tartozik a tesztek összegzése, a különbségek megjelenítése, a bemenetek és kimenetek
     * megjelenítése, valamint a tesztek újrafuttatása.
     */
    static void interact() {// NOSONAR cognitive complexity ekkora kell hogy legyen
        boolean exit = false;
        String command;
        do {
            System.out.print("> ");
            String[] line = scanner.nextLine().split(" ");
            command = line.length > 0 ? line[0] : "";

            switch (line.length) {
            case 0:
                break;
            case 1:
                switch (command) {
                case "sum" -> sum();
                case "help" -> help();
                case "diff" -> notEnoughArgs();
                case "inp" -> notEnoughArgs();
                case "exp" -> notEnoughArgs();
                case "act" -> notEnoughArgs();
                case "exit" -> exit = true;
                case "rerun" -> rerun = exit = true;
                default -> System.out.println("Unknown command: " + command);
                }
                break;
            case 2:
                int testIndex;
                try {
                    testIndex = Integer.parseInt(line[1]) - 1;
                } catch (NumberFormatException e) {
                    testIndex = -1;
                }
                if (testIndex < 0 || testIndex >= tests.size()) {
                    System.out.println("Invalid test number");
                    continue;
                }
                Test t = tests.get(testIndex);

                switch (command) {
                case "diff" -> diff(t);
                case "inp" -> inp(t);
                case "exp" -> exp(t);
                case "act" -> act(t);
                case "save" -> save(t);
                default -> System.out.println("Unknown command: " + command);
                }
                break;
            default:
                System.out.println("Invalid command");
                break;
            }

        } while (!exit);
    }

    /** Kiírja, hogy nem adtak meg teszt számot. */
    static void notEnoughArgs() {
        System.out.println("No test number provided");
    }

    // #region COMMANDS

    /** Kiírja a parancsok listáját, amelyeket a felhasználó használhat az interaktív konzolon. */
    static void help() {
        System.out.println("  sum              - Summary");
        System.out.println("  diff <test num>  - First difference between expected and actual output");
        System.out.println("  inp <test num>   - Input");
        System.out.println("  exp <test num>   - Expected output");
        System.out.println("  act <test num>   - Actual output");
        System.out.println("  save <test num>  - Save actual output to .resa file");
        System.out.println("  rerun            - Rebuild and rerun tests");
        System.out.println("  exit             - Exit");
    }

    /** Kiírja a tesztek összegzését, beleértve a teszt könyvtárát, nevét és állapotát (sikeres vagy sikertelen). */
    static void sum() {
        String prevDir = "";
        for (int i = 0; i < tests.size(); i++) {
            Test t = tests.get(i);
            if (!t.getDir().equals(prevDir)) {
                System.out.println("      " + t.getDir());
                prevDir = t.getDir();
            }
            System.out.format("%s %02d %s%n", t.hasPassed() ? "[PASS]  " : "[FAIL] X", i + 1, t.getName());
        }
        System.out.println("Passed " + tests.stream().filter(Test::hasPassed).count() + "/" + tests.size() + " tests");
    }

    /** Ha a teszt nem sikerült, akkor kiírja a hibás teszt első eltérését a várt és a tényleges kimenet között. */
    static void diff(Test t) {
        int idx = t.getDiffLine();
        if (idx == -1) {
            System.out.println("The actual output is the same as the expected output");
            return;
        }
        if (idx == Math.min(t.getResult().size(), t.getActualOutput().size())) {
            System.out.println("The actual output is not the same length as the expected output");
            return;
        }
        System.out.println("First difference at line " + (idx + 1));
        System.out.println("Expected: \n" + t.getResult().get(idx));
        System.out.println("Actual: \n" + t.getActualOutput().get(idx));
    }

    /** Kiírja a teszt bemenetét. */
    static void inp(Test t) {
        for (String line : t.getInput()) {
            System.out.println(line);
        }
    }

    /** Kiírja a teszt várt kimenetét. */
    static void exp(Test t) {
        for (String line : t.getResult()) {
            System.out.println(line);
        }
    }

    /** Kiírja a teszt tényleges kimenetét. */
    static void act(Test t) {
        for (String line : t.getActualOutput()) {
            System.out.println(line);
        }
    }

    /** Elmenti a teszt tényleges kimenetét egy .resa fájlba. */
    static void save(Test t) {
        File outputFile = new File(Test.TESTS_DIR + File.separator + t.getPath() + ".resa");
        if (outputFile.exists()) {
            System.out.println("File already exists, overwriting!");
            outputFile.delete();// NOSONAR
        }
        try {
            outputFile.createNewFile();// NOSONAR
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            for (String line : t.getActualOutput()) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // #endregion
}