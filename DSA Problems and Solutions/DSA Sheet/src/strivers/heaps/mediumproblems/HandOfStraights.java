package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 846. Hand of Straights (Medium)
 * ==================================================================================================
 * Alice has cards and wants to rearrange them into groups of size 'groupSize'.
 * Each group must consist of 'groupSize' consecutive cards (e.g., [1, 2, 3]).
 * Return true if possible, false otherwise.
 *
 * Example 1:
 * Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
 * Output: true ([1,2,3], [2,3,4], [6,7,8])
 *
 * Example 2:
 * Input: hand = [1,2,3,4,5], groupSize = 4
 * Output: false
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (TreeMap / Sorting + Manual Search)
 * ==================================================================================================
 * 1. Count the frequency of each card using a TreeMap (which keeps keys sorted).
 * 2. While the map is not empty:
 * a. Get the smallest card 'first'.
 * b. Try to find 'first + 1', 'first + 2', ..., 'first + groupSize - 1' in the map.
 * c. If any consecutive card is missing, return false.
 * d. Decrement the count of each card in the group.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Min-Heap + Frequency Map)
 * ==================================================================================================
 * This approach is essentially a more efficient version of the Brute Force logic.
 * 1. If (total cards % groupSize != 0), return false immediately (Edge Case).
 * 2. Use a HashMap to store frequencies.
 * 3. Use a Min-Heap to store all unique card values.
 * 4. While the Heap is not empty:
 * a. Peek at the smallest value. If its count in the map is 0, just remove it from the heap.
 * b. If count > 0, try to form a group starting from this value.
 * c. For each value in the range [val, val + groupSize - 1]:
 * - If it doesn't exist or its count is 0, return false.
 * - Otherwise, decrease its count in the HashMap.
 * ==================================================================================================
 */

import java.util.*;

public class HandOfStraights {

    public static void main(String[] args) {
        int[] hand1 = {1, 2, 3, 6, 2, 3, 4, 7, 8};
        int groupSize1 = 3;
        System.out.println("Test Case 1: " + isNStraightHandOptimal(hand1, groupSize1)); // Output: true

        int[] hand2 = {1, 2, 3, 4, 5};
        int groupSize2 = 4;
        System.out.println("Test Case 2: " + isNStraightHandOptimal(hand2, groupSize2)); // Output: false
    }

    /**
     * OPTIMAL APPROACH: Min-Heap + HashMap
     * Time Complexity: O(N log N) where N is the number of cards.
     * Space Complexity: O(N) to store frequencies and unique elements.
     */
    public static boolean isNStraightHandOptimal(int[] hand, int groupSize) {
        // Edge Case: Total cards must be divisible by groupSize
        if (hand.length % groupSize != 0) {
            return false;
        }

        // 1. Map to store frequency of each card
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int card : hand) {
            countMap.put(card, countMap.getOrDefault(card, 0) + 1);
        }

        // 2. Min-Heap to keep unique cards sorted
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int card : countMap.keySet()) {
            minHeap.add(card);
        }

        // 3. Process cards to form groups
        while (!minHeap.isEmpty()) {
            int first = minHeap.peek(); // Start with the smallest available card

            // If this card has already been exhausted by previous groups
            if (countMap.get(first) == 0) {
                minHeap.poll();
                continue;
            }

            // Try to form a consecutive group of groupSize
            for (int i = 0; i < groupSize; i++) {
                int currentCard = first + i;

                // If the next consecutive card is missing or exhausted
                if (!countMap.containsKey(currentCard) || countMap.get(currentCard) <= 0) {
                    return false;
                }

                // Decrement frequency of the current card
                countMap.put(currentCard, countMap.get(currentCard) - 1);
            }
        }

        return true;
    }
}