package com.questions.strivers.bitmanipulation.advancemaths;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Find the two numbers appearing odd number of times
 * ==================================================================================================
 * Given an array nums of length n, every integer appears twice except for two integers.
 * Identify and return these two integers in ascending order.
 *
 * Example 1:
 * Input: nums = [1, 2, 1, 3, 5, 2]
 * Output: [3, 5]
 *
 * Example 2:
 * Input: nums = [-1, 0]
 * Output: [-1, 0]
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Frequency Hashing)
 * ==================================================================================================
 * We can use a HashMap to store the frequency of each element. After a single pass,
 * we iterate through the map to find the two elements with a frequency of 1.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation - XOR Partitioning)
 * ==================================================================================================
 * 1. XOR all numbers: The result (X) will be (Num1 ^ Num2) because all pairs cancel out.
 * 2. Find the Rightmost Set Bit: Since Num1 and Num2 are distinct, at least one bit
 * in their XOR result must be 1. This bit represents a position where Num1 and Num2 differ.
 * 3. Partition into Groups: Divide the numbers into two groups based on whether
 * they have this specific bit set.
 * - Group 1: Numbers with the bit set.
 * - Group 2: Numbers with the bit not set.
 * 4. XOR each group: The pairs in each group will cancel out, leaving the two
 * unique numbers isolated in their respective groups.
 * ==================================================================================================
 */

import java.util.*;

public class TwoUniqueNumbers {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 1, 3, 5, 2};
        System.out.println("Test Case 1 (1,2,1,3,5,2):");
        System.out.println("Optimal Result: " + Arrays.toString(findTwoUniqueOptimal(nums1)));

        int[] nums2 = {-1, 0};
        System.out.println("Test Case 2 (-1, 0):");
        System.out.println("Optimal Result: " + Arrays.toString(findTwoUniqueOptimal(nums2)));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (HashMap)
     * ----------------------------------------------------------------------
     * Logic: Use a frequency map to count occurrences.
     * Complexity: Time O(N), Space O(N).
     */
    public static int[] findTwoUniqueBruteForce(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int[] result = new int[2];
        int idx = 0;
        for (int num : map.keySet()) {
            if (map.get(num) % 2 != 0) {
                result[idx++] = num;
            }
        }
        Arrays.sort(result); // Ascending order
        return result;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. XOR all elements to get Num1 ^ Num2.
     * 2. Find the rightmost set bit to distinguish between Num1 and Num2.
     * 3. XOR elements into two separate groups.
     */
    public static int[] findTwoUniqueOptimal(int[] nums) {
        int xorAll = 0;

        // Step 1: XOR all elements
        for (int num : nums) {
            xorAll ^= num;
        }

        // Step 2: Find the rightmost set bit
        // xorAll & -xorAll is a trick to isolate the lowest set bit.
        // Example: if xorAll is 6 (110), rightmostSetBit becomes 2 (010).
        int rightmostSetBit = xorAll & -xorAll;

        int num1 = 0;
        int num2 = 0;

        // Step 3: Partition the numbers into two groups and XOR them separately
        for (int num : nums) {
            if ((num & rightmostSetBit) != 0) {
                // If the bit is set at that position, XOR into num1
                num1 ^= num;
            } else {
                // If the bit is not set at that position, XOR into num2
                num2 ^= num;
            }
        }

        // Final result needs to be sorted in ascending order
        int[] result = {num1, num2};
        Arrays.sort(result);
        return result;
    }
}