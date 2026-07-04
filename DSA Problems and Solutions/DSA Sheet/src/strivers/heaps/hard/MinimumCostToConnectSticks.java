package strivers.heaps.hard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ==============================================================================================
 * 1167. Minimum Cost to Connect Sticks
 * ==============================================================================================
 * Formal Problem Statement:
 * You have some number of sticks with positive integer lengths. These lengths are given as an
 * array sticks, where sticks[i] is the length of the ith stick.
 * * You can connect any two sticks of lengths x and y into one stick by paying a cost of x + y.
 * You must connect all the sticks until there is only one stick remaining.
 * * Return the minimum cost of connecting all the given sticks into one stick in this way.
 * * Example 1:
 * Input: sticks = [2,4,3]
 * Output: 14
 * Explanation: You start with sticks = [2,4,3].
 * 1. Combine sticks 2 and 3 for a cost of 2 + 3 = 5. Now you have sticks = [5,4].
 * 2. Combine sticks 5 and 4 for a cost of 5 + 4 = 9. Now you have sticks = [9].
 * There is only one stick left, so you are done. The total cost is 5 + 9 = 14.
 * * Example 2:
 * Input: sticks = [1,8,3,5]
 * Output: 30
 * Explanation: You start with sticks = [1,8,3,5].
 * 1. Combine sticks 1 and 3 for a cost of 1 + 3 = 4. Now you have sticks = [4,8,5].
 * 2. Combine sticks 4 and 5 for a cost of 4 + 5 = 9. Now you have sticks = [9,8].
 * 3. Combine sticks 9 and 8 for a cost of 9 + 8 = 17. Now you have sticks = [17].
 * There is only one stick left, so you are done. The total cost is 4 + 9 + 17 = 30.
 * * Constraints:
 * - 1 <= sticks.length <= 10^4
 * - 1 <= sticks[i] <= 10^4
 * ==============================================================================================
 */
