package strivers.arrays.hard;

import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: MERGE OVERLAPPING SUB-INTERVALS
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array of intervals where intervals[i] = [starti, endi], merge all
 * overlapping intervals, and return an array of the non-overlapping intervals
 * that cover all the intervals in the input.
 * * * Input/Output Formats:
 * Input: A 2D integer array `intervals` of size N x 2.
 * Output: A 2D integer array containing the merged, non-overlapping intervals.
 * * * Constraints (Standard LeetCode 56):
 * - 1 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= starti <= endi <= 10^4
 *
 * * * Example 1:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] are overlapping we can merge them
 * to form [1,6].
 *
 * * * Example 2:
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Since intervals [1,4] and [4,5] are overlapping we can merge them
 * to form [1,5].
 *
 * * * Conceptual Visualization (Sorting + Line Sweep):
 * Original: [[2,6], [1,3], [15,18], [8,10]]
 * * 1. Sort by start time:
 * [[1,3], [2,6], [8,10], [15,18]]
 * * 2. Iteration & Merge Process:
 * Current Active: [1,3]
 * Next: [2,6] -> Start (2) <= Active End (3) -> OVERLAP!
 * New Active End = max(3, 6) = 6. Active becomes [1,6].
 * Next: [8,10] -> Start (8) > Active End (6) -> DISJOINT!
 * Save [1,6]. New Active becomes [8,10].
 * Next: [15,18] -> Start (15) > Active End (10) -> DISJOINT!
 * Save [8,10]. New Active becomes [15,18].
 * End of array -> Save [15,18].
 * ============================================================================
 */
