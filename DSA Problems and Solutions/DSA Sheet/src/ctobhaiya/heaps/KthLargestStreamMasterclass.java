package ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: Kth LARGEST ELEMENT IN A STREAM (LeetCode #703)
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * You are part of a university admissions office and need to keep track of the
 * kth highest test score from applicants in real-time. This helps to determine
 * cut-off marks for interviews and admissions dynamically as new applicants
 * submit their scores.
 * * You are tasked to implement a class which, for a given integer k, maintains
 * a stream of test scores and continuously returns the kth highest test score
 * after a new score has been submitted. More specifically, we are looking for
 * the kth highest score in the sorted list of all scores.
 * * Implement the KthLargest class:
 * - KthLargest(int k, int[] nums) Initializes the object with the integer k
 * and the stream of test scores nums.
 * - int add(int val) Adds a new test score val to the stream and returns the
 * element representing the kth largest element in the pool of test scores so far.
 * * Constraints:
 * - 0 <= nums.length <= 10^4
 * - 1 <= k <= nums.length + 1
 * - -10^4 <= nums[i] <= 10^4
 * - -10^4 <= val <= 10^4
 * - At most 10^4 calls will be made to add.
 * * Examples:
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
 * * Conceptual Visualization (Min-Heap tracking top 3 elements):
 * KthLargest(3, [4, 5, 8, 2])
 * Init: Heap filters to top 3 -> [4, 5, 8]. Root is 4.
 * * add(3) -> Heap [4, 5, 8], 3 < 4, ignored. Return Root -> 4
 * add(5) -> Heap [5, 5, 8], 5 > 4, replace 4. Return Root -> 5
 * add(10)-> Heap [5, 8, 10], 10 > 5, replace 5. Return Root -> 5
 * add(9) -> Heap [8, 9, 10], 9 > 5, replace 5. Return Root -> 8
 * add(4) -> Heap [8, 9, 10], 4 < 8, ignored. Return Root -> 8
 * * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Design Problem)
 * ----------------------------------------------------------------------------
 * Note: Because this is an Object-Oriented Design problem rather than a single
 * algorithmic method, the phases are represented as static inner classes to
 * demonstrate the progression from Brute Force to Optimal state management.
 * * Phase 1: Brute Force Approach (Dynamic List + Sorting) - The "Think it" stage.
 * - Store all incoming elements in a dynamic list.
 * - Every time `add()` is called, append the new element, sort the entire list
 * in descending order, and return the element at index `k-1`.
 * * Phase 2: Optimal Approach (Min-Heap / PriorityQueue) - The "Perfect it" stage.
 * - Maintain a Min-Heap of strict size K.
 * - The root of this heap will always natively hold the Kth largest element.
 * - For every `add()`, if the new element is larger than the root, pop the root
 * and push the new element. Otherwise, ignore it. O(log K) insertion.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class KthLargestStreamMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (DYNAMIC LIST + RE-SORTING)
     * ========================================================================
     * Detailed Intuition:
     * The simplest way to know the Kth largest element at any given time is to
     * have a perfectly sorted list of all elements seen so far. We keep an
     * ArrayList. During initialization, we add all elements. Whenever `add()`
     * is called, we append the new value, trigger a full sort in descending
     * order, and simply fetch the element at `k - 1`.
     * * Complexity Analysis:
     * - Time Complexity:
     * - Constructor: O(N log N) where N is the length of `nums`.
     * - `add(val)`: O(M log M) where M is the total number of elements
     * currently in the stream. This degrades severely as the stream grows.
     * - Space Complexity: O(M) auxiliary heap space to store all elements
     * ever added to the stream. O(log M) auxiliary stack space during sorting.
     */
    public static class KthLargestBruteForce {
        private final int k;
        private final List<Integer> stream;

        public KthLargestBruteForce(int k, int[] nums) {
            this.k = k;
            this.stream = new ArrayList<>();
            for (int num : nums) {
                this.stream.add(num);
            }
            // Initial sort
            this.stream.sort(Collections.reverseOrder());
        }

        public int add(int val) {
            stream.add(val);
            // Re-sort the entire history of the stream
            stream.sort(Collections.reverseOrder());
            return stream.get(k - 1);
        }
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL APPROACH (MIN-HEAP BOUNDED BY K)
     * ========================================================================
     * Detailed Intuition:
     * We don't need to remember all the elements in the stream, nor do we need
     * to sort them all. We only care about the top K elements.
     * By using a Min-Heap of size K, the Kth largest element will sit perfectly
     * at the root (the minimum of the top K elements).
     * If a new element comes in and is smaller than the root, it can never be
     * in the top K, so we discard it. If it is larger, it kicks the root out
     * of the top K, and we add it to the heap.
     * * Complexity Analysis:
     * - Time Complexity:
     * - Constructor: O(N log K) where N is the length of `nums`. We only
     * push to a heap capped at size K.
     * - `add(val)`: O(log K). We only ever perform heap operations on K elements.
     * - Space Complexity: O(K) auxiliary heap space for the PriorityQueue.
     * O(1) auxiliary stack space. We strictly discard elements outside the Top K.
     */
    public static class KthLargestOptimal {
        private final int k;
        private final PriorityQueue<Integer> minHeap;

        public KthLargestOptimal(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>(k);

            // Populate the heap with initial numbers
            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            // If we haven't reached capacity K, simply add the element
            if (minHeap.size() < k) {
                minHeap.offer(val);
            }
            // If the heap is at capacity K, only add if the new value is
            // strictly greater than the current Kth largest (the root)
            else if (val > minHeap.peek()) {
                minHeap.poll();  // Evict the current Kth largest
                minHeap.offer(val); // Insert the new candidate
            }

            // The root of the Min-Heap is the Kth largest element overall
            return minHeap.peek();
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Executing Kth Largest in Stream Testing Suite...\n");

        // Test Case 1: Standard Example 1
        System.out.println("Test Case 1: Standard Stream (k=3, nums=[4, 5, 8, 2])");
        KthLargestOptimal opt1 = new KthLargestOptimal(3, new int[]{4, 5, 8, 2});
        System.out.println("add(3)  -> Expected: 4 | Actual: " + opt1.add(3));
        System.out.println("add(5)  -> Expected: 5 | Actual: " + opt1.add(5));
        System.out.println("add(10) -> Expected: 5 | Actual: " + opt1.add(10));
        System.out.println("add(9)  -> Expected: 8 | Actual: " + opt1.add(9));
        System.out.println("add(4)  -> Expected: 8 | Actual: " + opt1.add(4));
        System.out.println("--------------------------------------------------");

        // Test Case 2: Standard Example 2 (Duplicates)
        System.out.println("Test Case 2: Duplicates (k=4, nums=[7, 7, 7, 7, 8, 3])");
        KthLargestOptimal opt2 = new KthLargestOptimal(4, new int[]{7, 7, 7, 7, 8, 3});
        System.out.println("add(2)  -> Expected: 7 | Actual: " + opt2.add(2));
        System.out.println("add(10) -> Expected: 7 | Actual: " + opt2.add(10));
        System.out.println("add(9)  -> Expected: 7 | Actual: " + opt2.add(9));
        System.out.println("add(9)  -> Expected: 8 | Actual: " + opt2.add(9));
        System.out.println("--------------------------------------------------");

        // Test Case 3: Edge Case - Initialization with fewer than K elements
        System.out.println("Test Case 3: Empty Initialization (k=1, nums=[])");
        // Problem constraints state: 1 <= k <= nums.length + 1
        // If nums is empty, k must be 1.
        KthLargestOptimal opt3 = new KthLargestOptimal(1, new int[]{});
        System.out.println("add(-3) -> Expected: -3 | Actual: " + opt3.add(-3));
        System.out.println("add(-2) -> Expected: -2 | Actual: " + opt3.add(-2));
        System.out.println("add(-4) -> Expected: -2 | Actual: " + opt3.add(-4));
        System.out.println("add(0)  -> Expected:  0 | Actual: " + opt3.add(0));
        System.out.println("--------------------------------------------------");

        // Test Case 4: Brute Force Validation against Optimal
        System.out.println("Test Case 4: Brute vs Optimal Validation");
        KthLargestBruteForce brute = new KthLargestBruteForce(2, new int[]{0});
        KthLargestOptimal optimal = new KthLargestOptimal(2, new int[]{0});

        int[] streamAdds = {1, 1, -1, 3, 5, -2, 8};
        boolean allMatch = true;
        for (int val : streamAdds) {
            int bRes = brute.add(val);
            int oRes = optimal.add(val);
            if (bRes != oRes) {
                allMatch = false;
                System.out.println("Mismatch on add(" + val + "): Brute=" + bRes + " | Optimal=" + oRes);
            }
        }
        System.out.println("Consistency Check: " + (allMatch ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}