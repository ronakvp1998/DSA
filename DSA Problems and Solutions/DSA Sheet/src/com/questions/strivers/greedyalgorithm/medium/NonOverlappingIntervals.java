package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 435. Non-overlapping Intervals (Medium)
 * ==================================================================================================
 * Given an array of intervals where intervals[i] = [starti, endi], return the minimum number of
 * intervals you need to remove to make the rest of the intervals non-overlapping.
 * * Note that intervals which only touch at a point are non-overlapping.
 * For example, [1, 2] and [2, 3] are non-overlapping.
 *
 * Example 1:
 * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 * Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
 *
 * Example 2:
 * Input: intervals = [[1,2],[1,2],[1,2]]
 * Output: 2
 * Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
 *
 * Example 3:
 * Input: intervals = [[1,2],[2,3]]
 * Output: 0
 * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Sort by End Time)
 * ==================================================================================================
 * This problem is functionally identical to the classic "Activity Selection Problem"
 * (or N Meetings in One Room).
 * * To MINIMIZE the number of intervals we remove, we must MAXIMIZE the number of non-overlapping
 * intervals we keep.
 * * The best way to maximize the number of intervals we can fit on a timeline is to always pick
 * the interval that ENDS EARLIEST. An interval that ends early leaves the maximum possible
 * room for future intervals.
 * * 1. Sort the intervals in ascending order based on their END times.
 * 2. Keep track of the `end` time of the last interval we safely added to our non-overlapping set.
 * 3. Iterate through the sorted intervals:
 * - If the current interval starts strictly BEFORE our tracked `end` time, it overlaps!
 * We must "remove" it (increment our removal counter).
 * - If it starts ON or AFTER our tracked `end` time, it doesn't overlap. We "keep" it by
 * updating our tracked `end` time to this current interval's end time.
 * ==================================================================================================
 */

import java.util.Arrays;

public class NonOverlappingIntervals {

    public static void main(String[] args) {
        // Test Case 1: Expected 1 (Remove [1,3])
        int[][] intervals1 = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println("Test Case 1 (Expected: 1) -> Result: " + eraseOverlapIntervals(intervals1));

        // Test Case 2: Expected 2 (Remove two [1,2]s)
        int[][] intervals2 = {{1, 2}, {1, 2}, {1, 2}};
        System.out.println("Test Case 2 (Expected: 2) -> Result: " + eraseOverlapIntervals(intervals2));

        // Test Case 3: Expected 0 (Already non-overlapping)
        int[][] intervals3 = {{1, 2}, {2, 3}};
        System.out.println("Test Case 3 (Expected: 0) -> Result: " + eraseOverlapIntervals(intervals3));
    }

    /**
     * Calculates the minimum number of intervals to remove.
     * * @param intervals 2D array of intervals
     * @return Minimum removals required
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        // Edge Case: If there are 0 or 1 intervals, no overlaps can exist.
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }

        // 1. Sort the intervals by their END time in ascending order.
        // We use Integer.compare to avoid potential integer underflow/overflow
        // that can occur with simple subtraction (a[1] - b[1]) if values are large negatives.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int removeCount = 0;

        // 2. Track the end time of the last non-overlapping interval we decided to "keep".
        // We start by "keeping" the very first interval in our sorted list because
        // it is guaranteed to end the earliest.
        int currentEndTime = intervals[0][1];

        // 3. Iterate through the rest of the intervals
        for (int i = 1; i < intervals.length; i++) {

            // Extract the start time of the current interval being evaluated
            int nextStartTime = intervals[i][0];

            // 4. Check for overlap
            // If the next interval starts BEFORE the current one finishes, they overlap.
            if (nextStartTime < currentEndTime) {
                // We must remove this overlapping interval.
                // Notice we DO NOT update currentEndTime here, because we are throwing this interval away.
                removeCount++;
            }
            else {
                // No overlap! The next interval starts after or exactly when the current one ends.
                // We "keep" this interval and update our boundary to its end time.
                currentEndTime = intervals[i][1];
            }
        }

        return removeCount;
    }
}