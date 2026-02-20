package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 45. Jump Game II (Medium)
 * ==================================================================================================
 * You are given a 0-indexed array of integers nums of length n. You are initially positioned at index 0.
 * Each element nums[i] represents the maximum length of a forward jump from index i. In other words,
 * if you are at index i, you can jump to any index (i + j) where:
 * - 0 <= j <= nums[i] and
 * - i + j < n
 * * Return the minimum number of jumps to reach index n - 1.
 * The test cases are generated such that you can reach index n - 1.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2.
 * Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Implicit BFS / Window Tracking)
 * ==================================================================================================
 * To find the MINIMUM jumps, we can think of this as expanding windows (or levels in a BFS).
 * - Level 0: The starting index (0).
 * - Level 1: All indices reachable in 1 jump from Level 0.
 * - Level 2: All indices reachable in 1 jump from any index in Level 1.
 * * Instead of creating actual levels, we track the 'currentEnd' of our current level.
 * As we iterate through the current level, we continuously calculate the 'farthest' point we
 * can reach for the NEXT level.
 * When we reach the 'currentEnd', we are forced to make a jump to enter the next level, so we
 * increment our jump count and set the new 'currentEnd' to the 'farthest' point we found.
 * ==================================================================================================
 */
public class JumpGameII {

    public static void main(String[] args) {
        // Test Case 1: Expected 2
        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("Test Case 1 (Expected: 2) -> Result: " + jump(nums1));

        // Test Case 2: Expected 2
        int[] nums2 = {2, 3, 0, 1, 4};
        System.out.println("Test Case 2 (Expected: 2) -> Result: " + jump(nums2));

        // Test Case 3: Expected 0 (Already at the end)
        int[] nums3 = {0};
        System.out.println("Test Case 3 (Expected: 0) -> Result: " + jump(nums3));
    }

    /**
     * Calculates the minimum number of jumps needed to reach the last index.
     * * @param nums Array where each element represents the max jump length.
     * @return The minimum number of jumps.
     */
    public static int jump(int[] nums) {
        // Edge Case: If the array has 1 or 0 elements, we are already at the end.
        if (nums.length <= 1) {
            return 0;
        }

        int jumps = 0;
        int currentEnd = 0; // The end boundary of our current "jump window"
        int farthest = 0;   // The farthest index we can reach from the current window

        // We only loop up to nums.length - 2 (or i < nums.length - 1).
        // Why? Because if we are standing at the last index, we don't need to jump anymore!
        // Checking the last index would unnecessarily trigger an extra jump if currentEnd == last index.
        for (int i = 0; i < nums.length - 1; i++) {

            // Greedily update the farthest we can reach from index 'i'
            farthest = Math.max(farthest, i + nums[i]);

            // If we have reached the end of our current jump window...
            if (i == currentEnd) {
                // ...we MUST make a jump to continue.
                jumps++;

                // The new window ends at the farthest point we discovered in the previous window.
                currentEnd = farthest;

                // Optimization / Early Exit:
                // If our new window boundary already covers or exceeds the last index,
                // we are guaranteed to reach the end. No need to process the rest of the array.
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }

        return jumps;
    }
}