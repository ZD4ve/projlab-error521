package proto;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomProvider {
    private static Queue<Integer> nexts = new LinkedList<>();
    private static Random R = new Random();

    public static int NextRand() {
        if (!nexts.isEmpty()) {
            return nexts.poll();
        }
        return R.nextInt();
    }

    public static void addNext(int next) {
        nexts.add(next);
    }
}
