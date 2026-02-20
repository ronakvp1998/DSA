package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 57. Insert Interval (Medium)
 * ==================================================================================================
 * You are given an array of non-overlapping intervals where intervals[i] = [starti, endi]
 * represent the start and the end of the ith interval and intervals is sorted in ascending order
 * by starti. You are also given an interval newInterval = [start, end] that represents the start
 * and end of another interval.
 * * Insert newInterval into intervals such that intervals is still sorted in ascending order by
 * starti and intervals still does not have any overlapping intervals (merge overlapping intervals
 * if necessary).
 * * Return intervals after the insertion.
 * Note that you don't need to modify intervals in-place. You can make a new array and return it.
 *
 * Example 1:
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 *
 * Example 2:
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 * ==================================================================================================
 * APPROACH: SINGLE PASS LINEAR SCAN (Three Phases)
 * ==================================================================================================
 * Since the input array is already sorted by start time, we don't need to sort it again.
 * We can solve this elegantly in a single linear pass by dividing the problem into 3 distinct phases:
 * * Phase 1 (Left side): Add all intervals that completely end BEFORE the newInterval starts.
 * Phase 2 (Overlap): Merge all intervals that overlap with the newInterval into one massive interval.
 * Phase 3 (Right side): Add all the remaining intervals that start AFTER the newInterval ends.
 * ==================================================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertInterval {

    public static void main(String[] args) {
        // Test Case 1
        int[][] intervals1 = {{1, 3}, {6, 9}};
        int[] newInterval1 = {2, 5};
        System.out.println("Test Case 1 -> " + Arrays.deepToString(insert(intervals1, newInterval1)));
        // Expected: [[1, 5], [6, 9]]

        // Test Case 2
        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval2 = {4, 8};
        System.out.println("Test Case 2 -> " + Arrays.deepToString(insert(intervals2, newInterval2)));
        // Expected: [[1, 2], [3, 10], [12, 16]]
    }

    /**
     * Inserts a new interval into a sorted array of disjoint intervals and merges overlaps.
     * * @param intervals Sorted array of non-overlapping intervals
     * @param newInterval The interval to insert
     * @return A new 2D array of merged intervals
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // ------------------------------------------------------------------
        // PHASE 1: Add all intervals that come strictly BEFORE the newInterval
        // ------------------------------------------------------------------
        // An interval comes before if its END time is less than the newInterval's START time.
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // ------------------------------------------------------------------
        // PHASE 2: Merge overlapping intervals
        // ------------------------------------------------------------------
        // An interval overlaps if its START time is less than or equal to the newInterval's END time.
        // We continuously expand the bounds of our newInterval to swallow overlapping ones.
        while (i < n && intervals[i][0] <= newInterval[1]) {
            // Update the start time to the absolute minimum of both
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);

            // Update the end time to the absolute maximum of both
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);

            i++;
        }
        // Add the newly constructed, fully merged interval to our result
        result.add(newInterval);

        // ------------------------------------------------------------------
        // PHASE 3: Add all remaining intervals that come strictly AFTER
        // ------------------------------------------------------------------
        // Everything left over is guaranteed to start after the merged newInterval ends.
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        // Convert the dynamic List back to a static 2D array for the final return type
        return result.toArray(new int[result.size()][]);
    }
}