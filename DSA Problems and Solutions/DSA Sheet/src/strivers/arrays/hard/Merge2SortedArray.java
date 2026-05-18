package strivers.arrays.hard;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: MERGE TWO SORTED ARRAYS WITHOUT EXTRA SPACE
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given two sorted arrays arr1[] and arr2[] of sizes n and m in non-decreasing order.
 * Merge them in sorted order. Modify arr1 so that it contains the first N elements
 * and modify arr2 so that it contains the last M elements. Do this without using
 * any extra space.
 * * Input/Output Formats:
 * Input: Two integer arrays `arr1` and `arr2`, and their respective sizes `n` and `m`.
 * Output: The arrays are modified in-place. No explicit return type is needed.
 * * Constraints:
 * - 1 <= n, m <= 10^5
 * - 0 <= arr1[i], arr2[i] <= 10^7
 *
 * * Example 1:
 * Input: n = 4, arr1[] = [1, 4, 8, 10], m = 3, arr2[] = [2, 3, 9]
 * Output: arr1[] = [1, 2, 3, 4], arr2[] = [8, 9, 10]
 * Explanation: After merging the two non-decreasing arrays, we get, 1 2 3 4 8 9 10.
 *
 * * Example 2:
 * Input: n = 4, arr1[] = [1, 3, 5, 7], m = 5, arr2[] = [0, 2, 6, 8, 9]
 * Output: arr1[] = [0, 1, 2, 3], arr2[] = [5, 6, 7, 8, 9]
 * Explanation: After merging the two non-decreasing arrays, we get, 0 1 2 3 5 6 7 8 9.
 *
 * * Conceptual Visualization (Two-Pointer Swap Logic):
 * arr1: [1, 4, 8, 10]  <- left pointer starts at 10 (end of arr1)
 * arr2: [2, 3, 9]      <- right pointer starts at 2 (start of arr2)
 * * Since 10 > 2, swap them.
 * arr1: [1, 4, 8, 2]
 * arr2: [10, 3, 9]
 * Continue swapping while left > right. Then simply sort both arrays!
 * ============================================================================
 */
