package strivers.recursionbacktracking.hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * # Strictly Increasing N-Digit Numbers
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Generate all N-digit numbers where the digits are strictly increasing from left to right.
 * The generated numbers should be returned or printed in lexicographically increasing order.
 * Note that numbers cannot have leading zeros (since a strictly increasing number starting
 * with 0 would require the next digit to be at least 1, but typical interpretations of
 * N-digit combinations from 1-9 restrict the starting digit to 1).
 *
 * **Constraints:**
 * - 1 <= n <= 9 (Since there are only 9 positive digits (1-9), we cannot form a strictly
 *   increasing number with more than 9 digits).
 *
 * **Examples:**
 * - Input: n = 1
 *   Output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
 *
 * - Input: n = 2
 *   Output: [12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 34, ..., 89]
 *
 * - Input: n = 9
 *   Output: [123456789]
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Mathematical integer generation (Avoids String overhead).
 * * **Phase 2: Brute Force Approach** - The provided String-concatenation backtracking method.
 * * **Phase 3: Alternative Approach** - Bitmasking Combinatorics (Iterating all subsets of 1-9).
 */
public class IncreasingNDigitNumbers {

    /**
     * ## Phase 1: Optimal Approach - Integer Math Backtracking
     *
     * **Detailed Intuition:**
     * The code you provided works perfectly, but string concatenation inside a recursive loop
     * creates a massive number of temporary `String` objects, triggering unnecessary Garbage Collection.
     * We can optimize this by doing simple arithmetic (`current * 10 + digit`) to build the
     * integer in-place. This is drastically faster and highly memory-efficient.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(C(9, n)) where C(9, n) is 9-choose-n. There are exactly C(9, n) valid numbers.
     *   Since we do O(1) math operations per node, Time is strictly proportional to the output size.
     * - **Space Complexity:** O(n) for the recursive auxiliary stack space. The heap space for the result
     *   is O(C(9, n)).
     */
    public List<Integer> generateOptimal(int n) {
        List<Integer> result = new ArrayList<>();
        if (n <= 0 || n > 9) return result; // Edge case handling

        backtrackOptimal(n, 1, 0, result);
        return result;
    }

    private void backtrackOptimal(int digitsRemaining, int startDigit, int currentNum, List<Integer> result) {
        // Base Case: No more digits required, add the formed number
        if (digitsRemaining == 0) {
            result.add(currentNum);
            return;
        }

        // Recursive Case: Append valid digits and recurse
        for (int i = startDigit; i <= 9; i++) {
            backtrackOptimal(digitsRemaining - 1, i + 1, currentNum * 10 + i, result);
        }
    }

    /**
     * ## Phase 2: Brute Force Approach - String Backtracking (Your Provided Code)
     *
     * **Detailed Intuition:**
     * This is the "Think It" phase. It beautifully maps the mental model of building a string
     * character by character. We iterate from the `start` digit to 9, appending it to the
     * `current` string and calling the function for the next digit.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(C(9, n) * n). String concatenation takes O(k) time where k is
     *   the current length. Over all leaves, this adds a multiplier of O(n).
     * - **Space Complexity:** O(n) auxiliary stack space + O(C(9, n) * n) heap space to store
     *   the string results.
     */
    public List<String> generateBruteForce(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0 || n > 9) return result;

        generateNumbersString(n, 1, "", result);
        return result;
    }

    private void generateNumbersString(int n, int start, String current, List<String> result) {
        if (current.length() == n) {
            result.add(current);
            return;
        }

        for (int digit = start; digit <= 9; digit++) {
            generateNumbersString(n, digit + 1, current + digit, result);
        }
    }

    /**
     * ## Phase 3: Alternative Approach - Bitmasking Combinatorics
     *
     * **Detailed Intuition:**
     * Since we are picking exactly `n` unique, strictly increasing digits from a fixed pool
     * of 9 digits (1 through 9), this is identical to finding all subsets of size `n`.
     * We can use a 9-bit integer where the i-th bit represents whether the digit (i+1) is chosen.
     * We iterate from 1 to 2^9 (512). If a number has exactly `n` bits set, we map those bits
     * to digits.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(2^9 * 9) = O(4608) strictly bounded constant time operations.
     *   We then sort the valid combinations which takes O(K log K) where K = C(9, n).
     * - **Space Complexity:** O(C(9, n)) to store the results. Auxiliary stack space is O(1).
     */
    public List<Integer> generateBitmask(int n) {
        List<Integer> result = new ArrayList<>();
        if (n <= 0 || n > 9) return result;

        // Iterate through all possible subsets of 9 items
        for (int mask = 1; mask < (1 << 9); mask++) {
            if (Integer.bitCount(mask) == n) {
                int num = 0;
                for (int i = 1; i <= 9; i++) {
                    // If the (i-1)th bit is set, include digit i
                    if ((mask & (1 << (i - 1))) != 0) {
                        num = num * 10 + i;
                    }
                }
                result.add(num);
            }
        }

        // Bitmask iteration doesn't guarantee numerical order, so we must sort
        Collections.sort(result);
        return result;
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        IncreasingNDigitNumbers solver = new IncreasingNDigitNumbers();

        // Edge Cases and Standard Cases
        int[] testCases = {1, 2, 3, 9, 10};

        System.out.println("--- Running Tests ---");

        // Use Java 8 Stream API to process test cases
        IntStream.of(testCases).forEach(n -> {
            System.out.println("\nTesting n = " + n);

            List<Integer> optimalRes = solver.generateOptimal(n);
            List<String> bruteRes = solver.generateBruteForce(n);
            List<Integer> bitmaskRes = solver.generateBitmask(n);

            // Print the sizes to verify identical behavior
            System.out.println("Optimal Count:   " + optimalRes.size());
            System.out.println("BruteForce Count:" + bruteRes.size());
            System.out.println("Bitmask Count:   " + bitmaskRes.size());

            if (n > 0 && n <= 9) {
                // Peek at the first and last elements for correctness validation
                System.out.println("First element (Optimal): " + optimalRes.get(0));
                System.out.println("Last element (Optimal):  " + optimalRes.get(optimalRes.size() - 1));
            } else {
                System.out.println("Invalid n handled correctly (Empty lists returned).");
            }
            System.out.println("---------------------");
        });
    }
}