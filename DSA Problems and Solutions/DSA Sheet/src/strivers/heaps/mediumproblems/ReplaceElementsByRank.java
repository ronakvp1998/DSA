package strivers.heaps.mediumproblems;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Replace Elements by its Rank in the Array
 * * Given an array of N integers, replace each element with its rank.
 * The rank represents the position of an element when the array is sorted in ascending order.
 * If multiple elements have the same value, they share the same rank.
 * * CONSTRAINTS:
 * - 0 <= N <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * INPUT/OUTPUT FORMATS:
 * Input: An array of integers.
 * Output: An array of integers where each element is replaced by its rank.
 * * Example 1:
 * Input:  [20, 15, 26, 2, 98, 6]
 * Output: [4, 3, 5, 1, 6, 2]
 * Explanation: Sorted unique values: 2(1), 6(2), 15(3), 20(4), 26(5), 98(6).
 * * Example 2:
 * Input:  [1, 5, 8, 15, 8, 25, 9]
 * Output: [1, 2, 3, 5, 3, 6, 4]
 * Explanation: Sorted unique values: 1(1), 5(2), 8(3), 9(4), 15(5), 25(6).
 * * (Note: As this is not a Dynamic Programming problem, DP concepts like
 * Recursion Trees and Tabulation states are omitted in favor of the Non-DP roadmap).
 * ==================================================================================================
 */
