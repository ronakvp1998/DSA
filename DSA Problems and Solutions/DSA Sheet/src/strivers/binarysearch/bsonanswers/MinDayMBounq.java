package strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 1482. Minimum Number of Days to Make m Bouquets
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * You are given an integer array bloomDay, an integer m and an integer k.
 * You want to make m bouquets. To make a bouquet, you need to use k adjacent
 * flowers from the garden.
 * * The garden consists of n flowers, the ith flower will bloom in the
 * bloomDay[i] and then can be used in exactly one bouquet.
 * * Return the minimum number of days you need to wait to be able to make m
 * bouquets from the garden. If it is impossible to make m bouquets return -1.
 * * * EXAMPLES:
 * Example 1:
 * Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
 * Output: 3
 * Explanation: Let us see what happened in the first three days. x means
 * flower bloomed and _ means flower did not bloom in the garden.
 * We need 3 bouquets each should contain 1 flower.
 * After day 1: [x, _, _, _, _]   // we can only make one bouquet.
 * After day 2: [x, _, _, _, x]   // we can only make two bouquets.
 * After day 3: [x, _, x, _, x]   // we can make 3 bouquets. The answer is 3.
 * * Example 2:
 * Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
 * Output: -1
 * Explanation: We need 3 bouquets each has 2 flowers, that means we need 6
 * flowers. We only have 5 flowers so it is impossible to get the needed
 * bouquets and we return -1.
 * * Example 3:
 * Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
 * Output: 12
 * Explanation: We need 2 bouquets each should have 3 flowers.
 * Here is the garden after the 7 and 12 days:
 * After day 7: [x, x, x, x, _, x, x]
 * We can make one bouquet of the first three flowers that bloomed. We cannot
 * make another bouquet from the last three flowers that bloomed because they
 * are not adjacent.
 * After day 12: [x, x, x, x, x, x, x]
 * It is obvious that we can make two bouquets in different ways.
 * * * CONSTRAINTS:
 * - bloomDay.length == n
 * - 1 <= n <= 10^5
 * - 1 <= bloomDay[i] <= 10^9
 * - 1 <= m <= 10^6
 * - 1 <= k <= n
 * ============================================================================
 */
