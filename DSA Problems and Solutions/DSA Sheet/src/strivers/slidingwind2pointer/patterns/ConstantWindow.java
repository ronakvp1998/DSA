/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array of integers `arr` and an integer `k`, find the maximum sum 
 * of a contiguous subarray of size exactly `k`.
 *
 * Constraints:
 * - 1 <= k <= arr.length <= 10^5
 * - -10^4 <= arr[i] <= 10^4
 * (If k > arr.length, the output should typically be considered invalid; 
 * we will handle it by returning a default/error value or throwing an exception).
 *
 * Input/Output Formats:
 * - Input: An integer array `arr` and an integer `k` (the window size).
 * - Output: An integer representing the maximum sum of a subarray of size `k`.
 *
 * Examples:
 * Example 1:
 * Input: arr = [1, 4, 2, 10, 2, 3, 1, 0, 20], k = 4
 * Output: 24
 * Explanation: The subarray [3, 1, 0, 20] yields the maximum sum of 24.
 *
 * Example 2:
 * Input: arr = [-1, -2, -3, -4], k = 2
 * Output: -3
 * Explanation: The subarray [-1, -2] yields the maximum sum of -3.
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Since this is not a DP problem, we follow the Non-DP roadmap:
 * - Phase 1: Optimal Approach (Sliding Window - provided code logic).
 * - Phase 2: Brute Force Approach (Nested loops).
 * - Phase 3: Alternative Approach (Prefix Sum array).
 * ============================================================================
 */
package strivers.slidingwind2pointer.patterns;

import java.util.Arrays;

public class ConstantWindow {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH - SLIDING WINDOW (User Provided, Refined)
     * ========================================================================
     * Detailed Intuition:
     * Instead of recalculating the sum of `k` elements from scratch for every 
     * new subarray, we can reuse the sum of the previous subarray. As the 
     * window slides one step to the right, one element leaves the window from 
     * the left, and one element enters the window from the right. We just 
     * subtract the outgoing element and add the incoming element.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the array. We iterate 
     *   through the first `k` elements once, and then the remaining N-k 
     *   elements exactly once.
     * - Space Complexity: O(1) auxiliary space. We only use a few integer 
     *   variables (sum, maxSum, l, r) on the stack memory. No heap space is used.
     */
    public static int optimalSlidingWindow(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input or window size");
        }

        int maxSum = Integer.MIN_VALUE;
        int l = 0;
        int r = k - 1;
        int sum = 0;
        int n = arr.length;

        // Calculate the sum of the first window of size 'k'
        for (int i = 0; i <= r; i++) {
            sum = sum + arr[i];
        }
        maxSum = sum;

        // Slide the window across the rest of the array
        while (r < n - 1) {
            sum = sum - arr[l];  // Remove the element going out of the window
            l++;
            r++;
            sum = sum + arr[r];  // Add the new element coming into the window
            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to consider every possible 
     * contiguous subarray of size `k`. We can iterate through every possible 
     * starting index from 0 to N-k, and for each starting index, run an inner 
     * loop to sum the next `k` elements.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * k). For each of the (N - k + 1) starting positions, 
     *   we do exactly `k` additions. In the worst case (e.g., k = N/2), this 
     *   approaches O(N^2).
     * - Space Complexity: O(1) auxiliary stack space. We only use primitive variables.
     */
    public static int bruteForceConstantWindow(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input or window size");
        }

        int maxSum = Integer.MIN_VALUE;
        int n = arr.length;

        // Iterate through all possible starting points of a window of size k
        for (int i = 0; i <= n - k; i++) {
            int currentSum = 0;
            // Calculate the sum of k elements starting from index i
            for (int j = i; j < i + k; j++) {
                currentSum += arr[j];
            }
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH - PREFIX SUM
     * ========================================================================
     * Detailed Intuition:
     * The sum of any subarray from index `i` to `j` can be found in O(1) time 
     * if we precompute a prefix sum array. The sum of elements between `i` 
     * and `i+k-1` is simply prefixSum[i+k-1] - prefixSum[i-1]. This shifts 
     * the time complexity to linear but requires extra space.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). O(N) to build the prefix sum array + O(N-k) to 
     *   evaluate the sums.
     * - Space Complexity: O(N) heap space to store the prefix sum array.
     */
    public static int prefixSumApproach(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input or window size");
        }

        int n = arr.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = arr[0];

        // Build the prefix sum array
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }

        // Initialize maxSum with the sum of the first 'k' elements
        int maxSum = prefixSum[k - 1];

        // Evaluate all other windows of size 'k'
        for (int i = 1; i <= n - k; i++) {
            int currentSum = prefixSum[i + k - 1] - prefixSum[i - 1];
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Testing standard cases, edge cases (negatives, k=1, k=N).
     * Utilizing Java 8 Streams for clean test reporting.
     */
    public static void main(String[] args) {
        // Define a set of test cases
        int[][] testArrays = {
                {1, 4, 2, 10, 2, 3, 1, 0, 20}, // Standard case (positive numbers)
                {-1, -2, -3, -4},              // All negative numbers
                {5, 5, 5, 5, 5},               // All identical elements
                {10, 20, 30, 40},              // Array size exactly equals k (when k=4)
                {100, 200, 300, 400}           // Window size k=1
        };

        int[] kValues = {4, 2, 3, 4, 1};

        System.out.println("Running test suite for ConstantWindow Maximum Sum:\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] currentArr = testArrays[i];
            int currentK = kValues[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("Array: ");

            // Using Java 8 Stream API to print array
            Arrays.stream(currentArr).forEach(num -> System.out.print(num + " "));
            System.out.println("\nk = " + currentK);

            // Execute all three phases
            int bruteResult = bruteForceConstantWindow(currentArr, currentK);
            int prefixResult = prefixSumApproach(currentArr, currentK);
            int optimalResult = optimalSlidingWindow(currentArr, currentK);

            System.out.println("Phase 1 (Optimal) : " + optimalResult);
            System.out.println("Phase 2 (Brute)   : " + bruteResult);
            System.out.println("Phase 3 (Prefix)  : " + prefixResult);

            // Assert correctness
            if (optimalResult == bruteResult && optimalResult == prefixResult) {
                System.out.println("Status: PASS\n");
            } else {
                System.out.println("Status: FAIL - Mismatch detected\n");
            }
        }
    }
}