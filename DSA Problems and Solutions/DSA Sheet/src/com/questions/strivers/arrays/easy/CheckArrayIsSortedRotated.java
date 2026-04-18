package com.questions.strivers.arrays.easy;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: CHECK IF ARRAY IS SORTED AND ROTATED
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * *
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array `nums`, return `true` if the array was originally sorted in
 * non-decreasing order, then rotated some number of positions (including zero).
 * Otherwise, return `false`.
 * * There may be duplicates in the original array.
 * Note: An array `A` rotated by `x` positions results in an array `B` of the
 * same length such that `A[i] == B[(i+x) % A.length]`, where `%` is the modulo operation.
 * * * * Constraints:
 * - 1 <= nums.length <= 100
 * - 1 <= nums[i] <= 100
 * * * * Input/Output Formats:
 * Input: An array of N integers.
 * Output: A boolean value (`true` if sorted and rotated, `false` otherwise).
 *
 * * * * Example 1:
 * Input: nums = [3, 4, 5, 1, 2]
 * Output: true
 * Explanation: The sorted array [1,2,3,4,5] rotated 3 positions results in [3,4,5,1,2].
 *
 * * * * Example 2:
 * Input: nums = [2, 1, 3, 4]
 * Output: false
 * Explanation: There is no sorted array once rotated that can make nums.
 *
 * * * * Example 3:
 * Input: nums = [1, 2, 3]
 * Output: true
 * Explanation: The sorted array [1,2,3] rotated 0 positions results in [1,2,3].
 * 
 * * * * Conceptual Visualization (The "One Drop" Rule):
 * If an array is sorted, plotting its values forms a constantly rising staircase.
 * If you take that staircase and rotate it, you are taking the top portion and
 * moving it to the bottom.
 * This creates EXACTLY ONE "drop" or "cliff" in the entire circular array.
 * * Array: [3, 4, 5, 1, 2]
 *           ↗  ↗  ↘  ↗
 * (rise)(rise)(DROP)(rise)
 * Wrap around: 2 -> 3 (rise)
 * Total Drops: 1. (Valid!)
 * * If there is more than 1 drop (including the wrap-around from the last element
 * to the first), it is impossible for the array to have been sorted.
 * ============================================================================
 */
