package strivers.recursionbacktracking.medium.subsequences;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Count Subsequences with Target Sum
 *
 * Given an array of integers and a target sum, count the number of subsequences
 * whose elements sum up exactly to the given target.
 *
 * 🚨 EVALUATION OF PROVIDED APPROACH:
 * The code provided uses a "Pick / Don't Pick" recursive strategy, building a
 * running sum (`res`). This is a correct Brute Force approach. However, because
 * it recalculates overlapping subproblems without caching, it is NOT the optimal
 * approach and runs in O(2^n) time. It will cause a Time Limit Exceeded (TLE)
 * for array sizes > ~20.
 *
 * Per your instructions, YOUR ORIGINAL CODE REMAINS COMPLETELY UNCHANGED below
 * (marked as Phase 1). I have simply added the requested masterclass explanations,
 * recursion tree, and appended the optimal Dynamic Programming (DP) phases
 * further down in the class to complete the evaluation.
 *
 * Constraints:
 * - 1 <= arr.length <= 100
 * - 0 <= arr[i] <= 1000
 * - 0 <= target <= 1000
 *
 * Input/Output Formats:
 * - Input: An array of integers `arr` and an integer `target`.
 * - Output: An integer count of the valid subsequences.
 *
 * Examples:
 *
 * Example 1:
 * Input: arr = [1, 2, 1], sum = 2
 * Output: 2
 * Explanation: The subsequences that sum to 2 are [1, 1] and [2].
 *
 * ----------------------------------------------------------------------------
 * CONCEPTUAL VISUALIZATION (Recursion Tree for Provided Code)
 * ----------------------------------------------------------------------------
 * Example: arr = [1, 2, 1], sum = 2
 *
 * Recursion Tree: countSubSeq(index, res)
 *
 *                               cS(0, 0)
 *                              /        \
 *                   (Pick 1) /            \ (Skip 1)
 *                          /                \
 *                    cS(1, 1)              cS(1, 0)
 *                    /      \              /      \
 *         (Pick 2) /  (Skip) \   (Pick 2)/  (Skip) \
 *                /            \        /            \
 *           cS(2, 3)        cS(2, 1) cS(2, 2)       cS(2, 0)
 *             /  \            /  \     /  \           /  \
 *        (P1)/    \(S1)  (P1)/ \(S1)(P1)/ \(S1)  (P1)/    \(S1)
 *          /        \      /    \    /    \      /        \
 *      cS(3,4)   cS(3,3) cS(3,2) cS(3,1) cS(3,3) cS(3,2)  cS(3,1) cS(3,0)
 *        |         |       |      |      |       |        |       |
 *      Ret 0     Ret 0   Ret 1  Ret 0  Ret 0   Ret 1    Ret 0   Ret 0
 *
 * Total = 0+0+1+0+0+1+0+0 = 2
 *
 * DP Array Final State (For Phase 3 Tabulation):
 * Rows = Indices (0 to 2), Columns = Target (0 to 2)
 *     0  1  2
 * 0 [ 1, 1, 0 ]
 * 1 [ 1, 1, 1 ]
 * 2 [ 1, 2, 2 ]  <- Final answer at bottom-right
 *
 * ============================================================================
 * 2.1. PROGRESSIVE IMPLEMENTATION ROADMAP (DP Problem)
 * ============================================================================
 */
public class CountSubsequences {

    public static void main(String[] args) {
        int arr[] = {1, 2, 1};
        int sum = 2;
        // Prints the number of subsequences that sum to target
        System.out.println(countSubSeq(0, arr, arr.length, sum, 0));
    }

