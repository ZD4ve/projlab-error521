package controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/** Kontrollálható módon állít elő random számokat. */
public class RandomProvider {
    /** A kézzel megadott "random" számokat tárolja. */
    private static Queue<Double> nextRandoms = new LinkedList<>();
    /** Szükség esetén random számokat generál. */
    private static Random r = new Random();

    /** Random - vagy ha felülírtuk, nem random - számot ad vissza 0 és 1 között. */
    public static double nextRand() {
        if (!nextRandoms.isEmpty()) {
            return nextRandoms.poll();
        }
        return r.nextDouble();
    }

    public static int nextInt(int maxExcl) {
        return r.nextInt(maxExcl);
    }

    /** Felülírja a következő lekért szám értékét. FIFO jelleggel működik, tehát több szám is sorba állítható. */
    public static void addNext(double next) {
        nextRandoms.add(next);
    }

    /** Az osztály nem példányosítható */
    private RandomProvider() {
    }
}
