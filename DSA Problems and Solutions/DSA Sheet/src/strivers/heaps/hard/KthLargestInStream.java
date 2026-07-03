package strivers.heaps.hard;

/**
 * ==================================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ==================================================================================================
 * PROBLEM STATEMENT: 703. Kth Largest Element in a Stream
 * --------------------------------------------------------------------------------------------------
 * You are part of a university admissions office and need to keep track of the kth highest test
 * score from applicants in real-time. This helps to determine cut-off marks for interviews and
 * admissions dynamically as new applicants submit their scores.
 * * You are tasked to implement a class which, for a given integer k, maintains a stream of test
 * scores and continuously returns the kth highest test score after a new score has been submitted.
 * More specifically, we are looking for the kth highest score in the sorted list of all scores.
 * * Implement the KthLargest class:
 * - KthLargest(int k, int[] nums) Initializes the object with the integer k and the stream of test scores nums.
 * - int add(int val) Adds a new test score val to the stream and returns the element representing the kth largest element in the pool of test scores so far.
 * * EXAMPLES:
 * Example 1:
 * Input:
 * ["KthLargest", "add", "add", "add", "add", "add"]
 * [[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
 * Output: [null, 4, 5, 5, 8, 8]
 * * Example 2:
 * Input:
 * ["KthLargest", "add", "add", "add", "add"]
 * [[4, [7, 7, 7, 7, 8, 3]], [2], [10], [9], [9]]
 * Output: [null, 7, 7, 7, 8]
 * * CONSTRAINTS:
 * - 0 <= nums.length <= 10^4
 * - 1 <= k <= nums.length + 1
 * - -10^4 <= nums[i] <= 10^4
 * - -10^4 <= val <= 10^4
 * - At most 10^4 calls will be made to add.
 * * NOTE: As this is an Object-Oriented Design and Heap problem, DP concepts like Recursion Trees
 * and Tabulation states are omitted. The progressive phases are implemented as static inner classes
 * to keep everything contained within a single executable file.
 * ==================================================================================================
 */

import java.util.*;
import java.util.stream.Collectors;

public class KthLargestInStream {

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Min-Heap (PriorityQueue)
     * ==============================================================================================
     * Detailed Intuition:
     * To find the Kth largest element continuously, we don't need to keep all elements sorted.
     * We only care about the top K elements. A Min-Heap (PriorityQueue in Java) is the perfect
     * data structure for this. We maintain a heap of maximum size K. The smallest element in this
     * heap (at the root) will intrinsically be the Kth largest element of the entire stream.
     * Whenever a new element comes in, we add it to the heap. If the heap size exceeds K, we
     * simply poll the root (the smallest of the K+1 elements), ensuring we only keep the top K.
     * * Complexity Analysis:
     * - Time Complexity:
     * - Initialization: O(N * log K), where N is the length of nums. We insert N elements, and
     * each insertion takes O(log K) time since the heap size is capped at K.
     * - add(): O(log K) to insert the new element and potentially remove the smallest.
     * - Space Complexity: O(K) heap space to store the top K elements in the PriorityQueue.
     * Auxiliary stack space is O(1).
     */
    public static class OptimalApproach {
        private final PriorityQueue<Integer> minHeap;
        private final int k;

