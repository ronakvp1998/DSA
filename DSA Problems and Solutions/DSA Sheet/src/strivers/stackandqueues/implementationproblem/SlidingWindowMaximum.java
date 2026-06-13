package strivers.stackandqueues.implementationproblem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 239. Sliding Window Maximum
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * You are given an array of integers nums, there is a sliding window of size k
 * which is moving from the very left of the array to the very right. You can
 * only see the k numbers in the window. Each time the sliding window moves right
 * by one position.
 * Return the max sliding window.
 *
 * Example 1:
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * CONSTRAINTS:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Monotonic Decreasing Deque (O(N) Time)
 * Phase 2: Brute Force Approach - Nested Window Traversal (O(N*K) Time)
 * Phase 3: Alternative Approach - Max Heap / Priority Queue (O(N log N) Time)
 * ============================================================================
 */
public class SlidingWindowMaximum {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Decreasing Deque)
     * ============================================================================
     * Detailed Intuition:
     * To achieve an O(N) solution, we must avoid scanning the window repeatedly.
     * We use a Double-Ended Queue (Deque) to store the **indices** of array elements,
     * maintaining a strictly decreasing monotonic property based on their values.
     * * 1. As the window slides to index `i`, any elements currently in the Deque
     * that are smaller than `nums[i]` are completely useless. They are both
     * smaller AND older than `nums[i]`, meaning they can NEVER be the maximum
     * for any future window. We pop them from the back.
     * 2. We then push the current index `i` to the back of the Deque.
     * 3. Next, we check the front of the Deque. If the index at the front falls
     * outside our current sliding window boundary (i.e., index < i - k + 1),
     * we remove it from the front.
     * 4. Because we maintained a strictly decreasing order, the element at the
     * front of the Deque is guaranteed to be the maximum for the current window.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array once. Every index is
     * added to the Deque exactly once and removed at most once. Amortized O(1).
     * - Space Complexity: O(K) auxiliary heap space for the Deque, as it will
     * never store more than `k` elements at a given time.
     * ============================================================================
     */
    public int[] maxSlidingWindowOptimal(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        int resultIndex = 0;

        // Deque stores INDICES, not values, to easily check window boundaries
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // 1. Remove elements from the FRONT that are out of the current window
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // 2. Enforce decreasing monotonic property from the BACK
            // Remove indices of smaller elements as they are now useless
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            // 3. Add the current index to the back
            deque.offerLast(i);

            // 4. If our window has reached size 'k', record the maximum (at the front)
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Window Traversal) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most intuitive way to solve this is to simulate the sliding window
     * exactly as described. We iterate a starting pointer `i` from `0` to `n - k`.
     * For each position, we iterate a secondary pointer `j` through the next `k`
     * elements, finding the local maximum, and recording it.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * K). For each of the (N - K + 1) windows, we do K
     * comparisons. Will lead to Time Limit Exceeded (TLE) for constraints 10^5.
     * - Space Complexity: O(1) auxiliary space (excluding the output array).
     * ============================================================================
     */
    public int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int currentMax = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                currentMax = Math.max(currentMax, nums[j]);
            }
            result[i] = currentMax;
        }

        return result;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Max Heap / Priority Queue)
     * ============================================================================
     * Detailed Intuition:
     * If a candidate forgets the Deque approach, a Priority Queue (Max Heap) is
     * a highly viable backup strategy. We store an array of `[value, index]` in
     * the Max Heap.
     * As we slide the window, we add the new element to the heap. The absolute
     * maximum value will always float to the top (`peek()`). However, that
     * maximum value might belong to an index that has already slipped out of our
     * sliding window. Therefore, while the top of the heap holds an outdated index,
     * we simply `poll()` it out. Once the top element is valid, it's our window max.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N). In the worst case (a strictly increasing array),
     * elements are never discarded from the heap until the end, meaning we do N
     * insertions at O(log N) cost each.
     * - Space Complexity: O(N) auxiliary heap space, as the Priority Queue can
     * grow to size N in the worst-case increasing array scenario.
     * ============================================================================
     */
    public int[] maxSlidingWindowAlternative(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];

        // PriorityQueue storing int[] {value, index}, sorted descending by value
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

        // Initialize the heap with the first k elements
        for (int i = 0; i < k; i++) {
            maxHeap.offer(new int[]{nums[i], i});
        }

        result[0] = maxHeap.peek()[0];

        // Process the rest of the array
        for (int i = k; i < n; i++) {
            maxHeap.offer(new int[]{nums[i], i});

            // Purge outdated maximums that are no longer in the window
            while (maxHeap.peek()[1] <= i - k) {
                maxHeap.poll();
            }

            result[i - k + 1] = maxHeap.peek()[0];
        }

        return result;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        SlidingWindowMaximum solver = new SlidingWindowMaximum();

        // Utility formatter using Java 8 Streams
        java.util.function.Function<int[], String> format =
                arr -> Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Input (Example 1) ===");
        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        System.out.println("Input:       " + format.apply(nums1) + ", k = " + k1);
        System.out.println("Optimal:     " + format.apply(solver.maxSlidingWindowOptimal(nums1, k1)));
        System.out.println("Brute Force: " + format.apply(solver.maxSlidingWindowBruteForce(nums1, k1)));
        System.out.println("Alternative: " + format.apply(solver.maxSlidingWindowAlternative(nums1, k1)));

        System.out.println("\n=== Test Case 2: Window Size 1 (Example 2) ===");
        int[] nums2 = {1};
        int k2 = 1;
        System.out.println("Input:       " + format.apply(nums2) + ", k = " + k2);
        System.out.println("Optimal:     " + format.apply(solver.maxSlidingWindowOptimal(nums2, k2)));

        System.out.println("\n=== Test Case 3: Strictly Decreasing Array ===");
        int[] nums3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int k3 = 3;
        System.out.println("Input:       " + format.apply(nums3) + ", k = " + k3);
        System.out.println("Optimal:     " + format.apply(solver.maxSlidingWindowOptimal(nums3, k3)));

        System.out.println("\n=== Test Case 4: Strictly Increasing Array ===");
        int[] nums4 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int k4 = 3;
        System.out.println("Input:       " + format.apply(nums4) + ", k = " + k4);
        System.out.println("Optimal:     " + format.apply(solver.maxSlidingWindowOptimal(nums4, k4)));

        System.out.println("\n=== Test Case 5: Window Size Equals Array Size ===");
        int[] nums5 = {1, 3, 5, 7, 2};
        int k5 = 5;
        System.out.println("Input:       " + format.apply(nums5) + ", k = " + k5);
        System.out.println("Optimal:     " + format.apply(solver.maxSlidingWindowOptimal(nums5, k5)));
    }
}