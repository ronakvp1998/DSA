package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * =====================================================================
 *              MAXIMUM WIDTH OF A BINARY TREE
 * =====================================================================
 *
 * -----------------------------
 * PROBLEM STATEMENT
 * -----------------------------
 * Given the root of a Binary Tree, find the MAXIMUM WIDTH of the tree.
 *
 * Width of a level is defined as:
 *   (index of rightmost node - index of leftmost node + 1)
 *
 * The indexing follows the same rule as a COMPLETE BINARY TREE:
 * - Root index = 0
 * - Left child  = 2 * index + 1
 * - Right child = 2 * index + 2
 *
 * IMPORTANT:
 * - Null nodes BETWEEN two valid nodes are counted.
 * - Tree is NOT necessarily complete.
 *
 * -----------------------------
 * EXAMPLE
 * -----------------------------
 * Tree:
 *
 *            1
 *          /   \
 *         2     3
 *        /       \
 *       4         5
 *
 * Level widths:
 * Level 0 → 1
 * Level 1 → 2
 * Level 2 → 4  ← maximum
 *
 * Output: 4
 *
 * =====================================================================
 * APPROACH USED: BFS (LEVEL ORDER) + INDEXING
 * =====================================================================
 *
 * IDEA:
 * - Perform level-order traversal (BFS)
 * - Assign each node an index as if it were in a complete binary tree
 * - For each level:
 *     width = lastIndex - firstIndex + 1
 *
 * WHY NORMALIZATION IS REQUIRED:
 * - Index values grow exponentially (2^level)
 * - To avoid integer overflow, we subtract the minimum index of that level
 *
 * =====================================================================
 * TIME & SPACE COMPLEXITY
 * =====================================================================
 * Time Complexity: O(N)
 * - Every node is visited exactly once
 *
 * Space Complexity: O(N)
 * - Queue stores nodes of one level
 * - Worst case: all nodes in last level
 *
 * =====================================================================
 */

public class MaxWidthBT {

    /**
     * Pair class to store:
     * - node → TreeNode reference
     * - num  → index of the node
     */
    static class Pair {
        TreeNode node;
        int num;

        Pair(TreeNode node, int num) {
            this.node = node;
            this.num = num;
        }
    }

    /**
     * ---------------------------------------------------------------
     * MAIN METHOD (FOR TESTING)
     * ---------------------------------------------------------------
     */
    public static void main(String[] args) {

        /*
         * Constructing sample tree:
         *
         *            1
         *          /   \
         *         2     3
         *        /       \
         *       4         5
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        System.out.println("Maximum Width = " + widthOfBT(root));
    }

    /**
     * ---------------------------------------------------------------
     * FUNCTION TO FIND MAXIMUM WIDTH OF BINARY TREE
     * ---------------------------------------------------------------
     *
     * @param root Root node of the binary tree
     * @return Maximum width across all levels
     */
    private static int widthOfBT(TreeNode root) {

        // Edge case: empty tree
        if (root == null) {
            return 0;
        }

        int ans = 0;

        // Queue for BFS traversal
        Queue<Pair> queue = new LinkedList<>();

        // Start with root node at index 0
        queue.offer(new Pair(root, 0));

        // Perform level-order traversal
        while (!queue.isEmpty()) {

            int size = queue.size();

            // Minimum index at this level (used for normalization)
            int mMin = queue.peek().num;

            int first = 0, last = 0;

            // Traverse all nodes at current level
            for (int i = 0; i < size; i++) {

                // Normalize index to prevent overflow
                int cur_id = queue.peek().num - mMin;

                // Extract node from queue
                TreeNode node = queue.peek().node;
                queue.poll();

                // First node of the level
                if (i == 0) {
                    first = cur_id;
                }

                // Last node of the level
                if (i == size - 1) {
                    last = cur_id;
                }

                // Add left child with calculated index
                if (node.left != null) {
                    queue.offer(new Pair(node.left, cur_id * 2 + 1));
                }

                // Add right child with calculated index
                if (node.right != null) {
                    queue.offer(new Pair(node.right, cur_id * 2 + 2));
                }
            }

            // Update maximum width
            ans = Math.max(ans, last - first + 1);
        }

        return ans;
    }
}
