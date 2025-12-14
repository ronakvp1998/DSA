package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 *                          PROBLEM STATEMENT
 * ============================================================================
 * You are given the root of a **Binary Search Tree (BST)** and an integer value `k`.
 * Your task is to **insert `k` into the BST** while maintaining all BST properties.
 *
 * Return the root of the BST after insertion.
 *
 * ---------------------------------------------------------------------------
 * Binary Search Tree (BST) Rules:
 * ---------------------------------------------------------------------------
 * 1. All values in the left subtree of a node are **less than** the node's value.
 * 2. All values in the right subtree of a node are **greater than or equal to**
 *    the node's value (as followed in this implementation).
 * 3. Both left and right subtrees are also BSTs.
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
 * Insert k = 9
 *
 * Resulting BST:
 *
 *              8
 *            /   \
 *           3     10
 *          / \    / \
 *         1   6  9  14
 *            / \     /
 *           4   7   13
 * ============================================================================
 */
public class InsertIntoBST {

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

        // Value to be inserted into the BST
        int k = 9;

        // Insert the value into the BST
        insertIntoBT(root, k);
    }

    /**
     * ========================================================================
     * FUNCTION: insertIntoBT (Iterative Approach)
     * ========================================================================
     * Inserts a value into a Binary Search Tree while maintaining BST properties.
     *
     * @param root Root of the BST
     * @param k    Value to be inserted
     * @return     Root of the BST after insertion
     * ========================================================================
     */
    private static TreeNode insertIntoBT(TreeNode root, int k) {

        // --------------------------------------------------------------------
        // Edge Case: If the tree is empty, the new value becomes the root
        // --------------------------------------------------------------------
        if (root == null) {
            return new TreeNode(k);
        }

        // Pointer used to traverse the tree
        TreeNode curr = root;

        // --------------------------------------------------------------------
        // Traverse the BST to find the correct insertion position
        // --------------------------------------------------------------------
        while (true) {

            // Case 1: Insert into the right subtree
            // Convention used here: duplicates go to the right
            if (curr.val <= k) {

                // If right child exists, keep traversing
                if (curr.right != null) {
                    curr = curr.right;
                }
                // Otherwise, insert the new node here
                else {
                    curr.right = new TreeNode(k);
                    break; // Insertion complete
                }
            }
            // Case 2: Insert into the left subtree
            else {

                // If left child exists, keep traversing
                if (curr.left != null) {
                    curr = curr.left;
                }
                // Otherwise, insert the new node here
                else {
                    curr.left = new TreeNode(k);
                    break; // Insertion complete
                }
            }
        }

        // Return the original root (BST structure preserved)
        return root;
    }
}
/* ============================================================================
 *                      TIME & SPACE COMPLEXITY
 * ============================================================================
 * Time Complexity:
 * - Best Case:  O(1)   → Inserted as root or immediate child
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
 * 1. If the BST is empty, create a new node and return it as root.
 * 2. Otherwise, start from the root and compare `k` with the current node.
 * 3. If k is greater than or equal to the current node:
 *    - Move to the right subtree.
 * 4. If k is smaller than the current node:
 *    - Move to the left subtree.
 * 5. Repeat until a null child is found and insert the node there.
 *
 * Why this works:
 * - BST ordering uniquely determines the correct position for any value.
 * - We only traverse one path from root to leaf.
 *
 * When to use this approach:
 * - When recursion is not required or stack space must be avoided.
 *
 * Limitations:
 * - Tree may become skewed, degrading performance.
 * ============================================================================
 */
/* ============================================================================
 *                      ALTERNATIVE APPROACHES
 * ============================================================================
 * 1. Recursive Insertion:
 *    - Simpler and cleaner code.
 *    - Uses recursion stack (O(H) space).
 *
 * 2. Self-Balancing Trees (AVL / Red-Black Tree):
 *    - Automatically keep tree balanced.
 *    - Guarantees O(log N) insertion time.
 * ============================================================================
 */
