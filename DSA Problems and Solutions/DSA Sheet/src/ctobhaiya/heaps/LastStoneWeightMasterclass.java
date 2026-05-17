package com.questions.ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: LAST STONE WEIGHT (LeetCode #1046)
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * You are given an array of integers `stones` where `stones[i]` is the weight
 * of the ith stone.
 * * We are playing a game with the stones. On each turn, we choose the heaviest
 * two stones and smash them together. Suppose the heaviest two stones have
 * weights x and y with x <= y. The result of this smash is:
 * - If x == y, both stones are destroyed, and
 * - If x != y, the stone of weight x is destroyed, and the stone of weight y
 * has new weight y - x.
 * * At the end of the game, there is at most one stone left.
 * Return the weight of the last remaining stone. If there are no stones left,
 * return 0.
 * * Constraints:
 * - 1 <= stones.length <= 30
 * - 1 <= stones[i] <= 1000
 * * Input/Output Formats:
 * Input: int[] stones
 * Output: int
 * * Examples:
 * Example 1:
 * Input: stones = [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
 * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
 * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
 * we combine 1 and 1 to get 0 so the array converts to [1] then that's the
 * value of the last stone.
 * * Example 2:
 * Input: stones = [1]
 * Output: 1
 * * Conceptual Visualization (Max-Heap Strategy):
 * Initial Array: [2, 7, 4, 1, 8, 1]
 * * Heap Construction (Max-Heap):
 * 8
 * /   \
 * 7     4
 * / \   /
 * 1   2 1
 * * Step 1: Pop 8 and 7. y = 8, x = 7. Difference = 1. Push 1.
 * Heap: [4, 2, 1, 1, 1]
 * * Step 2: Pop 4 and 2. y = 4, x = 2. Difference = 2. Push 2.
 * Heap: [2, 1, 1, 1]
 * * Step 3: Pop 2 and 1. y = 2, x = 1. Difference = 1. Push 1.
 * Heap: [1, 1, 1]
 * * Step 4: Pop 1 and 1. y = 1, x = 1. Difference = 0. Push nothing.
 * Heap: [1]
 * * Final State: Heap size is 1. Return the root (1).
 * * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ----------------------------------------------------------------------------
 * Phase 1: Brute Force Approach (Sorting & List Manipulation) - The "Think it" stage.
 * - Store elements in a dynamic list.
 * - Repeatedly sort the list, remove the last two elements (the largest), and
 * insert their difference back into the list if it's greater than 0.
 * * Phase 2: Optimal Approach (Priority Queue / Max-Heap) - The "Perfect it" stage.
 * - Use a Max-Heap to maintain the dynamic sorting of stones.
 * - Poll the top two elements, compute the difference, and push back if needed.
 * * Phase 3: Alternative Approach (Bucket/Counting Sort) - The "Domain Expert" stage.
 * - Given the tight constraint (stones[i] <= 1000), use an array as buckets
 * to track the frequency of each weight, avoiding log(N) operations entirely.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LastStoneWeightMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (SORTING & DYNAMIC LIST)
     * ========================================================================
     * Detailed Intuition:
     * To find the two heaviest stones, we can sort the collection. Once sorted,
     * the two heaviest are at the end of the list. We pop them, calculate their
     * difference, and if a stone survives, we put it back. Because putting it
     * back ruins the sorted order, we must sort the list all over again in the
     * next iteration.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 log N). We have at most N iterations. In each
     * iteration, we sort a list of size up to N, taking O(N log N) time.
     * - Space Complexity: O(N) auxiliary heap space to store the stones in an
     * ArrayList. O(log N) auxiliary stack space used by the sorting algorithm
     * under the hood.
     */
    public static int lastStoneWeightBruteForce(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        List<Integer> list = new ArrayList<>();
        for (int stone : stones) {
            list.add(stone);
        }

        while (list.size() > 1) {
            // Sort ascending: the two largest elements are at the end
            Collections.sort(list);

            int n = list.size();
            int y = list.remove(n - 1); // Heaviest
            int x = list.remove(n - 2); // Second heaviest

            if (y != x) {
                list.add(y - x);
            }
        }

        return list.isEmpty() ? 0 : list.get(0);
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL APPROACH (MAX-HEAP)
     * ========================================================================
     * Detailed Intuition:
     * Sorting the entire array repeatedly is a massive waste of operations when
     * we only care about the top 2 maximums at any given moment. A Priority
     * Queue (Max-Heap) is the perfect data structure here. It keeps the maximum
     * element at the root. We can extract the top two elements in O(log N) time,
     * smash them, and re-insert the remainder in O(log N) time. The heap self-
     * balances, entirely removing the need for O(N log N) full sorts.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Building the initial heap takes O(N) or
     * O(N log N) depending on insertion method. We then do at most N iterations,
     * each doing up to 3 heap operations (2 polls, 1 offer), taking O(log N) each.
     * - Space Complexity: O(N) auxiliary heap space to store the elements in
     * the PriorityQueue. O(1) auxiliary stack space.
     */
    public static int lastStoneWeightHeap(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        // Max-Heap in Java requires a custom comparator (reverse order)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int stone : stones) {
            maxHeap.offer(stone);
        }

        while (maxHeap.size() > 1) {
            int y = maxHeap.poll(); // Heaviest
            int x = maxHeap.poll(); // Second heaviest

            if (y != x) {
                maxHeap.offer(y - x);
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (BUCKET SORT / FREQUENCY ARRAY)
     * ========================================================================
     * Detailed Intuition:
     * LeetCode constraints note that `stones[i] <= 1000`. Whenever we have small,
     * bounded numerical inputs, Bucket Sort or Counting Sort should come to mind.
     * We create an array of size 1001 to store the count (frequency) of each stone
     * weight. We iterate backwards from 1000 to find the two heaviest stones.
     * This avoids tree-based balancing entirely.
     * * Complexity Analysis:
     * - Time Complexity: O(N + W) where N is the number of stones and W is the
     * maximum possible weight (1000). Finding the pairs and updating indices
     * takes linear time relative to the weight bound.
     * - Space Complexity: O(W) auxiliary heap space for the bucket array of size 1001.
     * O(1) auxiliary stack space.
     */
    public static int lastStoneWeightBucket(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        int maxWeight = 0;
        for (int stone : stones) {
            maxWeight = Math.max(maxWeight, stone);
        }

        int[] buckets = new int[maxWeight + 1];
        for (int stone : stones) {
            buckets[stone]++;
        }

        int currentWeight = maxWeight;
        while (currentWeight > 0) {
            // If there are no stones of this weight, move down
            if (buckets[currentWeight] == 0) {
                currentWeight--;
            } else {
                // If there is an even number of stones of this weight, they all
                // perfectly obliterate each other (x == y).
                buckets[currentWeight] %= 2;

                // If one stone of this weight survives
                if (buckets[currentWeight] == 1) {
                    // Find the NEXT heaviest stone
                    int nextWeight = currentWeight - 1;
                    while (nextWeight > 0 && buckets[nextWeight] == 0) {
                        nextWeight--;
                    }

                    // If no other stones exist, we found our last stone!
                    if (nextWeight == 0) {
                        return currentWeight;
                    }

                    // Smash them
                    buckets[currentWeight]--; // The heavier stone is destroyed
                    buckets[nextWeight]--;    // The second heavier is destroyed

                    // A new stone is born from the difference
                    buckets[currentWeight - nextWeight]++;

                    // The next iteration should check from the newly created
                    // stone's weight or the next highest weight, whichever is larger.
                    currentWeight = Math.max(currentWeight - nextWeight, nextWeight);
                }
            }
        }

        return 0; // Everything was destroyed
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Executing Last Stone Weight Masterclass Testing Suite...\n");

        int[][] testCases = {
                {2, 7, 4, 1, 8, 1},    // Standard Case (Example 1)
                {1},                   // Single Stone (Example 2)
                {2, 2},                // Perfect Annihilation
                {10, 4, 2, 10},        // Duplicates
                {7, 6, 7, 6, 9},       // Odd numbered smashes
                {1000, 1000, 1000},    // Max constraints
                {}                     // Empty edge case
        };

        int[] expectedResults = {1, 1, 0, 2, 3, 1000, 0};

        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(original));

            // Create copies to prevent in-place modifications/state-sharing
            int[] bruteInput = Arrays.copyOf(original, original.length);
            int[] heapInput = Arrays.copyOf(original, original.length);
            int[] bucketInput = Arrays.copyOf(original, original.length);

            // Execute
            int bruteRes = lastStoneWeightBruteForce(bruteInput);
            int heapRes = lastStoneWeightHeap(heapInput);
            int bucketRes = lastStoneWeightBucket(bucketInput);
            int expected = expectedResults[i];

            // Validation
            System.out.println("  Expected:      " + expected);
            System.out.println("  Brute Force:   " + bruteRes + (bruteRes == expected ? " ✅" : " ❌"));
            System.out.println("  Max-Heap:      " + heapRes + (heapRes == expected ? " ✅" : " ❌"));
            System.out.println("  Bucket Sort:   " + bucketRes + (bucketRes == expected ? " ✅" : " ❌"));
            System.out.println("--------------------------------------------------");
        }
    }
}