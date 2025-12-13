package com.questions.strivers.binarysearch.bsonanswers;

/**
 * Problem Statement: A monkey is given ‘n’ piles of bananas, whereas the 'ith' pile has ‘a[i]’ bananas.
 * An integer ‘h’ is also given, which denotes the time (in hours) for all the bananas to be eaten.
 *         Each hour, the monkey chooses a non-empty pile of bananas and eats ‘k’ bananas.
 *         If the pile contains less than ‘k’ bananas, then the monkey consumes all the bananas and won’t eat any more bananas in that hour.
 *         Find the minimum number of bananas ‘k’ to eat per hour so that the monkey can eat all the bananas within ‘h’ hours.
 *         Examples
 *         Example 1:
 *         Input Format: N = 4, a[] = {7, 15, 6, 3}, h = 8
 *         Result: 5
 *         Explanation: If Koko eats 5 bananas/hr, he will take 2, 3, 2, and 1 hour to eat the piles accordingly.
 *         So, he will take 8 hours to complete all the piles.
 *         Example 2:
 *         Input Format: N = 5, a[] = {25, 12, 8, 14, 19}, h = 5
 *         Result: 25
 *         Explanation: If Koko eats 25 bananas/hr, he will take 1, 1, 1, 1, and 1 hour to eat the piles accordingly.
 *         So, he will take 5 hours to complete all the piles.
 *         Before moving on to the solution, let’s understand how Koko will eat the bananas. Assume,
 *         the given array is {3, 6, 7, 11} and the given time i.e. h is 8.
 *         First of all, Koko cannot eat bananas from different piles. He should complete the pile he has chosen and then he can go for another pile.
 *         Now, Koko decides to eat 2 bananas/hour. So, in order to complete the first he will take
 *         3 / 2 = 2 hours. Though mathematically, he should take 1.5 hrs but it is clearly stated in the question that
 *         after completing a pile Koko will not consume more bananas in that hour.
 *         So, for the first pile, Koko will eat 2 bananas in the first hour and then he will consume 1 banana in another hour.
 *
 *         From here we can conclude that we have to take ceil of (3/2). Similarly, we will calculate the times for other piles.

 *         1st pile: ceil(3/2) = 2 hrs
 *         2nd pile: ceil(6/2) = 3 hrs
 *         3rd pile: ceil(7/2) = 4 hrs
 *         4th pile: ceil(11/2) = 6 hrs
 *
 *         Koko will take 15 hrs in total to consume all the bananas from all the piles.
 *         Observation: Upon observation, it becomes evident that the maximum number of bananas (represented by 'k') that Koko can consume in an hour is obtained from the pile that contains the largest quantity of bananas. Therefore, the maximum value of 'k' corresponds to the maximum element present in the given array.
 *         So, our answer i.e. the minimum value of ‘k’ lies between 1 and the maximum element in the array i.e. max(a[]).
 *         Now, let’s move on to the solution.
 *
 * */
public class KokoEatingBananas {

    // ----------------------------------------------
    // Brute-Force Approach
    // ----------------------------------------------

    // Utility method to find the maximum element in the array
    private static int findMax(int[] v) {
        int maxi = Integer.MIN_VALUE;
        int n = v.length;
        for (int i = 0; i < n; i++) {
            maxi = Math.max(maxi, v[i]);  // Keep track of the maximum value
        }
        return maxi;
    }

    // Calculate total hours required to finish all bananas with a given eating speed
    private static int calculateTotalHours(int[] v, int hourly) {
        int totalH = 0;
        int n = v.length;
        for (int i = 0; i < n; i++) {
            // Use Math.ceil to ensure partial piles take full hour
            totalH += Math.ceil((double)(v[i]) / (double)(hourly));
        }
        return totalH;
    }

    // Brute-force method to find the minimum bananas per hour
    private static int minimumRateToEatBananas(int[] v, int h) {
        int maxi = findMax(v);  // The max possible value for k

        // Try all possible rates from 1 to max
        for (int i = 1; i <= maxi; i++) {
            int reqTime = calculateTotalHours(v, i);  // Time taken at current rate
            if (reqTime <= h) {
                return i;  // First valid k found
            }
        }

        return maxi;  // Fallback return
    }

    /**
     * Time Complexity (Brute Force):
     * - Outer loop runs up to max(v[i]) => O(max)
     * - Inner loop for each rate: O(N)
     * - Total = O(max * N)
     *
     * Space Complexity: O(1) – constant extra space
     */

    // ----------------------------------------------
    // Optimized Binary Search Approach
    // ----------------------------------------------

    // Duplicate of findMax method for binary search approach
    private static int findMax2(int[] v) {
        int maxi = Integer.MIN_VALUE;
        int n = v.length;
        for (int i = 0; i < n; i++) {
            maxi = Math.max(maxi, v[i]);
        }
        return maxi;
    }

    // Duplicate of calculateTotalHours method for binary search approach
    private static int calculateTotalHours2(int[] v, int hourly) {
        int totalH = 0;
        int n = v.length;
        for (int i = 0; i < n; i++) {
            totalH += Math.ceil((double)(v[i]) / (double)(hourly));
        }
        return totalH;
    }

    // Binary search to find minimum valid rate (k)
    private static int minimumRateToEatBananas2(int[] v, int h) {
        int low = 1, high = findMax2(v);  // Range of k

        while (low <= high) {
            int mid = (low + high) / 2;  // Try mid rate
            int totalH = calculateTotalHours2(v, mid);

            if (totalH <= h) {
                // Can eat all in time or less, try smaller k
                high = mid - 1;
            } else {
                // Not enough, need to increase k
                low = mid + 1;
            }
        }

        return low;  // Minimum rate that satisfies the condition
    }

    /**
     * Time Complexity (Binary Search):
     * - Binary Search on range [1, max] => O(log(max))
     * - For each mid, we compute total time in O(N)
     * - Total = O(N * log(max))
     *
     * Space Complexity: O(1) – constant extra space
     */

    // ----------------------------------------------
    // Driver Method
    // ----------------------------------------------
    public static void main(String[] args) {
        int[] v = {7, 15, 6, 3};
        int h = 8;

        // Brute-force approach
        int ans = minimumRateToEatBananas(v, h);
        System.out.println("Brute-force: Koko should eat at least " + ans + " bananas/hr.");

        // Optimized binary search approach
        int ans2 = minimumRateToEatBananas2(v, h);
        System.out.println("Binary Search: Koko should eat at least " + ans2 + " bananas/hr.");
    }
}
