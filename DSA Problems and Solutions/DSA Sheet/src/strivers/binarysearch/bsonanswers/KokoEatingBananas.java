package strivers.binarysearch.bsonanswers;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 875. Koko Eating Bananas
 * Solved | Medium
 * * PROBLEM STATEMENT:
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has
 * piles[i] bananas. The guards have gone and will come back in h hours.
 * * Koko can decide her bananas-per-hour eating speed of k. Each hour, she
 * chooses some pile of bananas and eats k bananas from that pile. If the pile
 * has less than k bananas, she eats all of them instead and will not eat any
 * more bananas during this hour.
 * * Koko likes to eat slowly but still wants to finish eating all the bananas
 * before the guards return.
 * * Return the minimum integer k such that she can eat all the bananas within
 * h hours.
 * * EXAMPLES:
 * Example 1:
 * Input: piles = [3,6,7,11], h = 8
 * Output: 4
 * * Example 2:
 * Input: piles = [30,11,23,4,20], h = 5
 * Output: 30
 * * Example 3:
 * Input: piles = [30,11,23,4,20], h = 6
 * Output: 23
 * * CONSTRAINTS:
 * - 1 <= piles.length <= 10^4
 * - piles.length <= h <= 10^9
 * - 1 <= piles[i] <= 10^9
 * ============================================================================
 */
public class KokoEatingBananas {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer)
     * ========================================================================
     * * APPROACH & STEPS:
     * We need to find a minimum speed 'k'.
     * - If Koko eats at speed k = 1, it takes the maximum possible time.
     * - If Koko eats at speed k = max(piles), it takes exactly piles.length hours.
     * * Notice the monotonic relationship: As speed (k) increases, the total
     * hours required decreases. This strictly decreasing function tells us we
     * can use Binary Search on the *answer range* [1, max(piles)] rather than
     * searching an index.
     * * 1. Initialize 'left' to 1 (minimum possible eating speed).
     * 2. Initialize 'right' to the maximum pile size (eating any faster won't
     * save time because a pile takes a minimum of 1 hour).
     * 3. Calculate 'mid' speed. Check how many hours it takes to eat all piles
     * at this speed.
     * 4. If hours <= h, this speed is valid. Record it, but try to find a
     * slower speed by searching the left half (right = mid - 1).
     * 5. If hours > h, this speed is too slow. Search the right half
     * (left = mid + 1).
     * * DETAILED INTUITION:
     * Instead of linearly guessing the speed, we halve our search space each
     * iteration. We calculate ceiling division without floating-point math by
     * using the formula: (pile + k - 1) / k.
     * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log M), where N is the number of piles and M is
     * the maximum number of bananas in a single pile. Getting the max pile takes
     * O(N). The binary search runs log(M) times. Each check takes O(N) time.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (No recursion used).
     * - Heap Space: O(1) (No dynamic data structures instantiated).
     */

     private static int minEatingSpeedOptimal(int[] piles, int h) {
         // Find maximum element
         int maxPile = Arrays.stream(piles).max().getAsInt();

         // Initialize low and high pointers
         int low = 1, high = maxPile;
         int ans = maxPile;

         // Binary search on answer space
         while (low <= high) {
             // Using low + (high - low) / 2 is safer to prevent overflow here as well
             int mid = low + (high - low) / 2;

             // Note: Now evaluating against a long
             long totalH = calculateTotalHours(piles, mid);

             // If possible, try smaller speed
             if (totalH <= h) {
                 ans = mid;
                 high = mid - 1;
             }
             // Otherwise, try larger speed
             else {
                 low = mid + 1;
             }
         }
         return ans;
     }

     // Changed return type to long
     private static long calculateTotalHours(int[] piles, int speed) {
         long totalH = 0; // Changed from int to long
         for (int bananas : piles) {
             totalH += (long) Math.ceil((double) bananas / speed);
         }
         return totalH;
     }
     
    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * The most straightforward way is to simulate the process. We start
     * guessing the speed k = 1, then k = 2, k = 3, etc., until we find the
     * first speed that allows Koko to finish within 'h' hours.
     * * DETAILED INTUITION:
     * This proves the logic of the time-calculation function. Since we want
     * the MINIMUM integer k, the very first valid k we hit while counting up
     * from 1 is guaranteed to be our answer.
     * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * M), where N is piles.length and M is the answer (k).
     * In the worst case, M can be up to 10^9, making this approach strictly
     * Time Limit Exceeded (TLE) for competitive programming constraints.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int minEatingSpeedBruteForce(int[] piles, int h) {
        int k = 1;
        while (true) {
            long hoursRequired = 0;
            for (int pile : piles) {
                // Utilizing double cast + Math.ceil for readability in Brute Force
                hoursRequired += (long) Math.ceil((double) pile / k);
            }
            if (hoursRequired <= h) {
                return k;
            }
            k++;
        }
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * For this specific problem, there are no other algorithm paradigms (like
     * Greedy, Bitmask, Two Pointers) that provide a better or mathematically
     * different approach than Binary Search on Answer.
     * * Mathematical Bound Alternative (Optimization):
     * We know that the minimum possible speed Koko *must* maintain on average
     * is the total sum of bananas divided by 'h'.
     * We can optimize the lower bound (`left`) of our binary search:
     * left = Math.ceil(Total Bananas / h).
     * * While this provides a tighter initial search space, summing the total
     * bananas requires an O(N) pass and doesn't change the O(N log M)
     * asymptotic time complexity. Binary search is already so aggressive that
     * skipping lower bounds saves negligible operations (logarithmically).
     * Therefore, the standard Binary Search in Phase 1 remains the industry standard.
     */


    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Thorough testing against standard cases and tricky edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Koko Eating Bananas Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;
        runTestCase(1, piles1, h1, 4);

        // Test Case 2: Faster eating required (Example 2)
        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;
        runTestCase(2, piles2, h2, 30);

        // Test Case 3: More relaxed time (Example 3)
        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;
        runTestCase(3, piles3, h3, 23);

        // Test Case 4: Edge Case - h equals piles.length (must eat max pile in 1 hr)
        int[] piles4 = {10, 20, 30, 40};
        int h4 = 4;
        runTestCase(4, piles4, h4, 40);

        // Test Case 5: Edge Case - Single pile, large h
        int[] piles5 = {100};
        int h5 = 100;
        runTestCase(5, piles5, h5, 1);

        // Test Case 6: Edge Case - Integer Overflow Prevention Check
        // If we sum hours incorrectly using ints, large numbers will overflow
        int[] piles6 = {805306368, 805306368, 805306368};
        int h6 = 1000000000;
        runTestCase(6, piles6, h6, 3);
    }

    private static void runTestCase(int testNumber, int[] piles, int h, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = minEatingSpeedOptimal(piles, h);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: piles = " + java.util.Arrays.toString(piles) + ", h = " + h);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}