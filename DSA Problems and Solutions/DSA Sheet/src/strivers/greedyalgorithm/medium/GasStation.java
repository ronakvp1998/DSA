package strivers.greedyalgorithm.medium;

/**
 * ============================================================================
 * 134. Gas Station
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * There are n gas stations along a circular route, where the amount of gas at
 * the ith station is gas[i].
 *
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to
 * travel from the ith station to its next (i + 1)th station. You begin the
 * journey with an empty tank at one of the gas stations.
 *
 * Given two integer arrays gas and cost, return the starting gas station's
 * index if you can travel around the circuit once in the clockwise direction,
 * otherwise return -1. If there exists a solution, it is guaranteed to be unique.
 *
 * Example 1:
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * Output: 3
 * Explanation:
 * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 4. Your tank = 4 - 1 + 5 = 8
 * Travel to station 0. Your tank = 8 - 2 + 1 = 7
 * Travel to station 1. Your tank = 7 - 3 + 2 = 6
 * Travel to station 2. Your tank = 6 - 4 + 3 = 5
 * Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
 * Therefore, return 3 as the starting index.
 *
 * Example 2:
 * Input: gas = [2,3,4], cost = [3,4,3]
 * Output: -1
 * Explanation:
 * You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
 * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 0. Your tank = 4 - 3 + 2 = 3
 * Travel to station 1. Your tank = 3 - 3 + 3 = 3
 * You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
 * Therefore, you can't travel around the circuit once no matter where you start.
 *
 * Constraints:
 * n == gas.length == cost.length
 * 1 <= n <= 10^5
 * 0 <= gas[i], cost[i] <= 10^4
 * The input is generated such that the answer is unique.
 *
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class GasStation {

    /**
     * ============================================================================
     * PHASE 1: Optimal Approach (Greedy Algorithm) - The "Best" stage.
     * ============================================================================
     * Detailed Intuition:
     * This problem rests on two foundational mathematical properties:
     * 1. If the total gas available across all stations is less than the total
     *    cost to travel the circuit, a solution is IMPOSSIBLE. (Total Gas < Total Cost -> -1)
     * 2. If a solution is possible, and we start at station A and run out of gas
     *    at station B, then NO station between A and B can be the starting point.
     *    Why? Because any station between A and B would start with an empty tank,
     *    whereas our journey from A reached that middle station with >= 0 gas. If
     *    we couldn't make it to B with a head start, we definitely can't make it
     *    starting from scratch.
     *    Therefore, if our tank drops below 0 at index `i`, the next potential
     *    starting candidate is `i + 1`.
     *
     * Complexity Analysis:
     * Time: O(N) - We iterate through the gas and cost arrays exactly once.
     * Space: O(1) - O(1) heap space (only using a few integer variables). O(1) auxiliary stack space.
     */
    public int phase1OptimalGreedy(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        int currentTank = 0;
        int startingStation = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];

            // Add the net gain/loss at the current station to our running tank
            currentTank += gas[i] - cost[i];

            // If our tank goes negative, we can't reach station i + 1 from our current start.
            if (currentTank < 0) {
                // The next viable starting station is i + 1
                startingStation = i + 1;
                // Reset our tank for the new starting point evaluation
                currentTank = 0;
            }
        }

        // Property 1: If total cost exceeds total gas, no circuit is possible.
        if (totalCost > totalGas) {
            return -1;
        }

        return startingStation;
    }

    /**
     * ============================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way is to simulate the journey starting from every
     * single station. For a given starting station `i`, we try to take `n` steps.
     * We use modulo arithmetic `(i + j) % n` to wrap around the circular route.
     * If at any point the gas drops below zero, we break out and try the next
     * starting station.
     *
     * Complexity Analysis:
     * Time: O(N^2) - In the worst case, we might travel almost the entire array
     *       for every starting position before failing.
     * Space: O(1) - O(1) heap space. O(1) auxiliary stack space.
     */
    public int phase2BruteForce(int[] gas, int[] cost) {
        int n = gas.length;

        // Try every station as a potential starting point
        for (int i = 0; i < n; i++) {
            int currentTank = 0;
            boolean canComplete = true;

            // Simulate the journey for n steps
            for (int j = 0; j < n; j++) {
                int station = (i + j) % n; // Wrap around using modulo

                currentTank += gas[station] - cost[station];

                // If we run out of gas, this starting point fails
                if (currentTank < 0) {
                    canComplete = false;
                    break;
                }
            }

            // If we completed n steps without running out of gas, we found the answer
            if (canComplete) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     * Executing comprehensive tests across approaches using Java 8 Streams.
     */
    public static void main(String[] args) {
        GasStation solver = new GasStation();

        // Define Test Cases
        int[][] gasCases = {
                {1, 2, 3, 4, 5},     // Standard case, valid circuit
                {2, 3, 4},           // Impossible case
                {5, 1, 2, 3, 4},     // Start is at index 0
                {3, 1, 1},           // Single station with enough gas
                {2},                 // Single station, not enough to traverse (cost > gas)
                {5, 8, 2, 8}         // Start is somewhere in the middle
        };

        int[][] costCases = {
                {3, 4, 5, 1, 2},
                {3, 4, 3},
                {4, 4, 1, 5, 1},
                {1, 2, 2},
                {3},
                {6, 5, 6, 6}
        };

        String[] approachNames = {
                "Phase 1: Optimal (Greedy)",
                "Phase 2: Brute Force     "
        };

        System.out.println("=================================================");
        System.out.println("          TESTING GAS STATION SOLUTIONS          ");
        System.out.println("=================================================\n");

        IntStream.range(0, gasCases.length).forEach(i -> {
            int[] gas = gasCases[i];
            int[] cost = costCases[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Gas : " + Arrays.toString(gas));
            System.out.println("Cost: " + Arrays.toString(cost));

            int[] results = {
                    solver.phase1OptimalGreedy(gas, cost),
                    solver.phase2BruteForce(gas, cost)
            };

            for (int j = 0; j < results.length; j++) {
                System.out.println(approachNames[j] + " => " + results[j]);
            }
            System.out.println("-------------------------------------------------");
        });

        System.out.println("All implementations executed successfully.");
    }
}