package strivers.binarysearch.bsonanswers;

import java.util.PriorityQueue;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * Minimise Maximum Distance between Gas Stations
 * Solved | Hard
 * * * PROBLEM STATEMENT:
 * You are given a sorted array ‘arr’ of length ‘n’, which contains positive
 * integer positions of ‘n’ gas stations on the X-axis. You are also given an
 * integer ‘k’. You have to place 'k' new gas stations on the X-axis. You can
 * place them anywhere on the non-negative side of the X-axis, even on
 * non-integer positions. Let 'dist' be the maximum value of the distance
 * between adjacent gas stations after adding k new gas stations. Find the
 * minimum value of ‘dist’.
 * * * EXAMPLES:
 * Example 1:
 * Input Format: N = 5, arr[] = {1,2,3,4,5}, k = 4
 * Result: 0.5
 * Explanation: One of the possible ways to place 4 gas stations is
 * {1,1.5,2,2.5,3,3.5,4,4.5,5}. Thus the maximum difference between adjacent
 * gas stations is 0.5. Hence, the value of ‘dist’ is 0.5. It can be shown
 * that there is no possible way to add 4 gas stations in such a way that the
 * value of ‘dist’ is lower than this.
 * * Example 2:
 * Input Format: N = 10, arr[] = {1,2,3,4,5,6,7,8,9,10}, k = 1
 * Result: 1.0
 * Explanation: One of the possible ways to place 1 gas station is
 * {1,1.5,2,3,4,5,6,7,8,9,10}. Thus the maximum difference between adjacent
 * gas stations is still 1. Hence, the value of ‘dist’ is 1.
 * * * CONSTRAINTS:
 * - 2 <= n <= 10^5
 * - 1 <= arr[i] <= 10^9
 * - 1 <= k <= 10^6
 * - Answers within 10^-6 of the actual answer will be accepted.
 * ============================================================================
 */

