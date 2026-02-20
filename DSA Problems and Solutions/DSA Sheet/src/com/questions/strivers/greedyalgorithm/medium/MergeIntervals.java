package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 56. Merge Intervals (Medium)
 * ==================================================================================================
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals,
 * and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 * Example 1:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 *
 * Example 2:
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * * Example 3:
 * Input: intervals = [[4,7],[1,4]]
 * Output: [[1,7]]
 * Explanation: Intervals [1,4] and [4,7] are considered overlapping.
 * ==================================================================================================
 * APPROACH: SORTING & LINEAR SCAN
 * ==================================================================================================
 * The key to solving this problem efficiently is realizing that if the intervals are sorted by
 * their START times, any overlapping intervals will naturally end up right next to each other
 * in the array.
 * * 1. Sort the intervals based on their start values.
 * 2. Create a list to hold the merged intervals.
 * 3. Keep a reference to a `currentInterval` that we are actively building.
 * 4. Iterate through the array:
 * - If the next interval's start time is LESS THAN OR EQUAL to the `currentInterval`'s end time,
 * they overlap! We "merge" them by extending the end time of `currentInterval` to the
 * maximum of both end times.
 * - If the next interval starts strictly AFTER the `currentInterval` ends, they do not overlap.
 * The `currentInterval` is finalized. We add the next interval to our list and make it
 * our new `currentInterval`.
 * ==================================================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {

    public static void main(String[] args) {
        // Test Case 1
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("Test Case 1 -> " + Arrays.deepToString(merge(intervals1)));
        // Expected: [[1, 6], [8, 10], [15, 18]]

        // Test Case 2
        int[][] intervals2 = {{1, 4}, {4, 5}};
        System.out.println("Test Case 2 -> " + Arrays.deepToString(merge(intervals2)));
        // Expected: [[1, 5]]

        // Test Case 3 (Out of order input)
        int[][] intervals3 = {{4, 7}, {1, 4}};
        System.out.println("Test Case 3 -> " + Arrays.deepToString(merge(intervals3)));
        // Expected: [[1, 7]]
    }

    /**
     * Merges all overlapping intervals.
     * * @param intervals 2D array of intervals
     * @return A new 2D array of merged, non-overlapping intervals
     */
    public static int[][] merge(int[][] intervals) {
        // Edge Case: If the array is empty or has only 1 interval, no merging is needed.
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // 1. Sort the intervals by their START time in ascending order.
        // We use Integer.compare to prevent potential overflow issues that can happen with simple subtraction (a[0] - b[0]).
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();

        // 2. Initialize the `currentInterval` with the first interval in the sorted array
        int[] currentInterval = intervals[0];

        // Add it to the result list immediately.
        // Because we hold a reference to the array object, modifying `currentInterval[1]` later
        // will automatically update the value stored inside the `result` list!
        result.add(currentInterval);

        // 3. Iterate through the remaining intervals
        for (int[] nextInterval : intervals) {

            // Extract boundaries for readability
            int currentEnd = currentInterval[1];
            int nextStart = nextInterval[0];
            int nextEnd = nextInterval[1];

            // 4. Check for Overlap
            // If the next interval starts before or exactly when the current one ends...
            if (currentEnd >= nextStart) {
                // OVERLAP DETECTED!
                // Merge them by updating the end time of the current interval.
                // We take the max because the next interval might be completely swallowed
                // by the current one (e.g., merging [1, 5] and [2, 3] should result in [1, 5]).
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            }
            else {
                // NO OVERLAP
                // The current interval is completely finished.
                // We move on to the next interval, add it to our list, and track it.
                currentInterval = nextInterval;
                result.add(currentInterval);
            }
        }

        // 5. Convert the dynamic List back into a static 2D array to match the return type
        return result.toArray(new int[result.size()][]);
    }
}