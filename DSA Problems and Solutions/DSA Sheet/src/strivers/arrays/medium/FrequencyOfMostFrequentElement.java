package strivers.arrays.medium;

import java.util.Arrays;

/**
 * ============================================================================
 * 1838. Frequency of the Most Frequent Element
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * **Problem Statement:**
 * The frequency of an element is the number of times it occurs in an array.
 * You are given an integer array nums and an integer k. In one operation, you
 * can choose an index of nums and increment the element at that index by 1.
 * * Return the maximum possible frequency of an element after performing at most
 * k operations.
 * * * **Example 1:**
 * Input: nums = [1,2,4], k = 5
 * Output: 3
 * Explanation: Increment the first element three times and the second element
 * two times to make nums = [4,4,4]. 4 has a frequency of 3.
 * * * **Example 2:**
 * Input: nums = [1,4,8,13], k = 5
 * Output: 2
 * Explanation: There are multiple optimal solutions:
 * - Increment the first element three times to make nums = [4,4,8,13]. 4 has a frequency of 2.
 * - Increment the second element four times to make nums = [1,8,8,13]. 8 has a frequency of 2.
 * - Increment the third element five times to make nums = [1,4,13,13]. 13 has a frequency of 2.
 * * * **Example 3:**
 * Input: nums = [3,9,6], k = 2
 * Output: 1
 * * * **Constraints:**
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= 10^5
 * * ============================================================================
 */
public class FrequencyOfMostFrequentElement {

    /**
     * ### Phase 1: Brute Force approach - The "Think it" stage.
     * * * **Detailed Intuition:**
     * Since we can only increment elements, the best strategy to make multiple elements
     * equal to a target element is to pick elements that are already close to it.
     * Therefore, sorting the array is the first logical step.
     * In the brute force approach, we iterate through every element, treating it as the
     * potential "most frequent" target. We then look backwards at smaller elements,
     * calculating the difference between the target and the smaller element, and subtract
     * that from `k`. We keep counting how many elements we can equalize until we run out of `k`.
     * * * **Complexity Analysis:**
     * - **Time (O):** O(N^2), where N is the length of the array. Sorting takes O(N log N),
     * but the nested loops (checking backwards for every element) take O(N^2) in the worst case.
     * - **Space (O):** O(1) auxiliary stack space, but O(log N) to O(N) heap space depending
     * on the underlying sorting algorithm used by Java's `Arrays.sort()`.
     */
    public int maxFrequencyBruteForce(int[] nums, int k) {
        Arrays.sort(nums);
        int maxFreq = 1;

        for (int i = 0; i < nums.length; i++) {
            int currentK = k;
            int currentFreq = 1;
            int target = nums[i];

            // Look backwards to see how many smaller elements we can bring up to 'target'
            for (int j = i - 1; j >= 0; j--) {
                int diff = target - nums[j];
                if (currentK >= diff) {
                    currentK -= diff;
                    currentFreq++;
                } else {
                    break; // Run out of operations for this target
                }
            }
            maxFreq = Math.max(maxFreq, currentFreq);
        }

        return maxFreq;
    }

    /**
     * ### Phase 2: Alternative Approach (Binary Search + Prefix Sums) - The "Refine it" stage.
     * * * **Detailed Intuition:**
     * Instead of linearly scanning backwards, we can use binary search. If we know our target
     * is at index `i`, we want to find the furthest index `j` (where j <= i) such that the cost
     * to make all elements from `j` to `i` equal to `nums[i]` is <= `k`.
     * The cost equation is: (Length of subarray * target) - (Sum of elements in subarray).
     * To compute the sum of the subarray in O(1) time, we precompute a Prefix Sum array.
     * * * **Complexity Analysis:**
     * - **Time (O):** O(N log N). Sorting takes O(N log N). We then iterate N times, and for
     * each element, we perform a binary search which takes O(log N) time.
     * - **Space (O):** O(N) heap space to store the `prefix` array. O(1) auxiliary stack space.
     */
    public int maxFrequencyBinarySearch(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long[] prefix = new long[n];
        prefix[0] = nums[0];

        // Build prefix sum array
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }

        int maxFreq = 1;

