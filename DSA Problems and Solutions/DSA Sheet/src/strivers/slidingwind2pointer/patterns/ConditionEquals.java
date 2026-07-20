/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array of non-negative integers `arr` and an integer `k`, find the 
 * total number of contiguous subarrays whose sum is exactly equal to `k`.
 *
 * Note on the Pattern: 
 * Counting subarrays with a sum *exactly* equal to `k` using a direct sliding 
 * window is problematic if the array contains zeros (e.g., [1, 0, 1] with k=2 
 * has multiple valid subarrays). To solve this purely with a sliding window, 
 * we use the mathematical deduction:
 * Count(Sum == K) = Count(Sum <= K) - Count(Sum <= K - 1)
 *
 * Constraints:
 * - 1 <= arr.length <= 10^5
 * - 0 <= arr[i] <= 10^4 (Non-negative is strictly required for the sliding window approach)
 * - 0 <= k <= 10^9
 *
 * Input/Output Formats:
 * - Input: An integer array `arr` and an integer `k`.
 * - Output: An integer representing the count of valid subarrays.
 *
 * Examples:
 * Example 1:
 * Input: arr = [1, 0, 1, 0, 1], k = 2
 * Output: 4
 * Explanation: The subarrays with sum exactly 2 are:
 * 1. [1, 0, 1] (indices 0 to 2)
 * 2. [1, 0, 1, 0] (indices 0 to 3)
 * 3. [0, 1, 0, 1] (indices 1 to 4)
 * 4. [1, 0, 1] (indices 2 to 4)
 *
 * Example 2:
 * Input: arr = [1, 2, 3], k = 3
 * Output: 2
 * Explanation: The subarrays are [1, 2] and [3].
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * - Phase 1: Optimal Approach - Sliding Window using the `atMost(K)` pattern.
 * - Phase 2: Brute Force Approach - Nested loops to evaluate all possible subarrays.
 * - Phase 3: Alternative Approach - Prefix Sum with HashMap (Best for arrays with negatives).
 * ============================================================================
 */
package strivers.slidingwind2pointer.patterns;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class ConditionEquals {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window Pattern: atMost)
     * ========================================================================
     * Detailed Intuition:
     * Directly finding the number of subarrays with sum EXACTLY equal to `k` is 
     * hard using a standard sliding window when zeros are present. Zeros don't 
     * change the sum, so the window wouldn't know whether to shrink or expand.
     *
     * Instead, finding the number of subarrays with a sum AT MOST `k` is easy 
     * and strictly monotonic (adding positive numbers strictly increases the sum).
     * Therefore, we calculate the count of subarrays with sum <= k and subtract 
     * the count of subarrays with sum <= k - 1.
     *
     * The number of valid subarrays ending at index `r` is exactly `r - l + 1`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We call the `atMost` function twice. In each call, 
     *   both `l` and `r` traverse the array at most once. Hence, O(2N) + O(2N) = O(N).
     * - Space Complexity: O(1) auxiliary stack space. We only use primitive variables. 
     *   No heap memory is dynamically allocated.
     */
    public static int optimalSlidingWindow(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 0) return 0;

        // sum <= k - sum <= (k - 1) => sum == k
        return countAtMost(arr, k) - countAtMost(arr, k - 1);
    }

    // Helper method to count subarrays with sum <= target
    private static int countAtMost(int[] arr, int target) {
        if (target < 0) return 0; // Negative target is impossible with non-negative array

        int l = 0, r = 0, sum = 0;
        int count = 0;
        int n = arr.length;

        while (r < n) {
            sum += arr[r];

            // Shrink window from left while sum is strictly greater than target
            while (sum > target) {
                sum -= arr[l];
                l++;
            }

            // If a window [l...r] is valid, then all subarrays ending at 'r' 
            // and starting anywhere between 'l' and 'r' are also valid.
            // The number of such subarrays is (r - l + 1).
            count += (r - l + 1);
            r++;
        }

        return count;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * Detailed Intuition:
     * The brute-force method checks every possible subarray combination. 
     * We iterate through the array using index `i` as the starting point, and 
     * an inner loop using index `j` as the ending point. For every subarray, 
     * we add the element at `j` to the running sum and check if it exactly equals `k`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). We use nested loops, evaluating N + (N-1) + ... + 1 
     *   combinations.
     * - Space Complexity: O(1) auxiliary stack space.
     */
    public static int bruteForce(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int count = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += arr[j];

                if (sum == k) {
                    count++;
                } else if (sum > k) {
                    // Since all numbers are non-negative, if sum exceeds k, 
                    // adding more numbers will never reduce it back to k.
                    break;
                }
            }
        }
        return count;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Prefix Sum + HashMap)
     * ========================================================================
     * Detailed Intuition:
     * While the sliding window math trick is brilliant, it FAILS if the array 
     * contains negative numbers. The globally optimal way to solve "Subarray 
     * Sum Equals K" uses a HashMap to store the frequencies of prefix sums.
     *
     * If the running prefix sum up to index `i` is `currSum`, and we want a 
     * subarray ending at `i` with sum `k`, we simply need to check if we have 
     * previously seen a prefix sum equal to `currSum - k`. If we have, every 
     * time it occurred represents a valid starting point for our subarray.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array exactly once, 
     *   performing O(1) average time map lookups and insertions.
     * - Space Complexity: O(N) heap space. The HashMap could store up to N 
     *   distinct prefix sums in the worst-case scenario.
     */
    public static int prefixSumHashMapApproach(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        // Base case: a prefix sum of 0 has occurred exactly 1 time (empty subarray)
        prefixSumMap.put(0, 1);

        int count = 0;
        int currSum = 0;

        for (int num : arr) {
            currSum += num;

            // Check if there is a prefix sum that we can chop off to get sum == k
            int removeTarget = currSum - k;
            if (prefixSumMap.containsKey(removeTarget)) {
                count += prefixSumMap.get(removeTarget);
            }

            // Record the current prefix sum in the map
            prefixSumMap.put(currSum, prefixSumMap.getOrDefault(currSum, 0) + 1);
        }

        return count;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        // Define a set of test cases
        int[][] testArrays = {
                {1, 0, 1, 0, 1},       // With zeros, counting overlapping valid windows
                {1, 2, 3},             // Standard case
                {0, 0, 0, 0},          // Array of all zeros, k = 0
                {1, 1, 1},             // k = 2
                {5, 10, 15, 20}        // Subarray doesn't exist
        };

        int[] kValues = {2, 3, 0, 2, 8};

        System.out.println("Running Subarray Sum Equals K test suite...\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] currentArr = testArrays[i];
            int currentK = kValues[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("Array: ");
            // Use Stream API for concise printing
            Arrays.stream(currentArr).forEach(num -> System.out.print(num + " "));
            System.out.println("\nk = " + currentK);

            // Execute all three phases
            int bruteResult = bruteForce(currentArr, currentK);
            int optimalResult = optimalSlidingWindow(currentArr, currentK);
            int prefixResult = prefixSumHashMapApproach(currentArr, currentK);

            System.out.println("Phase 1 (Optimal Sliding Window) : " + optimalResult);
            System.out.println("Phase 2 (Brute Force)            : " + bruteResult);
            System.out.println("Phase 3 (Prefix Sum HashMap)     : " + prefixResult);

            // Assert correctness across all approaches
            if (optimalResult == bruteResult && optimalResult == prefixResult) {
                System.out.println("Status: PASS\n");
            } else {
                System.out.println("Status: FAIL - Mismatch detected\n");
            }
        }
    }
}