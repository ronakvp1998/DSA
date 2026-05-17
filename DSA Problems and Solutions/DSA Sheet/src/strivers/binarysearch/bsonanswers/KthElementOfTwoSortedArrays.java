package com.questions.strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * K-th Element of two sorted arrays
 * Solved | Hard (Conceptually identical to Median of Two Sorted Arrays)
 * * * PROBLEM STATEMENT:
 * Given two sorted arrays a and b of size m and n respectively. Find the kth
 * element of the final sorted array.
 * * * EXAMPLES:
 * Example 1:
 * Input: a = [2, 3, 6, 7, 9], b = [1, 4, 8, 10], k = 5
 * Output: 6
 * Explanation: The final sorted array would be [1, 2, 3, 4, 6, 7, 8, 9, 10].
 * The 5th element of this array is 6.
 * * Example 2:
 * Input: a = [100, 112, 256, 349, 770], b = [72, 86, 113, 119, 265, 445, 892], k = 7
 * Output: 256
 * Explanation: The final sorted array is [72, 86, 100, 112, 113, 119, 256, 265, 349, 445, 770, 892].
 * The 7th element of this array is 256.
 * * * CONSTRAINTS:
 * - 1 <= m, n <= 10^5
 * - 0 <= a[i], b[i] <= 10^9
 * - 1 <= k <= m + n
 * ============================================================================
 */
