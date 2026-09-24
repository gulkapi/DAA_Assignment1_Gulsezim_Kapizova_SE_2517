import java.util.Random;

public class QuickSelect {
    public static int select(int[] a, int k) {
        return select(a, k, new Metrics());
    }

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k must be in range [0, " + (a.length - 1) + "] but was " + k);
        }
        Random rnd = new Random();
        int[] bounds = new int[2];
        int lo = 0;
        int hi = a.length - 1;
        int levels = 0;
        while (lo < hi) {
            m.enter();
            levels++;
            QuickSort.partition(a, lo, hi, bounds, rnd, m);
            if (k < bounds[0]) {
                hi = bounds[0] - 1;
            } else if (k > bounds[1]) {
                lo = bounds[1] + 1;
            } else {
                break;
            }
        }
        for (int i = 0; i < levels; i++) {
            m.exit();
        }
        return a[k];
    }
}