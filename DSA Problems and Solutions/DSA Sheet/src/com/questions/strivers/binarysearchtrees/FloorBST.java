package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 *                          PROBLEM STATEMENT
 * ============================================================================
 * Given the root of a **Binary Search Tree (BST)** and an integer value `k`,
 * find the **floor** of `k` in the BST.
 *
 * The **floor** of a number `k` in a BST is defined as:
 * 👉 The **largest value in the BST that is less than or equal to `k`**.
 *
 * If no such value exists, return `-1`.
 *
 * ---------------------------------------------------------------------------
 * Binary Search Tree (BST) Property Reminder:
 * ---------------------------------------------------------------------------
 * - Left subtree contains values smaller than the node.
 * - Right subtree contains values greater than the node.
 *
 * These properties allow us to efficiently find the floor without
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
 * Floor = 8
 * ============================================================================
 */
public class FloorBST {

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

        // Value whose floor is to be found
        int k = 9;

        // Finding and printing the floor value
        System.out.println(floorInBST(root, k)); // Expected output: 8
    }

    /**
     * ========================================================================
     * FUNCTION: floorInBST (Iterative Approach)
     * ========================================================================
     * Finds the floor of a given value `k` in a Binary Search Tree.
     *
     * Core Idea:
     * - Traverse the BST starting from the root.
     * - Keep track of the best possible floor found so far.
     *
     * @param root Root of the BST
     * @param k    Target value
     * @return     Floor value of k, or -1 if no floor exists
     * ========================================================================
     */
    private static int floorInBST(TreeNode root, int k) {

        // Variable to store the current best candidate for floor
        int floor = -1;

        // Traverse the BST until we reach a null node
        while (root != null) {

            // Case 1: Exact match found
            // The floor of k is k itself
            if (root.val == k) {
                floor = root.val;
                return floor;
            }

            // Case 2: k is greater than current node value
            // Current node can be a potential floor
            if (k > root.val) {
                floor = root.val;   // Update floor candidate
                root = root.right; // Move right to find a closer value
            }
            // Case 3: k is smaller than current node value
            // Floor must lie in the left subtree
            else {
                root = root.left;
            }
        }

        // If traversal ends, return the best floor found
        return floor;
    }
}


/* ============================================================================
 *                      TIME & SPACE COMPLEXITY
 * ============================================================================
 * Time Complexity:
 * - Best Case:  O(1)   → Root is the floor
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
 * 2. Maintain a variable `floor` to store the best candidate so far.
 * 3. At each node:
 *    - If node value == k → this is the floor.
 *    - If node value < k → update floor and move right.
 *    - If node value > k → move left.
 * 4. Continue until the tree ends.
 *
 * Why this works:
 * - BST ordering allows us to eliminate half the tree at every step.
 * - We always move closer to the largest value ≤ k.
 *
 * When to use:
 * - When the tree is a valid BST.
 * - When efficiency matters.
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
 *    - Same logic implemented using recursion.
 *    - Cleaner but uses call stack space.
 *
 * 2. Inorder Traversal:
 *    - Traverse BST in sorted order.
 *    - Track the last value ≤ k.
 *    - Time: O(N), Space: O(N).
 *    - Less efficient but works for understanding.
 * ============================================================================
 */
