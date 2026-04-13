package com.questions.strivers.arrays.hard;

import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: 4 SUM (Find Unique Quadruplets That Add Up To Target)
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array nums of n integers, return an array of all the unique quadruplets
 * [nums[a], nums[b], nums[c], nums[d]] such that:
 * - 0 <= a, b, c, d < n
 * - a, b, c, and d are distinct.
 * - nums[a] + nums[b] + nums[c] + nums[d] == target
 * You may return the answer in any order. The solution set must not contain
 * duplicate quadruplets.
 * * Input/Output Formats:
 * Input: An array of integers `nums` and an integer `target`.
 * Output: A List of Lists containing the unique integer quadruplets.
 * * Constraints (Standard LeetCode 18):
 * - 1 <= nums.length <= 200
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 *
 * * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * * Example 2:
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 *
 * * Conceptual Visualization (Sorting + Two Pointers Approach):
 * Target = 0
 * Original Array: [1, 0, -1, 0, -2, 2]
 * Sorted Array:   [-2, -1, 0, 0, 1, 2]
 *                  ^    ^  ^        ^
 *                  i    j left    right
 * * By fixing 'i' and 'j' with two outer loops, we reduce the 4Sum problem
 * into a standard 2Sum problem for the remaining array, which is solved
 * efficiently using two pointers ('left' and 'right') moving inwards.
 * ============================================================================
 */
