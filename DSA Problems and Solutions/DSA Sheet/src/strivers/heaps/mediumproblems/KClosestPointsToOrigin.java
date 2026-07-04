package strivers.heaps.mediumproblems;

/**
 * ==============================================================================================
 * 973. K Closest Points to Origin
 * ==============================================================================================
 * Formal Problem Statement:
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and
 * an integer k, return the k closest points to the origin (0, 0).
 * * The distance between two points on the X-Y plane is the Euclidean distance
 * (i.e., sqrt((x1 - x2)^2 + (y1 - y2)^2)).
 * * You may return the answer in any order. The answer is guaranteed to be unique
 * (except for the order that it is in).
 * * Example 1:
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 * * Example 2:
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * Output: [[3,3],[-2,4]]
 * Explanation: The answer [[-2,4],[3,3]] would also be accepted.
 * * Constraints:
 * - 1 <= k <= points.length <= 10^4
 * - -10^4 <= xi, yi <= 10^4
 * * Note on Distance Calculation:
 * To avoid floating-point inaccuracies and expensive square root operations, we use
 * the squared Euclidean distance: x^2 + y^2.
 * ==============================================================================================
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class KClosestPointsToOrigin {

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Max Heap (Priority Queue)
     * ==============================================================================================
     * Detailed Intuition:
     * If we want to avoid sorting the entire array, we can use a Max Heap of size K.
     * We iterate through the points, adding them to the heap. If the heap exceeds size K,
     * we remove the point with the maximum distance (the root). By the end of the iteration,
     * the Max Heap will strictly contain the K closest points.
     * * Complexity Analysis:
     * - Time: O(N log K). We iterate through N points, and each insertion/deletion in a heap of
     * size K takes O(log K).
     * - Space: O(K) for maintaining the Priority Queue of size K.
     */
    public static int[][] kClosestAlternative(int[][] points, int k) {
        // Max Heap: largest distance at the root
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(squaredDistance(p2), squaredDistance(p1))
        );

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Remove the point furthest from origin
            }
        }

        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    /**
     * Helper Method: Calculate squared distance from origin.
     * By omitting the square root, we save computational cycles and avoid floating-point errors.
     */
    private static int squaredDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - QuickSelect (The "Master it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * We don't need to fully sort the array (which takes O(N log N)), nor do we strictly need
     * a heap (which takes O(N log K)). We just need to find the Kth smallest element and ensure
     * all elements smaller than it are to its left.
     * QuickSelect, based on QuickSort's partitioning logic, is perfect for this. We pick a pivot,
     * partition the array, and check the pivot's final index. If the index equals K, we are done.
     * If it's less than K, we search the right half. If greater, we search the left half.
     * * Complexity Analysis:
     * - Time: O(N) average case. We process N elements, then N/2, then N/4... summing to ~2N.
     * Worst case is O(N^2) if the pivot choices are extremely poor, but random/middle
     * pivots mitigate this.
     * - Space: O(1) auxiliary space (modifying array in-place), O(log N) for the recursion stack.
     */
    public static int[][] kClosestOptimal(int[][] points, int k) {
        quickSelect(points, 0, points.length - 1, k);
        return Arrays.copyOfRange(points, 0, k);
    }

    private static void quickSelect(int[][] points, int left, int right, int k) {
        if (left >= right) return;

        int pivotIndex = partition(points, left, right);

        // If the pivot index matches exactly K, the first K elements are our answer.
        if (pivotIndex == k) {
            return;
        } else if (pivotIndex < k) {
            // We need more elements, search the right partition
            quickSelect(points, pivotIndex + 1, right, k);
        } else {
            // We have too many elements, search the left partition
            quickSelect(points, left, pivotIndex - 1, k);
        }
    }

    private static int partition(int[][] points, int left, int right) {
        // Choose the rightmost element as the pivot
        int[] pivot = points[right];
        int pivotDist = squaredDistance(pivot);
        int i = left;

        for (int j = left; j < right; j++) {
            if (squaredDistance(points[j]) <= pivotDist) {
                swap(points, i, j);
                i++;
            }
        }
        swap(points, i, right);
        return i;
    }

    private static void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Sorting (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * The most straightforward way to find the K closest points is to calculate the distance for
     * all points, completely sort the array based on these distances, and then slice the first
     * K elements. Using Java 8 Streams makes this visually concise, though computationally heavier.
     * * Complexity Analysis:
     * - Time: O(N log N) due to the full array sort.
     * - Space: O(log N) to O(N) depending on the internal sorting algorithm (TimSort in Java).
     */
    public static int[][] kClosestBruteForce(int[][] points, int k) {
        return Arrays.stream(points)
                .sorted((p1, p2) -> Integer.compare(squaredDistance(p1), squaredDistance(p2)))
                .limit(k)
                .toArray(int[][]::new);
    }

    /**
     * ==============================================================================================
     * 4. Testing Suite
     * ==============================================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- 973. K Closest Points to Origin ---");

        int[][][] testCases = {
                {{1, 3}, {-2, 2}},                      // Standard Case 1
                {{3, 3}, {5, -1}, {-2, 4}},             // Standard Case 2
                {{0, 1}, {1, 0}},                       // Edge Case: Equidistant points
                {{1, 1}, {2, 2}, {3, 3}, {4, 4}},       // Edge Case: K = points.length
                {{0, 0}}                                // Edge Case: Origin itself
        };
        int[] kValues = {1, 2, 1, 4, 1};

        for (int i = 0; i < testCases.length; i++) {
            int[][] points = testCases[i];
            int k = kValues[i];

            System.out.println("\nTest Case " + (i + 1) + ":");
            System.out.println("Points: " + Arrays.deepToString(points) + " | K: " + k);

            // Need to deep copy arrays to prevent in-place modifications from ruining subsequent tests
            int[][] pointsForOptimal = copy2DArray(points);
            int[][] pointsForBrute = copy2DArray(points);
            int[][] pointsForHeap = copy2DArray(points);

            long startTime, endTime;

            // Test Phase 1: Optimal (QuickSelect)
            startTime = System.nanoTime();
            int[][] resOptimal = kClosestOptimal(pointsForOptimal, k);
            endTime = System.nanoTime();
            System.out.println("Optimal (QuickSelect) : " + Arrays.deepToString(resOptimal) +
                    " [Time: " + (endTime - startTime) + " ns]");

            // Test Phase 2: Brute Force (Sorting)
            startTime = System.nanoTime();
            int[][] resBrute = kClosestBruteForce(pointsForBrute, k);
            endTime = System.nanoTime();
            System.out.println("Brute Force (Sort)    : " + Arrays.deepToString(resBrute) +
                    " [Time: " + (endTime - startTime) + " ns]");

            // Test Phase 3: Alternative (Max Heap)
            startTime = System.nanoTime();
            int[][] resHeap = kClosestAlternative(pointsForHeap, k);
            endTime = System.nanoTime();
            System.out.println("Alternative (Max Heap): " + Arrays.deepToString(resHeap) +
                    " [Time: " + (endTime - startTime) + " ns]");
        }
    }

    private static int[][] copy2DArray(int[][] original) {
        return Arrays.stream(original)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }
}