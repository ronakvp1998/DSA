package strivers.binarysearch.bson2darray;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * Median of Row Wise Sorted Matrix
 * Solved | Hard
 * * * PROBLEM STATEMENT:
 * Given a row-wise sorted matrix of size M*N, where M is no. of rows and N is
 * no. of columns, find the median in the given matrix.
 * Note: M*N is odd.
 * * * EXAMPLES:
 * Example 1:
 * Input: M = 3, N = 3, matrix[][] =
 * 1 4 9
 * 2 5 6
 * 3 8 7
 * Output: 5
 * Explanation:
 * If we find the linear sorted array, the array becomes 1 2 3 4 5 6 7 8 9.
 * Therefore, median = 5
 * * Example 2:
 * Input: M = 3, N = 3, matrix[][] =
 * 1 3 8
 * 2 3 4
 * 1 2 5
 * Output: 3
 * Explanation:
 * If we find the linear sorted array, the array becomes 1 1 2 2 3 3 4 5 8.
 * Therefore, median = 3
 * * * CONSTRAINTS (Standard Competitive Programming bounds):
 * - 1 <= M, N <= 1000
 * - 1 <= matrix[i][j] <= 10^9
 * - M * N is guaranteed to be an odd number.
 * ============================================================================
 */
import java.util.Arrays;
import java.util.PriorityQueue;