        public OptimalApproach(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>(k);
            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            minHeap.offer(val);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove the smallest element to maintain size K
            }
            // Defensive Check: Ensure we have at least k elements before peeking
            return minHeap.size() < k ? -1 : minHeap.peek();
        }
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - ArrayList & Sorting (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * The most straightforward way to find the Kth largest element is to keep all the elements
     * in a list, append the new element, sort the list in descending order, and return the
     * element at index K-1. While easy to conceptualize, sorting the entire list on every single
     * insertion is highly inefficient for a continuous stream of data.
     * * Complexity Analysis:
     * - Time Complexity:
     * - Initialization: O(N) to add all elements to the list.
     * - add(): O(M * log M) where M is the current number of elements in the stream. Sorting on
     * every call makes this approach extremely slow for large streams.
     * - Space Complexity: O(M) heap space to store all elements seen so far.
     */
    public static class BruteForceApproach {
        private final List<Integer> stream;
        private final int k;

        public BruteForceApproach(int k, int[] nums) {
            this.k = k;
            this.stream = new ArrayList<>();
            for (int num : nums) {
                stream.add(num);
            }
        }

        public int add(int val) {
            stream.add(val);
            // Sort in descending order
            stream.sort((a, b) -> Integer.compare(b, a));

            // Defensive Check: Prevent IndexOutOfBounds if stream has fewer than k elements
            if (stream.size() < k) return -1;

            return stream.get(k - 1);
        }
    }

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Stream API Initialization + SortedList Insertion
     * ==============================================================================================
     * Detailed Intuition:
     * We can leverage Java 8 Streams for elegant initialization to grab the top K elements right
     * off the bat. For ongoing insertions, instead of sorting the whole list (like Brute Force)
     * or using a Heap, we maintain a strictly sorted list of size K. When a new element arrives,
     * we find its insertion point using Binary Search (Collections.binarySearch). If it belongs
     * in the top K, we insert it and remove the smallest element.
     * * Complexity Analysis:
     * - Time Complexity:
     * - Initialization: O(N * log N) due to stream sorting.
     * - add(): O(K). Binary search takes O(log K), but inserting into an ArrayList takes O(K)
     * to shift elements. Faster than brute force, but slower than Min-Heap.
     * - Space Complexity: O(K) heap space to hold exactly K elements.
     */
    public static class StreamAPIApproach {
        private final List<Integer> topKList;
        private final int k;

        public StreamAPIApproach(int k, int[] nums) {
            this.k = k;
            // Use Stream API to sort, limit to top K, and collect into an ArrayList
            this.topKList = Arrays.stream(nums)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .limit(k)
                    .collect(Collectors.toCollection(ArrayList::new));

            // Note: Elements are stored in DESCENDING order for easy binary search/shifting.
            // Index 0 is 1st largest, Index k-1 is Kth largest.
        }

        public int add(int val) {
            // Find position using binary search (custom comparator for descending order)
            int pos = Collections.binarySearch(topKList, val, Collections.reverseOrder());
            if (pos < 0) {
                pos = -(pos + 1); // Get insertion point
            }

            // Insert if it belongs in the top K
            if (pos < k) {
                topKList.add(pos, val);
                if (topKList.size() > k) {
                    topKList.remove(topKList.size() - 1); // Remove the smallest
                }
            } else if (topKList.size() < k) {
                // Edge case where initial stream was smaller than K
                topKList.add(val);
            }

            // Defensive Check
            return topKList.size() < k ? -1 : topKList.get(k - 1);
        }
    }

    /**
     * ==============================================================================================
     * 4. TESTING SUITE
     * ==============================================================================================
     * Main method to thoroughly test all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("Running Testing Suite: 703. Kth Largest Element in a Stream");
        System.out.println("==========================================================\n");

        // Standard Case 1
        runTestCase(1, 3, new int[]{4, 5, 8, 2}, new int[]{3, 5, 10, 9, 4}, new int[]{4, 5, 5, 8, 8});

        // Standard Case 2 (Duplicates)
        runTestCase(2, 4, new int[]{7, 7, 7, 7, 8, 3}, new int[]{2, 10, 9, 9}, new int[]{7, 7, 7, 8});

        // Edge Case: Initial array has fewer than K elements (respecting 1 <= k <= nums.length + 1)
        runTestCase(3, 2, new int[]{5}, new int[]{1, 10, 3}, new int[]{1, 5, 5});

        // Edge Case: Negative values
        runTestCase(4, 2, new int[]{-10, -5, 0}, new int[]{-3, 5, -2}, new int[]{-3, 0, 0});

        // Edge Case: Empty initial array
        runTestCase(5, 1, new int[]{}, new int[]{3, 5, 10, 9, 4}, new int[]{3, 5, 10, 10, 10});
    }

    private static void runTestCase(int testNum, int k, int[] initialNums, int[] streamAdds, int[] expectedOutputs) {
        System.out.println("Test Case " + testNum + ": k = " + k + ", initial = " + Arrays.toString(initialNums));

        OptimalApproach optimal = new OptimalApproach(k, initialNums);
        BruteForceApproach brute = new BruteForceApproach(k, initialNums);
        StreamAPIApproach stream = new StreamAPIApproach(k, initialNums);

        boolean allPassed = true;

        for (int i = 0; i < streamAdds.length; i++) {
            int val = streamAdds[i];
            int exp = expectedOutputs[i];

            int optRes = optimal.add(val);
            int bruteRes = brute.add(val);
            int streamRes = stream.add(val);

            if (optRes != exp || bruteRes != exp || streamRes != exp) {
                allPassed = false;
                System.out.printf("  [FAIL] Add %d -> Expected: %d | Opt: %d | Brute: %d | Stream: %d\n",
                        val, exp, optRes, bruteRes, streamRes);
            }
        }

        if (allPassed) {
            System.out.println("  -> All stream operations passed! ✅\n");
        }
    }
}