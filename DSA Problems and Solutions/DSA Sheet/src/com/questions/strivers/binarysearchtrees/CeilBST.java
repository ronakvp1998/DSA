package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 *                          PROBLEM STATEMENT
 * ============================================================================
 * Given the root of a **Binary Search Tree (BST)** and an integer value `k`,
 * find the **ceil** of `k` in the BST.
 *
 * The **ceil** of a number `k` in a BST is defined as:
 * 👉 The **smallest value in the BST that is greater than or equal to `k`**.
 *
 * If no such value exists, return `-1`.
 *
 * ---------------------------------------------------------------------------
 * Binary Search Tree (BST) Property Reminder:
 * ---------------------------------------------------------------------------
 * - Left subtree contains values smaller than the node.
 * - Right subtree contains values greater than the node.
 *
 * Using this ordering, we can efficiently find the ceil without
 * traversing the entire tree.
 *
 * ---------------------------------------------------------------------------
 * Example:
 * ---------------------------------------------------------------------------
 *              8
 *            /   \
 *           3     10
 *          / \      \
 *         1   6      14
 *            / \     /
 *           4   7   13
 *
 * k = 9
 * Ceil = 10
 * ============================================================================
 */
public class CeilBST {

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // STEP 1: Constructing a sample Binary Search Tree (BST)
        // --------------------------------------------------------------------
        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(3);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(6);
        root.right.right = new TreeNode(14);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(7);
        root.right.right.left = new TreeNode(13);

        // Value whose ceil is to be found
        int k = 9;

        // Finding and printing the ceil value
        System.out.println(ceilBT(root, k)); // Expected output: 10
    }

    /**
     * ========================================================================
     * FUNCTION: ceilBT (Iterative Approach)
     * ========================================================================
     * Finds the ceil of a given value `k` in a Binary Search Tree.
     *
     * Core Idea:
     * - Traverse the BST starting from the root.
     * - Keep track of the best possible ceil found so far.
     *
     * @param root Root of the BST
     * @param k    Target value
     * @return     Ceil value of k, or -1 if no ceil exists
     * ========================================================================
     */
    private static int ceilBT(TreeNode root, int k) {

        // --------------------------------------------------------------------
        // BUG FIX:
        // --------------------------------------------------------------------
        // Original code initialized `ceil` with 1, which is incorrect because:
        // - 1 may not exist in the BST
        // - It can produce wrong results when no ceil exists
        // Correct initialization is -1 (sentinel for "not found")
        int ceil = -1;

        // Traverse the BST until we reach a null node
        while (root != null) {

            // Case 1: Exact match found
            // The ceil of k is k itself
            if (root.val == k) {
                ceil = root.val;
                return ceil;
            }

            // Case 2: k is greater than current node value
            // Ceil must lie in the right subtree
            if (k > root.val) {
                root = root.right;
            }
            // Case 3: k is smaller than current node value
            // Current node can be a potential ceil
            else {
                ceil = root.val;  // Update ceil candidate
                root = root.left;
            }
        }

        // Return the best ceil found during traversal
        return ceil;
    }
}
/* ============================================================================
 *                      TIME & SPACE COMPLEXITY
 * ============================================================================
 * Time Complexity:
 * - Best Case:  O(1)   → Root is the ceil
 * - Average:    O(log N) for balanced BST
 * - Worst Case: O(N)   for skewed BST
 *
 * Space Complexity:
 * - O(1) → Iterative solution uses constant extra space
 * ============================================================================
 */
/* ============================================================================
 *                          APPROACH EXPLANATION
 * ============================================================================
 * 1. Start from the root of the BST.
 * 2. Maintain a variable `ceil` to store the best candidate so far.
 * 3. At each node:
 *    - If node value == k → this is the ceil.
 *    - If node value < k → move right (ceil must be larger).
 *    - If node value > k → update ceil and move left.
 * 4. Continue until the tree ends.
 *
 * Why this works:
 * - BST ordering ensures values increase as we move right.
 * - We always move closer to the smallest value ≥ k.
 *
 * When to use:
 * - When the tree is a valid BST.
 *
 * Limitations:
 * - Does not work for a general binary tree.
 * - Worst-case time becomes O(N) for skewed BSTs.
 * ============================================================================
 */

/* ============================================================================
 *                      ALTERNATIVE APPROACHES
 * ============================================================================
 * 1. Recursive Approach:
 *    - Same logic implemented recursively.
 *    - Cleaner but uses O(H) stack space.
 *
 * 2. Inorder Traversal:
 *    - Traverse BST in sorted order.
 *    - First value ≥ k is the ceil.
 *    - Time: O(N), Space: O(N).
 * ============================================================================
 */
