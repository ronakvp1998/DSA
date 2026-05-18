package strivers.slidingwind2pointer.sum;

/**
 * ============================================================================
 * DSA MASTERCLASS: Maximum Sum Subarray of Size K
 * Constant Window Problem
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an array of integers `arr[]` and an integer `k`, find the maximum sum
 * of any contiguous subarray of length exactly `k`.
 *
 * Constraints:
 * - 1 <= arr.length <= 10^5
 * - 1 <= k <= arr.length
 * - -10^4 <= arr[i] <= 10^4
 *
 * Input Format:
 * - `arr`: An array of integers.
 * - `k`: An integer representing the window size.
 *
 * Output Format:
 * - An integer representing the maximum possible sum of `k` consecutive elements.
 *
 * Examples:
 * ---------
 * Example 1:
 * Input: arr = {-1, 2, 3, 3, 4, 5, -1}, k = 4
 * Output: 15
 * Explanation:
 * Possible windows of size 4 and their sums:
 *   {-1, 2, 3, 3} -> 7
 *   {2, 3, 3, 4}  -> 12
 *   {3, 3, 4, 5}  -> 15  (maximum sum)
 *   {3, 4, 5, -1} -> 11
 *
 * Example 2:
 * Input: arr = {100, 200, 300, 400}, k = 2
 * Output: 700
 * Explanation:
 *   {100, 200} -> 300
 *   {200, 300} -> 500
 *   {300, 400} -> 700 (maximum sum)
 *
 * Example 3 (Edge Case - All Negatives):
 * Input: arr = {-5, -2, -9, -1, -3}, k = 3
 * Output: -13
 * Explanation:
 *   {-5, -2, -9} -> -16
 *   {-2, -9, -1} -> -12
 *   {-9, -1, -3} -> -13
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ============================================================================
 */

import java.util.Arrays;

public class MaximumSumSubarrayK {

    /**
     * PHASE 1: Optimal Approach - Fixed Size Sliding Window (Evaluated & Refined)
     * ----------------------------------------------------------------------------
     * The provided code in the prompt is already using the optimal Sliding Window
     * approach. I have retained its core logic while ensuring standard naming
     * conventions and robust edge-case handling (e.g., k > arr.length).
     *
     * Detailed Intuition:
     * Instead of recalculating the sum of elements from scratch for every window,
     * we recognize an overlapping property between adjacent windows. The next window
     * is identical to the current window, except it drops the first element of the
     * current window and adds the very next element in the array. This transforms
     * an O(K) summation into an O(1) arithmetic update (Current Sum - Outgoing + Incoming).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We calculate the sum of the first `k` elements in O(K) time, and then process
     *   the remaining `N - K` elements in O(1) time each. Total time is strictly linear.
     * - Space Complexity: O(1) Auxiliary Space
     *   We only maintain a few integer variables (`maxSum`, `sum`, `l`, `r`). No extra
     *   heap space is allocated.
     */
    public static int constantWindowOptimal(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input or window size");
        }

        int n = arr.length;
        int l = 0, r = k - 1;
        int sum = 0;

        // Step 1: Calculate sum of the first window (first k elements)
        for (int i = 0; i <= r; i++) {
            sum += arr[i];
        }

        int maxSum = sum;

        // Step 2: Slide the window until the end of the array
        while (r < n - 1) {
            // Remove the element going out of the window (leftmost)
            sum -= arr[l];
            l++;

            // Add the new element entering the window (rightmost)
            r++;
            sum += arr[r];

            // Update maximum sum
            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }

    /**
     * PHASE 2: Brute Force Approach - Nested Loops
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * The most intuitive way to solve this is to physically generate every possible
     * subarray of size `k`, calculate the sum of its elements by iterating through
     * it, and keep track of the maximum sum encountered. This acts as our foundational
     * logic to ensure correctness before optimizing.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * K)
     *   For each of the (N - K + 1) possible starting positions, we loop `K` times
     *   to compute the sum.
     * - Space Complexity: O(1) Auxiliary Space
     *   Only primitive variables are used for iteration and aggregation.
     */
    public static int constantWindowBruteForce(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input or window size");
        }

