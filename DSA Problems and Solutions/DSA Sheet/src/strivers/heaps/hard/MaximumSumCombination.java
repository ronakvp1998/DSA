package strivers.heaps.hard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ==============================================================================================
 * Maximum Sum Combination
 * ==============================================================================================
 * Formal Problem Statement:
 * Given two integer arrays A and B of size N, find the top K maximum sum combinations
 * where a combination sum is defined as A[i] + B[j].
 * * You must return a list of the top K largest sums in descending order.
 * * Example 1:
 * Input: A = [3, 2], B = [1, 4], K = 2
 * Output: [7, 6]
 * Explanation:
 * All possible combinations are:
 * 3 + 1 = 4, 3 + 4 = 7, 2 + 1 = 3, 2 + 4 = 6.
 * The top 2 maximum sums are 7 and 6.
 * * Example 2:
 * Input: A = [1, 4, 2, 3], B = [2, 5, 1, 6], K = 4
 * Output: [10, 9, 9, 8]
 * Explanation:
 * 4 + 6 = 10
 * 4 + 5 = 9
 * 3 + 6 = 9
 * 2 + 6 = 8 (or 3 + 5 = 8)
 * * Constraints:
 * - 1 <= N <= 10^5
 * - 1 <= K <= N^2
 * - -10^4 <= A[i], B[i] <= 10^4
 * ==============================================================================================
 */
