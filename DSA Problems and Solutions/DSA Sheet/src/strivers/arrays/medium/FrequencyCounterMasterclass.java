package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: COUNT FREQUENCY OF EACH ELEMENT IN THE ARRAY
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * Problem Statement:
 * Given an array, we have found the number of occurrences of each element in the array.
 * * Examples:
 * Example 1:
 * Input: arr[] = {10,5,10,15,10,5};
 * Output: 10  3
 * 5   2
 * 15  1
 * Explanation: 10 occurs 3 times in the array
 * 5 occurs 2 times in the array
 * 15 occurs 1 time in the array
 * * Example 2:
 * Input: arr[] = {2,2,3,4,4,2};
 * Output: 2  3
 * 3  1
 * 4  2
 * Explanation: 2 occurs 3 times in the array
 * 3 occurs 1 time in the array
 * 4 occurs 2 times in the array
 * * Constraints (Inferred for standard practice):
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * ---
 * * ### Conceptual Visualization (Hash Map State Tracking)
 * * Since this is an Array/Hashing problem and not Dynamic Programming, we visualize
 * the state of our optimal Hash Map as we traverse the array.
 * * Input: [10, 5, 10, 15, 10, 5]
 * * Step | Element | Map State (Element : Frequency)
 * ------------------------------------------------
 * 1   |   10    | {10: 1}
 * 2   |    5    | {10: 1, 5: 1}
 * 3   |   10    | {10: 2, 5: 1}
 * 4   |   15    | {10: 2, 5: 1, 15: 1}
 * 5   |   10    | {10: 3, 5: 1, 15: 1}
 * 6   |    5    | {10: 3, 5: 2, 15: 1}  <-- Final State
 * * Note: To maintain the output order as the first appearance of elements (like in Example 1),
 * a LinkedHashMap is optimal.
 * * ============================================================================
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FrequencyCounterMasterclass {

    /**
     * ========================================================================
     * ### 2.2 Progressive Implementation Roadmap
     * * Phase 1: Brute Force Approach - The "Think it" stage.
     * Approach:
     * Use two nested loops. The outer loop selects an element, and the inner loop
     * counts its occurrences in the array. To avoid counting the same element
     * multiple times, we use a boolean `visited` array of the same size.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * This is the most literal translation of the problem. "For every element,
     * count how many times it appears." We just need the `visited` array to
     * ensure that when we encounter the second '10', we don't count it all over again.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2)
     * For every unvisited element, we scan the rest of the array. In the worst case
     * (all distinct elements), we do N + (N-1) + ... + 1 operations.
     * - Space Complexity: O(N)
     * We allocate a boolean `visited` array of size N in heap space. Auxiliary
     * stack space is O(1).
     * ========================================================================
     */
    public Map<Integer, Integer> countFrequencyBruteForce(int[] arr) {
        int n = arr.length;
        boolean[] visited = new boolean[n];
        // Using LinkedHashMap merely to return the result in insertion order for testing
        Map<Integer, Integer> result = new LinkedHashMap<>();

        for (int i = 0; i < n; i++) {
            // Skip this element if already processed
            if (visited[i]) {
                continue;
            }

            int count = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] == arr[j]) {
                    visited[j] = true;
                    count++;
                }
            }
            result.put(arr[i], count);
        }
        return result;
    }

    /**
     * ========================================================================
     * Phase 2: Sorting Approach (Alternative) - The "Refine it" stage.
     * Approach:
     * Sort the array first. This groups all identical elements adjacently. Then,
     * traverse the array linearly, keeping a running count. Whenever the current
     * element differs from the previous one, record the count and reset it.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * Scanning an unsorted array takes O(N) for *each* element. If we sort it
     * first, we pay a one-time cost of O(N log N). Once sorted, finding duplicates
     * is trivial because they are sitting right next to each other. We can count
     * them in a single O(N) pass.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N)
     * Sorting the array is the dominant operation. The linear traversal takes O(N).
     * - Space Complexity: O(1) or O(log N)
     * Heap space is O(1) as we sort in place. Auxiliary stack space for standard
     * sorting algorithms (Dual-Pivot Quicksort) is typically O(log N).
     * (Note: This alters the original array and loses original insertion order).
     * ========================================================================
     */
    public Map<Integer, Integer> countFrequencySorting(int[] arr) {
        if (arr == null || arr.length == 0) return new HashMap<>();

        // Create a copy to avoid mutating the original array for our test suite
        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        Map<Integer, Integer> result = new LinkedHashMap<>();
        int count = 1;

        for (int i = 1; i < sortedArr.length; i++) {
            if (sortedArr[i] == sortedArr[i - 1]) {
                count++;
            } else {
                result.put(sortedArr[i - 1], count);
                count = 1; // reset for the new element
            }
        }
        // Don't forget the last element's group!
        result.put(sortedArr[sortedArr.length - 1], count);

        return result;
    }

    /**
     * ========================================================================
     * Phase 3: Optimal Hash Map Approach - The "Perfect it" stage.
     * Approach:
     * Iterate through the array exactly once. Use a Hash Map where the key is
     * the element and the value is its frequency. For each element, increment
     * its count in the map.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * To achieve an O(N) time complexity without modifying the input array, we
     * trade space for time. A Hash Table provides O(1) average time complexity
     * for lookups and insertions. By using a LinkedHashMap, we also preserve the
     * exact order of the elements' first appearances.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the array exactly once. Hash map `getOrDefault` and `put`
     * operations are O(1) on average.
     * - Space Complexity: O(N)
     * We allocate heap space for the Map. In the worst case (all elements are
     * unique), the Map stores N distinct key-value pairs. Auxiliary stack
     * space is O(1).
     * ========================================================================
     */
    public Map<Integer, Integer> countFrequencyOptimal(int[] arr) {
        // LinkedHashMap maintains insertion order to perfectly match the Example 1 output
        Map<Integer, Integer> frequencyMap = new LinkedHashMap<>();

        for (int num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        return frequencyMap;
    }

    /**
     * ========================================================================
     * ### 4. Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        FrequencyCounterMasterclass solution = new FrequencyCounterMasterclass();

        // Define Test Cases
        int[][] testCases = {
                {10, 5, 10, 15, 10, 5},    // Example 1: Standard case
                {2, 2, 3, 4, 4, 2},        // Example 2: Consecutive and non-consecutive duplicates
                {1, 2, 3, 4, 5},           // Edge Case: All distinct elements
                {7, 7, 7, 7, 7},           // Edge Case: All identical elements
                {-5, 0, -5, 10, 0},        // Edge Case: Negative numbers and zero
                {}                         // Edge Case: Empty array
        };

        System.out.println("=========================================================");
        System.out.println("Executing Frequency Counter Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(arr));

            // Test Brute Force
            long start1 = System.nanoTime();
            Map<Integer, Integer> res1 = solution.countFrequencyBruteForce(arr);
            long end1 = System.nanoTime();

            // Test Sorting Approach
            long start2 = System.nanoTime();
            Map<Integer, Integer> res2 = solution.countFrequencySorting(arr);
            long end2 = System.nanoTime();

            // Test Optimal Approach
            long start3 = System.nanoTime();
            Map<Integer, Integer> res3 = solution.countFrequencyOptimal(arr);
            long end3 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + formatMap(res1) + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Sorting]     Output: " + formatMap(res2) + " | Time: " + (end2 - start2) + " ns");
            System.out.println("  [Hash Map]    Output: " + formatMap(res3) + " | Time: " + (end3 - start3) + " ns");

            System.out.println("---------------------------------------------------------");
        }
    }

    /**
     * Helper method to format the Map output cleanly, mirroring the prompt's requested style.
     */
    private static String formatMap(Map<Integer, Integer> map) {
        if (map.isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder("{ ");
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }
        sb.append("}");
        return sb.toString();
    }
}