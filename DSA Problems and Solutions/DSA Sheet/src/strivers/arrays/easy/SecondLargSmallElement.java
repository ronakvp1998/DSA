package strivers.arrays.easy;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: SECOND SMALLEST AND SECOND LARGEST ELEMENT IN AN ARRAY
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * *
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array of integers, find the second smallest and second largest
 * element in the array. Print '-1' in the event that either of them doesn't exist.
 * * * Constraints:
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * * Input/Output Formats:
 * Input: An integer array `arr`.
 * Output: An array or list of two integers representing [second_smallest, second_largest].
 * If either does not exist, return -1 for that respective position.
 * * * Example 1:
 * Input: arr = [1, 2, 4, 7, 7, 5]
 * Output: [2, 5]
 * Explanation: The smallest is 1 and largest is 7. The second smallest is 2,
 * and the second largest is 5.
 * * * Example 2:
 * Input: arr = [1]
 * Output: [-1, -1]
 * Explanation: Since there is only one element, there is no second smallest
 * or second largest element.
 * * * Example 3:
 * Input: arr = [5, 5, 5]
 * Output: [-1, -1]
 * Explanation: All elements are the same, so there are no distinct second
 * order elements.
 * * * Conceptual Visualization:
 * As we iterate through the array, we maintain two distinct "top 2" podiums:
 * one for the smallest elements and one for the largest elements.
 * If a new element is better than the gold medalist, the current gold
 * drops to silver, and the new element takes gold. If it's only better
 * than the silver medalist (and not equal to gold), it directly takes silver.
 * ============================================================================
 */
