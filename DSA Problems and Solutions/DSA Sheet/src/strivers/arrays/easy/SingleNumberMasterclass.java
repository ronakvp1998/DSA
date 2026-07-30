package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 136. Single Number
 * Difficulty: Easy
 *
 * Formal Problem Statement:
 * Given a non-empty array of integers nums, every element appears twice except
 * for one. Find that single one.
 *
 * You must implement a solution with a linear runtime complexity and use
 * only constant extra space.
 *
 * Constraints:
 * - 1 <= nums.length <= 3 * 10^4
 * - -3 * 10^4 <= nums[i] <= 3 * 10^4
 * - Each element in the array appears twice except for one element which
 *   appears only once.
 *
 * Example 1:
 * Input: nums = [2,2,1]
 * Output: 1
 *
 * Example 2:
 * Input: nums = [4,1,2,1,2]
 * Output: 4
 *
 * Example 3:
 * Input: nums = [1]
 * Output: 1
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SingleNumberMasterclass {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Bitwise XOR)
     * ============================================================================
     * Detailed Intuition:
     * To achieve strictly O(N) time and O(1) space, we must avoid nested loops
     * and external data structures. The perfect tool for this is the Bitwise XOR
     * operator (`^`).
     *
     * Key XOR Properties:
     * 1. XOR of a number with itself is 0: a ^ a = 0
     * 2. XOR of a number with 0 is the number itself: a ^ 0 = a
     * 3. XOR is associative and commutative: a ^ b ^ a = (a ^ a) ^ b = 0 ^ b = b
     *
     * Since every element appears exactly twice except for one, if we XOR all
     * the elements together, the duplicate numbers will cancel each other out
     * (becoming 0), leaving only the single unique number at the end.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of elements in the array.
     *   We iterate through the array exactly once.
     * - Space Complexity: O(1) auxiliary space. We use a single integer variable,
     *   satisfying the strict constant extra space constraint.
     */
    public int singleNumberOptimal(int[] nums) {
        int singleNum = 0;
        for (int num : nums) {
            singleNum ^= num;
        }
        return singleNum;
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (Nested Loops)
     * ============================================================================
     * Detailed Intuition:
     * The most rudimentary way to find the single element without extra space is
     * to pick an element, and then scan the entire array to count how many times
     * it appears. If it appears exactly once, we found our answer.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). For each of the N elements, we iterate through
     *   all N elements to count its frequency. This will likely result in a Time
     *   Limit Exceeded (TLE) error for large arrays.
     * - Space Complexity: O(1) auxiliary space. No extra memory is allocated.
     */
    public int singleNumberBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count == 1) {
                return nums[i];
            }
        }
        return -1; // Fallback, theoretically unreachable given problem constraints
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 1 (Hashing via HashSet)
     * ============================================================================
     * Detailed Intuition:
     * We can trade space to improve our time complexity. Using a HashSet, we can
     * keep track of the elements we see.
     * As we iterate:
     * - If the number is NOT in the set, we add it.
     * - If the number IS already in the set, we remove it (since it's a duplicate).
     * By the time we finish iterating, all pairs will have been added and then
     * removed. The only number left in the HashSet will be our single number.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array once. Adding and
     *   removing from a HashSet takes O(1) on average.
     * - Space Complexity: O(N) heap space. In the worst-case scenario (before
     *   we start finding duplicates), the HashSet might store up to (N/2 + 1)
     *   elements. Fails the O(1) space constraint of the problem.
     */
    public int singleNumberHashSet(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            // set.add() returns false if the element was already present
            if (!set.add(num)) {
                set.remove(num);
            }
        }

        // The set will contain exactly one element at this point
        return set.iterator().next();
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 2 (Java 8 Streams Reduction)
     * ============================================================================
     * Detailed Intuition:
     * We can execute the exact same logic as the optimal XOR approach but write
     * it in a highly declarative, functional style using Java 8 Streams.
     * The `reduce` function continuously applies the XOR operation across all
     * elements in the stream, accumulating the result.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). The stream traverses the array once.
     * - Space Complexity: O(1) conceptually, though Stream creation introduces
     *   minor operational overhead under the hood. Still an excellent one-liner.
     */
    public int singleNumberStreams(int[] nums) {
        return Arrays.stream(nums)
                .reduce(0, (a, b) -> a ^ b);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        SingleNumberMasterclass solution = new SingleNumberMasterclass();

        System.out.println("--- Testing LeetCode 136: Single Number ---");

        // Test Case 1: Standard case (Example 1)
        int[] tc1 = {2, 2, 1};
        System.out.println("\nTest 1 (Optimal XOR):");
        System.out.println("Input: " + Arrays.toString(tc1));
        System.out.println("Output: " + solution.singleNumberOptimal(tc1)); // Expected: 1

        // Test Case 2: Mixed order (Example 2)
        int[] tc2 = {4, 1, 2, 1, 2};
        System.out.println("\nTest 2 (HashSet Approach):");
        System.out.println("Input: " + Arrays.toString(tc2));
        System.out.println("Output: " + solution.singleNumberHashSet(tc2)); // Expected: 4

        // Test Case 3: Single element array (Example 3)
        int[] tc3 = {1};
        System.out.println("\nTest 3 (Brute Force):");
        System.out.println("Input: " + Arrays.toString(tc3));
        System.out.println("Output: " + solution.singleNumberBruteForce(tc3)); // Expected: 1

        // Test Case 4: Negative numbers
        int[] tc4 = {-1, -1, -5, 3, 3};
        System.out.println("\nTest 4 (Negative Numbers - Streams):");
        System.out.println("Input: " + Arrays.toString(tc4));
        System.out.println("Output: " + solution.singleNumberStreams(tc4)); // Expected: -5

        // Test Case 5: Large Zero values
        int[] tc5 = {0, 0, 99};
        System.out.println("\nTest 5 (Zeroes Included - Optimal XOR):");
        System.out.println("Input: " + Arrays.toString(tc5));
        System.out.println("Output: " + solution.singleNumberOptimal(tc5)); // Expected: 99
    }
}