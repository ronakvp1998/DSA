package com.questions.strivers.bitmanipulation.interviewproblem;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 78. Subsets (Medium)
 * ==================================================================================================
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [[],[0]]
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Recursive Backtracking)
 * ==================================================================================================
 * We can use recursion to explore every possibility: for each number, we either include
 * it in the current subset or we don't. This builds a state-space tree with 2^n leaves.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation / Bitmasking)
 * ==================================================================================================
 * A set with 'n' elements has 2^n total subsets (including the empty set).
 * We can map each subset to a binary number from 0 to (2^n - 1).
 * For an array of size 3 (indices 0, 1, 2), the binary number 5 (101) represents a subset
 * where the elements at index 0 and 2 are included, and index 1 is excluded.
 *
 * Binary   Subset
 * 000  ->  []
 * 001  ->  [nums[0]]
 * 010  ->  [nums[1]]
 * 011  ->  [nums[0], nums[1]]
 * ... and so on.
 * ==================================================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};

        System.out.println("Input: [1, 2, 3]");

        // Testing Bit Manipulation Approach
        List<List<Integer>> resultBit = subsetsBitManipulation(nums);
        System.out.println("Subsets (Bit Manipulation): " + resultBit);

        // Testing Backtracking Approach
        List<List<Integer>> resultBacktrack = subsetsBacktracking(nums);
        System.out.println("Subsets (Backtracking): " + resultBacktrack);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BIT MANIPULATION (Optimal for small N)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Total subsets = 2^n. In code, this is (1 << n).
     * 2. Loop from 0 to (2^n - 1). Each 'i' is a bitmask.
     * 3. For each bitmask, check which bits are set to 1.
     * 4. If the j-th bit is set, include nums[j] in the current subset.
     */
    public static List<List<Integer>> subsetsBitManipulation(int[] nums) {
        int n = nums.length;
        int totalSubsets = 1 << n; // 2^n
        List<List<Integer>> result = new ArrayList<>();

        // Iterate through all possible binary combinations from 0 to 2^n - 1
        for (int i = 0; i < totalSubsets; i++) {
            List<Integer> currentSubset = new ArrayList<>();

            // Check each bit of the current number 'i'
            for (int j = 0; j < n; j++) {
                // If the j-th bit of 'i' is set (1), include nums[j]
                if ((i & (1 << j)) != 0) {
                    currentSubset.add(nums[j]);
                }
            }
            result.add(currentSubset);
        }
        return result;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: BACKTRACKING (Standard Recursive Approach)
     * ----------------------------------------------------------------------
     */
    public static List<List<Integer>> subsetsBacktracking(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums, int start) {
        // Add the current path to the result
        result.add(new ArrayList<>(tempList));

        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]); // Include nums[i]
            backtrack(result, tempList, nums, i + 1); // Move to next
            tempList.remove(tempList.size() - 1); // Backtrack (exclude nums[i])
        }
    }
}