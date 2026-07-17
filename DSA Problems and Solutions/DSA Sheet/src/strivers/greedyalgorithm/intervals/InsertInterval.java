package strivers.greedyalgorithm.intervals;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: 57. Insert Interval
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer & Competitive Programming Evaluator
 *
 * ### 1. Header & Problem Context
 *
 * PROBLEM STATEMENT:
 * You are given an array of non-overlapping intervals `intervals` where
 * `intervals[i] = [starti, endi]` represent the start and the end of the ith
 * interval and `intervals` is sorted in ascending order by `starti`. You are
 * also given an interval `newInterval = [start, end]` that represents the
 * start and end of another interval.
 *
 * Insert `newInterval` into `intervals` such that `intervals` is still sorted
 * in ascending order by `starti` and `intervals` still does not have any
 * overlapping intervals (merge overlapping intervals if necessary).
 *
 * Return `intervals` after the insertion.
 * Note that you don't need to modify `intervals` in-place. You can make a
 * new array and return it.
 *
 * EXAMPLES:
 * Example 1:
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 *
 * Example 2:
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 *
 * CONSTRAINTS:
 * - 0 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= starti <= endi <= 10^5
 * - intervals is sorted by starti in ascending order.
 * - newInterval.length == 2
 * - 0 <= start <= end <= 10^5
 *
 * ============================================================================
 * ### 2.2. Progressive Implementation Roadmap (For Non-DP Problems)
 * ============================================================================
 *
 * Phase 1: Optimal Approach (Single Pass Linear Scan) - The "Master it" Stage
 *          Leverages the sorted nature of the input to insert and merge in exactly O(N).
 * Phase 2: Brute Force Approach (Insert, Sort & Merge) - The "Think it" Stage
 *          Treats the problem like a standard merge intervals problem by adding
 *          the new interval, re-sorting, and then merging.
 * Phase 3: Alternative Approach (Binary Search)
 *          Uses Binary Search to find the exact insertion and merge bounds,
 *          optimizing the search phase (though array copying still makes it O(N)).
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertInterval {

    /**
     * ========================================================================
     * ### 3. In-Code Technical Analysis
     * PHASE 1: OPTIMAL APPROACH (Single Pass Linear Scan)
     * ========================================================================
     *
     * DETAILED INTUITION:
     * Since the array is already sorted, we don't need to re-sort it. We can
     * process the intervals in a single sweep from left to right, dividing the
     * problem into three distinct, logical phases:
     *
     * 1. Left Side (Strictly Before): Any interval that completely ends before
     *    the `newInterval` starts (`interval[1] < newInterval[0]`) has no overlap.
     *    We can safely add these to our result.
     * 2. The Overlap Zone: If an interval doesn't end before `newInterval` starts,
     *    and it starts before `newInterval` ends (`interval[0] <= newInterval[1]`),
     *    they overlap. We "swallow" the current interval by updating `newInterval`'s
     *    start to the minimum start and end to the maximum end. We repeat this
     *    until we break out of the overlap zone. We then add this merged `newInterval`.
     * 3. Right Side (Strictly After): Any remaining intervals start strictly after
     *    the merged `newInterval` ends. We simply append them to the result.
     *
     * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N), where N is the number of intervals. We visit
     *   each interval exactly once.
     * - Space Complexity: O(N) heap space used for the `result` List to store
     *   the merged intervals before returning them as an array. O(1) auxiliary
     *   stack space.
     * ========================================================================
     */
    public int[][] insertOptimal(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Phase 1: Add all intervals strictly before the new interval
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2: Merge overlapping intervals
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        // Add the fully merged interval
        result.add(newInterval);

        // Phase 3: Add all remaining intervals strictly after the new interval
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    /**
     * ========================================================================
     * ### 3. In-Code Technical Analysis
     * PHASE 2: BRUTE FORCE APPROACH (Insert, Sort & Merge)
     * ========================================================================
     *
     * DETAILED INTUITION:
     * If we temporarily forget that the array is already sorted, we can reduce
     * this problem to standard LeetCode 56: Merge Intervals.
     * 1. Append the `newInterval` to the end of the existing `intervals` array.
     * 2. Sort the entirely new array based on the start times.
     * 3. Iterate through the sorted array. If the current interval overlaps
     *    with the last interval added to our result list, merge them. Otherwise,
     *    add it as a distinct interval.
     *
     * This approach is highly robust but ignores the strict ascending order
     * constraint provided by the problem, resulting in unnecessary sorting overhead.
     *
     * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N log N) dominated by the sorting step of an array
     *   of size N + 1.
     * - Space Complexity: O(N) heap space for the combined array and result list.
     * ========================================================================
     */
    public int[][] insertBruteForce(int[][] intervals, int[] newInterval) {
        // Step 1: Combine arrays
        int[][] combined = new int[intervals.length + 1][2];
        for (int i = 0; i < intervals.length; i++) {
            combined[i] = intervals[i];
        }
        combined[intervals.length] = newInterval;

        // Step 2: Sort by start time
        Arrays.sort(combined, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 3: Merge Overlapping Intervals
        List<int[]> result = new ArrayList<>();
        int[] currentMerge = combined[0];
        result.add(currentMerge);

        for (int[] interval : combined) {
            int currentEnd = currentMerge[1];
            int nextStart = interval[0];
            int nextEnd = interval[1];

            if (currentEnd >= nextStart) { // Overlap detected
                currentMerge[1] = Math.max(currentEnd, nextEnd);
            } else { // No overlap, move to next
                currentMerge = interval;
                result.add(currentMerge);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    /**
     * ========================================================================
     * ### 3. In-Code Technical Analysis
     * PHASE 3: ALTERNATIVE APPROACH (Binary Search)
     * ========================================================================
     *
     * DETAILED INTUITION:
     * While Phase 1's linear scan is optimal overall (because array reconstruction
     * always takes O(N) time), we can optimize the *search* phase using Binary Search.
     * We can find exactly where the left bound of the overlap begins and where
     * the right bound of the overlap ends in O(log N) time.
     *
     * However, constructing the final List/Array still requires copying the
     * unaffected left and right halves, capping the theoretical lower bound
     * at O(N). This approach is mainly useful if the overlap merge logic is
     * extremely expensive or if we were using a data structure like a TreeMap/TreeSet.
     *
     * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N). Binary search takes O(log N), but shifting/copying
     *   elements into the result list takes O(N).
     * - Space Complexity: O(N) for the resulting output array.
     * ========================================================================
     */
    public int[][] insertBinarySearch(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) return new int[][]{newInterval};

        List<int[]> result = new ArrayList<>();
        int n = intervals.length;

        // Find the first interval that overlaps or is strictly after newInterval
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][1] < newInterval[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        int startIndex = left;

        // Find the last interval that overlaps or is strictly before newInterval
        left = 0;
        right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][0] > newInterval[1]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        int endIndex = right;

        // 1. Add strictly left intervals
        for (int i = 0; i < startIndex; i++) {
            result.add(intervals[i]);
        }

        // 2. Merge the middle overlapping section (if any overlap exists)
        if (startIndex <= endIndex) {
            newInterval[0] = Math.min(newInterval[0], intervals[startIndex][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[endIndex][1]);
        }
        result.add(newInterval);

        // 3. Add strictly right intervals
        for (int i = endIndex + 1; i < n; i++) {
            result.add(intervals[i]);
        }

        return result.toArray(new int[result.size()][]);
    }

    /**
     * ========================================================================
     * ### 4. Testing Suite
     * ========================================================================
     * Comprehensive tests against standard scenarios and tricky edge cases.
     */
    public static void main(String[] args) {
        InsertInterval solver = new InsertInterval();

        record TestCase(int[][] intervals, int[] newInterval, String name) {}

        List<TestCase> testCases = Arrays.asList(
                new TestCase(
                        new int[][]{{1, 3}, {6, 9}},
                        new int[]{2, 5},
                        "Example 1: Standard Overlap"
                ),
                new TestCase(
                        new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}},
                        new int[]{4, 8},
                        "Example 2: Multiple Overlaps"
                ),
                new TestCase(
                        new int[][]{},
                        new int[]{5, 7},
                        "Edge Case: Empty Intervals Array"
                ),
                new TestCase(
                        new int[][]{{1, 5}},
                        new int[]{2, 3},
                        "Edge Case: New Interval completely swallowed"
                ),
                new TestCase(
                        new int[][]{{1, 5}},
                        new int[]{6, 8},
                        "Edge Case: No Overlap, Insert at End"
                ),
                new TestCase(
                        new int[][]{{6, 8}},
                        new int[]{1, 5},
                        "Edge Case: No Overlap, Insert at Beginning"
                ),
                new TestCase(
                        new int[][]{{1, 2}, {3, 4}, {5, 6}},
                        new int[]{1, 6},
                        "Edge Case: New Interval swallows ALL"
                )
        );

        System.out.println("🚀 Executing Insert Interval Testing Suite 🚀\n");

        testCases.forEach(test -> {
            System.out.println("Test Case: " + test.name());
            System.out.println("Intervals: " + Arrays.deepToString(test.intervals()));
            System.out.println("New Int  : " + Arrays.toString(test.newInterval()));

            // Reusing identical input requires deep copy to avoid mutation pollution
            // between different approach calls during testing.

            int[][] resOptimal = solver.insertOptimal(deepCopy(test.intervals()), test.newInterval().clone());
            int[][] resBrute = solver.insertBruteForce(deepCopy(test.intervals()), test.newInterval().clone());
            int[][] resBinary = solver.insertBinarySearch(deepCopy(test.intervals()), test.newInterval().clone());

            System.out.printf("  -> Optimal (Linear) : %s%n", Arrays.deepToString(resOptimal));
            System.out.printf("  -> Brute Force      : %s%n", Arrays.deepToString(resBrute));
            System.out.printf("  -> Binary Search    : %s%n", Arrays.deepToString(resBinary));
            System.out.println("-".repeat(70));
        });
    }

    /** Helper method to deep copy 2D arrays for clean testing */
    private static int[][] deepCopy(int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(int[]::clone).toArray($ -> matrix.clone());
    }
}