public class SecondLargSmallElement {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Single Pass Optimal)
     * ========================================================================
     * Approach and Steps:
     * 1. Check edge cases (array size < 2). Return [-1, -1] immediately.
     * 2. Initialize four variables: `small` and `secondSmall` to infinity,
     * `large` and `secondLarge` to negative infinity.
     * 3. Do a single traversal of the array:
     * a. If current element < `small`: Update `secondSmall` = `small`,
     * then `small` = current element.
     * b. Else if current element < `secondSmall` AND current element != `small`:
     * Update `secondSmall` = current element.
     * c. If current element > `large`: Update `secondLarge` = `large`,
     * then `large` = current element.
     * d. Else if current element > `secondLarge` AND current element != `large`:
     * Update `secondLarge` = current element.
     * 4. Check if `secondSmall` or `secondLarge` were never updated (still at
     * infinity bounds). If so, replace them with -1.
     * 5. Return the findings.
     * * * Detailed Intuition:
     * We can find both the second smallest and second largest in a single linear
     * scan by keeping track of the top two extremes on both ends simultaneously.
     * The critical logical check is ensuring we only update the "second" variable
     * if the new element is strictly distinct from the primary extreme, handling
     * duplicates flawlessly.
     * * * Complexity Analysis:
     * - Time (O): O(N). We traverse the array exactly once, doing constant time checks.
     * - Space (O): O(1) auxiliary heap space. Only 4 primitive tracking variables are used.
     * ========================================================================
     */
    public static int[] getSecondOrderElementsOptimal(int[] arr) {
        if (arr == null || arr.length < 2) {
            return new int[]{-1, -1};
        }

        int small = Integer.MAX_VALUE;
        int secondSmall = Integer.MAX_VALUE;
        int large = Integer.MIN_VALUE;
        int secondLarge = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            int current = arr[i];

            // Finding smallest and second smallest
            if (current < small) {
                secondSmall = small;
                small = current;
            } else if (current < secondSmall && current != small) {
                secondSmall = current;
            }

            // Finding largest and second largest
            if (current > large) {
                secondLarge = large;
                large = current;
            } else if (current > secondLarge && current != large) {
                secondLarge = current;
            }
        }

        // Handle cases where all elements were the same
        if (secondSmall == Integer.MAX_VALUE) secondSmall = -1;
        if (secondLarge == Integer.MIN_VALUE) secondLarge = -1;

        return new int[]{secondSmall, secondLarge};
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage (Sorting)
     * ========================================================================
     * Approach and Steps:
     * 1. Check for array size < 2.
     * 2. Sort the array in ascending order.
     * 3. The smallest is at index 0. Iterate forward to find the first element
     * that is strictly greater than arr[0]. That is the `secondSmall`.
     * 4. The largest is at index N-1. Iterate backward to find the first element
     * that is strictly less than arr[N-1]. That is the `secondLarge`.
     * 5. If iterations exhaust the array without finding distinct elements,
     * they don't exist (-1).
     * * * Detailed Intuition:
     * Sorting naturally groups duplicates and establishes extreme ends. By looking
     * at the extremes and moving inward until a value change is detected, we can
     * guarantee we've found the second order elements. It is easy to implement
     * but doing O(N log N) work for an O(N) problem is computationally wasteful.
     * * * Complexity Analysis:
     * - Time (O): O(N log N). The sorting algorithm dictates the time complexity.
     * - Space (O): O(1) to O(log N) auxiliary stack space used by the underlying
     * sorting algorithm (Dual-Pivot Quicksort).
     * ========================================================================
     */
    public static int[] getSecondOrderElementsBruteForce(int[] arr) {
        if (arr == null || arr.length < 2) {
            return new int[]{-1, -1};
        }

        int[] sortedArr = arr.clone(); // Clone to preserve original array state
        Arrays.sort(sortedArr);

        int n = sortedArr.length;
        int secondSmall = -1;
        int secondLarge = -1;

        // Find second smallest
        for (int i = 1; i < n; i++) {
            if (sortedArr[i] != sortedArr[0]) {
                secondSmall = sortedArr[i];
                break;
            }
        }

        // Find second largest
        for (int i = n - 2; i >= 0; i--) {
            if (sortedArr[i] != sortedArr[n - 1]) {
                secondLarge = sortedArr[i];
                break;
            }
        }

        return new int[]{secondSmall, secondLarge};
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Two Passes)
     * ========================================================================
     * Approach and Steps:
     * 1. Check array size.
     * 2. Pass 1: Iterate through the array to find the absolute `min` and `max`.
     * 3. Pass 2: Iterate again. Find the smallest element that is strictly greater
     * than `min` (this becomes `secondSmall`), and the largest element strictly
     * less than `max` (this becomes `secondLarge`).
     * 4. Return results (replacing infinity bounds with -1 if needed).
     * * * Detailed Intuition:
     * This breaks the problem into two much simpler logical steps. First, establish
     * the absolute boundaries. Second, find the extremes while completely ignoring
     * those boundaries. It is very readable and avoids the complex compound `if`
     * logic of the single-pass optimal approach.
     * * * Complexity Analysis:
     * - Time (O): O(2N) ~ O(N). We traverse the array exactly twice.
     * - Space (O): O(1) auxiliary space.
     * ========================================================================
     */
    public static int[] getSecondOrderElementsAlternative(int[] arr) {
        if (arr == null || arr.length < 2) {
            return new int[]{-1, -1};
        }

        int small = Integer.MAX_VALUE;
        int large = Integer.MIN_VALUE;

        // Pass 1: Find absolute min and max
        for (int i = 0; i < arr.length; i++) {
            small = Math.min(small, arr[i]);
            large = Math.max(large, arr[i]);
        }

        int secondSmall = Integer.MAX_VALUE;
        int secondLarge = Integer.MIN_VALUE;

        // Pass 2: Find second extremes ignoring absolute extremes
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > small && arr[i] < secondSmall) {
                secondSmall = arr[i];
            }
            if (arr[i] < large && arr[i] > secondLarge) {
                secondLarge = arr[i];
            }
        }

        if (secondSmall == Integer.MAX_VALUE) secondSmall = -1;
        if (secondLarge == Integer.MIN_VALUE) secondLarge = -1;

        return new int[]{secondSmall, secondLarge};
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * cases, duplicate elements, identical arrays, and single-element bounds.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== SECOND ORDER ELEMENTS TESTING SUITE ===\n");

        int[][] testCases = {
                {1, 2, 4, 7, 7, 5},            // Standard Case
                {1, 2, 4, 6, 7, 5},            // All unique
                {1, 1, 1, 1, 1},               // All identical
                {5},                           // Single element
                {10, 10, 5},                   // Duplicates at extreme
                {-10, -40, -20, -5, -5},       // Negative numbers
                {2, 1}                         // Exactly two elements
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Test Brute Force (Sorting)
            long start1 = System.nanoTime();
            int[] res1 = getSecondOrderElementsBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + Arrays.toString(res1) + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // Test Alternative (Two Passes)
            long start2 = System.nanoTime();
            int[] res2 = getSecondOrderElementsAlternative(tc);
            long end2 = System.nanoTime();
            System.out.println("Alternative Output: " + Arrays.toString(res2) + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Test Optimal (Single Pass)
            long start3 = System.nanoTime();
            int[] res3 = getSecondOrderElementsOptimal(tc);
            long end3 = System.nanoTime();
            System.out.println("Optimal Output:     " + Arrays.toString(res3) + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = Arrays.equals(res1, res2) && Arrays.equals(res2, res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(50) + "\n");
        }
    }
}