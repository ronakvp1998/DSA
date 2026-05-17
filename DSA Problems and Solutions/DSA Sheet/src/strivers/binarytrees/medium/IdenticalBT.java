package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

public class IdenticalBT {

    /**
     * -----------------------------------------------------------
     * PROBLEM STATEMENT
     * -----------------------------------------------------------
     * Given two binary trees (root1 and root2), determine whether
     * the two trees are IDENTICAL.
     *
     * Two binary trees are said to be identical if:
     *  1. They have the same structure.
     *  2. Corresponding nodes contain the same value.
     *
     * This is a foundational binary-tree problem commonly asked in
     * interviews (Amazon, Google, Microsoft) to test recursion and
     * tree traversal understanding.
     */

    public static void main(String[] args) {

        // ------------------ Creating Tree 1 ------------------
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);

        // ------------------ Creating Tree 2 ------------------
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);

        // --------- Check if both trees are identical ----------
        if (isIdentical(root1, root2)) {
            System.out.println("The binary trees are identical.");
        } else {
            System.out.println("The binary trees are not identical.");
        }
    }

    /**
     * -----------------------------------------------------------
     * FUNCTION: isIdentical(root1, root2)
     * -----------------------------------------------------------
     * PURPOSE:
     *   Recursively check if two binary trees are identical.
     *
     * LOGIC:
     *   - If both nodes are null → identical (same empty subtree)
     *   - If one node is null and the other is not → NOT identical
     *   - If values differ → NOT identical
     *   - Recursively check:
     *          left subtree of both trees
     *          right subtree of both trees
     *
     * WHY RECURSION WORKS:
     *   Trees are naturally recursive structures. Each subtree is
     *   itself a tree, so recursion fits perfectly for comparing them.
     *
     * EDGE CASES HANDLED:
     *   - Both trees empty → identical
     *   - One empty, one not → not identical
     *   - Value mismatch at any level
     *   - Structural mismatch (e.g., one child missing)
     *
     * @return true if the trees are identical, false otherwise
     */
    private static boolean isIdentical(TreeNode root1, TreeNode root2) {

        // Case 1: Both nodes are NULL → same structure at this point
        if (root1 == null && root2 == null) {
            return true;
        }

        // Case 2: One node is NULL but the other is not → different structure
        if (root1 == null || root2 == null) {
            return false;
        }

        // Case 3: Both nodes exist but values differ → not identical
        if (root1.val != root2.val) {
            return false;
        }

        // Case 4: Recursively check:
        // 1. Left subtree of both trees
        // 2. Right subtree of both trees
        // Only if both subtrees are identical → current subtree is identical
        return isIdentical(root1.left, root2.left)
                && isIdentical(root1.right, root2.right);
    }
}
