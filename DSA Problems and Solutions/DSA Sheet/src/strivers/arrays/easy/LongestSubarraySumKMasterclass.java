package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: Longest Subarray with given Sum K
 * Difficulty: Medium
 *
 * Formal Problem Statement:
 * Given an array nums of size n and an integer k, find the length of the longest
 * sub-array that sums to k. If no such sub-array exists, return 0.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= k <= 10^9
 * *(Note: While the title says "Positives", Example 2 contains negatives.
 * Therefore, the ultimate optimal approach must handle negatives, but we will
 * also provide the O(1) space Sliding Window approach strictly for positives).*
 *
 * Example 1:
 * Input: nums = [10, 5, 2, 7, 1, 9], k = 15
 * Output: 4
 * Explanation: The longest sub-array with a sum equal to 15 is [5, 2, 7, 1],
 * which has a length of 4. This sub-array starts at index 1 and ends at index 4.
 *
 * Example 2:
 * Input: nums = [-3, 2, 1], k = 6
 * Output: 0
 * Explanation: There is no sub-array in the array that sums to 6.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LongestSubarraySumKMasterclass {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Prefix Sum + HashMap)
     * ============================================================================
     * Detailed Intuition:
     * This is the universal optimal approach because it works for arrays containing
     * both positive AND negative numbers (like Example 2).
     *
     * We maintain a running `prefixSum` as we iterate.
     * If the sum of elements from index 0 to `i` is `prefixSum`, and we are looking
     * for a subarray of sum `k`, we check if a prefix sum of `(prefixSum - k)`
     * has been seen before. If it has, it means the subarray strictly between the
     * previous index and `i` has exactly the sum `k`!
     *
     * We use a HashMap to store the FIRST occurrence of every `prefixSum`. We only
     * store the first occurrence to ensure we maximize the distance (length)
     * between the previous index and our current index.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We traverse the array exactly once. HashMap
     *   lookups/insertions are O(1) on average.
     * - Space Complexity: O(N) heap space. In the worst case, all prefix sums
     *   are distinct and stored in the HashMap.
     */
    public int longestSubarrayOptimalMap(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        // Map stores (prefixSum, earliest index where it occurred)
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        long currentPrefixSum = 0;
        int maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            currentPrefixSum += nums[i];

            // If the prefix sum from index 0 to i itself is equal to k
            if (currentPrefixSum == k) {
                maxLen = Math.max(maxLen, i + 1);
            }

            // If we have seen (prefixSum - k) before, we found a valid subarray
            long targetSum = currentPrefixSum - k;
            if (prefixSumMap.containsKey(targetSum)) {
                int previousIndex = prefixSumMap.get(targetSum);
                maxLen = Math.max(maxLen, i - previousIndex);
            }

            // Only add the currentPrefixSum to the map if it doesn't exist.
            // This guarantees we keep the EARLIEST index for maximum length.
            if (!prefixSumMap.containsKey(currentPrefixSum)) {
                prefixSumMap.put(currentPrefixSum, i);
            }
        }

        return maxLen;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: OPTIMAL APPROACH FOR STRICTLY POSITIVES (Sliding Window)
     * ============================================================================
     * Detailed Intuition:
     * If the problem guarantees that the array contains ONLY POSITIVE numbers
     * (and zeros), we can optimize our space complexity to O(1) using the
     * Sliding Window / Two Pointers technique.
     *
     * We maintain a window `[left, right]`. We expand the window to the right
     * by adding elements to our `currentSum`.
     * - If `currentSum == k`, we record the length.
     * - If `currentSum > k`, the sum is too large. Since numbers are positive,
     *   adding more right elements will only increase it further. So, we shrink
     *   the window from the `left` until the sum is <= k.
     *
     * *NOTE: This fails if negative numbers are present, because adding a negative
     * number can reduce the sum, invalidating the decision to shrink from the left.*
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Both `left` and `right` pointers only move forward.
     *   Each element is processed at most twice.
     * - Space Complexity: O(1) auxiliary space. We only use primitive variables.
     */
    public int longestSubarrayPositivesSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0;
        int right = 0;
        long currentSum = 0; // use long to prevent integer overflow
        int maxLen = 0;
        int n = nums.length;

        while (right < n) {
            currentSum += nums[right];

            // Shrink the window from the left if the sum exceeds k
            while (left <= right && currentSum > k) {
                currentSum -= nums[left];
                left++;
            }

            // Check if we hit the target sum
            if (currentSum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }

            right++;
        }

        return maxLen;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: BRUTE FORCE APPROACH
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way: check every single possible subarray.
     * We set a starting index `i`, and for every `i`, we expand an ending index
     * `j` to the right, calculating the sum on the fly. If the sum hits `k`,
     * we record the length `j - i + 1`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). We evaluate the sum of all contiguous subarrays.
     *   Will result in Time Limit Exceeded (TLE) for N = 10^5.
     * - Space Complexity: O(1) auxiliary space.
     */
    public int longestSubarrayBruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        int maxLen = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            long sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum == k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        LongestSubarraySumKMasterclass solution = new LongestSubarraySumKMasterclass();

        System.out.println("--- Testing Longest Subarray with given Sum K ---");

        // Test Case 1: Standard Example 1 (All Positives)
        int[] tc1 = {10, 5, 2, 7, 1, 9};
        int k1 = 15;
        System.out.println("\nTest 1 (All Positives):");
        System.out.println("Input: " + printArray(tc1) + " | k = " + k1);
        System.out.println("Optimal Map: " + solution.longestSubarrayOptimalMap(tc1, k1)); // Expected: 4
        System.out.println("Sliding Window: " + solution.longestSubarrayPositivesSlidingWindow(tc1, k1)); // Expected: 4

        // Test Case 2: Standard Example 2 (With Negatives)
        int[] tc2 = {-3, 2, 1};
        int k2 = 6;
        System.out.println("\nTest 2 (With Negatives - Fails Sliding Window):");
        System.out.println("Input: " + printArray(tc2) + " | k = " + k2);
        System.out.println("Optimal Map: " + solution.longestSubarrayOptimalMap(tc2, k2)); // Expected: 0

        // Test Case 3: Zeros and Negatives
        int[] tc3 = {2, 0, 0, 3};
        int k3 = 5;
        System.out.println("\nTest 3 (With Zeros - Needs Greedy Length Maximization):");
        System.out.println("Input: " + printArray(tc3) + " | k = " + k3);
        System.out.println("Optimal Map: " + solution.longestSubarrayOptimalMap(tc3, k3)); // Expected: 4

        // Test Case 4: Long Subarray with negative cancellation
        int[] tc4 = {1, -1, 5, -2, 3};
        int k4 = 3;
        System.out.println("\nTest 4 (Negative Cancellation):");
        System.out.println("Input: " + printArray(tc4) + " | k = " + k4);
        System.out.println("Optimal Map: " + solution.longestSubarrayOptimalMap(tc4, k4)); // Expected: 4 (length of [-1, 5, -2, 3] is 4)

        // Test Case 5: Entire array sums to K
        int[] tc5 = {1, 2, 3, 4};
        int k5 = 10;
        System.out.println("\nTest 5 (Full Array):");
        System.out.println("Input: " + printArray(tc5) + " | k = " + k5);
        System.out.println("Optimal Map: " + solution.longestSubarrayOptimalMap(tc5, k5)); // Expected: 4
    }

    /** Helper using Java 8 Stream API to format array printing cleanly. */
    private static String printArray(int[] arr) {
        return Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}