import java.util.Random;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Benchmark {
    private static final String[] ALGORITHMS = {"MergeSort", "QuickSort", "QuickSelect"};
    private static final String[] INPUTS = {"random", "sorted", "duplicates"};
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "results.csv";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("algorithm,input,n,time_ms,comparisons,max_depth");
            for (String algorithm : ALGORITHMS) {
                for (String input : INPUTS) {
                    for (int n : SIZES) {
                        int[] data = generate(input, n);
                        Metrics[] runs = new Metrics[RUNS];
                        for (int r = 0; r < RUNS; r++) {
                            runs[r] = run(algorithm, data);
                        }
                        Arrays.sort(runs, (x, y) -> Long.compare(x.getElapsedNanos(), y.getElapsedNanos()));
                        Metrics median = runs[RUNS / 2];
                        String line = algorithm + "," + input + "," + n + ","
                                + String.format(java.util.Locale.US, "%.4f", median.getElapsedMillis()) + ","
                                + median.getComparisons() + "," + median.getMaxDepth();
                        out.println(line);
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot write " + path + ": " + e.getMessage());
        }
    }

    private static Metrics run(String algorithm, int[] data) {
        int[] a = data.clone();
        Metrics m = new Metrics();
        m.start();
        switch (algorithm) {
            case "MergeSort":
                MergeSort.sort(a, m);
                break;
            case "QuickSort":
                QuickSort.sort(a, m);
                break;
            default:
                QuickSelect.select(a, a.length / 2, m);
                break;
        }
        m.stop();
        return m;
    }

    private static int[] generate(String input, int n) {
        Random rnd = new Random(12345L + n);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = input.equals("duplicates") ? rnd.nextInt(10) : rnd.nextInt();
        }
        if (input.equals("sorted")) {
            Arrays.sort(a);
        }
        return a;
    }
}