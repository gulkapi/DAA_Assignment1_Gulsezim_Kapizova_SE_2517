import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class QuickSelectTest {
    @Test
    void matchesSortedArrayOnRandomInputs() {
        Random rnd = new Random(4);
        for (int t = 0; t < 200; t++) {
            int n = 1 + rnd.nextInt(3000);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(2001) - 1000;
            }
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int k = rnd.nextInt(n);
            org.junit.jupiter.api.Assertions.assertEquals(sorted[k], QuickSelect.select(a.clone(), k));
        }
    }

    @Test
    void minimumAndMaximum() {
        Random rnd = new Random(5);
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt();
        }
        int[] sorted = a.clone();
        Arrays.sort(sorted);
        org.junit.jupiter.api.Assertions.assertEquals(sorted[0], QuickSelect.select(a.clone(), 0));
        org.junit.jupiter.api.Assertions.assertEquals(sorted[999], QuickSelect.select(a.clone(), 999));
    }

    @Test
    void singleElement() {
        org.junit.jupiter.api.Assertions.assertEquals(42, QuickSelect.select(new int[]{42}, 0));
    }

    @Test
    void allElementsEqual() {
        int[] a = new int[1000];
        Arrays.fill(a, 9);
        org.junit.jupiter.api.Assertions.assertEquals(9, QuickSelect.select(a, 500));
    }

    @Test
    void alreadySortedArray() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        org.junit.jupiter.api.Assertions.assertEquals(2500, QuickSelect.select(a, 2500));
    }

    @Test
    void emptyArrayThrows() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[0], 0));
    }

    @Test
    void negativeKThrows() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, -1));
    }

    @Test
    void kEqualToLengthThrows() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, 3));
    }
}