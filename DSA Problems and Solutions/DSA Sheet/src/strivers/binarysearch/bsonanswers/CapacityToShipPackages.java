package com.questions.strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 1011. Capacity To Ship Packages Within D Days
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * A conveyor belt has packages that must be shipped from one port to another
 * within days days.
 * * The ith package on the conveyor belt has a weight of weights[i]. Each day,
 * we load the ship with packages on the conveyor belt (in the order given by
 * weights). We may not load more weight than the maximum weight capacity of
 * the ship.
 * * Return the least weight capacity of the ship that will result in all the
 * packages on the conveyor belt being shipped within days days.
 * * * EXAMPLES:
 * Example 1:
 * Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
 * Output: 15
 * Explanation: A ship capacity of 15 is the minimum to ship all the packages
 * in 5 days like this:
 * 1st day: 1, 2, 3, 4, 5
 * 2nd day: 6, 7
 * 3rd day: 8
 * 4th day: 9
 * 5th day: 10
 * Note that the cargo must be shipped in the order given, so using a ship of
 * capacity 14 and splitting the packages into parts like (2, 3, 4, 5),
 * (1, 6, 7), (8), (9), (10) is not allowed.
 * * Example 2:
 * Input: weights = [3,2,2,4,1,4], days = 3
 * Output: 6
 * Explanation: A ship capacity of 6 is the minimum to ship all the packages
 * in 3 days like this:
 * 1st day: 3, 2
 * 2nd day: 2, 4
 * 3rd day: 1, 4
 * * Example 3:
 * Input: weights = [1,2,3,1,1], days = 4
 * Output: 3
 * Explanation:
 * 1st day: 1
 * 2nd day: 2
 * 3rd day: 3
 * 4th day: 1, 1
 * * * CONSTRAINTS:
 * - 1 <= days <= weights.length <= 5 * 10^4
 * - 1 <= weights[i] <= 500
 * ============================================================================
 */
