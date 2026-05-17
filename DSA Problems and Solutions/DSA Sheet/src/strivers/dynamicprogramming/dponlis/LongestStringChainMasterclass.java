package com.questions.strivers.dynamicprogramming.dponlis;

/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: LONGEST STRING CHAIN (LeetCode 1048)
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * You are given an array of words where each word consists of lowercase English letters.
 * `wordA` is a predecessor of `wordB` if and only if we can insert exactly one letter anywhere
 * in `wordA` without changing the order of the other characters to make it equal to `wordB`.
 * * A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a
 * predecessor of word2, word2 is a predecessor of word3, and so on.
 * * Return the length of the longest possible word chain with words chosen from the given list.
 * * EXAMPLES:
 * Input: words = ["a","b","ba","bca","bda","bdca"]
 * Output: 4
 * Explanation: One of the longest word chains is ["a","ba","bda","bdca"].
 * * CONSTRAINTS:
 * - 1 <= words.length <= 1000
 * - 1 <= words[i].length <= 16
 * - words[i] only consists of lowercase English letters.
 * * ==================================================================================================
 * 💡 THE CORE INTUITION (The "Aha!" Moment)
 * ==================================================================================================
 * This problem is a variation of the Longest Increasing Subsequence (LIS).
 * Instead of checking if `nums[i] > nums[j]`, we check if `isPredecessor(words[j], words[i])`.
 * * Crucially, because order in the input array doesn't matter (we can pick ANY words to form the chain),
 * we MUST sort the array by string length first. A word can only be a predecessor to a word that is
 * exactly 1 character longer.
 * *
 * * ==================================================================================================
 * 🌳 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * RECURSION TREE (For sorted words = ["a", "ba", "bca"])
 * State: f(currentIndex, previousIndex)
 *
 * *                                   f(idx=0, prev=-1)
 *                                  /                   \
 *                      (Pick "a") /                     \ (Skip "a")
 *                                /                       \
 *                  f(idx=1, prev=0)                 f(idx=1, prev=-1)
 *                      /             \                  /              \
 *              (Pick "ba")        (Skip "ba")    (Pick "ba")        (Skip "ba")
 *                  /                  \              /                  \
 *              f(2, prev=1)        f(2, prev=0)   f(2, prev=1)         f(2, prev=-1)
 *              | (Pick "bca")      | (Skip)       | (Pick "bca")       | (Pick "bca")
 *              Chain: 3             Chain: 2       Chain: 2             Chain: 1
 *
 * * --------------------------------------------------------------------------------------------------
 * 📦 DP TABLE STATE TRANSITIONS (Tabulation)
 * words = ["a", "b", "ba", "bca"] (Sorted by length)
 * * Index (i) | Word  | Valid Predecessors (j) | dp[i] = max(dp[i], dp[j] + 1)
 * --------------------------------------------------------------------------
 * 0         | "a"   | None                   | 1
 * 1         | "b"   | None                   | 1
 * 2         | "ba"  | "a" (0), "b" (1)       | max(1, dp[0]+1, dp[1]+1) = 2
 * 3         | "bca" | "ba" (2)               | max(1, dp[2]+1) = 3
 * *
 * ==================================================================================================
 */

import java.util.*;

public class LongestStringChainMasterclass {

