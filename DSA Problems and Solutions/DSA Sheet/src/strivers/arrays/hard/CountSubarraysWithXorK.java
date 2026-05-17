package com.questions.strivers.arrays.hard;
/**
 * ============================================================================
 * 🎯 MASTERCLASS: Subarrays with XOR 'K'
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 * * Formal Problem Statement:
 * Given an array of integers A and an integer B (often referred to as k).
 * Find the total number of subarrays having bitwise XOR of all elements equal to B.
 *
 * Constraints:
 * - 1 <= A.length <= 10^5
 * - 1 <= A[i] <= 10^5
 * - 1 <= B <= 10^5
 *
 * Example 1:
 * Input Format: A = [4, 2, 2, 6, 4] , k = 6
 * Result: 4
 * Explanation: The subarrays having XOR of their elements as 6 are:
 * [4, 2], [4, 2, 2, 6, 4], [2, 2, 6], [6]
 *
 * Example 2:
 * Input Format: A = [5, 6, 7, 8, 9], k = 5
 * Result: 2
 * Explanation: The subarrays having XOR of their elements as 5 are:
 * [5] and [5, 6, 7, 8, 9]
 *
 * * Conceptual Visualization (Prefix XOR Logic):
 * Let's denote the prefix XOR up to index `i` as `Y`.
 * We are looking for a subarray ending at `i` that has an XOR equal to `k`.
 * Suppose this subarray starts at index `j+1`.
 * This means the prefix XOR up to index `j` is `X`.
 * * According to XOR properties: X ^ k = Y
 * Taking XOR with 'k' on both sides: X ^ k ^ k = Y ^ k
 * Since k ^ k = 0, this simplifies to: X = Y ^ k
 * * Visualization for A = [4, 2, 2, 6, 4], k = 6:
 * Array:       [ 4,  2,  2,  6,  4 ]
 * Prefix XOR:  [ 4,  6,  4,  2,  6 ]
 * * If current prefix XOR is 2 (at index 3), and k = 6:
 * We need previous prefix XOR = 2 ^ 6 = 4.
 * We look back and see prefix XOR '4' occurred twice (at index 0 and 2).
 * Thus, we found 2 valid subarrays ending at index 3!
 * ============================================================================
 * ### 2.2 Progressive Implementation Roadmap
 * * Phase 1: Best and recommended approach - Prefix XOR with HashMap
 * * Phase 2: Brute Force approach - The "Think it" stage (Nested loops O(N^3))
 * * Phase 3: Alternative Approaches - Optimized Brute Force (O(N^2))
 * ============================================================================
 */
import java.util.HashMap;
import java.util.Arrays;

public class CountSubarraysWithXorK {

