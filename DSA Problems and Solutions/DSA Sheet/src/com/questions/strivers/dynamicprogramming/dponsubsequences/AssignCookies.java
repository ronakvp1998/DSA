package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://leetcode.com/problems/assign-cookies/description/
//455. Assign Cookies
//        Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie.
//        Each child i has a greed factor g[i], which is the minimum size of a cookie that the child will be content with; and each cookie j has a size s[j]. If s[j] >= g[i], we can assign the cookie j to the child i, and the child i will be content. Your goal is to maximize the number of your content children and output the maximum number.
//        Example 1:
//
//        Input: g = [1,2,3], s = [1,1]
//        Output: 1
//        Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
//        And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
//        You need to output 1.
//        Example 2:
//
//        Input: g = [1,2], s = [1,2,3]
//        Output: 2
//        Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2.
//        You have 3 cookies and their sizes are big enough to gratify all of the children,
//        You need to output 2.
//
//        Constraints:
//        1 <= g.length <= 3 * 104
//        0 <= s.length <= 3 * 104
//        1 <= g[i], s[j] <= 231 - 1
// not tested
import java.util.Arrays;
public class AssignCookies {

    // Recursive Approach (TLE for large input)
    public static int recursive(int[] g, int[] s, int i, int j) {
        // Base case: If all children or all cookies are processed
        if (i == g.length || j == s.length) return 0;

        // Option 1: If cookie j can satisfy child i
        if (s[j] >= g[i]) {
            // Either assign or skip
            return Math.max(
                    1 + recursive(g, s, i + 1, j + 1), // assign
                    recursive(g, s, i, j + 1)          // skip this cookie
            );
        } else {
            // Cannot assign -> skip this cookie
            return recursive(g, s, i, j + 1);
        }
    }

    // Memoization Approach (Top-down DP)
    public static int memo(int[] g, int[] s, int i, int j, int[][] dp) {
        // Base case
        if (i == g.length || j == s.length) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (s[j] >= g[i]) {
            // Try both assigning and skipping
            dp[i][j] = Math.max(
                    1 + memo(g, s, i + 1, j + 1, dp),
                    memo(g, s, i, j + 1, dp)
            );
        } else {
            dp[i][j] = memo(g, s, i, j + 1, dp);
        }

        return dp[i][j];
    }

    // Tabulation (Bottom-up DP)
    public static int tabulation(int[] g, int[] s) {
        int n = g.length;
        int m = s.length;

        int[][] dp = new int[n + 1][m + 1];

        // Fill from bottom up
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (s[j] >= g[i]) {
                    dp[i][j] = Math.max(1 + dp[i + 1][j + 1], dp[i][j + 1]);
                } else {
                    dp[i][j] = dp[i][j + 1];
                }
            }
        }

        return dp[0][0];
    }

    // Space Optimization
    public static int spaceOptimized(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0; // pointer for child
        int j = 0; // pointer for cookies
        int count = 0;

        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                count++;
                i++;
                j++;
            } else {
                j++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[] g = {1, 2, 3};
        int[] s = {1, 1};

        Arrays.sort(g);
        Arrays.sort(s);

        // Recursive (may be slow for large inputs)
        System.out.println("Recursive: " + recursive(g, s, 0, 0));

        // Memoization
        int[][] dp = new int[g.length + 1][s.length + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("Memoization: " + memo(g, s, 0, 0, dp));

        // Tabulation
        System.out.println("Tabulation: " + tabulation(g, s));

        // Space Optimized Greedy
        System.out.println("Space Optimized (Greedy): " + spaceOptimized(g, s));
    }
}
