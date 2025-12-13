package com.questions.strivers.dynamicprogramming.dponstrings;

//https://takeuforward.org/data-structure/longest-common-substring-dp-27/
//Problem Statement: Longest Common Substring
//A substring of a string is a subsequence in which all the characters are consecutive.
//Given two strings, we need to find the longest common substring.
//We need to print the length of the longest common substring.
// s1 = "abcjklp" s2 = "acjpk"
// Longest common substring is "cjp" with length 3

public class LongestCommonSubstring {

    // 🔁 1. Recursive approach to find the length of the longest common substring
    // s1 and s2: input strings
    // i and j: current indices being compared in s1 and s2
    // count: length of current matching substring till this point
    // Time Complexity: O(3^min(n, m)) → exponential (due to three recursive branches)
    // Space Complexity: O(min(n, m)) → recursion stack depth in worst case
    private static int lcsRecursive(String s1, String s2, int i, int j, int count) {
        // 🔚 Base case: if either index is out of bounds (i.e., we’ve reached start of one string)
        // We return the current count because the substring (if any) ends here
        if (i < 0 || j < 0) return count;

        // Store current count in a temporary variable, may get updated if chars match
        int newCount = count;

        // ✅ If characters at current positions match, it means we can extend a common substring
        if (s1.charAt(i) == s2.charAt(j)) {
            // So, we add 1 to the count and move diagonally left-up (i-1, j-1)
            newCount = lcsRecursive(s1, s2, i - 1, j - 1, count + 1);
        }

        // ❌ If characters don't match, reset count to 0 and explore other options:
        // 1. Move left in s1 (i-1, j) — this ends the current substring
        int op1 = lcsRecursive(s1, s2, i - 1, j, 0);

        // 2. Move left in s2 (i, j-1) — this also ends the current substring
        int op2 = lcsRecursive(s1, s2, i, j - 1, 0);

        // 🟢 Return the maximum among:
        // - newCount (longest substring including current match),
        // - op1 and op2 (results from other paths where characters didn’t match)
        return Math.max(newCount, Math.max(op1, op2));
    }

    // 🧠 2. Memoization
    // Note: Since 'count' changes dynamically and we can't memoize it properly, we fallback to tabulation
    // Time Complexity: O(n * m)
    // Space Complexity: O(n * m)
    private static int lcsMemoization(String s1, String s2) {
        return lcsTabulation(s1, s2); // Reuse tabulation as effective memoization
    }

    // 📊 3. Tabulation (Bottom-Up DP for Longest Common Substring)
    // Time Complexity: O(n * m) → We compute each cell (i, j) once in the dp table
    // Space Complexity: O(n * m) → 2D DP array of size (n+1) x (m+1)
    private static int lcsTabulation(String string1, String string2) {
        int length1 = string1.length(); // Length of first string
        int length2 = string2.length(); // Length of second string

        // dp[i][j] represents the length of the longest common substring
        // ending at string1[i-1] and string2[j-1]
        int[][] dp = new int[length1 + 1][length2 + 1];

        // Variable to store the maximum length of any common substring found so far
        int maxLength = 0;

        // Build the dp table in bottom-up fashion
        for (int i = 1; i <= length1; i++) {
            for (int j = 1; j <= length2; j++) {

                // Fetch the current characters from both strings
                char char1 = string1.charAt(i - 1); // character at (i-1) index of string1
                char char2 = string2.charAt(j - 1); // character at (j-1) index of string2

                if (char1 == char2) {
                    // If characters match, increase the length from previous diagonal cell
                    dp[i][j] = 1 + dp[i - 1][j - 1];

                    // Update the global maximum length found so far
                    maxLength = Math.max(maxLength, dp[i][j]);
                } else {
                    // If characters don't match, no common substring ends here
                    // So we reset dp[i][j] to 0
                    dp[i][j] = 0;
                }
            }
        }
        // Return the length of the longest common substring
        return maxLength;
    }

    // 💾 4. Space Optimized Tabulation
    // Time Complexity: O(n * m)
    // Space Complexity: O(m) → Only 2 rows needed (previous and current)
    private static int lcsSpaceOptimized(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        int maxLen = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                    maxLen = Math.max(maxLen, curr[j]);
                } else {
                    curr[j] = 0;
                }
            }
            // Copy current row to previous row for next iteration
            prev = curr.clone();
        }

        return maxLen;
    }

    // ✅ Driver Code to Test All Approaches
    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "abfce";

        System.out.println("1. Recursive (Exponential): " + lcsRecursive(s1, s2, s1.length() - 1, s2.length() - 1, 0));
        System.out.println("2. Memoization (via Tabulation): " + lcsMemoization(s1, s2));
        System.out.println("3. Tabulation: " + lcsTabulation(s1, s2));
        System.out.println("4. Space Optimized: " + lcsSpaceOptimized(s1, s2));
    }
}

