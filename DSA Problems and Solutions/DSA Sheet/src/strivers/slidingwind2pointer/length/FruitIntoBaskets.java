package strivers.slidingwind2pointer.length;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: Fruit Into Baskets (Longest Subarray with <= 2 Elements)
 * ============================================================================
 *
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * There is only one row of fruit trees on the farm, oriented left to right.
 * An integer array called fruits represents the trees, where fruits[i] denotes
 * the kind of fruit produced by the ith tree.
 *
 * The goal is to gather as much fruit as possible, adhering to the owner's
 * stringent rules:
 *   1. There are two baskets available, and each basket can only contain one
 *      kind of fruit. The quantity of fruit each basket can contain is unlimited.
 *   2. Start at any tree, but as you proceed to the right, select exactly one
 *      fruit from each tree, including the starting tree.
 *   3. One of the baskets must hold the harvested fruits.
 *   4. Once reaching a tree with fruit that cannot fit into any basket, stop.
 *
 * Return the maximum number of fruits that can be picked.
 *
 * Constraints (Typical for this LC problem):
 *   - 1 <= fruits.length <= 10^5
 *   - 0 <= fruits[i] < fruits.length
 *
 * Examples:
 * Input : fruits = [1, 2, 1]
 * Output : 3
 * Explanation : We will start from first tree.
 * The first tree produces the fruit of kind '1' and we will put that in the first basket.
 * The second tree produces the fruit of kind '2' and we will put that in the second basket.
 * The third tree produces the fruit of kind '1' and we have first basket that is already
 * holding fruit of kind '1'. So we will put it in first basket.
 *
 * Input : fruits = [1, 2, 3, 2, 2]
 * Output : 4
 * Explanation : we will start from second tree.
 * The first basket contains fruits from second, fourth and fifth.
 * The second basket will contain fruit from third tree.
 *
 * Note: This is fundamentally a "Longest Subarray with at most 2 distinct elements" problem.
 * Because this is a Non-DP problem, we will follow the Section 2.2 roadmap.
 * ============================================================================
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class FruitIntoBaskets {

    /**
     * ------------------------------------------------------------------------
     * 2.2 PHASE 1: OPTIMAL APPROACH (Sliding Window / Two Pointers with Map)
     * ------------------------------------------------------------------------
     *
     * Detailed Intuition:
     * The problem asks for the longest contiguous subarray containing at most 2
     * distinct integers (representing fruit types). A brute force approach would
     * repeatedly check subarrays, doing redundant work. The Sliding Window technique
     * is the optimal pattern here. We maintain a "window" using 'left' and 'right'
     * pointers. We expand the window by moving 'right' and adding fruits to a HashMap
     * (which tracks fruit frequency). If the HashMap size exceeds 2 (meaning we have
     * 3 types of fruit), the window is invalid. We then shrink the window from the
     * 'left' until the HashMap size drops back to 2, removing fruits as we go.
     * We record the maximum valid window size at each step.
     *
     * Complexity Analysis:
     * Time Complexity: O(N)
     *   - Both the 'right' and 'left' pointers traverse the array exactly once.
     *   - HashMap operations (put, get, remove) take O(1) time on average.
     * Space Complexity: O(1)
     *   - Auxiliary Heap Space: O(1). The HashMap will store a MAXIMUM of 3 key-value
     *     pairs at any given time before being shrunk. Therefore, memory scales constantly,
     *     independent of N.
     *   - Auxiliary Stack Space: O(1), no recursion is used.
     * ------------------------------------------------------------------------
     */
    public int totalFruitOptimal(int[] fruits) {
        if (fruits == null || fruits.length == 0) return 0;

        Map<Integer, Integer> fruitFrequency = new HashMap<>();
        int maxFruits = 0;
        int left = 0, right = 0;

        while (right < fruits.length) {
            // Add current tree's fruit to our basket tracking
            fruitFrequency.put(fruits[right], fruitFrequency.getOrDefault(fruits[right], 0) + 1);

            // If we have more than 2 types of fruit, our baskets are overflowing.
            // We must drop fruits from the left side until we only have 2 types again.
            while (fruitFrequency.size() > 2) {
                int leftFruit = fruits[left];
                fruitFrequency.put(leftFruit, fruitFrequency.get(leftFruit) - 1);

                // If we've completely dropped a fruit type, remove it from the map
                if (fruitFrequency.get(leftFruit) == 0) {
                    fruitFrequency.remove(leftFruit);
                }
                left++; // Shrink the window
            }

            // At this point, the window is valid (<= 2 fruit types)
            maxFruits = Math.max(maxFruits, right - left + 1);
            right++;
        }

        return maxFruits;
    }

    /**
     * ------------------------------------------------------------------------
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (The "Think It" Stage)
     * ------------------------------------------------------------------------
     *
     * Detailed Intuition:
     * The most straightforward way to solve this is to evaluate every single possible
     * subarray. For each starting index 'i', we iterate forward with a pointer 'j',
     * adding fruits to a HashSet. As soon as the HashSet contains more than 2 distinct
     * fruits, we break out of the inner loop because any longer subarray starting
     * at 'i' will also be invalid. We track the maximum valid length encountered.
     * This establishes our functional baseline.
     *
     * Complexity Analysis:
     * Time Complexity: O(N^2)
     *   - In the worst case (e.g., an array of only 2 distinct fruits like [1,2,1,2...]),
     *     the inner loop runs completely to the end for every starting index 'i'.
     *     (N) + (N-1) + (N-2) ... = N(N+1)/2 operations.
     * Space Complexity: O(1)
     *   - Auxiliary Heap Space: O(1). The HashSet holds at most 3 elements at any time.
     *   - Auxiliary Stack Space: O(1).
     * ------------------------------------------------------------------------
     */
    public int totalFruitBruteForce(int[] fruits) {
        if (fruits == null || fruits.length == 0) return 0;

        int maxFruits = 0;

        for (int i = 0; i < fruits.length; i++) {
            Set<Integer> currentBaskets = new HashSet<>();
            int currentCount = 0;

            for (int j = i; j < fruits.length; j++) {
                currentBaskets.add(fruits[j]);

                // If we pick up a 3rd type of fruit, stop processing this subarray
                if (currentBaskets.size() > 2) {
                    break;
                }

                currentCount++;
                maxFruits = Math.max(maxFruits, currentCount);
            }
        }

        return maxFruits;
    }

    /**
     * ------------------------------------------------------------------------
     * 2.2 PHASE 3: ALTERNATIVE APPROACH (State-Variable Sliding Window - O(1) Hashless)
     * ------------------------------------------------------------------------
     *
     * Detailed Intuition:
     * While Phase 1 is O(N) time and O(1) space, creating and garbage-collecting Map
     * nodes carries a slight overhead. Can we optimize away the HashMap entirely? Yes.
     * Since we only ever care about TWO types of fruit, we can use integer variables
     * to track 'type1' and 'type2'.
     * The trick: As we iterate, we also need to know how far back to reset if we hit a
     * 3rd type of fruit. We do this by tracking the number of *continuous* fruits of
     * the most recently seen type (`lastFruitCount`).
     *
     * If we hit a new fruit (type 3):
     * 1. Our new window length drops to `lastFruitCount + 1`
     *    (the continuous streak of the previous fruit, plus the new fruit).
     * 2. 'type1' becomes the previous fruit.
     * 3. 'type2' becomes the new fruit.
     *
     * Complexity Analysis:
     * Time Complexity: O(N)
     *   - We iterate through the array strictly once. No inner loops or Hash lookups.
     * Space Complexity: O(1)
     *   - Auxiliary Heap Space: O(1). Strictly primitive variables used. Zero allocations.
     *   - Auxiliary Stack Space: O(1).
     * ------------------------------------------------------------------------
     */
    public int totalFruitSpaceOptimized(int[] fruits) {
        if (fruits == null || fruits.length == 0) return 0;

        int maxFruits = 0;
        int currentLength = 0;
        int type1 = -1, type2 = -1;
        int lastFruitCount = 0; // Tracks consecutive streak of the most recently seen fruit

        for (int fruit : fruits) {
            // Case A: The fruit matches one of our baskets
            if (fruit == type1 || fruit == type2) {
                currentLength++;
            }
            // Case B: We found a 3rd type of fruit
            else {
                // The new valid window consists of the continuous block of the
                // immediately preceding fruit, plus this new fruit.
                currentLength = lastFruitCount + 1;
            }

            // Update state variables
            if (fruit == type2) {
                lastFruitCount++; // Continuing streak of type2
            } else {
                // The fruit is either type1 or a brand new type.
                // In either case, it becomes our new "last seen" fruit (type2).
                lastFruitCount = 1;
                type1 = type2;
                type2 = fruit;
            }

            maxFruits = Math.max(maxFruits, currentLength);
        }

        return maxFruits;
    }

    /**
     * ------------------------------------------------------------------------
     * 4. TESTING SUITE
     * ------------------------------------------------------------------------
     * Thoroughly testing all approaches using standard and edge cases.
     * Utilizing Java 8 Streams to format and print test results.
     */
    public static void main(String[] args) {
        FruitIntoBaskets solver = new FruitIntoBaskets();

        // Define Test Cases
        List<TestCase> tests = Arrays.asList(
                new TestCase("Example 1 (Standard)", new int[]{1, 2, 1}, 3),
                new TestCase("Example 2 (Standard)", new int[]{1, 2, 3, 2, 2}, 4),
                new TestCase("Example 3 (Extended Valid)", new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}, 5), // Subarray [1, 2, 1, 1, 2]
                new TestCase("Edge: Only one fruit type", new int[]{0, 0, 0, 0}, 4),
                new TestCase("Edge: All distinct fruits", new int[]{1, 2, 3, 4, 5}, 2),
                new TestCase("Edge: Empty array", new int[]{}, 0),
                new TestCase("Edge: Zero-value constraints", new int[]{0, 1, 0, 2}, 3) // Subarray [0, 1, 0]
        );

        System.out.println("=========================================================");
        System.out.println("🧪 RUNNING TEST SUITE: Fruit Into Baskets");
        System.out.println("=========================================================\n");

        // Java 8 Stream iteration over test cases
        IntStream.range(0, tests.size()).forEach(i -> {
            TestCase test = tests.get(i);
            int[] arr = test.input;
            int expected = test.expected;

            int resBrute = solver.totalFruitBruteForce(arr);
            int resOptimal = solver.totalFruitOptimal(arr);
            int resStateVar = solver.totalFruitSpaceOptimized(arr);

            boolean pass = (resBrute == expected) && (resOptimal == expected) && (resStateVar == expected);

            System.out.printf("Test %d: %s\n", i + 1, test.name);
            System.out.printf("  Input    : %s\n", Arrays.toString(arr));
            System.out.printf("  Expected : %d\n", expected);
            System.out.printf("  Brute F. : %d\n", resBrute);
            System.out.printf("  Optimal  : %d\n", resOptimal);
            System.out.printf("  StateVar : %d\n", resStateVar);
            System.out.printf("  Status   : %s\n\n", pass ? "✅ PASS" : "❌ FAIL");
        });
    }

    // Helper class for test definitions
    static class TestCase {
        String name;
        int[] input;
        int expected;

        TestCase(String name, int[] input, int expected) {
            this.name = name;
            this.input = input;
            this.expected = expected;
        }
    }
}