public class Sum4Problem {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Sorting + Two Pointers)
     * ========================================================================
     * Approach and Steps:
     * 1. Sort the array. Sorting is mandatory to effectively use two pointers
     * and to easily skip duplicate elements to ensure unique quadruplets.
     * 2. Use an outer loop 'i' from 0 to n-4. Skip duplicates for 'i'.
     * 3. Use an inner loop 'j' from i+1 to n-3. Skip duplicates for 'j'.
     * 4. For the remaining elements, use two pointers: left = j + 1, right = n - 1.
     * 5. Calculate the sum of elements at i, j, left, and right.
     * *CRITICAL*: Cast to 'long' during addition to prevent Integer Overflow
     * because nums[i] and target can be up to 10^9.
     * 6. If sum == target, record the quadruplet, move left and right pointers
     * inwards, and strictly bypass any duplicate values.
     * 7. If sum < target, increment left pointer. If sum > target, decrement right.
     *
     * * Detailed Intuition:
     * 4Sum is an extension of 3Sum. Instead of fixing one element, we fix two
     * elements using nested loops, reducing the remaining search space to a 1D
     * array where the Two-Pointer technique thrives. Sorting elegantly solves the
     * "unique combinations only" constraint by grouping identical numbers together.
     * * Complexity Analysis:
     * - Time (O): O(N log N) for sorting + O(N^3) for the nested loops = O(N^3).
     * - Space (O): O(1) auxiliary space (excluding the output list). The sorting
     * algorithm (e.g., Dual-Pivot Quicksort) may use O(log N) to O(N) stack space.
     * ========================================================================
     */
    public static List<List<Integer>> fourSumOptimal(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        if (nums == null || n < 4) return result;

        Arrays.sort(nums); // Step 1: Sort to enable two-pointers & duplicate skipping

        for (int i = 0; i < n - 3; i++) {
            // Step 2: Skip duplicates for the 1st element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < n - 2; j++) {
                // Step 3: Skip duplicates for the 2nd element
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    // Step 5: Prevent overflow by casting to long
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // Step 6: Skip duplicates for the 3rd and 4th elements
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;

                        // Move inward after finding a valid quadruplet
                        left++;
                        right--;
                    } else if (sum < target) {
                        left++; // Need a larger sum
                    } else {
                        right--; // Need a smaller sum
                    }
                }
            }
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Use four nested loops to generate every possible quadruplet (i, j, k, l).
     * 2. Sum the four elements (using long to avoid overflow).
     * 3. If the sum equals the target, sort the quadruplet (to ensure identical
     * combinations map to the same signature).
     * 4. Store the sorted quadruplet in a HashSet to automatically filter duplicates.
     * * Detailed Intuition:
     * This is the baseline combinatorial approach. We blindly generate all nC4
     * combinations. Because order does not matter for the combinations, and we
     * want unique sets, we sort each found subset and rely on a Set for uniqueness.
     * * Complexity Analysis:
     * - Time (O): O(N^4 * log(unique_quads)). N^4 to traverse all combinations.
     * Sorting 4 elements is O(1), but inserting into the HashSet involves hashing
     * the list, adding overhead.
     * - Space (O): O(unique_quads) heap space to store the Set of Lists. Auxiliary
     * stack space is O(1).
     * ========================================================================
     */
    public static List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
        Set<List<Integer>> uniqueQuads = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                        if (sum == target) {
                            List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(temp);
                            uniqueQuads.add(temp);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(uniqueQuads);
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (3 Loops + Hashing)
     * ========================================================================
     * Approach and Steps:
     * 1. Loop through the array fixing the first two elements ('i' and 'j').
     * 2. Initialize a HashSet for the innermost loop.
     * 3. Loop a third variable 'k' from j+1 to n.
     * 4. Calculate what the 4th element needs to be: needed = target - (nums[i]+nums[j]+nums[k]).
     * 5. Check if 'needed' exists in the HashSet.
     * 6. If it does, a valid quadruplet is found. Sort it and add to a global Set.
     * 7. Add nums[k] to the HashSet for subsequent 'k' iterations.
     * * Detailed Intuition:
     * We optimize the brute force by dropping the 4th nested loop. By keeping
     * a running HashSet of elements we've seen in the innermost iteration, we
     * can look up the required 4th number in O(1) time. This trades memory for speed.
     * * Complexity Analysis:
     * - Time (O): O(N^3 * log(unique_quads)). The nested loops take O(N^3). Hashing
     * lookup is O(1). Global set insertion adds slight logarithmic overhead.
     * - Space (O): O(N) auxiliary space for the inner HashSet + O(unique_quads)
     * for the global answer Set.
     * ========================================================================
     */
    public static List<List<Integer>> fourSumHashing(int[] nums, int target) {
        Set<List<Integer>> uniqueQuads = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                Set<Long> hashSet = new HashSet<>();
                for (int k = j + 1; k < n; k++) {
                    long currentSum = (long) nums[i] + nums[j] + nums[k];
                    long needed = target - currentSum;

                    if (hashSet.contains(needed)) {
                        List<Integer> temp = Arrays.asList(nums[i], nums[j], (int) needed, nums[k]);
                        Collections.sort(temp);
                        uniqueQuads.add(temp);
                    }
                    hashSet.add((long) nums[k]);
                }
            }
        }
        return new ArrayList<>(uniqueQuads);
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * Tests all implementations against standard arrays, identical elements,
     * edge-case lengths, and massive integer overflow scenarios.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== 4 SUM MASTERCLASS TESTING SUITE ===");

        // Test Case Data Structure: { target, ...arrayElements }
        // We package the target as the first element for easy iteration.
        int[][] testCases = {
                {0,   1, 0, -1, 0, -2, 2},         // Standard Case 1
                {8,   2, 2, 2, 2, 2},              // All duplicates
                {0,   0, 0, 0, 0},                 // All zeroes
                {0,   -3, -1, 0, 2, 4, 5},         // No valid quadruplets
                {0,   1, 2, -3},                   // Edge Case: Too few elements (< 4)
                {-294967296, 1000000000, 1000000000, 1000000000, 1000000000} // Integer Overflow Case
        };

        for (int i = 0; i < testCases.length; i++) {
            int target = testCases[i][0];
            int[] tc = Arrays.copyOfRange(testCases[i], 1, testCases[i].length);

            System.out.println("\nTest Case " + (i + 1) + ": Target = " + target + ", Array = " + Arrays.toString(tc));

            // Test Brute Force (Skip overflow case for Brute Force/Hashing as they are slow/memory intensive)
            List<List<Integer>> res1 = new ArrayList<>();
            if (tc.length <= 10) {
                long start1 = System.nanoTime();
                res1 = fourSumBruteForce(tc.clone(), target);
                long end1 = System.nanoTime();
                System.out.println("Brute Force Output: " + res1 + " (Time: " + (end1 - start1) / 1000 + " us)");
            }

            // Test Hashing
            List<List<Integer>> res2 = new ArrayList<>();
            if (tc.length <= 10) {
                long start2 = System.nanoTime();
                res2 = fourSumHashing(tc.clone(), target);
                long end2 = System.nanoTime();
                System.out.println("Hashing Output:     " + res2 + " (Time: " + (end2 - start2) / 1000 + " us)");
            }

            // Test Optimal (Two Pointers)
            long start3 = System.nanoTime();
            List<List<Integer>> res3 = fourSumOptimal(tc.clone(), target);
            long end3 = System.nanoTime();
            System.out.println("Optimal Output:     " + res3 + " (Time: " + (end3 - start3) / 1000 + " us)");

            // Verification (only for small sets where BF/Hash ran)
            if (tc.length <= 10) {
                boolean isMatch = res1.size() == res2.size() && res2.size() == res3.size();
                System.out.println("Sanity Check Passed: " + isMatch);
            }
        }
    }
}