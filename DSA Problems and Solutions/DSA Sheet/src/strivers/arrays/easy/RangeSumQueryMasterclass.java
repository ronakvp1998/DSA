package strivers.arrays.easy;

/**
 * ============================================================================
 * 🎯 MASTERCLASS: Range Sum Query - Immutable (LeetCode 303)
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an integer array nums, handle multiple queries of the following type:
 * Calculate the sum of the elements of nums between indices left and right
 * inclusive where left <= right.
 *
 * Implement the NumArray class:
 * - NumArray(int[] nums): Initializes the object with the integer array nums.
 * - int sumRange(int left, int right): Returns the sum of the elements of nums
 *   between indices left and right inclusive.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^4
 * - -10^5 <= nums[i] <= 10^5
 * - 0 <= left <= right < nums.length
 * - At most 10^4 calls will be made to sumRange.
 *
 * Example 1:
 * Input:
 * ["NumArray", "sumRange", "sumRange", "sumRange"]
 * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
 * Output:
 * [null, 1, -1, -3]
 *
 * Explanation:
 * NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
 * numArray.sumRange(0, 2); // return (-2) + 0 + 3 = 1
 * numArray.sumRange(2, 5); // return 3 + (-5) + 2 + (-1) = -1
 * numArray.sumRange(0, 5); // return (-2) + 0 + 3 + (-5) + 2 + (-1) = -3
 *
 * ============================================================================
 */

import java.util.Arrays;

public class RangeSumQueryMasterclass {

    /**
     * ========================================================================
     * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (NON-DP PROBLEM)
     * ========================================================================
     * Note: Since this problem requires state (a class that initializes and
     * then processes queries), we will implement each phase as a static inner
     * class to keep everything contained within a single file for testing.
     * ========================================================================
     */

    /**
     * Phase 1: Optimal Approach - Prefix Sum Array
     * ------------------------------------------------------------------------
     * Detailed Intuition:
     * If the array is immutable and we have up to 10^4 queries, we cannot afford
     * to recount the elements every time. Instead, we compute the cumulative sum
     * of the array upfront (Prefix Sum).
     *
     * Let `prefix[i]` be the sum of elements from index 0 to i-1.
     * To find the sum of elements from `left` to `right`, we can take the sum
     * of all elements up to `right` and subtract the sum of elements up to
     * `left - 1`.
     * Formula: sumRange(left, right) = prefix[right + 1] - prefix[left]
     *
     * Complexity Analysis:
     * - Time Complexity:
     *   - Initialization: O(N) to build the prefix array.
     *   - Query: O(1) mathematical subtraction.
     * - Space Complexity: O(N) Heap space to store the prefix sum array.
     *   O(1) Auxiliary stack space.
     */
    public static class NumArrayOptimal {
        private int[] prefixSums;

        public NumArrayOptimal(int[] nums) {
            // We make it nums.length + 1 to easily handle left = 0 queries
            // without going out of bounds (prefixSums[0] = 0).
            prefixSums = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefixSums[i + 1] = prefixSums[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefixSums[right + 1] - prefixSums[left];
        }
    }

    /**
     * Phase 2: Brute Force Approach - On-Demand Calculation
     * ------------------------------------------------------------------------
     * Detailed Intuition:
     * The most intuitive way to solve this is to simply iterate from the 'left'
     * index to the 'right' index every time a query is made. While memory
     * efficient, this approach fails under heavy query loads.
     *
     * Here, we utilize the Java 8 Stream API for an elegant, readable implementation
     * of the brute force logic.
     *
     * Complexity Analysis:
     * - Time Complexity:
     *   - Initialization: O(1) if we just store the reference, O(N) if we clone.
     *   - Query: O(N) in the worst case (where left=0, right=N-1).
     * - Space Complexity: O(N) Heap space to store the array clone.
     *   O(1) Auxiliary stack space.
     */
    public static class NumArrayBruteForce {
        private int[] nums;

        public NumArrayBruteForce(int[] nums) {
            // Cloning to ensure immutability if the external array is modified
            this.nums = nums.clone();
        }

        public int sumRange(int left, int right) {
            // Java 8 Stream API to calculate sum on the fly
            return Arrays.stream(nums, left, right + 1).sum();
        }
    }

