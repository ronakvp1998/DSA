package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ---------------------------------------------------------------
 *                    LOWEST COMMON ANCESTOR IN BST
 * ---------------------------------------------------------------
 *
 * Given a Binary Search Tree (BST) and two nodes p and q,
 * find their Lowest Common Ancestor (LCA).
 *
 * The LCA is the deepest node such that both p and q lie
 * in its left/right subtree or one of them is the node itself.
 *
 * ---------------------------------------------------------------
 * KEY OBSERVATION (BST PROPERTY):
 * ---------------------------------------------------------------
 * - If both p and q are smaller than current node:
 *      -> LCA lies in the left subtree
 * - If both p and q are greater than current node:
 *      -> LCA lies in the right subtree
 * - Otherwise:
 *      -> Current node is the LCA
 *
 * ---------------------------------------------------------------
 * EDGE CASES:
 * ---------------------------------------------------------------
 * - Root is null
 * - One node is ancestor of the other
 * - p and q are on different sides of root
 */
public class LCABST {

    /**
     * Finds the Lowest Common Ancestor of two nodes in a BST.
     *
     * @param root Root of the BST
     * @param p    First node
     * @param q    Second node
     * @return     Lowest Common Ancestor node
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // Base case: If tree is empty, LCA does not exist
        if (root == null) {
            return null;
        }

        // Store current node's value for comparison
        int curr = root.val;

        /*
         * If both p and q are greater than current node,
         * then LCA must lie in the right subtree
         */
        if (curr < p.val && curr < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }

        /*
         * If both p and q are smaller than current node,
         * then LCA must lie in the left subtree
         */
        if (curr > p.val && curr > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }

        /*
         * If one node lies on the left and the other on the right
         * OR one of them is the current node,
         * then current node is the LCA
         */
        return root;
    }

    /**
     * Main method for testing the LCA logic
     */
    public static void main(String[] args) {

        /*
         * Constructing the following BST:
         *
         *          6
         *        /   \
         *       2     8
         *      / \   / \
         *     0   4 7   9
         *        / \
         *       3   5
         */

        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);

        TreeNode p = root.left;               // Node with value 2
        TreeNode q = root.left.right;         // Node with value 4

        TreeNode lca = lowestCommonAncestor(root, p, q);

        System.out.println("LCA of " + p.val + " and " + q.val + " is: " + lca.val);
    }
}

//        ⏳ Time Complexity: O(h)
//        h = height of BST
//        Each recursive call moves one level down
//        Best Case: O(1) (root is LCA)
//        Worst Case: O(n) (skewed tree)

//        🧠 Space Complexity: O(h)
//        Due to recursion stack
//        Worst case: skewed tree → O(n)
//        Balanced BST → O(log n)
