package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: LONGEST CONSECUTIVE SEQUENCE
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * 128. Longest Consecutive Sequence
 * Solved | Medium | Topics: Array, Hash Table, Union Find
 * * Problem Statement:
 * Given an unsorted array of integers `nums`, return the length of the longest
 * consecutive elements sequence.
 * * You must write an algorithm that runs in O(n) time.
 * * Example 1:
 * Input: nums = [100, 4, 200, 1, 3, 2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 * * Example 2:
 * Input: nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
 * Output: 9
 * Explanation: The longest consecutive elements sequence is [0, 1, 2, 3, 4, 5, 6, 7, 8].
 * * Constraints:
 * - 0 <= nums.length <= 10^5
 * - -10^9 <= nums[i] <= 10^9
 *
 * 1. The Problem: "Consecutive Sequence" (Value-Based)
 * In this specific problem, we only care about the mathematical values.
 * What matters: The numbers must form a math sequence where each number is exactly +1 greater than the last (e.g., $1, 2, 3, 4$).
 * What DOES NOT matter: Their original positions or order in the array. They can be completely scrambled.
 * Example: [1, 2, 3, 4] is the longest consecutive sequence here.
 * Notice how 1 is at index 3, 2 is at index 5, 3 is at index 4, and 4 is at index 1.
 * We completely ignored their original order.
 *
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Set-Based Sequence Starters)
 * ============================================================================
 * * Since this is an Array/Hashing problem, not DP, we don't use a recursion tree.
 * Instead, here is a visualization of the optimal O(N) Hash Set strategy.
 * * To hit O(N) time, we can only afford to count each sequence exactly ONCE.
 * How do we identify the TRUE START of a sequence?
 * A number is a "starter" ONLY IF `num - 1` does NOT exist in the set.
 * * Input: [100, 4, 200, 1, 3, 2]
 * Set: {100, 4, 200, 1, 3, 2}
 * * Iteration through Set:
 * Current | is `current - 1` present? | Action
 * ----------------------------------------------------------------------------
 * 100   | No (99 is missing)        | -> Start counting: 100. (Streak: 1)
 * 4    | Yes (3 is present)        | -> Skip. (Not a starter)
 * 200   | No (199 is missing)       | -> Start counting: 200. (Streak: 1)
 * 1    | No (0 is missing)         | -> Start counting: 1, 2, 3, 4. (Streak: 4)
 * 3    | Yes (2 is present)        | -> Skip. (Not a starter)
 * 2    | Yes (1 is present)        | -> Skip. (Not a starter)
 * * Global Maximum Streak: 4
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSeqLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * * Approach:
     * For every element in the array, we assume it is the start of a sequence.
     * We then do a linear search through the entire array to find `num + 1`,
     * then `num + 2`, and so on, keeping a running count of the streak.
     * * Detailed Intuition:
     * This is the literal translation of the problem into code. We pick an
     * arbitrary starting point and iteratively scan the entire array over and
     * over again for the next sequential number.
     * * Complexity Analysis:
     * - Time Complexity: O(N^3)
     * We iterate over N elements. For each element, the inner `while` loop
     * might run up to N times. Inside that loop, the `arrayContains` helper
     * performs a linear scan taking O(N) time. Total = N * N * N.
     * - Space Complexity: O(1)
     * Heap Space: O(1). No auxiliary data structures are created.
     * Stack Space: O(1). Execution runs iteratively.
     */
    public int longestConsecutiveBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int longestStreak = 0;

        for (int num : nums) {
            int currentNum = num;
            int currentStreak = 1;

            // Search the whole array for the next number in the sequence
            while (arrayContains(nums, currentNum + 1)) {
                currentNum += 1;
                currentStreak += 1;
            }

            longestStreak = Math.max(longestStreak, currentStreak);
        }

        return longestStreak;
    }

    // Helper for Brute Force
    private boolean arrayContains(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) return true;
        }
        return false;
    }

    /**
     * ========================================================================
     * PHASE 2: SORTING APPROACH (Alternative Approach - "Refine it")
     * ========================================================================
     * * Approach:
     * Sort the array in ascending order. Iterate through it while maintaining a
     * running streak. If the current number is `previous + 1`, increment the streak.
     * If it is identical to `previous`, skip it. Otherwise, the sequence is broken,
     * so update the max streak and reset the current streak to 1.
     * * Detailed Intuition:
     * Linear searching is the bottleneck in Phase 1. By sorting the array,
     * consecutive elements are forced to sit right next to each other. We can
     * now verify consecutive sequences in a single, clean linear pass.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N)
     * Sorting the array dominates the runtime. The linear scan takes O(N).
     * - Space Complexity: O(1) or O(log N)
     * Heap Space: O(1) if sorting in place.
     * Stack Space: O(log N) for Java's Dual-Pivot Quicksort recursion tree.
     */
    public int longestConsecutiveSorting(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);

        int longestStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) { // Ignore duplicates
                if (nums[i] == nums[i - 1] + 1) {
                    currentStreak++; // Sequence continues
                } else {
                    // Sequence broke. Snapshot best score and reset.
                    longestStreak = Math.max(longestStreak, currentStreak);
                    currentStreak = 1;
                }
            }
        }

        // Final check in case the longest sequence ends at the last element
        return Math.max(longestStreak, currentStreak);
    }

    /**
     * ========================================================================
     * PHASE 3: OPTIMAL HASHING APPROACH (The "Perfect it" stage)
     * ========================================================================
     * * Approach:
     * Insert all array elements into a HashSet. Iterate over the set.
     * Check if the current element is the start of a sequence by querying
     * `!set.contains(num - 1)`. If it is, use a while loop to check for
     * `num + 1`, `num + 2`, etc. within the set, incrementing the streak.
     * * Detailed Intuition:
     * To drop from O(N log N) to O(N), we must abandon sorting and trade Space
     * for Time. A HashSet provides O(1) lookups. To ensure we don't do redundant
     * counting (which would regress us to O(N^2)), we apply the "Starter Check".
     * We ONLY count upwards if the current number has no left-neighbor in the set.
     * This guarantees the inner `while` loop runs exactly N times *in total* * across the entire execution of the algorithm.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * Populating the Set takes O(N). Iterating the set takes O(N). Because of
     * the `!set.contains(num - 1)` guard, the inner while loop evaluates each
     * element at most once globally. Total time: O(N) + O(N) = O(N).
     * - Space Complexity: O(N)
     * Heap Space: O(N) to store up to N unique integers in the HashSet.
     * Stack Space: O(1) iterative execution.
     */
    public int longestConsecutiveOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;

        for (int num : numSet) {
            // Intelligent Gatekeeper: Only proceed if this is a sequence starter
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // O(1) lookups string together the sequence instantly
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestConsecutiveSeqLength solution = new LongestConsecutiveSeqLength();

        // Define Test Cases
        int[][] testCases = {
                {100, 4, 200, 1, 3, 2},             // Example 1: Standard case
                {3, 8, 5, 7, 6},                    // Example 2: From prompt
                {0, 3, 7, 2, 5, 8, 4, 6, 0, 1},     // Example 3: Contains duplicates (0)
                {1, 2, 0, 1},                       // Edge Case: Small array with duplicates
                {10, 20, 30, 40},                   // Edge Case: No consecutive elements
                {5},                                // Edge Case: Single element
                {},                                 // Edge Case: Empty array
                {-5, -4, -3, -1, 0, 1, 2}           // Edge Case: Negative numbers mixed with positive
        };

        System.out.println("=============================================================");
        System.out.println("Executing Longest Consecutive Sequence Testing Suite");
        System.out.println("=============================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];

            // Deep clone to prevent the Sorting phase from mutating the array
            // before the Optimal phase runs.
            int[] numsForBrute = nums.clone();
            int[] numsForSort = nums.clone();
            int[] numsForOptimal = nums.clone();

            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums));

            // 1. Brute Force
            long start1 = System.nanoTime();
            int res1 = solution.longestConsecutiveBruteForce(numsForBrute);
            long end1 = System.nanoTime();

            // 2. Sorting Approach
            long start2 = System.nanoTime();
            int res2 = solution.longestConsecutiveSorting(numsForSort);
            long end2 = System.nanoTime();

            // 3. Optimal Approach
            long start3 = System.nanoTime();
            int res3 = solution.longestConsecutiveOptimal(numsForOptimal);
            long end3 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Sorting]     Output: " + res2 + " | Time: " + (end2 - start2) + " ns");
            System.out.println("  [Hashing]     Output: " + res3 + " | Time: " + (end3 - start3) + " ns");

            // Verification
            boolean isValid = (res1 == res2) && (res2 == res3);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("-------------------------------------------------------------");
        }
    }
}