package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: Find the Missing Number
 *
 * Formal Problem Statement:
 * Given an array arr[] of size n-1 with distinct integers in the range of [1, n].
 * This array represents a permutation of the integers from 1 to n with one
 * element missing. Find the missing element in the array.
 *
 * Constraints (Implicit from standard competitive programming platforms):
 * - 1 <= n <= 10^6
 * - 1 <= arr[i] <= n
 * - All elements in the array are distinct.
 *
 * Example 1:
 * Input: arr[] = [8, 2, 4, 5, 3, 7, 1]
 * Output: 6
 * Explanation: All the numbers from 1 to 8 are present except 6.
 *
 * Example 2:
 * Input: arr[] = [1, 2, 3, 5]
 * Output: 4
 * Explanation: Here the size of the array is 4, so the range will be [1, 5].
 * The missing number between 1 to 5 is 4.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MissingNumberMasterclass {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Bit Manipulation - XOR)
     * ============================================================================
     * Detailed Intuition:
     * The most bulletproof and optimal way to find a missing number without
     * risking integer overflow is using the XOR bitwise operator.
     * Properties of XOR:
     * 1. a ^ a = 0 (XORing a number with itself cancels it out)
     * 2. a ^ 0 = a (XORing with 0 leaves the number unchanged)
     *
     * If we XOR all numbers from 1 to n, and then XOR that result with all the
     * elements present in the array, every number that is present will appear
     * exactly twice (once from the 1..n sequence, once from the array). They
     * will cancel each other out to 0. The missing number will only appear once
     * (in the 1..n sequence), so it will be the only number left standing!
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. We make a
     *   single pass through the numbers.
     * - Space Complexity: O(1) auxiliary space. We only use two integer variables.
     */
    public int missingNumberOptimal(int[] arr) {
        if (arr == null || arr.length == 0) return 1;

        int n = arr.length + 1;
        int xorFullSequence = 0;
        int xorArrayElements = 0;

        // XOR all numbers from 1 to n
        for (int i = 1; i <= n; i++) {
            xorFullSequence ^= i;
        }

        // XOR all elements in the given array
        for (int num : arr) {
            xorArrayElements ^= num;
        }

        // The missing number is the difference between the two XORs
        return xorFullSequence ^ xorArrayElements;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (Nested Loops)
     * ============================================================================
     * Detailed Intuition:
     * The most basic "Think it" approach. For every number `i` from 1 to `n`,
     * we scan the entire array to check if `i` exists. If we finish scanning
     * the array and haven't found `i`, then `i` must be the missing number.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). For each of the N elements, we might iterate
     *   through the entire array of size N-1. This will cause Time Limit Exceeded
     *   (TLE) for large inputs.
     * - Space Complexity: O(1) auxiliary space.
     */
    public int missingNumberBruteForce(int[] arr) {
        int n = arr.length + 1;

        for (int i = 1; i <= n; i++) {
            boolean found = false;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == i) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return i;
            }
        }
        return -1; // Should theoretically never be reached if input is valid
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 1 (Mathematical Formula & Streams)
     * ============================================================================
     * Detailed Intuition:
     * We know that the sum of the first `N` natural numbers follows the formula:
     * Sum = N * (N + 1) / 2.
     * If we calculate the expected sum of all numbers from 1 to n, and then
     * subtract the actual sum of the elements present in the array, the
     * difference will be exactly our missing number.
     *
     * *CRITICAL NOTE:* We MUST use `long` to prevent integer overflow. If N is
     * 10^5, N*(N+1)/2 is ~5*10^9, which exceeds the max value of a 32-bit signed
     * integer (2.14*10^9). We leverage Java 8 Streams here for a concise summation.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We traverse the array once to calculate the sum.
     * - Space Complexity: O(1) auxiliary space.
     */
    public int missingNumberMath(int[] arr) {
        long n = arr.length + 1;

        // Expected sum formula: n * (n + 1) / 2
        long expectedSum = (n * (n + 1)) / 2;

        // Using Java 8 Streams to calculate the actual sum of the array.
        // asLongStream() prevents overflow during summation.
        long actualSum = Arrays.stream(arr).asLongStream().sum();

        return (int) (expectedSum - actualSum);
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 2 (Hashing / HashSet)
     * ============================================================================
     * Detailed Intuition:
     * We can trade space for time. By inserting all elements of the array into a
     * HashSet (or a boolean frequency array), we can achieve O(1) lookup times.
     * Then, we simply loop from 1 to n and check which number is missing from the set.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Inserting N-1 elements takes O(N), and checking
     *   takes O(N).
     * - Space Complexity: O(N) heap space to store the Set/Hash structure.
     */
    public int missingNumberHashing(int[] arr) {
        int n = arr.length + 1;

        // Java 8 Stream to Box ints and collect to a HashSet
        Set<Integer> numSet = Arrays.stream(arr).boxed().collect(Collectors.toSet());

        for (int i = 1; i <= n; i++) {
            if (!numSet.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        MissingNumberMasterclass solution = new MissingNumberMasterclass();

        System.out.println("--- Testing Find the Missing Number ---");

        // Test Case 1: Standard Example 1 (Missing in middle)
        int[] tc1 = {8, 2, 4, 5, 3, 7, 1};
        System.out.println("\nTest 1 (Optimal XOR):");
        System.out.println("Input: " + Arrays.toString(tc1));
        System.out.println("Output: " + solution.missingNumberOptimal(tc1)); // Expected: 6

        // Test Case 2: Standard Example 2 (Missing at end)
        int[] tc2 = {1, 2, 3, 5};
        System.out.println("\nTest 2 (Math & Streams):");
        System.out.println("Input: " + Arrays.toString(tc2));
        System.out.println("Output: " + solution.missingNumberMath(tc2)); // Expected: 4

        // Test Case 3: Missing the first number (1)
        int[] tc3 = {2, 3, 4, 5};
        System.out.println("\nTest 3 (Missing First Element - Hashing):");
        System.out.println("Input: " + Arrays.toString(tc3));
        System.out.println("Output: " + solution.missingNumberHashing(tc3)); // Expected: 1

        // Test Case 4: Missing the last number (n)
        int[] tc4 = {1, 2, 3, 4};
        System.out.println("\nTest 4 (Missing Last Element - Brute Force):");
        System.out.println("Input: " + Arrays.toString(tc4));
        System.out.println("Output: " + solution.missingNumberBruteForce(tc4)); // Expected: 5

        // Test Case 5: Smallest valid array (size 0, range 1)
        int[] tc5 = {};
        System.out.println("\nTest 5 (Empty Array - Optimal XOR):");
        System.out.println("Input: [] (n=1)");
        System.out.println("Output: " + solution.missingNumberOptimal(tc5)); // Expected: 1
    }
}