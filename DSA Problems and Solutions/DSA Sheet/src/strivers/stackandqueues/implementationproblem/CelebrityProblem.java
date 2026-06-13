package strivers.stackandqueues.implementationproblem;

import java.util.*;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * The Celebrity Problem
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among
 * them, there may exist one celebrity. The definition of a celebrity is that all
 * the other n - 1 people know them, but they do not know any of them.
 * * You are given an integer n and a 2D array M (or an API knows(a, b)) where
 * M[i][j] = 1 means person i knows person j, and M[i][j] = 0 means they do not.
 * Find the celebrity's label. If there is no celebrity, return -1.
 * * Example 1:
 * Input: M = [[0, 1, 0],
 * [0, 0, 0],
 * [0, 1, 0]]
 * Output: 1
 * Explanation: 0 knows 1, 2 knows 1. 1 knows nobody. Therefore, 1 is the celebrity.
 * * Example 2:
 * Input: M = [[0, 1],
 * [1, 0]]
 * Output: -1
 * Explanation: Both 0 and 1 know each other. Therefore, there is no celebrity.
 * * CONSTRAINTS:
 * 2 <= n <= 3000
 * M[i][i] is always 0.
 * M[i][j] is either 0 or 1.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Two Pointers / Elimination (O(N) Time, O(1) Space)
 * Phase 2: Brute Force Approach - Graph Indegree/Outdegree (O(N^2) Time)
 * Phase 3: Alternative Approach - Stack-based Elimination (O(N) Time, O(N) Space)
 * ============================================================================
 */
public class CelebrityProblem {

