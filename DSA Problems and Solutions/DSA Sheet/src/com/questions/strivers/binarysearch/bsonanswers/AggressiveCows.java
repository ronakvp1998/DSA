package com.questions.strivers.binarysearch.bsonanswers;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * Aggressive Cows : Detailed Solution
 * Solved | Medium / Hard
 * * * PROBLEM STATEMENT:
 * You are given an array 'arr' of size 'n' which denotes the position of stalls.
 * You are also given an integer 'k' which denotes the number of aggressive cows.
 * You are given the task of assigning stalls to 'k' cows such that the minimum
 * distance between any two of them is the maximum possible. Find the maximum
 * possible minimum distance.
 * * * EXAMPLES:
 * Example 1:
 * Input Format: N = 6, k = 4, arr[] = {0,3,4,7,10,9}
 * Result: 3
 * Explanation: The maximum possible minimum distance between any two cows will
 * be 3 when 4 cows are placed at positions {0, 3, 7, 10}. Here the distances
 * between cows are 3, 4, and 3 respectively. We cannot make the minimum
 * distance greater than 3 in any ways.
 * * Example 2:
 * Input Format: N = 5, k = 2, arr[] = {4,2,1,3,6}
 * Result: 5
 * Explanation: The maximum possible minimum distance between any two cows will
 * be 5 when 2 cows are placed at positions {1, 6}.
 * * * CONSTRAINTS (Standard Competitive Programming Assumptions):
 * - 2 <= k <= N <= 10^5
 * - 0 <= arr[i] <= 10^9
 * ============================================================================
 */

public class AggressiveCows {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer)
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Sort the Array: To linearly simulate placing cows from left to right,
     * the stalls must be in strictly increasing order of their positions.
     * 2. Define Search Space:
     * - Minimum possible distance (`low`) is 1 (cows can be in adjacent stalls).
     * - Maximum possible distance (`high`) is `arr[N-1] - arr[0]` (placing 2
     * cows at the extreme ends).
     * 3. Apply Binary Search: Calculate `mid` as a candidate minimum distance.
     * 4. Greedily Check Feasibility: Use a helper method to place cows. Start
     * the first cow at `arr[0]`. Iterate through the stalls. If the distance
     * between the current stall and the last placed cow is >= `mid`, place a
     * cow here.
     * 5. Decision Logic:
     * - If we can successfully place all `k` cows with at least `mid` distance,
     * then `mid` is a valid answer. However, we want the *maximum* possible
     * distance, so we save `mid` and search the right half (`low = mid + 1`).
     * - If we cannot place `k` cows, the distance `mid` is too large. We must
     * shrink our search space to the left half (`high = mid - 1`).
     * * * DETAILED INTUITION:
     * This is the quintessential "Maximin" (Maximize the Minimum) problem pattern
     * heavily featured in rigorous revision resources. We transition to Binary
     * Search because the answer space is monotonic: if placing cows with a
     * minimum distance of `X` is possible, then `X-1`, `X-2` are inherently
     * possible. If `X` is impossible, `X+1` is definitely impossible. This allows
     * us to discard half the search space continuously.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N log N + N log M), where N is the number of stalls
     * and M is the difference between the maximum and minimum stall positions
     * (`arr[N-1] - arr[0]`). Sorting takes O(N log N). The binary search runs
     * log(M) times, and the feasibility check inside it takes O(N).
     * - Space Complexity: O(1) auxiliary (ignoring the O(log N) space used
     * by Java's Dual-Pivot Quicksort implementation under the hood of Arrays.sort).
     * - Auxiliary Stack Space: O(1) (Iterative binary search).
     * - Heap Space: O(1) (No dynamic structures instantiated).
     */
    public static int aggressiveCowsOptimal(int[] arr, int k) {
        if (arr == null || arr.length < k) return -1;

        Arrays.sort(arr);
        int n = arr.length;

        int low = 1;
        int high = arr[n - 1] - arr[0];
        int optimalDistance = 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canPlaceCows(arr, k, mid)) {
                optimalDistance = mid; // Valid distance, but look for a larger one
                low = mid + 1;
            } else {
                high = mid - 1; // Distance too large, cows don't fit
            }
        }

        return optimalDistance;
    }

    // Helper method to greedily simulate placing k cows with at least 'minDist' between them
