package com.questions.strivers.bitmanipulation.advancemaths;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Find the two numbers appearing odd number of times
 * ==================================================================================================
 * Given an array nums where every integer appears twice except for two integers, identify
 * and return the two integers that appear only once. Return them in ascending order.
 *
 * Example 1:
 * Input: nums = [1, 2, 1, 3, 5, 2]
 * Output: [3, 5]
 *
 * Example 2:
 * Input: nums = [-1, 0]
 * Output: [-1, 0]
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Hashing)
 * ==================================================================================================
 * Use a HashMap to store the frequency of each number. Iterate through the map and
 * collect numbers with a frequency of 1. Sort the result before returning.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation / XOR Partitioning)
 * ==================================================================================================
 * 1. XOR all elements: The result (xorAll) will be (Num1 ^ Num2) because pairs cancel out.
 * 2. Find the Rightmost Set Bit: Since Num1 and Num2 are different, at least one bit
 * in their XOR result must be 1. We find the rightmost bit that is 1.
 * 3. Partition: This set bit exists in one of our target numbers but not the other.
 * We divide all numbers in the array into two groups:
 * - Group A: Numbers that have this specific bit set.
 * - Group B: Numbers that do NOT have this specific bit set.
 * 4. XOR each group: XORing Group A will yield Num1; XORing Group B will yield Num2.
 * ==================================================================================================
 */

import java.util.*;

public class TwoOddAppearing {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 1, 3, 5, 2};
        System.out.println("Test Case 1 (Brute Force): " + Arrays.toString(findTwoUniqueBruteForce(nums1)));
        System.out.println("Test Case 1 (Optimal):     " + Arrays.toString(findTwoUniqueOptimal(nums1)));

        int[] nums2 = {-1, 0};
        System.out.println("\nTest Case 2 (Optimal):     " + Arrays.toString(findTwoUniqueOptimal(nums2)));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Frequency Map)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Traverse array to count frequencies.
     * 2. Filter keys where value == 1.
     * 3. Sort for ascending order.
     */
    public static int[] findTwoUniqueBruteForce(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        int[] result = new int[2];
        int idx = 0;
        for (int key : map.keySet()) {
            if (map.get(key) == 1) {
                result[idx++] = key;
            }
        }
        Arrays.sort(result);
        return result;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (XOR Partitioning)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. xorAll will be the XOR of the two unique numbers.
     * 2. (xorAll & -xorAll) isolates the lowest set bit.
     * 3. This bit acts as a "differentiator" to split the two unique numbers.
     */
    public static int[] findTwoUniqueOptimal(int[] nums) {
        // Step 1: XOR all elements
        int xorAll = 0;
        for (int n : nums) {
            xorAll ^= n;
        }

        // Step 2: Isolate the rightmost set bit
        // This bit is 1 where the two target numbers differ in binary.
        int rightmostSetBit = xorAll & -xorAll;

        // Step 3: Partition the numbers into two groups and XOR them
        int num1 = 0;
        int num2 = 0;
        for (int n : nums) {
            // Check if the specific bit is set in the current number
            if ((n & rightmostSetBit) != 0) {
                num1 ^= n; // Group A: Bit is 1
            } else {
                num2 ^= n; // Group B: Bit is 0
            }
        }

        // Step 4: Sort the final two numbers
        int[] result = {num1, num2};
        Arrays.sort(result);
        return result;
    }
}