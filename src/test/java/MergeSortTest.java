import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {
    private static int[] randomArray(Random rnd, int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(2001) - 1000;
        }
        return a;
    }

    @Test
    void matchesArraysSortOnRandomArrays() {
        Random rnd = new Random(1);
        for (int t = 0; t < 200; t++) {
            int[] a = randomArray(rnd, rnd.nextInt(3000));
            int[] expected = a.clone();
            Arrays.sort(expected);
            MergeSort.sort(a, new Metrics());
            org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
        }
    }

    @Test
    void handlesSizesAroundCutoff() {
        Random rnd = new Random(2);
        for (int n = 0; n <= 40; n++) {
            int[] a = randomArray(rnd, n);
            int[] expected = a.clone();
            Arrays.sort(expected);
            MergeSort.sort(a, new Metrics());
            org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
        }
    }

    @Test
    void emptyArray() {
        int[] a = new int[0];
        MergeSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertEquals(0, a.length);
    }

    @Test
    void singleElement() {
        int[] a = {42};
        MergeSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void allElementsEqual() {
        int[] a = new int[1000];
        Arrays.fill(a, 7);
        int[] expected = a.clone();
        MergeSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
    }

    @Test
    void alreadySortedArray() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        int[] expected = a.clone();
        MergeSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
    }

    @Test
    void reverseSortedArray() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        int[] expected = a.clone();
        Arrays.sort(expected);
        MergeSort.sort(a, new Metrics());
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, a);
    }
}