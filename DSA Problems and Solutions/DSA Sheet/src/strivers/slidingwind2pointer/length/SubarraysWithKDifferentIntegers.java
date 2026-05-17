package com.questions.strivers.slidingwind2pointer.length;

/**
 * ============================================================================
 * 🤖 Senior DSA Interviewer & Competitive Programming Evaluator
 * Masterclass Solution: 992. Subarrays with K Different Integers
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 *
 * Problem Statement:
 * Given an integer array `nums` and an integer `k`, return the number of
 * good subarrays of `nums`.
 * A good array is an array where the number of different integers in that
 * array is exactly `k`.
 *
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 * Input: nums = [1,2,1,2,3], k = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers:
 * [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
 *
 * Example 2:
 * Input: nums = [1,2,1,3,4], k = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers:
 * [1,2,1,3], [2,1,3], [1,3,4].
 *
 * Constraints:
 * - 1 <= nums.length <= 2 * 10^4
 * - 1 <= nums[i], k <= nums.length
 *
 * Conceptual Approach (Non-DP problem):
 * This problem fundamentally relies on the "Sliding Window" technique.
 * Counting subarrays with *exactly* K distinct elements directly using a
 * single sliding window is tricky because shrinking the window doesn't always
 * reduce the distinct count immediately.
 *
 * The mathematical trick to simplify this is:
 * Exactly(K) = AtMost(K) - AtMost(K - 1)
 *
 * We will structure the implementation progressively below.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SubarraysWithKDifferentIntegers {

    /**
     * ============================================================================
     * Phase 1: Optimal Approach - Sliding Window with Math Transformation
     * The "Best & Recommended" Stage
     * ============================================================================
     *
     * Detailed Intuition:
     * Counting subarrays with *at most* K distinct integers is a standard
     * sliding window problem. As we expand our window to the right, if our
     * window contains `X` valid distinct integers where `X <= K`, every subarray
     * ending at `right` and starting anywhere from `left` to `right` is valid.
     * The number of such subarrays is exactly `right - left + 1`.
     * By calculating the answer for "At Most K" and subtracting the answer for
     * "At Most K-1", we mathematically filter out all subarrays that had strictly
     * less than K distinct integers, leaving only those with exactly K.
     *
     * Complexity Analysis:
     * - Time: O(N) where N is the length of nums. We do two passes (one for K,
     *   one for K-1). In each pass, left and right pointers only move forward,
     *   meaning each element is processed at most twice.
     * - Space: O(N) (Heap space) for the frequency array. No auxiliary stack space.
     *   Since nums[i] <= nums.length, we can use an integer array instead of a Map
     *   for O(1) constant time lookups without hashing overhead.
     */
    public int subarraysWithKDistinctOptimal(int[] nums, int k) {
        return atMostKDistinct(nums, k) - atMostKDistinct(nums, k - 1);
    }

    private int atMostKDistinct(int[] nums, int k) {
        if (k == 0) return 0;

        int n = nums.length;
        // Frequency array. Constraints say 1 <= nums[i] <= n.
        int[] freq = new int[n + 1];
        int left = 0;
        int distinctCount = 0;
        int totalSubarrays = 0;

        for (int right = 0; right < n; right++) {
            if (freq[nums[right]] == 0) {
                distinctCount++;
            }
            freq[nums[right]]++;

            // Shrink window if we exceed allowed distinct integers
            while (distinctCount > k) {
                freq[nums[left]]--;
                if (freq[nums[left]] == 0) {
                    distinctCount--;
                }
                left++;
            }

            // All subarrays ending at 'right' and starting between 'left' and 'right' are valid
            totalSubarrays += (right - left + 1);
        }

        return totalSubarrays;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach
     * The "Think it" Stage
     * ============================================================================
     *
     * Detailed Intuition:
     * To find all valid subarrays, we literally generate every possible contiguous
     * subarray `nums[i...j]`. For each subarray, we keep track of the unique
     * elements. If the count of unique elements reaches `k`, it's a valid "good"
     * subarray. If it exceeds `k`, we can immediately stop exploring further
     * elements for the current starting index `i`, as adding more elements will
     * never decrease the distinct count.
     *
     * Complexity Analysis:
     * - Time: O(N^2) in the worst case. We have an outer loop N times and an
     *   inner loop up to N times.
     * - Space: O(N) (Heap space) for the boolean array tracking seen elements
     *   per starting index.
     */
    public int subarraysWithKDistinctBruteForce(int[] nums, int k) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // Reinitialize tracking for each new starting point
            boolean[] seen = new boolean[n + 1];
            int distinct = 0;

            for (int j = i; j < n; j++) {
                if (!seen[nums[j]]) {
                    seen[nums[j]] = true;
                    distinct++;
                }

                if (distinct == k) {
                    count++;
                } else if (distinct > k) {
                    // Optimization: Once we exceed K distincts, further elements won't help
                    break;
                }
            }
        }
        return count;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - One-Pass Sliding Window
     * The "Flex your knowledge" Stage
     * ============================================================================
     *
     * Detailed Intuition:
     * Instead of doing two separate passes (AtMost K and AtMost K-1), we can
     * maintain a single sliding window but track the "prefix of redundant elements".
     * If a window has exactly K distinct elements, we can shrink it from the left
     * as long as the element we remove appears again further right in the window.
     * The number of times we can safely shrink the window without dropping below
     * K distinct elements is our "prefix count". Every time we expand to the right
     * and maintain K distincts, we add `1 + prefix` to our answer.
     *
     * Complexity Analysis:
     * - Time: O(N). Both left and right pointers traverse the array strictly left
     *   to right once.
     * - Space: O(N) (Heap space) for the frequency array.
     */
    public int subarraysWithKDistinctOnePass(int[] nums, int k) {
        int n = nums.length;
        int[] freq = new int[n + 1];
        int left = 0, right = 0;
        int distinctCount = 0, prefixValidCount = 0, totalGoodSubarrays = 0;

        while (right < n) {
            if (freq[nums[right]] == 0) {
                distinctCount++;
            }
            freq[nums[right]]++;

            // If distinct exceeds k, we MUST shrink from left to restore validity.
            // When this happens, our previously calculated valid prefix is reset.
            if (distinctCount > k) {
                freq[nums[left]]--;
                left++;
                distinctCount--;
                prefixValidCount = 0;
            }

            // If we have exactly K distincts, check if the left-most element is redundant
            // If its frequency > 1, removing it won't change the distinct count.
            while (freq[nums[left]] > 1) {
                prefixValidCount++;
                freq[nums[left]]--;
                left++;
            }

            // If we have achieved exactly K distinct elements,
            // the valid subarrays ending at 'right' are the current minimal window
            // plus all the redundant prefixes we collapsed.
            if (distinctCount == k) {
                totalGoodSubarrays += (1 + prefixValidCount);
            }

            right++;
        }

        return totalGoodSubarrays;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * Validates all phases against test cases to ensure structural integrity
     * and algorithmic accuracy.
     * ============================================================================
     */
    public static void main(String[] args) {
        SubarraysWithKDifferentIntegers solution = new SubarraysWithKDifferentIntegers();

        // Standard and Edge Test Cases
        int[][][] testCases = {
                {{1, 2, 1, 2, 3}, {2}},      // Standard Example 1 (Output: 7)
                {{1, 2, 1, 3, 4}, {3}},      // Standard Example 2 (Output: 3)
                {{1, 1, 1, 1, 1}, {1}},      // All identical elements (Output: 15)
                {{1, 2, 3, 4, 5}, {1}},      // All distinct, k=1 (Output: 5)
                {{1, 2, 3, 4, 5}, {5}},      // All distinct, k=length (Output: 1)
                {{1, 2}, {3}}                // K > unique elements (Output: 0)
        };

        System.out.println("🚀 Running Test Suite: 992. Subarrays with K Different Integers\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int k = testCases[i][1][0];

            // Utilizing Java 8 Stream API to format the array for readable output
            String arrayStr = Arrays.stream(nums)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(", ", "[", "]"));

            int optimalRes = solution.subarraysWithKDistinctOptimal(nums, k);
            int bruteRes = solution.subarraysWithKDistinctBruteForce(nums, k);
            int onePassRes = solution.subarraysWithKDistinctOnePass(nums, k);

            boolean passed = (optimalRes == bruteRes) && (optimalRes == onePassRes);

            System.out.printf("Test Case %d:\n", i + 1);
            System.out.printf("Input: nums = %s, k = %d\n", arrayStr, k);
            System.out.printf("Results  => Optimal: %d | One-Pass: %d | BruteForce: %d\n",
                    optimalRes, onePassRes, bruteRes);
            System.out.printf("Status   => %s\n", passed ? "✅ PASS" : "❌ FAIL");
            System.out.println("-".repeat(60));
        }
    }
}