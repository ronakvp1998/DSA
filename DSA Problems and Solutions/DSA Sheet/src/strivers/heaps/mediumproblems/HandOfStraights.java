package strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ==================================================================================================
 * PROBLEM STATEMENT: 846. Hand of Straights
 * --------------------------------------------------------------------------------------------------
 * Alice has some number of cards and she wants to rearrange the cards into groups so that each group
 * is of size groupSize, and consists of groupSize consecutive cards.
 * Given an integer array hand where hand[i] is the value written on the ith card and an integer
 * groupSize, return true if she can rearrange the cards, or false otherwise.
 * * Note: This question is the same as 1296:
 * https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
 * * EXAMPLES:
 * Example 1:
 * Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
 * Output: true
 * Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]
 * * Example 2:
 * Input: hand = [1,2,3,4,5], groupSize = 4
 * Output: false
 * Explanation: Alice's hand can not be rearranged into groups of 4.
 * * CONSTRAINTS:
 * - 1 <= hand.length <= 10^4
 * - 0 <= hand[i] <= 10^9
 * - 1 <= groupSize <= hand.length
 * * NOTE: As this is a Greedy / Hashing problem and not a Dynamic Programming problem, DP concepts
 * like Recursion Trees and Tabulation states are omitted in favor of the Non-DP Progressive Roadmap.
 * ==================================================================================================
 */

import java.util.*;
import java.util.stream.Collectors;

public class HandOfStraights {


    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Stream API + PriorityQueue (Min-Heap)
     * ==============================================================================================
     * Detailed Intuition:
     * Similar to the optimal approach, but leveraging Java 8 Stream API to build a frequency map.
     * Instead of a TreeMap, we load all unique keys into a PriorityQueue (Min-Heap). This allows
     * us to repeatedly poll the smallest available card and attempt to build a sequence. We keep
     * checking the frequency map, and if a card count drops to zero, we ignore it when it pops
     * from the heap.
     * * Complexity Analysis:
     * - Time Complexity: O(N + K log K). Building the map with streams is O(N). Building the Heap
     * takes O(K). Extracting the minimum and polling takes O(K log K) across the process.
     * - Space Complexity: O(K) heap space for the frequency map and PriorityQueue.
     */
    public static boolean isNStraightHandStreamAPI(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) {
            return false;
        }

        // 1. Build Frequency Map using Java 8 Streams
        Map<Integer, Long> frequencyMap = Arrays.stream(hand)
                .boxed()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        // 2. Load unique cards into a Min-Heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(frequencyMap.keySet());

        while (!minHeap.isEmpty()) {
            int smallestCard = minHeap.peek();

            // If we've exhausted this card in previous sequences, remove it
            if (frequencyMap.get(smallestCard) == 0) {
                minHeap.poll();
                continue;
            }

            // Attempt to build a group of 'groupSize' starting from 'smallestCard'
            for (int i = 0; i < groupSize; i++) {
                int currentCard = smallestCard + i;
                long count = frequencyMap.getOrDefault(currentCard, 0L);

                if (count == 0) {
                    return false; // Sequence broken
                }

                frequencyMap.put(currentCard, count - 1);
            }
        }

