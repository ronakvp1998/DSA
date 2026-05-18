package strivers.arrays.hard;
/**
 * ============================================================================
 * 🎯 MASTERCLASS: Majority Element II (> N/3 times)
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 * * Formal Problem Statement (LeetCode 229):
 * Given an integer array of size N, find all elements that appear more than
 * ⌊N/3⌋ times.
 *
 * Constraints:
 * - 1 <= nums.length <= 5 * 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - Follow up: Could you solve the problem in linear time and in O(1) space?
 *
 * Example 1:
 * Input Format: N = 5, array[] = {1,2,2,3,2}
 * Result: [2]
 * Explanation: Count(1) = 1, Count(2) = 3, Count(3) = 1.
 * N/3 = 5/3 = 1. The count of 2 is > 1. Hence, 2 is the answer.
 *
 * Example 2:
 * Input Format:  N = 6, array[] = {11,33,33,11,33,11}
 * Result: [11, 33]
 * Explanation: Count(11) = 3, Count(33) = 3.
 * N/3 = 6/3 = 2. Both counts are > 2. Hence, 11 and 33 are the answer.
 *
 * * Conceptual Visualization (The Math Property):
 * CRITICAL INSIGHT: How many elements can appear MORE than ⌊N/3⌋ times?
 * - Let N = 10. ⌊10/3⌋ = 3. We need elements appearing >= 4 times.
 * - 4 + 4 + 4 = 12, which is > 10.
 * - Therefore, there can be AT MOST 2 elements that satisfy this condition!
 * * Visualization of Extended Boyer-Moore Voting:
 * Array: [ 1,  1,  1,  3,  3,  2,  2,  2 ]
 * We track up to 2 candidates.
 * Step 1: 1 enters -> Cand1 = 1 (Count: 1)
 * Step 2: 1 enters -> Cand1 = 1 (Count: 2)
 * Step 3: 1 enters -> Cand1 = 1 (Count: 3)
 * Step 4: 3 enters -> Cand2 = 3 (Count: 1)
 * Step 5: 3 enters -> Cand2 = 3 (Count: 2)
 * Step 6: 2 enters -> Neither Cand1 nor Cand2. Both counts decrement.
 * Cand1 = 1 (Count: 2), Cand2 = 3 (Count: 1)
 * Step 7: 2 enters -> Neither Cand1 nor Cand2. Both counts decrement.
 * Cand1 = 1 (Count: 1), Cand2 = 3 (Count: 0 -> Evicted!)
 * Step 8: 2 enters -> Cand2 = 2 (Count: 1)
 * Result Candidates: 1 and 2. (Pass 2 will verify them).
 *
 * ============================================================================
 * ### 2.2 Progressive Implementation Roadmap
 * * Phase 1: Best and Recommended Approach (Extended Boyer-Moore Voting)
 * -> Maintain 2 variables and 2 counters. Pass 1 finds candidates. Pass 2 verifies.
 * * Phase 2: Brute Force Approach - The "Think it" stage.
 * -> Nested loops to explicitly count the frequency of every single element.
 * * Phase 3: Alternative Approach (Hashing)
 * -> Use a HashMap to compute frequencies in a single pass, then filter.
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityElementsN3 {

    /**
     * ============================================================================
     * Phase 1: Best and recommended approach - Extended Boyer-Moore Voting
     * ============================================================================
     * Detailed Intuition:
     * Since at most 2 elements can be the answer, we can extend the Boyer-Moore
     * Majority Voting algorithm to keep track of TWO candidates instead of one.
     * We iterate through the array. If the current element matches candidate 1,
     * we increment count 1. If it matches candidate 2, we increment count 2.
     * If it matches neither, we check if there's an "empty slot" (count == 0).
     * If both slots are full and the element matches neither, it represents a
     * "triplet" of 3 distinct elements. We conceptually discard this triplet
     * by decrementing both counters.
     * Finally, we must do a second pass to verify if the surviving candidates
     * actually appear more than N/3 times.
     *
     * Complexity Analysis:
     * Time (O): O(N) - We do exactly two passes over the array. 2N operations.
     * Space (O): O(1) - Only four primitive variables allocated in auxiliary
     * stack space. The returned List is not counted towards extra space.
     */
    public static List<Integer> phase1OptimalBoyerMoore(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;

        int n = nums.length;

        // Step 1: Find potential candidates
        int cand1 = Integer.MIN_VALUE, cand2 = Integer.MIN_VALUE;
        int count1 = 0, count2 = 0;

        for (int i = 0; i < n; i++) {
            // Priority 1: Check if current element matches existing candidates
            if (nums[i] == cand1) {
                count1++;
            } else if (nums[i] == cand2) {
                count2++;
            }
            // Priority 2: If no match, check for an empty candidate slot
            else if (count1 == 0) {
                cand1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = nums[i];
                count2 = 1;
            }
            // Priority 3: Element matches neither candidate and slots are full.
            // Discard a triplet consisting of (cand1, cand2, nums[i]).
            else {
                count1--;
                count2--;
            }
        }

        // Step 2: Verify the candidates
        count1 = 0;
        count2 = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == cand1) count1++;
            else if (nums[i] == cand2) count2++;
        }

        int threshold = n / 3;
        if (count1 > threshold) result.add(cand1);
        if (count2 > threshold) result.add(cand2);

        return result;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * Simply pick an element, scan the entire array to count its occurrences.
     * If the count is > ⌊N/3⌋, add it to the result.
     * *Senior Dev Optimization:* To prevent adding the same valid element twice
     * (e.g., in [1, 1, 1, 1], we only want [1]), we check if our result list already
     * contains it. Also, knowing the math property, if our result list reaches size 2,
     * we can break out of the loops immediately to save time!
     *
     * Complexity Analysis:
     * Time (O): O(N^2) - For each of the N elements, we iterate through the N array elements.
     * Space (O): O(1) - Constant auxiliary stack space.
     */
    public static List<Integer> phase2BruteForce(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int n = nums.length;
        int threshold = n / 3;

        for (int i = 0; i < n; i++) {
            // Optimization: If we already found 2 elements, we can stop.
            if (result.size() == 2) break;

            // Optimization: Skip counting if it's already in our answer list
            if (result.contains(nums[i])) continue;

            int count = 0;
            for (int j = 0; j < n; j++) {
                if (nums[j] == nums[i]) {
                    count++;
                }
            }

            if (count > threshold) {
                result.add(nums[i]);
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approaches - Hashing (Frequency Map)
     * ============================================================================
     * Detailed Intuition:
     * We can trade space for time. By utilizing a HashMap, we can count the
     * frequency of all elements in a single pass. In a second pass over the map's
     * keys, we extract those whose frequencies strictly exceed ⌊N/3⌋.
     *
     * Complexity Analysis:
     * Time (O): O(N) - One pass to populate the map, another pass to filter.
     * (Note: Technically O(N log N) worst case if there are heavy hash collisions,
     * but amortized to O(N)).
     * Space (O): O(N) - In the worst case (all elements unique), we allocate
     * N key-value pairs on the heap.
     */
    public static List<Integer> phase3AlternativeHashing(int[] nums) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        int threshold = nums.length / 3;

        // Populate frequencies
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Filter valid elements
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Running Test Suite for Majority Element (> N/3)...");
        System.out.println("----------------------------------------------------\n");

        // Format: {input_array}
        // Expected results are determined by the logical execution,
        // we will manually check them in the console output.
        int[][] testCases = {
                {1, 2, 2, 3, 2},                     // Standard: One majority element
                {11, 33, 33, 11, 33, 11},            // Standard: Two majority elements
                {3, 2, 3},                           // Standard: Leetcode example
                {1},                                 // Edge case: Array of size 1
                {1, 2},                              // Edge case: Array of size 2 (both are majority)
                {1, 1, 1, 1, 1},                     // Edge case: All elements same
                {1, 2, 3, 4, 5},                     // Edge case: No majority element
                {2, 1, 1, 3, 1, 4, 5, 6}             // No majority element
        };

        boolean allPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];

            List<Integer> res1 = phase1OptimalBoyerMoore(nums);
            List<Integer> res2 = phase2BruteForce(nums);
            List<Integer> res3 = phase3AlternativeHashing(nums);

            // To compare lists regardless of order, we could sort them,
            // but for these approaches, the order usually matches or is easily verifiable.
            // A strict equals check requires elements in same order or manual verification.
            // For robustness, we check if sizes match and res1 contains all from res2.
            boolean pass = (res1.size() == res2.size() &&
                    res1.containsAll(res2) &&
                    res3.size() == res1.size() &&
                    res3.containsAll(res1));

            System.out.printf("Test %d: Input: %s\n", i + 1, java.util.Arrays.toString(nums));
            System.out.printf("  Optimal (Boyer-Moore) : %s\n", res1.toString());
            System.out.printf("  Brute Force           : %s\n", res2.toString());
            System.out.printf("  Hashing Approach      : %s\n", res3.toString());
            System.out.printf("  Status: %s\n\n", pass ? "✅ PASS" : "❌ FAIL");

            if (!pass) allPassed = false;
        }

        System.out.println("----------------------------------------------------");
        System.out.println(allPassed ? "🎉 ALL TESTS PASSED!" : "⚠️ SOME TESTS FAILED.");
    }
}