public class ReplaceElementsByRank {

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Priority Queue (Min-Heap)
     * ==============================================================================================
     * Detailed Intuition:
     * To find the rank, we need to process the elements in ascending order. A Priority Queue
     * (Min-Heap) naturally serves this purpose. By storing the value alongside its original index
     * in the heap, we can continuously poll the smallest element. We maintain a 'current rank'
     * counter. If the polled element is greater than the previously polled element, it belongs
     * to the next rank tier, so we increment the counter. We then place this rank into the result
     * array at the element's original index. This avoids fully sorting a secondary array while
     * handling duplicates seamlessly.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N) - Inserting N elements into the Priority Queue takes O(N log N),
     * and polling N elements also takes O(N log N).
     * - Space Complexity: O(N) Total - Heap space strictly requires O(N) to store the N array
     * objects `int[]{value, index}` in the Priority Queue, plus O(N) for the result array.
     * Auxiliary stack space is O(1) as this is iteratively processed.
     * ==============================================================================================
     */
    public static int[] optimalApproachPQ(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];

        // Min-Heap sorting primarily by value.
        // Array structure inside PQ: [elementValue, originalIndex]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < arr.length; i++) {
            minHeap.offer(new int[]{arr[i], i});
        }

        int[] result = new int[arr.length];
        int currentRank = 0;
        Integer previousValue = null; // Using Integer object to safely handle Integer.MIN_VALUE in array

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int value = current[0];
            int originalIndex = current[1];

            // If it's the first element or strictly greater than the last processed element, bump rank
            if (previousValue == null || value > previousValue) {
                currentRank++;
            }

            result[originalIndex] = currentRank;
            previousValue = value;
        }

        return result;
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - The "Think it" stage
     * ==============================================================================================
     * Detailed Intuition:
     * The simplest way to define the "rank" of a number is to count exactly how many *unique* * numbers in the array are strictly smaller than it. If there are exactly 'k' unique numbers
     * smaller than X, then the rank of X is 'k + 1'. We can iterate through every element, and for
     * each element, iterate through the rest of the array, dropping strictly smaller numbers into a
     * HashSet. The size of the HashSet + 1 is the rank.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) - For each of the N elements, we scan the entire array of size N
     * and perform HashSet insertions (average O(1)).
     * - Space Complexity: O(N) Total - Heap space requires O(N) for the result array, and up to
     * O(N) for the HashSet in the worst case (all unique elements). Auxiliary stack is O(1).
     * ==============================================================================================
     */
    public static int[] bruteForceApproach(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];

        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            Set<Integer> smallerUniqueElements = new HashSet<>();
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    smallerUniqueElements.add(arr[j]);
                }
            }
            result[i] = smallerUniqueElements.size() + 1;
        }

        return result;
    }

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Hashing & Sorting (Java 8 Streams focus)
     * ==============================================================================================
     * Detailed Intuition:
     * We can leverage the Java 8 Stream API to write highly declarative code. The logic remains
     * similar to standard sorting: we extract all unique elements, sort them, and map them to their
     * respective ranks (1 to M) in a HashMap. Finally, we map the original array elements to their
     * ranks using this map. This is often the preferred approach in enterprise codebases due to
     * its readability and lack of boilerplate.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N) - Sorting the distinct elements takes O(M log M) where M <= N.
     * Mapping elements to the result array takes O(N).
     * - Space Complexity: O(N) Total - Heap space for the intermediate distinct sorted array, the
     * HashMap mapping values to ranks, and the final result array. Auxiliary stack is O(1).
     * ==============================================================================================
     */
    public static int[] alternativeApproachStreams(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];

        // 1. Get distinct sorted elements
        int[] sortedUnique = Arrays.stream(arr)
                .distinct()
                .sorted()
                .toArray();

        // 2. Create a Map of Value -> Rank using IntStream
        Map<Integer, Integer> rankMap = IntStream.range(0, sortedUnique.length)
                .boxed()
                .collect(Collectors.toMap(
                        i -> sortedUnique[i],
                        i -> i + 1
                ));

        // 3. Map original array to their ranks
        return Arrays.stream(arr)
                .map(rankMap::get)
                .toArray();
    }

    /**
     * ==============================================================================================
     * 4. Testing Suite
     * ==============================================================================================
     */
    public static void main(String[] args) {
        // Standard Example 1
        int[] test1 = {20, 15, 26, 2, 98, 6};
        System.out.println("Test 1 (Standard): " + Arrays.toString(test1));
        System.out.println("Optimal (PQ):   " + Arrays.toString(optimalApproachPQ(test1)));
        System.out.println("Brute Force:    " + Arrays.toString(bruteForceApproach(test1)));
        System.out.println("Stream API:     " + Arrays.toString(alternativeApproachStreams(test1)));
        System.out.println("Expected:       [4, 3, 5, 1, 6, 2]\n");

        // Standard Example 2 (With Duplicates)
        int[] test2 = {1, 5, 8, 15, 8, 25, 9};
        System.out.println("Test 2 (Duplicates): " + Arrays.toString(test2));
        System.out.println("Optimal (PQ):   " + Arrays.toString(optimalApproachPQ(test2)));
        System.out.println("Stream API:     " + Arrays.toString(alternativeApproachStreams(test2)));
        System.out.println("Expected:       [1, 2, 3, 5, 3, 6, 4]\n");

        // Edge Case: Negative values and zero
        int[] test3 = {0, -10, 0, -100, 50};
        System.out.println("Test 3 (Negatives/Zero): " + Arrays.toString(test3));
        System.out.println("Optimal (PQ):   " + Arrays.toString(optimalApproachPQ(test3)));
        System.out.println("Expected:       [3, 2, 3, 1, 4]\n");

        // Edge Case: Empty Array
        int[] test4 = {};
        System.out.println("Test 4 (Empty): " + Arrays.toString(test4));
        System.out.println("Optimal (PQ):   " + Arrays.toString(optimalApproachPQ(test4)));
        System.out.println("Expected:       []\n");

        // Edge Case: All identical elements
        int[] test5 = {7, 7, 7, 7};
        System.out.println("Test 5 (All Identical): " + Arrays.toString(test5));
        System.out.println("Optimal (PQ):   " + Arrays.toString(optimalApproachPQ(test5)));
        System.out.println("Expected:       [1, 1, 1, 1]\n");
    }
}