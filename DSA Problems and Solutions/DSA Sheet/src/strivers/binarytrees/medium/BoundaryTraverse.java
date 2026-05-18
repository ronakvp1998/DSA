package strivers.binarytrees.medium;

import strivers.binarytrees.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================================
 * 🟩 PROBLEM: Boundary Traversal of a Binary Tree
 * ============================================================================================
 *
 * Given a binary tree, return the boundary traversal in anti-clockwise direction.
 *
 * Boundary Traversal Order:
 * 1. Root
 * 2. Left Boundary (excluding leaves)
 * 3. All Leaves (left → right)
 * 4. Right Boundary (excluding leaves, added in reverse)
 *
 * Example:
 *              1
 *            /   \
 *           2     3
 *         /  \   / \
 *        4    5 6   7
 *
 * Boundary = [1, 2, 4, 5, 6, 7, 3]
 *
 * Why is this asked in interviews?
 * --------------------------------
 * ✔ Tests tree traversal mastery
 * ✔ Requires careful ordering
 * ✔ Requires handling leaves + boundaries separately
 * ✔ Ensures candidate manages duplicates & edge cases well
 *
 * This file implements the most optimal approach.
 * ============================================================================================
 */
public class BoundaryTraverse {

    // ============================================================================================
    // 🔍 Helper #1 — Check if a node is a leaf (no children)
    // ============================================================================================
    private static boolean isLeaf(TreeNode root) {
        return root.left == null && root.right == null;
    }

    // ============================================================================================
    // 🔍 Helper #2 — Add LEFT boundary (excluding leaf nodes)
    // ============================================================================================
    private static void addLeftBoundary(TreeNode root, List<Integer> res) {

        // Start from root.left (root already handled separately)
        TreeNode curr = root.left;

        // Traverse until you run out of boundary nodes
        while (curr != null) {

            // Add only NON-leaf nodes to boundary
            if (!isLeaf(curr)) {
                res.add(curr.val);
            }

            // Prefer left child → standard boundary rule
            if (curr.left != null) {
                curr = curr.left;
            } else {
                // If no left child → go right (still part of boundary)
                curr = curr.right;
            }
        }
    }

    // ============================================================================================
    // 🔍 Helper #3 — Add RIGHT boundary (excluding leaf nodes, ADDED IN REVERSE)
    // ============================================================================================
    private static void addRightBoundary(TreeNode root, List<Integer> res) {

        TreeNode curr = root.right;

        // We use a temporary list because right boundary must be added bottom → up
        List<Integer> temp = new ArrayList<>();

        while (curr != null) {

            if (!isLeaf(curr)) {
                temp.add(curr.val);
            }

            // Prefer right child → right boundary rule
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        // Add in reverse to maintain anti-clockwise order
        for (int i = temp.size() - 1; i >= 0; --i) {
            res.add(temp.get(i));
        }
    }

    // ============================================================================================
    // 🔍 Helper #4 — Add ALL LEAF NODES (in left → right order)
    // ============================================================================================
    private static void addLeaves(TreeNode root, List<Integer> res) {

        // If node itself is leaf → directly add
        if (isLeaf(root)) {
            res.add(root.val);
            return;
        }

        // DFS → Go left and right
        if (root.left != null) addLeaves(root.left, res);
        if (root.right != null) addLeaves(root.right, res);
    }

    // ============================================================================================
    // ⭐ MAIN FUNCTION — Boundary Traversal
    // ============================================================================================
    private static List<Integer> printBoundary(TreeNode root) {

        List<Integer> res = new ArrayList<>();

        // Edge case: empty tree
        if (root == null) return res;

        // Step 1 → Add root (only if not leaf)
        if (!isLeaf(root)) {
            res.add(root.val);
        }

        // Step 2 → Add left boundary
        addLeftBoundary(root, res);

        // Step 3 → Add all leaves
        addLeaves(root, res);

        // Step 4 → Add right boundary
        addRightBoundary(root, res);

        return res;
    }

    // ============================================================================================
    // Utility → Print list
    // ============================================================================================
    private static void printResult(List<Integer> result) {
        for (int val : result) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    // ============================================================================================
    // MAIN DRIVER
    // ============================================================================================
    public  static void main(String[] args) {

        // Creating sample tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        // Perform boundary traversal
        List<Integer> result = printBoundary(root);

        System.out.print("Boundary Traversal: ");
        printResult(result);
    }
}
