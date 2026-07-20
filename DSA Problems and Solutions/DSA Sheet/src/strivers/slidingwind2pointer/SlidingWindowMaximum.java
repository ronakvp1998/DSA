package strivers.slidingwind2pointer;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * You are given an array of integers `nums`, there is a sliding window of size `k`
 * which is moving from the very left of the array to the very right. You can
 * only see the `k` numbers in the window. Each time the sliding window moves
 * right by one position.
 *
 * Return the max sliding window.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - 1 <= k <= nums.length
 *
 * Input/Output Formats:
 * - Input: An integer array `nums` and an integer `k`.
 * - Output: An integer array representing the maximum value in each sliding window.
 *           Length of the output array will be (nums.length - k + 1).
 *
 * Examples:
 * Example 1:
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * - Phase 1: Optimal Approach - Monotonic Deque O(N)
 * - Phase 2: Brute Force Approach - Nested loops checking each window O(N*K)
 * - Phase 3: Alternative Approach - Max-Heap / Priority Queue O(N log N)
 * ============================================================================
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

public class SlidingWindowMaximum {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Deque)
     * ========================================================================
     * Detailed Intuition:
     * We need to find the maximum in a sliding window in O(1) amortized time per
     * window shift. We can achieve this using a double-ended queue (Deque) to
     * store the *indices* of array elements.
     *
     * We maintain a strictly monotonically decreasing deque (based on the values
     * the indices point to).
     * 1. Remove elements from the front of the deque if they fall outside the
     *    current window bounds (index <= i - k).
     * 2. Remove elements from the back of the deque if they are smaller than or
     *    equal to the current element nums[i]. Why? Because a smaller element
     *    that appeared *before* the current element can NEVER be the maximum
     *    for any future windows as long as the current (larger and newer)
     *    element exists.
     * 3. Add the current element's index to the back.
     * 4. The maximum of the current window is always at the front of the deque.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Each element is pushed into the deque exactly once
     *   and popped at most once. Operations are amortized O(1).
     * - Space Complexity: O(K) auxiliary heap space. The deque will store at most
     *   'k' indices at any given time. Output array takes O(N - K + 1) space.
     */
    public static int[] optimalMonotonicDeque(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        int resultIndex = 0;

        // Deque stores INDICES of the array elements, not the values themselves
        Deque<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // 1. Remove elements from the front that are out of the current window
            if (!q.isEmpty() && q.peekFirst() == i - k) {
                q.pollFirst();
            }

            // 2. Remove elements from the back that are smaller than the current element
            // They are useless because the current element is larger and arrives later
            while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) {
                q.pollLast();
            }

            // 3. Add current element's index to the back
            q.offerLast(i);

            // 4. Once we have processed at least 'k' elements, record the maximum
            if (i >= k - 1) {
                result[resultIndex++] = nums[q.peekFirst()];
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * Detailed Intuition:
     * The most straightforward way is to simulate the sliding window. We iterate
     * through every possible starting index of the window (from 0 to n-k). For
     * each starting index, we run an inner loop over the next 'k' elements to
     * find the maximum value.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * K). For each of the (N - K + 1) windows, we do
     *   K comparisons. If K is large (e.g., K = N/2), this devolves to O(N^2)
     *   which will result in a Time Limit Exceeded (TLE) on LeetCode.
     * - Space Complexity: O(1) auxiliary space (excluding the result array).
     */
    public static int[] bruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result[i] = max;
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Max-Heap / Priority Queue)
     * ========================================================================
     * Detailed Intuition:
     * Instead of a Deque, we can maintain a Max-Heap to quickly retrieve the
     * maximum element. We store pairs of `[value, index]`.
     * As the window slides, the top of the heap might contain the maximum
     * value of a *previous* window. We lazily delete it: we only pop from the
     * top of the heap if the index of the top element is strictly less than or
     * equal to `i - k` (meaning it fell out of the window).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N) worst case. Elements are continually added
     *   to the Priority Queue. In the worst case (strictly increasing array),
     *   the PQ grows to size N, making insertion and deletion O(log N).
     * - Space Complexity: O(N) auxiliary heap space in the worst case to store
     *   elements in the Priority Queue.
     */
    public static int[] priorityQueueApproach(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];

        // Max Heap comparing by value descending. Stores int[]{value, index}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{nums[i], i});

            if (i >= k - 1) {
                // Lazily remove elements from the top of the max-heap if they
                // are outside the bounds of the current window
                while (pq.peek()[1] <= i - k) {
                    pq.poll();
                }
                result[i - k + 1] = pq.peek()[0];
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        int[][] testArrays = {
                {1, 3, -1, -3, 5, 3, 6, 7}, // Standard mixed array
                {1},                        // Single element
                {7, 6, 5, 4, 3, 2, 1},      // Strictly decreasing
                {1, 2, 3, 4, 5, 6, 7},      // Strictly increasing
                {1, 3, 1, 2, 0, 5}          // Random variations
        };

        int[] kValues = {3, 1, 3, 3, 3};

        System.out.println("Running Sliding Window Maximum Test Suite...\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] currentArr = testArrays[i];
            int currentK = kValues[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("Array: ");
            Arrays.stream(currentArr).forEach(num -> System.out.print(num + " "));
            System.out.println("\nk = " + currentK);

            int[] bruteResult = bruteForce(currentArr, currentK);
            int[] pqResult = priorityQueueApproach(currentArr, currentK);
            int[] optimalResult = optimalMonotonicDeque(currentArr, currentK);

            // Using Streams to nicely format array output
            System.out.print("Phase 1 (Optimal Deque) : ");
            Arrays.stream(optimalResult).forEach(num -> System.out.print(num + " "));

            System.out.print("\nPhase 2 (Brute Force)   : ");
            Arrays.stream(bruteResult).forEach(num -> System.out.print(num + " "));

            System.out.print("\nPhase 3 (Priority Q)    : ");
            Arrays.stream(pqResult).forEach(num -> System.out.print(num + " "));
            System.out.println();

            if (Arrays.equals(optimalResult, bruteResult) && Arrays.equals(optimalResult, pqResult)) {
                System.out.println("Status: PASS\n");
            } else {
                System.out.println("Status: FAIL - Mismatch detected\n");
            }
        }
    }
}