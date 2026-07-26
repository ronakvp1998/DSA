package strivers.arrays.hard;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement: Merge two Sorted Arrays Without Extra Space (LeetCode 88)
 *
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
 * and two integers m and n, representing the number of elements in nums1 and nums2
 * respectively.
 *
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 *
 * The final sorted array should not be returned by the function, but instead be
 * stored inside the array nums1. To accommodate this, nums1 has a length of m + n,
 * where the first m elements denote the elements that should be merged, and the
 * last n elements are set to 0 and should be ignored. nums2 has a length of n.
 *
 * Constraints:
 * - nums1.length == m + n
 * - nums2.length == n
 * - 0 <= m, n <= 200
 * - 1 <= m + n <= 200
 * - -10^9 <= nums1[i], nums2[j] <= 10^9
 *
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
 * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
 *
 * Example 2:
 * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
 * Output: [1]
 * Explanation: The arrays we are merging are [] and [1].
 * The result of the merge is [1].
 * Note that because m = 0, there are no elements in nums1. The 0 is only there to
 * ensure the merge result can fit in nums1.
 * ============================================================================
 */

import java.util.Arrays;

public class MergeSortedArrays {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Provided Code)
     * ============================================================================
     * Detailed Intuition:
     * If we start merging from the front (left-to-right), we would overwrite
     * the existing elements in nums1 before we have a chance to evaluate them.
     * However, nums1 has exactly 'n' empty spaces (zeros) at its end.
     * By utilizing three pointers and starting from the BACK (right-to-left),
     * we guarantee that we are always placing the largest remaining element into
     * a space that is already a "buffer" zero, or into a space whose original
     * value has already been safely moved to the right.
     *
     * We only strictly need to loop until 'nums2' is fully placed (p2 >= 0).
     * If nums1 runs out first (p1 < 0), we just copy the rest of nums2.
     * If nums2 runs out first (p2 < 0), the remaining elements in nums1 are
     * already in their correct sorted positions!
     *
     * Complexity Analysis:
     * - Time Complexity: O(m + n) -> We traverse both arrays exactly once.
     * - Space Complexity: O(1) auxiliary space -> Modifications are strictly in-place.
     */
    public static void mergeOptimal(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;

        // We only need to iterate until nums2 is fully merged.
        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ============================================================================
     * Detailed Intuition:
     * The most naive way to solve this is to completely ignore the fact that
     * the arrays are already sorted. Since nums1 has enough space at the end
     * to hold all elements of nums2, we can simply copy all elements of nums2
     * into the trailing zeros of nums1. Once both arrays are combined into one,
     * we use a built-in sorting algorithm to sort the entire nums1 array.
     *
     * Complexity Analysis:
     * - Time Complexity: O((m + n) log(m + n)) -> Dominated by the sorting step.
     * - Space Complexity: O(1) or O(log(m + n)) -> Depending on the underlying
     *   sorting algorithm (Java uses Dual-Pivot Quicksort for primitives which
     *   takes logarithmic stack space).
     */
    public static void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {
        // Step 1: Copy nums2 into the end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }

        // Step 2: Sort the combined array
        Arrays.sort(nums1);
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Forward Two-Pointers with Extra Space)
     * ============================================================================
     * Detailed Intuition:
     * If we were not constrained by the O(1) space requirement, the standard
     * way to merge two sorted arrays is to use two pointers starting from the
     * front (index 0). We compare the elements, pick the smaller one, and put it
     * into a new results array. Once we finish, we copy the results array back
     * into nums1. This shows how a standard merge step in MergeSort works.
     *
     * Complexity Analysis:
     * - Time Complexity: O(m + n) -> We visit each element once.
     * - Space Complexity: O(m + n) auxiliary heap space -> We create a temporary
     *   array of size m + n to hold the merged elements.
     */
    public static void mergeAlternative(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m + n];
        int p1 = 0;
        int p2 = 0;
        int p = 0;

        // Traverse both arrays from the front
        while (p1 < m && p2 < n) {
            if (nums1[p1] <= nums2[p2]) {
                temp[p++] = nums1[p1++];
            } else {
                temp[p++] = nums2[p2++];
            }
        }

        // Collect any remaining elements from nums1
        while (p1 < m) {
            temp[p++] = nums1[p1++];
        }

        // Collect any remaining elements from nums2
        while (p2 < n) {
            temp[p++] = nums2[p2++];
        }

        // Copy the sorted temp array back into nums1
        for (int i = 0; i < m + n; i++) {
            nums1[i] = temp[i];
        }
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("Starting Test Suite for Merge Sorted Arrays...\n");

        // Test Case 1: Standard case with interleaving elements
        runTest("Test Case 1 (Standard)",
                new int[]{1, 2, 3, 0, 0, 0}, 3,
                new int[]{2, 5, 6}, 3,
                "[1, 2, 2, 3, 5, 6]");

        // Test Case 2: nums1 is effectively empty (m = 0)
        runTest("Test Case 2 (nums1 empty)",
                new int[]{0}, 0,
                new int[]{1}, 1,
                "[1]");

        // Test Case 3: nums2 is effectively empty (n = 0)
        runTest("Test Case 3 (nums2 empty)",
                new int[]{1}, 1,
                new int[]{}, 0,
                "[1]");

        // Test Case 4: nums1 elements are all greater than nums2 elements
        runTest("Test Case 4 (nums2 entirely before nums1)",
                new int[]{4, 5, 6, 0, 0, 0}, 3,
                new int[]{1, 2, 3}, 3,
                "[1, 2, 3, 4, 5, 6]");

        // Test Case 5: nums2 elements are all greater than nums1 elements
        runTest("Test Case 5 (nums1 entirely before nums2)",
                new int[]{1, 2, 3, 0, 0, 0}, 3,
                new int[]{4, 5, 6}, 3,
                "[1, 2, 3, 4, 5, 6]");
    }

    /**
     * Helper method to run a test case across all three implementations to
     * ensure absolute correctness and consistency.
     */
    private static void runTest(String testName, int[] nums1, int m, int[] nums2, int n, String expected) {
        System.out.println("--- " + testName + " ---");

        // Clone initial state for unbiased testing across different methods
        int[] nums1Brute = nums1.clone();
        int[] nums1Alt = nums1.clone();
        int[] nums1Opt = nums1.clone();

        // Execute Brute Force
        mergeBruteForce(nums1Brute, m, nums2, n);
        System.out.println("Brute Force : " + Arrays.toString(nums1Brute));

        // Execute Alternative
        mergeAlternative(nums1Alt, m, nums2, n);
        System.out.println("Alternative : " + Arrays.toString(nums1Alt));

        // Execute Optimal
        mergeOptimal(nums1Opt, m, nums2, n);
        System.out.println("Optimal     : " + Arrays.toString(nums1Opt));

        // Verification validation
        boolean passed = Arrays.toString(nums1Opt).equals(expected);
        System.out.println("Status      : " + (passed ? "PASS" : "FAIL (Expected: " + expected + ")"));
        System.out.println();
    }
}