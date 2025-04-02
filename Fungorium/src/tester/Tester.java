package tester;

import java.io.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class Tester {
    private static List<Test> tests;
    private static int passed = 0;

    public static void main(String[] args) {
        build();
        readTests();
        System.out.println("Running tests...");
        tests.forEach(Test::run);
        System.out.println("Tests finished!");
        sum();
        interact();
    }

    // TODO: remove this method
    // ONLY FOR DEVELOPMENT
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
                String name = folder.getName() + File.separator
                        + file.getName().substring(0, file.getName().length() - 4);
                try {
                    tests.add(new Test(name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void interact() {
        Scanner scanner = new Scanner(System.in);
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
                case "ls" -> ls();
                case "sum" -> sum();
                case "help" -> help();
                case "diff" -> notEnoughArgs();
                case "inp" -> notEnoughArgs();
                case "exp" -> notEnoughArgs();
                case "act" -> notEnoughArgs();
                case "exit" -> System.out.println("Exiting...");
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
                case "exit" -> System.out.println("Exiting...");
                default -> System.out.println("Unknown command: " + command);
                }
                break;
            default:
                System.out.println("Invalid command");
                break;
            }

        } while (!command.equals("exit"));
        scanner.close();

    }

    static void notEnoughArgs() {
        System.out.println("No test number provided");
    }

    // #region COMMANDS
    static void sum() {
        for (Test t : tests) {
            if (t.hasPassed()) {
                System.out.println("[PASS] " + t.getName());
                passed++;
            } else {
                System.out.println("[FAIL] " + t.getName());
            }
        }
        System.out.println("Passed " + passed + "/" + tests.size() + " tests");
    }

    static void ls() {
        for (int i = 0; i < tests.size(); i++) {
            System.out.println((i + 1) + ". " + tests.get(i).getName());
        }
    }

    static void help() {
        System.out.println("  ls               - List test numbers");
        System.out.println("  sum              - Summary");
        System.out.println("  diff <test num>  - First difference between expected and actual output");
        System.out.println("  inp <test num>   - Input");
        System.out.println("  exp <test num>   - Expected output");
        System.out.println("  act <test num>   - Actual output");
        System.out.println("  save <test num>  - Save actual output to .resa file");
        System.out.println("  exit             - Exit");
    }

    static void diff(Test t) {
        int idx = t.getDiffLine();
        if (idx == -1) {
            System.out.println("The actual output is the same as the expected output");
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
        File outputFile = new File(Test.TESTS_DIR + File.separator + t.getName() + ".resa");
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