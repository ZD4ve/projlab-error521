package tester;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Egy konkrét tesztet reprezentáló osztály.
 */
public class Test {
    /** Teszt fájlok könyvtára */
    public static final String TESTS_DIR = "tests";

    /** Tesztcsoport neve */
    private String dir;
    /** Teszt neve */
    private String name;
    /** Teszt térkép bemenete */
    private List<String> map;
    /** Teszt műveletek bemenete */
    private List<String> operation;
    /** Teszt elvárt kimenete */
    private List<String> result;
    /** Teszt tényleges kimenete */
    private List<String> actualOutput = new ArrayList<>();
    /** Teszt sikeressége */
    private boolean passed = false;
    /** Első eltérő sor sorszáma */
    private int diffLine = -1;

    /**
     * Konstruktor, amely beolvassa a teszt fájlokat.
     *
     * @param dir  a teszt könyvtár neve
     * @param name a teszt fájl neve
     * @throws IOException ha a fájlok beolvasása nem sikerül
     */
    public Test(String dir, String name) throws IOException {
        this.dir = dir;
        this.name = name;
        map = Files.readAllLines(new File(TESTS_DIR + File.separator + getPath() + ".map").toPath());
        operation = Files.readAllLines(new File(TESTS_DIR + File.separator + getPath() + ".op").toPath());
        result = Files.readAllLines(new File(TESTS_DIR + File.separator + getPath() + ".res").toPath());

        result = new ArrayList<>(result.stream().map(String::stripTrailing).toList());
        for (int i = result.size() - 1; i >= 0; i--) {
            if (!result.get(i).isEmpty()) {
                break;
            }
            result.remove(i);
        }
    }

    /**
     * Összefűzi a bemeneti térképet és a műveleteket.
     * 
     * @return a teszthez tartozó bemenet.
     */
    public List<String> getInput() {
        List<String> input = new ArrayList<>();
        input.addAll(map);
        input.add("");
        input.add("end");
        input.addAll(operation);
        input.add("exit");
        return input;
    }

    /**
     * Elindít egy új folyamatot, amely a Fungorium programot futtatja. Bemenetként a teszt bemeneti térképét és
     * műveleteit adja meg. Vár, amíg a folyamat befejeződik, majd ellenőrzi a kimenetet. Soronként összehasonlítja a
     * várt és a tényleges kimenetet. Ha eltérés van, akkor a diffLine változóban tárolja az eltérő sor sorszámát.
     * 
     * @return true, ha a teszt sikeres, false, ha nem.
     */
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

    // #region GETTERS

    /** Visszaadja az első eltérő sor sorszámát. */
    public int getDiffLine() {
        return diffLine;
    }

    /** Visszaadja az aktuális kimenetet. */
    public List<String> getActualOutput() {
        return actualOutput;
    }

    /** Visszaadja a teszt csoportnevét. */
    public String getDir() {
        return dir;
    }

    /** Visszaadja a teszt nevét. */
    public String getName() {
        return name;
    }

    /** Visszaadja a teszt fájlok teljes elérési útját. */
    public String getPath() {
        return dir + File.separator + name;
    }

    /** Visszaadja a várt kimenetet */
    public List<String> getResult() {
        return result;
    }

    /** Visszaadja a teszt sikerességét. */
    public boolean hasPassed() {
        return passed;
    }

    // #endregion
}