public class MergeOverlappingSubIntervals {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Sorting + Single Pass)
     * ========================================================================
     * Approach and Steps:
     * 1. Check for edge cases (empty array or single interval).
     * 2. Sort the intervals based on their start values in ascending order.
     * 3. Create a List to hold the dynamically sized merged intervals.
     * 4. Initialize the 'current' interval with the first interval in the sorted array.
     * 5. Iterate through the remaining intervals:
     * a. If the next interval overlaps with 'current' (next.start <= current.end),
     * merge them by updating current.end to Math.max(current.end, next.end).
     * b. If they do not overlap, add 'current' to the results list and reassign
     * 'current' to be the next interval.
     * 6. Add the final 'current' interval to the results list.
     * 7. Convert the List back to a 2D integer array and return.
     * * * Detailed Intuition:
     * To merge intervals efficiently, we must establish a predictable order.
     * By sorting intervals based on their start times, we guarantee that any
     * overlapping intervals will be adjacent to each other in the array. This
     * reduces a complex multiple-overlap problem into a simple sequential comparison.
     * We only ever need to compare the current interval against the most recently
     * updated "active" merged interval.
     * * * Complexity Analysis:
     * - Time (O): O(N log N) - Sorting the 2D array dominates the time complexity.
     * The subsequent linear scan takes O(N) time. Overall Time: O(N log N).
     * - Space (O): O(N) to O(log N) auxiliary space.
     * - Heap Space: O(N) to store the result list before converting to an array
     * (worst case: no intervals overlap).
     * - Stack Space: O(log N) auxiliary stack space used by Java's underlying
     * Dual-Pivot Quicksort for primitive/object sorting.
     * ========================================================================
     */
    public static int[][] mergeOptimal(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Step 2: Sort based on start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();

        // Step 4: Initialize the first active interval
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        // Step 5: Linear scan to merge
        for (int[] interval : intervals) {
            int currentEnd = currentInterval[1];
            int nextBegin = interval[0];
            int nextEnd = interval[1];

            if (currentEnd >= nextBegin) {
                // Overlap detected: expand the current interval's end
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // Disjoint detected: add the new interval as the active one
                currentInterval = interval;
                merged.add(currentInterval);
            }
        }

        // Step 7: Convert List to 2D array
        return merged.toArray(new int[merged.size()][]);
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Sort the intervals by start time (same as optimal).
     * 2. Iterate over the intervals with an outer loop `i`.
     * 3. For each interval at `i`, establish its start and end.
     * 4. Use an inner loop `j` starting from `i + 1` to find all subsequent
     * intervals that overlap with the interval at `i`.
     * 5. Continuously update the end boundary as long as overlaps are found.
     * 6. Once a non-overlapping interval is found (or end of array is reached),
     * add the merged interval to the answer.
     * 7. Fast-forward the outer loop pointer `i` to `j - 1` to skip the intervals
     * we just merged.
     * * * Detailed Intuition:
     * Before realizing we can maintain a running "active" interval, a candidate
     * might think about explicitly checking every subsequent interval against the
     * current one using nested loops. While sorting still makes this easier, the
     * nested loop structure is conceptually a Brute Force way of grouping items,
     * even if we optimize the outer pointer to skip processed intervals.
     * * * Complexity Analysis:
     * - Time (O): O(N log N) + O(N). Although there are nested loops, every element
     * is visited at most twice because we advance the outer pointer `i` based
     * on `j`. The bottleneck remains the O(N log N) sorting step.
     * (Note: A pure brute force without sorting would be O(N^2) or O(N^3), but
     * is overly complex to implement due to recursive cascading merges).
     * - Space (O): O(N) heap space for the output List. O(log N) stack space for sorting.
     * ========================================================================
     */
    public static int[][] mergeBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> ans = new ArrayList<>();
        int n = intervals.length;

        for (int i = 0; i < n; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            // Skip intervals that are already merged in the answer list
            if (!ans.isEmpty() && end <= ans.get(ans.size() - 1)[1]) {
                continue;
            }

            // Check the rest of the intervals for overlaps
            for (int j = i + 1; j < n; j++) {
                if (intervals[j][0] <= end) {
                    end = Math.max(end, intervals[j][1]);
                } else {
                    break;
                }
            }

            ans.add(new int[]{start, end});
        }

        return ans.toArray(new int[ans.size()][]);
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Graph / Connected Components)
     * ========================================================================
     * Approach and Steps:
     * 1. Treat each interval as a node in a graph.
     * 2. Draw an undirected edge between two nodes if their intervals overlap.
     * (Overlap condition: intervalA[0] <= intervalB[1] && intervalB[0] <= intervalA[1])
     * 3. Traverse the graph to find all Connected Components.
     * 4. For each component, the merged interval is [min(starts), max(ends)]
     * of all nodes in that component.
     * * * Detailed Intuition:
     * If sorting is restricted, we can use Graph Theory. Overlapping is a
     * transitive property in the context of continuous lines. If A overlaps B,
     * and B overlaps C, then A, B, and C form a single continuous merged interval.
     * This perfectly maps to the concept of Connected Components in an undirected graph.
     * * * Complexity Analysis:
     * - Time (O): O(N^2) to build the adjacency matrix/list by comparing every pair.
     * O(N + E) to traverse components (where E can be up to N^2). Overall Time: O(N^2).
     * - Space (O): O(N^2) heap space for the graph adjacency structures.
     * O(N) auxiliary stack space for DFS/BFS traversal.
     * (Note: Because this is strictly inferior to Phase 1 in both time and space,
     * code implementation is omitted in standard interviews, but mentioning it
     * demonstrates deep architectural thinking).
     * ========================================================================
     */

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A robust main method to validate implementations against standard cases,
     * subset intervals, touching boundaries, and zero-value edge cases.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== MERGE INTERVALS MASTERCLASS TESTING SUITE ===");

        int[][][] testCases = {
                {{1, 3}, {2, 6}, {8, 10}, {15, 18}}, // Standard Case 1
                {{1, 4}, {4, 5}},                    // Standard Case 2 (Touching boundaries)
                {{1, 4}, {2, 3}},                    // Fully enclosed subset
                {{1, 5}, {2, 4}, {3, 6}},            // Multiple cascading overlaps
                {{1, 5}},                            // Single interval
                {{0, 0}, {0, 0}},                    // Zero-value duplicate intervals
                {{2, 3}, {4, 5}, {6, 7}, {8, 9}, {1, 10}} // One massive interval engulfing others
        };

        for (int i = 0; i < testCases.length; i++) {
            int[][] tc = testCases[i];

            // Deep clone to prevent in-place sorting from affecting original test data output logging
            int[][] tcClone1 = deepClone(tc);
            int[][] tcClone2 = deepClone(tc);

            System.out.println("\nTest Case " + (i + 1) + ": " + deepToString(tc));

            // Test Brute Force
            long start1 = System.nanoTime();
            int[][] res1 = mergeBruteForce(tcClone1);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + deepToString(res1) +
                    " (Time: " + (end1 - start1) / 1000 + " us)");

            // Test Optimal
            long start2 = System.nanoTime();
            int[][] res2 = mergeOptimal(tcClone2);
            long end2 = System.nanoTime();
            System.out.println("Optimal Output:     " + deepToString(res2) +
                    " (Time: " + (end2 - start2) / 1000 + " us)");

            // Verification
            boolean isMatch = Arrays.deepEquals(res1, res2);
            System.out.println("Sanity Check Passed: " + isMatch);
        }
    }

    // Helper method to print 2D array cleanly
    private static String deepToString(int[][] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(Arrays.toString(arr[i]));
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper method for deep cloning 2D arrays to ensure pure test states
    private static int[][] deepClone(int[][] arr) {
        int[][] clone = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            clone[i] = arr[i].clone();
        }
        return clone;
    }
}