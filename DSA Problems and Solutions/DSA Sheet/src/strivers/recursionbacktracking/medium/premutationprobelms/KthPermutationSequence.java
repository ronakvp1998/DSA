package strivers.recursionbacktracking.medium.premutationprobelms;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ==============================================================================================
 * 🤖 K-TH PERMUTATION SEQUENCE (LeetCode 60)
 * ==============================================================================================
 *
 * PROBLEM STATEMENT:
 * ------------------
 * Given n and k, return the k-th permutation sequence of numbers [1, 2, 3, ..., n].
 * The permutations are generated in lexicographical order.
 *
 * CONSTRAINTS:
 * ------------
 * - 1 <= n <= 9
 * - 1 <= k <= n!
 *
 * EXAMPLES:
 * ---------
 * Example 1:
 * Input: n = 3, k = 3
 * Output: "213"
 * Explanation:
 * The ordered permutations for n=3 are:
 * 1. "123"
 * 2. "132"
 * 3. "213"  <-- 3rd permutation
 * 4. "231"
 * 5. "312"
 * 6. "321"
 *
 * Example 2:
 * Input: n = 4, k = 9
 * Output: "2314"
 * Explanation:
 * The 9th permutation out of 24 total permutations for n=4.
 * ==============================================================================================
 */
public class KthPermutationSequence {

    /**
     * ==========================================================================================
     * PHASE 1: OPTIMAL APPROACH (Mathematical / Factorial Number System)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * Instead of generating all permutations (which takes O(n! * n) time), we can directly
     * construct the k-th permutation using a mathematical pattern based on factorials.
     * For a set of n numbers, the total number of permutations starting with any specific digit is (n-1)!.
     * By dividing (k-1) by (n-1)!, we can find the exact index of the first digit to pick from our available pool.
     * We then reduce k (using modulo) and repeat this process for the remaining digits, dynamically updating
     * the factorial size ($fact / remaining\_numbers.size()$).
     *
     * Complexity Analysis:
     * - Time Complexity: O(n^2). There are n iterations, and inside each iteration, list.remove(index)
     *   takes O(n) time in the worst case. (Given n <= 9, this runs instantaneously).
     * - Space Complexity: O(n) Heap space for the numbers list and the output StringBuilder.
     *   Auxiliary Stack Space: O(1) (Iterative solution).
     */
    public static String getPermutationOptimal(int n, int k) {
        // Step 1: Prepare the list of numbers [1, 2, 3, ..., n] using Java 8 Stream API
        List<Integer> numbers = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList());

        // Step 2: Pre-compute factorial values for (n-1)!
        int fact = 1;
        for (int i = 1; i < n; i++) {
            fact *= i;
        }

        // Step 3: Convert k into zero-based index
        k = k - 1;

        StringBuilder ans = new StringBuilder();

        // Step 4: Construct permutation by choosing elements one by one
        while (true) {
            // Find the index of the current number
            int index = k / fact;
            ans.append(numbers.get(index));

            // Remove chosen number from the list
            numbers.remove(index);

            if (numbers.isEmpty()) break;

            // Update k for the remaining digits
            k = k % fact;

            // Update factorial for the next round
            fact = fact / numbers.size();
        }

        return ans.toString();
    }

    /**
     * ==========================================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Backtracking / Lexicographical Generation)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * The most naive way to approach this problem is to generate all possible permutations
     * of numbers from 1 to n in strict lexicographical order, store them in a list or count until
     * we hit the k-th permutation, and return it.
     * While intuitive, this approach explores the entire search space of size n!, making it
     * inefficient for larger values of n (e.g., n = 9 results in 362,880 operations).
     *
     * Complexity Analysis:
     * - Time Complexity: O(n! * n). There are n! permutations, and constructing/copying each takes O(n).
     * - Space Complexity:
     *   - Auxiliary Stack Space: O(n) due to recursion depth during backtracking.
     *   - Heap Space: O(n! * n) to store all generated permutation strings in memory.
     */
    public static String getPermutationBruteForce(int n, int k) {
        List<String> allPermutations = new ArrayList<>();
        boolean[] visited = new boolean[n + 1];
        generatePermutations(n, new StringBuilder(), visited, allPermutations);

        // Return the k-th permutation (1-based index converted to 0-based index)
        if (k > 0 && k <= allPermutations.size()) {
            return allPermutations.get(k - 1);
        }
        return "";
    }

    private static void generatePermutations(int n, StringBuilder current, boolean[] visited, List<String> result) {
        if (current.length() == n) {
            result.add(current.toString());
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                current.append(i);
                generatePermutations(n, current, visited, result);
                current.deleteCharAt(current.length() - 1);
                visited[i] = false;
            }
        }
    }

    /**
     * ==========================================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Repeated Next Permutation Algorithm)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * An alternative iterative technique is to start with the initial sorted permutation ("123...n")
     * and repeatedly execute the standard "Next Lexicographical Permutation" algorithm exactly k-1 times.
     * The next permutation algorithm finds the first decreasing element from the right, swaps it with
     * the next larger element, and reverses the suffix.
     *
     * Complexity Analysis:
     * - Time Complexity: O(k * n). Finding the next permutation takes O(n) time, repeated k-1 times.
     *   In the worst case where k = n!, this is O(n! * n), but it works well when k is small.
     * - Space Complexity:
     *   - Auxiliary Stack Space: O(1).
     *   - Heap Space: O(n) to maintain the character/integer array representing the sequence.
     */
    public static String getPermutationNextPermutation(int n, int k) {
        // Initialize the array with [1, 2, ..., n]
        int[] nums = IntStream.rangeClosed(1, n).toArray();

        // Apply next permutation k-1 times
        for (int step = 1; step < k; step++) {
            nextPermutation(nums);
        }

        // Convert array to string using Java 8 streams
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num);
        }
        return sb.toString();
    }

    private static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1, nums.length - 1);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start++, end--);
        }
    }

    /**
     * ==========================================================================================
     * 4. TESTING SUITE
     * ==========================================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Starting Comprehensive Test Suite for K-th Permutation Sequence...\n");

        int[][] testCases = {
                // {n, k}
                {3, 3}, // Expected: "213"
                {4, 9}, // Expected: "2314"
                {3, 1}, // Edge case: first permutation ("123")
                {3, 6}, // Edge case: last permutation for n=3 ("321")
                {1, 1}  // Edge case: n = 1 ("1")
        };

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i][0];
            int k = testCases[i][1];

            System.out.printf("Test Case %d: n = %d, k = %d%n", i + 1, n, k);

            // Run Optimal Approach
            String optResult = getPermutationOptimal(n, k);
            System.out.printf("   Optimal Approach          -> %s%n", optResult);

            // Run Brute Force Approach (Only run for small n to prevent excessive heap allocation)
            if (n <= 4) {
                String bfResult = getPermutationBruteForce(n, k);
                System.out.printf("   Brute Force Backtracking  -> %s%n", bfResult);
            } else {
                System.out.println("   Brute Force Backtracking  -> Skipped (n too large for full generation)");
            }

            // Run Next Permutation Approach
            String npResult = getPermutationNextPermutation(n, k);
            System.out.printf("   Next Permutation Approach -> %s%n", npResult);

            System.out.println("---------------------------------------------------------");
        }

        System.out.println("\n✅ All test cases executed successfully.");
    }
}