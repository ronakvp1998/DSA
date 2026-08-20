package strivers.recursionbacktracking.medium;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * # 50. Pow(x, n)
 *
 * ## 1. Header & Problem Context
 * Implement pow(x, n), which calculates x raised to the power n (x^n).
 *
 * **Examples:**
 * - Input: x = 2.00000, n = 10
 *   Output: 1024.00000
 * - Input: x = 2.10000, n = 3
 *   Output: 9.26100
 * - Input: x = 2.00000, n = -2
 *   Output: 0.25000 (Explanation: 2^-2 = 1 / (2^2) = 0.25)
 *
 * **Constraints:**
 * - -100.0 < x < 100.0
 * - -2^31 <= n <= 2^31 - 1
 * - n is an integer.
 * - Either x != 0 or n > 0.
 * - -10^4 <= x^n <= 10^4
 *
 * ---
 *
 * ## Conceptual Visualization (Binary Exponentiation)
 * Instead of multiplying x by itself n times, we can break down the power.
 * Let's visualize calculating 2.0 ^ 10:
 *
 * Step 1: 10 is even -> (2.0 ^ 2) ^ 5 -> 4.0 ^ 5
 * Step 2: 5 is odd   -> 4.0 * (4.0 ^ 4)
 * Step 3: 4 is even  -> 4.0 * ((4.0 ^ 2) ^ 2) -> 4.0 * (16.0 ^ 2)
 * Step 4: 2 is even  -> 4.0 * ((16.0 ^ 2) ^ 1) -> 4.0 * (256.0 ^ 1)
 * Step 5: 1 is odd   -> 4.0 * 256.0 * (256.0 ^ 0)
 * Step 6: Power is 0 -> Result = 1024.0
 */
public class PowXN {

    /**
     * ## Phase 1: Optimal Approach - Iterative Binary Exponentiation
     *
     * **Detailed Intuition:**
     * The linear approach takes too long for large values of n (up to 2^31 - 1).
     * We can optimize this by squaring the base and halving the exponent at each step.
     * If the current exponent is odd, we multiply our running result by the base.
     * A crucial edge case is when n = -2^31. If we simply do n = -n, it will overflow
     * the 32-bit integer limit. To prevent this, we must cast n to a long first.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(log n) because we are dividing the power by 2 at each iteration.
     * - **Space Complexity:** O(1) auxiliary space, as we only use a few primitive variables.
     */
    public double myPowOptimal(double x, int n) {
        double result = 1.0;
        long power = n;

        // Handle negative powers
        if (power < 0) {
            power = -power;
        }

        while (power > 0) {
            // If the current power is odd, multiply the current x into the result
            if ((power & 1) == 1) {
                result *= x;
            }
            // Square the base and halve the power
            x *= x;
            power >>= 1;
        }

        // If the original n was negative, the answer is 1 / result
        return n < 0 ? 1.0 / result : result;
    }

    /**
     * ## Phase 2: Brute Force Approach - Linear Multiplication
     *
     * **Detailed Intuition:**
     * The most intuitive way to calculate x^n is to set a result to 1 and multiply it
     * by x exactly |n| times. If n is negative, we invert the result at the end.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(n), which results in Time Limit Exceeded (TLE) when n approaches 2^31.
     * - **Space Complexity:** O(1) auxiliary space.
     */
    public double myPowBruteForce(double x, int n) {
        // Handle n = 0 edge case quickly
        if (n == 0) return 1.0;

        double result = 1.0;
        long power = Math.abs((long) n);

        for (long i = 0; i < power; i++) {
            result *= x;
        }

        return n < 0 ? 1.0 / result : result;
    }

    /**
     * ## Phase 3: Alternative Approach - Recursive Binary Exponentiation
     *
     * **Detailed Intuition:**
     * We can express the binary exponentiation logic recursively.
     * Base case: if n == 0, return 1.
     * If n is even, pow(x, n) = pow(x*x, n/2).
     * If n is odd, pow(x, n) = x * pow(x, n-1).
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(log n) because we halve the exponent in recursive calls.
     * - **Space Complexity:** O(log n) auxiliary stack space used by the recursion tree.
     */
    public double myPowRecursive(double x, int n) {
        long power = n;
        if (power < 0) {
            power = -power;
            x = 1 / x; // Invert x upfront for recursive simplicity
        }
        return recursiveHelper(x, power);
    }

    private double recursiveHelper(double x, long n) {
        if (n == 0) return 1.0;

        double halfPow = recursiveHelper(x, n / 2);

        if (n % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * x;
        }
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        PowXN solver = new PowXN();

        // Custom wrapper for tests: {x, n, expectedOutput}
        class TestCase {
            double x; int n; double expected;
            TestCase(double x, int n, double expected) {
                this.x = x; this.n = n; this.expected = expected;
            }
        }

        TestCase[] tests = {
                new TestCase(2.0, 10, 1024.0),
                new TestCase(2.1, 3, 9.261),
                new TestCase(2.0, -2, 0.25),
                new TestCase(2.0, 0, 1.0),                  // Edge case: zero power
                new TestCase(1.0, 2147483647, 1.0),         // Edge case: max integer
                new TestCase(2.0, -2147483648, 0.0)         // Edge case: min integer (overflow trap)
        };

        System.out.println("--- Running Pow(x, n) Tests ---");

        // Utilizing Java 8 Stream API for clean test execution
        Stream.of(tests).forEach(test -> {
            System.out.println("Testing x = " + test.x + ", n = " + test.n);

            double optRes = solver.myPowOptimal(test.x, test.n);
            double recRes = solver.myPowRecursive(test.x, test.n);

            // Using an epsilon for floating point comparison
            double epsilon = 1e-5;
            boolean optPass = Math.abs(optRes - test.expected) < epsilon;
            boolean recPass = Math.abs(recRes - test.expected) < epsilon;

            System.out.printf("Optimal:   [%.5f] -> %s\n", optRes, (optPass ? "PASS" : "FAIL"));
            System.out.printf("Recursive: [%.5f] -> %s\n", recRes, (recPass ? "PASS" : "FAIL"));

            // Skip brute force for massive bounds to prevent hang
            if (Math.abs((long)test.n) < 1000000) {
                double bfRes = solver.myPowBruteForce(test.x, test.n);
                boolean bfPass = Math.abs(bfRes - test.expected) < epsilon;
                System.out.printf("BruteForce:[%.5f] -> %s\n", bfRes, (bfPass ? "PASS" : "FAIL"));
            } else {
                System.out.println("BruteForce: SKIPPED (To prevent TLE)");
            }
            System.out.println("-------------------------------");
        });
    }
}