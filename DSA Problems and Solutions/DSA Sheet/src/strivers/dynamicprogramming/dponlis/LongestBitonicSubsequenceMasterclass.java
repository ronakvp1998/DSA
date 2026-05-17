package com.questions.strivers.dynamicprogramming.dponlis;

/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: LONGEST BITONIC SUBSEQUENCE (DP-46)
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * Given an array 'arr' of n integers, find the length of the longest bitonic sequence.
 * A sequence is considered bitonic if it strictly increases, then strictly decreases.
 * The sequence does not have to be contiguous. A sequence can also be strictly increasing
 * or strictly decreasing to be considered bitonic (the peak is at the end or beginning).
 * * EXAMPLES:
 * Example 1:
 * Input: arr = [5, 1, 4, 2, 3, 6, 8, 7]
 * Output: 6
 * Explanation: The longest bitonic sequence is [1, 2, 3, 6, 8, 7].
 * It increases from 1 to 8, then decreases to 7.
 * * Example 2:
 * Input: arr = [10, 20, 30, 40, 50, 40, 30, 20]
 * Output: 8
 * Explanation: The entire array is bitonic (increases to 50, then decreases).
 * * CONSTRAINTS:
 * - 1 <= arr.length <= 1000 (standard constraint, though our optimal solves for 10^5)
 * - 1 <= arr[i] <= 10^5
 * * ==================================================================================================
 * 💡 THE CORE INTUITION (The "Aha!" Moment)
 * ==================================================================================================
 * A bitonic sequence has a "peak" element.
 * At any peak element `arr[i]`, the longest bitonic subsequence peaking at `i` is made of:
 * 1. The Longest Increasing Subsequence (LIS) ending at `i`.
 * 2. The Longest Decreasing Subsequence (LDS) starting at `i`.
 * * Because the peak element `arr[i]` is counted twice (once in LIS, once in LDS),
 * the length of the bitonic sequence peaking at `i` is exactly:
 * Bitonic_Length[i] = LIS[i] + LDS[i] - 1
 * * Our goal is to calculate the LIS array from left-to-right, the LDS array from right-to-left,
 * and then find the maximum value of (LIS[i] + LDS[i] - 1) for all 0 <= i < n.
 *
 * * ==================================================================================================
 * 🌳 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * RECURSION TREE (Finding LIS ending at index i, e.g., arr = [5, 1, 4])
 *
 *                          State: lisEndingAt(idx)
 * *                        lisEndingAt(2) -> val: 4
 *                              /               \
 *              (Compare with 5)               (Compare with 1)
 *              4 > 5? False (Skip)            4 > 1? True! -> 1 + lisEndingAt(1)
 *                  |
 *              Base: 1 (val: 1)
 *              Result for idx 2: Max(1, 1 + 1) = 2.
 *
 * * --------------------------------------------------------------------------------------------------
 * 📦 DP TABLE STATE TRANSITIONS (Tabulation)
 * arr = [1, 5, 2, 3]
 * * Index (i) | Value | LIS[i] (Left to Right) | LDS[i] (Right to Left) | Bitonic (LIS + LDS - 1)
 * -----------------------------------------------------------------------------------------
 * 0         |   1   | 1                      | 1 (1)                  | 1 + 1 - 1 = 1
 * 1         |   5   | 2 (1->5)               | 2 (5->2)               | 2 + 2 - 1 = 3
 * 2         |   2   | 2 (1->2)               | 1 (2)                  | 2 + 1 - 1 = 2
 * 3         |   3   | 3 (1->2->3)            | 1 (3)                  | 3 + 1 - 1 = 3
 * * Max Bitonic Length = 3.
 *
 * ==================================================================================================
 */

import java.util.*;

public class LongestBitonicSubsequenceMasterclass {

    public static void main(String[] args) {
        LongestBitonicSubsequenceMasterclass solver = new LongestBitonicSubsequenceMasterclass();

        int[] arr1 = {5, 1, 4, 2, 3, 6, 8, 7};
        int[] arr2 = {10, 20, 30, 40, 50, 40, 30, 20};

        System.out.println("Input Array 1: " + Arrays.toString(arr1));
        System.out.println("---------------------------------------------------------");

        System.out.println("1. Brute Force Recursion : " + solver.phase1_bruteForce(arr1));
        System.out.println("2. Top-Down Memoization  : " + solver.phase2_memoization(arr1));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(arr1));
        System.out.println("4. Space/Time Optimized  : " + solver.phase4_binarySearchOptimization(arr1));

