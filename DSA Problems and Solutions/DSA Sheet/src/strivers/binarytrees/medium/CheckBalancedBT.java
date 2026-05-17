package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ================================================================
 * 📝 PROBLEM STATEMENT — Check If a Binary Tree Is Height-Balanced
 * ================================================================
 *
 * A **Binary Tree is height-balanced** (also called an "AVL property") if:
 *
 *      | height(left subtree) - height(right subtree) | ≤ 1
 *
 * for **EVERY node** in the tree.
 *
 * WHY IS THIS IMPORTANT?
 * ----------------------
 * Balanced trees guarantee:
 *      ✔ O(log N) search
 *      ✔ O(log N) insertion
 *      ✔ O(log N) deletion
 *
 * Unbalanced trees degrade to:
 *      ❌ O(N) performance (behaves like a linked list)
 *
 * This is why AVL Trees, Red-Black Trees, Segment Trees, etc.
 * always ensure balance.
 *
 * Your task:
 * ----------
 * Given a binary tree, determine if it is height-balanced.
 * Return:
 *      → true  → if the tree is balanced
 *      → false → if it is not balanced
 *
 * ---------------------------------------------------------------
 */
public class CheckBalancedBT {

    public static void main(String[] args) {

        /*
                   1
                 /   \
                2     3
               / \
              4   5
         Example tree used for testing.
        */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // Using the optimized DFS approach
        // dfsHeight returns -1 if unbalanced, otherwise height
        System.out.println(dfsHeight(root) != -1);
    }


    /**
     * =====================================================================
     * 🚀 APPROACH 1 — Optimized DFS (Post-Order) → O(N) TIME
     * =====================================================================
     *
     * MAIN IDEA:
     * ----------
     * Instead of:
     *      ✔ computing height separately
     *      ✔ checking balance separately
     * we do BOTH in **one DFS traversal**.
     *
     * HOW IT WORKS:
     * -------------
     * For every node:
     *    1️⃣ Compute height of left subtree
     *    2️⃣ Compute height of right subtree
     *    3️⃣ If any subtree is unbalanced → return -1 immediately (early exit)
     *    4️⃣ If |lh - rh| > 1 → unbalanced → return -1
     *    5️⃣ Else return height = 1 + max(lh, rh)
     *
     * WHY RETURN -1?
     * --------------
     * - Acts as a special signal indicating "subtree is unbalanced".
     * - Prevents unnecessary recursion.
     * - Enables early exit → O(N) time.
     *
     * ADVANTAGES:
     * -----------
     * ✔ Single DFS traversal
     * ✔ No repeated height computations
     * ✔ Early exit when imbalance detected
     *
     * USE WHEN?
     * ---------
     * - Always recommended for checking balanced binary trees.
     * - Best possible time complexity.
     *
     * LIMITATION:
     * -----------
     * - Uses recursion (stack = tree height)
     *
     * ---------------------------------------------------------------------
     * ⏱ TIME COMPLEXITY: O(N)
     * - Each node is processed once.
     *
     * 💾 SPACE COMPLEXITY: O(H)
     * - H = height of tree (recursion stack)
     * - Balanced → O(log N)
     * - Skewed → O(N)
     * =====================================================================
     */
    private static int dfsHeight(TreeNode root) {

        // Base case:
        // Empty tree has height 0 → also balanced
        if (root == null) {
            return 0;
        }

        // Recursively compute left subtree height
        int leftHeight = dfsHeight(root.left);

        // If left subtree already unbalanced → stop further processing
        if (leftHeight == -1) {
            return -1;
        }

        // Recursively compute right subtree height
        int rightHeight = dfsHeight(root.right);

        // If right subtree is unbalanced → propagate failure
        if (rightHeight == -1) {
            return -1;
        }

        // If height difference exceeds 1 → current subtree unbalanced
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // propagate upwards
        }

        // Otherwise return height of this subtree
        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * =====================================================================
     * APPROACH 2 — Naive (Brute Force) Method → O(N²) TIME
     * =====================================================================
     *
     * APPROACH:
     * ---------
     * 1. For each node:
     *      ✔ compute height of left subtree  → O(N)
     *      ✔ compute height of right subtree → O(N)
     * 2. Check if their heights differ by more than 1
     * 3. Recursively check left and right trees
     *
     * WHY O(N²)?
     * ----------
     * - At each node you compute height → O(N)
     * - You do this for all N nodes → N * N = O(N²)
     *
     * USE WHEN?
     * ---------
     * ❌ Almost never
     * ✔ Only for conceptual understanding
     *
     * TRADE-OFF:
     * ----------
     * + Easy to understand
     * - Very slow for large trees
     *
     * =====================================================================
     */
    private static boolean checkBalanced(TreeNode root) {

        // Empty tree = balanced
        if (root == null) {
            return true;
        }

        // Compute heights of left & right subtrees
        int lh = findHeight(root.left);
        int rh = findHeight(root.right);

        // If height difference is > 1 → not balanced
        if (Math.abs(lh - rh) > 1) {
            return false;
        }

        // Recursively check the left and right branches
        boolean left = checkBalanced(root.left);
        boolean right = checkBalanced(root.right);

        // If any subtree is unbalanced → whole tree is unbalanced
        return left && right;
    }


    /**
     * =====================================================================
     * HELPER METHOD — Compute Height of a Subtree
     * =====================================================================
     *
     * Height = length of the longest path from root → leaf.
     *
     * This is called repeatedly in the naive O(N²) approach.
     *
     * ⏱ Time:  O(N)  (for each height calculation)
     * 💾 Space: O(H)  (recursion depth)
     * =====================================================================
     */
    private static int findHeight(TreeNode root) {

        // Empty tree → height 0
        if (root == null) {
            return 0;
        }

        // Height = 1 + max(leftHeight, rightHeight)
        int left = findHeight(root.left);
        int right = findHeight(root.right);

        return 1 + Math.max(left, right);
    }

}
