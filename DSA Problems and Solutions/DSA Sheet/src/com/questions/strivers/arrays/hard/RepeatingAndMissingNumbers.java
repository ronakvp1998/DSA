package com.questions.strivers.arrays.hard;

/**
 * ============================================================================
 * 🎯 MASTERCLASS: Find the Repeating and Missing Numbers
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 * * Formal Problem Statement:
 * You are given a read-only array of N integers with values in the range [1, N].
 * Each integer appears exactly once except A which appears twice and B which is missing.
 * Return A and B.
 * Note: Your algorithm should have a linear runtime complexity. Could you implement it
 * without using extra memory?
 *
 * Constraints:
 * - 2 <= N <= 10^5
 * - 1 <= array[i] <= N
 * - There is exactly one missing number and one repeating number.
 *
 * Input/Output Formats:
 * - Input: An integer array `nums`.
 * - Output: An integer array of size 2 containing {repeating_number, missing_number}.
 *
 * Example 1:
 * Input:  nums = {3, 1, 2, 5, 3}
 * Output: {3, 4}
 * Explanation: 3 is repeated, 4 is missing.
 *
 * Example 2:
 * Input:  nums = {3, 1, 2, 4, 3}
 * Output: {3, 5}
 * Explanation: 3 is repeated, 5 is missing.
 *
 * Example 3:
 * Input: nums = {2, 2}
 * Output: {2, 1}
 * Explanation: 2 is repeated, 1 is missing.
 * ============================================================================
 */
import java.util.Arrays;

public class RepeatingAndMissingNumbers {

    /**
     * ============================================================================
     * Phase 1: Best and recommended approach - The Math Approach
     * ============================================================================
     * Detailed Intuition:
     * We can use basic algebra to solve this in O(N) time and O(1) space.
     * Let X be the repeating number and Y be the missing number.
     * 1. Sum of first N numbers: Sn = (N * (N + 1)) / 2
     * 2. Sum of squares of first N numbers: S2n = (N * (N + 1) * (2N + 1)) / 6
     * 3. Let S be the actual sum of the array, and S2 be the actual sum of squares.
     * * We can form two equations:
     * Equation 1: S - Sn = X - Y
     * Equation 2: S2 - S2n = X^2 - Y^2
     * * We know X^2 - Y^2 = (X - Y) * (X + Y).
     * Substituting Equation 1 into Equation 2 gives us (X + Y).
     * Once we have (X - Y) and (X + Y), we can solve for X and Y using simple addition.
     * *CRITICAL:* We must use 64-bit integers (`long`) to prevent arithmetic overflow
     * when calculating the sum and sum of squares for large inputs (N up to 10^5).
     *
     * Complexity Analysis:
     * Time (O): O(N) - We iterate through the array exactly once.
     * Space (O): O(1) - Only a few primitive long variables are allocated in the
     * auxiliary stack space. No heap space is used.
     */
    public static int[] phase1OptimalMath(int[] nums) {
        long n = nums.length;

        // Sum of 1 to N
        long SN = (n * (n + 1)) / 2;
        // Sum of squares of 1 to N
        long S2N = (n * (n + 1) * (2 * n + 1)) / 6;

        long S = 0;
        long S2 = 0;

        // Calculate actual sum and sum of squares
        for (int i = 0; i < n; i++) {
            S += nums[i];
            S2 += (long) nums[i] * (long) nums[i];
        }

        // S - SN = X - Y
        long val1 = S - SN;

        // S2 - S2N = X^2 - Y^2
        long val2 = S2 - S2N;

        // X - Y = val1
        // val2 = (X - Y)(X + Y) => (X + Y) = val2 / (X - Y)
        long val3 = val2 / val1;

        // Now we have:
        // x2 - y2 = val2
        // X - Y = val1
        // X + Y = val3
        // 2X = val1 + val3 => X = (val1 + val3) / 2
        long x = (val1 + val3) / 2;
        long y = x - val1;

        return new int[]{(int) x, (int) y};
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way is to pick every number from 1 to N and count
     * how many times it appears in the given array.
     * - If the count is 2, we found the repeating number.
     * - If the count is 0, we found the missing number.
     * Once both are found, we can terminate early.
     *
     * Complexity Analysis:
     * Time (O): O(N^2) - For each number from 1 to N, we iterate through the entire
     * array of size N.
     * Space (O): O(1) - Constant auxiliary stack space. No extra heap space.
     */
    public static int[] phase2BruteForce(int[] nums) {
        int n = nums.length;
        int repeating = -1;
        int missing = -1;

        for (int i = 1; i <= n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (nums[j] == i) {
                    count++;
                }
            }
            if (count == 2) repeating = i;
            if (count == 0) missing = i;

            // Early exit if both are found
            if (repeating != -1 && missing != -1) {
                break;
            }
        }
        return new int[]{repeating, missing};
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approaches - Hashing (Frequency Array)
     * ============================================================================
     * Detailed Intuition:
     * To improve the O(N^2) time of the brute force approach, we can trade space
     * for time. We can use a frequency array of size N+1 initialized to zeros.
     * We iterate through the given array and increment the count of each number
     * in our frequency array. Finally, we iterate from 1 to N in our frequency
     * array to find the values with counts of 2 (repeating) and 0 (missing).
     *
     * Complexity Analysis:
     * Time (O): O(N) - One pass to build the frequency array, one pass to scan it.
     * Space (O): O(N) - We allocate a frequency array of size N+1 on the heap.
     */
    public static int[] phase3AlternativeHashing(int[] nums) {
        int n = nums.length;
        int[] hash = new int[n + 1]; // Heap allocation

        int repeating = -1;
        int missing = -1;

        // Build frequency map
        for (int i = 0; i < n; i++) {
            hash[nums[i]]++;
        }

        // Identify repeating and missing
        for (int i = 1; i <= n; i++) {
            if (hash[i] == 2) repeating = i;
            else if (hash[i] == 0) missing = i;

            if (repeating != -1 && missing != -1) break;
        }

        return new int[]{repeating, missing};
    }

    /**
     * ============================================================================
     * Phase 3 (Bonus): Alternative Approaches - Bit Manipulation (XOR)
     * ============================================================================
     * Detailed Intuition:
     * This is another O(1) space approach, immune to the integer overflow issues
     * of the Math approach.
     * 1. XOR all elements in the array and all numbers from 1 to N. The result is
     * `X XOR Y` (where X is repeating, Y is missing).
     * 2. Find the rightmost set bit in `X XOR Y`. This bit differs between X and Y.
     * 3. Divide all numbers (both array elements and 1 to N) into two buckets based
     * on whether this bit is set or not.
     * 4. XORing all elements in each bucket isolates X and Y.
     * 5. Finally, do a linear scan to check which of the two isolated numbers is
     * actually in the array (that one is repeating, the other is missing).
     *
     * Complexity Analysis:
     * Time (O): O(N) - Constant number of linear passes.
     * Space (O): O(1) - Primitive bitwise variables on the stack.
     */
    public static int[] phase3AlternativeXor(int[] nums) {
        int n = nums.length;
        int xor = 0;

        // Step 1: Get X XOR Y
        for (int i = 0; i < n; i++) {
            xor ^= nums[i];
            xor ^= (i + 1);
        }

        // Step 2: Get rightmost set bit
        int rightmostSetBit = xor & ~(xor - 1);

        // Step 3 & 4: Bucket elements and XOR them
        int zeroBucket = 0;
        int oneBucket = 0;

        for (int i = 0; i < n; i++) {
            // Bucket array elements
            if ((nums[i] & rightmostSetBit) != 0) {
                oneBucket ^= nums[i];
            } else {
                zeroBucket ^= nums[i];
            }

            // Bucket numbers from 1 to N
            if (((i + 1) & rightmostSetBit) != 0) {
                oneBucket ^= (i + 1);
            } else {
                zeroBucket ^= (i + 1);
            }
        }

        // Step 5: Identify which is repeating and which is missing
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == zeroBucket) {
                count++;
            }
        }

