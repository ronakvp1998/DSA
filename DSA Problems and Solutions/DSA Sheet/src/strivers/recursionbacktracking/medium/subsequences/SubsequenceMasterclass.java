package strivers.recursionbacktracking.medium.subsequences;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Subsequence Variations (Generate & Count)
 *
 * Given an array of integers and a target sum K, perform the following tasks:
 * 1. Generate and return ALL subsequences.
 * 2. Generate and return ALL subsequences whose elements sum to K.
 * 3. Find and return ANY ONE subsequence whose elements sum to K.
 * 4. Count the total number of subsequences whose elements sum to K.
 *
 * 🚨 EVALUATION OF PROVIDED CODE:
 * The code you provided correctly utilizes the "Pick / Don't Pick" recursive
 * backtracking pattern.
 * - For tasks 1, 2, and 3 (Generation/Printing), Backtracking is mathematically
 *   the optimal approach since you must physically traverse and build the paths.
 * - However, for task 4 (Counting), the provided recursive approach is a
 *   Brute Force O(2^n) solution. It calculates overlapping subproblems repeatedly.
 *   Furthermore, the `count` parameter in `subsequences4` is redundant (it acts
 *   as a local 0 rather than a global accumulator).
 *   Task 4 can be highly optimized to O(n * K) using Dynamic Programming (DP).
 *
 * Constraints:
 * - 1 <= arr.length <= 100 (For DP Counting. For generation, length <= 20 to avoid TLE).
 * - 0 <= arr[i] <= 1000 (Non-negative is standard for DP array indexing).
 * - 0 <= K <= 1000
 *
 * Input/Output Formats:
 * - Input: An integer array `arr` and target `K`.
 * - Output: Respective lists of subsequences, or an integer for the count.
 *
 * Examples:
 *
 * Example 1:
 * Input: arr = [1, 2, 1], K = 2
 * Output:
 *   All Sum K: [[1, 1], [2]]
 *   Any One Sum K: [1, 1]
 *   Count Sum K: 2
 *
 * ----------------------------------------------------------------------------
 * CONCEPTUAL VISUALIZATION (Recursion Tree & DP Array for Count Variation)
 * ----------------------------------------------------------------------------
 * Example: arr = [1, 2, 1], K = 2
 *
 * Recursion Tree (Count Sum K): f(index, remaining_target)
 *
 *                                f(2, 2)
 *                              /         \
 *               (Pick 1)     /             \   (Skip 1)
 *                          /                 \
 *                    f(1, 1)                 f(1, 2)
 *                    /     \                 /     \
 *         (Pick 2) / (Skip) \      (Pick 2)/ (Skip) \
 *                /           \           /           \
 *           f(0, -1)       f(0, 1)    f(0, 0)       f(0, 2)
 *             |              |          |             |
 *           Ret 0          Ret 1      Ret 1         Ret 0
 *          (Exceeds)     (arr[0]=1) (target=0)     (No Match)
 *
 * Total = 0 + 1 + 1 + 0 = 2
 *
 * DP Array Final State (Rows = Index 0 to 2, Columns = Target 0 to 2):
 *     0  1  2
 * 0 [ 1, 1, 0 ]  <- Base cases for index 0
 * 1 [ 1, 1, 1 ]
 * 2 [ 1, 2, 2 ]  <- Final answer at bottom-right (dp[2][2])
 *
 * ============================================================================
 * 2.1. PROGRESSIVE IMPLEMENTATION ROADMAP (DP Problem for Variation 4)
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SubsequenceMasterclass {

    /**
     * ============================================================================
     * PHASE 1: BRUTE FORCE RECURSION (Backtracking for Generation & Counting)
     * ============================================================================
     *
     * Detailed Intuition:
     * This phase directly adapts your provided logic. We use the "Pick/Don't Pick"
     * pattern. We traverse index 0 to N.
     * - Task 1 & 2: Push elements to a temporary list, add deep copies to result.
     * - Task 3: Return boolean `true` to immediately collapse the recursion tree
     *   once the first valid sequence is found.
     * - Task 4: Return 1 if valid, 0 if not, and sum the left and right branches.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^n * n) for generation tasks, O(2^n) for counting.
     * - Space Complexity: O(n) Auxiliary Stack Space + O(2^n * n) Heap Space for outputs.
     */

    // 1. Generate ALL Subsequences
    public static void generateAll(int index, int[] arr, List<Integer> ds, List<List<Integer>> ans) {
        if (index == arr.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        // Pick
        ds.add(arr[index]);
        generateAll(index + 1, arr, ds, ans);
        // Backtrack
        ds.remove(ds.size() - 1);
        // Don't Pick
        generateAll(index + 1, arr, ds, ans);
    }

    // 2. Generate ALL Subsequences with Sum K
    public static void generateAllSumK(int index, int[] arr, int sum, int k, List<Integer> ds, List<List<Integer>> ans) {
        if (index == arr.length) {
            if (sum == k) ans.add(new ArrayList<>(ds));
            return;
        }
        // Pick
        ds.add(arr[index]);
        generateAllSumK(index + 1, arr, sum + arr[index], k, ds, ans);
        // Backtrack
        ds.remove(ds.size() - 1);
        // Don't Pick
        generateAllSumK(index + 1, arr, sum, k, ds, ans);
    }

    // 3. Generate ANY ONE Subsequence with Sum K
    public static boolean generateOneSumK(int index, int[] arr, int sum, int k, List<Integer> ds, List<Integer> ans) {
        if (index == arr.length) {
            if (sum == k) {
                ans.addAll(ds); // Store the found subsequence
                return true;    // Signal to stop further recursion
            }
            return false;
        }
        // Pick
        ds.add(arr[index]);
        if (generateOneSumK(index + 1, arr, sum + arr[index], k, ds, ans)) return true;
        // Backtrack
        ds.remove(ds.size() - 1);
        // Don't Pick
        if (generateOneSumK(index + 1, arr, sum, k, ds, ans)) return true;

        return false;
    }

    // 4. Count Subsequences with Sum K (Your provided logic, cleaned up)
    public static int countBruteForce(int index, int[] arr, int sum, int k) {
        if (index == arr.length) {
            return (sum == k) ? 1 : 0;
        }
        int pick = countBruteForce(index + 1, arr, sum + arr[index], k);
        int notPick = countBruteForce(index + 1, arr, sum, k);
        return pick + notPick;
    }


    /**
     * ============================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (Optimizing Task 4: Count)
     * ============================================================================
     *
     * Detailed Intuition:
     * To memoize, it is much easier to iterate backwards (n-1 to 0) and subtract
     * from the target. The state is strictly defined by `(index, remaining_target)`.
     * We cache these results in a `dp[n][target + 1]` array to prevent recomputing
     * overlapping states.
     *
     * 🚨 Zeros Trap: If `arr[0] == 0`, picking it leaves target at 0, skipping it
     * leaves target at 0. Both are valid distinct subsequences, so return 2, not 1!
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * k) - Each state is computed exactly once.
     * - Space Complexity: O(n * k) Heap Space + O(n) Aux Stack Space.
     */
    public static int countMemoization(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return solveMemo(n - 1, target, arr, dp);
    }

    private static int solveMemo(int index, int target, int[] arr, int[][] dp) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }
        if (dp[index][target] != -1) return dp[index][target];

        int notPick = solveMemo(index - 1, target, arr, dp);
        int pick = 0;
        if (arr[index] <= target) {
            pick = solveMemo(index - 1, target - arr[index], arr, dp);
        }
        return dp[index][target] = pick + notPick;
    }


    /**
     * ============================================================================
     * PHASE 3: BOTTOM-UP TABULATION
     * ============================================================================
     *
     * EXACT DEFAULT STATE AFTER BASE CASE INITIALIZATION:
     * (For arr = [1, 2, 1], K = 2)
     *     0  1  2
     * 0 [ 1, 1, 0 ]   <-- dp[0] populated!
     * 1 [ 0, 0, 0 ]
     * 2 [ 0, 0, 0 ]
     *
     * Detailed Intuition:
     * We convert the recursion stack into iterative table population.
     * `dp[i][t]` stores the number of valid subsequences using elements up to
     * index `i` that sum to `t`.
     * Transition: dp[i][t] = dp[i-1][t] + (if t >= arr[i]) dp[i-1][t-arr[i]]
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * k) - Two nested loops.
     * - Space Complexity: O(n * k) Heap Space for the 2D array. O(1) Aux Space.
     */
    public static int countTabulation(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Base cases
        if (arr[0] == 0) dp[0][0] = 2;
        else dp[0][0] = 1;

        if (arr[0] != 0 && arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

        // Fill table
        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {
                int notPick = dp[i - 1][t];
                int pick = 0;
                if (arr[i] <= t) pick = dp[i - 1][t - arr[i]];

                dp[i][t] = pick + notPick;
            }
        }
        return dp[n - 1][target];
    }


    /**
     * ============================================================================
     * PHASE 4: SPACE OPTIMIZATION
     * ============================================================================
     *
     * Detailed Intuition:
     * Notice in Tabulation that computing `dp[i][t]` only ever requires the row
     * immediately above it (`dp[i-1]`). We can discard the rest of the 2D matrix
     * and maintain just two 1D arrays: `prev` and `curr`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * k)
     * - Space Complexity: O(k) Heap Space. This is the ultimate optimization.
     */
    public static int countSpaceOptimized(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base cases
        if (arr[0] == 0) prev[0] = 2;
        else prev[0] = 1;

        if (arr[0] != 0 && arr[0] <= target) prev[arr[0]] = 1;

        // Iteration
        for (int i = 1; i < n; i++) {
            int[] curr = new int[target + 1];
            for (int t = 0; t <= target; t++) {
                int notPick = prev[t];
                int pick = 0;
                if (arr[i] <= t) pick = prev[t - arr[i]];

                curr[t] = pick + notPick;
            }
            prev = curr;
        }
        return prev[target];
    }


    /**
     * ============================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Bitmasking for Generate All)
     * ============================================================================
     *
     * Detailed Intuition:
     * For generating subsequences, instead of recursion, we can use Bitmasking.
     * A number from 0 to (2^n - 1) has a binary representation that perfectly maps
     * to a subsequence (1 = pick, 0 = don't pick).
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * 2^n)
     * - Space Complexity: O(1) Aux Space (excluding output list).
     */
    public static List<List<Integer>> generateAllBitmask(int[] arr) {
        int n = arr.length;
        int total = 1 << n; // 2^n
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            List<Integer> sub = new ArrayList<>();
            for (int bit = 0; bit < n; bit++) {
                if ((i & (1 << bit)) != 0) {
                    sub.add(arr[bit]);
                }
            }
            ans.add(sub);
        }
        return ans;
    }


    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Masterclass Test Suite: Subsequences\n");

        int[] arr = {1, 2, 1};
        int k = 2;

        System.out.println("Array: " + Arrays.toString(arr) + " | Target K: " + k + "\n");

        // 1. Generate All
        List<List<Integer>> allSub = new ArrayList<>();
        generateAll(0, arr, new ArrayList<>(), allSub);
        System.out.println("1. All Subsequences (Backtracking) : " + formatList(allSub));

        // 2. Generate All Sum K
        List<List<Integer>> allSumK = new ArrayList<>();
        generateAllSumK(0, arr, 0, k, new ArrayList<>(), allSumK);
        System.out.println("2. All Subsequences Sum = " + k + "      : " + formatList(allSumK));

        // 3. Generate One Sum K
        List<Integer> oneSumK = new ArrayList<>();
        generateOneSumK(0, arr, 0, k, new ArrayList<>(), oneSumK);
        System.out.println("3. Any One Subsequence Sum = " + k + "   : " + oneSumK);

        // 4. Count Sum K (Validating all phases)
        System.out.println("\n--- Task 4: Count Subsequences DP Validation ---");
        int countBrute = countBruteForce(0, arr, 0, k);
        int countMemo  = countMemoization(arr, k);
        int countTab   = countTabulation(arr, k);
        int countOpt   = countSpaceOptimized(arr, k);

        System.out.println("Phase 1: Brute Force      -> " + countBrute);
        System.out.println("Phase 2: Memoization      -> " + countMemo);
        System.out.println("Phase 3: Tabulation       -> " + countTab);
        System.out.println("Phase 4: Space Optimized  -> " + countOpt);

        boolean passed = (countBrute == 2 && countMemo == 2 && countTab == 2 && countOpt == 2);
        System.out.println("DP Status: " + (passed ? "✅ PASS" : "❌ FAIL"));
    }

    private static String formatList(List<List<Integer>> result) {
        return result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}