package strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 1539. Kth Missing Positive Number
 * Solved | Easy (but optimal approach is Medium difficulty conceptually)
 * * * PROBLEM STATEMENT:
 * Given an array arr of positive integers sorted in a strictly increasing order,
 * and an integer k.
 * Return the kth positive integer that is missing from this array.
 * * * EXAMPLES:
 * Example 1:
 * Input: arr = [2,3,4,7,11], k = 5
 * Output: 9
 * Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...].
 * The 5th missing positive integer is 9.
 * * Example 2:
 * Input: arr = [1,2,3,4], k = 2
 * Output: 6
 * Explanation: The missing positive integers are [5,6,7,...].
 * The 2nd missing positive integer is 6.
 * * * CONSTRAINTS:
 * - 1 <= arr.length <= 1000
 * - 1 <= arr[i] <= 1000
 * - 1 <= k <= 1000
 * - arr[i] < arr[j] for 1 <= i < j <= arr.length
 * * Follow up: Could you solve this problem in less than O(n) complexity?
 * ============================================================================
 */
public class KthMissingPositiveNumber {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search)
     * ========================================================================
     * * APPROACH & STEPS:
     * This problem is a staple in the Striver sheet revision path. The core
     * trick relies on the property of the sorted array to mathematically
     * deduce how many numbers are missing before any given index.
     * 1. If an array of positive integers has no missing numbers, the value
     * at any index `i` would be exactly `i + 1`. (e.g., index 0 has 1, index 1 has 2).
     * 2. Therefore, the number of missing elements strictly BEFORE index `i`
     * is exactly: `arr[i] - (i + 1)`.
     * 3. We can apply Binary Search on the indices. If the number of missing
     * elements at `mid` is less than `k`, our answer lies to the right.
     * 4. If the missing elements are greater than or equal to `k`, our answer
     * lies to the left.
     * 5. When the loop terminates, `left` will be exactly one index ahead of `right`.
     * The answer simplifies beautifully to just `left + k`.
     * * * DETAILED INTUITION:
     * Why does it simplify to `left + k`?
     * After the binary search, `right` is the index of the largest element
     * where the number of missing numbers is strictly less than `k`.
     * The missing numbers before `arr[right]` = `arr[right] - (right + 1)`.
     * We need `k - missing` MORE numbers starting from `arr[right]`.
     * So, Answer = `arr[right] + (k - (arr[right] - (right + 1)))`
     * Let's do the algebra:
     * Answer = arr[right] + k - arr[right] + right + 1
     * Answer = right + 1 + k.
     * Since `left = right + 1` when the loop exits, Answer = `left + k`.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(log N), where N is the length of the array. We are
     * halving the search space of indices at each step. This answers the
     * follow-up question directly.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative binary search).
     * - Heap Space: O(1).
     */
    public static int findKthPositiveOptimal(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Calculate how many numbers are missing before index 'mid'
            int missingNumbers = arr[mid] - (mid + 1);

            if (missingNumbers < k) {
                // We need more missing numbers, search right
                left = mid + 1;
            } else {
                // We have enough missing numbers, search left to find the exact bound
                right = mid - 1;
            }
        }

        // Mathematical simplification of: arr[right] + k - (arr[right] - (right + 1))
        return left + k;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Iterate through every element in the array.
     * 2. If the current element is less than or equal to `k`, it means this
     * present number "pushes" our target `k`th missing number one spot further.
     * So, we increment `k` by 1.
     * 3. If the current element is strictly greater than `k`, we have found our
     * target. The answer is the current value of `k`.
     * * * DETAILED INTUITION:
     * Imagine `k=5`. If the first array element is 2, it occupies one of the
     * spots that would have been a missing number. So the 5th missing number
     * is now actually the 6th integer overall. By incrementing `k` for every
     * number we see that is <= `k`, we naturally shift our target past the
     * existing numbers.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N), where N is the length of the array. We scan the
     * array at most once.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int findKthPositiveBruteForce(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= k) {
                k++;
            } else {
                break;
            }
        }
        return k;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Hashing / Visited Array:
     * We could create a boolean array or HashSet up to `arr[n-1] + k`, mark
     * all elements in `arr` as true/visited, and then iterate from 1 upwards,
     * counting the unvisited numbers until we hit `k`.
     * * Why it's suboptimal: This requires O(Max_Value + k) Time and Space
     * complexity, making it vastly inferior to both the Brute Force O(N) and
     * the optimal O(log N) approaches. It is generally not recommended in an
     * interview setting.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Thorough testing against standard cases and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Kth Missing Positive Number Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] arr1 = {2, 3, 4, 7, 11};
        int k1 = 5;
        runTestCase(1, arr1, k1, 9);

        // Test Case 2: Standard case (Example 2)
        int[] arr2 = {1, 2, 3, 4};
        int k2 = 2;
        runTestCase(2, arr2, k2, 6);

        // Test Case 3: Edge Case - Missing numbers are strictly BEFORE the array starts
        // arr starts at 5, we want the 3rd missing. (1, 2, 3 are missing). Answer should be 3.
        int[] arr3 = {5, 6, 7, 8};
        int k3 = 3;
        runTestCase(3, arr3, k3, 3);

        // Test Case 4: Edge Case - Missing numbers are strictly AFTER the array ends
        // arr has no missing numbers up to 4. We want the 3rd missing. (5, 6, 7 are missing). Answer should be 7.
        int[] arr4 = {1, 2, 3, 4};
        int k4 = 3;
        runTestCase(4, arr4, k4, 7);

        // Test Case 5: Single element array
        int[] arr5 = {2};
        int k5 = 1; // 1 is missing before 2
        runTestCase(5, arr5, k5, 1);

        // Test Case 6: Single element array, missing after
        int[] arr6 = {2};
        int k6 = 3; // missing: 1, 3, 4. Answer should be 4
        runTestCase(6, arr6, k6, 4);
    }

    private static void runTestCase(int testNumber, int[] arr, int k, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = findKthPositiveOptimal(arr, k);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: arr = " + java.util.Arrays.toString(arr) + ", k = " + k);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}