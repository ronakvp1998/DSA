package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * 🎯 MASTERCLASS: Product of Array Except Self
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 * * * Formal Problem Statement (LeetCode 238):
 * Given an integer array nums, return an array answer such that answer[i] is
 * equal to the product of all the elements of nums except nums[i].
 * * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * * You must write an algorithm that runs in O(n) time and without using the division operation.
 *
 * Constraints:
 * - 2 <= nums.length <= 10^5
 * - -30 <= nums[i] <= 30
 * - The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 *
 * Follow up: Can you solve the problem in O(1) extra space complexity? (The output
 * array does not count as extra space for space complexity analysis.)
 *
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 *
 * Example 2:
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 *
 * * Conceptual Visualization (Prefix & Suffix Products):
 * Instead of recursion, this problem relies on states of accumulated products.
 * Array:       [   1,      2,      3,      4   ]
 * Left Prods:  [   1,      1,      2,      6   ] (Product of elements to the left)
 * Right Prods: [  24,     12,      4,      1   ] (Product of elements to the right)
 * Result:      [ 1*24,   1*12,    2*4,    6*1  ] -> [24, 12, 8, 6]
 *
 * ============================================================================
 * ### 2.2 Progressive Implementation Roadmap
 * * Phase 1: Brute Force Approach - The "Think it" stage.
 * -> Iterate through the array. For every element, iterate through the rest of
 * the array to calculate the product.
 * * Phase 2: Prefix & Suffix Arrays - The "Refine it" stage.
 * -> Precompute all left products and right products into two separate arrays.
 * Multiply them together for the final answer.
 * * Phase 3: Space Optimized Two-Pass - The "Perfect it" stage.
 * -> Use the result array to store left products. Keep a running right product
 * variable to multiply backwards, achieving O(1) extra space.
 * ============================================================================
 */
import java.util.Arrays;

public class ProductOfArrayExceptSelf {

    /**
     * ============================================================================
     * Phase 1: Brute Force approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to follow the definition exactly.
     * For every index `i`, we calculate the product of all `j` where `i != j`.
     * This establishes our baseline correctness but ignores the O(n) constraint.
     *
     * Complexity Analysis:
     * Time (O): O(n^2) - Nested loops; for each element, we traverse the array again.
     * Space (O): O(1) auxiliary space (excluding the output array). O(1) stack space.
     */
    public static int[] phase1BruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }
            result[i] = product;
        }

        return result;
    }

    /**
     * ============================================================================
     * Phase 2: Prefix and Suffix Arrays - The "Refine it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The brute force calculates the same prefix and suffix products repeatedly.
     * We can trade space for time by precomputing the product of all elements to
     * the left of `i` and storing it in `left[i]`, and all elements to the right
     * of `i` in `right[i]`. The answer at `i` is simply `left[i] * right[i]`.
     *
     * Complexity Analysis:
     * Time (O): O(n) - Three independent passes over the array of size n.
     * Space (O): O(n) heap space for the `left` and `right` arrays. O(1) stack.
     */
    public static int[] phase2PrefixSuffixArrays(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] result = new int[n];

        // Base cases for boundaries
        left[0] = 1;
        right[n - 1] = 1;

        // Build left products
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        // Build right products
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        // Combine
        for (int i = 0; i < n; i++) {
            result[i] = left[i] * right[i];
        }

        return result;
    }

    /**
     * ============================================================================
     * Phase 3: Space Optimized Two-Pass - The "Perfect it" stage.
     * ============================================================================
     * Detailed Intuition:
     * We notice that we don't strictly need two separate arrays. We can use the
     * `result` array to store the left products. Then, instead of building a `right`
     * array, we can iterate backwards through the array maintaining a running
     * `rightProduct` variable, multiplying it with the `result` array in place.
     *
     * Complexity Analysis:
     * Time (O): O(n) - Two passes over the array.
     * Space (O): O(1) extra heap space. We only use a single integer variable.
     * (Note: The return array does not count towards space complexity per the prompt).
     */
    public static int[] phase3SpaceOptimized(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Step 1: Calculate left products directly into the result array
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // Step 2: Calculate right product on the fly and multiply with result
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightProduct;
            rightProduct *= nums[i]; // Update running right product for the next iteration
        }

        return result;
    }

    /**
     * ============================================================================
     * ### 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Running Test Suite for Product of Array Except Self...");
        System.out.println("----------------------------------------------------------\n");

        // Test Cases format: {input_array, expected_output_array}
        int[][][] testCases = {
                // Standard case (No zeros)
                {{1, 2, 3, 4}, {24, 12, 8, 6}},

                // Contains one zero
                {{-1, 1, 0, -3, 3}, {0, 0, 9, 0, 0}},

                // Contains multiple zeros
                {{0, 4, 0}, {0, 0, 0}},

                // Two elements (Minimum constraint)
                {{5, 8}, {8, 5}},

                // Negative numbers
                {{-2, -3, -4, -5}, {-60, -40, -30, -24}}
        };

        boolean allPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int[] expected = testCases[i][1];

            int[] res1 = phase1BruteForce(nums);
            int[] res2 = phase2PrefixSuffixArrays(nums);
            int[] res3 = phase3SpaceOptimized(nums);

            boolean pass = Arrays.equals(res1, expected) &&
                    Arrays.equals(res2, expected) &&
                    Arrays.equals(res3, expected);

            System.out.printf("Test %d: Input: %s\n", i + 1, Arrays.toString(nums));
            System.out.printf("  Expected        : %s\n", Arrays.toString(expected));
            System.out.printf("  Brute Force     : %s\n", Arrays.toString(res1));
            System.out.printf("  Prefix/Suffix   : %s\n", Arrays.toString(res2));
            System.out.printf("  Space Optimized : %s\n", Arrays.toString(res3));
            System.out.printf("  Status: %s\n\n", pass ? "✅ PASS" : "❌ FAIL");

            if (!pass) allPassed = false;
        }

        System.out.println("----------------------------------------------------------");
        System.out.println(allPassed ? "🎉 ALL TESTS PASSED!" : "⚠️ SOME TESTS FAILED.");
    }
}