package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: TWO SUM
 * ============================================================================
 * * 1. Two Sum
 * Solved | Easy | Topics | Companies
 * * Hint:
 * Given an array of integers nums and an integer target, return indices of the
 * two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may
 * not use the same element twice.
 * You can return the answer in any order.
 * * Example 1:
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * * Example 2:
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * * Example 3:
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 * * Constraints:
 * - 2 <= nums.length <= 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 * - Only one valid answer exists.
 * * Follow-up: Can you come up with an algorithm that is less than O(n^2) time complexity?
 * * ----------------------------------------------------------------------------
 * CONCEPTUAL VISUALIZATION (Hashing Approach)
 * ----------------------------------------------------------------------------
 * Since this is not a DP problem, a recursion tree isn't applicable. Instead,
 * here is a visualization of the optimal One-Pass Hash Map approach.
 * * Target = 9 | Array = [2, 7, 11, 15]
 * * Step | Current Num | Target - Num (Needed) | In Map? | Action
 * ----------------------------------------------------------------------------
 * 0   |     2       | 9 - 2 = 7             |   No    | Add {2: index 0} to Map
 * 1   |     7       | 9 - 7 = 2             |   Yes!  | Found 2 at index 0.
 * Return [0, 1]
 * * Map State Evolution:
 * Init: {}
 * i=0:  {2: 0}
 * i=1:  Hit! The complement 2 exists in the map.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Sum2Problem {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * We need to find pairs. The most straightforward way is to check every
     * single possible pair in the array to see if their sum equals the target.
     * We use a nested loop: the outer loop picks the first element, and the
     * inner loop iterates through the remaining elements to find a match.
     * * Intuition:
     * Before optimizing, establish the baseline. We know the array length is up
     * to 10^4. Checking all pairs means N * (N - 1) / 2 operations. For 10^4,
     * this is roughly 5 * 10^7 operations, which might pass but is highly inefficient.
     * * Complexity Analysis:
     * - Time Complexity: O(n^2)
     * We are iterating over the rest of the array for each element.
     * - Space Complexity: O(1)
     * No extra memory is used. Auxiliary stack space is O(1) and heap space is O(1).
     */
    public int[] twoSumBruteForce(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{}; // Should not be reached based on problem constraints
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL HASHING (The "Perfect it" stage - Alternative Approach)
     * ========================================================================
     * Approach:
     * We use a HashMap to map values to their indices. As we iterate through
     * the array, we calculate the "complement" (target - current element).
     * If the complement is already in our map, we have found our pair. If not,
     * we add the current element and its index to the map and continue.
     * * Intuition:
     * The brute force fails because we repeatedly search the array for the
     * complement (an O(n) operation per element). We can trade space for time
     * by storing elements we've seen in a Hash Table, reducing the lookup time
     * from O(n) to O(1) on average. This reduces the overall time to O(n).
     * * Complexity Analysis:
     * - Time Complexity: O(n)
     * We traverse the array exactly once. Hash map `containsKey` and `get`
     * operations are O(1) on average.
     * - Space Complexity: O(n)
     * We allocate heap space for the HashMap, which in the worst case
     * (no match found until the very end) will store n - 1 elements.
     * Auxiliary stack space is O(1).
     */
    public int[] twoSumOptimal(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // If we have seen the complement before, we found our pair!
            if (numMap.containsKey(complement)) {
                return new int[]{numMap.get(complement), i};
            }

            // Otherwise, store the current number and its index
            numMap.put(nums[i], i);
        }

        return new int[]{}; // Fallback
    }

    /**
     * ========================================================================
     * PHASE 3: TWO POINTERS W/ SORTING (Alternative Approach)
     * ========================================================================
     * Approach:
     * Normally, Two Pointers is fantastic for finding pairs that sum to a target
     * if the array is sorted. We place one pointer at the start and one at the end.
     * If sum > target, move the right pointer left. If sum < target, move left
     * pointer right.
     * * Intuition:
     * Since the problem asks for the *original indices*, sorting the array destroys
     * that information. To use this approach, we must first bind the original
     * indices to the values (using a 2D array or custom object) and then sort.
     * This is generally worse than the HashMap approach for this specific problem
     * but demonstrates strong algorithmic breadth to an interviewer.
     * * Complexity Analysis:
     * - Time Complexity: O(n log n)
     * Sorting the custom array takes O(n log n) time. The two-pointer traversal
     * takes O(n) time. Total = O(n log n).
     * - Space Complexity: O(n)
     * Heap space is O(n) to store the pairs of {value, original_index}.
     * Auxiliary stack space for sorting (Dual-Pivot Quicksort / TimSort) is O(log n).
     */
    public int[] twoSumTwoPointers(int[] nums, int target) {
        // Create an array to hold the value and its original index
        int[][] pairs = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = i;
        }

        // Sort based on the values
        Arrays.sort(pairs, (a, b) -> Integer.compare(a[0], b[0]));

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int currentSum = pairs[left][0] + pairs[right][0];

            if (currentSum == target) {
                return new int[]{pairs[left][1], pairs[right][1]};
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{}; // Fallback
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        Sum2Problem solution = new Sum2Problem();

        // Define Test Cases
        int[][] testArrays = {
                {2, 7, 11, 15},   // Example 1: Standard case
                {3, 2, 4},        // Example 2: Out of order
                {3, 3},           // Example 3: Duplicate elements
                {0, 4, 3, 0},     // Edge Case: Zero values
                {-10, 7, 19, 15}  // Edge Case: Negative values
        };
        int[] targets = {9, 6, 6, 0, 9};

        System.out.println("=============================================");
        System.out.println("Executing Two Sum Testing Suite");
        System.out.println("=============================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": nums = " + Arrays.toString(testArrays[i]) + ", target = " + targets[i]);

            // Test Brute Force
            long start1 = System.nanoTime();
            int[] res1 = solution.twoSumBruteForce(testArrays[i], targets[i]);
            long end1 = System.nanoTime();

            // Test Optimal (HashMap)
            long start2 = System.nanoTime();
            int[] res2 = solution.twoSumOptimal(testArrays[i], targets[i]);
            long end2 = System.nanoTime();

            // Test Two Pointers
            long start3 = System.nanoTime();
            int[] res3 = solution.twoSumTwoPointers(testArrays[i], targets[i]);
            long end3 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + Arrays.toString(res1) + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [HashMap]     Output: " + Arrays.toString(res2) + " | Time: " + (end2 - start2) + " ns");
            System.out.println("  [Two Pointer] Output: " + Arrays.toString(res3) + " | Time: " + (end3 - start3) + " ns");
            System.out.println("---------------------------------------------");
        }
    }
}