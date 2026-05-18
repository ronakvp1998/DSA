package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: MAJORITY ELEMENT
 * ============================================================================
 * * 169. Majority Element
 * Solved | Easy | Topics | Companies
 * * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 * * Example 1:
 * Input: nums = [3,2,3]
 * Output: 3
 * * Example 2:
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 * * Constraints:
 * - n == nums.length
 * - 1 <= n <= 5 * 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - The input is generated such that a majority element will exist in the array.
 * * Follow-up: Could you solve the problem in linear time and in O(1) space?
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Boyer-Moore Voting Algorithm)
 * ============================================================================
 * Since this is an Array/Counting problem, a recursion tree isn't applicable.
 * Instead, here is a visualization of the optimal O(1) space approach (Boyer-Moore).
 * * The intuition is that the majority element will always outnumber all other
 * elements combined. We can "cancel out" different elements. The one that
 * survives the cancellation process is our majority element.
 * * Input: [2, 2, 1, 1, 1, 2, 2]
 * * Step | Element | Count Check | Candidate | Count | Explanation
 * ----------------------------------------------------------------------------
 * Init |   -     | count == 0  |  None     |   0   | Starting state.
 * 0   |   2     | count == 0  |   2       |   1   | New candidate is 2.
 * 1   |   2     | num == cand |   2       |   2   | Same as candidate, count++.
 * 2   |   1     | num != cand |   2       |   1   | Different, count-- (cancel).
 * 3   |   1     | num != cand |   2       |   0   | Different, count-- (cancel).
 * 4   |   1     | count == 0  |   1       |   1   | Count is 0! New candidate is 1.
 * 5   |   2     | num != cand |   1       |   0   | Different, count-- (cancel).
 * 6   |   2     | count == 0  |   2       |   1   | Count is 0! New candidate is 2.
 * * Final Result: Candidate is 2.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MajorityElementsN2 {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * We iterate through the array, and for every element, we iterate through
     * the array again to count its total occurrences. If the count exceeds n/2,
     * we return it.
     * * Intuition:
     * This is the most basic translation of the problem statement. "Find an
     * element that appears more than n/2 times." We literally count every element.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2)
     * Nested loops traverse the array of size N.
     * - Space Complexity: O(1)
     * We only use a few integer variables. Auxiliary stack space is O(1) and
     * heap space is O(1).
     */
    public int majorityElementBruteForce(int[] nums) {
        int majorityCount = nums.length / 2;

        for (int num : nums) {
            int count = 0;
            for (int elem : nums) {
                if (elem == num) {
                    count++;
                }
            }
            if (count > majorityCount) {
                return num;
            }
        }
        return -1; // Fallback, though constraint says majority always exists
    }

    /**
     * ========================================================================
     * PHASE 2: HASH MAP APPROACH (Alternative Approach)
     * ========================================================================
     * Approach:
     * Iterate through the array once and maintain a frequency map. If any
     * element's frequency exceeds n/2, return it.
     * * Intuition:
     * The brute force re-counts elements repeatedly. We can trade space for
     * time by caching the frequencies as we see them. This reduces the time
     * down to a single linear pass.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the array once. HashMap insertions and lookups are O(1) on average.
     * - Space Complexity: O(N)
     * We allocate heap space for the HashMap, which in the worst case might
     * store up to N/2 elements. Auxiliary stack space is O(1).
     */
    public int majorityElementHashMap(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        int majorityCount = nums.length / 2;

        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
            if (counts.get(num) > majorityCount) {
                return num;
            }
        }
        return -1;
    }

    /**
     * ========================================================================
     * PHASE 3: SORTING APPROACH (Alternative Approach)
     * ========================================================================
     * Approach:
     * Sort the array in ascending order. The majority element must occupy the
     * middle index `nums.length / 2` because it appears more than n/2 times.
     * * Intuition:
     * If an element constitutes more than half of the array, then no matter how
     * it is arranged when sorted, it will always cross the exact middle line of
     * the array. For example, in a sorted array of 5 elements, if an element
     * appears 3 times, it will occupy indices [0,1,2] or [1,2,3] or [2,3,4].
     * Index 2 is always covered.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N)
     * Sorting the array is the dominant operation.
     * - Space Complexity: O(1) or O(log N)
     * Heap space is O(1) as sorting is in-place. Auxiliary stack space for
     * Java's Dual-Pivot Quicksort (for primitives) is O(log N).
     */
    public int majorityElementSorting(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * ========================================================================
     * PHASE 4: BOYER-MOORE VOTING ALGORITHM (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * Maintain a `candidate` and a `count`. Iterate through the array.
     * If `count` is 0, assign the current element as `candidate`.
     * If the current element equals the candidate, increment `count`.
     * Otherwise, decrement `count`. Return the `candidate`.
     * * Intuition:
     * This answers the specific follow-up for O(N) time and O(1) space.
     * Imagine elements as votes. Because the majority element has more than
     * half the total votes, even if every other element uniquely voted against
     * it, the majority element would still have votes left over. The counter
     * acts as a cancellation mechanism.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * A single pass through the array.
     * - Space Complexity: O(1)
     * We only use two variables: `candidate` and `count`.
     * Heap space: O(1). Stack space: O(1).
     */
    public int majorityElementOptimal(int[] nums) {
        int count = 0;
        int candidate = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }

            if (num == candidate) {
                count += 1;
            } else {
                count -= 1;
            }
        }

        return candidate;
    }

    /**
     * ========================================================================
     * 5. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        MajorityElementsN2 solution = new MajorityElementsN2();

        // Define Test Cases
        int[][] testCases = {
                {3, 2, 3},                           // Example 1
                {2, 2, 1, 1, 1, 2, 2},               // Example 2
                {1},                                 // Edge Case: Single element
                {8, 8, 7, 7, 7},                     // All items clustered
                {0, 0, 0, 1, 0},                     // Edge Case: Zeroes
                {-1, 1, 1, 1, 2, 1, -1}              // Edge Case: Negatives
        };

        System.out.println("=============================================");
        System.out.println("Executing Majority Element Testing Suite");
        System.out.println("=============================================\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(testCases[i]));

            // Clone arrays where mutation (sorting) occurs
            int[] testArrBrute = testCases[i].clone();
            int[] testArrHash = testCases[i].clone();
            int[] testArrSort = testCases[i].clone();
            int[] testArrOpt = testCases[i].clone();

            // Test Brute Force
            long start1 = System.nanoTime();
            int res1 = solution.majorityElementBruteForce(testArrBrute);
            long end1 = System.nanoTime();

            // Test Hash Map
            long start2 = System.nanoTime();
            int res2 = solution.majorityElementHashMap(testArrHash);
            long end2 = System.nanoTime();

            // Test Sorting
            long start3 = System.nanoTime();
            int res3 = solution.majorityElementSorting(testArrSort);
            long end3 = System.nanoTime();

            // Test Optimal (Boyer-Moore)
            long start4 = System.nanoTime();
            int res4 = solution.majorityElementOptimal(testArrOpt);
            long end4 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Hash Map]    Output: " + res2 + " | Time: " + (end2 - start2) + " ns");
            System.out.println("  [Sorting]     Output: " + res3 + " | Time: " + (end3 - start3) + " ns");
            System.out.println("  [Boyer-Moore] Output: " + res4 + " | Time: " + (end4 - start4) + " ns");

            // Verification
            boolean isValid = (res1 == res2) && (res2 == res3) && (res3 == res4);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------");
        }
    }
}