        for (int i = 0; i < n; i++) {
            int target = nums[i];
            int left = 0;
            int right = i;
            int bestStartIndex = i;

            // Binary search for the optimal starting point
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int count = i - mid + 1;
                long windowSum = prefix[i] - (mid > 0 ? prefix[mid - 1] : 0);
                long cost = (long) count * target - windowSum;

                if (cost <= k) {
                    bestStartIndex = mid;
                    right = mid - 1; // Try to expand the window by moving left
                } else {
                    left = mid + 1;  // Window is too big, shrink it by moving right
                }
            }
            maxFreq = Math.max(maxFreq, i - bestStartIndex + 1);
        }

        return maxFreq;
    }

    /**
     * ### Phase 3: Alternative Approach (Sliding Window / Two Pointers) - The "Perfect it" stage.
     * * * **Detailed Intuition:**
     * We can optimize further using a Sliding Window. Let `left` and `right` be the bounds of our window.
     * The target element is always `nums[right]`. As `right` expands, we add to our running `windowSum`.
     * If the cost to equalize all elements in the window to `nums[right]` exceeds `k`, the window is
     * invalid. We must shrink the window by moving `left` forward and subtracting `nums[left]` from the sum.
     * Because we are looking for the *maximum* window size, we don't strictly have to shrink the window
     * down to a valid state; we can just slide the maximum window size along if it's invalid.
     * * * **Complexity Analysis:**
     * - **Time (O):** O(N log N). Sorting dominates the time complexity. The sliding window
     * processes each element with the `left` and `right` pointers at most once, taking O(N) time.
     * - **Space (O):** O(1) auxiliary heap space (excluding the array sorting overhead). O(1) stack space.
     */
    public int maxFrequencySlidingWindow(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0;
        long windowSum = 0;
        int maxFreq = 0;

        for (int right = 0; right < nums.length; right++) {
            windowSum += nums[right];

            // Cost equation: (count of elements * target) - sum of elements
            // If cost > k, the window is invalid, shrink from the left
            while ((long) (right - left + 1) * nums[right] - windowSum > k) {
                windowSum -= nums[left];
                left++;
            }

            maxFreq = Math.max(maxFreq, right - left + 1);
        }

        return maxFreq;
    }

    /**
     * ### 4. Testing Suite
     * Thoroughly testing the implementations with standard cases and edge cases.
     */
    public static void main(String[] args) {
        FrequencyOfMostFrequentElement solver = new FrequencyOfMostFrequentElement();

        // Test Cases
        int[][] testNums = {
                {1, 2, 4},              // Standard Case 1
                {1, 4, 8, 13},          // Standard Case 2
                {3, 9, 6},              // Standard Case 3
                {10, 10, 10},           // All elements same
                {1},                    // Edge Case: Single element
                {1, 100000}             // Extreme difference, k is small
        };
        int[] testKs = {5, 5, 2, 5, 10000, 1};
        int[] expectedResults = {3, 2, 1, 3, 1, 1};

        System.out.println("=========================================================");
        System.out.println("Running Testing Suite: Frequency of Most Frequent Element");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testNums.length; i++) {
            // Arrays.sort works in-place, so we must clone for testing multiple phases safely
            int[] numsBrute = testNums[i].clone();
            int[] numsBS = testNums[i].clone();
            int[] numsSW = testNums[i].clone();
            int k = testKs[i];

            int bruteResult = solver.maxFrequencyBruteForce(numsBrute, k);
            int bsResult = solver.maxFrequencyBinarySearch(numsBS, k);
            int swResult = solver.maxFrequencySlidingWindow(numsSW, k);

            boolean passed = (bruteResult == expectedResults[i]) &&
                    (bsResult == expectedResults[i]) &&
                    (swResult == expectedResults[i]);

            System.out.println("Test Case " + (i + 1) + ": nums = " + Arrays.toString(testNums[i]) + ", k = " + k);
            System.out.println("  Expected Output : " + expectedResults[i]);
            System.out.println("  Brute Force     : " + bruteResult);
            System.out.println("  Binary Search   : " + bsResult);
            System.out.println("  Sliding Window  : " + swResult);
            System.out.println("  Status          : " + (passed ? "PASS" : "FAIL") + "\n");
        }
    }
}