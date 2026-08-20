package strivers.recursionbacktracking.medium;

/**
 * # 1922. Count Good Numbers
 *
 * ## 1. Header & Problem Context
 * A digit string is good if the digits (0-indexed) at even indices are even
 * and the digits at odd indices are prime (2, 3, 5, or 7).
 *
 * For example, "2582" is good because the digits (2 and 8) at even positions
 * are even and the digits (5 and 2) at odd positions are prime. However, "3245"
 * is not good because 3 is at an even index but is not even.
 *
 * Given an integer n, return the total number of good digit strings of length n.
 * Since the answer may be large, return it modulo 10^9 + 7.
 *
 * A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.
 *
 * **Examples:**
 * - Input: n = 1 | Output: 5
 *   Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
 * - Input: n = 4 | Output: 400
 * - Input: n = 50 | Output: 564908303
 *
 * **Constraints:**
 * - $1 \le n \le 10^{15}$
 *
 * ---
 *
 * ## Conceptual Visualization (Combinatorics)
 * We have 5 choices for even indices (0, 2, 4, 6, 8).
 * We have 4 choices for odd indices (2, 3, 5, 7).
 *
 * If length is n:
 * Number of even indices = (n + 1) / 2
 * Number of odd indices = n / 2
 *
 * Total permutations = $(5^{\text{even\_indices}} \times 4^{\text{odd\_indices}}) \pmod{10^9 + 7}$
 */
import java.math.BigInteger;
import java.util.Arrays;

public class CountGoodNumbers {

    private static final long MOD = 1_000_000_007;

    /**
     * ## Phase 1: Optimal Approach - Fast Exponentiation
     *
     * **Detailed Intuition:**
     * Because $n$ can be up to $10^{15}$, calculating the power linearly will result
     * in a Time Limit Exceeded (TLE) error. We must use Binary Exponentiation
     * (also known as Fast Exponentiation) to calculate $x^y \pmod m$ in logarithmic time.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(\log n)$ because we halve the power at each step.
     * - **Space Complexity:** $O(1)$ auxiliary space since we are using an iterative approach.
     */
    public int countGoodNumbersOptimal(long n) {
        long evenPositions = (n + 1) / 2;
        long oddPositions = n / 2;

        long evenChoicesTotal = binaryExp(5, evenPositions);
        long oddChoicesTotal = binaryExp(4, oddPositions);

        return (int) ((evenChoicesTotal * oddChoicesTotal) % MOD);
    }

    private long binaryExp(long base, long power) {
        long result = 1;
        base = base % MOD;

        while (power > 0) {
            if ((power & 1) == 1) { // If power is odd
                result = (result * base) % MOD;
            }
            base = (base * base) % MOD; // Square the base
            power >>= 1;                // Divide power by 2
        }
        return result;
    }

    /**
     * ## Phase 2: Brute Force Approach - Linear Multiplication
     *
     * **Detailed Intuition:**
     * The most basic way to compute this is to iterate $n$ times. For every even index,
     * multiply the running total by 5. For every odd index, multiply by 4.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(n)$, which will fail for $n = 10^{15}$.
     * - **Space Complexity:** $O(1)$ heap and stack space.
     */
    public int countGoodNumbersBruteForce(long n) {
        long total = 1;
        for (long i = 0; i < n; i++) {
            if (i % 2 == 0) {
                total = (total * 5) % MOD;
            } else {
                total = (total * 4) % MOD;
            }
        }
        return (int) total;
    }

    /**
     * ## Phase 3: Alternative Approach - BigInteger built-in
     *
     * **Detailed Intuition:**
     * Java's `BigInteger` class has a highly optimized `modPow` method. While
     * interviewers usually want to see manual Binary Exponentiation, this is the
     * cleanest and most robust way to handle it in production Java code.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(\log n)$ underlying implementation.
     * - **Space Complexity:** $O(1)$ aside from object creation overhead.
     */
    public int countGoodNumbersBigInteger(long n) {
        long evenPositions = (n + 1) / 2;
        long oddPositions = n / 2;

        BigInteger mod = BigInteger.valueOf(MOD);
        BigInteger evens = BigInteger.valueOf(5).modPow(BigInteger.valueOf(evenPositions), mod);
        BigInteger odds = BigInteger.valueOf(4).modPow(BigInteger.valueOf(oddPositions), mod);

        return evens.multiply(odds).mod(mod).intValue();
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        CountGoodNumbers solver = new CountGoodNumbers();

        // Test Cases: { n, expectedOutput }
        long[][] testCases = {
                {1, 5},
                {4, 400},
                {50, 564908303}
        };

        System.out.println("--- Running Tests ---");

        // Utilizing Java 8 Streams to process tests
        Arrays.stream(testCases).forEach(test -> {
            long n = test[0];
            long expected = test[1];

            System.out.println("Testing n = " + n);
            System.out.println("Optimal: " + (solver.countGoodNumbersOptimal(n) == expected ? "PASS" : "FAIL"));
            System.out.println("BigInteger: " + (solver.countGoodNumbersBigInteger(n) == expected ? "PASS" : "FAIL"));

            if (n <= 50) { // Brute force will hang on massive numbers
                System.out.println("BruteForce: " + (solver.countGoodNumbersBruteForce(n) == expected ? "PASS" : "FAIL"));
            }
            System.out.println("---------------------");
        });
    }
}