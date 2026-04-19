package com.questions.strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 162. Find Peak Element
 * Level: Medium
 * * Problem Statement:
 * A peak element is an element that is strictly greater than its neighbors.
 * Given a 0-indexed integer array nums, find a peak element, and return its index.
 * If the array contains multiple peaks, return the index to any of the peaks.
 * * You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is
 * always considered to be strictly greater than a neighbor that is outside the array.
 * * You must write an algorithm that runs in O(log n) time.
 * * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 * * Example 2:
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2,
 * or index number 5 where the peak element is 6.
 * * Constraints:
 * 1 <= nums.length <= 1000
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums[i] != nums[i + 1] for all valid i.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 * As this is an Array/Binary Search problem (Not DP), we follow the non-DP roadmap:
 * Phase 1: Best and recommended approach (Optimized Iterative Binary Search)
 * Phase 2: Brute Force approach (Linear Scan)
 * Phase 3: Alternative Approaches (Recursive Binary Search)
 */

public class FindPeakElement {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Binary Search)
     * ========================================================================
     * Approach:
     * We can use Binary Search to find a peak in O(log n) time. The core idea
     * relies on the fact that if we probe an index `mid` and compare it to `mid + 1`,
     * we can determine the direction of an upward slope.
     * Since the edges are implicitly -∞, an upward slope guarantees that a peak
     * exists in that direction.
     * * Detailed Intuition:
     * 1. Calculate `mid`.
     * 2. Compare `nums[mid]` with `nums[mid + 1]`.
     * 3. If `nums[mid] > nums[mid + 1]`, we are currently on a descending slope.
     * This means a peak MUST exist to our left (or `mid` itself is the peak).
     * Therefore, we narrow our search space by setting `high = mid`.
     * 4. If `nums[mid] < nums[mid + 1]`, we are on an ascending slope.
     * This means a peak MUST exist strictly to our right.
     * Therefore, we narrow our search space by setting `low = mid + 1`.
     * 5. When `low == high`, the search space has collapsed onto a single element,
     * which is guaranteed to be a peak.
     * * Complexity Analysis:
     * - Time Complexity: O(log N) where N is the length of the array. We halve
     * the search space at each step.
     * - Space Complexity: O(1) auxiliary space. We only use primitive pointers
     * (low, high, mid) on the stack. Zero heap space is consumed.
     */
    public int findPeakElementBest(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            // If we are on a downward slope, peak is to the left (including mid)
            if (nums[mid] > nums[mid + 1]) {
                high = mid;
            }
            // If we are on an upward slope, peak is to the right
            else {
                low = mid + 1;
            }
        }

        return low;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Scan) - The "Think it" stage
     * ========================================================================
     * Approach:
     * Iterate through the array from left to right. Since we are looking for
     * a local maximum, the first element we find that is greater than its
     * right neighbor is a peak.
     * * Detailed Intuition:
     * Because nums[-1] is -∞, the sequence always starts by going "up" or
     * staying flat (though constraints say adjacent elements are never equal).
     * As we walk the array, the moment we hit a "down" step (`nums[i] > nums[i+1]`),
     * `nums[i]` must be a peak. If we traverse the entire array without going down,
     * it means the array is strictly increasing, and the last element is the peak
     * (because nums[n] is also -∞).
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. In the worst
     * case (strictly increasing array), we traverse N-1 elements.
     * - Space Complexity: O(1) auxiliary space. Only a loop counter is used.
     */
    public int findPeakElementBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return i;
            }
        }

        // If no early return, the array is strictly increasing.
        return nums.length - 1;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Recursive Binary Search)
     * ========================================================================
     * Approach:
     * We use the exact same binary search logic as Phase 1, but implemented
     * via recursion to demonstrate a functional paradigm.
     * * Detailed Intuition:
     * The problem exhibits a clear divide-and-conquer property. We check the
     * slope at `mid`. Based on the slope's direction, we recursively call the
     * function on either the left half or the right half. The base case is
     * when the search boundaries converge (`low == high`).
     * * Complexity Analysis:
     * - Time Complexity: O(log N). The search space halves at each recursive frame.
     * - Space Complexity: O(log N) auxiliary stack space. The maximum depth of
     * the recursive call stack will be bounded by log(N). No dynamic heap space.
     */
    public int findPeakElementAlternative(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        return searchRecursive(nums, 0, nums.length - 1);
    }

    private int searchRecursive(int[] nums, int low, int high) {
        if (low == high) {
            return low;
        }

        int mid = low + (high - low) / 2;

        if (nums[mid] > nums[mid + 1]) {
            return searchRecursive(nums, low, mid);
        } else {
            return searchRecursive(nums, mid + 1, high);
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering standard cases, strictly increasing/decreasing
     * cases, and edge cases.
     */
    public static void main(String[] args) {
        FindPeakElement solution = new FindPeakElement();

        // Test Cases Setup
        int[][] testArrays = {
                {1, 2, 3, 1},             // Standard case (Peak at index 2)
                {1, 2, 1, 3, 5, 6, 4},    // Multiple peaks (Indices 1 or 5 are valid)
                {1},                      // Edge Case: Single element
                {2, 1},                   // Edge Case: Strictly decreasing (Peak at index 0)
                {1, 2},                   // Edge Case: Strictly increasing (Peak at index 1)
                {1, 2, 3, 4, 5},          // Edge Case: Strictly increasing, larger
                {5, 4, 3, 2, 1},          // Edge Case: Strictly decreasing, larger
                {Integer.MIN_VALUE, 0, Integer.MIN_VALUE} // Edge Case: Involving extreme limits
        };

        System.out.println("==========================================================");
        System.out.println("Executing Testing Suite for Find Peak Element");
        System.out.println("==========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array:    " + java.util.Arrays.toString(nums));

            int res1 = solution.findPeakElementBest(nums);
            int res2 = solution.findPeakElementBruteForce(nums);
            int res3 = solution.findPeakElementAlternative(nums);

            System.out.println("Phase 1 (Optimal) : Index " + res1 + " (Value: " + nums[res1] + ")");
            System.out.println("Phase 2 (Brute)   : Index " + res2 + " (Value: " + nums[res2] + ")");
            System.out.println("Phase 3 (Recurse) : Index " + res3 + " (Value: " + nums[res3] + ")");

            // Note: Since multiple peaks can exist, res1, res2, and res3 MIGHT return
            // different valid indices for arrays with multiple peaks.
            // We validate by checking if the returned index actually represents a peak.
            boolean valid1 = isPeak(nums, res1);
            boolean valid2 = isPeak(nums, res2);
            boolean valid3 = isPeak(nums, res3);

            if (valid1 && valid2 && valid3) {
                System.out.println("Status: PASS ✅");
            } else {
                System.out.println("Status: FAIL ❌ (Returned index is not a peak)");
            }
            System.out.println("----------------------------------------------------------");
        }
    }

    /**
     * Helper method to verify if an index is truly a peak.
     */
    private static boolean isPeak(int[] nums, int index) {
        if (index < 0 || index >= nums.length) return false;

        boolean greaterThanLeft = (index == 0) || (nums[index] > nums[index - 1]);
        boolean greaterThanRight = (index == nums.length - 1) || (nums[index] > nums[index + 1]);

        return greaterThanLeft && greaterThanRight;
    }
}