package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 *                          PROBLEM STATEMENT
 * ============================================================================
 * You are given the root of a **Binary Search Tree (BST)** and an integer value
 * `k`. Your task is to **search for a node whose value is equal to `k`**.
 *
 * If such a node exists in the BST, return a reference to that node.
 * Otherwise, return `null`.
 *
 * ---------------------------------------------------------------------------
 * What is a Binary Search Tree (BST)?
 * ---------------------------------------------------------------------------
 * A Binary Search Tree is a special type of binary tree where:
 * 1. All values in the **left subtree** of a node are **less than** the node's value.
 * 2. All values in the **right subtree** of a node are **greater than** the node's value.
 * 3. Both left and right subtrees are themselves BSTs.
 *
 * These properties allow efficient searching, insertion, and deletion.
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
 * Searching for k = 6 → returns the TreeNode containing value 6.
 * ============================================================================
 */
public class SearchInBT {

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // STEP 1: Constructing a sample Binary Search Tree (BST)
        // --------------------------------------------------------------------
        // Root node of the BST
        TreeNode root = new TreeNode(8);

        // Level 1 children
        root.left = new TreeNode(3);    // Left child of 8 (smaller than 8)
        root.right = new TreeNode(10);  // Right child of 8 (greater than 8)

        // Level 2 children
        root.left.left = new TreeNode(1);   // Left child of 3
        root.left.right = new TreeNode(6);  // Right child of 3
        root.right.right = new TreeNode(14); // Right child of 10

        // Level 3 children
        root.left.right.left = new TreeNode(4);  // Left child of 6
        root.left.right.right = new TreeNode(7); // Right child of 6
        root.right.right.left = new TreeNode(13); // Left child of 14

        // Value to search in the BST
        int k = 6;

        // --------------------------------------------------------------------
        // STEP 2: Search the BST for value k
        // --------------------------------------------------------------------
        TreeNode result = searchBT(root, k);

        // Printing the result (assuming k exists in the tree)
        // NOTE: If k does not exist, result will be null
        System.out.println(result.val);
    }

    /**
     * ========================================================================
     * FUNCTION: searchBT
     * ========================================================================
     * Searches for a given value in a Binary Search Tree (BST).
     *
     * @param root The root node of the BST
     * @param val  The value to search for
     * @return     The TreeNode containing the value if found, otherwise null
     * ========================================================================
     */
    private static TreeNode searchBT(TreeNode root, int val) {

        // --------------------------------------------------------------------
        // We traverse the tree iteratively until:
        // 1. root becomes null (value not found), OR
        // 2. root.val equals the target value
        // --------------------------------------------------------------------
        while (root != null && root.val != val) {

            // If the target value is smaller than the current node's value,
            // move to the left subtree (BST property)
            if (val < root.val) {
                root = root.left;
            }
            // Otherwise, move to the right subtree
            else {
                root = root.right;
            }
        }

        // At this point:
        // - root == null → value not found
        // - root.val == val → value found
        return root;
    }
}

/* ============================================================================
 *                      TIME & SPACE COMPLEXITY
 * ============================================================================
 * Time Complexity:
 * - Best Case:  O(1)   → Value found at the root
 * - Average:    O(log N) for a balanced BST
 * - Worst Case: O(N)   for a skewed BST
 *
 * Space Complexity:
 * - O(1) → Iterative approach uses constant extra space
 *
 * Explanation:
 * - Each step moves one level down the tree.
 * - No recursion stack or auxiliary data structures are used.
 * ============================================================================
 */

/* ============================================================================
 *                          APPROACH EXPLANATION
 * ============================================================================
 * 1. We start from the root of the BST.
 * 2. At each node:
 *    - If the node's value matches the target, we return it.
 *    - If the target is smaller, we move left.
 *    - If the target is larger, we move right.
 * 3. We repeat this process until the node is found or we reach a null pointer.
 *
 * Why this works:
 * - The BST property allows us to eliminate half of the tree at every step.
 * - This is similar to binary search on a sorted array.
 *
 * When to use this approach:
 * - When the tree is a valid Binary Search Tree.
 * - When you want efficient search without recursion.
 *
 * Limitations:
 * - If the BST is skewed (like a linked list), performance degrades.
 * - Does not work for general Binary Trees (without BST property).
 * ============================================================================
 */

/* ============================================================================
 *                      ALTERNATIVE APPROACHES
 * ============================================================================
 * 1. Recursive Search in BST
 *    - Same logic as iterative but implemented using recursion.
 *    - Cleaner code, but uses extra call stack space.
 *
 * 2. Search in a Normal Binary Tree (DFS / BFS)
 *    - Required if the tree is NOT a BST.
 *    - Time complexity becomes O(N).
 *    - Cannot prune branches early.
 * ============================================================================
 */

