package strivers.recursionbacktracking.medium;

/**
 * ==============================================================================================
 * 🤖 K-TH SYMBOL IN GRAMMAR (LeetCode 779)
 * ==============================================================================================
 *
 * PROBLEM STATEMENT:
 * ------------------
 * We build a table of n rows (1-indexed). We start by writing 0 in the 1st row.
 * Now in every subsequent row, we look at the previous row and replace each occurrence of
 * 0 with 01, and each occurrence of 1 with 10.
 *
 * Task:
 * Given two integers n (row number) and k (position in that row, 1-based index),
 * return the k-th symbol in row n.
 *
 * CONSTRAINTS:
 * ------------
 * - 1 <= n <= 30
 * - 1 <= k <= 2^(n-1)
 *
 * EXAMPLES:
 * ---------
 * Example 1:
 * Input: n = 1, k = 1
 * Output: 0
 * Explanation: Row 1: 0
 *
 * Example 2:
 * Input: n = 2, k = 1
 * Output: 0
 * Explanation:
 * Row 1: 0
 * Row 2: 01 (The 1st symbol is 0)
 *
 * Example 3:
 * Input: n = 4, k = 5
 * Output: 1
 * Explanation:
 * Row 1: 0
 * Row 2: 01
 * Row 3: 0110
 * Row 4: 01101001 (The 5th symbol is 1)
 *
 * CONCEPTUAL VISUALIZATION:
 * -------------------------
 * Notice the structural pattern of the strings being generated:
 * Row 1: [0]
 * Row 2: [0] [1]                   (Row 1 followed by its complement)
 * Row 3: [0 1] [1 0]               (Row 2 followed by its complement)
 * Row 4: [0 1 1 0] [1 0 0 1]       (Row 3 followed by its complement)
 *
 * This perfect mirroring means that the sequence is intimately tied to the binary
 * representation of the indices. Specifically, moving to the right half of any row
 * mathematically corresponds to a bit toggle (inverting the symbol).
 * ==============================================================================================
 */

public class KthSymbolGrammar {

    /**
     * ==========================================================================================
     * PHASE 1: OPTIMAL APPROACH (Bit Manipulation / Popcount)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * This is the absolute optimal way to solve the problem. If we convert k to a 0-based index
     * (i.e., k - 1), we can determine the answer solely based on its binary representation.
     * Every time a path to the k-th element branches into the "second half" (meaning it flips
     * the bit from its parent), it corresponds to a '1' bit in the binary representation of (k - 1).
     *
     * Therefore, the number of bit flips from the root (which is 0) is exactly the number of set
     * bits (1s) in `k - 1`.
     * - If the number of flips is even, the final bit remains 0.
     * - If the number of flips is odd, the final bit becomes 1.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) [or O(log k) strictly speaking, but Integer.bitCount is an intrinsic
     *   instruction that runs in O(1) CPU cycles].
     * - Space Complexity: O(1) auxiliary space. No stack, no arrays.
     */
    public static int kthGrammarOptimal(int n, int k) {
        // Find the number of set bits (1s) in the 0-based index (k - 1)
        int setBits = Integer.bitCount(k - 1);

        // If the number of set bits is even, it flipped an even number of times -> 0
        // If the number of set bits is odd, it flipped an odd number of times -> 1
        return setBits % 2 == 0 ? 0 : 1;
    }

    /**
     * ==========================================================================================
     * PHASE 2: BRUTE FORCE APPROACH (String Generation) - The "Think it" stage
     * ==========================================================================================
     *
     * Detailed Intuition:
     * The most straightforward but naive approach is to physically simulate the grammar generation.
     * We start with "0" and repeatedly replace '0' with "01" and '1' with "10" until we construct
     * the entire n-th row. Finally, we return the character at the (k-1)th index.
     *
     * NOTE: This will throw an OutOfMemoryError for n > ~25 because the string length grows
     * exponentially (2^(n-1)).
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^n). The length of the string doubles at each step up to 2^(n-1).
     * - Space Complexity: O(2^n) (Heap space). Storing the massive StringBuilder requires
     *   exponential memory.
     */
    public static int kthGrammarBruteForce(int n, int k) {
        StringBuilder current = new StringBuilder("0");

        // Generate rows iteratively
        for (int i = 1; i < n; i++) {
            StringBuilder next = new StringBuilder();
            for (int j = 0; j < current.length(); j++) {
                if (current.charAt(j) == '0') {
                    next.append("01");
                } else {
                    next.append("10");
                }
            }
            current = next;
        }

        // Return the k-th character (1-based index to 0-based)
        return current.charAt(k - 1) - '0';
    }