    // Helper method to simulate the LeetCode knows(a, b) API using the matrix
    private boolean knows(int[][] M, int a, int b) {
        return M[a][b] == 1;
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Two Pointers / Elimination)
     * ============================================================================
     * Detailed Intuition:
     * We can find the celebrity in O(N) time using an elimination strategy.
     * If person A knows person B, A cannot be the celebrity.
     * If person A does NOT know person B, B cannot be the celebrity.
     * * By maintaining two pointers (start and end), we can eliminate one candidate
     * at every step.
     * 1. Place `top` at 0 and `down` at n-1.
     * 2. If `top` knows `down`, `top` is not a celebrity, so `top++`.
     * 3. Else, `down` is not a celebrity, so `down--`.
     * 4. When `top == down`, we have a single potential candidate.
     * 5. Finally, we must verify this candidate, as the elimination process
     * guarantees they are the *only* possible celebrity, but doesn't guarantee
     * they actually meet all conditions.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We do exactly N-1 comparisons to find the candidate,
     * and then 2N comparisons to verify them. Total time is strictly linear.
     * - Space Complexity: O(1) auxiliary space. Only two integer pointers are used.
     * ============================================================================
     */
    public int findCelebrityOptimal(int[][] M) {
        int n = M.length;
        int top = 0;
        int down = n - 1;

        // Step 1: Find the candidate
        while (top < down) {
            if (knows(M, top, down)) {
                top++; // top knows down, so top cannot be the celebrity
            } else {
                down--; // top doesn't know down, so down cannot be the celebrity
            }
        }

        int candidate = top;

        // Step 2: Verify the candidate
        for (int i = 0; i < n; i++) {
            if (i != candidate) {
                // A celebrity knows nobody, and everyone knows the celebrity
                if (knows(M, candidate, i) || !knows(M, i, candidate)) {
                    return -1;
                }
            }
        }

        return candidate;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Graph Indegree / Outdegree) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * We can model this problem as a directed graph. If person A knows person B,
     * there is a directed edge from A to B.
     * A celebrity is a sink node: they have an out-degree of 0 (knows nobody)
     * and an in-degree of N-1 (everyone knows them).
     * We simply iterate through the entire matrix, counting the in-degrees and
     * out-degrees for every person. Finally, we check if any person matches
     * the celebrity profile.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). We traverse the entire N x N matrix.
     * - Space Complexity: O(N) auxiliary space. We create two arrays of size N
     * to store the indegrees and outdegrees.
     * ============================================================================
     */
    public int findCelebrityBruteForce(int[][] M) {
        int n = M.length;
        int[] indegree = new int[n];
        int[] outdegree = new int[n];

        // Build the graph degrees
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 1 && i != j) {
                    outdegree[i]++;
                    indegree[j]++;
                }
            }
        }

        // Find the node with outdegree 0 and indegree N-1
        for (int i = 0; i < n; i++) {
            if (outdegree[i] == 0 && indegree[i] == n - 1) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Stack-based Elimination)
     * ============================================================================
     * Detailed Intuition:
     * This is a classic variation of the optimal logic using a Stack.
     * 1. Push all people (0 to n-1) onto a stack.
     * 2. Pop the top two people (A and B).
     * 3. If A knows B, A is discarded, and B is pushed back onto the stack.
     * 4. If A does not know B, B is discarded, and A is pushed back.
     * 5. Repeat until only one person remains in the stack. This is the candidate.
     * 6. Verify the candidate just like in the optimal approach.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Pushing all elements takes O(N). Each pop/evaluate/push
     * cycle eliminates one person, taking O(N) operations. Verification takes O(N).
     * - Space Complexity: O(N) auxiliary stack space for the Deque.
     * ============================================================================
     */
    public int findCelebrityStack(int[][] M) {
        int n = M.length;
        Deque<Integer> stack = new ArrayDeque<>();

        // Push all elements to the stack
        // Using Java 8 IntStream for concise initialization
        IntStream.range(0, n).forEach(stack::push);

        // Eliminate until one candidate remains
        while (stack.size() > 1) {
            int a = stack.pop();
            int b = stack.pop();

            if (knows(M, a, b)) {
                stack.push(b); // a knows b, so a is out
            } else {
                stack.push(a); // a does not know b, so b is out
            }
        }

        int candidate = stack.pop();

        // Verify the candidate
        for (int i = 0; i < n; i++) {
            if (i != candidate) {
                if (knows(M, candidate, i) || !knows(M, i, candidate)) {
                    return -1;
                }
            }
        }

        return candidate;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        CelebrityProblem solver = new CelebrityProblem();

        System.out.println("=== The Celebrity Problem Testing Suite ===\n");

        int[][][] testCases = {
                // Test Case 1: Standard case with a celebrity (1)
                {
                        {0, 1, 0},
                        {0, 0, 0},
                        {0, 1, 0}
                },
                // Test Case 2: No celebrity (both know each other)
                {
                        {0, 1},
                        {1, 0}
                },
                // Test Case 3: No celebrity (circle of knowing)
                {
                        {0, 1, 0},
                        {0, 0, 1},
                        {1, 0, 0}
                },
                // Test Case 4: Standard case with a celebrity (2)
                {
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 0}
                },
                // Test Case 5: Single element graph (technically a celebrity by default definition,
                // though constraints say N >= 2, we test structural robustness)
                {
                        {0}
                }
        };

        int[] expectedResults = {1, -1, -1, 2, 0};

        for (int i = 0; i < testCases.length; i++) {
            int[][] M = testCases[i];
            int expected = expectedResults[i];

            int optRes = solver.findCelebrityOptimal(M);
            int bfRes = solver.findCelebrityBruteForce(M);
            int stackRes = solver.findCelebrityStack(M);

            boolean passed = (optRes == expected) && (bfRes == expected) && (stackRes == expected);

            System.out.printf("Test Case %d:\n", i + 1);
            System.out.printf("  Expected: %d | Optimal: %d | Brute: %d | Stack: %d\n",
                    expected, optRes, bfRes, stackRes);
            System.out.printf("  Status:   %s\n\n", passed ? "PASS \u2714" : "FAIL \u2718");
        }
    }
}