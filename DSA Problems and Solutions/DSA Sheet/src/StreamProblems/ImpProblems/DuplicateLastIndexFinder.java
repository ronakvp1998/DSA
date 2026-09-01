package StreamProblems.ImpProblems;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given a List of Integers, identify all elements that appear more than once (duplicates).
 * For each duplicate element, find its LAST occurring index in the original list.
 * Return a List of integer arrays, where each array is of the format {element, lastIndex}.
 *
 * Constraints:
 * - The input list may contain duplicate elements.
 * - The list can be empty.
 * - Output order does not strictly matter, but elements should be distinct.
 * - MUST use strictly Java 8 Stream API.
 *
 * Input/Output Formats:
 * Input: List<Integer>
 * Output: List<int[]> where int[] = {duplicate_element, last_index}
 *
 * Examples:
 * Example 1:
 * Input: [1, 2, 3, 2, 1, 4]
 * Output: [[1, 4], [2, 3]]
 * Explanation: '1' repeats and its last index is 4. '2' repeats and its last index is 3.
 *
 * Example 2:
 * Input: [5, 5, 5]
 * Output: [[5, 2]]
 * Explanation: '5' repeats multiple times, last seen at index 2.
 *
 * Example 3:
 * Input: [7, 8, 9]
 * Output: []
 * Explanation: No duplicates exist.
 * ============================================================================
 */
public class DuplicateLastIndexFinder {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Stream toMap Merge Strategy)
     * ========================================================================
     * Detailed Intuition:
     * Instead of collecting all indices (which wastes space), we can map each element
     * directly to an array holding {count, last_index}. We use IntStream to iterate
     * over the indices, convert to a Map using Collectors.toMap, and merge conflicts.
     * When a collision occurs (same element), we sum the counts and keep the Math.max
     * of the indices. Finally, we filter the map entries where count > 1 and map
     * them to the required output format.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the number of elements in the list.
     *   We do a single pass to build the map, and another pass over unique elements.
     * - Space Complexity: O(U) where U is the number of unique elements in the list.
     *   This is the optimal space usage as we only store state per unique element.
     */
    public static List<int[]> findDuplicatesOptimal(List<Integer> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();

        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.toMap(
                        list::get, // Key: The element
                        i -> new int[]{1, i}, // Value: int[]{count, index}
                        (existing, incoming) -> new int[]{
                                existing[0] + incoming[0], // Sum counts
                                Math.max(existing[1], incoming[1]) // Track max index
                        }
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue()[0] > 1) // Only duplicates
                .map(entry -> new int[]{entry.getKey(), entry.getValue()[1]})
                .collect(Collectors.toList());
    }

    /**
     * ========================================================================
     * PHASE 2: ALTERNATIVE OPTIMAL APPROACH (Grouping By Indices)
     * ========================================================================
     * Detailed Intuition:
     * We stream over the indices of the list and group them by the actual element
     * at that index using Collectors.groupingBy. This yields a Map<Integer, List<Integer>>
     * where the key is the element and the value is a list of all indices where it appears.
     * Since IntStream.range processes in ascending order, the last index in the grouped
     * list will always be the maximum (last) index. We filter for lists with size > 1.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the size of the list.
     * - Space Complexity: O(N) auxiliary space. In the worst case (all identical elements),
     *   the value list in the map will store all N indices. Less space efficient than Phase 1.
     */
    public static List<int[]> findDuplicatesGrouping(List<Integer> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();

        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.groupingBy(list::get))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1) // Only duplicates
                .map(entry -> {
                    List<Integer> indices = entry.getValue();
                    // The indices list is naturally sorted because of IntStream.range
                    int lastIndex = indices.get(indices.size() - 1);
                    return new int[]{entry.getKey(), lastIndex};
                })
                .collect(Collectors.toList());
    }

    /**
     * ========================================================================
     * PHASE 3: BRUTE FORCE APPROACH (Collections API inside Streams)
     * ========================================================================
     * Detailed Intuition:
     * The "Think it" stage. We stream the list elements themselves, remove duplicates
     * using .distinct(), and then use Collections.frequency() to check for duplicates
     * and list.lastIndexOf() to find the required index. This is purely declarative
     * but highly inefficient because frequency() and lastIndexOf() both scan the
     * entire list for every single distinct element.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * U) where N is list size and U is unique elements.
     *   In the worst case (all unique), this devolves to O(N^2).
     * - Space Complexity: O(U) for the distinct elements stream processing.
     */
    public static List<int[]> findDuplicatesBruteForce(List<Integer> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();

        return list.stream()
                .distinct() // Process each unique element only once
                .filter(e -> Collections.frequency(list, e) > 1) // O(N) operation inside filter
                .map(e -> new int[]{e, list.lastIndexOf(e)}) // O(N) operation inside map
                .collect(Collectors.toList());
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        List<List<Integer>> testCases = Arrays.asList(
                Arrays.asList(1, 2, 3, 2, 1, 4),        // Standard case with multiple duplicates
                Arrays.asList(5, 5, 5, 5),              // Single element duplicated many times
                Arrays.asList(1, 2, 3, 4, 5),           // No duplicates
                Arrays.asList(),                        // Empty list edge case
                Arrays.asList(0, 0, -1, -1, 0)          // Zero and negative values
        );

        int caseNum = 1;
        for (List<Integer> testCase : testCases) {
            System.out.println("Test Case " + caseNum++ + ": " + testCase);

            List<int[]> optimalResult = findDuplicatesOptimal(testCase);
            System.out.println("  Optimal:     " + formatResult(optimalResult));

            List<int[]> groupingResult = findDuplicatesGrouping(testCase);
            System.out.println("  Grouping By: " + formatResult(groupingResult));

            List<int[]> bruteResult = findDuplicatesBruteForce(testCase);
            System.out.println("  Brute Force: " + formatResult(bruteResult));
            System.out.println("-".repeat(40));
        }
    }

    // Helper method to nicely format the List<int[]> output for console
    private static String formatResult(List<int[]> result) {
        return result.stream()
                .map(Arrays::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}