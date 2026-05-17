package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * =====================================================================
 *                  CHILD SUM PROPERTY IN BINARY TREE
 * =====================================================================
 *
 * -----------------------------
 * PROBLEM STATEMENT
 * -----------------------------
 * Given the root of a Binary Tree, convert the tree so that it
 * follows the CHILD SUM PROPERTY.
 *
 * Child Sum Property states:
 * - For every node in the tree,
 *   node.val = value(left child) + value(right child)
 *
 * IMPORTANT RULES:
 * - You are allowed to INCREMENT node values
 * - You are NOT allowed to DECREMENT any node value
 * - Structure of the tree must remain unchanged
 *
 * -----------------------------
 * EXAMPLE
 * -----------------------------
 * Original Tree:
 *
 *            40
 *          /    \
 *        10      20
 *               / \
 *             2     5
 *
 * After enforcing Child Sum Property:
 *
 *            40
 *          /    \
 *        20      20
 *               / \
 *             10    10
 *
 * -----------------------------
 * APPROACH USED
 * -----------------------------
 * We use DFS (Depth First Search) with POST-ORDER traversal.
 *
 * The algorithm works in TWO PHASES for each node:
 *
 * 1️⃣ TOP-DOWN ADJUSTMENT
 *    - Compare parent value with sum of children
 *    - If children sum < parent → increment child values
 *    - If children sum >= parent → update parent
 *
 * 2️⃣ BOTTOM-UP FIX
 *    - After recursion, recompute the sum of children
 *    - Update parent node to maintain the property
 *
 * -----------------------------
 * WHY THIS WORKS
 * -----------------------------
 * - We only INCREASE values (never decrease)
 * - Post-order traversal ensures children are fixed before parent
 * - Final adjustment guarantees correctness
 *
 * =====================================================================
 */

public class ChildSumProperty {

    /**
     * ---------------------------------------------------------------
     * MAIN METHOD (FOR TESTING)
     * ---------------------------------------------------------------
     */
    public static void main(String[] args) {

        /*
         * Constructing sample tree:
         *
         *            40
         *          /    \
         *        10      20
         *               / \
         *             2     5
         */

        TreeNode root = new TreeNode(40);
        root.left = new TreeNode(10);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(2);
        root.right.right = new TreeNode(5);

        // Apply Child Sum Property
        childSumPro(root);

        // Print updated root value (for validation)
        System.out.println("Root value after transformation: " + root.val);
    }

    /**
     * ---------------------------------------------------------------
     * FUNCTION TO ENFORCE CHILD SUM PROPERTY
     * ---------------------------------------------------------------
     *
     * @param root Current node of the binary tree
     */
    private static void childSumPro(TreeNode root) {

        // Base case: If node is null, nothing to process
        if (root == null) {
            return;
        }

        // STEP 1: Calculate sum of child nodes
        int child = 0;

        if (root.left != null) {
            child += root.left.val;
        }

        if (root.right != null) {
            child += root.right.val;
        }

        /*
         * STEP 2: TOP-DOWN ADJUSTMENT
         *
         * If children sum is greater or equal to parent,
         * update parent to match children sum
         */
        if (child >= root.val) {
            root.val = child;
        }
        /*
         * If children sum is less than parent,
         * increment child values to match parent
         */
        else {
            if (root.left != null) {
                root.left.val = root.val;
            }
            if (root.right != null) {
                root.right.val = root.val;
            }
        }

        // STEP 3: Recursively fix left and right subtrees
        childSumPro(root.left);
        childSumPro(root.right);

        /*
         * STEP 4: BOTTOM-UP FIX
         * Recalculate total after recursion
         */
        int tot = 0;

        if (root.left != null) {
            tot += root.left.val;
        }

        if (root.right != null) {
            tot += root.right.val;
        }

        /*
         * Update current node value only if it has at least one child
         */
        if (root.left != null || root.right != null) {
            root.val = tot;
        }
    }
}