        System.out.println("\nInput Array 2: " + Arrays.toString(arr2));
        System.out.println("4. Space/Time Optimized  : " + solver.phase4_binarySearchOptimization(arr2));
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Intuition:
     * To find the longest bitonic subsequence, we can treat every element as a potential "peak".
     * For a peak at index 'i', we independently calculate the Longest Increasing Subsequence
     * ending at 'i', and the Longest Decreasing Subsequence starting at 'i'.
     * We do this purely recursively, branching backward for LIS and branching forward for LDS.
     * * Complexity Analysis:
     * - Time Complexity: $O(N \times 2^N)$. For each of the N peaks, calculating LIS and LDS
     * recursively takes exponential time because overlapping subproblems are repeatedly solved.
     * - Space Complexity: $O(N)$ auxiliary stack space. No extra heap structures.
     */
    public int phase1_bruteForce(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;
        int maxBitonic = 0;

        for (int i = 0; i < n; i++) {
            int lis = getLISEndingAt(arr, i);
            int lds = getLDSStartingAt(arr, i);
            maxBitonic = Math.max(maxBitonic, lis + lds - 1);
        }
        return maxBitonic;
    }

    private int getLISEndingAt(int[] arr, int currIdx) {
        int maxLen = 1;
        for (int prevIdx = 0; prevIdx < currIdx; prevIdx++) {
            if (arr[prevIdx] < arr[currIdx]) {
                maxLen = Math.max(maxLen, 1 + getLISEndingAt(arr, prevIdx));
            }
        }
        return maxLen;
    }

    private int getLDSStartingAt(int[] arr, int currIdx) {
        int maxLen = 1;
        for (int nextIdx = currIdx + 1; nextIdx < arr.length; nextIdx++) {
            if (arr[nextIdx] < arr[currIdx]) {
                maxLen = Math.max(maxLen, 1 + getLDSStartingAt(arr, nextIdx));
            }
        }
        return maxLen;
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Intuition:
     * In Phase 1, `getLISEndingAt(i)` and `getLDSStartingAt(i)` are called repeatedly for the same
     * indices. We can introduce two 1D cache arrays: `memoLIS` and `memoLDS`.
     * If an index has already been computed, we return the cached value in $O(1)$ time.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^2)$. We calculate LIS and LDS for N elements. Each element looks at
     * all previous/subsequent elements at most once due to caching.
     * - Space Complexity: $O(N)$ for the memoization arrays (heap) + $O(N)$ auxiliary stack space.
     */
    public int phase2_memoization(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;

        int[] memoLIS = new int[n];
        int[] memoLDS = new int[n];
        Arrays.fill(memoLIS, -1);
        Arrays.fill(memoLDS, -1);

        int maxBitonic = 0;
        for (int i = 0; i < n; i++) {
            int lis = memoizedLIS(arr, i, memoLIS);
            int lds = memoizedLDS(arr, i, memoLDS);
            maxBitonic = Math.max(maxBitonic, lis + lds - 1);
        }
        return maxBitonic;
    }

    private int memoizedLIS(int[] arr, int currIdx, int[] memo) {
        if (memo[currIdx] != -1) return memo[currIdx];

        int maxLen = 1;
        for (int prevIdx = 0; prevIdx < currIdx; prevIdx++) {
            if (arr[prevIdx] < arr[currIdx]) {
                maxLen = Math.max(maxLen, 1 + memoizedLIS(arr, prevIdx, memo));
            }
        }
        return memo[currIdx] = maxLen;
    }

