package com.questions.strivers.arrays.hard;

import java.util.*;

/**
 * Problem:
 * Find all unique quadruplets (4 elements) in the array that sum up to the given target.
 *
 * Input:  arr = [1, 0, -1, 0, -2, 2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Note: Quadruplets must not be repeated.
 */
public class Sum4Problem {

    /**
     * ✅ Method 1: Brute Force + Set to Remove Duplicates
     *
     * Approach:
     * - Try all combinations of 4 elements using 4 nested loops.
     * - Check if their sum equals the target.
     * - Store the sorted quadruplet in a HashSet to avoid duplicates.
     *
     * Time Complexity: O(n⁴ * log(4)) ≈ O(n⁴) due to 4 nested loops
     * Space Complexity: O(k) where k is number of unique quadruplets
     */
    private static List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        Set<List<Integer>> set = new HashSet<>();

        // Try every combination of 4 elements
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        // Use long to avoid overflow
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                        if (sum == target) {
                            // Sort to avoid duplicate permutations
                            List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(temp);
                            set.add(temp); // HashSet removes duplicates
                        }
                    }
                }
            }
        }

        // Convert HashSet to List
        return new ArrayList<>(set);
    }

    /**
     * ✅ Method 2: Hashing + Reduced Time
     *
     * Approach:
     * - Fix first 2 elements using 2 nested loops.
     * - For the rest, use a HashSet to find the 4th element as: target - (i + j + k)
     * - Sort and store result in Set to remove duplicates.
     *
     * Time Complexity: O(n³ * log(4)) ≈ O(n³)
     * Space Complexity: O(n + k)
     *   - O(n) for HashSet
     *   - O(k) for result set
     */
    private static List<List<Integer>> fourSum2(int[] nums, int target) {
        int n = nums.length;
        Set<List<Integer>> st = new HashSet<>();

        // Fix first and second elements
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<Long> hashset = new HashSet<>();
                // Find the remaining two numbers using HashSet
                for (int k = j + 1; k < n; k++) {
                    long sum = (long) nums[i] + nums[j] + nums[k];
                    long fourth = (long) target - sum;
                    if (hashset.contains(fourth)) {
                        List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], (int) fourth);
                        temp.sort(Integer::compareTo); // Sort to handle duplicates
                        st.add(temp); // HashSet removes duplicates
                    }
                    hashset.add((long) nums[k]); // Store for future lookup
                }
            }
        }

        return new ArrayList<>(st);
    }

    /**
     * ✅ Method 3: Two Pointers (Optimal Approach)
     *
     * Approach:
     * - Sort the array
     * - Fix first and second numbers using 2 loops
     * - Use 2 pointers (`k`, `l`) to find the remaining two numbers
     * - Skip duplicates
     *
     * Time Complexity: O(n³)
     *   - 2 nested loops + 2 pointers = O(n² * n) = O(n³)
     * Space Complexity: O(k)
     *   - For storing result list
     */
    private static List<List<Integer>> fourSum3(int[] nums, int target) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        Arrays.sort(nums); // Sort for two-pointer and duplicate handling

        for (int i = 0; i < n; i++) {
            // Skip duplicate `i`
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < n; j++) {
                // Skip duplicate `j`
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int k = j + 1;
                int l = n - 1;

                // Two pointer search between j+1 and end
                while (k < l) {
                    long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                    if (sum == target) {
                        // Found valid quadruplet
                        ans.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                        k++;
                        l--;

                        // Skip duplicate `k`
                        while (k < l && nums[k] == nums[k - 1]) k++;

                        // Skip duplicate `l`
                        while (k < l && nums[l] == nums[l + 1]) l--;
                    } else if (sum < target) {
                        k++; // Need larger sum
                    } else {
                        l--; // Need smaller sum
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {4, 3, 3, 4, 4, 2, 1, 2, 1, 1};
        int target = 9;

        // You can call any of the three methods
        List<List<Integer>> ans = fourSum3(nums, target); // Optimal method

        System.out.println("The quadruplets are: ");
        for (List<Integer> it : ans) {
            System.out.print("[");
            for (int ele : it) {
                System.out.print(ele + " ");
            }
            System.out.print("] ");
        }
        System.out.println();
    }
}
