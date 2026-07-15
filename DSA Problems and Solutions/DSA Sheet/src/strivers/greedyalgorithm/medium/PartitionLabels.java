package strivers.greedyalgorithm.medium;

/**
 * ============================================================================
 * 763. Partition Labels
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * You are given a string s. We want to partition the string into as many parts
 * as possible so that each letter appears in at most one part.
 *
 * Note that the partition is done so that after concatenating all the parts
 * in order, the resultant string should be s.
 * Return a list of integers representing the size of these parts.
 *
 * Example 1:
 * Input: s = "ababcbacadefegdehijhklij"
 * Output: [9, 7, 8]
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it
 * splits s into less parts.
 *
 * Example 2:
 * Input: s = "eccbbbbdec"
 * Output: [10]
 *
 * Constraints:
 * 1 <= s.length <= 500
 * s consists of lowercase English letters.
 *
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Comparator;

public class PartitionLabels {

    /**
     * ============================================================================
     * PHASE 1: Optimal Approach (Greedy with Last Occurrence) - The "Best" stage.
     * ============================================================================
     * Detailed Intuition:
     * To ensure a letter appears in at most one part, the partition containing that
     * letter MUST extend to at least its last occurrence in the string.
     * 1. First Pass: We record the last occurrence index of every character.
     * 2. Second Pass: We iterate through the string, maintaining a `start` and `end`
     *    pointer for our current partition. For every character we visit, we update
     *    our `end` to be the maximum of the current `end` and the character's last
     *    occurrence index.
     * 3. When our current index `i` catches up to `end` (i == end), it means all
     *    characters we've seen so far do not appear anywhere further in the string.
     *    We can safely close this partition, record its size, and start a new one.
     *
     * Complexity Analysis:
     * Time: O(N) - We traverse the string of length N exactly twice.
     * Space: O(1) - The `lastOccurrence` array is always size 26 (lowercase English
     *               letters), which is constant extra heap space. Auxiliary stack
     *               space is O(1).
     */
    public List<Integer> phase1OptimalGreedy(String s) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0) return result;

        // Map to store the last occurrence of each character
        int[] lastOccurrence = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence[s.charAt(i) - 'a'] = i;
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            // Extend the boundary of the current partition if necessary
            end = Math.max(end, lastOccurrence[s.charAt(i) - 'a']);

            // If we reach the boundary, we've found a valid partition
            if (i == end) {
                result.add(end - start + 1);
                start = i + 1; // Reset start for the next partition
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * Without preprocessing the last occurrences, we can iteratively expand our
     * partition. We start a partition at `start`. For every character inside the
     * current partition bounds, we manually scan the REST of the string to find
     * its last occurrence. If we find it further down, we stretch our partition's
     * `end`. We repeat this until we've checked every character in the current chunk.
     *
     * Complexity Analysis:
     * Time: O(N^2) - For each partition, we iterate through its characters, and for
     *       each character, we might scan the remaining string to find its last occurrence.
     * Space: O(1) - Only a few integer variables are used. O(1) auxiliary stack space.
     */
    public List<Integer> phase2BruteForce(String s) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0) return result;

        int start = 0;
        while (start < s.length()) {
            int end = start;

            // Expand 'end' by checking every character in the current bounds
            for (int i = start; i <= end; i++) {
                char currentChar = s.charAt(i);

                // Scan the rest of the string to find the last occurrence
                for (int j = s.length() - 1; j > end; j--) {
                    if (s.charAt(j) == currentChar) {
                        end = j; // Stretch the partition boundary
                        break;
                    }
                }
            }

            // Add the chunk size
            result.add(end - start + 1);
            start = end + 1;
        }

        return result;
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Interval Merging
     * ============================================================================
     * Detailed Intuition:
     * We can reframe this problem as standard "Merge Intervals".
     * 1. Find the first and last occurrence for every character that exists in the
     *    string, forming an interval [first, last].
     * 2. Sort these intervals based on their starting positions.
     * 3. Merge overlapping intervals. The sizes of the resulting merged intervals
     *    are the sizes of our partitions.
     *
     * Complexity Analysis:
     * Time: O(N + 1) - O(N) to find intervals. Sorting takes O(K log K) where K <= 26
     *       (number of unique characters). Merging takes O(K). Thus, effectively O(N).
     * Space: O(1) - The intervals array is at most size 26, which is constant.
     */
    public List<Integer> phase3IntervalMerging(String s) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0) return result;

        // [start, end] for each character, initialized to -1
        int[][] intervals = new int[26][2];
        for (int i = 0; i < 26; i++) Arrays.fill(intervals[i], -1);

        // Populate first and last occurrences
        for (int i = 0; i < s.length(); i++) {
            int charIdx = s.charAt(i) - 'a';
            if (intervals[charIdx][0] == -1) {
                intervals[charIdx][0] = i; // First occurrence
            }
            intervals[charIdx][1] = i;     // Update last occurrence
        }

        // Filter out non-existent characters and sort by start index
        int[][] validIntervals = Arrays.stream(intervals)
                .filter(interval -> interval[0] != -1)
                .sorted(Comparator.comparingInt(a -> a[0]))
                .toArray(int[][]::new);

        // Merge overlapping intervals
        int currentStart = validIntervals[0][0];
        int currentEnd = validIntervals[0][1];

        for (int i = 1; i < validIntervals.length; i++) {
            if (validIntervals[i][0] <= currentEnd) {
                // Overlapping interval, stretch the end
                currentEnd = Math.max(currentEnd, validIntervals[i][1]);
            } else {
                // Non-overlapping interval, record previous size and reset
                result.add(currentEnd - currentStart + 1);
                currentStart = validIntervals[i][0];
                currentEnd = validIntervals[i][1];
            }
        }

        // Add the final interval
        result.add(currentEnd - currentStart + 1);

        return result;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     * Executing comprehensive tests across approaches using Java 8 Streams.
     */
    public static void main(String[] args) {
        PartitionLabels solver = new PartitionLabels();

        // Define Test Cases
        String[] testCases = {
                "ababcbacadefegdehijhklij",  // Example 1: Standard case
                "eccbbbbdec",                // Example 2: One large partition wrapping others
                "a",                         // Edge Case: Single character
                "abc",                       // Edge Case: All unique characters
                "aaaaa",                     // Edge Case: All same characters
                "abaccbdeffed"               // Mixed case
        };

        String[] approachNames = {
                "Phase 1: Optimal (Greedy)  ",
                "Phase 2: Brute Force (O(N2))",
                "Phase 3: Interval Merging  "
        };

        System.out.println("=================================================");
        System.out.println("       TESTING PARTITION LABELS SOLUTIONS        ");
        System.out.println("=================================================\n");

        IntStream.range(0, testCases.length).forEach(i -> {
            String s = testCases[i];

            System.out.println("Test Case " + (i + 1) + ": \"" + s + "\"");

            List<List<Integer>> results = Arrays.asList(
                    solver.phase1OptimalGreedy(s),
                    solver.phase2BruteForce(s),
                    solver.phase3IntervalMerging(s)
            );

            for (int j = 0; j < results.size(); j++) {
                System.out.println(approachNames[j] + " => " + results.get(j));
            }
            System.out.println("-------------------------------------------------");
        });

        System.out.println("All implementations executed successfully.");
    }
}