    private int memoizedLDS(int[] arr, int currIdx, int[] memo) {
        if (memo[currIdx] != -1) return memo[currIdx];

        int maxLen = 1;
        for (int nextIdx = currIdx + 1; nextIdx < arr.length; nextIdx++) {
            if (arr[nextIdx] < arr[currIdx]) {
                maxLen = Math.max(maxLen, 1 + memoizedLDS(arr, nextIdx, memo));
            }
        }
        return memo[currIdx] = maxLen;
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage) - ✨ STANDARD DP SOLUTION
     * ==============================================================================================
     * Intuition:
     * We convert the Top-Down logic to Bottom-Up. We use two arrays `dp1` (for LIS) and `dp2` (for LDS).
     * We populate `dp1` by iterating left-to-right.
     * We populate `dp2` by iterating right-to-left.
     * Finally, we loop through to find the maximum `dp1[i] + dp2[i] - 1`.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^2)$. Two nested loops for LIS, two for LDS.
     * - Space Complexity: $O(N)$. Two 1D arrays of size N. No recursion stack overhead.
     */
    public int phase3_tabulation(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;

        // Step 1: Calculate LIS ending at each index
        int[] dp1 = new int[n];
        Arrays.fill(dp1, 1);
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[prev] < arr[i] && dp1[i] < dp1[prev] + 1) {
                    dp1[i] = dp1[prev] + 1;
                }
            }
        }

        // Step 2: Calculate LDS starting at each index
        int[] dp2 = new int[n];
        Arrays.fill(dp2, 1);
        for (int i = n - 1; i >= 0; i--) {
            for (int next = n - 1; next > i; next--) {
                if (arr[next] < arr[i] && dp2[i] < dp2[next] + 1) {
                    dp2[i] = dp2[next] + 1;
                }
            }
        }

        // Step 3: Find max bitonic length
        int maxBitonic = 0;
        for (int i = 0; i < n; i++) {
            maxBitonic = Math.max(maxBitonic, dp1[i] + dp2[i] - 1);
        }

        return maxBitonic;
    }

    /**
     * ==============================================================================================
     * PHASE 4: TIME OPTIMIZATION VIA BINARY SEARCH (The "Perfect it" Stage) - ✨ ULTIMATE SOLUTION
     * ==============================================================================================
     * Intuition:
     * The $O(N^2)$ Tabulation works, but we can compute LIS and LDS in $O(N \log N)$ time using
     * Patience Sorting (Binary Search).
     * Instead of just finding the overall LIS, we record the *insertion index* of each element
     * into the active LIS array. The insertion index + 1 gives us the EXACT length of the LIS
     * ending at that specific element!
     * We do this left-to-right for LIS, and right-to-left for LDS.
     * * Complexity Analysis:
     * - Time Complexity: $O(N \log N)$. Binary searching the insertion point takes $O(\log N)$
     * for each of the N elements, done twice.
     * - Space Complexity: $O(N)$. Using a temporary array to track active subsequences.
     */
    public int phase4_binarySearchOptimization(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;

        // Calculate LIS using Binary Search
        int[] lis = new int[n];
        List<Integer> tempLIS = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int pos = binarySearch(tempLIS, arr[i]);
            if (pos == tempLIS.size()) {
                tempLIS.add(arr[i]);
            } else {
                tempLIS.set(pos, arr[i]);
            }
            lis[i] = pos + 1; // Length of LIS ending at i
        }

        // Calculate LDS using Binary Search (traverse backward)
        int[] lds = new int[n];
        List<Integer> tempLDS = new ArrayList<>();

        for (int i = n - 1; i >= 0; i--) {
            int pos = binarySearch(tempLDS, arr[i]);
            if (pos == tempLDS.size()) {
                tempLDS.add(arr[i]);
            } else {
                tempLDS.set(pos, arr[i]);
            }
            lds[i] = pos + 1; // Length of LDS starting at i
        }

        // Find maximum bitonic sequence length
        int maxBitonic = 0;
        for (int i = 0; i < n; i++) {
            maxBitonic = Math.max(maxBitonic, lis[i] + lds[i] - 1);
        }

        return maxBitonic;
    }

    /**
     * Helper method to find the lower bound (first element >= target)
     */
    private int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE APPROACHES
     * ==============================================================================================
     * 1. Segment Trees / Fenwick Trees:
     * We can use a Fenwick Tree (Binary Indexed Tree) to find the maximum LIS ending at element
     * `arr[i]` by querying the maximum value in the range [1, arr[i]-1]. This also achieves
     * $O(N \log N)$ time but requires coordinate compression if elements are large or negative.
     * The Binary Search (Phase 4) approach is generally much cleaner to implement in interviews.
     */
}