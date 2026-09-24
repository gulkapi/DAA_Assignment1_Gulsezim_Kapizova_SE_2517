import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Analysis {
    private static String f(double v) {
        return String.format(java.util.Locale.US, "%.4f", v);
    }

    public static void main(String[] args) {
        String inPath = args.length > 0 ? args[0] : "results.csv";
        String outPath = args.length > 1 ? args[1] : "ratios.csv";
        String[] alg = new String[1000];
        String[] inp = new String[1000];
        long[] n = new long[1000];
        double[] ratio = new double[1000];
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(inPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] p = line.split(",");
                alg[count] = p[0];
                inp[count] = p[1];
                n[count] = Long.parseLong(p[2]);
                double comparisons = Double.parseDouble(p[4]);
                if (p[0].equals("QuickSelect")) {
                    ratio[count] = comparisons / n[count];
                } else {
                    ratio[count] = comparisons / (n[count] * (Math.log(n[count]) / Math.log(2)));
                }
                count++;
            }
        } catch (IOException e) {
            System.err.println("Cannot read " + inPath + ": " + e.getMessage());
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(outPath))) {
            pw.println("algorithm,input,n,ratio");
            for (int i = 0; i < count; i++) {
                pw.println(alg[i] + "," + inp[i] + "," + n[i] + "," + f(ratio[i]));
            }
        } catch (IOException e) {
            System.err.println("Cannot write " + outPath + ": " + e.getMessage());
            return;
        }

        int start = 0;
        while (start < count) {
            int end = start;
            while (end + 1 < count && alg[end + 1].equals(alg[start]) && inp[end + 1].equals(inp[start])) {
                end++;
            }
            double last = ratio[end];
            int from = end;
            while (from > start && Math.abs(ratio[from - 1] - last) <= 0.25 * last) {
                from--;
            }
            double c1 = ratio[from];
            double c2 = ratio[from];
            for (int i = from; i <= end; i++) {
                if (ratio[i] < c1) {
                    c1 = ratio[i];
                }
                if (ratio[i] > c2) {
                    c2 = ratio[i];
                }
            }
            System.out.println(alg[start] + " / " + inp[start] + ": n0=" + n[from] + ", c1=" + f(c1) + ", c2=" + f(c2));
            start = end + 1;
        }
    }
}