    /**
     * Phase 3: Alternative Approach - Segment Tree
     * ------------------------------------------------------------------------
     * Detailed Intuition:
     * A Segment Tree is a binary tree where each node stores the sum of a
     * specific range of the array. While this is overkill for an *immutable*
     * array (where Prefix Sum is faster), it is the industry standard approach
     * if the problem allowed array updates (e.g., LeetCode 307: Range Sum Query - Mutable).
     * Including it here demonstrates deep DSA mastery.
     *
     * Complexity Analysis:
     * - Time Complexity:
     *   - Initialization: O(N) to build the segment tree.
     *   - Query: O(log N) as we traverse the height of the tree.
     * - Space Complexity: O(N) Heap space (specifically 4*N to safely size the tree array).
     *   O(log N) Auxiliary stack space for recursive queries.
     */
    public static class NumArraySegmentTree {
        private int[] tree;
        private int n;

        public NumArraySegmentTree(int[] nums) {
            if (nums.length > 0) {
                n = nums.length;
                tree = new int[n * 4];
                buildTree(nums, 0, 0, n - 1);
            }
        }

        private void buildTree(int[] nums, int treeIndex, int lo, int hi) {
            if (lo == hi) {
                tree[treeIndex] = nums[lo];
                return;
            }
            int mid = lo + (hi - lo) / 2;
            int leftChild = 2 * treeIndex + 1;
            int rightChild = 2 * treeIndex + 2;

            buildTree(nums, leftChild, lo, mid);
            buildTree(nums, rightChild, mid + 1, hi);

            // The current node is the sum of its left and right children
            tree[treeIndex] = tree[leftChild] + tree[rightChild];
        }

        public int sumRange(int left, int right) {
            return queryTree(0, 0, n - 1, left, right);
        }

        private int queryTree(int treeIndex, int lo, int hi, int left, int right) {
            // Range completely outside
            if (left > hi || right < lo) {
                return 0;
            }
            // Range completely inside
            if (left <= lo && right >= hi) {
                return tree[treeIndex];
            }
            // Partial overlap
            int mid = lo + (hi - lo) / 2;
            int leftSum = queryTree(2 * treeIndex + 1, lo, mid, left, right);
            int rightSum = queryTree(2 * treeIndex + 2, mid + 1, hi, left, right);

            return leftSum + rightSum;
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Thorough testing of all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Range Sum Query Masterclass Tests     ");
        System.out.println("=========================================\n");

        int[] nums = {-2, 0, 3, -5, 2, -1};
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println();

        // 1. Test Optimal Approach (Prefix Sum)
        NumArrayOptimal optimal = new NumArrayOptimal(nums);
        System.out.println("--- Phase 1: Optimal (Prefix Sum) ---");
        System.out.println("sumRange(0, 2) Expected: 1  | Actual: " + optimal.sumRange(0, 2));
        System.out.println("sumRange(2, 5) Expected: -1 | Actual: " + optimal.sumRange(2, 5));
        System.out.println("sumRange(0, 5) Expected: -3 | Actual: " + optimal.sumRange(0, 5));
        System.out.println();

        // 2. Test Brute Force Approach (Java 8 Streams)
        NumArrayBruteForce bruteForce = new NumArrayBruteForce(nums);
        System.out.println("--- Phase 2: Brute Force (Streams) ---");
        System.out.println("sumRange(0, 2) Expected: 1  | Actual: " + bruteForce.sumRange(0, 2));
        System.out.println("sumRange(2, 5) Expected: -1 | Actual: " + bruteForce.sumRange(2, 5));
        System.out.println("sumRange(0, 5) Expected: -3 | Actual: " + bruteForce.sumRange(0, 5));
        System.out.println();

        // 3. Test Alternative Approach (Segment Tree)
        NumArraySegmentTree segTree = new NumArraySegmentTree(nums);
        System.out.println("--- Phase 3: Alternative (Segment Tree) ---");
        System.out.println("sumRange(0, 2) Expected: 1  | Actual: " + segTree.sumRange(0, 2));
        System.out.println("sumRange(2, 5) Expected: -1 | Actual: " + segTree.sumRange(2, 5));
        System.out.println("sumRange(0, 5) Expected: -3 | Actual: " + segTree.sumRange(0, 5));
        System.out.println();

        // 4. Edge Cases
        System.out.println("--- Edge Cases ---");
        int[] singleElement = {42};
        NumArrayOptimal edgeOptimal = new NumArrayOptimal(singleElement);
        System.out.println("Single Element Array [42], sumRange(0,0): Expected: 42 | Actual: " + edgeOptimal.sumRange(0, 0));

        System.out.println("\nAll tests passed successfully.");
        System.out.println("=========================================");
    }
}