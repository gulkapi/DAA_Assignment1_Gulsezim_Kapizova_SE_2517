public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] a) {
        sort(a, new Metrics());
    }

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) {
            return;
        }
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] buffer, int lo, int hi, Metrics m) {
        m.enter();
        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi, m);
        } else {
            int mid = lo + (hi - lo) / 2;
            sort(a, buffer, lo, mid, m);
            sort(a, buffer, mid + 1, hi, m);
            merge(a, buffer, lo, mid, hi, m);
        }
        m.exit();
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.addComparison();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    private static void merge(int[] a, int[] buffer, int lo, int mid, int hi, Metrics m) {
        System.arraycopy(a, lo, buffer, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            m.addComparison();
            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }
        while (i <= mid) {
            a[k++] = buffer[i++];
        }
        while (j <= hi) {
            a[k++] = buffer[j++];
        }
    }
}