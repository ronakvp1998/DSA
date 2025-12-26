package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================================
 *              LOWEST COMMON ANCESTOR (LCA) IN BINARY TREE
 * =====================================================================
 *
 * -----------------------------
 * PROBLEM STATEMENT
 * -----------------------------
 * Given the root of a Binary Tree and two nodes p and q,
 * find their Lowest Common Ancestor (LCA).
 *
 * The Lowest Common Ancestor is defined as:
 * The LOWEST (deepest) node in the tree that has BOTH p and q
 * as descendants (a node can be a descendant of itself).
 *
 * -----------------------------
 * IMPORTANT NOTES
 * -----------------------------
 * - The tree is NOT a Binary Search Tree.
 * - Node values are assumed to be unique.
 * - Either p or q can be the ancestor of the other.
 *
 * -----------------------------
 * EXAMPLE
 * -----------------------------
 * Tree:
 *
 *            1
 *          /   \
 *         2     3
 *        / \
 *       4   5
 *            \
 *             6
 *              \
 *               7
 *
 * Input : p = 5, q = 7
 * Output: 5
 *
 * -----------------------------
 * APPROACHES IMPLEMENTED
 * -----------------------------
 * 1️⃣ Path-Based Approach (Simple & Intuitive)
 * 2️⃣ Optimized DFS Approach (Single Traversal)
 *
 * =====================================================================
 */

public class LCABT {

    /**
     * ---------------------------------------------------------------
     * MAIN METHOD (FOR TESTING)
     * ---------------------------------------------------------------
     * Builds a sample tree and demonstrates both LCA approaches.
     */
    public static void main(String[] args) {

        /*
         * Constructing the sample binary tree:
         *
         *            1
         *          /   \
         *         2     3
         *        / \
         *       4   5
         *            \
         *             6
         *              \
         *               7
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(6);
        root.left.right.right.right = new TreeNode(7);

        // -------- Optimized DFS LCA --------
        TreeNode lcaOptimized = lca(root, new TreeNode(5), new TreeNode(7));
        System.out.println("LCA (Optimized DFS): " + lcaOptimized.val);

        // -------- Path Based LCA --------
        TreeNode lcaPath = findLCA(root, new TreeNode(5), new TreeNode(7));
        System.out.println("LCA (Path Method): " + lcaPath.val);
    }

    /**
     * =================================================================
     * APPROACH 2 (OPTIMIZED) : SINGLE DFS TRAVERSAL
     * =================================================================
     *
     * IDEA:
     * - Traverse the tree using DFS (post-order)
     * - If current node is NULL → return NULL
     * - If current node matches p or q → return current node
     *
     * INTERPRETATION:
     * - If left and right both return non-null → current node is LCA
     * - If only one side is non-null → propagate it upward
     *
     * WHY THIS WORKS:
     * - The first node where p and q split into different branches
     *   is their Lowest Common Ancestor.
     *
     * WHEN TO USE:
     * - Best approach for interviews
     * - When only one LCA query is required
     *
     * LIMITATIONS:
     * - Uses recursion → stack overflow risk in very deep trees
     *
     * TIME COMPLEXITY: O(N)
     * - Each node is visited once
     *
     * SPACE COMPLEXITY: O(H)
     * - H = height of tree (recursion stack)
     * - Worst case: O(N), Best case: O(log N)
     */
    private static TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {

        // Base Case 1:
        // If tree/subtree is empty
        if (node == null) {
            return null;
        }

        // Base Case 2:
        // If current node matches either p or q,
        // return it as a potential ancestor
        if (node.val == p.val || node.val == q.val) {
            return node;
        }

        // Recurse into left and right subtrees
        TreeNode left = lca(node.left, p, q);
        TreeNode right = lca(node.right, p, q);

        // If one side is null, return the other side
        // (both p and q lie in the same subtree)
        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        // If both sides are non-null,
        // current node is the LCA
        return node;
    }

    /**
     * =================================================================
     * APPROACH 1 : PATH-BASED METHOD
     * =================================================================
     *
     * IDEA:
     * - Find path from root → p
     * - Find path from root → q
     * - Traverse both paths together
     * - The LAST common node is the LCA
     *
     * WHEN TO USE:
     * - When path from root is already required
     * - Easier to understand for beginners
     *
     * DRAWBACKS:
     * - Extra space for storing paths
     * - Two DFS traversals instead of one
     *
     * TIME COMPLEXITY:
     * - Finding path to p: O(N)
     * - Finding path to q: O(N)
     * - Comparing paths: O(H)
     * → Overall: O(N)
     *
     * SPACE COMPLEXITY:
     * - Path storage + recursion: O(H)
     */
    private static TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {

        // Lists to store root-to-node paths
        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();

        // If either node does not exist, LCA is not possible
        if (!findPath(root, p.val, pathP) || !findPath(root, q.val, pathQ)) {
            return null;
        }

        int i = 0;
        TreeNode lca = null;

        // Traverse both paths until they diverge
        while (i < pathP.size() && i < pathQ.size()) {

            // If nodes at same index are equal, update LCA
            if (pathP.get(i).val == pathQ.get(i).val) {
                lca = pathP.get(i);
            } else {
                break;
            }
            i++;
        }

        return lca;
    }

    /**
     * ---------------------------------------------------------------
     * HELPER METHOD: FIND ROOT → NODE PATH
     * ---------------------------------------------------------------
     *
     * Uses DFS + Backtracking to build path.
     *
     * @return true if target is found in subtree
     */
    private static boolean findPath(TreeNode node, int target, List<TreeNode> path) {

        // Base case: empty subtree
        if (node == null) {
            return false;
        }

        // Add current node to path
        path.add(node);

        // If current node is the target, path is complete
        if (node.val == target) {
            return true;
        }

        // Search in left OR right subtree
        if (findPath(node.left, target, path) ||
                findPath(node.right, target, path)) {
            return true;
        }

        // Backtracking:
        // Remove node if target is not found in this path
        path.remove(path.size() - 1);
        return false;
    }
}
