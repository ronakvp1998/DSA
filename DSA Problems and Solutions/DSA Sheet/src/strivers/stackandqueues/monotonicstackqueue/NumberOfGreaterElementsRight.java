package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * Number of Greater Elements to the Right
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given an integer array `nums`, return an integer array `counts` where
 * `counts[i]` is the number of elements to the right of `nums[i]` that are
 * strictly greater than `nums[i]`.
 *
 * Example 1:
 * Input: nums = [5, 2, 6, 1]
 * Output: [1, 1, 0, 0]
 * Explanation:
 * - To the right of 5, there is 1 greater element (6).
 * - To the right of 2, there is 1 greater element (6).
 * - To the right of 6, there are 0 greater elements.
 * - To the right of 1, there are 0 greater elements.
 *
 * Example 2:
 * Input: nums = [1, 2, 3, 4]
 * Output: [3, 2, 1, 0]
 * Explanation:
 * - To the right of 1, there are 3 greater elements (2, 3, 4).
 * - To the right of 2, there are 2 greater elements (3, 4).
 * - To the right of 3, there is 1 greater element (4).
 * - To the right of 4, there are 0 greater elements.
 *
 * CONSTRAINTS:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Binary Indexed Tree (Fenwick Tree) with Coordinate Compression
 * Phase 2: Brute Force Approach - Nested Loops
 * Phase 3: Alternative Approach - Merge Sort (Divide and Conquer)
 * ============================================================================
 */