    public static void main(String[] args) {
        LongestStringChainMasterclass solver = new LongestStringChainMasterclass();

        String[] words1 = {"a", "b", "ba", "bca", "bda", "bdca"};
        System.out.println("Input Array: " + Arrays.toString(words1));
        System.out.println("---------------------------------------------------------");

        System.out.println("1. Brute Force Recursion : " + solver.phase1_bruteForce(words1));
        System.out.println("2. Top-Down Memoization  : " + solver.phase2_memoization(words1));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(words1));
        System.out.println("4. Space/Time Optimized  : " + solver.phase4_spaceTimeOptimization(words1));
    }

    /**
     * Helper Method: Checks if string w1 is a predecessor of string w2.
     * w1 is a predecessor if we can insert exactly ONE character into w1 to get w2.
     * Time Complexity: O(L) where L is the length of w2.
     */
    private boolean isPredecessor(String w1, String w2) {
        if (w1.length() + 1 != w2.length()) return false;

        int first = 0;  // Pointer for w1
        int second = 0; // Pointer for w2

        while (second < w2.length()) {
            if (first < w1.length() && w1.charAt(first) == w2.charAt(second)) {
                first++;
                second++;
            } else {
                second++; // Skip the one extra character in w2
            }
        }
        // If we matched all characters of w1, it's a valid predecessor
        return first == w1.length();
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Intuition:
     * First, sort the array by string length. Then, for every word, we have two choices:
     * 1. Skip it.
     * 2. Pick it (if there is no previous word picked yet, OR if the previous word is a predecessor).
     * We return the maximum length formed by either choice.
     * * Complexity Analysis:
     * - Time Complexity: O(N * log N + 2^N * L). Sorting takes N log N. Recursion tree has 2^N
     * branches, and comparing strings takes O(L) where L is the max string length (16).
     * - Space Complexity: O(N) auxiliary stack space for the recursion tree.
     */
    public int phase1_bruteForce(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        return solveRecursive(0, -1, words);
    }

    private int solveRecursive(int idx, int prevIdx, String[] words) {
        if (idx == words.length) return 0;

        // Choice 1: Skip current word
        int skip = solveRecursive(idx + 1, prevIdx, words);

        // Choice 2: Pick current word (if valid)
        int pick = 0;
        if (prevIdx == -1 || isPredecessor(words[prevIdx], words[idx])) {
            pick = 1 + solveRecursive(idx + 1, idx, words);
        }

        return Math.max(skip, pick);
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Intuition:
     * The brute force recalculates the same (idx, prevIdx) states. We cache the results in a 2D
     * array `dp`. Since `prevIdx` can be -1, we shift it by +1 when accessing the DP array.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 * L). There are N^2 unique states. For each state, we do an O(L)
     * string comparison. Total time is bounded by N^2 * L.
     * - Space Complexity: O(N^2) for the DP table (heap) + O(N) auxiliary recursion stack.
     */
    public int phase2_memoization(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        int n = words.length;
        int[][] dp = new int[n][n + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        return solveMemo(0, -1, words, dp);
    }

    private int solveMemo(int idx, int prevIdx, String[] words, int[][] dp) {
        if (idx == words.length) return 0;

        if (dp[idx][prevIdx + 1] != -1) return dp[idx][prevIdx + 1];

        int skip = solveMemo(idx + 1, prevIdx, words, dp);

        int pick = 0;
        if (prevIdx == -1 || isPredecessor(words[prevIdx], words[idx])) {
            pick = 1 + solveMemo(idx + 1, idx, words, dp);
        }

        return dp[idx][prevIdx + 1] = Math.max(skip, pick);
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage)
     * ==============================================================================================
     * Intuition:
     * We convert the memoization into an iterative LIS pattern.
     * `dp[i]` represents the length of the longest word chain ending at index `i`.
     * For each word `i`, we look back at all previous words `j`. If word `j` is a predecessor
     * of word `i`, we update `dp[i] = max(dp[i], dp[j] + 1)`.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 * L). Nested loops iterate N^2 times, with an O(L) comparison.
     * - Space Complexity: O(N) for the 1D DP array on the heap. No recursion stack!
     */
    public int phase3_tabulation(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        int n = words.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Every word is a chain of length 1 by itself

        int maxChain = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (isPredecessor(words[j], words[i]) && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
            maxChain = Math.max(maxChain, dp[i]);
        }

        return maxChain;
    }

    /**
     * ==============================================================================================
     * PHASE 4: SPACE/TIME OPTIMIZATION (The "Perfect it" Stage) - ✨ INDUSTRY STANDARD
     * ==============================================================================================
     * Intuition:
     * The O(N^2) Tabulation works, but we can do much better! Instead of checking EVERY previous
     * word to see if it's a predecessor, we can take the current word, physically REMOVE one
     * character at a time to generate all possible predecessors, and look them up in a HashMap in O(1)!
     * * Since the max string length (L) is only 16, generating predecessors takes O(L^2) time
     * (L strings created, each taking O(L) to substring). This is fundamentally faster than O(N).
     * * Complexity Analysis:
     * - Time Complexity: O(N log N + N * L^2). Sorting takes N log N. Then for each of the N words,
     * we create L substrings of length L. Much faster than O(N^2 * L) when N is up to 1000.
     * - Space Complexity: O(N * L) to store the words and their chain lengths in the HashMap.
     */
    public int phase4_spaceTimeOptimization(String[] words) {
        // Sort words by length
        Arrays.sort(words, Comparator.comparingInt(String::length));

        // Map stores { word : length of longest chain ending at this word }
        Map<String, Integer> dpMap = new HashMap<>();
        int maxChain = 1;

        for (String word : words) {
            int currentLongest = 1;

            // Try removing exactly one character from every possible index
            for (int i = 0; i < word.length(); i++) {
                // Form the predecessor string
                String predecessor = word.substring(0, i) + word.substring(i + 1);

                // If this predecessor exists in our map, we can extend its chain
                if (dpMap.containsKey(predecessor)) {
                    currentLongest = Math.max(currentLongest, dpMap.get(predecessor) + 1);
                }
            }

            // Save the best result for the current word
            dpMap.put(word, currentLongest);
            maxChain = Math.max(maxChain, currentLongest);
        }

        return maxChain;
    }
}