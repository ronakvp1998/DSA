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
 *
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * ==================================================================================================
 * APPROACH 1: GREEDY ALGORITHM (Optimal - Tracking Maximum Reach)
 * ==================================================================================================
 * Instead of simulating every possible jump combination, keep track of the "farthest index"
 * we can reach at any given point.
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 * * ==================================================================================================
 * APPROACH 2: BRUTE FORCE RECURSIVE (Explores all paths)
 * ==================================================================================================
 * This approach simulates the game exactly as a player might play it by trying every possible jump.
 * From position 'i', if we can jump up to 'k' steps, we recursively try jumping 1 step, then
 * 2 steps, all the way up to 'k' steps. If ANY of those paths reach the end, we return true.
 * * * Drawback: Because it branches out for every possible jump, it recalculates the same paths
 * over and over. This leads to an exponential Time Complexity of O(2^N), which will trigger a
 * Time Limit Exceeded (TLE) error on LeetCode for large arrays.
 * Space Complexity: O(N) due to the recursion stack.
 * ==================================================================================================
 */
public class JumpGame {

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 1, 1, 4};
        int[] nums2 = {3, 2, 1, 0, 4};
        int[] nums3 = {0};

        System.out.println("--- TESTING OPTIMAL GREEDY APPROACH ---");
        System.out.println("Test Case 1 (Expected: true)  -> Result: " + canJump(nums1));
        System.out.println("Test Case 2 (Expected: false) -> Result: " + canJump(nums2));
        System.out.println("Test Case 3 (Expected: true)  -> Result: " + canJump(nums3));

        System.out.println("\n--- TESTING BRUTE FORCE RECURSIVE APPROACH ---");
        System.out.println("Test Case 1 (Expected: true)  -> Result: " + canJumpRecursive(nums1));
        System.out.println("Test Case 2 (Expected: false) -> Result: " + canJumpRecursive(nums2));
        System.out.println("Test Case 3 (Expected: true)  -> Result: " + canJumpRecursive(nums3));
    }

    /**
     * APPROACH 1: OPTIMAL GREEDY
     * Determines if you can reach the last index of the array in O(N) time.
     */
    public static boolean canJump(int[] nums) {
        int maxReach = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) {
                return false;
            }
            maxReach = Math.max(maxReach, i + nums[i]);

            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        return true;
    }

    /**
     * APPROACH 2: BRUTE FORCE RECURSIVE (Wrapper Method)
     * Kicks off the recursive helper from the starting index (0).
     */
    public static boolean canJumpRecursive(int[] nums) {
        // Start the recursive check from index 0
        return canJumpFromPosition(0, nums);
    }

    /**
     * Helper method for the recursive approach.
     * @param position The current index we are standing on.
     * @param nums The original array of maximum jump lengths.
     * @return true if we can reach the end from this position, false otherwise.
     */
    private static boolean canJumpFromPosition(int position, int[] nums) {
        // BASE CASE:
        // If our current position is at or beyond the last index, we've successfully finished the game!
        if (position >= nums.length - 1) {
            return true;
        }

        // Determine the furthest we can jump from this current position.
        // We use Math.min to ensure we don't try to jump completely out of the array bounds.
        int furthestJump = Math.min(position + nums[position], nums.length - 1);

        // RECURSIVE STEP:
        // Try every single possible jump length from our current position, starting from 1 step
        // all the way up to our maximum allowed steps (nums[position]).
        // We iterate from 'nextPosition = position + 1' up to 'furthestJump'.
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {

            // Recursively check if jumping to 'nextPosition' leads to the end of the array.
            if (canJumpFromPosition(nextPosition, nums)) {
                return true; // If any branch reaches the end, this path is a winner!
            }
        }

        // FAILURE CONDITION:
        // If we tried every possible jump from this position and NONE of them returned true,
        // it means this path is a dead end. We return false to backtrack.
        return false;
    }
}