//    We always assign the 1st slot because it guarantees the absolute maximum leftover space for the remaining cows.
//    If a specific distance cannot be achieved by starting at arr[0],
//    it is an absolute mathematical certainty that it cannot be achieved by starting anywhere else.
    private static boolean canPlaceCows(int[] arr, int k, int minDist) {
        int cowsPlaced = 1;          // Place the first cow...
        int lastPlacedPosition = arr[0]; // ...at the first stall

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - lastPlacedPosition >= minDist) {
                cowsPlaced++;
                lastPlacedPosition = arr[i];

                if (cowsPlaced == k) {
                    return true; // Successfully placed all cows
                }
            }
        }
        return false;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Sort the array of stalls.
     * 2. Identify the search boundaries: 1 to `arr[n-1] - arr[0]`.
     * 3. Run a linear loop starting with distance `d = 1`.
     * 4. For each distance `d`, use the greedy placement logic.
     * 5. The moment a distance `d` fails (we cannot place `k` cows), we know
     * the maximum possible distance was the *previous* one (`d - 1`).
     * * * DETAILED INTUITION:
     * We simulate the absolute basic thought process. "Can I place them 1 unit
     * apart? Yes. 2 units? Yes. 3 units? Yes. 4 units? No." If 4 fails, 3 is
     * our answer. It logically proves the condition but fails under strict
     * time limits.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N log N + M * N), where M is the max possible distance
     * (`arr[N-1] - arr[0]`). If stall coordinates are up to 10^9, this loop
     * will strictly result in Time Limit Exceeded (TLE).
     * - Space Complexity: O(1) auxiliary (ignoring sort space).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int aggressiveCowsBruteForce(int[] arr, int k) {
        if (arr == null || arr.length < k) return -1;

        Arrays.sort(arr);
        int n = arr.length;
        int limit = arr[n - 1] - arr[0];

        // Linearly scan the answer space
        for (int d = 1; d <= limit; d++) {
            if (!canPlaceCows(arr, k, d)) {
                return d - 1; // Return the last successful distance
            }
        }

        return limit;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Why purely Greedy fails without Binary Search:
     * You might be tempted to just place cows by jumping exactly `(max-min)/k`
     * stalls. However, stalls are not uniformly distributed. A purely greedy
     * placement without testing a strict numerical distance threshold will
     * inevitably group cows too closely when stalls are clustered, completely
     * ruining the global minimum distance.
     * * Binary Search on Answer combined with a Greedy checker is the singular,
     * mathematically optimal paradigm for this problem.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Validating implementations against examples and critical edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Aggressive Cows Test Suite...\n");

        // Test Case 1: Standard case (Example 1) - Note: Input is unsorted
        int[] arr1 = {0, 3, 4, 7, 10, 9};
        int k1 = 4;
        runTestCase(1, arr1, k1, 3);

        // Test Case 2: Standard case (Example 2) - Note: Input is unsorted
        int[] arr2 = {4, 2, 1, 3, 6};
        int k2 = 2;
        runTestCase(2, arr2, k2, 5);

        // Test Case 3: Edge Case - Exactly 2 cows
        // Answer should be the distance between the first and last stall after sorting.
        int[] arr3 = {1, 2, 8, 4, 9};
        int k3 = 2;
        runTestCase(3, arr3, k3, 8); // Sorted: 1, 2, 4, 8, 9. 9-1 = 8.

        // Test Case 4: Edge Case - k equals N (Cows = Stalls)
        // Must place a cow in every single stall. Answer is the min adjacent difference.
        int[] arr4 = {2, 1, 8, 4, 9}; // Sorted: 1, 2, 4, 8, 9
        int k4 = 5;
        // Differences: (2-1=1), (4-2=2), (8-4=4), (9-8=1). Min difference is 1.
        runTestCase(4, arr4, k4, 1);

        // Test Case 5: Large Gap Case
        int[] arr5 = {1, 1000000000};
        int k5 = 2;
        runTestCase(5, arr5, k5, 999999999);
    }

    private static void runTestCase(int testNumber, int[] arr, int k, int expected) {
        // Clone array to prevent Phase 1 sort from bypassing Phase 2 sort cost in benchmarking
        int[] arrForOptimal = arr.clone();

        long startTime = System.nanoTime();
        int resultOptimal = aggressiveCowsOptimal(arrForOptimal, k);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: arr = " + Arrays.toString(arr) + ", k = " + k);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}