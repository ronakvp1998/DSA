package com.questions.strivers.arrays.hard;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: MAXIMUM PRODUCT SUBARRAY
 * ============================================================================
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an integer array nums, find a subarray that has the largest product,
 * and return the product. The test cases are generated so that the answer
 * will fit in a 32-bit integer.
 * Note that the product of an array with a single element is the value of that element.
 * * Constraints:
 * - 1 <= nums.length <= 2 * 10^4
 * - -10 <= nums[i] <= 10
 * - The product of any subarray of nums is guaranteed to fit in a 32-bit integer.
 * * Input: An array of N integers.
 * Output: An integer representing the maximum product of any contiguous subarray.
 * * Example 1:
 * Input: nums = [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 * * Example 2:
 * Input: nums = [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a contiguous subarray.
 * * Conceptual Visualization (Prefix/Suffix Logic & The Zero/Negative Dilemma):
 * Array: [ 2,  3, -2,  4, -1]
 * * The challenge lies in negative numbers and zeros:
 * - A single negative number ruins a positive product.
 * - TWO negative numbers multiplied together create a massive positive product!
 * - A zero instantly destroys the product (resets it to 0).
 * * If the array has NO zeros:
 * - Even count of negative numbers: The whole array is the max product.
 * - Odd count of negative numbers: The max product is either the prefix up to
 * the LAST negative number, OR the suffix starting from the FIRST negative number.
 * * Thus, computing the product from Left-to-Right (Prefix) and Right-to-Left (Suffix)
 * guarantees we capture the maximum possible contiguous stretch without getting
 * trapped by the "odd negative" boundary!
 * ============================================================================
 */
public class MaxProdSubArray {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Prefix and Suffix Accumulation)
     * ========================================================================
     * Approach and Steps:
     * 1. Initialize `maxProduct` to the smallest possible integer.
     * 2. Initialize `prefix` and `suffix` product trackers to 1.
     * 3. Loop through the array from i = 0 to n - 1.
     * 4. If `prefix` or `suffix` becomes 0, reset it to 1 (because 0 breaks
     * the contiguous product chain, starting a new subarray).
     * 5. Multiply `prefix` by nums[i] and `suffix` by nums[n - 1 - i].
     * 6. Update `maxProduct` with the maximum of its current value, `prefix`,
     * and `suffix`.
     * 7. Return `maxProduct`.
     * * Detailed Intuition:
     * This is the most elegant approach. By sweeping from left to right, we
     * evaluate all contiguous subarrays starting from boundaries broken by 0.
     * By sweeping right to left simultaneously, we evaluate the reverse.
     * If there's an odd number of negatives, the "bad" negative element will
     * either be sliced off by the prefix iteration (dropping the right end)
     * or the suffix iteration (dropping the left end). Zeros simply act as
     * natural boundaries that reset our product trackers.
     * * Complexity Analysis:
     * - Time (O): O(N). We traverse the array exactly once, performing constant
     * time operations at each step.
     * - Space (O): O(1) auxiliary space. We only use a few primitive variables
     * allocated on the stack. No heap space is consumed.
     * ========================================================================
     */
    public static int maxProductOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int maxProduct = Integer.MIN_VALUE;
        int prefix = 1;
        int suffix = 1;

        for (int i = 0; i < n; i++) {
            // Reset to 1 if we hit a zero in the previous step
            if (prefix == 0) prefix = 1;
            if (suffix == 0) suffix = 1;

            prefix = prefix * nums[i];
            suffix = suffix * nums[n - 1 - i];

            maxProduct = Math.max(maxProduct, Math.max(prefix, suffix));
        }

        return maxProduct;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Initialize `maxProduct` to Integer.MIN_VALUE.
     * 2. Use an outer loop `i` to represent the start of the subarray.
     * 3. Initialize a `currentProduct` to 1.
     * 4. Use an inner loop `j` from `i` to n - 1 to expand the subarray.
     * 5. Multiply `currentProduct` by nums[j].
     * 6. Continuously update `maxProduct` with the maximum of itself and `currentProduct`.
     * * Detailed Intuition:
     * To guarantee we find the maximum product, we systematically generate every
     * possible contiguous subarray. By fixing a starting point and progressively
     * multiplying the next element, we can evaluate all subarrays starting at `i`.
     * While inefficient, it provides absolute correctness and builds the baseline
     * understanding that a subarray is fundamentally continuous.
     * * Complexity Analysis:
     * - Time (O): O(N^2). Two nested loops generate and evaluate all N*(N+1)/2 subarrays.
     * - Space (O): O(1) auxiliary stack space. No extra arrays are created.
     * ========================================================================
     */
    public static int maxProductBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxProduct = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int currentProduct = 1;
            for (int j = i; j < n; j++) {
                currentProduct *= nums[j];
                maxProduct = Math.max(maxProduct, currentProduct);
            }
        }

        return maxProduct;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Kadane's DP Variation - Min/Max Tracking)
     * ========================================================================
     * Approach and Steps:
     * 1. Maintain three variables: `ans` (global max), `currMax` (max product
     * ending at current index), and `currMin` (min product ending at current index).
     * 2. Initialize all three to nums[0].
     * 3. Iterate from i = 1 to n - 1.
     * 4. If nums[i] is negative, multiplying it by `currMax` makes it a minimum,
     * and multiplying it by `currMin` makes it a maximum! Thus, we SWAP `currMax`
     * and `currMin` before multiplying.
     * 5. Update `currMax` = Math.max(nums[i], currMax * nums[i]).
     * 6. Update `currMin` = Math.min(nums[i], currMin * nums[i]).
     * 7. Update `ans` = Math.max(ans, currMax).
     * * Detailed Intuition:
     * This is a Space-Optimized Dynamic Programming approach (an extension of
     * Kadane's Algorithm for max sum). The key insight: a very large negative
     * number (minimum product) can instantly become a massive positive number
     * (maximum product) if multiplied by another negative number. Therefore,
     * at every step, we MUST track BOTH the maximum possible product AND the
     * minimum possible product ending at that index.
     * * Complexity Analysis:
     * - Time (O): O(N). Single pass through the array.
     * - Space (O): O(1) auxiliary stack space. We only keep track of the DP
     * state for the current and previous element using primitive variables.
     * ========================================================================
     */
    public static int maxProductKadanesVariation(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int ans = nums[0];
        int currMax = nums[0];
        int currMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            // If the current number is negative, min becomes max and max becomes min
            if (num < 0) {
                int temp = currMax;
                currMax = currMin;
                currMin = temp;
            }

            // Should we start a new subarray at `num` or extend the existing one?
            currMax = Math.max(num, currMax * num);
            currMin = Math.min(num, currMin * num);

            // Update global maximum
            ans = Math.max(ans, currMax);
        }

        return ans;
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * sequences, edge cases with zeros, multiple negatives, and single elements.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== MAXIMUM PRODUCT SUBARRAY MASTERCLASS TESTING SUITE ===\n");

        int[][] testCases = {
                {2, 3, -2, 4},           // Standard case 1
                {-2, 0, -1},             // Standard case 2 (Zero boundary)
                {-2},                    // Single negative element
                {0, 2, 0},               // Zeros surrounding a positive
                {-2, 3, -4},             // Odd negatives vs Even negatives
                {0, -3, 1, 1, -2, 0},    // Multiple zero boundaries with two negatives
                {-1, -2, -3, -4},        // All negative numbers
                {1, 2, 3, 4, 5}          // All positive numbers
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Test Brute Force
            long start1 = System.nanoTime();
            int res1 = maxProductBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output:     " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // Test DP (Kadane's Variation)
            long start2 = System.nanoTime();
            int res2 = maxProductKadanesVariation(tc);
            long end2 = System.nanoTime();
            System.out.println("DP (Min/Max) Output:    " + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Test Optimal (Prefix/Suffix)
            long start3 = System.nanoTime();
            int res3 = maxProductOptimal(tc);
            long end3 = System.nanoTime();
            System.out.println("Optimal Prefix/Suffix:  " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = (res1 == res2) && (res2 == res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(60) + "\n");
        }
    }
}