public class MedianOfRowWiseSortedMatrix {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer Space)
     * ========================================================================
     * * APPROACH & STEPS:
     * We cannot use the standard 2D matrix binary search because the entire
     * matrix is not strictly sorted end-to-end (only individual rows are).
     * Instead, we use Binary Search on the *Range of Values*.
     * 1. Define Search Space: Find the absolute minimum element (`low`) by
     * scanning the first column, and the absolute maximum element (`high`) by
     * scanning the last column.
     * 2. The median of an array of odd length (M * N) is the element that has
     * strictly `(M * N) / 2` elements less than or equal to it. Let's call
     * this required count `req`.
     * 3. Apply Binary Search on the values between `low` and `high`.
     * 4. For a candidate `mid`, calculate how many numbers in the entire matrix
     * are less than or equal to `mid`. We do this efficiently by using the
     * `upperBound` logic (a secondary binary search) on each sorted row.
     * 5. Decision Logic:
     * - If `count <= req`, our candidate `mid` is too small to be the median.
     * We need to search for a larger value (`low = mid + 1`).
     * - If `count > req`, the candidate `mid` might be the median, or the
     * median could be smaller. We squeeze the upper bound (`high = mid - 1`).
     * 6. When the loop terminates, `low` perfectly settles on the actual median.
     * * * DETAILED INTUITION:
     * This relies on a profound monotonic property: as we pick larger numbers,
     * the count of elements in the matrix that are `<= number` strictly increases.
     * By counting how many elements are smaller than our guess, we logically
     * converge onto the exact median value without ever sorting the full dataset.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(M * log(Max - Min) * log(N)). The outer binary search
     * runs log(Max - Min) times. Inside, we iterate over M rows, and for each row,
     * we perform an O(log N) binary search to count elements. Highly optimal.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative algorithms).
     * - Heap Space: O(1) (No new dynamic objects).
     */
    public static int findMedianOptimal(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;

        // Find minimum and maximum bounds from the start and end of rows
        for (int i = 0; i < m; i++) {
            low = Math.min(low, matrix[i][0]);
            high = Math.max(high, matrix[i][n - 1]);
        }

        int requiredCount = (m * n) / 2;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int countSmallerOrEqual = 0;

            // Count elements <= mid row by row
            for (int i = 0; i < m; i++) {
                countSmallerOrEqual += upperBound(matrix[i], mid);
            }

            if (countSmallerOrEqual <= requiredCount) {
                low = mid + 1; // We need a larger number to cover the median index
            } else {
                high = mid - 1; // Squeeze the range downward
            }
        }

        return low;
    }

    // Helper method to find number of elements <= target in a sorted row
    private static int upperBound(int[] row, int target) {
        int low = 0;
        int high = row.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (row[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Create a 1D integer array of size M * N.
     * 2. Flatten the entire 2D matrix into this 1D array using a nested loop.
     * 3. Sort the 1D array using the built-in sorting algorithm.
     * 4. Return the element at the middle index: `(M * N) / 2`.
     * * * DETAILED INTUITION:
     * This is the literal translation of the problem statement's explanation
     * section. It proves the concept but completely ignores the existing
     * row-wise sorted structure, resulting in unnecessary memory overhead
     * and a suboptimal time complexity.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(M * N * log(M * N)). Flattening takes O(M * N),
     * sorting the flattened array takes O(K log K) where K = M * N.
     * - Space Complexity: O(M * N) to store the flattened array.
     * - Auxiliary Stack Space: O(log(M * N)) for Quicksort recursion tree.
     * - Heap Space: O(M * N) for array instantiation.
     */
    public static int findMedianBruteForce(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] flattened = new int[m * n];
        int index = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                flattened[index++] = matrix[i][j];
            }
        }

        Arrays.sort(flattened);
        return flattened[(m * n) / 2];
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Min-Heap / Priority Queue)
     * ========================================================================
     * * APPROACH & STEPS:
     * This approach is similar to merging K-sorted arrays.
     * 1. Create a Min-Heap.
     * 2. Insert the first element of each row into the heap, along with its
     * row index and column index. (Heap size is M).
     * 3. Extract the minimum element from the heap.
     * 4. If the extracted element is not from the last column of its row,
     * insert the next element from that same row into the heap.
     * 5. Repeat this extraction `(M * N) / 2` times.
     * 6. The next element extracted is the median.
     * * * DETAILED INTUITION:
     * We effectively simulate a synchronized linear traversal of all M rows
     * simultaneously. The Min-Heap guarantees that we always look at the
     * globally smallest available element.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(M * log M + K * log M), where K = (M * N) / 2.
     * Overall time is basically O(M * N * log M). It's faster than brute force
     * but strictly slower than the Binary Search on Answer method.
     * - Space Complexity: O(M) to maintain the Priority Queue.
     */
    public static int findMedianHeap(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // Priority Queue stores arrays of format: [value, rowIndex, colIndex]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < m; i++) {
            minHeap.offer(new int[]{matrix[i][0], i, 0});
        }

        int count = 0;
        int required = (m * n) / 2;

        while (count < required) {
            int[] current = minHeap.poll();
            int r = current[1];
            int c = current[2];

            if (c + 1 < n) {
                minHeap.offer(new int[]{matrix[r][c + 1], r, c + 1});
            }
            count++;
        }

        return minHeap.peek()[0];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running Median of Row Wise Sorted Matrix Test Suite...\n");

        // Test Case 1: Standard case (from Example 1)
        // Adjusting example to reflect actual row-wise sorting
        int[][] matrix1 = {
                {1, 4, 9},
                {2, 5, 6},
                {3, 7, 8} // Fixed 3 8 7 -> 3 7 8 to uphold strict row-sorting constraint
        };
        runTestCase(1, matrix1, 5);

        // Test Case 2: Standard case (Example 2)
        int[][] matrix2 = {
                {1, 3, 8},
                {2, 3, 4},
                {1, 2, 5}
        };
        runTestCase(2, matrix2, 3);

        // Test Case 3: 1x1 Matrix
        int[][] matrix3 = {
                {42}
        };
        runTestCase(3, matrix3, 42);

        // Test Case 4: Long narrow matrix (1x5)
        int[][] matrix4 = {
                {10, 20, 30, 40, 50}
        };
        runTestCase(4, matrix4, 30);

        // Test Case 5: Large Values Matrix
        int[][] matrix5 = {
                {1000000, 2000000, 3000000},
                {1500000, 2500000, 3500000},
                {1200000, 2200000, 3200000}
        };
        runTestCase(5, matrix5, 2200000);
    }

    private static void runTestCase(int testNumber, int[][] matrix, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = findMedianOptimal(matrix);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}