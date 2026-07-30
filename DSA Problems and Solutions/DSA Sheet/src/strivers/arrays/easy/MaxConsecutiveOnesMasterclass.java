package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 485. Max Consecutive Ones
 * Difficulty: Easy
 *
 * Formal Problem Statement:
 * Given a binary array nums, return the maximum number of consecutive 1's in
 * the array.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - nums[i] is either 0 or 1.
 *
 * Example 1:
 * Input: nums = [1,1,0,1,1,1]
 * Output: 3
 * Explanation: The first two digits or the last three digits are consecutive 1s.
 * The maximum number of consecutive 1s is 3.
 *
 * Example 2:
 * Input: nums = [1,0,1,1,0,1]
 * Output: 2
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.Collectors;

public class MaxConsecutiveOnesMasterclass {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Single Pass / Counter Method)
     * ============================================================================
     * Detailed Intuition:
     * We can solve this problem in a single pass. We maintain two variables:
     * `currentCount` to track the length of the current streak of 1s, and
     * `maxCount` to track the longest streak seen so far.
     *
     * As we iterate through the array:
     * - If the current element is 1, we increment `currentCount`. We also
     *   update `maxCount` if `currentCount` exceeds it.
     * - If the current element is 0, the streak is broken, so we reset
     *   `currentCount` back to 0.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. We visit
     *   each element exactly once.
     * - Space Complexity: O(1) auxiliary space. We only use two integer variables
     *   regardless of the array size.
     */
    public int findMaxConsecutiveOnesOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxCount = 0;
        int currentCount = 0;

        for (int num : nums) {
            if (num == 1) {
                currentCount++;
                // Updating maxCount inside the if-condition avoids unnecessary
                // Math.max calls when encountering 0s.
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                }
            } else {
                currentCount = 0;
            }
        }

        return maxCount;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (Nested Loops)
     * ============================================================================
     * Detailed Intuition:
     * The brute force way to think about this is: for every 1 we find, let's look
     * ahead to see exactly how many consecutive 1s follow it. We start a second
     * loop every time we see a 1 to count the length of that specific window,
     * stopping as soon as we hit a 0 or the end of the array.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) in the absolute worst case (if we didn't smartly
     *   skip ahead, though this specific implementation behaves closer to O(N)
     *   because the inner loop advances `i` indirectly or stops quickly). Strictly
     *   speaking, without pointer jumps, evaluating a sequence starting from
     *   every index yields O(N^2).
     * - Space Complexity: O(1) auxiliary space.
     */
    public int findMaxConsecutiveOnesBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxCount = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                int currentCount = 0;
                for (int j = i; j < nums.length; j++) {
                    if (nums[j] == 1) {
                        currentCount++;
                    } else {
                        break;
                    }
                }
                maxCount = Math.max(maxCount, currentCount);
            }
        }

        return maxCount;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH (Java 8 Streams & String Splitting)
     * ============================================================================
     * Detailed Intuition:
     * We can leverage high-level Java APIs for a declarative, functional approach.
     * We convert the integer array into a single contiguous String (e.g., "110111").
     * Then, we split this string using "0" as the delimiter. This creates an array
     * of strings, each containing only "1"s (e.g., ["11", "111"]).
     * Finally, we map each substring to its length and find the maximum value.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). String building takes O(N), splitting takes O(N),
     *   and finding the max takes O(N).
     * - Space Complexity: O(N) heap space. We allocate memory for the String
     *   representation and the split arrays. This is an elegant but memory-heavy
     *   solution.
     */
    public int findMaxConsecutiveOnesStreams(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // Convert the array of integers into a single string (e.g., "110111")
        String binaryString = Arrays.stream(nums)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());

        // Split by "0", which yields sequences of "1"s.
        // We find the max length among those sequences.
        return Arrays.stream(binaryString.split("0"))
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        MaxConsecutiveOnesMasterclass solution = new MaxConsecutiveOnesMasterclass();

        System.out.println("--- Testing 485. Max Consecutive Ones ---");

        // Test Case 1: Standard Example 1
        int[] tc1 = {1, 1, 0, 1, 1, 1};
        System.out.println("\nTest 1 (Optimal):");
        System.out.println("Input: " + Arrays.toString(tc1));
        System.out.println("Output: " + solution.findMaxConsecutiveOnesOptimal(tc1));
        // Expected: 3

        // Test Case 2: Standard Example 2
        int[] tc2 = {1, 0, 1, 1, 0, 1};
        System.out.println("\nTest 2 (Brute Force):");
        System.out.println("Input: " + Arrays.toString(tc2));
        System.out.println("Output: " + solution.findMaxConsecutiveOnesBruteForce(tc2));
        // Expected: 2

        // Test Case 3: All Zeros
        int[] tc3 = {0, 0, 0, 0};
        System.out.println("\nTest 3 (All Zeros - Streams):");
        System.out.println("Input: " + Arrays.toString(tc3));
        System.out.println("Output: " + solution.findMaxConsecutiveOnesStreams(tc3));
        // Expected: 0

        // Test Case 4: All Ones
        int[] tc4 = {1, 1, 1, 1, 1};
        System.out.println("\nTest 4 (All Ones - Optimal):");
        System.out.println("Input: " + Arrays.toString(tc4));
        System.out.println("Output: " + solution.findMaxConsecutiveOnesOptimal(tc4));
        // Expected: 5

        // Test Case 5: Single element 1
        int[] tc5 = {1};
        System.out.println("\nTest 5 (Size 1 - Optimal):");
        System.out.println("Input: " + Arrays.toString(tc5));
        System.out.println("Output: " + solution.findMaxConsecutiveOnesOptimal(tc5));
        // Expected: 1
    }
}