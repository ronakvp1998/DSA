package com.questions.strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 4. Median of Two Sorted Arrays
 * Solved | Hard
 * * * PROBLEM STATEMENT:
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return
 * the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 * * * EXAMPLES:
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 * * Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 * * * CONSTRAINTS:
 * - nums1.length == m
 * - nums2.length == n
 * - 0 <= m <= 1000
 * - 0 <= n <= 1000
 * - 1 <= m + n <= 2000
 * - -10^6 <= nums1[i], nums2[i] <= 10^6
 * ============================================================================
 */
public class MedianOfTwoSortedArrays {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Partitions)
     * ========================================================================
     * * APPROACH & STEPS:
     * This is the quintessential partitioning pattern heavily emphasized in
     * rigorous revision paths like the Striver A2Z sheet.
     * 1. Ensure `nums1` is the smaller array to minimize the binary search space.
     * If not, swap the references.
     * 2. Define the search space on the smaller array: `low = 0`, `high = n1`.
     * 3. The goal is to partition both arrays such that the total number of
     * elements on the left side of the partition equals the total on the right
     * (or left has one more if the total length is odd).
     * 4. Perform Binary Search to find the correct `mid1` (partition line for nums1).
     * 5. Derive `mid2` (partition line for nums2) based on the total required
     * left-half elements: `(n1 + n2 + 1) / 2 - mid1`.
     * 6. Calculate `l1`, `l2` (max elements on the left of partitions) and `r1`,
     * `r2` (min elements on the right of partitions). Handle edge cases using
     * Integer.MIN_VALUE and Integer.MAX_VALUE.
     * 7. Check validity: If `l1 <= r2` and `l2 <= r1`, the partition is perfect.
     * - If total elements are even: median is `(max(l1, l2) + min(r1, r2)) / 2.0`.
     * - If odd: median is `max(l1, l2)`.
     * 8. If `l1 > r2`, we took too many elements from nums1's left; move left (`high = mid1 - 1`).
     * 9. Else, move right (`low = mid1 + 1`).
     * * * DETAILED INTUITION:
     * Instead of looking for elements, we are searching for a "cut". A valid cut
     * ensures that every element on the left of the cut (across both arrays) is
     * smaller than or equal to every element on the right. By utilizing the
     * sorted nature of the arrays, we only need to cross-check the boundary
     * elements (`l1 <= r2` and `l2 <= r1`). This reduces an O(m+n) merge process
     * to a logarithmic search on the smallest array.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(log(min(m, n))). The binary search is strictly performed
     * on the smaller array. This easily satisfies and even beats the O(log(m+n))
     * requirement.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative binary search).
     * - Heap Space: O(1) (No new arrays instantiated).
     */
    public static double findMedianSortedArraysOptimal(int[] nums1, int[] nums2) {
        // Guarantee nums1 is the smaller array to achieve O(log(min(m,n)))
        if (nums1.length > nums2.length) {
            return findMedianSortedArraysOptimal(nums2, nums1);
        }

        int n1 = nums1.length;
        int n2 = nums2.length;
        int low = 0;
        int high = n1;

        // Number of elements required in the left half of the combined array
        int leftPart = (n1 + n2 + 1) / 2;
        int totalLen = n1 + n2;

        while (low <= high) {
            int mid1 = low + (high - low) / 2;
            int mid2 = leftPart - mid1;

            // Boundary checks: use MIN/MAX_VALUE for out-of-bounds
            int l1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int l2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int r1 = (mid1 == n1) ? Integer.MAX_VALUE : nums1[mid1];
            int r2 = (mid2 == n2) ? Integer.MAX_VALUE : nums2[mid2];

            // Valid partition condition
            if (l1 <= r2 && l2 <= r1) {
                if (totalLen % 2 == 0) {
                    return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
                } else {
                    return Math.max(l1, l2);
                }
            }
            // Partition is wrong: l1 is too large, need to move left in nums1
            else if (l1 > r2) {
                high = mid1 - 1;
            }
            // Partition is wrong: l2 is too large, need to move right in nums1
            else {
                low = mid1 + 1;
            }
        }

        return 0.0; // Fallback, should not be reached for valid inputs
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Create a new array of size `m + n`.
     * 2. Use two pointers to iterate through both arrays simultaneously, merging
     * them into the new array in sorted order (standard Merge Sort subroutine).
     * 3. Once fully merged, locate the median using index arithmetic.
     * - If length is even, average the two middle elements.
     * - If length is odd, return the exact middle element.
     * * * DETAILED INTUITION:
     * This is the most literal translation of the problem statement: "merged array =
     * [...] and median is [...]". While it intuitively proves the result, it
     * completely ignores the fact that both arrays are *already* sorted and wastes
     * extensive memory and time building a redundant data structure.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m + n). We must iterate through every element in
     * both arrays to merge them.
     * - Space Complexity: O(m + n).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(m + n) for the newly allocated merged array.
     */
    public static double findMedianSortedArraysBruteForce(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m + n];

        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }

        while (i < m) merged[k++] = nums1[i++];
        while (j < n) merged[k++] = nums2[j++];

        int total = m + n;
        if (total % 2 == 1) {
            return merged[total / 2];
        } else {
            return (merged[total / 2 - 1] + merged[total / 2]) / 2.0;
        }
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Better Brute Force)
     * ========================================================================
     * * APPROACH & STEPS:
     * We can optimize the Space Complexity of the Brute Force by omitting the
     * creation of the merged array.
     * 1. Calculate the required median indices: `ind2 = (m+n)/2` and `ind1 = ind2 - 1`.
     * 2. Use two pointers to simulate the merge process, keeping a counter of
     * how many elements have been "placed".
     * 3. When the counter hits `ind1` or `ind2`, store those specific values.
     * 4. Return the calculated median based on those two values.
     * * * DETAILED INTUITION:
     * We don't actually need the full sorted array; we just need to know what
     * the elements at the median indices *would* be if we merged them. We simulate
     * the merge, count our steps, and pluck out the exact numbers we need.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m + n). We still iterate up to the half-way point.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1) (No merged array is created).
     * * * Why Phase 1 is still better: This O(m+n) time complexity still fails
     * the strict O(log(m+n)) requirement of the problem statement.
     */
    public static double findMedianSortedArraysBetterBrute(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int totalLen = m + n;

        int ind2 = totalLen / 2;
        int ind1 = ind2 - 1;

        int count = 0;
        int ind1el = -1, ind2el = -1;
        int i = 0, j = 0;

        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                if (count == ind1) ind1el = nums1[i];
                if (count == ind2) ind2el = nums1[i];
                count++; i++;
            } else {
                if (count == ind1) ind1el = nums2[j];
                if (count == ind2) ind2el = nums2[j];
                count++; j++;
            }
        }

        while (i < m) {
            if (count == ind1) ind1el = nums1[i];
            if (count == ind2) ind2el = nums1[i];
            count++; i++;
        }

        while (j < n) {
            if (count == ind1) ind1el = nums2[j];
            if (count == ind2) ind2el = nums2[j];
            count++; j++;
        }

        if (totalLen % 2 == 1) {
            return ind2el;
        } else {
            return (ind1el + ind2el) / 2.0;
        }
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running Median of Two Sorted Arrays Test Suite...\n");

        // Test Case 1: Standard case (Odd total length)
        int[] nums1_1 = {1, 3};
        int[] nums2_1 = {2};
        runTestCase(1, nums1_1, nums2_1, 2.0);

        // Test Case 2: Standard case (Even total length)
        int[] nums1_2 = {1, 2};
        int[] nums2_2 = {3, 4};
        runTestCase(2, nums1_2, nums2_2, 2.5);

        // Test Case 3: One array is empty
        int[] nums1_3 = {};
        int[] nums2_3 = {1};
        runTestCase(3, nums1_3, nums2_3, 1.0);

        // Test Case 4: Completely distinct ranges (Array 1 much smaller)
        int[] nums1_4 = {1, 2, 3};
        int[] nums2_4 = {8, 9, 10, 11, 12};
        // Merged: 1 2 3 8 9 10 11 12 (len 8). Medians are 8 and 9 -> 8.5
        runTestCase(4, nums1_4, nums2_4, 8.5);

        // Test Case 5: Overlapping elements and duplicates
        int[] nums1_5 = {1, 1, 3, 5};
        int[] nums2_5 = {1, 2, 3, 4};
        // Merged: 1 1 1 2 3 3 4 5 (len 8). Medians are 2 and 3 -> 2.5
        runTestCase(5, nums1_5, nums2_5, 2.5);
    }

    private static void runTestCase(int testNumber, int[] nums1, int[] nums2, double expected) {
        long startTime = System.nanoTime();
        double resultOptimal = findMedianSortedArraysOptimal(nums1, nums2);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: nums1 = " + java.util.Arrays.toString(nums1) +
                ", nums2 = " + java.util.Arrays.toString(nums2));
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}