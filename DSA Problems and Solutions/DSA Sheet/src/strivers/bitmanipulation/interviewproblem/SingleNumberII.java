package strivers.bitmanipulation.interviewproblem;

/**
 * ============================================================================
 * DSA MASTERCLASS: Single Number II
 * ============================================================================
 * * 🎯 PROBLEM STATEMENT (LeetCode 137)
 * Given an integer array `nums` where every element appears three times
 * except for one, which appears exactly once. Find the single element and return it.
 * * You must implement a solution with a linear runtime complexity and use
 * only constant extra space.
 * * 📌 EXAMPLES
 * Example 1:
 * Input: nums = [2,2,3,2]
 * Output: 3
 * * Example 2:
 * Input: nums = [0,1,0,1,0,1,99]
 * Output: 99
 * * ⚙️ CONSTRAINTS
 * - 1 <= nums.length <= 3 * 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 * - Each element in nums appears exactly three times except for one element
 * which appears once.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Brute Force (Hashing) - Count frequencies, find the 1.
 * Phase 2: Alternative (Sorting) - Sort and check adjacent elements.
 * Phase 3: Alternative (Bit Counting) - Count set bits at each of the 32 positions.
 * Phase 4: Optimal (Bit Manipulation) - Use digital logic to track bit states.
 * ============================================================================
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class SingleNumberII {

    /**
     * ========================================================================
     * PHASE 1: Brute Force Approach (Hashing)
     * ========================================================================
     * 🧠 Detailed Intuition:
     * The simplest way to "Find the single element" is to keep a tally of how
     * many times we've seen every number. A Hash Map maps each number to its
     * frequency. After populating the map, we iterate through it to find the
     * key that has a value of exactly 1.
     * * ⏱️ Complexity Analysis:
     * - Time Complexity: O(N) to iterate through the array and populate the map,
     * plus O(N/3) to iterate through the map. Overall Time: O(N).
     * - Space Complexity: O(N) auxiliary space for the HashMap to store up to
     * (N/3)+1 distinct elements.
     * * Fails the O(1) space constraint of the problem.
     * ========================================================================
     */
    public int singleNumberHashMap(int[] nums) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return -1; // Should not be reached given problem constraints
    }

    /**
     * ========================================================================
     * PHASE 2: Alternative Approach (Sorting)
     * ========================================================================
     * 🧠 Detailed Intuition:
     * If we cannot use extra space for a HashMap, what if we group identical
     * numbers together? Sorting the array puts the groups of threes next to
     * each other. We can then iterate through the array in steps of 3. If the
     * current element doesn't match the next element, we've found our single number.
     * * ⏱️ Complexity Analysis:
     * - Time Complexity: O(N log N) due to the sorting algorithm.
     * - Space Complexity: O(1) auxiliary space (ignoring the underlying sort space),
     * but fails the O(N) time constraint.
     * ========================================================================
     */
    public int singleNumberSorting(int[] nums) {
        Arrays.sort(nums);

        // Iterate jumping by 3. If we are at the last element, or the current
        // doesn't match the next, it's our single number.
        for (int i = 0; i < nums.length - 1; i += 3) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        // If the loop finishes without returning, the single number is the last element
        return nums[nums.length - 1];
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approach (Bit Counting)
     * ========================================================================
     * 🧠 Detailed Intuition:
     * We need O(N) time and O(1) space. Let's look at the numbers at the bit level.
     * Since every number (except one) appears exactly 3 times, if we sum the bits
     * at the i-th position for ALL numbers, that sum must be a multiple of 3.
     * If the sum is NOT a multiple of 3 (i.e., sum % 3 == 1), it means the single
     * number has a 1 at that i-th bit position. We can rebuild the single number bit by bit.
     * * ⏱️ Complexity Analysis:
     * - Time Complexity: O(32 * N) -> O(N). We iterate through 32 bits, and for
     * each bit, we iterate through the N elements.
     * - Space Complexity: O(1) auxiliary space. We only use a few integer variables.
     * * Meets all constraints, but can be optimized further in practice!
     * ========================================================================
     */
    public int singleNumberBitCounting(int[] nums) {
        int result = 0;

        // Iterate through all 32 bit positions of an integer
        for (int i = 0; i < 32; i++) {
            int bitCount = 0;

            // Count how many numbers have the i-th bit set to 1
            for (int num : nums) {
                if (((num >> i) & 1) == 1) {
                    bitCount++;
                }
            }

            // If the count is not divisible by 3, the single number has this bit set
            if (bitCount % 3 != 0) {
                result |= (1 << i);
            }
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 4: Optimal Approach (Bit Manipulation State Machine)
     * ========================================================================
     * 🧠 Detailed Intuition:
     * Instead of counting bits individually using an inner loop, we can process
     * all 32 bits simultaneously using bitwise logic. We want a state machine that
     * tracks the bits that have appeared exactly ONCE and exactly TWICE.
     * * Let `ones` keep track of bits that have appeared 1 time (modulo 3).
     * Let `twos` keep track of bits that have appeared 2 times (modulo 3).
     * * When a bit appears for the 1st time: Add to `ones`.
     * When a bit appears for the 2nd time: Remove from `ones`, add to `twos`.
     * When a bit appears for the 3rd time: Remove from `twos`.
     * * Logic for `ones`:
     * `ones ^ num` adds the bit if it's not there, removes it if it is.
     * `& ~twos` ensures we don't add bits that have already appeared twice.
     * * ⏱️ Complexity Analysis:
     * - Time Complexity: O(N) - Single pass through the array.
     * - Space Complexity: O(1) auxiliary space - Only two integer variables used.
     * * Absolute mastery of constraints.
     * ========================================================================
     * Occurrence of a 1 bit,State of twos,State of ones,What is happening?
     * 0 times (Start),0,0,Haven't seen this bit yet.
     * 1st time,0,1,Bit is recorded in ones.
     * 2nd time,1,0,"Removed from ones, added to twos."
     * 3rd time,0,0,Reset. Removed from twos.
     */
    public int singleNumberOptimal(int[] nums) {
        int ones = 0;
        int twos = 0;

        for (int num : nums) {
            // Update 'ones': Add the bit if it's in 'num' and NOT in 'twos'
            ones = (ones ^ num) & ~twos;

            // Update 'twos': Add the bit if it's in 'num' and NOT in 'ones'
            // (Note: 'ones' here is the updated 'ones' from the previous line)
            twos = (twos ^ num) & ~ones;
        }

        // 'ones' will hold the result because the single number appears exactly once
        return ones;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering standard cases, negatives, and edge cases.
     */
    public static void main(String[] args) {
        SingleNumberII solver = new SingleNumberII();

        // Test Case 1: Standard LeetCode Example 1
        int[] test1 = {2, 2, 3, 2};
        System.out.println("Test 1 [2,2,3,2] Expected: 3");
        System.out.println("  HashMap:     " + solver.singleNumberHashMap(test1));
        System.out.println("  Sorting:     " + solver.singleNumberSorting(test1));
        System.out.println("  Bit Counting:" + solver.singleNumberBitCounting(test1));
        System.out.println("  Optimal:     " + solver.singleNumberOptimal(test1));
        System.out.println("--------------------------------------------------");

        // Test Case 2: Standard LeetCode Example 2
        int[] test2 = {0, 1, 0, 1, 0, 1, 99};
        System.out.println("Test 2 [0,1,0,1,0,1,99] Expected: 99");
        System.out.println("  HashMap:     " + solver.singleNumberHashMap(test2));
        System.out.println("  Sorting:     " + solver.singleNumberSorting(test2));
        System.out.println("  Bit Counting:" + solver.singleNumberBitCounting(test2));
        System.out.println("  Optimal:     " + solver.singleNumberOptimal(test2));
        System.out.println("--------------------------------------------------");

        // Test Case 3: Negative Numbers
        int[] test3 = {-2, -2, 1, 1, -3, 1, -3, -3, -4, -2};
        System.out.println("Test 3 [-2,-2,1,1,-3,1,-3,-3,-4,-2] Expected: -4");
        System.out.println("  HashMap:     " + solver.singleNumberHashMap(test3));
        System.out.println("  Sorting:     " + solver.singleNumberSorting(test3));
        System.out.println("  Bit Counting:" + solver.singleNumberBitCounting(test3));
        System.out.println("  Optimal:     " + solver.singleNumberOptimal(test3));
        System.out.println("--------------------------------------------------");

        // Test Case 4: Array of length 1
        int[] test4 = {42};
        System.out.println("Test 4 [42] Expected: 42");
        System.out.println("  HashMap:     " + solver.singleNumberHashMap(test4));
        System.out.println("  Sorting:     " + solver.singleNumberSorting(test4));
        System.out.println("  Bit Counting:" + solver.singleNumberBitCounting(test4));
        System.out.println("  Optimal:     " + solver.singleNumberOptimal(test4));
    }
}