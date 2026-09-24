# DAA Assignment 1: Divide and Conquer & Asymptotic Notations

Author: Gulsezim Kapizova, group SE2517
Course: Design and Analysis of Algorithms

This project is my solution for Assignment 1. It contains my own implementations of MergeSort, QuickSort and QuickSelect in Java, a small metrics system, a benchmark that writes results to a CSV file, and JUnit 5 tests. The analysis of the results (bounds, recurrences, plots and discussion) is in `REPORT.md`.

## What is implemented

**MergeSort**
- one helper array is allocated once in the top-level call and passed down the recursion
- subarrays with 15 elements or fewer are sorted with Insertion Sort
- merging two sorted halves takes O(n)

**QuickSort**
- random pivot, so sorted input does not become O(n^2)
- 3-way partition (less than, equal to, greater than the pivot), so many equal values stay fast
- recursion goes into the smaller part, the larger part is handled by a `while` loop, so the depth stays around log n and there is no stack overflow

**QuickSelect**
- `select(int[] a, int k)` returns the k-th smallest element (k starts from 0)
- uses the same `partition` method as QuickSort
- continues only in the part that contains position k
- throws `IllegalArgumentException` with a message if the array is empty or k is out of range

**Metrics**
- counts comparisons, maximum recursion depth and time (`System.nanoTime()`)
- a `Metrics` object is passed into every algorithm, there are no global variables

## Project structure

```
src/main/java
    Metrics.java
    MergeSort.java
    QuickSort.java
    QuickSelect.java
    Benchmark.java
    Analysis.java
src/test/java
    MergeSortTest.java
    QuickSortTest.java
    QuickSelectTest.java
pom.xml
results.csv
ratios.csv
plots/
REPORT.md
```

## Requirements

- JDK 25 (the version is set in `pom.xml`)
- Maven 3.9 or newer
- Git

## How to build

```
mvn compile
```

## How to run the tests

```
mvn test
```

The tests use JUnit 5 and check:
- MergeSort and QuickSort against `Arrays.sort` on 200 random arrays
- edge cases: empty array, one element, all elements equal, already sorted array
- QuickSort recursion depth on a sorted array of 100 000 elements (must be at most 2 * log2(n))
- QuickSort on all-equal input stays linear
- QuickSelect against `sorted[k]` on 200 random arrays
- QuickSelect throws `IllegalArgumentException` for an empty array and for k out of range

## How to run the benchmark

One command runs the benchmark and creates `results.csv` in the project root:

```
mvn compile exec:java
```

If you want cleaner timings (without the Maven JVM around the code), you can also run:

```
mvn compile
java -cp target/classes Benchmark
```

The benchmark runs all three algorithms on sizes n = 1 000, 10 000, 100 000 and 1 000 000 and on three input types:
- `random`: random integers
- `sorted`: already sorted array
- `duplicates`: random values from 0 to 9

Every case is run 5 times and the median time is saved. The input data is generated with a fixed seed, so all algorithms get the same arrays. For QuickSelect I search for the median (k = n / 2).

`results.csv` has these columns:

```
algorithm,input,n,time_ms,comparisons,max_depth
```

## How to run the analysis

```
mvn compile exec:java -Dexec.mainClass=Analysis
```

This reads `results.csv`, writes `ratios.csv` and prints rough values of n0, c1 and c2 for the Theta check. The ratio is `comparisons / (n * log2(n))` for the sorts and `comparisons / n` for QuickSelect.

## Plots

The plots are saved as PNG files in the `plots/` folder:
- time vs n
- max recursion depth vs n
- ratio vs n

They are built from `results.csv` and `ratios.csv`.

## Report

`REPORT.md` contains the table of asymptotic bounds, the recurrences with Master Theorem cases, the plots, the Theta check and the discussion.

## Notes about the implementation

- In MergeSort I do not skip the merge when the two halves are already in order, so the algorithm stays Theta(n log n) on every input.
- Comparisons are counted in Insertion Sort and in merge for MergeSort, and once per element in each partition step for QuickSort and QuickSelect.
- QuickSelect is written as a loop, not as recursion. For the depth metric I count every partition level as one step, so the depth plot is comparable with the recursive algorithms.
- The `partition` result (the bounds of the "equal to pivot" part) is returned through a small array that is created only once per call, so the recursion does not allocate new arrays.

## Git workflow

- `main`: only working code, release tag `v1.0`
- `feature/metrics`: Metrics class, benchmark and analysis
- `feature/mergesort`: MergeSort and its tests
- `feature/quicksort`: QuickSort and its tests
- `feature/select`: QuickSelect and its tests

Commit messages follow the style `feat(mergesort): ...`, `test(quicksort): ...`, `docs(report): ...`.