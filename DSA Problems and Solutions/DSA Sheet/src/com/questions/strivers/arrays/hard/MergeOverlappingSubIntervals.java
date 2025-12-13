package com.questions.strivers.arrays.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
//https://takeuforward.org/data-structure/merge-overlapping-sub-intervals/
/*Problem Statement: Given an array of intervals,
 merge all the overlapping intervals and return an array of non-overlapping intervals.
Example 1:
        Example 1:
        Input: intervals=[[1,3],[2,6],[8,10],[15,18]]
        Output: [[1,6],[8,10],[15,18]]
        Explanation: Since intervals [1,3] and [2,6] are overlapping we can merge them to form [1,6]
        intervals.
        Example 2:
        Input: [[1,4],[4,5]]
        Output: [[1,5]]
        Explanation: Since intervals [1,4] and [4,5] are overlapping we can merge them to form [1,5]. */
public class MergeOverlappingSubIntervals {

    /**
     * 🔍 Approach for mergeOverlappingIntervals():
     * 1. **Sort** the intervals based on the starting time.
     * 2. **Iterate** over the sorted intervals.
     * 3. For each interval, try to merge with all following intervals that overlap.
     * 4. Skip already merged intervals to avoid reprocessing.
     * 5. Add the final merged interval to the answer list.
     *
     * ✅ This approach avoids redundant merging using nested loops and skipping merged intervals.
     *
     * ⏱ Time Complexity: O(n log n + n^2)
     *      - O(n log n) for sorting
     *      - O(n^2) in the worst case due to nested for-loop
     * 🧠 Space Complexity: O(n)
     *      - For storing merged intervals in `ans`
     */
    private static List<List<Integer>> mergeOverlappingIntervals(int[][] arr) {
        int n = arr.length; // total number of intervals

        // 🔽 Step 1: Sort the array by the start time of each interval
        Arrays.sort(arr, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] - b[0]; // Ascending order of start times
            }
        });

        List<List<Integer>> ans = new ArrayList<>(); // List to store the merged intervals

        // 🔁 Step 2: Traverse each interval
        for (int i = 0; i < n; i++) {
            int start = arr[i][0]; // Current interval start
            int end = arr[i][1];   // Current interval end

            // ⏩ Step 3: If this interval is completely covered in a previous merge, skip it
            if (!ans.isEmpty() && end <= ans.get(ans.size() - 1).get(1)) {
                continue;
            }

            // 🔁 Step 4: Try to merge all overlapping intervals
            for (int j = i + 1; j < n; j++) {
                // Check if the current interval overlaps with the next one
                if (arr[j][0] <= end) {
                    end = Math.max(end, arr[j][1]); // Merge by extending the end
                } else {
                    break; // No more overlaps
                }
            }

            // ✅ Step 5: Add the merged interval
            ans.add(Arrays.asList(start, end));
        }

        return ans; // Return the list of merged intervals
    }

    /**
     * 🔍 Optimized Approach for mergeOverlappingIntervals2():
     * 1. Sort the intervals by starting time.
     * 2. Traverse through intervals:
     *      - If there's no overlap with the last in result, add it.
     *      - Else, merge with the last interval by extending its end.
     *
     * ✅ This is the optimal approach using only one pass after sorting.
     *
     * ⏱ Time Complexity: O(n log n)
     *      - O(n log n) for sorting
     *      - O(n) for merging in a single pass
     * 🧠 Space Complexity: O(n)
     *      - For output list `ans`
     */
    private static List<List<Integer>> mergeOverlappingIntervals2(int[][] arr) {
        int n = arr.length;

        // 🔽 Step 1: Sort the intervals based on start time
        Arrays.sort(arr, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });

        List<List<Integer>> ans = new ArrayList<>(); // Output list

        // 🔁 Step 2: Iterate and merge
        for (int i = 0; i < n; i++) {
            // 🧾 Case 1: No overlap with last merged interval
            if (ans.isEmpty() || arr[i][0] > ans.get(ans.size() - 1).get(1)) {
                ans.add(Arrays.asList(arr[i][0], arr[i][1])); // Add new interval
            }
            // 🔗 Case 2: Overlap exists → merge with last interval
            else {
                // Update the end of last interval to max of both
                ans.get(ans.size() - 1).set(1,
                        Math.max(ans.get(ans.size() - 1).get(1), arr[i][1]));
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] arr = {{1, 3}, {8, 10}, {2, 6}, {15, 18}};

        // You can use either function here
        List<List<Integer>> ans = mergeOverlappingIntervals(arr);
        // List<List<Integer>> ans = mergeOverlappingIntervals2(arr);

        System.out.print("The merged intervals are: \n");
        for (List<Integer> it : ans) {
            System.out.print("[" + it.get(0) + ", " + it.get(1) + "] ");
        }
        System.out.println();
    }
}
