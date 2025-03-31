package controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomProvider {
    private static Queue<Double> nextRandoms = new LinkedList<>();
    private static Random r = new Random();

    public static double nextRand() {
        if (!nextRandoms.isEmpty()) {
            return nextRandoms.poll();
        }
        return r.nextDouble();
    }

    public static void addNext(double next) {
        nextRandoms.add(next);
    }

    private RandomProvider() {
    }
}
