package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 *                     COUNT TOTAL NODES IN A COMPLETE BINARY TREE
 * ============================================================================
 *
 * ---------------------------
 * PROBLEM STATEMENT
 * ---------------------------
 * Given the root of a COMPLETE Binary Tree, return the total
 * number of nodes present in the tree.
 *
 * NOTE:
 * A Complete Binary Tree is defined as:
 * 1️⃣ Every level is completely filled
 * 2️⃣ Except possibly the last level
 * 3️⃣ Nodes of the last level are filled from LEFT to RIGHT
 *
 * Example:
 *
 *              1
 *            /   \
 *           2     3
 *          / \   /
 *         4  5  6
 *
 * Output = 6
 *
 * If the tree was a perfect binary tree (all levels completely filled):
 *
 *              1
 *            /   \
 *           2     3
 *          / \   / \
 *         4  5  6  7
 *
 * Output = 7
 *
 * ---------------------------
 * INTUITION & APPROACH
 * ---------------------------
 * ⚡ We use a VERY IMPORTANT OBSERVATION for COMPLETE Binary Trees:
 *
 * If the height of the LEFTMOST path == height of the RIGHTMOST path
 * then the tree is a PERFECT BINARY TREE.
 *
 * For a perfect binary tree:
 *          Nodes = 2^h – 1
 *
 * So instead of traversing every node, we:
 *
 * 1️⃣ Compute left height (keep going left)
 * 2️⃣ Compute right height (keep going right)
 * 3️⃣ If both heights equal → directly return 2^h – 1
 * 4️⃣ Otherwise recursively count nodes in left & right subtrees
 *
 * This dramatically reduces time compared to naive O(N) traversal.
 *
 * ---------------------------
 * WHY THIS WORKS?
 * ---------------------------
 * In a COMPLETE Binary Tree:
 * - Many subtrees are actually PERFECT binary trees.
 * - Instead of counting node by node in them,
 *   we directly compute using the formula 2^h – 1.
 *
 * ---------------------------
 * TIME COMPLEXITY
 * ---------------------------
 * Computing left height = O(h)
 * Computing right height = O(h)
 *
 * At every level we do O(h) work and go max h levels deep.
 *
 * 👉 Time Complexity: O(log² N)
 * Explanation:
 * - Tree height ≈ log N
 * - Each level we compute height again (log N)
 * - So overall: O(log N * log N)
 *
 * 👍 Much better than O(N)
 *
 * ---------------------------
 * SPACE COMPLEXITY
 * ---------------------------
 * Only recursion stack
 *
 * Worst Case (skewed-ish complete tree): O(log N)
 * Best Case (lots of perfect subtrees): O(log N)
 *
 * ---------------------------
 * LIMITATIONS
 * ---------------------------
 * ❌ Works only for COMPLETE Binary Trees
 * ❌ Do NOT use for general binary trees
 *
 * ---------------------------
 * ALTERNATIVE APPROACHES
 * ---------------------------
 *
 * 1️⃣ Simple DFS / BFS traversal
 *    - Visit every node
 *    - Count nodes
 *    - Time = O(N)
 *    - Space = O(H)
 *    ✔ Works for ANY binary tree
 *    ❌ Slower than optimized solution
 *
 * 2️⃣ Using Level Order Traversal (Queue)
 *    - BFS level-by-level
 *    - Count nodes
 *    - Time = O(N)
 *    - Space = O(N)
 *
 * ============================================================================
 */

public class CountTotalNodes {

    /**
     * ---------------------------
     * MAIN METHOD (TESTING)
     * ---------------------------
     */
    public static void main(String[] args) {

        /*
         * Building a sample COMPLETE Binary Tree
         *
         *             1
         *           /   \
         *          2     3
         *         / \   / \
         *        4  5  6  7
         *                 \
         *                  17  <-- To show tree is not perfect but complete
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(17);

        System.out.println(countNodes(root));  // Output expected: 8
    }


    /**
     * ---------------------------------------------------------
     * FUNCTION TO COUNT NODES IN COMPLETE BINARY TREE
     * ---------------------------------------------------------
     *
     * @param root root of tree
     * @return total number of nodes
     */
    private static int countNodes(TreeNode root){

        // Base Case:
        // If tree is empty, node count is 0
        if(root == null){
            return 0;
        }

        // Compute height of left-most chain
        int lefth = leftHeight(root);

        // Compute height of right-most chain
        int righth = rightHeight(root);

        // 📌 If both heights are equal
        // It means the tree is PERFECT
        // Direct formula: 2^h - 1
        if(lefth == righth){
            return (int) (Math.pow(2, lefth) - 1);
        }

        // Otherwise:
        // Recursively compute nodes in left + right subtree
        // +1 for current root node
        return countNodes(root.left) + countNodes(root.right) + 1;
    }


    /**
     * ---------------------------------------------------------
     * FUNCTION TO COMPUTE RIGHT HEIGHT
     * ---------------------------------------------------------
     * Keep moving ONLY right until null
     * Counts number of levels
     */
    private static int rightHeight(TreeNode node){
        int right = 0;

        while (node != null){
            right++;          // Count current level
            node = node.right; // Move to right child
        }

        return right;
    }


    /**
     * ---------------------------------------------------------
     * FUNCTION TO COMPUTE LEFT HEIGHT
     * ---------------------------------------------------------
     * Keep moving ONLY left until null
     * Counts number of levels
     */
    private static int leftHeight(TreeNode node){
        int left = 0;

        while (node != null){
            left++;          // Count current level
            node = node.left; // Move to left child
        }

        return left;
    }
}
