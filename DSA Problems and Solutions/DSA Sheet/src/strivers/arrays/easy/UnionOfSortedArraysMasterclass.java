package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: Union of Two Sorted Arrays
 * Difficulty: Easy/Medium
 *
 * Formal Problem Statement:
 * Given two sorted arrays, arr1, and arr2 of size n and m. Find the union of
 * two sorted arrays. The union of two arrays can be defined as the common and
 * distinct elements in the two arrays.
 *
 * NOTE: Elements in the union should be in ascending order.
 *
 * Constraints (Implicit from standard platforms like GFG):
 * - 1 <= n, m <= 10^5
 * - 1 <= arr1[i], arr2[i] <= 10^9
 *
 * Example 1:
 * Input: n = 5, m = 5, arr1[] = {1,2,3,4,5}, arr2[] = {2,3,4,4,5}
 * Output: {1,2,3,4,5}
 * Explanation:
 * Common Elements in arr1 and arr2 are: 2,3,4,5
 * Distinct Elements in arr1 are: 1
 * Distinct Elements in arr2 are: No distinct elements.
 *
 * Example 2:
 * Input: n = 10, m = 7, arr1[] = {1,2,3,4,5,6,7,8,9,10}, arr2[] = {2,3,4,4,5,11,12}
 * Output: {1,2,3,4,5,6,7,8,9,10,11,12}
 * Explanation:
 * Common Elements: 2,3,4,5
 * Distinct in arr1: 1,6,7,8,9,10
 * Distinct in arr2: 11,12
 * ============================================================================
 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UnionOfSortedArraysMasterclass {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Two Pointers Method)
     * ============================================================================
     * Detailed Intuition:
     * Since both arrays are already sorted in ascending order, we can leverage
     * the "Merge" step of the Merge Sort algorithm using two pointers (i and j).
     *
     * 1. Place pointer 'i' at the start of arr1 and 'j' at the start of arr2.
     * 2. Compare arr1[i] and arr2[j].
     * 3. Pick the smaller element to maintain the sorted order.
     * 4. CRITICAL: Before adding the picked element to our result list, check if
     *    it matches the last element added to the list. If it does, skip it to
     *    prevent duplicates in our union.
     * 5. If elements are equal, pick either (and increment both pointers, or
     *    just one, while relying on the duplicate check).
     * 6. Once one array is exhausted, process the remaining elements of the
     *    other array, still checking for duplicates.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N + M) where N and M are the sizes of arr1 and arr2.
     *   We traverse both arrays exactly once.
     * - Space Complexity: O(1) auxiliary space (ignoring the space required
     *   for the output list, which takes O(N + M) heap space to store the answer).
     */
    public List<Integer> findUnionOptimal(int[] arr1, int[] arr2) {
        List<Integer> union = new ArrayList<>();
        int i = 0;
        int j = 0;
        int n = arr1.length;
        int m = arr2.length;

        while (i < n && j < m) {
            // Case 1: arr1's element is smaller or equal
            if (arr1[i] <= arr2[j]) {
                // Add only if the union list is empty or the last element is different
                if (union.isEmpty() || !union.get(union.size() - 1).equals(arr1[i])) {
                    union.add(arr1[i]);
                }
                i++;
            }
            // Case 2: arr2's element is smaller
            else {
                if (union.isEmpty() || !union.get(union.size() - 1).equals(arr2[j])) {
                    union.add(arr2[j]);
                }
                j++;
            }
        }

        // Add remaining elements of arr1 (if any)
        while (i < n) {
            if (union.isEmpty() || !union.get(union.size() - 1).equals(arr1[i])) {
                union.add(arr1[i]);
            }
            i++;
        }

        // Add remaining elements of arr2 (if any)
        while (j < m) {
            if (union.isEmpty() || !union.get(union.size() - 1).equals(arr2[j])) {
                union.add(arr2[j]);
            }
            j++;
        }

        return union;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (Using TreeSet)
     * ============================================================================
     * Detailed Intuition:
     * The definition of a Union requires uniqueness and ascending order.
     * In Java, a TreeSet perfectly fits this criteria: it inherently rejects
     * duplicate values and maintains its elements in a sorted tree structure
     * (usually a Red-Black Tree). We can simply iterate through both arrays and
     * dump every element into a TreeSet.
     *
     * Why is this brute force? Because it completely ignores the fact that the
     * input arrays are ALREADY sorted, forcing unnecessary logarithmic time
     * insertions for every single element.
     *
     * Complexity Analysis:
     * - Time Complexity: O((N + M) log(N + M)). Inserting an element into a
     *   TreeSet takes logarithmic time.
     * - Space Complexity: O(N + M) heap space to store elements in the TreeSet.
     */
    public List<Integer> findUnionBruteForce(int[] arr1, int[] arr2) {
        Set<Integer> set = new TreeSet<>();

        for (int num : arr1) {
            set.add(num);
        }

        for (int num : arr2) {
            set.add(num);
        }

        return new ArrayList<>(set);
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH (Java 8 Streams API)
     * ============================================================================
     * Detailed Intuition:
     * We can achieve the Union operation in a highly declarative manner using
     * Java 8 Streams. We concatenate streams of both arrays, use `.distinct()`
     * to remove duplicates, and use `.sorted()` to enforce ascending order,
     * finally collecting the result into a List.
     *
     * This is excellent for readability and modern Java codebases where
     * performance bottlenecks are not strict, though under the hood it suffers
     * from similar unoptimized time complexities as the Brute Force approach
     * because it ignores the initial sorted state.
     *
     * Complexity Analysis:
     * - Time Complexity: O((N + M) log(N + M)) due to the sorting operation.
     * - Space Complexity: O(N + M) auxiliary heap space for Stream evaluations.
     */
    public List<Integer> findUnionStreams(int[] arr1, int[] arr2) {
        return IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .distinct()
                .sorted()
                .boxed() // Converts IntStream to Stream<Integer>
                .collect(Collectors.toList());
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        UnionOfSortedArraysMasterclass solution = new UnionOfSortedArraysMasterclass();

        System.out.println("--- Testing Union of Two Sorted Arrays ---");

        // Test Case 1: Standard Example 1
        int[] arr1_1 = {1, 2, 3, 4, 5};
        int[] arr2_1 = {2, 3, 4, 4, 5};
        System.out.println("\nTest 1 (Optimal):");
        System.out.println("arr1: " + Arrays.toString(arr1_1) + " | arr2: " + Arrays.toString(arr2_1));
        System.out.println("Output: " + solution.findUnionOptimal(arr1_1, arr2_1));
        // Expected: [1, 2, 3, 4, 5]

        // Test Case 2: Standard Example 2
        int[] arr1_2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr2_2 = {2, 3, 4, 4, 5, 11, 12};
        System.out.println("\nTest 2 (Brute Force TreeSet):");
        System.out.println("arr1: " + Arrays.toString(arr1_2) + " | arr2: " + Arrays.toString(arr2_2));
        System.out.println("Output: " + solution.findUnionBruteForce(arr1_2, arr2_2));
        // Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]

        // Test Case 3: Completely disjoint arrays
        int[] arr1_3 = {1, 3, 5};
        int[] arr2_3 = {2, 4, 6};
        System.out.println("\nTest 3 (Disjoint Arrays - Streams):");
        System.out.println("arr1: " + Arrays.toString(arr1_3) + " | arr2: " + Arrays.toString(arr2_3));
        System.out.println("Output: " + solution.findUnionStreams(arr1_3, arr2_3));
        // Expected: [1, 2, 3, 4, 5, 6]

        // Test Case 4: Heavy duplicates in both arrays
        int[] arr1_4 = {1, 1, 1, 2, 2, 3};
        int[] arr2_4 = {1, 2, 3, 3, 3, 4};
        System.out.println("\nTest 4 (Heavy Duplicates - Optimal):");
        System.out.println("arr1: " + Arrays.toString(arr1_4) + " | arr2: " + Arrays.toString(arr2_4));
        System.out.println("Output: " + solution.findUnionOptimal(arr1_4, arr2_4));
        // Expected: [1, 2, 3, 4]

        // Test Case 5: One array is empty
        int[] arr1_5 = {};
        int[] arr2_5 = {1, 2, 3};
        System.out.println("\nTest 5 (Empty Array - Optimal):");
        System.out.println("arr1: " + Arrays.toString(arr1_5) + " | arr2: " + Arrays.toString(arr2_5));
        System.out.println("Output: " + solution.findUnionOptimal(arr1_5, arr2_5));
        // Expected: [1, 2, 3]
    }
}