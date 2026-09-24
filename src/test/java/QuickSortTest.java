import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {
    private static int[] randomArray(Random rnd, int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(2001) - 1000;
        }
        return a;
    }

    @Test
    void matchesArraysSortOnRandomArrays() {
        Random rnd = new Random(3);
        for (int t = 0; t < 200; t++) {
            int[] a = randomArray(rnd, rnd.nextInt(3000));
            int[] expected = a.clone();
            Arrays.sort(expected);
            QuickSort.sort(a, new Metrics());
            org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
        }
    }

    @Test
    void emptyArray() {
        int[] a = new int[0];
        QuickSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertEquals(0, a.length);
    }

    @Test
    void singleElement() {
        int[] a = {42};
        QuickSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void allElementsEqual() {
        int[] a = new int[1000];
        Arrays.fill(a, 7);
        int[] expected = a.clone();
        QuickSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
    }

    @Test
    void alreadySortedArray() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        int[] expected = a.clone();
        QuickSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
    }

    @Test
    void depthOnSortedArrayIsLogarithmic() {
        int n = 100000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        double limit = 2 * (Math.log(n) / Math.log(2));
        org.junit.jupiter.api.Assertions.assertTrue(m.getMaxDepth() <= limit,
                "maxDepth " + m.getMaxDepth() + " exceeds " + limit);
    }

    @Test
    void allEqualElementsStayLinear() {
        int n = 200000;
        int[] a = new int[n];
        Arrays.fill(a, 5);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        org.junit.jupiter.api.Assertions.assertTrue(m.getComparisons() <= 2L * n,
                "comparisons " + m.getComparisons());
    }
}