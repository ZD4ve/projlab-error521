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

        result = new ArrayList<>(result.stream().map(String::stripTrailing).toList());
        for (int i = result.size() - 1; i >= 0; i--) {
            if (!result.get(i).isEmpty()) {
                break;
            }
            result.remove(i);
        }
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                actualOutput.add(line.stripTrailing());
            }
            process.waitFor();
            writer.close();
            reader.close();
            for (int i = actualOutput.size() - 1; i >= 0; i--) {
                if (!actualOutput.get(i).isEmpty()) {
                    break;
                }
                actualOutput.remove(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false;
        }
        for (int i = 0; i < Math.max(result.size(), actualOutput.size()); i++) {
            if (i >= Math.min(result.size(), actualOutput.size())) {
                diffLine = i;
                return false;
            }
            if (!result.get(i).trim().equals(actualOutput.get(i).trim())) {
                diffLine = i;
                return false;
            }
        }
        passed = true;
        return true;
    }
}