    /**
     * ============================================================================
     * PHASE 1: BRUTE FORCE RECURSION (Your Original Code)
     * ============================================================================
     *
     * Detailed Intuition:
     * We use a decision tree approach. At every index `i`, we make a choice:
     * 1. Include `arr[i]` in our subsequence (add it to `res`).
     * 2. Exclude `arr[i]` from our subsequence (keep `res` as is).
     * We push these choices down the call stack until we reach the end of the
     * array (`index == n`). Once at the base case, we check if the accumulated
     * sum `res` equals the `target sum`. If it does, we return 1 (representing
     * one valid path/subsequence found), otherwise 0.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^n) - For every element, we make exactly 2 recursive
     *   calls. A tree of depth `n` has 2^n leaf nodes.
     * - Space Complexity: O(n) - Auxiliary stack space used by the recursion
     *   depth. No extra heap space is allocated.
     */
    private static int countSubSeq(int index, int arr[], int n, int sum, int res) {
        // Base case: If we have reached the end of the array
        if (index == n) {
            // If current sum matches target, count this subsequence
            if (res == sum) {
                return 1;
            } else {
                return 0;
            }
        }

        // Include current element in sum
        res = res + arr[index];
        int left = countSubSeq(index + 1, arr, n, sum, res);

        // Backtrack: remove current element and move ahead
        res = res - arr[index];
        int right = countSubSeq(index + 1, arr, n, sum, res);

        // Total count = subsequences including current element + excluding it
        return left + right;
    }

    /**
     * ============================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (Optimal Refinement)
     * ============================================================================
     *
     * Detailed Intuition:
     * To optimize the O(2^n) time complexity, we must cache the results of our
     * recursive calls. The state in your logic is defined by `(index, res)`.
     * If we traverse backwards (from n-1 to 0) and reduce the `target` instead of
     * increasing `res`, it bounds our DP array size strictly to `[n][sum+1]`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * sum) - Each state is computed exactly once.
     * - Space Complexity: O(n * sum) Heap Space for DP table + O(n) Call Stack Space.
     */
    public static int countMemoization(int index, int[] arr, int target, int[][] dp) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        if (dp[index][target] != -1) return dp[index][target];

        int notPick = countMemoization(index - 1, arr, target, dp);
        int pick = 0;
        if (arr[index] <= target) {
            pick = countMemoization(index - 1, arr, target - arr[index], dp);
        }

        return dp[index][target] = pick + notPick;
    }

    /**
     * ============================================================================
     * PHASE 3: BOTTOM-UP TABULATION
     * ============================================================================
     *
     * EXACT DEFAULT STATE AFTER BASE CASE INITIALIZATION:
     *     0  1  2
     * 0 [ 1, 1, 0 ]   <-- dp[0] populated based on arr[0]
     * 1 [ 0, 0, 0 ]
     * 2 [ 0, 0, 0 ]
     *
     * Detailed Intuition:
     * We convert recursion into an iterative table-filling process to eliminate
     * the call stack entirely. We fill the matrix strictly following the
     * transition logic: dp[i][t] = dp[i-1][t] + dp[i-1][t - arr[i]].
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * sum)
     * - Space Complexity: O(n * sum) Heap Space for the 2D array. O(1) Aux Space.
     */
    public static int countTabulation(int[] arr, int sum) {
        int n = arr.length;
        int[][] dp = new int[n][sum + 1];

        if (arr[0] == 0) dp[0][0] = 2;
        else dp[0][0] = 1;

        if (arr[0] != 0 && arr[0] <= sum) dp[0][arr[0]] = 1;

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= sum; t++) {
                int notPick = dp[i - 1][t];
                int pick = 0;
                if (arr[i] <= t) pick = dp[i - 1][t - arr[i]];

                dp[i][t] = pick + notPick;
            }
        }
        return dp[n - 1][sum];
    }

    /**
     * ============================================================================
     * PHASE 4: SPACE OPTIMIZATION
     * ============================================================================
     *
     * Detailed Intuition:
     * Notice that computing `dp[i][t]` only ever requires the row immediately
     * above it (`dp[i-1]`). We can discard the rest of the 2D matrix and maintain
     * just a single 1D array (`prev`) that we update row by row.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * sum)
     * - Space Complexity: O(sum) Heap Space. This is the optimal space solution.
     */
    public static int countSpaceOptimized(int[] arr, int sum) {
        int n = arr.length;
        int[] prev = new int[sum + 1];

        if (arr[0] == 0) prev[0] = 2;
        else prev[0] = 1;

        if (arr[0] != 0 && arr[0] <= sum) prev[arr[0]] = 1;

        for (int i = 1; i < n; i++) {
            int[] curr = new int[sum + 1];
            for (int t = 0; t <= sum; t++) {
                int notPick = prev[t];
                int pick = 0;
                if (arr[i] <= t) pick = prev[t - arr[i]];

                curr[t] = pick + notPick;
            }
            prev = curr;
        }
        return prev[sum];
    }
}