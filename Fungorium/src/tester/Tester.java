package tester;

import java.io.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class Tester {
    private static List<Test> tests;
    private static boolean rerun = false;
    private static Scanner scanner;

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

    static void interact() {
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
                int testIndex = Integer.parseInt(line[1]) - 1;
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

    static void notEnoughArgs() {
        System.out.println("No test number provided");
    }

    // #region COMMANDS
    static void sum() {
        String prevDir = "";
        for (int i = 0; i < tests.size(); i++) {
            Test t = tests.get(i);
            if (!t.getDir().equals(prevDir)) {
                System.out.println("      " + t.getDir());
                prevDir = t.getDir();
            }
            System.out.format("[%s]  %02d %s%n", t.hasPassed() ? "PASS" : "FAIL", i + 1, t.getName());
        }
        System.out.println("Passed " + tests.stream().filter(Test::hasPassed).count() + "/" + tests.size() + " tests");
    }

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

    static void inp(Test t) {
        for (String line : t.getInput()) {
            System.out.println(line);
        }
    }

    static void exp(Test t) {
        for (String line : t.getResult()) {
            System.out.println(line);
        }
    }

    static void act(Test t) {
        for (String line : t.getActualOutput()) {
            System.out.println(line);
        }
    }

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