package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============================================================
 *                    PROBLEM STATEMENT
 * ===============================================================
 * LEFT VIEW OF BINARY TREE:
 *   - The nodes visible when the tree is viewed from the LEFT side.
 *   - At every level, the FIRST node we encounter (while traversing from left to right)
 *     becomes part of the left view.
 *
 * RIGHT VIEW OF BINARY TREE:
 *   - The nodes visible when the tree is viewed from the RIGHT side.
 *   - At every level, the FIRST node we encounter (while traversing from right to left)
 *     becomes part of the right view.
 *
 * We must print both LEFT and RIGHT view of a given binary tree.
 *
 * ---------------------------------------------------------------
 * Example Tree:
 *                   1
 *                 /   \
 *               2       3
 *             /   \    / \
 *           4    10  9   10
 *             \
 *              5
 *               \
 *                6
 *
 * Left View Output  → [1, 2, 4, 5, 6]
 * Right View Output → [1, 3, 10, 6]
 *
 * ---------------------------------------------------------------
 * CONSTRAINTS:
 *   - TreeNode count: 0 to N (up to 10^5 in interviews)
 *   - Values may repeat
 *
 * ---------------------------------------------------------------
 * APPROACH USED:
 *   DFS (Depth-First Search) + Level Tracking
 *
 *   Right View:
 *       - Visit RIGHT subtree first
 *       - If this is the first node at this level → add it to result
 *
 *   Left View:
 *       - Visit LEFT subtree first
 *       - If this is the first node at this level → add it to result
 *
 * Why this works?
 *   - Because DFS reaches depth levels in the required direction first.
 *   - We ensure only the first node per depth is recorded.
 *
 * ============================================================== */

public class LeftRightView {

    public static void main(String[] args) {

        // ---------------- SAMPLE TREE CREATION -------------------
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);

        root.right = new TreeNode(3);
        root.right.right = new TreeNode(10);
        root.right.left = new TreeNode(9);

        // --------------------- RIGHT VIEW -------------------------
        List<Integer> rightView = rightsideView(root);

        System.out.print("Right View Traversal: ");
        for (int val : rightView) {
            System.out.print(val + " ");
        }
        System.out.println();

        // ---------------------- LEFT VIEW -------------------------
        List<Integer> leftView = leftsideView(root);

        System.out.print("Left View Traversal: ");
        for (int val : leftView) {
            System.out.print(val + " ");
        }
        System.out.println();
    }


    /**
     * Function to get RIGHT VIEW of the tree.
     * Uses DFS and visits RIGHT child first.
     */
    private static List<Integer> rightsideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        recursiveRight(root, 0, res);
        return res;
    }

    /**
     * Recursive helper for Right View.
     *
     * @param root  current node
     * @param level current depth level
     * @param res   result list storing first node at each level
     */
    private static void recursiveRight(TreeNode root, int level, List<Integer> res) {

        // Base case: null node → return
        if (root == null) {
            return;
        }

        // If visiting this level for the FIRST time → add current node
        if (res.size() == level) {
            res.add(root.val);
        }

        // Traverse RIGHT first to ensure rightmost element is picked first
        recursiveRight(root.right, level + 1, res);

        // Then traverse LEFT subtree
        recursiveRight(root.left, level + 1, res);
    }


    /**
     * Function to get LEFT VIEW of the tree.
     * Uses DFS and visits LEFT child first.
     */
    private static List<Integer> leftsideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        recursiveLeft(root, 0, res);
        return res;
    }

    /**
     * Recursive helper for Left View.
     *
     * @param root  current node
     * @param level current depth level
     * @param res   result list storing first node at each level
     */
    private static void recursiveLeft(TreeNode root, int level, List<Integer> res) {

        // Base case: null node → return
        if (root == null) {
            return;
        }

        // If visiting this level for the FIRST time → add current node
        if (res.size() == level) {
            res.add(root.val);
        }

        // Visit LEFT first to ensure leftmost element is captured
        recursiveLeft(root.left, level + 1, res);

        // Then visit RIGHT subtree
        recursiveLeft(root.right, level + 1, res);
    }
}