        if (count == 2) {
            return new int[]{zeroBucket, oneBucket};
        }
        return new int[]{oneBucket, zeroBucket};
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Running Test Suite for Find Repeating and Missing Numbers...");
        System.out.println("---------------------------------------------------------------\n");

        // Format: {input_array, expected_repeating, expected_missing}
        int[][][] testCases = {
                {{3, 1, 2, 5, 3}, {3, 4}}, // Standard case
                {{3, 1, 2, 4, 3}, {3, 5}}, // Missing at end
                {{2, 2}, {2, 1}},          // Minimal constraint case
                {{1, 2, 3, 4, 4}, {4, 5}}, // Repeating at the end
                {{1, 1, 2, 3, 4}, {1, 5}}, // Repeating at the beginning
                {{2, 3, 1, 5, 4, 3}, {3, 6}} // Randomized order
        };

        boolean allPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int[] expected = testCases[i][1];

            // Create copies to prevent any accidental modification during tests
            int[] res1 = phase1OptimalMath(nums.clone());
            int[] res2 = phase2BruteForce(nums.clone());
            int[] res3 = phase3AlternativeHashing(nums.clone());
            int[] res4 = phase3AlternativeXor(nums.clone());

            boolean pass = Arrays.equals(res1, expected) &&
                    Arrays.equals(res2, expected) &&
                    Arrays.equals(res3, expected) &&
                    Arrays.equals(res4, expected);

            System.out.printf("Test %d: Input: %s\n", i + 1, Arrays.toString(nums));
            System.out.printf("  Expected        : %s\n", Arrays.toString(expected));
            System.out.printf("  Optimal Math    : %s\n", Arrays.toString(res1));
            System.out.printf("  Brute Force     : %s\n", Arrays.toString(res2));
            System.out.printf("  Hashing Appr.   : %s\n", Arrays.toString(res3));
            System.out.printf("  XOR Approach    : %s\n", Arrays.toString(res4));
            System.out.printf("  Status: %s\n\n", pass ? "✅ PASS" : "❌ FAIL");

            if (!pass) allPassed = false;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println(allPassed ? "🎉 ALL TESTS PASSED!" : "⚠️ SOME TESTS FAILED.");
    }
}