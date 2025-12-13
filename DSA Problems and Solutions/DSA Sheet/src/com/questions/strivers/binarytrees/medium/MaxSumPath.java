package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ------------------------------------------------------------
 * 🧩 PROBLEM: Maximum Path Sum in a Binary Tree
 * ------------------------------------------------------------
 * A *path* in a binary tree is any sequence of nodes connected
 * through parent-child edges. The path:
 *
 *      ✔ can start and end at ANY node
 *      ✔ does NOT need to go through root
 *      ✔ cannot revisit a node (no cycles)
 *
 * We must find:
 *
 *      ➜ The maximum possible sum of values across ANY valid path.
 *
 * ------------------------------------------------------------
 * 🔥 Example:
 *         1
 *        / \
 *       2   3
 *
 * Best path: 2 → 1 → 3
 * Sum = 6
 *
 * ------------------------------------------------------------
 * WHY THIS IS ASKED IN INTERVIEWS?
 * ------------------------------------------------------------
 * - Tests tree recursion mastery
 * - Requires combining:
 *       → height-style computation
 *       → path-breaking logic
 *       → global tracking of best result
 * - Classic dynamic programming on trees problem
 */
public class MaxSumPath {

    public static void main(String[] args) {

        /*
         * Constructing the sample tree:
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

        // Compute and print Maximum Path Sum
        int maxPathSum = maxPathSum(root);
        System.out.println("Maximum Path Sum: " + maxPathSum);
    }

    // ============================================================
    // ⭐ APPROACH — OPTIMAL O(N) USING MODIFIED HEIGHT CALCULATION
    // ============================================================
    private static int maxPathSum(TreeNode root) {

        // Array used because Java cannot return multiple values
        int[] maxi = { Integer.MIN_VALUE };

        findMaxPathSum(root, maxi);
        return maxi[0];
    }

    /**
     * ------------------------------------------------------------
     * 🔥 CORE LOGIC — Modified DFS:
     * ------------------------------------------------------------
     * For each node:
     *
     * 1️⃣ Recursively compute:
     *        leftMax  = best downward path from left subtree
     *        rightMax = best downward path from right subtree
     *
     *     NOTE ➜ We only take positive contributions:
     *
     *        Math.max(0, childContribution)
     *
     *     Because a negative path would reduce our sum.
     *
     * 2️⃣ At each node, compute the maximum path *through* that node:
     *
     *        leftMax + rightMax + node.val
     *
     *     This path can TOUCH the node but does not need to be returned upward.
     *
     * 3️⃣ Update the global maximum.
     *
     * 4️⃣ Return to parent ONLY the best single-branch path:
     *
     *        node.val + max(leftMax, rightMax)
     *
     *     Because parent cannot take both branches (would form a "V" shape).
     *
     * ------------------------------------------------------------
     * @param root  current node
     * @param maxi  array storing global maximum path sum
     * @return best contribution (single path) to parent
     */
    private static int findMaxPathSum(TreeNode root, int[] maxi) {

        // Base case: null nodes contribute 0
        if (root == null)
            return 0;

        // Compute left & right contributions (ignore negative paths)
        int leftMaxPath = Math.max(0, findMaxPathSum(root.left, maxi));
        int rightMaxPath = Math.max(0, findMaxPathSum(root.right, maxi));

        // Update global maximum:
        // Case: best path goes THROUGH this node using BOTH children
        maxi[0] = Math.max(maxi[0], leftMaxPath + rightMaxPath + root.val);

        // Return only ONE side upward (to avoid splitting)
        return root.val + Math.max(leftMaxPath, rightMaxPath);
    }

    // =====================================================================
// ❌ APPROACH 1 — BRUTE FORCE (O(N²))
// =====================================================================

    /**
     * Computes Maximum Path Sum using brute-force.
     *
     * For each node:
     *   - Compute best downward path from left subtree   → O(N)
     *   - Compute best downward path from right subtree  → O(N)
     *   - Combine them to check path THROUGH current node
     *   - Recursively compute on left and right children
     *
     * Total: O(N²)
     */
    private static int maxPathSumBruteForce(TreeNode root) {
        int[] maxi = { Integer.MIN_VALUE };
        computeBruteForce(root, maxi);
        return maxi[0];
    }

    /**
     * Brute-force recursive helper.
     *
     * @param root current node
     * @param maxi array storing global maximum path sum
     */
    private static void computeBruteForce(TreeNode root, int[] maxi) {
        if (root == null) return;

        // Get downward path sums (expensive: O(N) each call)
        int leftDown = findDownwardPathSum(root.left);
        int rightDown = findDownwardPathSum(root.right);

        // Max path THROUGH this node (considering both children)
        maxi[0] = Math.max(maxi[0], leftDown + rightDown + root.val);

        // Recurse on left and right children
        computeBruteForce(root.left, maxi);
        computeBruteForce(root.right, maxi);
    }

    /**
     * Computes the maximum sum of a downward path starting from this node.
     *
     * Brute-force version:
     * Traverses entire subtree → O(N)
     *
     * @param root subtree root
     * @return best downward path sum
     */
    private static int findDownwardPathSum(TreeNode root) {
        if (root == null) return 0;

        // Best downward path is value + best of left or right
        return root.val + Math.max(findDownwardPathSum(root.left),
                findDownwardPathSum(root.right));
    }


}
