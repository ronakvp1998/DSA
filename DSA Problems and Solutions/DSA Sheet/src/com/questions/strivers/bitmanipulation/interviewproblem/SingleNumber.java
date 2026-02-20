package com.questions.strivers.bitmanipulation.interviewproblem;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 136. Single Number (Easy)
 * ==================================================================================================
 * Given a non-empty array of integers nums, every element appears twice except for one.
 * Find that single one.
 * * Requirement: Implement a solution with a linear runtime complexity O(n) and
 * use only constant extra space O(1).
 *
 * Example 1:
 * Input: nums = [2,2,1] -> Output: 1
 *
 * Example 2:
 * Input: nums = [4,1,2,1,2] -> Output: 4
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Frequency Map / HashSet)
 * ==================================================================================================
 * We can use a HashMap to count the frequency of each number. After counting, we iterate
 * through the map to find the key with a frequency of 1.
 * Alternatively, use a HashSet: if the number is already in the set, remove it;
 * otherwise, add it. The remaining element in the set is the single number.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation using XOR)
 * ==================================================================================================
 * The Bitwise XOR (^) operator has three critical properties:
 * 1. n ^ 0 = n (Identity)
 * 2. n ^ n = 0 (Self-Inverse)
 * 3. a ^ b ^ a = (a ^ a) ^ b = 0 ^ b = b (Commutative and Associative)
 * * If we XOR every number in the array together, the pairs will cancel each other out
 * to become 0, leaving only the unique number behind.
 * ==================================================================================================
 */

import java.util.HashMap;
import java.util.Map;

public class SingleNumber {

    public static void main(String[] args) {
        // Test Case 1
        int[] nums1 = {4, 1, 2, 1, 2};
        System.out.println("Test Case 1 [4,1,2,1,2]:");
        System.out.println("Brute Force : " + findSingleBruteForce(nums1));
        System.out.println("Optimal     : " + findSingleOptimal(nums1));
        System.out.println("--------------------------------------------------");

        // Test Case 2
        int[] nums2 = {2, 2, 1};
        System.out.println("Test Case 2 [2,2,1]:");
        System.out.println("Brute Force : " + findSingleBruteForce(nums2));
        System.out.println("Optimal     : " + findSingleOptimal(nums2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (HashMap)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Traverse the array and store the count of each element in a map.
     * 2. Traverse the map and return the element whose value is 1.
     * * Drawback: This violates the O(1) space complexity requirement.
     */
    public static int findSingleBruteForce(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();

        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return -1; // Should not reach here per constraints
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation / XOR)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Initialize a variable `result` to 0.
     * 2. Iterate through every number in the array and XOR it with `result`.
     * 3. Because XOR is commutative, the order doesn't matter. All pairs
     * eventually XOR themselves into 0.
     * 4. 0 ^ singleNumber = singleNumber.
     */
    public static int findSingleOptimal(int[] nums) {
        int result = 0;

        for (int num : nums) {
            // XORing result with the current number
            result = result ^ num;
        }

        return result;
    }
}