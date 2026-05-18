package strivers.slidingwind2pointer.sum;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM: 1423. Maximum Points You Can Obtain from Cards (Medium)
 *
 * --- HEADER & PROBLEM CONTEXT ---
 * There are several cards arranged in a row, and each card has an associated
 * number of points. The points are given in the integer array `cardPoints`.
 * In one step, you can take one card from the beginning or from the end of the
 * row. You have to take exactly `k` cards.
 * Your score is the sum of the points of the cards you have taken.
 * Given the integer array `cardPoints` and the integer `k`, return the maximum
 * score you can obtain.
 *
 * Example 1:
 * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
 * Output: 12
 * Explanation: After the first step, your score will always be 1. However,
 * choosing the rightmost card first will maximize your total score. The optimal
 * strategy is to take the three cards on the right, giving a final score of
 * 1 + 6 + 5 = 12.
 *
 * Example 2:
 * Input: cardPoints = [2,2,2], k = 2
 * Output: 4
 * Explanation: Regardless of which two cards you take, your score will always be 4.
 *
 * Example 3:
 * Input: cardPoints = [9,7,7,9,7,7,9], k = 7
 * Output: 55
 * Explanation: You have to take all the cards. Your score is the sum of points
 * of all cards.
 *
 * Constraints:
 * 1 <= cardPoints.length <= 10^5
 * 1 <= cardPoints[i] <= 10^4
 * 1 <= k <= cardPoints.length
 * ============================================================================
 * NOTE ON PROGRESSION:
 * This is an Array / Sliding Window problem (Non-DP). We will follow
 * the Non-DP Progressive Implementation Roadmap.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class MaximumPointsFromCards {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window from Ends)
     * ============================================================================
     * Detailed Intuition:
     * We need to select exactly `k` cards from either the left or right ends.
     * This means any valid selection will consist of some `i` cards from the
     * left and `k - i` cards from the right (where 0 <= i <= k).
     *
     * We can compute this dynamically without recalculating from scratch.
     * First, we assume we take all `k` cards from the left. This is our baseline sum.
     * Next, we slide our "window" of selection: we drop the rightmost card from
     * our left selection, and pick up one card from the far right end of the array.
     * We repeat this until we've evaluated the scenario of taking all `k` cards
     * from the right. We track the maximum sum encountered during this slide.
     *
     * Complexity Analysis:
     * Time Complexity: O(k). We initialize the sum in O(k) operations, and then
     * perform a single loop of size `k` to slide the selection.
     * Space Complexity: O(1) Auxiliary Space. We only maintain a few integer
     * variables (`leftSum`, `rightSum`, `maxSum`), using stack space only.
     */
    public int maxScoreOptimal(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int leftSum = 0;
        int rightSum = 0;

        // Baseline: Take all k cards from the left
        for (int i = 0; i < k; i++) {
            leftSum += cardPoints[i];
        }

        int maxSum = leftSum;

        // Slide the window: drop one from the left, add one from the right
        int rightIndex = n - 1;
        for (int leftIndex = k - 1; leftIndex >= 0; leftIndex--) {
            leftSum -= cardPoints[leftIndex];
            rightSum += cardPoints[rightIndex];

            maxSum = Math.max(maxSum, leftSum + rightSum);

            rightIndex--;
        }

        return maxSum;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Recursive Decision Tree)
     * ============================================================================
     * Detailed Intuition:
     * The "Think it" stage. At any point, we have two choices: take the leftmost
     * available card OR take the rightmost available card.
     * We can simulate this process using recursion. We ask our recursive function
     * to return the maximum score possible after picking `k` cards from the
     * current `[left, right]` bounds of the array.
     *
     * Complexity Analysis:
     * Time Complexity: O(2^k). At each step, we branch into 2 possible decisions
     * (left or right). The recursion goes `k` levels deep. This will Time Out (TLE)
     * for large `k`.
     * Space Complexity: O(k) Auxiliary Stack Space. The maximum depth of the
     * recursion tree is `k`.
     */
    public int maxScoreBruteForce(int[] cardPoints, int k) {
        return solveBrute(cardPoints, k, 0, cardPoints.length - 1);
    }

    private int solveBrute(int[] cardPoints, int k, int left, int right) {
        // Base Case: If we have no more cards to pick, we gain 0 points.
        if (k == 0) {
            return 0;
        }

        // Option 1: Take the card on the left
        int takeLeft = cardPoints[left] + solveBrute(cardPoints, k - 1, left + 1, right);

        // Option 2: Take the card on the right
        int takeRight = cardPoints[right] + solveBrute(cardPoints, k - 1, left, right - 1);

        return Math.max(takeLeft, takeRight);
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Min Subarray Sum of size N-k)
     * ============================================================================
     * Detailed Intuition:
     * We can reframe the problem. If we take `k` cards from the outer edges of
     * an array of size `N`, we are leaving behind exactly `N - k` continuous
     * cards in the middle of the array.
     *
     * To maximize the sum of the `k` selected cards, we must *minimize* the sum
     * of the `N - k` leftover cards.
     * We can find the minimum continuous subarray of size `N - k` using a
     * standard fixed-size sliding window. Finally, subtract this minimum sum
     * from the total sum of the array.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). We traverse the entire array once to find the
     * total sum, and then traverse it again with our sliding window. O(2N) -> O(N).
     * Space Complexity: O(1) Auxiliary Space. We only use primitive variables.
     */
    public int maxScoreAlternative(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int windowSize = n - k;

        int totalSum = 0;
        for (int point : cardPoints) {
            totalSum += point;
        }

        // If we must take all cards, the answer is just the total sum
        if (k == n) return totalSum;

        // Find the sum of the first window of size (N - k)
        int currentWindowSum = 0;
        for (int i = 0; i < windowSize; i++) {
            currentWindowSum += cardPoints[i];
        }

        int minWindowSum = currentWindowSum;

        // Slide the window across the rest of the array
        for (int i = windowSize; i < n; i++) {
            currentWindowSum += cardPoints[i];              // Add the new element entering the window
            currentWindowSum -= cardPoints[i - windowSize]; // Remove the oldest element leaving the window

            minWindowSum = Math.min(minWindowSum, currentWindowSum);
        }

        return totalSum - minWindowSum;
    }

    /**
     * ============================================================================
     * SECTION 4: TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        MaximumPointsFromCards solver = new MaximumPointsFromCards();

        // Object array format: {int[] cardPoints, int k, int expectedResult}
        Object[][] testCases = {
                {new int[]{1, 2, 3, 4, 5, 6, 1}, 3, 12},  // Example 1 (Standard)
                {new int[]{2, 2, 2}, 2, 4},               // Example 2 (Uniform values)
                {new int[]{9, 7, 7, 9, 7, 7, 9}, 7, 55},  // Example 3 (k = N)
                {new int[]{1, 1000, 1}, 1, 1},            // Edge Case: Can't reach the middle high value
                {new int[]{1, 79, 80, 1, 1, 1, 200, 1}, 3, 202} // Must pick mix of left and right
        };

        System.out.println("🚀 Running Tests for LeetCode 1423: Maximum Points You Can Obtain from Cards...\n");

        // Java 8 Stream API to process and evaluate test cases
        IntStream.range(0, testCases.length).forEach(i -> {
            int[] cardPoints = (int[]) testCases[i][0];
            int k = (int) testCases[i][1];
            int expected = (int) testCases[i][2];

            int resOptimal = solver.maxScoreOptimal(cardPoints, k);
            int resAlternative = solver.maxScoreAlternative(cardPoints, k);

            // To prevent TLE on huge arrays, we only run Brute Force if k is small enough (< 20)
            int resBrute = (k <= 20) ? solver.maxScoreBruteForce(cardPoints, k) : resOptimal;

            boolean passed = (resOptimal == expected) &&
                    (resBrute == expected) &&
                    (resAlternative == expected);

            System.out.printf("Test %d: k = %d, cardPoints = %s\n", i + 1, k, Arrays.toString(cardPoints));
            System.out.printf("   Expected    : %d\n", expected);

            if (k <= 20) {
                System.out.printf("   Optimal     : %d | Brute: %d | Alternative: %d\n", resOptimal, resBrute, resAlternative);
            } else {
                System.out.printf("   Optimal     : %d | Brute: SKIPPED (k too large) | Alternative: %d\n", resOptimal, resAlternative);
            }

            System.out.printf("   Result      : %s\n", passed ? "✅ PASS" : "❌ FAIL");
            System.out.println("---------------------------------------------------------");
        });
    }
}

//
///*
//Problem Statement: Maximum Points You Can Obtain from Cards
//
//There are several cards arranged in a row, and each card has an associated number of points.
//You can take exactly k cards from either the beginning or the end of the array.
//Return the maximum score you can obtain.
//
//Examples:
//1. Input: cardPoints = [1,2,3,4,5,6,1], k = 3 → Output: 12
//   Explanation: Best choice is taking [6,5,1] → sum = 12.
//
//2. Input: cardPoints = [2,2,2], k = 2 → Output: 4
//   Explanation: Any two cards = 2 + 2 = 4.
//
//3. Input: cardPoints = [9,7,7,9,7,7,9], k = 7 → Output: 55
//   Explanation: Must take all cards = total sum.
//*/
//
//public class MaximumPointsCards {
//
//    public static void main(String[] args) {
//        int arr[] = {6,2,3,4,7,2,1,7,1};
//        int k = 4;
//
//        // Two different implementations that solve the same problem
//        System.out.println(maxSum1(arr, k)); // Approach 1
//        System.out.println(maxSum2(arr, k)); // Approach 2
//    }
//
//    /**
//     * Approach 1: Sliding Window Technique
//     * -------------------------------------
//     * 1. Start by taking the first k elements (all from the left).
//     * 2. Then, gradually replace one element from the left side with one from the right side.
//     * 3. Update the sum in each iteration and track the maximum.
//     *
//     * Example:
//     * arr = [6,2,3,4,7,2,1,7,1], k=4
//     * Step 1: Take [6,2,3,4] → sum = 15
//     * Step 2: Replace 4 with last element 1 → sum = 12
//     * Step 3: Replace 3 with 7 → sum = 16
//     * Step 4: Replace 2 with 1 → sum = 15
//     * Step 5: Replace 6 with 7 → sum = 21 (max)
//     *
//     * Time Complexity: O(k) → We check k possible splits.
//     * Space Complexity: O(1) → Only variables used.
//     */
//    private static int maxSum1(int arr[], int k) {
//        int maxSum = Integer.MIN_VALUE, sum = 0, l = 0, r = 0, n = arr.length;
//
//        // Step 1: Take first k elements from the left
//        for (int i = 0; i < k; i++) {
//            sum = sum + arr[i];
//        }
//        maxSum = sum;
//
//        // Step 2: Slide the window by replacing left elements with right elements
//        l = k - 1;
//        r = n - 1;
//        while (l >= 0) {
//            sum = sum - arr[l];  // Remove one element from left side
//            sum = sum + arr[r];  // Add one element from right side
//            l--;
//            r--;
//            maxSum = Math.max(sum, maxSum);
//        }
//        return maxSum;
//    }
//
//    /**
//     * Approach 2: Two-Pointer with Prefix-Suffix Sum
//     * ----------------------------------------------
//     * 1. First, take the sum of the first k elements (all left).
//     * 2. Then, step-by-step, remove one element from the left sum and add one element from the right sum.
//     * 3. Keep track of the maximum.
//     *
//     * Example:
//     * arr = [6,2,3,4,7,2,1,7,1], k=4
//     * LeftSum = 6+2+3+4 = 15
//     * Now swap gradually:
//     *   Take 3 from left, add 1 from right → sum = 13
//     *   Take 2 from left, add 7 from right → sum = 18
//     *   Take 6 from left, add 1 from right → sum = 15
//     *   Max = 18
//     *
//     * Time Complexity: O(k)
//     * Space Complexity: O(1)
//     */
//    private static int maxSum2(int arr[], int k) {
//        int maxSum = Integer.MIN_VALUE, lsum = 0, rsum = 0, n = arr.length;
//
//        // Step 1: Sum of first k elements (all from left)
//        for (int i = 0; i < k; i++) {
//            lsum = lsum + arr[i];
//        }
//        maxSum = lsum;
//
//        // Step 2: Swap left elements with right elements one by one
//        int rightIndex = n - 1;
//        for (int i = k - 1; i >= 0; i--) {
//            lsum = lsum - arr[i];          // Remove from left
//            rsum = rsum + arr[rightIndex]; // Add from right
//            rightIndex = rightIndex - 1;
//            maxSum = Math.max(maxSum, lsum + rsum);
//        }
//        return maxSum;
//    }
//}
