package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: SUBARRAY SUM EQUALS K
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * 560. Subarray Sum Equals K
 * Solved | Medium | Topics: Array, Hash Table, Prefix Sum | Companies
 * * Hint:
 * Given an array of integers `nums` and an integer `k`, return the total number
 * of subarrays whose sum equals to `k`.
 * A subarray is a contiguous non-empty sequence of elements within an array.
 * * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 * * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 * * Constraints:
 * - 1 <= nums.length <= 2 * 10^4
 * - -1000 <= nums[i] <= 1000
 * - -10^7 <= k <= 10^7
 * * ---
 * * ### Conceptual Visualization (Prefix Sum + Hash Map)
 * * Since this is not a DP problem, we don't use a recursion tree. Instead, let's
 * visualize the optimal Prefix Sum + Hashing approach.
 * * Why does this work?
 * If the cumulative sum up to index `i` is `Sum_i`, and the cumulative sum up to
 * some previous index `j` is `Sum_j`, then the sum of the subarray from `j+1`
 * to `i` is exactly `Sum_i - Sum_j`.
 * * We want: `Sum_i - Sum_j = k`
 * Rearranged: `Sum_j = Sum_i - k`
 * * This means: As we iterate through the array maintaining a running sum (`Sum_i`),
 * we just need to look back and ask: "Have we ever seen a running sum equal to
 * `runningSum - k`?" If yes, a valid subarray exists! We use a HashMap to count
 * exactly *how many times* we've seen that `Sum_j` in the past.
 * * Visualization for nums = [3, 4, 7, 2, -3, 1, 4, 2], k = 7
 * * Step | Num | RunningSum (RS) | Target (RS - k) | Map Contains Target? | Map State Updates
 * ----------------------------------------------------------------------------------------
 * 0   |  -  |       0         |      N/A        |        N/A           | {0: 1}  <- Base case!
 * 1   |  3  |       3         |   3 - 7 = -4    | No                   | {0: 1, 3: 1}
 * 2   |  4  |       7         |   7 - 7 = 0     | Yes! (+1 to answer)  | {0: 1, 3: 1, 7: 1}
 * 3   |  7  |      14         |  14 - 7 = 7     | Yes! (+1 to answer)  | {0: 1, 3: 1, 7: 1, 14: 1}
 * 4   |  2  |      16         |  16 - 7 = 9     | No                   | {0: 1, 3: 1, 7: 1, 14: 1, 16: 1}
 * ... and so on.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CountSubarraySum {

    /**
     * ========================================================================
     * ### 2.2. Progressive Implementation Roadmap
     * * Phase 1: Brute Force approach - The "Think it" stage.
     * Approach: We calculate the sum of every possible subarray. We use two nested
     * loops where the outer loop fixes the start index, and the inner loop extends
     * the end index, maintaining a running sum to avoid a third loop.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * The simplest way to find all subarrays is to literally generate them all.
     * By keeping a running tally in the inner loop, we efficiently calculate the
     * sum of subarray `nums[i...j]` in O(1) time using the sum of `nums[i...j-1]`.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) where N is the length of the array. We evaluate
     * every possible contiguous sequence.
     * - Space Complexity: O(1). Only a few integer variables are allocated in
     * heap/stack space.
     * ========================================================================
     */
    public int subarraySumBruteForce(int[] nums, int k) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            // Extend the subarray ending at j
            for (int j = i; j < n; j++) {
                currentSum += nums[j];

                // Check if the current contiguous sum equals our target
                if (currentSum == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * ========================================================================
     * Phase 2: Alternative Approach (Prefix Sum + Hash Map) - The "Perfect it" stage.
     * Approach: We iterate through the array exactly once, calculating a cumulative
     * prefix sum. We use a HashMap to store the frequencies of every prefix sum
     * we have seen so far. If `prefixSum - k` exists in our map, we add its
     * frequency to our total count.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * Why not use a Sliding Window / Two Pointers? Because the constraints state
     * that `nums[i]` can be negative! A sliding window relies on the assumption
     * that expanding the window increases the sum and shrinking it decreases the sum.
     * Negative numbers break this monotonicity.
     * Therefore, the Prefix Sum with a Hash Map is the absolute optimal way to
     * track historical sums in O(1) lookup time, completely eliminating the
     * need for the O(N^2) nested loop.
     * * Complexity Analysis:
     * - Time Complexity: O(N). We traverse the array exactly once. HashMap
     * insertions and lookups take O(1) time on average.
     * - Space Complexity: O(N). We allocate heap space for the HashMap, which
     * in the worst case (all distinct prefix sums) will store N distinct
     * key-value pairs. Auxiliary stack space is O(1).
     * ========================================================================
     */
    public int subarraySumOptimal(int[] nums, int k) {
        int count = 0;
        int runningSum = 0;

        // Map stores: <PrefixSum, Frequency>
        Map<Integer, Integer> prefixSumMap = new HashMap<>();

        // CRITICAL BASE CASE:
        // We initialize the map with (0, 1) because a running sum that perfectly
        // equals 'k' early on means (runningSum - k) will be 0.
        // This accounts for subarrays that start precisely at index 0.
        prefixSumMap.put(0, 1);

        for (int num : nums) {
            runningSum += num;

            // Check if we've seen a prefix sum that leaves exactly 'k' remaining
            int target = runningSum - k;
            if (prefixSumMap.containsKey(target)) {
                // Add the frequency of how many times we've seen this target
                count += prefixSumMap.get(target);
            }

            // Update the map with the current running sum
            prefixSumMap.put(runningSum, prefixSumMap.getOrDefault(runningSum, 0) + 1);
        }

        return count;
    }

    /**
     * ========================================================================
     * ### 4. Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        CountSubarraySum solution = new CountSubarraySum();

        // Define Test Cases
        int[][] testArrays = {
                {1, 1, 1},                      // Example 1
                {1, 2, 3},                      // Example 2
                {3, 4, 7, 2, -3, 1, 4, 2},      // Complex mix of positives and negatives
                {0, 0, 0, 0, 0},                // Edge Case: Zeroes (overlapping targets)
                {-1, -1, 1},                    // Edge Case: Negatives
                {1000}                          // Edge Case: Single element
        };
        int[] targets = {2, 3, 7, 0, 0, 1000};

        System.out.println("=========================================================");
        System.out.println("Executing Subarray Sum Equals K Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int k = targets[i];
            System.out.println("Test Case " + (i + 1) + ": nums = " + Arrays.toString(nums) + ", k = " + k);

            // Test Brute Force
            long start1 = System.nanoTime();
            int res1 = solution.subarraySumBruteForce(nums, k);
            long end1 = System.nanoTime();

            // Test Optimal Hash Map
            long start2 = System.nanoTime();
            int res2 = solution.subarraySumOptimal(nums, k);
            long end2 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Optimal]     Output: " + res2 + " | Time: " + (end2 - start2) + " ns");

            // Verification
            boolean isValid = (res1 == res2);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------------------");
        }
    }
}