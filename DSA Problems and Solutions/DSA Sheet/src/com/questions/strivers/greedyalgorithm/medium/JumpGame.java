package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 55. Jump Game (Medium)
 * ==================================================================================================
 * You are given an integer array nums. You are initially positioned at the array's first index,
 * and each element in the array represents your maximum jump length at that position.
 * * Return true if you can reach the last index, or false otherwise.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0,
 * which makes it impossible to reach the last index.
 * * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Tracking Maximum Reach)
 * ==================================================================================================
 * Instead of simulating every possible jump combination (which is too slow), we can simply
 * keep track of the "farthest index" we can reach at any given point.
 * * As we iterate through the array:
 * 1. If we arrive at an index 'i' that is GREATER than our current 'maxReach', it means
 * we are stuck and cannot even reach this current spot. We immediately return false.
 * 2. Otherwise, we update our 'maxReach' to be the maximum of what it already is, OR the
 * farthest we can jump from our current spot (i + nums[i]).
 * 3. If 'maxReach' ever becomes greater than or equal to the last index, we know for sure
 * we can finish the game, so we return true.
 * ==================================================================================================
 */
public class JumpGame {

    public static void main(String[] args) {
        // Test Case 1: Expected true
        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("Test Case 1 (Expected: true)  -> Result: " + canJump(nums1));

        // Test Case 2: Expected false (Stuck at the 0)
        int[] nums2 = {3, 2, 1, 0, 4};
        System.out.println("Test Case 2 (Expected: false) -> Result: " + canJump(nums2));

        // Test Case 3: Expected true (Only one element, already at the end)
        int[] nums3 = {0};
        System.out.println("Test Case 3 (Expected: true)  -> Result: " + canJump(nums3));
    }

    /**
     * Determines if you can reach the last index of the array.
     * * @param nums Array of maximum jump lengths
     * @return true if the last index is reachable, false otherwise
     */
    public static boolean canJump(int[] nums) {
        // This variable keeps track of the farthest index we can currently reach.
        // Initially, standing at index 0, our reach is at least 0.
        int maxReach = 0;

        // Iterate through each index of the array
        for (int i = 0; i < nums.length; i++) {

            // EDGE CASE / FAILURE CONDITION:
            // If our current index 'i' is beyond our 'maxReach', it means we don't have
            // enough jump power from any previous steps to even get to this current square.
            // Therefore, reaching the end is impossible.
            if (i > maxReach) {
                return false;
            }

            // GREEDY CHOICE: Update the farthest we can reach.
            // i + nums[i] represents the farthest index we can jump to FROM our current position.
            // We take the max of our existing maxReach and this new potential reach.
            maxReach = Math.max(maxReach, i + nums[i]);

            // EARLY EXIT (Optimization):
            // If at any point our maxReach is able to hit or pass the last index,
            // we don't need to check the rest of the array. We already won.
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }

        // If we successfully iterated through without getting stuck, return true.
        return true;
    }
}