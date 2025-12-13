package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 * PROBLEM STATEMENT
 * ============================================================================
 * Given the root of a **Binary Search Tree (BST)**, find:
 * <p>
 * 1. The **minimum value** present in the BST
 * 2. The **maximum value** present in the BST
 * <p>
 * You must leverage the properties of a BST to solve the problem efficiently.
 * <p>
 * ---------------------------------------------------------------------------
 * Binary Search Tree (BST) Properties Recap:
 * ---------------------------------------------------------------------------
 * 1. All values in the **left subtree** of a node are **smaller** than the node.
 * 2. All values in the **right subtree** of a node are **greater** than the node.
 * 3. Both subtrees are themselves BSTs.
 * <p>
 * Because of these properties:
 * - The **minimum value** is found at the **leftmost node**.
 * - The **maximum value** is found at the **rightmost node**.
 * <p>
 * ---------------------------------------------------------------------------
 * Example BST:
 * ---------------------------------------------------------------------------
 * 8
 * / \
 * 3 10
 * / \ \
 * 1 6 14
 * / \ /
 * 4 7 13
 *
 * Minimum value = 1
 * Maximum value = 14
 * ============================================================================
 */
public class MinMaxBST {

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // STEP 1: Constructing a sample Binary Search Tree (BST)
        // --------------------------------------------------------------------
        // Root node of the BST
        TreeNode root = new TreeNode(8);

        // Level 1 children
        root.left = new TreeNode(3);     // Left child of 8 (smaller values)
        root.right = new TreeNode(10);   // Right child of 8 (larger values)

        // Level 2 children
        root.left.left = new TreeNode(1);    // Left child of 3
        root.left.right = new TreeNode(6);   // Right child of 3
        root.right.right = new TreeNode(14); // Right child of 10

        // Level 3 children
        root.left.right.left = new TreeNode(4);   // Left child of 6
        root.left.right.right = new TreeNode(7);  // Right child of 6
        root.right.right.left = new TreeNode(13); // Left child of 14

        // --------------------------------------------------------------------
        // STEP 2: Find and print minimum and maximum values in the BST
        // --------------------------------------------------------------------
        System.out.println(minBST(root)); // Expected output: 1
        System.out.println(maxBST(root)); // Expected output: 14
    }

    /**
     * ========================================================================
     * FUNCTION: minBST
     * ========================================================================
     * Finds the minimum value in a Binary Search Tree.
     * <p>
     * Logic:
     * - In a BST, the smallest value always lies in the leftmost node.
     * - Keep moving left until no more left child exists.
     *
     * @param root Root of the BST
     * @return Minimum value in the BST, or -1 if tree is empty
     * ========================================================================
     */
    private static int minBST(TreeNode root) {

        // Edge case: If the tree is empty, no minimum exists
        if (root == null) {
            return -1; // Sentinel value indicating empty tree
        }

        // Traverse left until we reach the leftmost node
        while (root != null) {

            // If there is no left child, current node is the minimum
            if (root.left == null) {
                return root.val;
            }

            // Move to the left child
            root = root.left;
        }

        // This line is technically unreachable but kept for safety
        return -1;
    }

    /**
     * ========================================================================
     * FUNCTION: maxBST
     * ========================================================================
     * Finds the maximum value in a Binary Search Tree.
     * <p>
     * Logic:
     * - In a BST, the largest value always lies in the rightmost node.
     * - Keep moving right until no more right child exists.
     *
     * @param root Root of the BST
     * @return Maximum value in the BST, or -1 if tree is empty
     * ========================================================================
     */
    private static int maxBST(TreeNode root) {

        // Edge case: If the tree is empty, no maximum exists
        if (root == null) {
            return -1; // Sentinel value indicating empty tree
        }

        // Traverse right until we reach the rightmost node
        while (root != null) {

            // If there is no right child, current node is the maximum
            if (root.right == null) {
                return root.val;
            }

            // Move to the right child
            root = root.right;
        }

        // This line is technically unreachable but kept for safety
        return -1;
    }

    /**
     * ========================================================================
     * RECURSIVE VERSION: maxBSTRecursive
     * ========================================================================
     * Recursively finds the maximum value in a Binary Search Tree.
     * <p>
     * Logic:
     * - If the current node has no right child, it is the maximum.
     * - Otherwise, keep moving right recursively.
     *
     * @param root Root of the BST
     * @return Maximum value in the BST, or -1 if tree is empty
     * ========================================================================
     */
    private static int maxBSTRecursive(TreeNode root) {
        // Base case: empty tree
        if (root == null) {
            return -1;
        }
        // If there is no right child, current node is the maximum
        if (root.right == null) {
            return root.val;
        }
        // Recursively move to the right subtree
        return maxBSTRecursive(root.right);
    }
    /**
     * ========================================================================
     * RECURSIVE VERSION: minBSTRecursive
     * ========================================================================
     * Recursively finds the minimum value in a Binary Search Tree.
     *
     * Logic:
     * - If the current node has no left child, it is the minimum.
     * - Otherwise, keep moving left recursively.
     *
     * @param root Root of the BST
     * @return Minimum value in the BST, or -1 if tree is empty
     * ========================================================================
     */
    private static int minBSTRecursive(TreeNode root) {
        // Base case: empty tree
        if (root == null) {
            return -1;
        }
        // If there is no left child, current node is the minimum
        if (root.left == null) {
            return root.val;
        }
        // Recursively move to the left subtree
        return minBSTRecursive(root.left);
    }
}

/* ============================================================================
 *                          APPROACH EXPLANATION
 * ============================================================================
 * 1. To find the minimum value:
 *    - Start from the root.
 *    - Repeatedly move to the left child.
 *    - The node with no left child is the minimum.
 *
 * 2. To find the maximum value:
 *    - Start from the root.
 *    - Repeatedly move to the right child.
 *    - The node with no right child is the maximum.
 *
 * Why this works:
 * - BST ordering guarantees smallest values on the left and largest on the right.
 * - No need to traverse the entire tree.
 *
 * When to use this approach:
 * - Only when the tree is a valid Binary Search Tree.
 *
 * Limitations:
 * - Does NOT work for a general Binary Tree.
 * - If BST is skewed, traversal becomes linear.
 * ============================================================================
 */

/* ============================================================================
 *                      ALTERNATIVE APPROACHES
 * ============================================================================
 * 1. Recursive Approach:
 *    - Recursively move left (min) or right (max).
 *    - Cleaner code but uses call stack space.
 *
 * 2. Full Tree Traversal (DFS / BFS):
 *    - Required if the tree is NOT a BST.
 *    - Time complexity becomes O(N).
 * ============================================================================
 */

/* ============================================================================
 *                      TIME & SPACE COMPLEXITY
 * ============================================================================
 * Time Complexity:
 * - Best Case:  O(1)   → Root itself is min/max
 * - Average:    O(log N) for a balanced BST
 * - Worst Case: O(N)   for a skewed BST
 *
 * Space Complexity:
 * - O(1) → Iterative approach uses constant extra space
 *
 * Explanation:
 * - We traverse only one path from root to leaf.
 * - No recursion or extra data structures are used.
 * ============================================================================
 */