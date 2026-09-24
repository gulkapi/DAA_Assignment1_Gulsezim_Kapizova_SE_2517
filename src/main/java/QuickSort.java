import java.util.Random;

public class QuickSort {
    public static void sort(int[] a) {
        sort(a, new Metrics());
    }

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1, new int[2], new Random(), m);
    }

    private static void sort(int[] a, int lo, int hi, int[] bounds, Random rnd, Metrics m) {
        m.enter();
        while (lo < hi) {
            partition(a, lo, hi, bounds, rnd, m);
            int lt = bounds[0];
            int gt = bounds[1];
            if (lt - lo < hi - gt) {
                sort(a, lo, lt - 1, bounds, rnd, m);
                lo = gt + 1;
            } else {
                sort(a, gt + 1, hi, bounds, rnd, m);
                hi = lt - 1;
            }
        }
        m.exit();
    }

    static void partition(int[] a, int lo, int hi, int[] bounds, Random rnd, Metrics m) {
        int pivot = a[lo + rnd.nextInt(hi - lo + 1)];
        int lt = lo;
        int i = lo;
        int gt = hi;
        while (i <= gt) {
            m.addComparison();
            int c = Integer.compare(a[i], pivot);
            if (c < 0) {
                swap(a, lt, i);
                lt++;
                i++;
            } else if (c > 0) {
                swap(a, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        bounds[0] = lt;
        bounds[1] = gt;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}