public class MinDayMBounq {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer)
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Base Case Check: If the total flowers required (m * k) is greater
     * than the total flowers in the garden (n), it's impossible. Return -1.
     * (Note: Cast to long to prevent integer overflow since m=10^6 and k=10^5).
     * 2. Identify Search Space: The minimum possible answer is the minimum
     * day a flower blooms. The maximum possible answer is the maximum day
     * a flower blooms.
     * 3. Apply Binary Search: Calculate 'mid' day. Check if we can form 'm'
     * bouquets by 'mid' day.
     * 4. If true, 'mid' is a potential answer. Save it and search the left
     * half (high = mid - 1) for a smaller (earlier) day.
     * 5. If false, we need more time. Search the right half (low = mid + 1).
     * * * DETAILED INTUITION:
     * We observe a monotonic property: If we can make 'm' bouquets on day 'X',
     * we can certainly make them on day 'X + 1', 'X + 2', etc. Conversely, if
     * we cannot make them on day 'X', we cannot make them on day 'X - 1'.
     * Because the validity of our condition flips from false to true at exactly
     * one point and stays true, we can optimize our search using Binary Search
     * on the answer range rather than checking day by day.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log(Max_Day - Min_Day)), where N is the length
     * of bloomDay. Finding min/max takes O(N). The binary search range is
     * at most 10^9, so the while loop runs about log2(10^9) ≈ 30 times.
     * Each check takes O(N) time. Overall time is extremely fast.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative approach, no recursion).
     * - Heap Space: O(1) (No new objects or arrays instantiated).
     */
    public static int minDaysOptimal(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        // Cast to long to prevent overflow: 10^6 * 10^5 = 10^11 > Integer.MAX_VALUE
        if ((long) m * k > n) {
            return -1;
        }

        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        for (int day : bloomDay) {
            low = Math.min(low, day);
            high = Math.max(high, day);
        }

        int optimalDays = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canMakeBouquets(bloomDay, mid, m, k)) {
                optimalDays = mid; // Possible answer, but try to find an earlier day
                high = mid - 1;
            } else {
                low = mid + 1; // Not enough time, must wait longer
            }
        }

        return optimalDays;
    }

    // Helper method to check if we can make 'm' bouquets by 'currentDay'
    private static boolean canMakeBouquets(int[] bloomDay, int currentDay, int m, int k) {
        int bouquetsCount = 0;
        int consecutiveFlowers = 0;

        for (int day : bloomDay) {
            if (day <= currentDay) {
                consecutiveFlowers++;
                if (consecutiveFlowers == k) {
                    bouquetsCount++;
                    consecutiveFlowers = 0; // Reset for the next bouquet
                }
            } else {
                consecutiveFlowers = 0; // Contiguity broken, reset counter
            }
        }
        return bouquetsCount >= m;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Initial validation check (m * k > n).
     * 2. Find the minimum and maximum days in the bloomDay array.
     * 3. Iterate sequentially from the minimum day up to the maximum day.
     * 4. For each day, linearly scan the bloomDay array to see if 'm'
     * bouquets can be formed.
     * 5. The first day that satisfies the condition is guaranteed to be
     * the minimum day.
     * * * DETAILED INTUITION:
     * This is the literal translation of the problem statement. We simulate
     * time passing day by day. Since we check in strictly increasing order,
     * the very first success is naturally our minimum day. While logically
     * sound, it fails under competitive constraints due to the massive range
     * of possible days (up to 10^9).
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * (Max_Day - Min_Day)). In the worst case,
     * Max_Day is 10^9, leading to 10^14 operations (guaranteed Time Limit
     * Exceeded).
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int minDaysBruteForce(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if ((long) m * k > n) {
            return -1;
        }

        int minDay = Integer.MAX_VALUE;
        int maxDay = Integer.MIN_VALUE;
        for (int day : bloomDay) {
            minDay = Math.min(minDay, day);
            maxDay = Math.max(maxDay, day);
        }

        // Linear scan over the answer space
        for (int currentDay = minDay; currentDay <= maxDay; currentDay++) {
            if (canMakeBouquets(bloomDay, currentDay, m, k)) {
                return currentDay;
            }
        }

        return -1;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * For this specific contiguous subset problem with a massive output range,
     * Binary Search on Answer is the singular optimal paradigm.
     * * Techniques like Sliding Window or Two Pointers do not apply because the
     * constraint is based on independent numeric thresholds (bloom values <= X)
     * rather than contiguous sums or dynamic lengths. The size of the "window"
     * (k) is rigidly fixed, and we are searching for a value threshold, not
     * subarray boundaries.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Thorough testing against standard cases, impossible cases, and integer
     * overflow edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Minimum Days to Make Bouquets Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] bloom1 = {1, 10, 3, 10, 2};
        int m1 = 3, k1 = 1;
        runTestCase(1, bloom1, m1, k1, 3);

        // Test Case 2: Impossible case (Example 2)
        int[] bloom2 = {1, 10, 3, 10, 2};
        int m2 = 3, k2 = 2;
        runTestCase(2, bloom2, m2, k2, -1);

        // Test Case 3: Contiguous requirement check (Example 3)
        int[] bloom3 = {7, 7, 7, 7, 12, 7, 7};
        int m3 = 2, k3 = 3;
        runTestCase(3, bloom3, m3, k3, 12);

        // Test Case 4: Edge Case - Exactly enough flowers
        int[] bloom4 = {5, 5, 5};
        int m4 = 1, k4 = 3;
        runTestCase(4, bloom4, m4, k4, 5);

        // Test Case 5: Edge Case - Integer Overflow Prevention Check
        // n = 3, m = 1000000, k = 100000. m*k = 10^11.
        // If int overflow happens, (m*k) might wrap to a negative number,
        // bypassing the initial length check and causing issues.
        int[] bloom5 = {1, 2, 3};
        int m5 = 1000000, k5 = 100000;
        runTestCase(5, bloom5, m5, k5, -1);
    }

    private static void runTestCase(int testNumber, int[] bloomDay, int m, int k, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = minDaysOptimal(bloomDay, m, k);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: bloomDay = " + java.util.Arrays.toString(bloomDay) +
                ", m = " + m + ", k = " + k);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}