public class MinimumCostToConnectSticks {

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Priority Queue / Min-Heap (The "Master it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * This is a classic Greedy problem (Huffman Coding logic). When we merge two sticks, their combined
     * length becomes a single stick. This combined length will be added to the total cost *again* every
     * time it is merged with another stick in the future.
     * To minimize the total cost, we must ensure that the longest sticks are merged as few times as
     * possible, and the shortest sticks are merged first. A Min-Heap (Priority Queue) allows us to
     * continually extract the two smallest current sticks, merge them, and insert the new stick back
     * into the pool in logarithmic time.
     * * Complexity Analysis:
     * - Time O(N log N): Building the heap takes O(N) or O(N log N) depending on insertion. We perform
     * N-1 merges. Each merge requires two poll() operations and one offer() operation, each taking
     * O(log N). Thus, total time is O(N log N).
     * - Space O(N): The Priority Queue requires auxiliary heap space proportional to the number of sticks.
     */
    public static int connectSticksOptimal(int[] sticks) {
        if (sticks == null || sticks.length <= 1) return 0;

        // Initialize Min-Heap using Java 8 Streams for clean mapping
        PriorityQueue<Integer> minHeap = Arrays.stream(sticks)
                .boxed()
                .collect(Collectors.toCollection(PriorityQueue::new));

        int totalCost = 0;

        // While there is more than 1 stick left to connect
        while (minHeap.size() > 1) {
            // Greedily pick the two smallest sticks
            int stick1 = minHeap.poll();
            int stick2 = minHeap.poll();

            int currentCost = stick1 + stick2;
            totalCost += currentCost;

            // Push the newly formed stick back into the heap
            minHeap.offer(currentCost);
        }

        return totalCost;
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Repeated Sorting (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * If we don't know about Priority Queues, the most intuitive way to find the two smallest elements
     * is to sort the array, take the first two, add them, put the sum back into the array, and sort
     * the array *again*. We repeat this process until only one element is left.
     * While logically sound, sorting the entire list at every step is highly inefficient.
     * * Complexity Analysis:
     * - Time O(N^2 log N): We perform N-1 merges. In each iteration, we sort a list of up to size N,
     * which takes O(N log N). Multiplying these gives O(N^2 log N).
     * - Space O(N): We use an ArrayList (auxiliary space) to easily remove and add elements.
     */
    public static int connectSticksBruteForce(int[] sticks) {
        if (sticks == null || sticks.length <= 1) return 0;

        List<Integer> list = Arrays.stream(sticks)
                .boxed()
                .collect(Collectors.toList());

        int totalCost = 0;

        while (list.size() > 1) {
            // Sort ascending every single iteration to find the two smallest
            Collections.sort(list);

            int stick1 = list.remove(0);
            int stick2 = list.remove(0);

            int currentCost = stick1 + stick2;
            totalCost += currentCost;

            list.add(currentCost);
        }

        return totalCost;
    }

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Two Queues (Sorting + Greedy)
     * ==============================================================================================
     * Detailed Intuition:
     * If we want to avoid the O(log N) overhead of heap operations during the merge phase, we can use
     * a Two-Queue approach.
     * 1. Sort the initial sticks and put them into Queue 1.
     * 2. Use Queue 2 to store the newly created sticks (the sums).
     * 3. Because we always merge the smallest available sticks, the sums added to Queue 2 will
     * naturally be in strictly increasing order.
     * 4. In each step, the two smallest sticks will always be at the front of either Queue 1 or Queue 2.
     * * Complexity Analysis:
     * - Time O(N log N): O(N log N) for the initial sort. After that, we do N-1 merges taking O(1)
     * time each. So the bottleneck is just the initial sort. The constant factors are much better
     * here than the Priority Queue.
     * - Space O(N): For the two Queues.
     */
    public static int connectSticksTwoQueues(int[] sticks) {
        if (sticks == null || sticks.length <= 1) return 0;

        // Step 1: Sort and populate Queue 1
        Arrays.sort(sticks);
        Queue<Integer> q1 = new LinkedList<>();
        for (int stick : sticks) {
            q1.offer(stick);
        }

        // Step 2: Queue 2 holds the merged sums
        Queue<Integer> q2 = new LinkedList<>();
        int totalCost = 0;

        while (q1.size() + q2.size() > 1) {
            // Extract the two absolute minimums across both queues
            int stick1 = getMinFromTwoQueues(q1, q2);
            int stick2 = getMinFromTwoQueues(q1, q2);

            int currentCost = stick1 + stick2;
            totalCost += currentCost;

            // The sums naturally remain sorted in Queue 2
            q2.offer(currentCost);
        }

        return totalCost;
    }

    /**
     * Helper method for Phase 3: Returns and removes the minimum element
     * from the front of the two queues.
     */
    private static int getMinFromTwoQueues(Queue<Integer> q1, Queue<Integer> q2) {
        if (q1.isEmpty()) return q2.poll();
        if (q2.isEmpty()) return q1.poll();

        // Peek at both fronts and extract the smaller one
        if (q1.peek() <= q2.peek()) {
            return q1.poll();
        } else {
            return q2.poll();
        }
    }

    /**
     * ==============================================================================================
     * 4. Testing Suite
     * ==============================================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- Minimum Cost to Connect Sticks ---");

        int[][] testCases = {
                {2, 4, 3},               // Standard Case 1
                {1, 8, 3, 5},            // Standard Case 2
                {5},                     // Edge Case: 1 stick (Cost 0)
                {},                      // Edge Case: 0 sticks (Cost 0)
                {3354, 4316, 3259, 4904} // Case with larger arbitrary numbers
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i];
            System.out.println("\nTest Case " + (i + 1) + ": " + Arrays.toString(original));

            // Deep copy to ensure fair benchmarking and avoid array mutations
            int[] forOptimal = Arrays.copyOf(original, original.length);
            int[] forBrute = Arrays.copyOf(original, original.length);
            int[] forTwoQueues = Arrays.copyOf(original, original.length);

            long startTime, endTime;

            // 1. Optimal
            startTime = System.nanoTime();
            int resOptimal = connectSticksOptimal(forOptimal);
            endTime = System.nanoTime();
            System.out.println("Optimal (Min-Heap)    : " + resOptimal + " [Time: " + (endTime - startTime) / 1000 + " µs]");

            // 2. Alternative (Two Queues)
            startTime = System.nanoTime();
            int resTwoQueues = connectSticksTwoQueues(forTwoQueues);
            endTime = System.nanoTime();
            System.out.println("Alternative (2 Queues): " + resTwoQueues + " [Time: " + (endTime - startTime) / 1000 + " µs]");

            // 3. Brute Force
            startTime = System.nanoTime();
            int resBrute = connectSticksBruteForce(forBrute);
            endTime = System.nanoTime();
            System.out.println("Brute Force (Sorting) : " + resBrute + " [Time: " + (endTime - startTime) / 1000 + " µs]");
        }
    }
}