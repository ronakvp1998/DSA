package strivers.slidingwind2pointer.length;

/**
 * ============================================================================
 * DSA MASTERCLASS: Minimum Size Subarray Sum (Shortest Valid Window)
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an array of positive integers `nums` and a positive integer `target`,
 * return the minimal length of a contiguous subarray whose sum is greater than
 * or equal to `target`. If there is no such subarray, return 0 instead.
 *
 * Constraints:
 * - 1 <= target <= 10^9
 * - 1 <= nums.length <= 10^5
 * - 1 <= nums[i] <= 10^4
 * (Note: The constraint that nums[i] is positive is crucial. If negatives were
 * allowed, a simple sliding window would fail because expanding the window
 * wouldn't guarantee an increased sum).
 *
 * Input Format:
 * - `nums`: An array of positive integers.
 * - `target`: An integer representing the required minimum sum.
 *
 * Output Format:
 * - An integer representing the length of the shortest valid window.
 *
 * Examples:
 * ---------
 * Example 1:
 * Input: target = 7, nums = {2, 3, 1, 2, 4, 3}
 * Output: 2
 * Explanation: The subarray {4, 3} has the minimal length under the problem constraint.
 *
 * Example 2:
 * Input: target = 4, nums = {1, 4, 4}
 * Output: 1
 * Explanation: The subarray {4} inherently satisfies the condition (4 >= 4).
 *
 * Example 3 (Edge Case - Impossible Target):
 * Input: target = 11, nums = {1, 1, 1, 1, 1, 1, 1, 1}
 * Output: 0
 * Explanation: The total sum is 8, which is less than 11. No valid window exists.
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class ShortestValidWindow {

    /**
     * PHASE 1: Optimal Approach - Dynamic Sliding Window (Two Pointers)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * We need the *shortest* window. We can maintain a window `[l, r]` and expand it
     * by moving `r` to the right, adding `nums[r]` to our running sum.
     * As soon as our window's sum becomes `>= target`, we have found a valid window.
     * But is it the shortest? To find out, we attempt to shrink the window from the
     * left by moving `l` forward. We continue shrinking as long as the sum remains
     * valid (`>= target`), updating our `minLen` at each step. Once the sum drops
     * below the target, we resume expanding `r`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Even though there is a `while` loop inside the `for` loop, each pointer
     *   (`l` and `r`) moves strictly forward. Each element is visited at most twice
     *   (once when added by `r`, once when removed by `l`). Thus, 2N operations = O(N).
     * - Space Complexity: O(1) Auxiliary Space
     *   We only use a few integer variables (`l`, `sum`, `minLen`) for state tracking.
     *   No extra heap space is allocated.
     */
    public static int optimalShortestWindow(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int minLen = Integer.MAX_VALUE;
        int l = 0;
        int currentSum = 0;

        for (int r = 0; r < n; r++) {
            currentSum += nums[r]; // Expand window

            // Shrink window from the left as much as possible while remaining valid
            while (currentSum >= target) {
                minLen = Math.min(minLen, r - l + 1); // Record valid window length
                currentSum -= nums[l];                // Remove leftmost element
                l++;                                  // Shrink
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * PHASE 2: Brute Force Approach - Generate All Subarrays
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * The most rudimentary way to solve this is to look at every possible contiguous
     * subarray. For every starting index `i`, we calculate the sum of subarrays ending
     * at `j`. As soon as the sum hits or exceeds the `target`, we record the length
     * and break out of the inner loop (since continuing would only find longer valid
     * subarrays starting at `i`, and we want the shortest).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2)
     *   In the worst case (e.g., when the valid window spans the entire array or
     *   doesn't exist), the outer loop runs N times and the inner loop runs N-i times,
     *   leading to quadratic time.
     * - Space Complexity: O(1) Auxiliary Space
     *   Only primitive loop counters and accumulators are used.
     */
    public static int bruteForceShortestWindow(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += nums[j];
                if (currentSum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // Optimization: No need to expand further for this 'i'
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * PHASE 3: Alternative Approach - Prefix Sums + Binary Search
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * Since all numbers are positive, a prefix sum array will be strictly increasing
     * (sorted). This opens the door for Binary Search.
     * We precompute `prefix[i]`, which stores the sum of `nums[0...i-1]`.
     * For each index `i`, we are looking for an index `j` such that the sum from `i`
     * to `j` is >= target. Mathematically: `prefix[j+1] - prefix[i] >= target`.
     * This rearranges to: `prefix[j+1] >= target + prefix[i]`.
     * Because `prefix` is sorted, we can use binary search (O(log N)) to find the
     * smallest `j+1` that satisfies this condition for each `i`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N)
     *   Building the prefix array takes O(N). Then, for each of the N elements,
     *   we perform a Binary Search taking O(log N) time.
     * - Space Complexity: O(N) Heap Space
     *   We must allocate an array of size N + 1 to store the prefix sums.
     */
    public static int binarySearchPrefixSumWindow(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int minLen = Integer.MAX_VALUE;
        int[] prefix = new int[n + 1]; // prefix[i] is sum of first i elements

        // Build prefix sum array
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }

        for (int i = 0; i < n; i++) {
            int requiredSum = target + prefix[i];

            // Arrays.binarySearch returns index if found, else -(insertion point) - 1
            int bound = Arrays.binarySearch(prefix, requiredSum);
            if (bound < 0) {
                bound = -bound - 1;
            }

            // If a valid bound is found within the array limits
            if (bound <= n) {
                minLen = Math.min(minLen, bound - i);
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * 4. TESTING SUITE
     * ----------------------------------------------------------------------------
     * Validating all approaches against standard and edge cases using Java Streams
     * for succinct test execution and reporting.
     */
    public static void main(String[] args) {
        // Define Test Cases [target, ...array_elements]
        int[][] testCases = {
                {7,  2, 3, 1, 2, 4, 3},       // Standard: Middle array shortest
                {4,  1, 4, 4},                // Standard: Single element satisfies
                {11, 1, 1, 1, 1, 1, 1, 1, 1}, // Edge: Impossible to reach target
                {15, 5, 1, 3, 5, 10, 7, 4, 9},// Standard: Random distribution
                {10, 10, 2, 3},               // Edge: First element is target
                {100, 1}                      // Edge: Array too small, single element
        };

        int[] expectedResults = {2, 1, 0, 2, 1, 0};

        System.out.println("======================================================");
        System.out.println("TESTING SUITE: Minimum Size Subarray Sum");
        System.out.println("======================================================\n");

        IntStream.range(0, testCases.length).forEach(i -> {
            int target = testCases[i][0];
            int[] nums = Arrays.copyOfRange(testCases[i], 1, testCases[i].length);
            int expected = expectedResults[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array: " + Arrays.toString(nums) + " | target = " + target);

            int bruteResult = bruteForceShortestWindow(target, nums);
            int binaryResult = binarySearchPrefixSumWindow(target, nums);
            int optimalResult = optimalShortestWindow(target, nums);

            boolean passed = (optimalResult == expected) &&
                    (bruteResult == expected) &&
                    (binaryResult == expected);

            System.out.println("Brute Force Output: " + bruteResult);
            System.out.println("Binary Search Out : " + binaryResult);
            System.out.println("Optimal Output    : " + optimalResult);
            System.out.println("Expected Output   : " + expected);
            System.out.println("Status            : " + (passed ? "✅ PASS" : "❌ FAIL"));
            System.out.println("------------------------------------------------------");
        });
    }
}