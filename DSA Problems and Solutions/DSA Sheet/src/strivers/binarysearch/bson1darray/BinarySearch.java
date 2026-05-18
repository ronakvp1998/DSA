package strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement (LeetCode 704: Binary Search):
 * Given an array of integers nums which is sorted in ascending order, and an
 * integer target, write a function to search target in nums. If target exists,
 * then return its index. Otherwise, return -1.
 * * You must write an algorithm with O(log n) runtime complexity.
 * * Examples:
 * ---------
 * Example 1:
 * Input: nums = [-1,0,3,5,9,12], target = 9
 * Output: 4
 * Explanation: 9 exists in nums and its index is 4
 * * Example 2:
 * Input: nums = [-1,0,3,5,9,12], target = 2
 * Output: -1
 * Explanation: 2 does not exist in nums so return -1
 * * Constraints:
 * - 1 <= nums.length <= 10^4
 * - -10^4 < nums[i], target < 10^4
 * - All the integers in nums are unique.
 * - nums is sorted in ascending order.
 * ============================================================================
 */
public class BinarySearch {

    /**
     * ============================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Iterative Binary Search)
     * ============================================================================
     * Detailed Intuition:
     * The brute force approach checks every element, ignoring the fact that the
     * array is sorted. To optimize, we leverage the sorted property. If we check
     * the middle element and it is smaller than our target, we know with absolute
     * certainty that the target cannot be in the left half of the array. We can
     * immediately discard half the search space. We repeat this process iteratively,
     * continually narrowing our boundaries ('left' and 'right').
     * * * Note on integer overflow: Always use `left + (right - left) / 2` instead
     * of `(left + right) / 2` to prevent overflow when dealing with very large indices.
     * * Complexity Analysis:
     * - Time Complexity: O(log n). In the worst case, we halve the search space
     * until only one element is left.
     * - Space Complexity: O(1). We only use a few integer variables (left, right,
     * mid) for pointers. Zero heap space and zero auxiliary stack space allocated.
     * ============================================================================
     */
    public static int searchOptimal(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            // Prevent potential integer overflow
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid; // Target found
            } else if (nums[mid] < target) {
                left = mid + 1; // Target must be in the right half
            } else {
                right = mid - 1; // Target must be in the left half
            }
        }

        return -1; // Search space exhausted, target not found
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ============================================================================
     * Detailed Intuition:
     * Before reaching the optimal solution, the most fundamental way to find an
     * element is to look at every single item in the collection from left to right.
     * This is a standard Linear Search. It completely ignores the sorted nature of
     * the input array.
     * * Complexity Analysis:
     * - Time Complexity: O(n), where n is the length of the array. In the worst case
     * (target is at the end or not present), we inspect every element.
     * - Space Complexity: O(1). Only a loop counter is used. No extra stack or
     * heap space is required.
     * ============================================================================
     */
    public static int searchBruteForce(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Recursive Binary Search)
     * ============================================================================
     * Detailed Intuition:
     * The iterative binary search logic maps perfectly to a recursive function.
     * Instead of a while loop updating pointers, we recursively call the function
     * with updated 'left' or 'right' boundaries. While mathematically elegant,
     * in Java, iterative is preferred for this specific problem to avoid call
     * stack overhead.
     * * Complexity Analysis:
     * - Time Complexity: O(log n). We still halve the search space at each step.
     * - Space Complexity: O(log n) auxiliary stack space. Because we make a recursive
     * call for each halving step, the maximum depth of the call stack will be log(n).
     * Zero heap space allocated.
     * ============================================================================
     */
    public static int searchRecursiveWrapper(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return searchRecursiveHelper(nums, target, 0, nums.length - 1);
    }

    private static int searchRecursiveHelper(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: boundaries crossed
        }

        int mid = left + (right - left) / 2;

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return searchRecursiveHelper(nums, target, mid + 1, right);
        } else {
            return searchRecursiveHelper(nums, target, left, mid - 1);
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thoroughly testing all approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- Testing Optimal Approach (Iterative O(log n)) ---");
        runTests("OPTIMAL");

        System.out.println("\n--- Testing Alternative Approach (Recursive O(log n)) ---");
        runTests("RECURSIVE");

        System.out.println("\n--- Testing Brute Force Approach (Linear O(n)) ---");
        runTests("BRUTE_FORCE");
    }

    private static void runTests(String mode) {
        // Test Case 1: Standard Case (Target present)
        verifyAndPrint("Test 1 (Target Present)", new int[]{-1, 0, 3, 5, 9, 12}, 9, mode);

        // Test Case 2: Standard Case (Target absent)
        verifyAndPrint("Test 2 (Target Absent)", new int[]{-1, 0, 3, 5, 9, 12}, 2, mode);

        // Test Case 3: Target at the very beginning
        verifyAndPrint("Test 3 (Target at Start)", new int[]{10, 20, 30, 40}, 10, mode);

        // Test Case 4: Target at the very end
        verifyAndPrint("Test 4 (Target at End)", new int[]{10, 20, 30, 40}, 40, mode);

        // Test Case 5: Single element (Present)
        verifyAndPrint("Test 5 (Single Element Present)", new int[]{5}, 5, mode);

        // Test Case 6: Single element (Absent)
        verifyAndPrint("Test 6 (Single Element Absent)", new int[]{5}, 10, mode);

        // Test Case 7: Empty Array
        verifyAndPrint("Test 7 (Empty Array)", new int[]{}, 5, mode);
    }

    private static void verifyAndPrint(String testName, int[] arr, int target, String mode) {
        int resultIndex;

        switch (mode) {
            case "OPTIMAL":
                resultIndex = searchOptimal(arr, target);
                break;
            case "RECURSIVE":
                resultIndex = searchRecursiveWrapper(arr, target);
                break;
            case "BRUTE_FORCE":
            default:
                resultIndex = searchBruteForce(arr, target);
                break;
        }

        System.out.printf("%-35s -> Target: %-3d | Found at index: %d\n", testName, target, resultIndex);
    }
}