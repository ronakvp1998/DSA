package com.questions.strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 33. Search in Rotated Sorted Array
 * Level: Medium
 * * Problem Statement:
 * There is an integer array nums sorted in ascending order (with distinct values).
 * Prior to being passed to your function, nums is possibly left rotated at an
 * unknown index k (1 <= k < nums.length) such that the resulting array is
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
 * For example, [0,1,2,4,5,6,7] might be left rotated by 3 indices and become
 * [4,5,6,7,0,1,2].
 * * Given the array nums after the possible rotation and an integer target, return
 * the index of target if it is in nums, or -1 if it is not in nums.
 * * You must write an algorithm with O(log n) runtime complexity.
 * * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 * * Example 3:
 * Input: nums = [1], target = 0
 * Output: -1
 * * Constraints:
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * All values of nums are unique.
 * nums is an ascending array that is possibly rotated.
 * -10^4 <= target <= 10^4
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 * As this is an Array/Search problem (Not DP), we follow the roadmap below:
 * Phase 1: Best and recommended approach (One-Pass Optimized Binary Search)
 * Phase 2: Brute Force approach (Linear Search)
 * Phase 3: Alternative Approaches (Two-Pass Binary Search: Find Pivot -> Search)
 */

public class SearchRotatedArrayI {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (One-Pass Binary Search)
     * ========================================================================
     * Approach:
     * We can achieve O(log n) strictly by modifying standard Binary Search.
     * In a rotated sorted array, if you pick any midpoint, AT LEAST ONE half
     * of the array (either left to mid, or mid to right) will ALWAYS be strictly
     * sorted. We can leverage this property.
     * * Detailed Intuition:
     * 1. Calculate 'mid'. Check if nums[mid] == target. If yes, return 'mid'.
     * 2. Identify the sorted half:
     * - If nums[low] <= nums[mid], the LEFT half is sorted.
     * - Otherwise, the RIGHT half is sorted.
     * 3. Check if the target exists within the bounds of the sorted half.
     * - If left is sorted: Is target >= nums[low] and < nums[mid]?
     * If yes, target is in the left half -> high = mid - 1.
     * Else -> low = mid + 1.
     * - If right is sorted: Is target > nums[mid] and <= nums[high]?
     * If yes, target is in the right half -> low = mid + 1.
     * Else -> high = mid - 1.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). We cut the search space in half each iteration.
     * - Space Complexity: O(1) auxiliary space. Only a few integer variables
     * (low, high, mid) are allocated on the stack. No heap space used.
     */
    public int searchBest(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Check if the left half is sorted
            if (nums[low] <= nums[mid]) {
                // Check if target lies within the sorted left half bounds
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1; // Target is in the left half
                } else {
                    low = mid + 1;  // Target is in the right half
                }
            }
            // Otherwise, the right half MUST be sorted
            else {
                // Check if target lies within the sorted right half bounds
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;  // Target is in the right half
                } else {
                    high = mid - 1; // Target is in the left half
                }
            }
        }

        return -1;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Search) - The "Think it" stage
     * ========================================================================
     * Approach:
     * Iterate through the array elements one by one comparing them to the target.
     * * Detailed Intuition:
     * To solve the problem at a fundamental level without worrying about the
     * O(log n) constraint, we just scan everything. This completely ignores the
     * "sorted and rotated" properties but guarantees we find the element if it
     * exists. Good baseline for correctness validation.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. We might
     * traverse the entire array in the worst case.
     * - Space Complexity: O(1) auxiliary space. Only a loop counter is used.
     */
    public int searchBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Find Pivot -> Normal Binary Search)
     * ========================================================================
     * Approach:
     * Find the index of the smallest element (the pivot/rotation point) first.
     * This index neatly divides the array into two sorted subarrays. Based on
     * the target's value compared to the last element, run a normal Binary
     * Search on the appropriate subarray.
     * * Detailed Intuition:
     * While Phase 1 does everything in one pass, this two-pass approach is often
     * easier for candidates to conceptualize initially.
     * 1. Binary Search to find the minimum element.
     * 2. Determine which half (0 to pivot-1, or pivot to n-1) the target could
     * belong to by comparing target with nums[end].
     * 3. Run standard Binary Search on that specific half.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). We do two consecutive binary searches.
     * O(log N) + O(log N) = O(log N).
     * - Space Complexity: O(1) auxiliary space. No extra array or stack frames.
     */
    public int searchAlternative(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int n = nums.length;
        int low = 0, high = n - 1;

        // Pass 1: Find the index of the minimum element (pivot)
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        int pivot = low;

        // Determine which side of the pivot to perform the standard Binary Search
        low = 0;
        high = n - 1;

        // If target is less than or equal to the last element, it must be on
        // the right side of the pivot (or is the pivot). Else, left side.
        if (target >= nums[pivot] && target <= nums[high]) {
            low = pivot;
        } else {
            high = pivot - 1;
        }

        // Pass 2: Standard Binary Search
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering normal cases, unrotated arrays, and edge cases.
     */
    public static void main(String[] args) {
        SearchRotatedArrayI solution = new SearchRotatedArrayI();

        int[][] testArrays = {
                {4, 5, 6, 7, 0, 1, 2}, // Standard rotated, target in right half
                {4, 5, 6, 7, 0, 1, 2}, // Standard rotated, target missing
                {1},                   // Edge Case: Single element missing
                {1},                   // Edge Case: Single element found
                {1, 3},                // Edge Case: Two elements, rotated
                {5, 1, 3},             // Edge Case: Small odd size
                {1, 2, 3, 4, 5, 6}     // Edge Case: Not rotated at all
        };

        int[] targets = {0, 3, 0, 1, 3, 5, 4};
        int[] expectedAnswers = {4, -1, -1, 0, 1, 0, 3};

        System.out.println("==========================================================");
        System.out.println("Executing Testing Suite for Search in Rotated Array");
        System.out.println("==========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int target = targets[i];
            int expected = expectedAnswers[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array:    " + java.util.Arrays.toString(nums));
            System.out.println("Target:   " + target);

            int res1 = solution.searchBest(nums, target);
            int res2 = solution.searchBruteForce(nums, target);
            int res3 = solution.searchAlternative(nums, target);

            System.out.println("Expected  : " + expected);
            System.out.println("Phase 1 (Optimal) : " + res1);
            System.out.println("Phase 2 (Brute)   : " + res2);
            System.out.println("Phase 3 (Alternative): " + res3);

            if(res1 == expected && res2 == expected && res3 == expected) {
                System.out.println("Status: PASS ✅");
            } else {
                System.out.println("Status: FAIL ❌ (Mismatch in outputs)");
            }
            System.out.println("----------------------------------------------------------");
        }
    }
}