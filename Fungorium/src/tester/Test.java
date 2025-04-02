package tester;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@SuppressWarnings("java:S1104")
public class Test {
    private String name;
    private List<String> map;
    private List<String> operation;
    private List<String> result;

    private boolean passed = false;
    private int diffLine = -1;
    private List<String> actualOutput = new ArrayList<>();

    public static final String TESTS_DIR = "tests";

    public Test(String name) throws IOException {
        this.name = name;
        map = Files.readAllLines(new File(TESTS_DIR + File.separator + name + ".map").toPath());
        operation = Files.readAllLines(new File(TESTS_DIR + File.separator + name + ".op").toPath());
        result = Files.readAllLines(new File(TESTS_DIR + File.separator + name + ".res").toPath());
    }

    public int getDiffLine() {
        return diffLine;
    }

    public List<String> getActualOutput() {
        return actualOutput;
    }

    public String getName() {
        return name;
    }

    public List<String> getInput() {
        List<String> input = new ArrayList<>();
        input.addAll(map);
        input.add("");
        input.add("end");
        input.addAll(operation);
        input.add("exit");
        return input;
    }

    public List<String> getResult() {
        return result;
    }

    public boolean hasPassed() {
        return passed;
    }

    public boolean run() {
        actualOutput = new ArrayList<>();
        try {
            ProcessBuilder builder = new ProcessBuilder("java", "-jar", "Fungorium.jar");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
            getInput().forEach(writer::println);
            writer.flush();
            writer.close();
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                actualOutput.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false;
        }
        for (int i = 0; i < result.size(); i++) {
            if (i >= actualOutput.size()) {
                diffLine = i;
                return false;
            }
            if (!result.get(i).equals(actualOutput.get(i))) {
                diffLine = i;
                return false;
            }
        }
        return true;
    }
}
