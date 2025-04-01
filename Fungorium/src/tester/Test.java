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

    public static final String TESTS_DIR = "tests";

    public Test(String name) throws IOException {
        this.name = name;
        map = Files.readAllLines(new File(TESTS_DIR + File.separator + name + ".map").toPath());
        operation = Files.readAllLines(new File(TESTS_DIR + File.separator + name + ".op").toPath());
        result = Files.readAllLines(new File(TESTS_DIR + File.separator + name + ".res").toPath());
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
}
