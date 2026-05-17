package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.*;

/**
 * ---------------------------------------------------------------
 *                         PROBLEM STATEMENT
 * ---------------------------------------------------------------
 * Print the **Bottom View** of a Binary Tree.
 *
 * When the tree is viewed from the bottom side, only the lowest
 * nodes (deepest nodes) at each vertical line are visible.
 *
 * Rules:
 * 1. Assign each node a vertical index (Horizontal Distance = HD)
 *        - Root has HD = 0
 *        - Left child  → HD - 1
 *        - Right child → HD + 1
 *
 * 2. Use BFS so deeper nodes overwrite shallower ones for the same HD.
 *
 * 3. For each HD, the **last node encountered in BFS** is the
 *    bottom-most (deepest) node.
 *
 * 4. Output must be sorted from leftmost HD to rightmost.
 *
 * Example Tree:
 *
 *                  1
 *               /     \
 *              2       3
 *            /   \    /  \
 *           4    10  9   10
 *            \
 *             5
 *              \
 *               6
 *
 * Bottom View Output:
 * [4, 5, 9, 3, 10]
 *
 * ---------------------------------------------------------------
 * Why BFS instead of DFS?
 * ---------------------------------------------------------------
 * - BFS processes nodes level by level.
 * - For bottom view, deeper nodes must REPLACE shallower ones.
 * - BFS naturally ensures that deeper nodes come later in traversal.
 *
 * DFS may incorrectly pick higher nodes if it explores deeper branches first.
 *
 * ---------------------------------------------------------------
 */

public class BottomView {

    /**
     * Computes the Bottom View of the Binary Tree.
     *
     * @param root Root of the binary tree
     * @return List of integers representing the bottom view
     *
     * ------------------------------------------------------------
     * APPROACH:
     * ------------------------------------------------------------
     * - Use BFS (queue) so that bottom-most nodes come last.
     * - Maintain a TreeMap<HD, value> where HD = horizontal distance.
     *
     * - For every node visited:
     *        mpp.put(HD, node.val);
     *
     *   This overwrites earlier values → keeping only the deepest node.
     *
     * - Insert children in queue:
     *        Left child  → HD - 1
     *        Right child → HD + 1
     *
     * - At the end, extract values from TreeMap (sorted by HD).
     */
    public static List<Integer> bottomView(TreeNode root) {

        // Result list to store bottom view nodes
        List<Integer> ans = new ArrayList<>();

        // Handle empty tree case
        if (root == null) {
            return ans;
        }

        // TreeMap keeps horizontal distances naturally sorted
        Map<Integer, Integer> mpp = new TreeMap<>();

        // Queue for BFS → stores (node, horizontalDistance)
        Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();

        // Start BFS with root at HD = 0
        q.add(new Pair<>(root, 0));

        // BFS traversal
        while (!q.isEmpty()) {

            // Remove the front element
            Pair<TreeNode, Integer> pair = q.poll();
            TreeNode node = pair.getKey();
            int hd = pair.getValue();

            // Overwrite entry for this HD → ensures deeper nodes replace shallower ones
            mpp.put(hd, node.val);

            // Process left child with HD - 1
            if (node.left != null) {
                q.add(new Pair<>(node.left, hd - 1));
            }

            // Process right child with HD + 1
            if (node.right != null) {
                q.add(new Pair<>(node.right, hd + 1));
            }
        }

        // Copy values from TreeMap in sorted HD order
        ans.addAll(mpp.values());

        return ans;
    }

    // Main driver to test bottom view
    public static void main(String[] args) {

        // Construct the sample tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(10);

        // Compute bottom view
        List<Integer> bottomView = bottomView(root);

        // Output results
        System.out.println("Bottom View Traversal: ");
        for (int node : bottomView) {
            System.out.print(node + " ");
        }
    }
}