        int maxSum = Integer.MIN_VALUE;

        // Iterate through all possible starting indices of the window
        for (int i = 0; i <= arr.length - k; i++) {
            int currentSum = 0;
            // Calculate sum for exactly k elements starting from index i
            for (int j = i; j < i + k; j++) {
                currentSum += arr[j];
            }
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    /**
     * PHASE 3: Alternative Approach - Prefix Sum Array
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * We can precompute a prefix sum array where `prefix[i]` stores the sum of all
     * elements from index 0 to `i-1`. The sum of any subarray from index `i` to `j`
     * can then be calculated in O(1) time as `prefix[j + 1] - prefix[i]`. This trades
     * space for time, achieving O(N) queries but requiring O(N) extra memory. While
     * Sliding Window is strictly better (O(1) space), Prefix Sum is a valuable pattern
     * to recognize for situations where window sizes are dynamic or multiple queries exist.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   O(N) to build the prefix array, and O(N) to evaluate all windows.
     * - Space Complexity: O(N) Heap Space
     *   We allocate an array of size N + 1 to store the prefix sums.
     */
    public static int constantWindowPrefixSum(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input or window size");
        }

        int n = arr.length;
        int[] prefix = new int[n + 1];

        // Build Prefix Sum Array
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + arr[i];
        }

        int maxSum = Integer.MIN_VALUE;

        // Query sum of windows using prefix sums
        for (int i = 0; i <= n - k; i++) {
            // Sum of arr[i...i+k-1]
            int currentWindowSum = prefix[i + k] - prefix[i];
            maxSum = Math.max(maxSum, currentWindowSum);
        }

        return maxSum;
    }

    /**
     * 4. TESTING SUITE
     * ----------------------------------------------------------------------------
     */
    public static void main(String[] args) {
        // Define Test Cases
        int[][] testArrays = {
                {-1, 2, 3, 3, 4, 5, -1}, // Standard case (from prompt)
                {100, 200, 300, 400},    // Strictly increasing
                {-5, -2, -9, -1, -3},    // All negatives
                {1, 2, 3, 4, 5},         // k = array length
                {5, 0, 0, 0, 5},         // Zeroes included
                {10, 20, 30, 40}         // k = 1
        };

        int[] kValues = {4, 2, 3, 5, 2, 1};
        int[] expectedOutputs = {15, 700, -12, 15, 5, 40};

        System.out.println("======================================================");
        System.out.println("TESTING SUITE: Maximum Sum Subarray of Size K");
        System.out.println("======================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] arr = testArrays[i];
            int k = kValues[i];
            int expected = expectedOutputs[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array: " + Arrays.toString(arr) + " | k = " + k);

            // Execute all phases
            int bruteResult = constantWindowBruteForce(arr, k);
            int prefixResult = constantWindowPrefixSum(arr, k);
            int optimalResult = constantWindowOptimal(arr, k);

            // Validation
            boolean passed = (optimalResult == expected) &&
                    (bruteResult == expected) &&
                    (prefixResult == expected);

            System.out.println("Brute Force Output: " + bruteResult);
            System.out.println("Prefix Sum Output : " + prefixResult);
            System.out.println("Optimal Output    : " + optimalResult);
            System.out.println("Expected Output   : " + expected);
            System.out.println("Status            : " + (passed ? "✅ PASS" : "❌ FAIL"));
            System.out.println("------------------------------------------------------");
        }

        // Edge Case Handling Test (Exception)
        try {
            System.out.println("Testing Invalid Window Size (k > array length):");
            constantWindowOptimal(new int[]{1, 2, 3}, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Status            : ✅ PASS (Exception caught: " + e.getMessage() + ")");
        }
        System.out.println("======================================================");
    }
}