public class KthElementOfTwoSortedArrays {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Partitions)
     * ========================================================================
     * * APPROACH & STEPS:
     * This problem is a direct extension of the "Median of Two Sorted Arrays"
     * logic and is heavily featured in the Striver A2Z sheet, which is the
     * gold standard for mastering these specific partition patterns.
     * 1. Ensure array `a` is the smaller array to minimize our binary search
     * space. If not, swap references.
     * 2. Define the Search Boundaries (This is the trickiest part):
     * - Minimum elements we can pick from `a` (`low`): If `k` is larger than
     * the size of `b`, we are FORCED to pick at least `k - b.length` elements
     * from `a`. Otherwise, we can pick 0. So, `low = Math.max(0, k - n)`.
     * - Maximum elements we can pick from `a` (`high`): We cannot pick more
     * elements than `k`, and we cannot pick more elements than the size of `a`.
     * So, `high = Math.min(k, m)`.
     * 3. Apply Binary Search: Calculate `mid1` (elements from `a`). The elements
     * needed from `b` is strictly `mid2 = k - mid1`.
     * 4. Check Boundary Conditions: Find `l1`, `l2` (max on left side of cut)
     * and `r1`, `r2` (min on right side of cut). Use Integer.MIN_VALUE and
     * Integer.MAX_VALUE for out-of-bound edge cases.
     * 5. Decision Logic:
     * - If `l1 <= r2` and `l2 <= r1`, the partition is valid! The k-th element
     * is simply the maximum of the left partition boundary: `Math.max(l1, l2)`.
     * - If `l1 > r2`, we picked too many large elements from `a`, so shift
     * search left (`high = mid1 - 1`).
     * - Else, shift right (`low = mid1 + 1`).
     * * * DETAILED INTUITION:
     * Instead of linearly merging to find the k-th element, we view this as a
     * partition problem. We need exactly `k` elements on the left side of our
     * conceptual "cut" across both arrays. Because the arrays are sorted, if the
     * largest element on the left of `a` is smaller than the smallest on the
     * right of `b` (and vice-versa), our left side contains the absolute smallest
     * `k` elements overall. The largest among those `k` elements is our answer.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(log(min(m, n))), where m and n are the array lengths.
     * We binary search exclusively on the bounds of the smaller array.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative loop).
     * - Heap Space: O(1) (No dynamic collections used).
     */
    public static int kthElementOptimal(int[] a, int[] b, int k) {
        // Guarantee 'a' is the smaller array
        if (a.length > b.length) {
            return kthElementOptimal(b, a, k);
        }

        int m = a.length;
        int n = b.length;

        // Edge case boundaries
        int low = Math.max(0, k - n);
        int high = Math.min(k, m);

        while (low <= high) {
            int mid1 = low + (high - low) / 2;
            int mid2 = k - mid1;

            int l1 = (mid1 == 0) ? Integer.MIN_VALUE : a[mid1 - 1];
            int l2 = (mid2 == 0) ? Integer.MIN_VALUE : b[mid2 - 1];
            int r1 = (mid1 == m) ? Integer.MAX_VALUE : a[mid1];
            int r2 = (mid2 == n) ? Integer.MAX_VALUE : b[mid2];

            if (l1 <= r2 && l2 <= r1) {
                // Partition is perfectly valid, the max of left half is the kth element
                return Math.max(l1, l2);
            } else if (l1 > r2) {
                // Took too many elements from array a
                high = mid1 - 1;
            } else {
                // Need more elements from array a
                low = mid1 + 1;
            }
        }

        return -1; // Fallback, shouldn't be reached with valid inputs
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Use two pointers (`i` for `a` and `j` for `b`).
     * 2. Maintain a counter to track how many elements we have theoretically
     * "merged" so far.
     * 3. Compare `a[i]` and `b[j]`. The smaller element conceptually enters the
     * merged array. Increment the counter and the respective pointer.
     * 4. When the counter reaches `k`, the element we just processed is our answer.
     * 5. If one array is exhausted, continue counting through the other array.
     * * * DETAILED INTUITION:
     * This mimics exactly what we do in the merge step of Merge Sort. However,
     * we optimize space by not actually creating the new merged array. We just
     * simulate the merge and keep a tally until we hit the k-th element.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(k). In the worst case (where k = m + n), this is
     * O(m + n). It fails to hit the logarithmic constraint required in interviews.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int kthElementBruteForce(int[] a, int[] b, int k) {
        int m = a.length;
        int n = b.length;
        int i = 0, j = 0;
        int counter = 0;
        int answer = -1;

        while (i < m && j < n) {
            if (a[i] < b[j]) {
                counter++;
                if (counter == k) return a[i];
                i++;
            } else {
                counter++;
                if (counter == k) return b[j];
                j++;
            }
        }

        // If array 'a' still has elements
        while (i < m) {
            counter++;
            if (counter == k) return a[i];
            i++;
        }

        // If array 'b' still has elements
        while (j < n) {
            counter++;
            if (counter == k) return b[j];
            j++;
        }

        return answer;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Creating the Full Merged Array:
     * - We could literally allocate `int[] merged = new int[m + n]`, run a full
     * merge loop, and then return `merged[k-1]`.
     * - Why it's terrible: This mandates O(m + n) Time Complexity AND O(m + n)
     * Space Complexity. The memory allocation alone makes it significantly slower
     * than the two-pointer simulation, and vastly inferior to the O(log N)
     * binary search.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running K-th Element of Two Sorted Arrays Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] a1 = {2, 3, 6, 7, 9};
        int[] b1 = {1, 4, 8, 10};
        int k1 = 5;
        runTestCase(1, a1, b1, k1, 6);

        // Test Case 2: Standard case (Example 2)
        int[] a2 = {100, 112, 256, 349, 770};
        int[] b2 = {72, 86, 113, 119, 265, 445, 892};
        int k2 = 7;
        runTestCase(2, a2, b2, k2, 256);

        // Test Case 3: Edge Case - k is exactly 1 (Find the global minimum)
        int[] a3 = {5, 6, 7};
        int[] b3 = {1, 2, 3};
        int k3 = 1;
        runTestCase(3, a3, b3, k3, 1);

        // Test Case 4: Edge Case - k is exactly m + n (Find the global maximum)
        int[] a4 = {1, 2};
        int[] b4 = {3, 4, 5};
        int k4 = 5;
        runTestCase(4, a4, b4, k4, 5);

        // Test Case 5: Edge Case - One array is completely smaller than the other
        int[] a5 = {1, 2, 3, 4};
        int[] b5 = {10, 20, 30, 40};
        int k5 = 6; // Answer should be 20
        runTestCase(5, a5, b5, k5, 20);
    }

    private static void runTestCase(int testNumber, int[] a, int[] b, int k, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = kthElementOptimal(a, b, k);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: a = " + java.util.Arrays.toString(a) +
                "\n       b = " + java.util.Arrays.toString(b) +
                ", k = " + k);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}