    /**
     * ==========================================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive Splitting) - The Provided Code
     * ==========================================================================================
     *
     * Detailed Intuition:
     * This is the beautifully structured recursive approach. As visualized above, row N is formed
     * by concatenating row N-1 with the complement of row N-1.
     *
     * We can mathematically deduce where the K-th element came from:
     * - We calculate the midpoint of row N, which is 2^(n-1) / 2.
     * - If K <= mid, the symbol is identical to the K-th symbol in row N-1.
     * - If K > mid, the symbol is the exact opposite of the (K - mid)th symbol in row N-1.
     * We recursively shrink the problem until we hit the base case of Row 1.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n). We make one recursive call per row, reducing n by 1 each time.
     * - Space Complexity: O(n) (Auxiliary Stack space). The maximum depth of the recursion tree is n.
     */
    public static int kthGrammarRecursive(int n, int k) {
        // Base case: first row, first symbol is always 0
        if (n == 1 && k == 1) {
            return 0;
        }

        // Length of row n = 2^(n-1), so mid is half of that
        int mid = (int) Math.pow(2, n - 1) / 2;

        if (k <= mid) {
            // If k is in the first half, same as Kth symbol in previous row
            return kthGrammarRecursive(n - 1, k);
        } else {
            // If k is in the second half, it's the opposite of (k-mid)th symbol in previous row
            return kthGrammarRecursive(n - 1, k - mid) == 0 ? 1 : 0;
        }
    }


    /**
     * ==========================================================================================
     * 4. TESTING SUITE
     * ==========================================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Starting Comprehensive Test Suite for K-th Symbol in Grammar...\n");

        int[][] testCases = {
                // {n, k, expected_output}
                {4, 5, 1},       // Standard case provided in prompt
                {1, 1, 0},       // Base case testing
                {2, 1, 0},       // Small edge case
                {2, 2, 1},       // Small edge case (second half)
                {3, 3, 1},       // Odd popcount
                {5, 12, 1},      // Larger simulation test
                {30, 432156, 0}  // Stress test (OOM risk for Brute Force)
        };

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i][0];
            int k = testCases[i][1];
            int expected = testCases[i][2];

            System.out.printf("Test Case %d: n = %d, k = %d (Expected: %d)%n", i + 1, n, k, expected);

            // Phase 1: Optimal
            int optimalRes = kthGrammarOptimal(n, k);
            System.out.printf("   Optimal Approach (Bitwise) -> %d [%s]%n",
                    optimalRes, (optimalRes == expected) ? "PASS" : "FAIL");

            // Phase 3: Recursive (Provided code)
            int recRes = kthGrammarRecursive(n, k);
            System.out.printf("   Recursive Approach         -> %d [%s]%n",
                    recRes, (recRes == expected) ? "PASS" : "FAIL");

            // Phase 2: Brute Force (Only run for small N to prevent OutOfMemoryError)
            if (n <= 20) {
                int bruteRes = kthGrammarBruteForce(n, k);
                System.out.printf("   Brute Force Generation     -> %d [%s]%n",
                        bruteRes, (bruteRes == expected) ? "PASS" : "FAIL");
            } else {
                System.out.printf("   Brute Force Generation     -> SKIPPED (N=%d is too large, would cause OOM)%n", n);
            }

            System.out.println("---------------------------------------------------------");
        }

        System.out.println("\n✅ All test cases executed.");
    }
}