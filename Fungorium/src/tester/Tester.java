package tester;

import java.io.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class Tester {
    private static List<Test> tests;
    private static int passed = 0;

    public static void main(String[] args) {
        readTests();
        tests.forEach(t -> {
            try {
                if (runTest(t)) {
                    System.out.println("Test " + t.getName() + " passed");
                    passed++;
                } else {
                    System.out.println("Test " + t.getName() + " failed");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Test " + t.getName() + " failed");
                e.printStackTrace();
            }
        });
        System.out.println("Passed " + passed + "/" + tests.size() + " tests");
    }

    private static void readTests() {
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

    private static boolean runTest(Test t) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("java -jar Fungorium.jar");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
        t.getInput().forEach(writer::println);
        writer.flush();
        writer.close();
        process.waitFor();
        List<String> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        reader.close();
        if (result.size() != t.getResult().size()) {
            return false;
        }
        for (int i = 0; i < result.size(); i++) {
            if (!result.get(i).equals(t.getResult().get(i))) {
                return false;
            }
        }
        return true;
    }

}