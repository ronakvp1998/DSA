/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array of positive integers `nums` and a positive integer `target`, 
 * return the minimal length of a contiguous subarray whose sum is greater than 
 * or equal to `target`. If there is no such subarray, return 0 instead.
 *
 * This is the classic "Shortest/Minimum Window satisfying a condition" pattern.
 * (Often corresponds to LeetCode 209: Minimum Size Subarray Sum).
 *
 * Constraints:
 * - 1 <= target <= 10^9
 * - 1 <= nums.length <= 10^5
 * - 1 <= nums[i] <= 10^4
 *
 * Input/Output Formats:
 * - Input: An integer array `nums` and an integer `target`.
 * - Output: An integer representing the minimal length of a valid subarray, 
 *           or 0 if no such subarray exists.
 *
 * Examples:
 * Example 1:
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 *
 * Example 2:
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 * Explanation: The subarray [4] has the minimal length (sum >= 4).
 *
 * Example 3:
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 * Explanation: The total sum is 8, which is less than 11. No valid subarray exists.
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * - Phase 1: Optimal Approach - Sliding Window O(N).
 * - Phase 2: Brute Force Approach - Nested loops checking all subarrays O(N^2).
 * - Phase 3: Alternative Approach - Prefix Sums + Binary Search O(N log N).
 * ============================================================================
 */
package strivers.slidingwind2pointer.patterns;

import java.util.Arrays;

public class ShortestMinWindowCondition {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window / Two Pointers)
     * ========================================================================
     * Detailed Intuition:
     * We use two pointers, `l` (left) and `r` (right), to represent a window.
     * We expand the window by moving `r` to the right and adding `nums[r]` to 
     * our running sum. 
     * As soon as the sum becomes >= target, we have found a valid window. But 
     * we want the *minimum* window. So, we try to shrink it from the left by 
     * moving `l` forward and subtracting `nums[l]`, continuously updating our 
     * minimum length as long as the condition (sum >= target) holds true.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). The right pointer `r` iterates through the array 
     *   once. The left pointer `l` also traverses the array at most once across 
     *   all iterations of the inner `while` loop. Total operations are proportional 
     *   to 2N.
     * - Space Complexity: O(1) auxiliary stack space. We only use primitive 
     *   pointers and sum trackers.
     */
    public static int optimalSlidingWindow(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int l = 0, r = 0, sum = 0;
        int minLen = Integer.MAX_VALUE;
        int n = nums.length;

        while (r < n) {
            sum += nums[r]; // Expand the window

            // Shrink the window as long as the condition is met
            while (sum >= target) {
                minLen = Math.min(minLen, r - l + 1);
                sum -= nums[l];
                l++;
            }
            r++;
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * Detailed Intuition:
     * We consider every possible starting point `i` in the array. From each `i`, 
     * we expand our subarray end point `j` to the right, adding to the sum.
     * The moment the sum hits or exceeds the target, we record the length 
     * `j - i + 1`, update our global minimum, and break out of the inner loop 
     * (since any further expansion from `i` would only yield a longer subarray, 
     * which we don't want).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). In the worst case (e.g., target is very large 
     *   and only met at the end of the array), we will iterate N times for the 
     *   first element, N-1 for the second, etc., leading to quadratic time.
     * - Space Complexity: O(1) auxiliary stack space. No extra memory is used.
     */
    public static int bruteForce(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int minLen = Integer.MAX_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // Found the shortest valid subarray starting at i
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Prefix Sum + Binary Search)
     * ========================================================================
     * Detailed Intuition:
     * Since all elements in the array are positive integers, a prefix sum array 
     * will be strictly monotonically increasing. This monotonicity allows us 
     * to use Binary Search.
     * For every starting index `i`, we can calculate its prefix sum up to `i-1`. 
     * We then want to find an ending index `j` such that the sum from `i` to `j` 
     * is >= target. In prefix sums: prefix[j] - prefix[i-1] >= target, which 
     * means we need to find a `j` where prefix[j] >= target + prefix[i-1].
     * We can use binary search to quickly find this `j`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N). We build a prefix sum array in O(N). Then, 
     *   we iterate N times, and in each iteration, we do a binary search O(log N).
     * - Space Complexity: O(N) heap space to store the prefix sum array.
     */
    public static int binarySearchPrefixSum(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] prefix = new int[n + 1];

        // Build 1-based prefix sum array
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }

        int minLen = Integer.MAX_VALUE;

        for (int i = 1; i <= n; i++) {
            int targetSum = target + prefix[i - 1];
            // Custom binary search to find the lower bound (first index where prefix >= targetSum)
            int bound = lowerBound(prefix, i, n, targetSum);
            if (bound <= n) {
                minLen = Math.min(minLen, bound - i + 1);
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // Helper method to find the first index in arr from [left...right] that is >= target
    private static int lowerBound(int[] arr, int left, int right, int target) {
        int ans = right + 1; // Default to out of bounds
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] >= target) {
                ans = mid;
                right = mid - 1; // Try to find a smaller index
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        int[][] testArrays = {
                {2, 3, 1, 2, 4, 3}, // Standard case
                {1, 4, 4},          // Single element satisfies the target
                {1, 1, 1, 1, 1},    // Target larger than whole array sum
                {10, 2, 3},         // First element satisfies the target
                {1, 2, 3, 4, 5}     // Exact match at the end
        };

        int[] targets = {7, 4, 11, 6, 15};

        System.out.println("Running Minimum Window Condition Test Suite...\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] currentArr = testArrays[i];
            int currentTarget = targets[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("Array: ");
            Arrays.stream(currentArr).forEach(num -> System.out.print(num + " "));
            System.out.println("\nTarget = " + currentTarget);

            int brute = bruteForce(currentTarget, currentArr);
            int binSearch = binarySearchPrefixSum(currentTarget, currentArr);
            int optimal = optimalSlidingWindow(currentTarget, currentArr);

            System.out.println("Phase 1 (Optimal Sliding Window) : " + optimal);
            System.out.println("Phase 2 (Brute Force)            : " + brute);
            System.out.println("Phase 3 (Prefix + Binary Search) : " + binSearch);

            if (brute == binSearch && binSearch == optimal) {
                System.out.println("Status: PASS\n");
            } else {
                System.out.println("Status: FAIL - Mismatch detected\n");
            }
        }
    }
}