        return true;
    }

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - TreeMap Frequency Counter (The Recommended Approach)
     * ==============================================================================================
     * Detailed Intuition:
     * To form groups of consecutive cards, we must always start building a group from the smallest
     * available card. If we don't, the smallest card will be left behind with no way to be part of
     * a valid sequence.
     * A `TreeMap` naturally keeps its keys (the card values) sorted. We can count the frequency of
     * each card. Then, we iterate through the sorted keys. If a card has a frequency `count > 0`,
     * we are forced to start `count` number of sequences from this card. We then check if the next
     * `groupSize - 1` consecutive cards have at least `count` occurrences available. If they do,
     * we subtract `count` from their frequencies. If they don't, it's impossible to form the groups.
     * * Complexity Analysis:
     * - Time Complexity: O(N log K), where N is the number of cards and K is the number of unique
     * cards. Inserting into the TreeMap takes O(N log K). Iterating and updating takes O(N log K).
     * - Space Complexity: O(K) heap space for the TreeMap to store at most K unique card frequencies.
     * Auxiliary stack space is O(1).
     */
    public static boolean isNStraightHandOptimal(int[] hand, int groupSize) {
        // Base condition: total cards must be perfectly divisible by groupSize
        if (hand.length % groupSize != 0) {
            return false;
        }

        TreeMap<Integer, Integer> cardCounts = new TreeMap<>();
        for (int card : hand) {
            cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
        }

        for (int startingCard : cardCounts.keySet()) {
            int count = cardCounts.get(startingCard);

            // If this card is already fully used in previous groups, skip it
            if (count > 0) {
                // We must form 'count' sequences starting with 'startingCard'
                for (int i = 0; i < groupSize; i++) {
                    int currentCard = startingCard + i;
                    int currentCardCount = cardCounts.getOrDefault(currentCard, 0);

                    // If we don't have enough of the required consecutive card, fail
                    if (currentCardCount < count) {
                        return false;
                    }

                    // Deduct the used cards from the map
                    cardCounts.put(currentCard, currentCardCount - count);
                }
            }
        }
        return true;
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Sorting + Visited Array (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * Before reaching for advanced data structures, we can solve this by simulating the manual
     * process. First, sort the array so cards are in ascending order. We use a boolean `visited`
     * array to mark cards as used. We iterate through the array, finding the first unvisited card.
     * This card must be the start of a new group. We then scan the rest of the array to find the
     * next strictly consecutive, unvisited cards to complete the group. If we can't find them, we
     * return false.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) in the worst-case scenario. Sorting takes O(N log N). Finding the
     * consecutive cards requires scanning ahead in the array, taking up to O(N) for each of the
     * N/groupSize groups.
     * - Space Complexity: O(N) heap space for the `visited` array. Sorting may take O(log N) stack
     * space depending on the algorithm.
     */
    public static boolean isNStraightHandBruteForce(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) {
            return false;
        }

        Arrays.sort(hand);
        boolean[] visited = new boolean[hand.length];

        for (int i = 0; i < hand.length; i++) {
            if (visited[i]) {
                continue;
            }

            visited[i] = true;
            int currentCard = hand[i];
            int cardsFound = 1;

            // Scan ahead to find the rest of the consecutive sequence
            for (int j = i + 1; j < hand.length && cardsFound < groupSize; j++) {
                if (!visited[j] && hand[j] == currentCard + cardsFound) {
                    visited[j] = true;
                    cardsFound++;
                }
            }

            // If we couldn't complete a group of groupSize, it's invalid
            if (cardsFound != groupSize) {
                return false;
            }
        }

        return true;
    }

    /**
     * ==============================================================================================
     * 4. TESTING SUITE
     * ==============================================================================================
     * Main method to thoroughly test all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        // Test Cases Setup
        class TestCase {
            int[] hand;
            int groupSize;
            boolean expected;
            String description;

            TestCase(int[] hand, int groupSize, boolean expected, String description) {
                this.hand = hand;
                this.groupSize = groupSize;
                this.expected = expected;
                this.description = description;
            }
        }

        TestCase[] testCases = {
                new TestCase(new int[]{1,2,3,6,2,3,4,7,8}, 3, true, "Standard Case 1 (Valid)"),
                new TestCase(new int[]{1,2,3,4,5}, 4, false, "Standard Case 2 (Invalid - Indivisible)"),
                new TestCase(new int[]{1,2,3,4,5,6}, 2, true, "Valid case with size 2"),
                new TestCase(new int[]{8,10,12}, 3, false, "Invalid Case - Non-consecutive"),
                new TestCase(new int[]{1,1,2,2,3,3}, 3, true, "Duplicates forming valid hands"),
                new TestCase(new int[]{5}, 1, true, "Edge Case - Group size 1"),
                new TestCase(new int[]{1,2,3,4,5,6,8,9,10,11,12,13}, 6, false, "Missing link in sequence")
        };

        System.out.println("==========================================================");
        System.out.println("Running Testing Suite: 846. Hand of Straights");
        System.out.println("==========================================================");

        for (int i = 0; i < testCases.length; i++) {
            TestCase tc = testCases[i];
            System.out.println("\nTest Case " + (i + 1) + ": " + tc.description);
            System.out.println("Hand: " + Arrays.toString(tc.hand) + " | GroupSize: " + tc.groupSize);

            // Execute all three phases
            boolean optimalResult = isNStraightHandOptimal(tc.hand, tc.groupSize);
            boolean bruteResult   = isNStraightHandBruteForce(tc.hand, tc.groupSize);
            boolean streamResult  = isNStraightHandStreamAPI(tc.hand, tc.groupSize);

            // Display Results
            System.out.println("  -> Phase 1 (Optimal)     : " + optimalResult);
            System.out.println("  -> Phase 2 (Brute Force) : " + bruteResult);
            System.out.println("  -> Phase 3 (Stream API)  : " + streamResult);
            System.out.println("  -> Expected              : " + tc.expected);

            // Assert Correctness
            boolean isCorrect = (optimalResult == tc.expected) &&
                    (bruteResult == tc.expected) &&
                    (streamResult == tc.expected);
            System.out.println("  -> Consistency Check     : " + (isCorrect ? "PASS ✅" : "FAIL ❌"));
        }
    }
}