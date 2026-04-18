package com.questions.strivers.arrays.easy;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement (LeetCode 26: Remove Duplicates from Sorted Array):
 * Given an integer array nums sorted in non-decreasing order, remove the
 * duplicates in-place such that each unique element appears only once. The
 * relative order of the elements should be kept the same. Then return the
 * number of unique elements in nums.
 * * Consider the number of unique elements of nums to be k, to get accepted, you
 * need to do the following things:
 * - Change the array nums such that the first k elements of nums contain the
 * unique elements in the order they were present in nums initially. The
 * remaining elements of nums are not important as well as the size of nums.
 * - Return k.
 * * Constraints:
 * - 1 <= nums.length <= 3 * 10^4
 * - -100 <= nums[i] <= 100
 * - nums is sorted in non-decreasing order.
 * * Examples:
 * ---------
 * Example 1:
 * Input: nums = [1,1,2]
 * Output: 2, nums = [1,2,_]
 * Explanation: Your function should return k = 2, with the first two elements
 * of nums being 1 and 2 respectively. It does not matter what you leave beyond
 * the returned k (hence they are underscores).
 * * Example 2:
 * Input: nums = [0,0,1,1,1,2,2,3,3,4]
 * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
 * Explanation: Your function should return k = 5, with the first five elements
 * of nums being 0, 1, 2, 3, and 4 respectively. It does not matter what you
 * leave beyond the returned k.
 * ============================================================================
 */

public class RemoveDuplicateSortedArray {

    /**
     * ============================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Since the array is already sorted, all duplicate elements are guaranteed
     * to be adjacent to each other. We can exploit this property using a Two-Pointer
     * technique. We maintain a pointer 'i' that points to the position of the last
     * unique element found so far. We use another pointer 'j' to iterate through
     * the array. When nums[j] != nums[i], it means we have found a new unique
     * element. We increment 'i', place the new unique element at nums[i], and
     * continue scanning.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the array. Both pointers
     * traverse the array at most once.
     * - Space Complexity: O(1) auxiliary space. The operation is strictly in-place
     * using only two integer variables. Zero heap space allocated.
     * ============================================================================
     */
    public static int removeDuplicatesOptimal(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 'i' keeps track of the index where the next unique element should be placed.
        int i = 0;

        for (int j = 1; j < nums.length; j++) {
            // If we find an element different from the last unique element...
            if (nums[j] != nums[i]) {
                i++; // Move the unique element pointer forward
                nums[i] = nums[j]; // Update the element in-place
            }
        }

        // 'i' is an index (0-based), so the count of unique elements is i + 1
        return i + 1;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ============================================================================
     * Detailed Intuition:
     * If we ignore the "in-place" requirement momentarily and just focus on removing
     * duplicates, the most intuitive data structure is a Set. Since we need to preserve
     * the original relative order, we use a LinkedHashSet. We insert all elements
     * of the array into the LinkedHashSet. The set automatically drops duplicates.
     * Finally, we iterate through the set and overwrite the beginning of the original
     * array with the unique elements.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Inserting N elements into a LinkedHashSet takes
     * O(N) time on average. Iterating over the set takes O(K) time, where K is
     * the number of unique elements. Total Time: O(N).
     * - Space Complexity: O(N) heap space. The LinkedHashSet requires O(N) memory
     * to store the unique elements in the worst case (if all elements are unique).
     * ============================================================================
     */
    public static int removeDuplicatesBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Use a LinkedHashSet to maintain insertion order
        java.util.LinkedHashSet<Integer> uniqueSet = new java.util.LinkedHashSet<>();

        for (int num : nums) {
            uniqueSet.add(num);
        }

        // Copy unique elements back to the original array
        int index = 0;
        for (int uniqueNum : uniqueSet) {
            nums[index++] = uniqueNum;
        }

        return uniqueSet.size();
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACHES
     * ============================================================================
     * Detailed Intuition:
     * While Phase 1 (Two Pointers) is the absolute optimal for this specific problem
     * constraints, what if the array was NOT sorted?
     * * If Unsorted:
     * - Hashing (Phase 2) would be the go-to O(N) Time / O(N) Space approach.
     * - Sorting the array first takes O(N log N) time and O(log N) to O(N) space
     * (depending on the sorting algorithm), bringing us back to the Phase 1 scenario
     * where we can then apply Two Pointers.
     * * Since the array is guaranteed to be sorted, the Two-Pointer approach strictly
     * dominates all alternatives in both Time and Space.
     * ============================================================================
     */

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thoroughly testing all approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- Testing Best/Optimal Approach (Two Pointers) ---");
        runTests(true);

        System.out.println("\n--- Testing Brute Force Approach (LinkedHashSet) ---");
        runTests(false);
    }

    private static void runTests(boolean useOptimal) {
        // Test Case 1: Standard Case
        int[] test1 = {1, 1, 2};
        verifyAndPrint("Test 1 (Standard)", test1, useOptimal);

        // Test Case 2: LeetCode Example 2
        int[] test2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        verifyAndPrint("Test 2 (Longer Array)", test2, useOptimal);

        // Test Case 3: All elements same
        int[] test3 = {7, 7, 7, 7, 7};
        verifyAndPrint("Test 3 (All Same)", test3, useOptimal);

        // Test Case 4: No duplicates
        int[] test4 = {1, 2, 3, 4, 5};
        verifyAndPrint("Test 4 (All Unique)", test4, useOptimal);

        // Test Case 5: Empty array
        int[] test5 = {};
        verifyAndPrint("Test 5 (Empty Array)", test5, useOptimal);

        // Test Case 6: Single element
        int[] test6 = {42};
        verifyAndPrint("Test 6 (Single Element)", test6, useOptimal);

        // Test Case 7: Negative Numbers
        int[] test7 = {-10, -10, -5, 0, 0, 1, 1};
        verifyAndPrint("Test 7 (Negative Numbers)", test7, useOptimal);
    }

    private static void verifyAndPrint(String testName, int[] arr, boolean useOptimal) {
        // Create a copy of the array so we don't mutate the original across test runs
        int[] copy = java.util.Arrays.copyOf(arr, arr.length);

        int k = useOptimal ? removeDuplicatesOptimal(copy) : removeDuplicatesBruteForce(copy);

        System.out.print(testName + " -> k: " + k + ", Array state: [");
        for (int i = 0; i < copy.length; i++) {
            if (i < k) {
                System.out.print(copy[i]);
            } else {
                System.out.print("_");
            }
            if (i < copy.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}