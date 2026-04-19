package com.questions.strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 81. Search in Rotated Sorted Array II
 * Level: Medium
 * * Problem Statement:
 * There is an integer array nums sorted in non-decreasing order (not necessarily
 * with distinct values).
 * Before being passed to your function, nums is rotated at an unknown pivot index k
 * (0 <= k < nums.length) such that the resulting array is
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
 * For example, [0,1,2,4,4,4,5,6,6,7] might be rotated at pivot index 5 and
 * become [4,5,6,6,7,0,1,2,4,4].
 * * Given the array nums after the rotation and an integer target, return true if target
 * is in nums, or false if it is not in nums.
 * * You must decrease the overall operation steps as much as possible.
 * * Examples:
 * Example 1:
 * Input: nums = [2,5,6,0,0,1,2], target = 0
 * Output: true
 * * Example 2:
 * Input: nums = [2,5,6,0,0,1,2], target = 3
 * Output: false
 * * Constraints:
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums is guaranteed to be rotated at some pivot.
 * -10^4 <= target <= 10^4
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 * As this is an Array/Search problem (Not DP), we follow the roadmap below:
 * Phase 1: Best and recommended approach (Optimized Binary Search)
 * Phase 2: Brute Force approach (Linear Search)
 * Phase 3: Alternative Approaches (Recursive Binary Search)
 */

public class SearchRotatedArrayII {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Binary Search)
     * ========================================================================
     * Approach:
     * We use a modified Binary Search. Since the array is rotated, at least
     * one half of the array (left or right of mid) will always be sorted.
     * We can determine which half is sorted by comparing nums[low] and nums[mid].
     * The tricky part in this variation (unlike version I) is the presence of
     * duplicate elements. If nums[low] == nums[mid] == nums[high], we cannot
     * reliably determine which half is sorted. In this edge case, we shrink
     * the search space by incrementing low and decrementing high.
     * * Detailed Intuition:
     * 1. Calculate mid. If nums[mid] == target, return true.
     * 2. If we hit the duplicate edge case (nums[low] == nums[mid] == nums[high]),
     * we safely shrink the boundaries (low++, high--).
     * 3. If the left half is sorted (nums[low] <= nums[mid]):
     * - Check if the target lies within this sorted left half. If so, move
     * the high pointer to mid - 1. Otherwise, move the low pointer to mid + 1.
     * 4. If the right half is sorted (nums[mid] <= nums[high]):
     * - Check if the target lies within this sorted right half. If so, move
     * the low pointer to mid + 1. Otherwise, move the high pointer to mid - 1.
     * * Complexity Analysis:
     * - Time Complexity: O(log N) average case, O(N) worst case. The worst case
     * occurs when all elements are duplicates (e.g., [3,3,3,3,3,3]) and the
     * algorithm degrades to shrinking the bounds one by one.
     * - Space Complexity: O(1) auxiliary space. No additional heap or stack
     * space is used.
     */
    public boolean searchBest(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;

        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Target found
            if (nums[mid] == target) {
                return true;
            }

            // Handle duplicates: Cannot determine which half is sorted
            if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
                low++;
                high--;
                continue;
            }

            // Left half is sorted
            if (nums[low] <= nums[mid]) {
                // Target is in the sorted left half
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    // Target is in the right half
                    low = mid + 1;
                }
            }
            // Right half is sorted
            else {
                // Target is in the sorted right half
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    // Target is in the left half
                    high = mid - 1;
                }
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Search) - The "Think it" stage
     * ========================================================================
     * Approach:
     * Iterate through every element in the array and check if it matches the
     * target. This completely ignores the "sorted and rotated" property of
     * the array.
     * * Detailed Intuition:
     * This is the most basic guarantee to find an element if it exists.
     * It serves as a good baseline to verify the logic of our optimized
     * solutions during testing.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the number of elements in the array.
     * We might have to scan the entire array in the worst case.
     * - Space Complexity: O(1) auxiliary space. Only loop counters are used.
     */
    public boolean searchBruteForce(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Recursive Binary Search)
     * ========================================================================
     * Approach:
     * The exact same logic as Phase 1, but implemented using recursion instead
     * of a while loop.
     * * Detailed Intuition:
     * We pass the 'low' and 'high' boundaries as arguments to a helper function.
     * This demonstrates a functional/recursive mindset which can sometimes be
     * preferred in specific interview settings or languages, though it carries
     * stack overhead.
     * * Complexity Analysis:
     * - Time Complexity: O(log N) average case, O(N) worst case (same as Phase 1).
     * - Space Complexity: O(log N) average, up to O(N) worst case auxiliary
     * stack space due to recursive calls. No extra heap space.
     */
    public boolean searchAlternative(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;
        return recursiveSearch(nums, target, 0, nums.length - 1);
    }

    private boolean recursiveSearch(int[] nums, int target, int low, int high) {
        if (low > high) {
            return false;
        }

        int mid = low + (high - low) / 2;

        if (nums[mid] == target) {
            return true;
        }

        if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
            return recursiveSearch(nums, target, low + 1, high - 1);
        }

        if (nums[low] <= nums[mid]) {
            if (nums[low] <= target && target < nums[mid]) {
                return recursiveSearch(nums, target, low, mid - 1);
            } else {
                return recursiveSearch(nums, target, mid + 1, high);
            }
        } else {
            if (nums[mid] < target && target <= nums[high]) {
                return recursiveSearch(nums, target, mid + 1, high);
            } else {
                return recursiveSearch(nums, target, low, mid - 1);
            }
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering normal cases, edge cases, and the tricky
     * duplicate scenarios.
     */
    public static void main(String[] args) {
        SearchRotatedArrayII solution = new SearchRotatedArrayII();

        // Test Cases Setup
        int[][] testArrays = {
                {2, 5, 6, 0, 0, 1, 2}, // Example 1 & 2
                {1, 0, 1, 1, 1},       // Edge Case: Lots of duplicates, target in middle
                {1, 1, 1, 1, 1, 1, 1}, // Edge Case: All duplicates
                {1},                   // Edge Case: Single element
                {2, 2, 2, 3, 2, 2, 2}, // Edge Case: Target surrounded by duplicates
                {}                     // Edge Case: Empty array
        };

        int[] targets = {0, 3, 0, 2, 1, 3, 3, 5};
        // Mapping targets to specific arrays for testing clarity
        int[] targetsToTest = {
                0, // For array 1: should be true
                3, // For array 1: should be false
                0, // For array 2: should be true
                2, // For array 3: should be false
                1, // For array 4: should be true
                3, // For array 5: should be true
                5  // For array 6: should be false
        };

        System.out.println("==========================================================");
        System.out.println("Executing Testing Suite for Search in Rotated Array II");
        System.out.println("==========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int target = targetsToTest[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array:  " + java.util.Arrays.toString(nums));
            System.out.println("Target: " + target);

            boolean res1 = solution.searchBest(nums, target);
            boolean res2 = solution.searchBruteForce(nums, target);
            boolean res3 = solution.searchAlternative(nums, target);

            System.out.println("Phase 1 (Optimal) : " + res1);
            System.out.println("Phase 2 (Brute)   : " + res2);
            System.out.println("Phase 3 (Recurse) : " + res3);

            if(res1 == res2 && res2 == res3) {
                System.out.println("Status: PASS ✅");
            } else {
                System.out.println("Status: FAIL ❌ (Mismatch in approach outputs)");
            }
            System.out.println("----------------------------------------------------------");
        }
    }
}