public class CheckArrayIsSortedRotated {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Single Pass "One Drop" Check)
     * ========================================================================
     * Approach and Steps:
     * 1. Traverse the array from index 0 to N-1.
     * 2. Compare the current element `nums[i]` with the next element `nums[(i + 1) % N]`.
     * Using modulo `% N` elegantly handles the circular wrap-around from the
     * last element back to the first element.
     * 3. Count how many times `nums[i] > nums[(i + 1) % N]` (this is a "drop").
     * 4. If the count of drops exceeds 1, return `false`.
     * 5. If the loop completes and drops <= 1, return `true`.
     * * * * Detailed Intuition:
     * A sorted array has 0 drops. A sorted array that has been rotated > 0 times
     * has exactly 1 drop (where the end of the original array meets the beginning).
     * By treating the array as a circular structure, we simply count the anomalies.
     * If the sequence breaks its non-decreasing property more than once, it's structurally
     * invalid. This is the most elegant and mathematically sound approach.
     * * * * Complexity Analysis:
     * - Time (O): O(N). We traverse the array exactly once, performing O(1) operations.
     * - Space (O): O(1) auxiliary space. We only maintain a primitive integer counter.
     * ========================================================================
     */
    public static boolean checkOptimal(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }

        int drops = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // Compare current element with the next (circularly)
            if (nums[i] > nums[(i + 1) % n]) {
                drops++;
            }

            // Early exit optimization
            if (drops > 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage (Simulation)
     * ========================================================================
     * Approach and Steps:
     * 1. Create a clone of the original array and sort it. This represents our
     * "target" structural state.
     * 2. Loop through all possible rotation offsets (`rotation` from 0 to N-1).
     * 3. For each offset, iterate through the original array and verify if every
     * element matches the sorted array at the shifted index.
     * 4. If an entire pass matches perfectly, return `true`.
     * 5. If all rotations are tried and none match, return `false`.
     * * * * Detailed Intuition:
     * When faced with a definition-heavy problem, the brute force approach is to
     * literally simulate the definition. The definition says: "sorted and rotated
     * by some number". We don't know the number, so we try them all. We create the
     * sorted base case, rotate it step by step, and check for equality. It is
     * logically bulletproof but wildly inefficient.
     * * * * Complexity Analysis:
     * - Time (O): O(N^2). We try N rotations, and for each rotation, we do an O(N)
     * array comparison. Sorting takes O(N log N), but the nested loops dominate.
     * - Space (O): O(N) auxiliary heap space to store the cloned, sorted array.
     * ========================================================================
     */
    public static boolean checkBruteForce(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }

        int n = nums.length;
        int[] expected = nums.clone();
        Arrays.sort(expected);

        // Try all possible rotations
        for (int rotation = 0; rotation < n; rotation++) {
            boolean isMatch = true;

            // Check if the current rotation matches the input array
            for (int i = 0; i < n; i++) {
                if (nums[(i + rotation) % n] != expected[i]) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                return true;
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Find Pivot and Verify)
     * ========================================================================
     * Approach and Steps:
     * 1. Traverse the array to find the "pivot" (the index where nums[i] > nums[i+1]).
     * 2. If no pivot is found, the array is purely sorted. Return `true`.
     * 3. If a pivot is found, we must verify two conditions:
     * a. The sub-array from `pivot + 1` to the end must be perfectly sorted.
     * b. The last element of the array must be <= the first element of the array.
     * 4. If another drop is found after the pivot, or the boundary check fails,
     * return `false`.
     * * * * Detailed Intuition:
     * This is a non-circular iteration variant of Phase 1. Instead of using modulo
     * math, we explicitly break the array into two contiguous chunks at the drop point.
     * We ensure Chunk A is sorted, Chunk B is sorted, and that Chunk B seamlessly
     * connects to the start of Chunk A.
     * * * * Complexity Analysis:
     * - Time (O): O(N). Linear scan to find the pivot and verify the rest.
     * - Space (O): O(1) auxiliary space.
     * ========================================================================
     */
    public static boolean checkAlternative(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }

        int n = nums.length;
        int pivot = -1;

        // Find the pivot point where the drop occurs
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                pivot = i;
                break;
            }
        }

        // If no drop is found, it's already sorted
        if (pivot == -1) {
            return true;
        }

        // Ensure the rest of the array is sorted
        for (int i = pivot + 1; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return false;
            }
        }

        // Check the boundary connection (last element must be <= first element)
        return nums[n - 1] <= nums[0];
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * cases, non-rotated sorted arrays, duplicates, and structurally invalid arrays.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== CHECK IF ARRAY IS SORTED AND ROTATED TESTING SUITE ===\n");

        int[][] testCases = {
                {3, 4, 5, 1, 2},         // Standard valid rotated
                {2, 1, 3, 4},            // Invalid rotated
                {1, 2, 3},               // Valid sorted (rotated 0 times)
                {1, 1, 1},               // All duplicates
                {5, 5, 6, 6, 6, 9, 1, 2},// Valid rotated with duplicates
                {2, 1},                  // Two elements (rotated)
                {1},                     // Single element
                {6, 10, 6}               // Valid rotated (from [6,6,10])
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Test Brute Force
            long start1 = System.nanoTime();
            boolean res1 = checkBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // Test Alternative (Pivot Check)
            long start2 = System.nanoTime();
            boolean res2 = checkAlternative(tc);
            long end2 = System.nanoTime();
            System.out.println("Alternative Output: " + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Test Optimal (One Drop Check)
            long start3 = System.nanoTime();
            boolean res3 = checkOptimal(tc);
            long end3 = System.nanoTime();
            System.out.println("Optimal Output:     " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = (res1 == res2) && (res2 == res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(60) + "\n");
        }
    }
}