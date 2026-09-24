public class Metrics {
    private long comparisons;
    private int depth;
    private int maxDepth;
    private long startNanos;
    private long elapsedNanos;

    public void reset() {
        comparisons = 0;
        depth = 0;
        maxDepth = 0;
        startNanos = 0;
        elapsedNanos = 0;
    }

    public void addComparison() {
        comparisons++;
    }

    public void enter() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void exit() {
        depth--;
    }

    public void start() {
        startNanos = System.nanoTime();
    }

    public void stop() {
        elapsedNanos = System.nanoTime() - startNanos;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    public double getElapsedMillis() {
        return elapsedNanos / 1_000_000.0;
    }
}