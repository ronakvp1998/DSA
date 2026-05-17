package com.questions.strivers.arrays.medium;
/**
 * ============================================================================
 * MASTERCLASS: REARRANGE ARRAY ELEMENTS BY SIGN
 * ============================================================================
 * * 2149. Rearrange Array Elements by Sign
 * Solved | Medium | Topics | Companies
 * * * Hint:
 * You are given a 0-indexed integer array nums of even length consisting of an
 * equal number of positive and negative integers.
 * * You should return the array of nums such that the array follows the given conditions:
 * - Every consecutive pair of integers have opposite signs.
 * - For all integers with the same sign, the order in which they were present in nums is preserved.
 * - The rearranged array begins with a positive integer.
 * * Return the modified array after rearranging the elements to satisfy the aforementioned conditions.
 * * * Example 1:
 * Input: nums = [3,1,-2,-5,2,-4]
 * Output: [3,-2,1,-5,2,-4]
 * Explanation:
 * The positive integers in nums are [3,1,2]. The negative integers are [-2,-5,-4].
 * The only possible way to rearrange them such that they satisfy all conditions is [3,-2,1,-5,2,-4].
 * Other ways such as [1,-2,2,-5,3,-4], [3,1,2,-2,-5,-4], [-2,3,-5,1,-4,2] are incorrect
 * because they do not satisfy one or more conditions.
 * * * Example 2:
 * Input: nums = [-1,1]
 * Output: [1,-1]
 * Explanation:
 * 1 is the only positive integer and -1 the only negative integer in nums.
 * So nums is rearranged to [1,-1].
 * * * Constraints:
 * - 2 <= nums.length <= 2 * 10^5
 * - nums.length is even
 * - 1 <= |nums[i]| <= 10^5
 * - nums consists of equal number of positive and negative integers.
 * * It is not required to do the modifications in-place.
 * * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Two-Pointer Placements)
 * ============================================================================
 * Since this is an Array/Two-Pointer problem, a recursion tree isn't applicable.
 * Instead, here is a visualization of the optimal One-Pass approach using pointers.
 * * We know the exact layout of our result array:
 * Indices:   0  1  2  3  4  5
 * Layout:   [+, -, +, -, +, -]
 * * We track two placement indices: `posIdx` starts at 0, `negIdx` starts at 1.
 * As we iterate through `nums`, we route the current number to its designated
 * index and jump that index forward by 2.
 * * Input: [3, 1, -2, -5, 2, -4]
 * * Step 0: posIdx = 0, negIdx = 1, ans = [_, _, _, _, _, _]
 * Step 1: nums[0] = 3  (>0) -> ans[posIdx] = 3, posIdx += 2
 * ans = [3, _, _, _, _, _]  | posIdx=2, negIdx=1
 * Step 2: nums[1] = 1  (>0) -> ans[posIdx] = 1, posIdx += 2
 * ans = [3, _, 1, _, _, _]  | posIdx=4, negIdx=1
 * Step 3: nums[2] = -2 (<0) -> ans[negIdx] = -2, negIdx += 2
 * ans = [3, -2, 1, _, _, _] | posIdx=4, negIdx=3
 * Step 4: nums[3] = -5 (<0) -> ans[negIdx] = -5, negIdx += 2
 * ans = [3, -2, 1, -5, _, _]| posIdx=4, negIdx=5
 * ... and so on until the array is fully populated.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeArrayBySign {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (TWO-PASS / SEPARATION) APPROACH - "Think it"
     * ========================================================================
     * Approach:
     * Iterate through the array and separate all positive numbers into one list
     * and all negative numbers into another list. Because we append to these
     * lists as we iterate, relative order is perfectly preserved.
     * Then, iterate `N/2` times, popping elements from the front of both lists
     * and placing them sequentially (positive, then negative) into a result array.
     * * * Detailed Intuition:
     * The easiest way to preserve relative order without over-complicating index
     * mathematics is to physically separate the elements into two ordered buckets.
     * Once separated, reconstructing the alternating pattern is trivial.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We make one complete pass to segregate the elements (O(N)), and another pass
     * to merge them back together (O(N)). Total = O(2N) which simplifies to O(N).
     * - Space Complexity: O(N)
     * Heap Space: We allocate O(N/2) space for the positives list, O(N/2) for
     * the negatives list, and O(N) for the result array. Total = O(N) heap space.
     * Stack Space: O(1) auxiliary stack space since we use iterative loops.
     */
    public int[] rearrangeArrayBruteForce(int[] nums) {
        int n = nums.length;
        List<Integer> pos = new ArrayList<>();
        List<Integer> neg = new ArrayList<>();

        // Pass 1: Segregate maintaining relative order
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                pos.add(nums[i]);
            } else {
                neg.add(nums[i]);
            }
        }

        // Pass 2: Merge in alternating sequence
        int[] result = new int[n];
        for (int i = 0; i < n / 2; i++) {
            result[2 * i] = pos.get(i);       // Even indices (0, 2, 4...)
            result[2 * i + 1] = neg.get(i);   // Odd indices (1, 3, 5...)
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL ONE-PASS TWO-POINTER APPROACH - "Perfect it"
     * ========================================================================
     * Approach:
     * We pre-allocate the result array of size `n`. We initialize `posIndex` to 0
     * and `negIndex` to 1. We iterate over the original array exactly once.
     * If the current number is positive, we place it at `result[posIndex]` and
     * increment `posIndex` by 2. If it is negative, we place it at `result[negIndex]`
     * and increment `negIndex` by 2.
     * * * Detailed Intuition:
     * The brute-force approach holds the elements in temporary buckets before
     * placing them. But we already know exactly where the 1st, 2nd, and 3rd
     * positive elements will end up (indices 0, 2, 4). By maintaining two separate
     * pointers pointing to the "next available" even and odd slots, we can route
     * the elements to their final destination immediately, skipping the overhead
     * of intermediate Lists and achieving a true single-pass solution.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the `nums` array exactly one time. Array assignments are O(1).
     * - Space Complexity: O(N)
     * Heap Space: O(N) to hold the output `result` array. (Note: The problem
     * states in-place modification is not required. Doing this strictly in-place
     * O(1) space while preserving relative order requires complex right-rotations
     * taking O(N^2) time, which is sub-optimal).
     * Stack Space: O(1) auxiliary stack space.
     */
    public int[] rearrangeArrayOptimal(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        int posIndex = 0; // Positives go to even indices
        int negIndex = 1; // Negatives go to odd indices

        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                result[posIndex] = nums[i];
                posIndex += 2; // Jump to the next even slot
            } else {
                result[negIndex] = nums[i];
                negIndex += 2; // Jump to the next odd slot
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        RearrangeArrayBySign solution = new RearrangeArrayBySign();

        // Define Test Cases
        int[][] testCases = {
                {3, 1, -2, -5, 2, -4},         // Example 1: Standard mix
                {-1, 1},                       // Example 2: Minimal array
                {28, -41, 22, -8, -37, 46},    // Random even length sequence
                {1, 2, 3, -1, -2, -3},         // All positives grouped, then negatives
                {-5, -6, -7, 5, 6, 7}          // All negatives grouped, then positives
        };

        System.out.println("=========================================================");
        System.out.println("Executing Rearrange Array Elements by Sign Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums));

            // 1. Brute Force (Two-Pass)
            long start1 = System.nanoTime();
            int[] res1 = solution.rearrangeArrayBruteForce(nums);
            long end1 = System.nanoTime();

            // 2. Optimal (One-Pass)
            long start2 = System.nanoTime();
            int[] res2 = solution.rearrangeArrayOptimal(nums);
            long end2 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + Arrays.toString(res1) + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Optimal]     Output: " + Arrays.toString(res2) + " | Time: " + (end2 - start2) + " ns");

            // Verification
            boolean isValid = Arrays.equals(res1, res2);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------------------");
        }
    }
}