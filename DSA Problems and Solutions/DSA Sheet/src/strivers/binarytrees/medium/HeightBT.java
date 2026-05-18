package strivers.binarytrees.medium;

import strivers.binarytrees.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

public class HeightBT {

    public static void main(String[] args) {

        // Creating a sample binary tree
        /*
                   1
                 /   \
                2     3
               / \
              4   5
        */

        // Root node
        TreeNode root = new TreeNode(1);

        // Building left & right subtree
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        // Extending left subtree
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // Calling recursive height calculation
        System.out.println(heightBT(root));
    }

    /**
     * ==================================================================
     * 🔥 APPROACH 1: RECURSIVE HEIGHT OF BINARY TREE (DFS)
     * ==================================================================
     *
     * PURPOSE:
     * --------
     * Compute the height (or maximum depth) of a binary tree.
     *
     * HEIGHT DEFINITION:
     * ------------------
     * Height = Number of nodes on the longest path from root to leaf.
     *
     * HOW THIS WORKS:
     * ---------------
     * We recursively:
     *   1️⃣ Ask left child for its height
     *   2️⃣ Ask right child for its height
     *   3️⃣ Current node height = 1 + max(leftHeight, rightHeight)
     *
     * WHY RECURSION?
     * --------------
     * - Height of a tree depends on heights of its subtrees.
     * - Each subtree is itself a smaller binary tree.
     * - Perfect fit for recursion + divide and conquer.
     *
     * BASE CASE:
     * ----------
     * - If the current node is NULL → height = 0.
     *
     * EDGE CASES HANDLED:
     * -------------------
     * - Empty tree (root == null)
     * - Skewed trees (linked-list shaped)
     * - Balanced trees
     *
     * ------------------------------------------------------------------
     * ⏱ TIME COMPLEXITY: O(N)
     * ------------------------------------------------------------------
     * - Each node is visited once.
     *
     * ------------------------------------------------------------------
     * 💾 SPACE COMPLEXITY: O(H)
     * ------------------------------------------------------------------
     * - H = height of tree (recursion call stack)
     * - Balanced → O(log N)
     * - Skewed → O(N)  (worst case)
     * ==================================================================
     */
    private static int heightBT(TreeNode root) {

        // Base case:
        // A null node contributes 0 to the height.
        if (root == null) {
            return 0;
        }

        // Recursively compute the height of the LEFT subtree.
        // This divides the problem into a smaller subproblem.
        int leftHeight = heightBT(root.left);

        // Recursively compute the height of the RIGHT subtree.
        int rightHeight = heightBT(root.right);

        // Height of the current node:
        // 1 (current node itself) + taller of left or right subtree.
        return 1 + Math.max(leftHeight, rightHeight);
    }


    /**
     * ==================================================================
     * 🔥 APPROACH 2: ITERATIVE HEIGHT CALCULATION (LEVEL ORDER TRAVERSAL)
     * ==================================================================
     *
     * HOW IT WORKS:
     * -------------
     * Uses BFS (Breadth-First Search) to traverse level by level.
     * Each level processed increases the height by 1.
     *
     * PROCEDURE:
     * ----------
     *   1️⃣ Add root to queue
     *   2️⃣ For each level:
     *        - Count nodes in that level
     *        - Remove each node from queue
     *        - Add their children back to queue
     *        - After processing the entire level → height++
     *
     * When queue becomes empty, we have processed all levels.
     *
     * WHY THIS WORKS:
     * ---------------
     * - Height = Number of levels in level-order traversal.
     *
     * ------------------------------------------------------------------
     * ⏱ TIME COMPLEXITY: O(N)
     * ------------------------------------------------------------------
     * - Every node is inserted & removed from queue exactly once.
     *
     * ------------------------------------------------------------------
     * 💾 SPACE COMPLEXITY: O(W)
     * ------------------------------------------------------------------
     * - W = maximum width of the tree (max nodes at any level)
     * - Worst case (complete tree) → O(N/2) → O(N)
     *
     * WHEN TO USE BFS FOR HEIGHT?
     * ----------------------------
     * ✔ When tree is very deep → avoids stack overflow
     * ✔ When you also need level information
     *
     * LIMITATION:
     * -----------
     * - Uses extra queue space
     * ==================================================================
     */
    int maxDepth(TreeNode root) {

        // If tree is empty → height is 0
        if (root == null) {
            return 0;
        }

        // Queue to perform BFS (level order)
        Queue<TreeNode> q = new LinkedList<>();

        // Keep track of number of levels processed
        int level = 0;

        // Add root to start BFS
        q.add(root);

        // Process until queue becomes empty
        while (!q.isEmpty()) {

            // Number of nodes at the current level
            int size = q.size();

            // Process all nodes in the current level
            for (int i = 0; i < size; i++) {

                // Remove one node from queue
                TreeNode front = q.poll();

                // Add left child
                if (front.left != null) {
                    q.add(front.left);
                }

                // Add right child
                if (front.right != null) {
                    q.add(front.right);
                }
            }

            // After processing the entire level, increase depth count
            level++;
        }

        // Level now represents the height of the tree
        return level;
    }
}