    /**
     * ============================================================================
     * Phase 1: Best and recommended approach - Prefix XOR & Hashing
     * ============================================================================
     * Detailed Intuition:
     * Building on the conceptual visualization, we use a HashMap to keep track of
     * the frequencies of all prefix XORs we have seen so far. As we iterate, we
     * calculate the running XOR (Y). We then calculate the target XOR we need to
     * chop off from the beginning (X = Y ^ k). If X exists in our map, we add its
     * frequency to our answer. Finally, we add the current running XOR to the map.
     * * CRITICAL: We must initialize the map with (0, 1). This handles the case where
     * a prefix itself has an XOR of exactly k (i.e., Y == k, so Y ^ k = 0).
     *
     * Complexity Analysis:
     * Time (O): O(N) or O(N log N) - We traverse the array exactly once. HashMap
     * operations are O(1) average, but can be O(log N) if there are heavy collisions.
     * Space (O): O(N) heap space. In the worst case, all prefix XORs are unique,
     * so we store N entries in the HashMap. O(1) auxiliary stack space.
     */
    public static int phase1OptimalHashing(int[] a, int k) {
        int n = a.length;
        int xr = 0;
        int count = 0;

        // Map stores {prefix_xor, frequency}
        HashMap<Integer, Integer> map = new HashMap<>();

        // Base case: a prefix XOR of 0 occurs 1 time initially
        map.put(0, 1);

        for (int i = 0; i < n; i++) {
            // Calculate prefix XOR till current index
            xr ^= a[i];

            // The required prefix XOR to chop off
            int target = xr ^ k;

            // If target exists in map, it means we found valid subarrays
            if (map.containsKey(target)) {
                count += map.get(target);
            }

            // Insert current prefix XOR into map
            map.put(xr, map.getOrDefault(xr, 0) + 1);
        }

        return count;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most naive way to solve this is to generate every single possible subarray.
     * For every starting point `i`, we pick an endpoint `j`, and then we run a third
     * loop from `i` to `j` to calculate the XOR of the elements. If the XOR equals `k`,
     * we increment our count.
     *
     * Complexity Analysis:
     * Time (O): O(N^3) - Three nested loops: picking start, picking end, and calculating XOR.
     * Space (O): O(1) heap space, O(1) auxiliary stack space.
     */
    public static int phase2BruteForce(int[] a, int k) {
        int n = a.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int currentXor = 0;
                // Calculate XOR for the subarray from i to j
                for (int m = i; m <= j; m++) {
                    currentXor ^= a[m];
                }

                if (currentXor == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approaches - Optimized Brute Force (O(N^2))
     * ============================================================================
     * Detailed Intuition:
     * In the Phase 2 approach, we recalculate the XOR from scratch for every endpoint `j`.
     * Instead, we can maintain a running XOR as we expand our endpoint `j`. The XOR
     * of the subarray from `i` to `j` is simply the XOR from `i` to `j-1` ^ `a[j]`.
     * This eliminates the need for the innermost third loop.
     * * Note: Two Pointers / Sliding Window cannot be used here because XOR is not
     * monotonic. Expanding the window does not strictly increase or decrease the XOR value.
     *
     * Complexity Analysis:
     * Time (O): O(N^2) - Two nested loops.
     * Space (O): O(1) heap space, O(1) auxiliary stack space.
     */
    public static int phase3AlternativeOptimizedBrute(int[] a, int k) {
        int n = a.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            int currentXor = 0;
            // Expand the subarray starting at i
            for (int j = i; j < n; j++) {
                currentXor ^= a[j];

                if (currentXor == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Running Test Suite for Subarrays with XOR 'K'...");
        System.out.println("---------------------------------------------------\n");

        // Format: {input_array, target_k, expected_result}
        Object[][] testCases = {
                {new int[]{4, 2, 2, 6, 4}, 6, 4},               // Example 1
                {new int[]{5, 6, 7, 8, 9}, 5, 2},               // Example 2
                {new int[]{1, 1, 1, 1}, 0, 4},                  // Edge Case: Subarrays of zeroes (pairs of 1s)
                {new int[]{2, 2, 2, 2, 2}, 2, 9},               // Target equals repeating element
                {new int[]{3, 1, 2, 6}, 6, 2},                  // Normal Case
                {new int[]{10}, 10, 1},                         // Single element equals target
                {new int[]{10}, 5, 0}                           // Single element not target
        };

        boolean allPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = (int[]) testCases[i][0];
            int k = (int) testCases[i][1];
            int expected = (int) testCases[i][2];

            int res1 = phase1OptimalHashing(arr, k);
            int res2 = phase2BruteForce(arr, k);
            int res3 = phase3AlternativeOptimizedBrute(arr, k);

            boolean pass = (res1 == expected) && (res2 == expected) && (res3 == expected);

            System.out.printf("Test %d: Input Array: %s, k = %d\n", i + 1, Arrays.toString(arr), k);
            System.out.printf("  Expected              : %d\n", expected);
            System.out.printf("  Optimal (Hashing)     : %d\n", res1);
            System.out.printf("  Brute Force (O(N^3))  : %d\n", res2);
            System.out.printf("  Opt. Brute (O(N^2))   : %d\n", res3);
            System.out.printf("  Status: %s\n\n", pass ? "✅ PASS" : "❌ FAIL");

            if (!pass) {
                allPassed = false;
            }
        }

        System.out.println("---------------------------------------------------");
        System.out.println(allPassed ? "🎉 ALL TESTS PASSED!" : "⚠️ SOME TESTS FAILED.");
    }
}