public class NumberOfGreaterElementsRight {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Binary Indexed Tree / Fenwick Tree)
     * ============================================================================
     * Detailed Intuition:
     * To solve this optimally in O(N log N) time, we traverse the array from
     * right to left. We need a data structure that can quickly answer the query:
     * "How many numbers currently processed are strictly greater than X?" and
     * then quickly insert X into the structure. A Binary Indexed Tree (BIT) is
     * perfect for this frequency counting.
     * * Because the values in `nums` can be negative or large, we first perform
     * "Coordinate Compression". We map every unique value in the array to a
     * rank (1 to K).
     * - When we are at `nums[i]`, we find its rank.
     * - We query the BIT for the sum of frequencies in the range `[rank + 1, K]`.
     * - We then update the BIT by adding 1 to the frequency of `rank`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N). Sorting the unique elements takes O(N log N).
     * For each of the N elements, querying and updating the BIT takes O(log N).
     * - Space Complexity: O(N) auxiliary heap space for the compressed array,
     * the BIT array, and the resulting answers array. O(1) auxiliary stack space.
     * ============================================================================
     */
    public int[] countGreaterOptimal(int[] nums) {
        int n = nums.length;
        if (n == 0) return new int[0];
        int[] ans = new int[n];

        // 1. Coordinate Compression using Java 8 Streams
        int[] uniqueSorted = Arrays.stream(nums).distinct().sorted().toArray();
        int maxRank = uniqueSorted.length;

        // BIT array is 1-indexed
        int[] bit = new int[maxRank + 1];

        // 2. Traverse right to left
        for (int i = n - 1; i >= 0; i--) {
            int rank = Arrays.binarySearch(uniqueSorted, nums[i]) + 1;

            // Query for elements strictly greater than nums[i]
            ans[i] = queryBIT(bit, maxRank) - queryBIT(bit, rank);

            // Insert current element's rank into the BIT
            updateBIT(bit, maxRank, rank, 1);
        }

        return ans;
    }

    // --- Helper Methods for Phase 1 --- //
    private void updateBIT(int[] bit, int n, int index, int val) {
        for (; index <= n; index += index & -index) {
            bit[index] += val;
        }
    }

    private int queryBIT(int[] bit, int index) {
        int sum = 0;
        for (; index > 0; index -= index & -index) {
            sum += bit[index];
        }
        return sum;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Loops) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward interpretation of the problem. For every element
     * at index `i`, we scan all elements to its right (from index `i + 1` to `N - 1`).
     * If an element is strictly greater than `nums[i]`, we increment a counter.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). Two nested loops comparing every pair of elements.
     * - Space Complexity: O(1) auxiliary space (excluding the output array).
     * ============================================================================
     */
    public int[] countGreaterBruteForce(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = i + 1; j < n; j++) {
                if (nums[j] > nums[i]) {
                    count++;
                }
            }
            ans[i] = count;
        }

        return ans;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Merge Sort / Divide and Conquer)
     * ============================================================================
     * Detailed Intuition:
     * Similar to the classic "Count Inversions" algorithm, we can tweak Merge Sort.
     * We need to keep track of the original indices as elements shift around, so
     * we encapsulate the value and its original index into an `Item` class.
     * * We sort the array in **descending** order. During the merge step, if we
     * pick an element from the right subarray, it is strictly greater than all
     * remaining elements in the left subarray. We maintain a counter of how many
     * elements from the right subarray have been placed. When we finally place
     * an element from the left subarray, the number of strictly greater elements
     * that originated to its right is exactly that counter!
     * * To handle duplicates properly and enforce strictly greater logic, if `L[i]`
     * equals `R[j]`, we pull from the left subarray first.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N). The standard time complexity of Merge Sort.
     * - Space Complexity: O(N) heap space for the `Item` objects and temporary
     * arrays used during the merge step. O(log N) auxiliary stack space for recursion.
     * ============================================================================
     */
    private static class Item {
        int val;
        int index;
        Item(int val, int index) {
            this.val = val;
            this.index = index;
        }
    }

    public int[] countGreaterMergeSort(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        if (n == 0) return ans;

        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(nums[i], i);
        }

        mergeSort(items, 0, n - 1, ans);
        return ans;
    }

    // --- Helper Methods for Phase 3 --- //
    private void mergeSort(Item[] items, int left, int right, int[] ans) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;

        mergeSort(items, left, mid, ans);
        mergeSort(items, mid + 1, right, ans);
        merge(items, left, mid, right, ans);
    }

    private void merge(Item[] items, int left, int mid, int right, int[] ans) {
        Item[] temp = new Item[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        int rightElementsPlaced = 0;

        // Sort in DESCENDING order
        while (i <= mid && j <= right) {
            // If equal, take left to prevent counting equals as strictly greater
            if (items[i].val >= items[j].val) {
                ans[items[i].index] += rightElementsPlaced;
                temp[k++] = items[i++];
            } else {
                rightElementsPlaced++;
                temp[k++] = items[j++];
            }
        }

        while (i <= mid) {
            ans[items[i].index] += rightElementsPlaced;
            temp[k++] = items[i++];
        }

        while (j <= right) {
            temp[k++] = items[j++];
        }

        for (int p = 0; p < temp.length; p++) {
            items[left + p] = temp[p];
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        NumberOfGreaterElementsRight solver = new NumberOfGreaterElementsRight();

        // Utility to cleanly format array outputs using Streams
        java.util.function.Function<int[], String> format =
                arr -> Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Input (Example 1) ===");
        int[] nums1 = {5, 2, 6, 1};
        System.out.println("Input:        " + format.apply(nums1));
        System.out.println("Optimal:      " + format.apply(solver.countGreaterOptimal(nums1)));
        System.out.println("Brute Force:  " + format.apply(solver.countGreaterBruteForce(nums1)));
        System.out.println("Merge Sort:   " + format.apply(solver.countGreaterMergeSort(nums1)));

        System.out.println("\n=== Test Case 2: Strictly Increasing Array (Example 2) ===");
        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Input:        " + format.apply(nums2));
        System.out.println("Optimal:      " + format.apply(solver.countGreaterOptimal(nums2)));
        System.out.println("Brute Force:  " + format.apply(solver.countGreaterBruteForce(nums2)));
        System.out.println("Merge Sort:   " + format.apply(solver.countGreaterMergeSort(nums2)));

        System.out.println("\n=== Test Case 3: Strictly Decreasing Array ===");
        int[] nums3 = {4, 3, 2, 1};
        System.out.println("Input:        " + format.apply(nums3));
        System.out.println("Optimal:      " + format.apply(solver.countGreaterOptimal(nums3)));

        System.out.println("\n=== Test Case 4: Identical Elements (Edge Case) ===");
        int[] nums4 = {7, 7, 7, 7};
        System.out.println("Input:        " + format.apply(nums4));
        System.out.println("Optimal:      " + format.apply(solver.countGreaterOptimal(nums4)));
        System.out.println("Merge Sort:   " + format.apply(solver.countGreaterMergeSort(nums4)));

        System.out.println("\n=== Test Case 5: Negative Values ===");
        int[] nums5 = {-5, -2, -6, -1};
        System.out.println("Input:        " + format.apply(nums5));
        System.out.println("Optimal:      " + format.apply(solver.countGreaterOptimal(nums5)));
    }
}