public class Merge2SortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;

        // We only need to iterate until nums2 is fully merged.
        // If p1 reaches < 0 first, we just keep dropping nums2 elements into place.
        // If p2 reaches < 0 first, nums1's remaining elements are already perfectly sorted at the front!
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
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Two-Pointer Swap + Sort)
     * ========================================================================
     * Approach and Steps:
     * 1. Initialize two pointers: `left` pointing to the last element of arr1
     * (index n-1) and `right` pointing to the first element of arr2 (index 0).
     * 2. Compare arr1[left] and arr2[right].
     * 3. If arr1[left] > arr2[right], swap them. Then move `left` backward and
     * `right` forward.
     * 4. If arr1[left] <= arr2[right], break out of the loop. Since both arrays
     * are sorted, all remaining elements in arr1 will be smaller than the
     * remaining elements in arr2.
     * 5. Finally, sort both arr1 and arr2 individually to restore their internal
     * sorted order.
     * * Detailed Intuition:
     * The goal is to ensure that the largest `m` elements end up in `arr2` and the
     * smallest `n` elements end up in `arr1`. Since `arr1` and `arr2` are already
     * sorted, the largest elements of `arr1` are at its end, and the smallest of
     * `arr2` are at its beginning. By swapping these across the boundary until
     * arr1's tail is smaller than arr2's head, we perfectly segregate the elements.
     * Sorting them afterwards guarantees the exact final state.
     * * Complexity Analysis:
     * - Time (O): O(min(n, m)) for the while loop + O(n log n) + O(m log m) for
     * sorting. Overall Time: O((n log n) + (m log m)). This is exceptionally fast
     * in practice due to the highly optimized nature of Java's Dual-Pivot Quicksort.
     * - Space (O): O(1) auxiliary space. No extra arrays are created. (Note: Java's
     * Arrays.sort may use O(log N) stack space for primitives).
     * ========================================================================
     */
    public static void mergeOptimal(long[] arr1, long[] arr2, int n, int m) {
        int left = n - 1;
        int right = 0;

        // Swap the larger elements of arr1 with smaller elements of arr2
        while (left >= 0 && right < m) {
            if (arr1[left] > arr2[right]) {
                long temp = arr1[left];
                arr1[left] = arr2[right];
                arr2[right] = temp;
                left--;
                right++;
            } else {
                // Since arrays are sorted, further elements will also be in correct relative order
                break;
            }
        }

        // Re-sort the arrays to organize the swapped elements
        Arrays.sort(arr1);
        Arrays.sort(arr2);
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Create a third array `arr3` of size n + m.
     * 2. Use two pointers to iterate through arr1 and arr2, picking the smaller
     * element at each step and placing it into arr3 (standard merge step of Merge Sort).
     * 3. Once all elements are in arr3, iterate through arr3.
     * 4. Place the first `n` elements back into arr1, and the remaining `m`
     * elements into arr2.
     * * Detailed Intuition:
     * When constrained by an in-place requirement, the most natural starting point
     * is to ignore the constraint to see if the logic holds. By using an extra
     * array, the problem simplifies into the fundamental "merge" subroutine of
     * Merge Sort. After merging into a single sorted continuum, we merely slice
     * it back into the original array vessels.
     * * Complexity Analysis:
     * - Time (O): O(n + m) to merge the arrays + O(n + m) to copy elements back.
     * Overall Time: O(n + m).
     * - Space (O): O(n + m) heap space for the auxiliary array `arr3`.
     * ========================================================================
     */
    public static void mergeBruteForce(long[] arr1, long[] arr2, int n, int m) {
        long[] arr3 = new long[n + m];
        int left = 0, right = 0, index = 0;

        // Merge both arrays into arr3
        while (left < n && right < m) {
            if (arr1[left] <= arr2[right]) {
                arr3[index++] = arr1[left++];
            } else {
                arr3[index++] = arr2[right++];
            }
        }

        // Copy remaining elements
        while (left < n) {
            arr3[index++] = arr1[left++];
        }
        while (right < m) {
            arr3[index++] = arr2[right++];
        }

        // Distribute back to original arrays
        for (int i = 0; i < n + m; i++) {
            if (i < n) {
                arr1[i] = arr3[i];
            } else {
                arr2[i - n] = arr3[i];
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (The Gap Method / Shell Sort Intuition)
     * ========================================================================
     * Approach and Steps:
     * 1. The total length is `len = n + m`. Initial gap = ceil(len / 2).
     * 2. Use two pointers separated by `gap`.
     * 3. Compare elements at `left` and `right`. If the left element is greater
     * than the right, swap them.
     * 4. Traverse the gap across three zones:
     * - Both pointers in arr1.
     * - `left` in arr1, `right` in arr2.
     * - Both pointers in arr2.
     * 5. Reduce the gap by half (ceil(gap / 2)) and repeat until gap reaches 0.
     * * Detailed Intuition:
     * This is derived from Shell Sort. Instead of moving elements one step at a
     * time (which is slow), we compare elements far apart and swap them. This
     * quickly shifts the massive elements to the right side (into arr2) and tiny
     * elements to the left (arr1). As the gap shrinks to 1, the array becomes
     * completely sorted without needing an explicit `Arrays.sort()` call at the end.
     * * Complexity Analysis:
     * - Time (O): O((n + m) * log(n + m)). The outer loop runs log(n+m) times
     * (as gap halves). The inner loop iterates at most n+m times.
     * - Space (O): O(1) auxiliary space. Strictly in-place.
     * ========================================================================
     */
    public static void mergeGapMethod(long[] arr1, long[] arr2, int n, int m) {
        int len = n + m;
        // Initial gap
        int gap = (len / 2) + (len % 2);

        while (gap > 0) {
            int left = 0;
            int right = left + gap;

            while (right < len) {
                // Case 1: left in arr1, right in arr2
                if (left < n && right >= n) {
                    swapIfGreater(arr1, arr2, left, right - n);
                }
                // Case 2: both in arr2
                else if (left >= n) {
                    swapIfGreater(arr2, arr2, left - n, right - n);
                }
                // Case 3: both in arr1
                else {
                    swapIfGreater(arr1, arr1, left, right);
                }
                left++;
                right++;
            }
            // Break to avoid infinite loop when gap hits 1
            if (gap == 1) break;
            // Recalculate gap
            gap = (gap / 2) + (gap % 2);
        }
    }

    // Helper method for the Gap Method
    private static void swapIfGreater(long[] arr1, long[] arr2, int ind1, int ind2) {
        if (arr1[ind1] > arr2[ind2]) {
            long temp = arr1[ind1];
            arr1[ind1] = arr2[ind2];
            arr2[ind2] = temp;
        }
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all approaches against standard,
     * duplicate-heavy, and edge-case inputs.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== MERGE SORTED ARRAYS MASTERCLASS TESTING SUITE ===\n");

        // Test Case 1: Standard overlapping merge
        long[] arr1_tc1 = {1, 4, 8, 10};
        long[] arr2_tc1 = {2, 3, 9};

        // Test Case 2: Standard interleaved merge
        long[] arr1_tc2 = {1, 3, 5, 7};
        long[] arr2_tc2 = {0, 2, 6, 8, 9};

        // Test Case 3: arr1 completely greater than arr2
        long[] arr1_tc3 = {10, 11, 12};
        long[] arr2_tc3 = {1, 2, 3};

        // Execution logic wrapped in a robust tester
        runTest("Test Case 1 (Brute Force)", arr1_tc1.clone(), arr2_tc1.clone(), 1);
        runTest("Test Case 1 (Gap Method)", arr1_tc1.clone(), arr2_tc1.clone(), 2);
        runTest("Test Case 1 (Optimal Swap+Sort)", arr1_tc1.clone(), arr2_tc1.clone(), 3);

        System.out.println("-".repeat(50));

        runTest("Test Case 2 (Brute Force)", arr1_tc2.clone(), arr2_tc2.clone(), 1);
        runTest("Test Case 2 (Gap Method)", arr1_tc2.clone(), arr2_tc2.clone(), 2);
        runTest("Test Case 2 (Optimal Swap+Sort)", arr1_tc2.clone(), arr2_tc2.clone(), 3);

        System.out.println("-".repeat(50));

        runTest("Test Case 3 (Brute Force)", arr1_tc3.clone(), arr2_tc3.clone(), 1);
        runTest("Test Case 3 (Gap Method)", arr1_tc3.clone(), arr2_tc3.clone(), 2);
        runTest("Test Case 3 (Optimal Swap+Sort)", arr1_tc3.clone(), arr2_tc3.clone(), 3);
    }

    // Helper runner to maintain clean code
    private static void runTest(String testName, long[] arr1, long[] arr2, int phase) {
        long start = System.nanoTime();

        if (phase == 1) {
            mergeBruteForce(arr1, arr2, arr1.length, arr2.length);
        } else if (phase == 2) {
            mergeGapMethod(arr1, arr2, arr1.length, arr2.length);
        } else {
            mergeOptimal(arr1, arr2, arr1.length, arr2.length);
        }

        long end = System.nanoTime();

        System.out.println(testName);
        System.out.println("arr1[] = " + Arrays.toString(arr1));
        System.out.println("arr2[] = " + Arrays.toString(arr2));
        System.out.println("Time: " + (end - start) / 1000 + " us\n");
    }
}