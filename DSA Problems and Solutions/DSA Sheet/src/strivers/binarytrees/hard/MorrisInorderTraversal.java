package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.*;

/**
 * =============================================================================
 *                       PROBLEM STATEMENT
 * =============================================================================
 * Perform **Inorder Traversal** of a Binary Tree **WITHOUT using recursion
 * and WITHOUT using an explicit stack**.
 *
 * Inorder Traversal Order:
 *   Left  →  Root  →  Right
 *
 * -----------------------------------------------------------------------------
 * The challenge:
 * -----------------------------------------------------------------------------
 * - Standard inorder traversal uses:
 *      1. Recursion (implicit stack)
 *      2. Explicit stack (iterative)
 * - Both consume **O(H)** extra space where H = height of tree
 *
 * Morris Traversal achieves:
 *   ✅ Inorder traversal
 *   ✅ O(1) extra space
 *   ✅ No recursion, no stack
 *
 * This is a **highly important interview problem**.
 * =============================================================================
 */
public class MorrisInorderTraversal {

    /* ==========================================================================
     *                      MORRIS INORDER TRAVERSAL
     * ==========================================================================
     * Core idea:
     * - Temporarily modify the tree by creating "threads"
     * - These threads help us return to a node after finishing its left subtree
     * - Tree structure is fully restored at the end
     *
     * @param root Root of the Binary Tree
     * @return     List containing inorder traversal
     * ==========================================================================
     */
    public static List<Integer> inorderMorris(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();

        // Current pointer starts at root
        TreeNode curr = root;

        // Traverse until all nodes are processed
        while (curr != null) {

            // -------------------------------------------------------------
            // CASE 1: No left child
            // -------------------------------------------------------------
            // If left subtree does not exist:
            // - Visit current node
            // - Move to right subtree
            if (curr.left == null) {
                inorder.add(curr.val);   // Visit node
                curr = curr.right;       // Move right
            }

            // -------------------------------------------------------------
            // CASE 2: Left child exists
            // -------------------------------------------------------------
            else {
                // Find inorder predecessor (rightmost node in left subtree)
                TreeNode prev = curr.left;

                // Move to the rightmost node OR stop if thread already exists
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }

                // ---------------------------------------------------------
                // CASE 2A: Thread does NOT exist
                // ---------------------------------------------------------
                // Create a temporary thread back to current node
                if (prev.right == null) {
                    prev.right = curr;   // Create thread
                    curr = curr.left;    // Move to left subtree
                }

                // ---------------------------------------------------------
                // CASE 2B: Thread already exists
                // ---------------------------------------------------------
                // Left subtree is fully processed
                else {
                    prev.right = null;   // Remove thread (restore tree)
                    inorder.add(curr.val); // Visit current node
                    curr = curr.right;   // Move to right subtree
                }
            }
        }
        return inorder;
    }

    /* ==========================================================================
     *                               MAIN METHOD
     * ==========================================================================
     * Used for testing Morris Inorder Traversal
     * ==========================================================================
     */
    public static void main(String[] args) {
        /*
         * Sample Binary Tree:
         *
         *              1
         *            /   \
         *           2     3
         *          / \   / \
         *         4   5 6   7
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        List<Integer> result = inorderMorris(root);
        System.out.println("Morris Inorder Traversal: " + result);
    }
}

/* =============================================================================
 *                     TIME & SPACE COMPLEXITY
 * =============================================================================
 * Time Complexity:
 * - O(N)
 *   Each node is visited at most twice
 *
 * Space Complexity:
 * - O(1)
 *   No recursion stack, no auxiliary data structure
 * =============================================================================
 */

/* =============================================================================
 *                          APPROACH EXPLANATION
 * =============================================================================
 * Inorder traversal requires returning to a node after its left subtree.
 *
 * Normally achieved using:
 * - Recursion (call stack)
 * - Explicit stack
 *
 * Morris Traversal removes both by:
 * - Temporarily linking inorder predecessor to current node
 * - Using this link to return after left subtree traversal
 * - Removing the link once used
 *
 * Key Observations:
 * - Each edge is visited at most twice
 * - Tree structure is restored completely
 * =============================================================================
 */

/* =============================================================================
 *                      WHEN TO USE MORRIS TRAVERSAL
 * =============================================================================
 * ✔ When space optimization is critical
 * ✔ When recursion/stack is not allowed
 * ✔ Interview optimization questions
 *
 * ❌ Not ideal when:
 * - Tree modification is not allowed
 * - Code simplicity is preferred over optimization
 * =============================================================================
 */

/* =============================================================================
 *                      COMPARISON WITH OTHER METHODS
 * =============================================================================
 * | Method            | Time | Space | Notes                         |
 * |-------------------|------|-------|-------------------------------|
 * | Recursive         | O(N) | O(H)  | Simple, uses call stack       |
 * | Iterative Stack   | O(N) | O(H)  | Controlled stack usage        |
 * | Morris Traversal  | O(N) | O(1)  | Best space optimization       |
 * =============================================================================
 */
