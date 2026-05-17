package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: Find the Duplicate Number (LeetCode 287)
 * ============================================================================
 *
 * 1. Header & Problem Context
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an array of integers nums containing n + 1 integers where each integer
 * is in the range [1, n] inclusive.
 * There is only one repeated number in nums, return this repeated number.
 * You must solve the problem without modifying the array nums and using only
 * constant extra space.
 *
 * Example 1:
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 *
 * Example 3:
 * Input: nums = [3,3,3,3,3]
 * Output: 3
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - nums.length == n + 1
 * - 1 <= nums[i] <= n
 * - All the integers in nums appear only once except for precisely one integer
 *   which appears two or more times.
 *
 * ============================================================================
 */
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindDuplicateNumber {

    /**
     * ========================================================================
     * Phase 1: Optimal Approach - Floyd's Tortoise and Hare (Cycle Detection)
     * ========================================================================
     * Detailed Intuition:
     * The constraints strictly limit us: O(1) space, no array modification.
     * Since the array has n+1 elements and values are in the range [1, n],
     * we can map this to a Linked List cycle detection problem.
     *
     * Imagine the array indices are "nodes" and the values are "pointers" to
     * the next node (i.e., node `i` points to node `nums[i]`).
     * Because multiple indices must point to the same target value (the duplicate),
     * multiple nodes point to the same next node. This guarantees a cycle exists,
     * and the entrance to this cycle is exactly the duplicate number!
     *
     * Algorithm:
     * 1. Initialize two pointers, `slow` and `fast`, both starting at `nums[0]`.
     * 2. Move `slow` by 1 step (`nums[slow]`) and `fast` by 2 steps (`nums[nums[fast]]`).
     * 3. Once they intersect inside the cycle, reset `slow` to 0.
     * 4. Move both `slow` and `fast` by 1 step. The point where they meet again
     *    is the exact entrance to the cycle (the duplicate number).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the size of the array. The pointers
     *   will traverse the array at most a linear number of times.
     * - Space Complexity: O(1) auxiliary space. We only use two integer pointers.
     */
    public static int findDuplicateOptimal(int[] nums) {
        if (nums == null || nums.length <= 1) return -1;

        // Step 1: Find intersection point of the two runners.
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Step 2: Find the entrance to the cycle.
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    /**
     * ========================================================================
     * Phase 2: Brute Force Approach - Nested Loops
     * ========================================================================
     * Detailed Intuition:
     * The most naive way to find a duplicate is to take every element and
     * compare it with every other element in the array. This is the "Think it"
     * stage before optimization.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) where N is the size of the array. We have a
     *   nested loop iterating over all pairs. This will TLE on LeetCode.
     * - Space Complexity: O(1) auxiliary space. No extra memory is allocated.
     */
    public static int findDuplicateBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }
        return -1;
    }

    /**
     * ========================================================================
     * Phase 3A: Alternative Approach - Binary Search on Answer Range
     * ========================================================================
     * Detailed Intuition:
     * Even though the array isn't sorted, the *range of possible answers* [1, n]
     * IS sorted. We can use Binary Search on the answer space (Pigeonhole Principle).
     *
     * For a guess `mid` in [1, n], we count how many numbers in `nums` are
     * less than or equal to `mid`.
     * - If count > mid, the duplicate MUST be in the range [low, mid].
     * - Otherwise, the duplicate MUST be in the range [mid + 1, high].
     *
     * Example: nums = [1,3,4,2,2], n = 4. Range = [1, 4]. Mid = 2.
     * Count of nums <= 2 is 3 (1, 2, 2). 3 > 2, so duplicate is in [1, 2].
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N). Binary search takes O(log N) iterations,
     *   and counting elements takes O(N) time per iteration.
     * - Space Complexity: O(1) auxiliary space.
     */
    public static int findDuplicateBinarySearch(int[] nums) {
        int low = 1;
        int high = nums.length - 1; // Since length is n + 1, high is n
        int duplicate = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Using Java 8 Streams to count elements <= mid
            // Note: In highly competitive environments, a traditional loop is faster,
            // but stream API is requested and perfectly valid here.
            long count = Arrays.stream(nums).filter(num -> num <= mid).count();

            if (count > mid) {
                duplicate = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return duplicate;
    }

    /**
     * ========================================================================
     * Phase 3B: Alternative Approach - Index Marking (Violates Constraints)
     * ========================================================================
     * Detailed Intuition:
     * If modifying the array was allowed, we could iterate through the array
     * and use the elements as indices. For each element, we flip the sign of
     * the value at the corresponding index to negative. If we encounter an
     * index that already has a negative value, we've found our duplicate!
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     * - Space Complexity: O(1) auxiliary space.
     * - Note: Violates the "Do not modify array" constraint.
     */
    public static int findDuplicateMarking(int[] nums) {
        // Clone array to prevent actual mutation in our test suite
        int[] clonedNums = nums.clone();

        for (int i = 0; i < clonedNums.length; i++) {
            int index = Math.abs(clonedNums[i]);
            if (clonedNums[index] < 0) {
                return index;
            }
            clonedNums[index] = -clonedNums[index];
        }
        return -1;
    }

    /**
     * ========================================================================
     * Phase 3C: Alternative Approach - HashSet (Violates Constraints)
     * ========================================================================
     * Detailed Intuition:
     * Use a HashSet to keep track of seen elements. The first element that
     * cannot be added to the set (because it already exists) is the duplicate.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     * - Space Complexity: O(N) auxiliary space.
     * - Note: Violates the "O(1) extra space" constraint.
     */
    public static int findDuplicateHashSet(int[] nums) {
        Set<Integer> seen = new java.util.HashSet<>();
        // Utilizing Java 8 Stream behavior conceptually, though a for-each is standard.
        for (int num : nums) {
            if (!seen.add(num)) {
                return num;
            }
        }
        return -1;
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     * A comprehensive testing method to validate all approaches against
     * standard and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("  LeetCode 287: Find the Duplicate Number Test Suite ");
        System.out.println("=====================================================\n");

        // Define test cases mapping Input Array -> Expected Output
        class TestCase {
            int[] input;
            int expected;
            String description;

            TestCase(int[] input, int expected, String description) {
                this.input = input;
                this.expected = expected;
                this.description = description;
            }
        }

        TestCase[] tests = {
                new TestCase(new int[]{1, 3, 4, 2, 2}, 2, "Standard Case 1"),
                new TestCase(new int[]{3, 1, 3, 4, 2}, 3, "Standard Case 2"),
                new TestCase(new int[]{3, 3, 3, 3, 3}, 3, "All Duplicates"),
                new TestCase(new int[]{2, 2, 2, 2, 2}, 2, "All Duplicates (Even)"),
                new TestCase(new int[]{1, 4, 4, 2, 4}, 4, "Duplicate in middle"),
                new TestCase(new int[]{1, 1}, 1, "Minimum Constraint (n=1)"),
                new TestCase(new int[]{2, 5, 9, 6, 9, 3, 8, 9, 7, 1}, 9, "Larger Array")
        };

        // Execute tests using Java 8 Streams for clean iteration
        IntStream.range(0, tests.length).forEach(i -> {
            TestCase test = tests[i];
            System.out.println("Test " + (i + 1) + ": " + test.description);
            System.out.println("Input: " + Arrays.toString(test.input));
            System.out.println("Expected: " + test.expected);

            // Execute all valid methods
            int resOptimal = findDuplicateOptimal(test.input);
            int resBrute = findDuplicateBruteForce(test.input);
            int resBinarySearch = findDuplicateBinarySearch(test.input);
            int resMarking = findDuplicateMarking(test.input);
            int resHash = findDuplicateHashSet(test.input);

            // Validation via Stream API boolean allMatch
            boolean allPassed = java.util.stream.Stream.of(
                    resOptimal, resBrute, resBinarySearch, resMarking, resHash
            ).allMatch(res -> res == test.expected);

            if (allPassed) {
                System.out.println("Result: [PASS] All methods returned " + test.expected);
            } else {
                System.out.println("Result: [FAIL] Mismatched outputs detected:");
                System.out.println("  Optimal: " + resOptimal);
                System.out.println("  Brute Force: " + resBrute);
                System.out.println("  Binary Search: " + resBinarySearch);
                System.out.println("  Marking: " + resMarking);
                System.out.println("  HashSet: " + resHash);
            }
            System.out.println("-----------------------------------------------------");
        });
    }
}