public class MaximumSumCombination {

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Priority Queue / Max Heap + HashSet (The "Master it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * Instead of generating all N^2 possible sums, we can use a greedy approach guided by a Max Heap.
     * 1. Sort both arrays in ascending order. The absolute maximum sum will naturally be the
     * sum of the last elements of both arrays: A[N-1] + B[N-1].
     * 2. If A[N-1] + B[N-1] is the largest, the next largest can only be either:
     * A[N-2] + B[N-1] OR A[N-1] + B[N-2].
     * 3. We use a Max Heap to keep track of the largest available sums. We store the sum along
     * with the indices (i, j) that produced it.
     * 4. To avoid processing the same pair of indices twice, we use a HashSet to track visited pairs.
     * 5. We extract the maximum element from the heap K times. Each time we extract (i, j), we
     * insert (i-1, j) and (i, j-1) into the heap if they haven't been visited yet.
     * * Complexity Analysis:
     * - Time O(N log N + K log K): O(N log N) to sort both arrays. In the worst case, we do K
     * extract-max operations and 2K insertions into the heap. Each heap operation takes O(log K),
     * yielding O(K log K) for the extraction phase.
     * - Space O(K): The Priority Queue and HashSet will store at most K elements (and their pairs)
     * at any given time.
     */
    public static List<Integer> solveOptimal(int[] A, int[] B, int K) {
        Arrays.sort(A);
        Arrays.sort(B);

        int n = A.length;
        List<Integer> result = new ArrayList<>();

        // Max Heap storing int[] {sum, index_A, index_B}
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

        // HashSet to track visited index pairs to prevent duplicate processing
        Set<String> visited = new HashSet<>();

        // Initialize with the maximum possible sum
        maxHeap.offer(new int[]{A[n - 1] + B[n - 1], n - 1, n - 1});
        visited.add((n - 1) + "," + (n - 1));

        for (int count = 0; count < K; count++) {
            if (maxHeap.isEmpty()) break;

            int[] current = maxHeap.poll();
            result.add(current[0]);

            int i = current[1];
            int j = current[2];

            // Push (i-1, j)
            if (i - 1 >= 0) {
                String leftPair = (i - 1) + "," + j;
                if (!visited.contains(leftPair)) {
                    maxHeap.offer(new int[]{A[i - 1] + B[j], i - 1, j});
                    visited.add(leftPair);
                }
            }

            // Push (i, j-1)
            if (j - 1 >= 0) {
                String rightPair = i + "," + (j - 1);
                if (!visited.contains(rightPair)) {
                    maxHeap.offer(new int[]{A[i] + B[j - 1], i, j - 1});
                    visited.add(rightPair);
                }
            }
        }

        return result;
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Generate All and Sort (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * The most intuitive fallback is to simply generate every single combination of sums between
     * array A and array B. Once we have all N^2 combinations, we sort them in descending order
     * and slice the top K elements.
     * We utilize Java 8 Streams here for a highly readable and concise functional implementation.
     * * Complexity Analysis:
     * - Time O(N^2 log(N^2)): Generating all sums takes O(N^2). Sorting an array of size N^2
     * takes O(N^2 log(N^2)).
     * - Space O(N^2): We must store all N^2 combinations in memory simultaneously before sorting.
     */
    public static List<Integer> solveBruteForce(int[] A, int[] B, int K) {
        return Arrays.stream(A)
                .boxed()
                .flatMap(a -> Arrays.stream(B).mapToObj(b -> a + b)) // Generate N^2 sums
                .sorted(Collections.reverseOrder())                  // Sort descending
                .limit(K)                                            // Take top K
                .collect(Collectors.toList());
    }

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Min Heap Filtering
     * ==============================================================================================
     * Detailed Intuition:
     * To improve upon the N^2 space complexity of the brute force approach, we can maintain a
     * Min Heap of size K. We iterate through all N^2 combinations. If a new sum is greater than
     * the smallest sum in our heap (the root), we remove the root and insert the new sum.
     * By the end of the iteration, the heap strictly contains the top K maximum sums.
     * * Complexity Analysis:
     * - Time O(N^2 log K): We iterate through N^2 elements. Heap operations take O(log K).
     * - Space O(K): The heap only stores K elements at any given time, greatly improving memory
     * efficiency over the brute-force approach.
     */
    public static List<Integer> solveAlternative(int[] A, int[] B, int K) {
        // Min Heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int a : A) {
            for (int b : B) {
                int sum = a + b;
                if (minHeap.size() < K) {
                    minHeap.offer(sum);
                } else if (sum > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.offer(sum);
                }
            }
        }

        // The heap contains the top K elements, but in ascending order (min heap).
        // We need to extract them, sort descending, and return.
        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        Collections.reverse(result);

        return result;
    }

    /**
     * ==============================================================================================
     * 4. Testing Suite
     * ==============================================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- Maximum Sum Combination ---");

        int[][][] arrayCases = {
                {{3, 2}, {1, 4}},                               // Standard Case 1
                {{1, 4, 2, 3}, {2, 5, 1, 6}},                   // Standard Case 2
                {{-1, -5, -3}, {-2, -4, -6}},                   // Edge Case: All Negative Numbers
                {{10, 10, 10}, {10, 10, 10}},                   // Edge Case: Duplicate elements
                {{0}, {0}}                                      // Edge Case: Zero values
        };
        int[] kValues = {2, 4, 3, 5, 1};

        for (int i = 0; i < arrayCases.length; i++) {
            int[] A = arrayCases[i][0];
            int[] B = arrayCases[i][1];
            int K = kValues[i];

            System.out.println("\nTest Case " + (i + 1) + ":");
            System.out.println("A: " + Arrays.toString(A) + " | B: " + Arrays.toString(B) + " | K: " + K);

            long startTime, endTime;

            // Test Phase 1: Optimal
            startTime = System.nanoTime();
            List<Integer> resOptimal = solveOptimal(A.clone(), B.clone(), K);
            endTime = System.nanoTime();
            System.out.println("Optimal (Max Heap)    : " + resOptimal +
                    " [Time: " + (endTime - startTime) / 1000 + " µs]");

            // Test Phase 2: Brute Force
            startTime = System.nanoTime();
            List<Integer> resBrute = solveBruteForce(A.clone(), B.clone(), K);
            endTime = System.nanoTime();
            System.out.println("Brute Force (N^2 Sort): " + resBrute +
                    " [Time: " + (endTime - startTime) / 1000 + " µs]");

            // Test Phase 3: Alternative
            startTime = System.nanoTime();
            List<Integer> resAlt = solveAlternative(A.clone(), B.clone(), K);
            endTime = System.nanoTime();
            System.out.println("Alternative (Min Heap): " + resAlt +
                    " [Time: " + (endTime - startTime) / 1000 + " µs]");
        }
    }
}