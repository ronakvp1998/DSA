package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.*;

/**
 * =============================================================================
 *                       PROBLEM STATEMENT
 * =============================================================================
 * Perform **Preorder Traversal** of a Binary Tree **WITHOUT using recursion
 * and WITHOUT using an explicit stack**.
 *
 * Preorder Traversal Order:
 *   Root  →  Left  →  Right
 *
 * -----------------------------------------------------------------------------
 * Traditional approaches:
 * -----------------------------------------------------------------------------
 * 1. Recursive preorder traversal → uses call stack (O(H) space)
 * 2. Iterative using stack        → uses explicit stack (O(H) space)
 *
 * Morris Preorder Traversal:
 *   ✔ No recursion
 *   ✔ No stack
 *   ✔ O(1) extra space
 *   ✔ Temporarily modifies tree but restores it
 *
 * This is a **high-value interview optimization problem**.
 * =============================================================================
 */
public class MorrisPreorderTraversal {

    /* ==========================================================================
     *                     MORRIS PREORDER TRAVERSAL
     * ==========================================================================
     * Core idea:
     * - Similar to Morris Inorder Traversal
     * - Difference: we **visit the node BEFORE going to left subtree**
     * - Temporary threads are created using inorder predecessor
     *
     * @param root Root of the Binary Tree
     * @return     List containing preorder traversal
     * ==========================================================================
     */
    public static List<Integer> preorderMorris(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();

        // Start traversal from root
        TreeNode curr = root;

        // Continue until all nodes are processed
        while (curr != null) {

            // -------------------------------------------------------------
            // CASE 1: Left child does NOT exist
            // -------------------------------------------------------------
            // In preorder, visit node first, then move right
            if (curr.left == null) {
                preorder.add(curr.val);  // Visit current node
                curr = curr.right;       // Move to right subtree
            }

            // -------------------------------------------------------------
            // CASE 2: Left child exists
            // -------------------------------------------------------------
            else {
                // Find inorder predecessor (rightmost node in left subtree)
                TreeNode prev = curr.left;

                // Move to the rightmost node OR stop if thread exists
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }

                // ---------------------------------------------------------
                // CASE 2A: Thread does NOT exist
                // ---------------------------------------------------------
                if (prev.right == null) {
                    preorder.add(curr.val); // Visit BEFORE going left (key difference)
                    prev.right = curr;      // Create thread back to current
                    curr = curr.left;       // Move to left subtree
                }

                // ---------------------------------------------------------
                // CASE 2B: Thread already exists
                // ---------------------------------------------------------
                else {
                    prev.right = null;      // Remove thread (restore tree)
                    curr = curr.right;      // Move to right subtree
                }
            }
        }
        return preorder;
    }

    /* ==========================================================================
     *                               MAIN METHOD
     * ==========================================================================
     * Used for testing Morris Preorder Traversal
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
         *
         * Expected Preorder: 1 2 4 5 3 6 7
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        List<Integer> result = preorderMorris(root);
        System.out.println("Morris Preorder Traversal: " + result);
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
 *   No recursion, no stack
 * =============================================================================
 */

/* =============================================================================
 *                          APPROACH EXPLANATION
 * =============================================================================
 * Preorder traversal requires visiting a node BEFORE its subtrees.
 *
 * Morris Preorder modifies Morris Inorder slightly:
 * - Visit node when first encountered
 * - Create thread to return after left subtree
 * - Remove thread after use
 *
 * Key Insight:
 * - The only difference from inorder is **WHEN the node is visited**
 * =============================================================================
 */

/* =============================================================================
 *                      WHEN TO USE MORRIS PREORDER
 * =============================================================================
 * ✔ When constant space traversal is required
 * ✔ When recursion/stack is restricted
 * ✔ Interview optimization questions
 *
 * ❌ Avoid when:
 * - Tree must remain immutable at all times
 * - Readability is more important than optimization
 * =============================================================================
 */

/* =============================================================================
 *                  MORRIS INORDER vs PREORDER (INTERVIEW)
 * =============================================================================
 * | Traversal | Visit Node | Thread Creation | Thread Removal |
 * |-----------|------------|-----------------|----------------|
 * | Inorder   | After Left | Yes             | Yes            |
 * | Preorder  | Before Left| Yes             | Yes            |
 * =============================================================================
 */