package com.questions.strivers.binarysearch.bsonanswers;

import java.util.Arrays;

/*
Problem Statement:
You are given an array 'arr' of size 'n' which denotes the positions of stalls.
You are also given an integer 'k' which denotes the number of aggressive cows.
You need to place the cows in the stalls such that the minimum distance between
any two cows is as large as possible.

---

Examples:

Example 1:
Input: n = 6, k = 4, arr = {0,3,4,7,10,9}
Output: 3
Explanation: Place cows at {0, 3, 7, 10}. The minimum distance = 3.

Example 2:
Input: n = 5, k = 2, arr = {4,2,1,3,6}
Output: 5
Explanation: Place cows at {1, 6}. Minimum distance = 5.

---

Approach 1: Brute Force (aggressiveCows)
1. Sort stalls.
2. Try all possible minimum distances from 1 to (max-min).
3. For each distance, check if we can place all cows (using canWePlace).
4. Return the largest valid distance.
Time Complexity: O(n * (max-min)) → very slow for large inputs.
Space Complexity: O(1).

Approach 2: Optimized Binary Search (aggressiveCows2)
1. Sort stalls.
2. The answer lies between [1, max(stalls)-min(stalls)].
3. Apply binary search on this range:
   - For a mid distance, check if we can place all cows (using canWePlace2).
   - If yes → try larger distance (low = mid+1).
   - Else → try smaller distance (high = mid-1).
4. Return high (largest valid distance).
Time Complexity: O(n log(max-min)) → efficient.
Space Complexity: O(1).

Helper Function: canWePlace / canWePlace2
- Greedily place cows: place the first cow at the first stall,
  then place next cows only if distance from last placed cow ≥ given dist.
- Return true if we can place all k cows.
*/

public class AggressiveCows {

    /**
     * Helper function for Brute Force approach.
     * Checks if we can place 'cows' with at least 'dist' spacing.
     */
    private static boolean canWePlace(int[] stalls, int dist, int cows) {
        int n = stalls.length;
        int cntCows = 1;      // Place first cow at the first stall
        int last = stalls[0]; // Last placed cow position

        for (int i = 1; i < n; i++) {
            if (stalls[i] - last >= dist) {
                cntCows++;       // Place next cow
                last = stalls[i]; // Update last position
            }
            if (cntCows >= cows) return true; // Successfully placed all cows
        }
        return false;
    }

    /**
     * Brute Force Approach:
     * Try all possible distances and return the maximum valid one.
     *
     * Time Complexity: O(n * (max-min))
     * Space Complexity: O(1)
     */
    private static int aggressiveCows(int[] stalls, int k) {
        int n = stalls.length;
        Arrays.sort(stalls); // Sort stalls in ascending order

        int limit = stalls[n - 1] - stalls[0]; // Max possible distance
        for (int i = 1; i <= limit; i++) {
            if (!canWePlace(stalls, i, k)) {
                // First invalid distance → return previous
                return (i - 1);
            }
        }
        return limit; // If all worked, return max possible distance
    }

    /**
     * Helper function for Binary Search approach.
     * Same logic as canWePlace().
     */
    private static boolean canWePlace2(int[] stalls, int dist, int cows) {
        int n = stalls.length;
        int cntCows = 1;
        int last = stalls[0];

        for (int i = 1; i < n; i++) {
            if (stalls[i] - last >= dist) {
                cntCows++;
                last = stalls[i];
            }
            if (cntCows >= cows) return true;
        }
        return false;
    }

    /**
     * Optimized Approach: Binary Search on Answer
     *
     * Time Complexity: O(n log(max-min))
     * Space Complexity: O(1)
     */
    private static int aggressiveCows2(int[] stalls, int k) {
        int n = stalls.length;
        Arrays.sort(stalls);

        int low = 1, high = stalls[n - 1] - stalls[0]; // Search space

        while (low <= high) {
            int mid = (low + high) / 2; // Middle distance

            if (canWePlace2(stalls, mid, k)) {
                // If possible with 'mid' distance, try for larger
                low = mid + 1;
            } else {
                // Otherwise reduce the distance
                high = mid - 1;
            }
        }
        return high; // Largest valid distance
    }

    public static void main(String[] args) {
        int[] stalls = {0, 3, 4, 7, 10, 9};
        int k = 4;

        // Optimized binary search solution
        int ans = aggressiveCows2(stalls, k);
        System.out.println("The maximum possible minimum distance is: " + ans);
    }
}