public class MinimiseMaxDistanceGasStations {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer Space)
     * ========================================================================
     * * APPROACH & STEPS:
     * This problem is a notorious cornerstone of the continuous "Binary Search
     * on Answer" pattern, heavily emphasized as a capstone in the Striver A2Z
     * revision path (which tends to structure these monotonic threshold problems
     * much more rigorously than other curriculums).
     * * 1. Define the Continuous Search Space:
     * - The absolute minimum possible maximum distance (`low`) is 0.0.
     * - The absolute maximum possible distance (`high`) is the largest existing
     * gap between two adjacent stations in the given array.
     * 2. Apply Binary Search on Decimals:
     * - Because the answer can be a floating-point number, our `while` loop
     * condition changes from `low <= high` to `(high - low) > 1e-6` to ensure
     * precision up to 6 decimal places.
     * 3. Greedily Check Feasibility:
     * - For a proposed maximum gap of `mid`, how many stations do we need to
     * insert in each original gap to ensure no subdivided gap exceeds `mid`?
     * - The required stations for a gap is `(gap / mid)`. If `gap` is perfectly
     * divisible by `mid`, we need one less station.
     * 4. Decision Logic:
     * - If `requiredStations <= k`, it means `mid` is a valid maximum gap. We
     * record it and aggressively try to minimize it further (`high = mid`).
     * - If `requiredStations > k`, `mid` is too small, forcing us to use more
     * stations than we have. We must increase the allowed gap (`low = mid`).
     * * * DETAILED INTUITION:
     * As the allowed maximum gap increases, the number of required new stations
     * strictly decreases. This monotonic relationship proves Binary Search is
     * optimal. By searching the continuous fractional space, we logarithmically
     * home in on the exact decimal answer without stepping through arbitrary
     * intervals.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log(Max_Gap / 10^-6)). Finding the initial max gap
     * takes O(N). The binary search loop runs a fixed number of times depending
     * on the precision required (roughly 50-60 iterations for 1e-6). Inside the
     * loop, we do an O(N) pass. Overall, it's highly efficient.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static double findSmallestMaxDistOptimal(int[] arr, int k) {
        int n = arr.length;
        double low = 0.0;
        double high = 0.0;

        // Find the maximum existing gap to establish the upper bound
        for (int i = 0; i < n - 1; i++) {
            high = Math.max(high, (double)(arr[i + 1] - arr[i]));
        }

        // Binary search for precision up to 6 decimal places
        double diff = 1e-6;
        while (high - low > diff) {
            double mid = low + (high - low) / 2.0;

            if (countRequiredStations(arr, mid) <= k) {
                // mid is feasible, but try to find an even smaller max distance
                high = mid;
            } else {
                // mid is too small, we don't have enough k to satisfy this gap
                low = mid;
            }
        }

        // high is minimized effectively up to our precision requirement
        return high;
    }

    // Helper method to simulate station placement based on a target max distance
    private static int countRequiredStations(int[] arr, double maxDistLimit) {
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            double gap = arr[i + 1] - arr[i];
            int stationsNeededInThisGap = (int) (gap / maxDistLimit);

            // Handle edge case where the gap is perfectly divisible by the limit
            if (gap == (stationsNeededInThisGap * maxDistLimit)) {
                stationsNeededInThisGap--;
            }

            count += stationsNeededInThisGap;
        }
        return count;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Create an array `howMany[]` of size N-1 to track the number of new
     * stations placed inside each original gap. Initially, it's all 0s.
     * 2. Loop `k` times (placing one station at a time).
     * 3. In each iteration, scan all original gaps to find the one with the
     * largest *current effective length*, calculated as `(arr[i+1] - arr[i]) / (howMany[i] + 1)`.
     * 4. Place a station in that maximum gap by incrementing `howMany[i]`.
     * 5. After `k` iterations, do one final scan to find the largest effective gap.
     * * * DETAILED INTUITION:
     * We greedily target the largest problem area. If a gap is massive, the
     * most logical move is to place our next available station squarely in its
     * middle to cut it in half.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(k * N). For each of the `k` stations, we scan the `N`
     * gaps. With `k = 10^6` and `N = 10^5`, this takes 10^11 operations and
     * will strictly result in Time Limit Exceeded (TLE).
     * - Space Complexity: O(N) to store the `howMany` array.
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(N) for the integer array allocation.
     */
    public static double findSmallestMaxDistBruteForce(int[] arr, int k) {
        int n = arr.length;
        int[] howMany = new int[n - 1];

        for (int station = 1; station <= k; station++) {
            double maxEffectiveGap = -1.0;
            int maxGapIndex = -1;

            for (int i = 0; i < n - 1; i++) {
                double currentEffectiveGap = (double)(arr[i + 1] - arr[i]) / (howMany[i] + 1);
                if (currentEffectiveGap > maxEffectiveGap) {
                    maxEffectiveGap = currentEffectiveGap;
                    maxGapIndex = i;
                }
            }
            // Place a station in the gap that was the largest
            howMany[maxGapIndex]++;
        }

        double maxAns = -1.0;
        for (int i = 0; i < n - 1; i++) {
            double finalGap = (double)(arr[i + 1] - arr[i]) / (howMany[i] + 1);
            maxAns = Math.max(maxAns, finalGap);
        }

        return maxAns;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Priority Queue / Max-Heap)
     * ========================================================================
     * * APPROACH & STEPS:
     * This intermediate approach bridges the intuitive gap between the Brute
     * Force and the Optimal Binary Search. Instead of linearly scanning to find
     * the maximum gap every time we place a station, we can maintain the gaps
     * in a Max-Heap.
     * 1. Create a custom class or double array to store the `effective_gap`,
     * the `original_gap`, and `stations_placed_here`.
     * 2. Insert all N-1 initial gaps into a Max-Heap ordered by `effective_gap`.
     * 3. Loop `k` times:
     * - Extract the maximum element from the heap.
     * - Increment its `stations_placed_here`.
     * - Recalculate its `effective_gap` as `original_gap / (stations + 1)`.
     * - Push it back into the heap.
     * 4. The answer is the `effective_gap` of the peak element in the heap.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log N + k * log N). Building the heap takes
     * O(N log N). Placing `k` stations takes O(k * log N). While significantly
     * faster than Brute Force, if `k` is 10^6 and `N` is 10^5, it still approaches
     * ~1.6 * 10^7 operations, which is slower than Binary Search and risks TLE
     * on tighter constraints.
     * - Space Complexity: O(N) for the Priority Queue.
     */
    public static double findSmallestMaxDistHeap(int[] arr, int k) {
        int n = arr.length;
        // Priority Queue stores arrays of [effective_gap, original_gap_index]
        // Since we need fast access to how many stations are in each gap
        int[] howMany = new int[n - 1];

        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));

        for (int i = 0; i < n - 1; i++) {
            pq.offer(new double[]{arr[i + 1] - arr[i], i});
        }

        for (int station = 1; station <= k; station++) {
            double[] maxGapInfo = pq.poll();
            int originalIndex = (int) maxGapInfo[1];

            howMany[originalIndex]++;

            double newEffectiveGap = (double)(arr[originalIndex + 1] - arr[originalIndex]) / (howMany[originalIndex] + 1);
            pq.offer(new double[]{newEffectiveGap, originalIndex});
        }

        return pq.peek()[0];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Validating implementations against examples and precision edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Minimise Maximum Distance Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] arr1 = {1, 2, 3, 4, 5};
        int k1 = 4;
        runTestCase(1, arr1, k1, 0.5);

        // Test Case 2: Standard case (Example 2)
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k2 = 1;
        runTestCase(2, arr2, k2, 1.0);

        // Test Case 3: Large initial gap requiring many stations
        int[] arr3 = {1, 100};
        int k3 = 9; // Creates 10 gaps. 99 / 10 = 9.9
        runTestCase(3, arr3, k3, 9.9);

        // Test Case 4: No new stations required to achieve minimal existing state
        // Even if k is large, we distribute them to minimize the maximum.
        int[] arr4 = {1, 2, 3};
        int k4 = 2; // Gaps are 1. Adding 2 stations means 1 in each gap -> max dist is 0.5
        runTestCase(4, arr4, k4, 0.5);

        // Test Case 5: Decimal Precision Edge Case
        int[] arr5 = {1, 5, 10};
        int k5 = 3;
        // Gaps: 4 and 5. k=3.
        // Optimal distribution: 1 station in gap 4 (size 2.0), 2 stations in gap 5 (size 1.666...). Max = 2.0.
        runTestCase(5, arr5, k5, 2.0);
    }

    private static void runTestCase(int testNumber, int[] arr, int k, double expected) {
        long startTime = System.nanoTime();
        double resultOptimal = findSmallestMaxDistOptimal(arr, k);
        long endTime = System.nanoTime();

        // Formatting output for clean precision tracking
        String formattedResult = String.format("%.6f", resultOptimal);
        String formattedExpected = String.format("%.6f", expected);

        // Checking precision validity up to 1e-6
        boolean isPass = Math.abs(resultOptimal - expected) <= 1e-6;

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: arr = " + java.util.Arrays.toString(arr) + ", k = " + k);
        System.out.println("Expected: " + formattedExpected);
        System.out.println("Output (Optimal): " + formattedResult);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (isPass ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}