public class CapacityToShipPackages {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer)
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Define the Search Space:
     * - The absolute MINIMUM capacity our ship can have is the maximum
     * weight of a single package (`max(weights)`). If the capacity is any
     * less, we can never ship that heaviest package.
     * - The absolute MAXIMUM capacity we'd ever need is the sum of all
     * packages (`sum(weights)`). This allows us to ship everything in 1 day.
     * 2. Apply Binary Search: Calculate `mid` capacity.
     * 3. Simulate Shipping: Greedily load packages day by day. If adding a
     * package exceeds `mid`, we ship what we have and start a new day.
     * 4. Decision Logic:
     * - If `days required <= target days`, this capacity works. We record
     * it as a potential answer but search for a tighter (smaller) capacity
     * in the left half (`right = mid - 1`).
     * - If `days required > target days`, this capacity is too small. We
     * search the right half (`left = mid + 1`).
     * * * DETAILED INTUITION:
     * We transition to Binary Search because of the monotonic nature of the
     * problem. As ship capacity increases, the number of days required to ship
     * decreases monotonically. Instead of linearly guessing capacities from
     * `max` to `sum`, we efficiently zero in on the exact minimum capacity
     * bound by halving the search space.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log(Sum - Max)), where N is `weights.length`.
     * Finding the bounds takes O(N). The Binary Search loop runs
     * log(Sum(weights) - Max(weights)) times. Each mid-check iterates through
     * the array taking O(N).
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative solution, no recursion stack).
     * - Heap Space: O(1) (No dynamic structures instantiated).
     */
    public static int shipWithinDaysOptimal(int[] weights, int days) {
        int left = 0;
        int right = 0;

        // Find the boundary of our search space
        for (int weight : weights) {
            left = Math.max(left, weight); // Minimum possible capacity
            right += weight;               // Maximum possible capacity
        }

        int optimalCapacity = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (calculateDaysRequired(weights, mid) <= days) {
                optimalCapacity = mid; // Valid capacity, but try to minimize it
                right = mid - 1;
            } else {
                left = mid + 1; // Capacity too small, takes too many days
            }
        }

        return optimalCapacity;
    }

    // Helper method to simulate the shipping process
    private static int calculateDaysRequired(int[] weights, int capacity) {
        int daysNeeded = 1;
        int currentLoad = 0;

        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                // Must start a new day
                daysNeeded++;
                currentLoad = weight; // Place package on the new day's ship
            } else {
                currentLoad += weight; // Add to current day's ship
            }
        }

        return daysNeeded;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Find the starting capacity: `max(weights)`.
     * 2. Iteratively increment the capacity by 1.
     * 3. For each capacity, simulate the loading process using a helper function.
     * 4. The first capacity that allows shipping within the target `days` is
     * returned.
     * * * DETAILED INTUITION:
     * This mimics real-world trial and error. We start with the smallest
     * physically possible ship (just big enough for the heaviest item) and
     * keep renting slightly larger ships until we hit our deadline. Since we
     * are counting upward, the first hit is guaranteed to be the minimum.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * (Sum - Max)). In the worst case, the difference
     * between the sum of weights and the max weight is huge (e.g., 5*10^4
     * items of weight 500 = 2.5 * 10^7 iterations). Yields Time Limit Exceeded (TLE).
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int shipWithinDaysBruteForce(int[] weights, int days) {
        int capacity = 0;
        for (int weight : weights) {
            capacity = Math.max(capacity, weight);
        }

        while (true) {
            if (calculateDaysRequired(weights, capacity) <= days) {
                return capacity;
            }
            capacity++;
        }
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Why not Dynamic Programming?
     * DP is excellent for finding subsets or breaking arrays into K partitions
     * to minimize the max sum (like the "Split Array Largest Sum" problem,
     * which is mathematically identical to this). However, DP runs in
     * O(K * N^2) time. Given N = 50,000, N^2 is 2.5 billion operations, which
     * strictly causes Memory Limit Exceeded (MLE) and Time Limit Exceeded (TLE).
     * * * Binary Search on Answer is the singular mathematically optimal approach
     * for continuous monotonic threshold search spaces. Striver's sheets
     * heavily emphasize this pattern for "minimax" problems (minimizing the
     * maximum capacity, or maximizing the minimum distance).
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Thorough testing against standard cases and tricky boundary constraints.
     */
    public static void main(String[] args) {
        System.out.println("Running Capacity To Ship Packages Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] weights1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days1 = 5;
        runTestCase(1, weights1, days1, 15);

        // Test Case 2: Unbalanced weights (Example 2)
        int[] weights2 = {3, 2, 2, 4, 1, 4};
        int days2 = 3;
        runTestCase(2, weights2, days2, 6);

        // Test Case 3: Many small weights (Example 3)
        int[] weights3 = {1, 2, 3, 1, 1};
        int days3 = 4;
        runTestCase(3, weights3, days3, 3);

        // Test Case 4: Edge Case - Days equal to number of packages
        // Must ship exactly one package per day. Capacity must be max(weights).
        int[] weights4 = {10, 20, 30, 40, 50};
        int days4 = 5;
        runTestCase(4, weights4, days4, 50);

        // Test Case 5: Edge Case - 1 Day to ship everything
        // Capacity must equal the sum of all weights.
        int[] weights5 = {10, 20, 30, 40, 50};
        int days5 = 1;
        runTestCase(5, weights5, days5, 150);

        // Test Case 6: Edge Case - All packages same weight
        int[] weights6 = {10, 10, 10, 10, 10, 10};
        int days6 = 3;
        runTestCase(6, weights6, days6, 20);
    }

    private static void runTestCase(int testNumber, int[] weights, int days, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = shipWithinDaysOptimal(weights, days);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: weights = " + java.util.Arrays.toString(weights) + ", days = " + days);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}