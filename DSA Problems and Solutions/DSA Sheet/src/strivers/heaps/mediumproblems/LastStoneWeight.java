package strivers.heaps.mediumproblems;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 1046. Last Stone Weight
 * * You are given an array of integers stones where stones[i] is the weight of the ith stone.
 * We are playing a game with the stones. On each turn, we choose the heaviest two stones and
 * smash them together. Suppose the heaviest two stones have weights x and y with x <= y.
 * The result of this smash is:
 * - If x == y, both stones are destroyed, and
 * - If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
 * * At the end of the game, there is at most one stone left.
 * Return the weight of the last remaining stone. If there are no stones left, return 0.
 * * CONSTRAINTS:
 * - 1 <= stones.length <= 30
 * - 1 <= stones[i] <= 1000
 * * INPUT/OUTPUT FORMATS:
 * Input: An array of integers stones.
 * Output: An integer representing the weight of the last stone (or 0).
 * * Example 1:
 * Input: stones = [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
 * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
 * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
 * we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.
 * * Example 2:
 * Input: stones = [1]
 * Output: 1
 * * (Note: As this is not a Dynamic Programming problem, DP concepts like
 * Recursion Trees and Tabulation states are omitted in favor of the Non-DP roadmap).
 * ==================================================================================================
 */
public class LastStoneWeight {

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Priority Queue (Max-Heap)
     * ==============================================================================================
     * Detailed Intuition:
     * The problem requires us to repeatedly find and extract the two maximum values from a dynamic
     * collection, process them, and insert the result back. A Max-Heap (PriorityQueue configured
     * in reverse order) is the perfect data structure for this. It allows us to retrieve the maximum
     * element in O(log N) time and maintain the sorted state dynamically as stones are smashed.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N) - We insert N elements into the heap, taking O(N log N) time.
     * In the worst case, we do N-1 smashes. Each smash requires two O(log N) polls and one
     * O(log N) offer. Thus, the total time is strictly bounded by O(N log N).
     * - Space Complexity: O(N) Total - Heap space requires O(N) to store the N integer elements in
     * the PriorityQueue. Auxiliary stack space is O(1) as the process is entirely iterative.
     * ==============================================================================================
     */
    public static int optimalApproachMaxHeap(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        // Initialize a Max-Heap using a custom comparator (or Collections.reverseOrder())
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // Populate the heap
        for (int stone : stones) {
            maxHeap.offer(stone);
        }

        // Simulate the smashing process
        while (maxHeap.size() > 1) {
            int y = maxHeap.poll(); // Heaviest
            int x = maxHeap.poll(); // Second heaviest

            if (x != y) {
                maxHeap.offer(y - x);
            }
        }

        // Return the last remaining stone, or 0 if the heap is empty
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Dynamic List Simulation with Repeated Sorting
     * ==============================================================================================
     * Detailed Intuition:
     * If we don't use a heap, we can simulate the process using a standard List. In every iteration,
     * we sort the list in ascending order to find the two heaviest stones (which will be at the very
     * end of the list). We remove them, calculate the difference, and if it's greater than 0,
     * add it back to the list before sorting again in the next iteration.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 log N) - In the worst case, we perform N iterations. In each iteration,
     * we sort an array of decreasing size. Sorting takes O(K log K), leading to O(N^2 log N) overall.
     * - Space Complexity: O(N) Total - Heap space requires O(N) for the dynamic ArrayList structure.
     * Auxiliary stack space is O(1).
     * ==============================================================================================
     */
    public static int bruteForceApproach(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        // Use Java 8 Streams to quickly convert primitive array to an ArrayList
        List<Integer> list = Arrays.stream(stones)
                .boxed()
                .collect(Collectors.toList());

        while (list.size() > 1) {
            // Sort to easily access the two heaviest stones at the end of the list
            Collections.sort(list);

            int n = list.size();
            int y = list.remove(n - 1); // Heaviest
            int x = list.remove(n - 2); // Second heaviest

            if (x != y) {
                list.add(y - x);
            }
        }

        return list.isEmpty() ? 0 : list.get(0);
    }

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Counting Sort / Bucket Array
     * ==============================================================================================
     * Detailed Intuition:
     * The constraints specify that stone weights are strictly between 1 and 1000. Whenever we have
     * a small, bounded range, Counting/Bucket Sort becomes viable. We create an array of size 1001
     * to keep track of the frequency of each weight. We iterate downwards from the maximum possible
     * weight (1000), simulating the collisions by matching pairs of stones at the current weight,
     * and propagating any remaining mass down to smaller buckets.
     * * Complexity Analysis:
     * - Time Complexity: O(N + M) where M is the maximum stone weight (1000). Populating the buckets
     * takes O(N). Iterating through the buckets takes O(M). In the worst case (chain collisions),
     * we might revisit buckets, but operations scale with the bounded maximum.
     * - Space Complexity: O(M) Total - Heap space requires an array of size M (1001) to store the
     * frequencies. Auxiliary stack space is O(1).
     * ==============================================================================================
     */
    public static int alternativeApproachCounting(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        int maxWeight = 1000;
        int[] buckets = new int[maxWeight + 1];

        // Populate the frequency array
        for (int stone : stones) {
            buckets[stone]++;
        }

        int currentHeaviest = maxWeight;

        while (currentHeaviest > 0) {
            // If there are no stones of this weight, move down
            if (buckets[currentHeaviest] == 0) {
                currentHeaviest--;
            }
            // If we have an odd number of stones, one will be left over after matching the rest
            else if (buckets[currentHeaviest] % 2 != 0) {
                buckets[currentHeaviest] = 1; // 1 stone left waiting for a collision
                int secondHeaviest = currentHeaviest - 1;

                // Find the next heaviest stone
                while (secondHeaviest > 0 && buckets[secondHeaviest] == 0) {
                    secondHeaviest--;
                }

                // If no other stones exist, this is our last stone
                if (secondHeaviest == 0) {
                    return currentHeaviest;
                }

                // Collision occurs
                buckets[currentHeaviest]--; // The heaviest is destroyed
                buckets[secondHeaviest]--;  // The second heaviest is destroyed
                buckets[currentHeaviest - secondHeaviest]++; // Add the resulting fragment

                // We need to re-evaluate from the newly created fragment's weight or below
                currentHeaviest = Math.max(currentHeaviest - secondHeaviest, secondHeaviest);
            }
            // If even number, they all obliterate each other
            else {
                buckets[currentHeaviest] = 0;
                currentHeaviest--;
            }
        }

        return 0;
    }

    /**
     * ==============================================================================================
     * 4. Testing Suite
     * ==============================================================================================
     */
    public static void main(String[] args) {
        // Standard Example 1 (Multiple smashes)
        int[] test1 = {2, 7, 4, 1, 8, 1};
        System.out.println("Test 1: " + Arrays.toString(test1));
        System.out.println("Optimal (Max-Heap): " + optimalApproachMaxHeap(test1));
        System.out.println("Brute Force (List): " + bruteForceApproach(test1));
        System.out.println("Bucket Array:       " + alternativeApproachCounting(test1));
        System.out.println("Expected:           1\n");

        // Standard Example 2 (Single stone)
        int[] test2 = {1};
        System.out.println("Test 2: " + Arrays.toString(test2));
        System.out.println("Optimal (Max-Heap): " + optimalApproachMaxHeap(test2));
        System.out.println("Brute Force (List): " + bruteForceApproach(test2));
        System.out.println("Bucket Array:       " + alternativeApproachCounting(test2));
        System.out.println("Expected:           1\n");

        // Edge Case: Two equal stones (Should obliterate to 0)
        int[] test3 = {10, 10};
        System.out.println("Test 3 (Equal Stones): " + Arrays.toString(test3));
        System.out.println("Optimal (Max-Heap): " + optimalApproachMaxHeap(test3));
        System.out.println("Expected:           0\n");

        // Edge Case: All equal stones (Odd count should leave 1 stone)
        int[] test4 = {7, 7, 7};
        System.out.println("Test 4 (Odd Equal): " + Arrays.toString(test4));
        System.out.println("Optimal (Max-Heap): " + optimalApproachMaxHeap(test4));
        System.out.println("Expected:           7\n");

        // Edge Case: Empty array
        int[] test5 = {};
        System.out.println("Test 5 (Empty Array): " + Arrays.toString(test5));
        System.out.println("Optimal (Max-Heap): " + optimalApproachMaxHeap(test5));
        System.out.println("Expected:           0\n");
    }
}