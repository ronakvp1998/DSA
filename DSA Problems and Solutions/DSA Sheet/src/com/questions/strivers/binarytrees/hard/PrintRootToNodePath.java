package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.ArrayList;

/**
 * =====================================================================
 *                          PROBLEM STATEMENT
 * =====================================================================
 * Given the root of a Binary Tree and an integer value `key`,
 * return the path from the ROOT node to the node containing `key`.
 *
 * The path should be returned as a list of node values starting
 * from the root and ending at the target node.
 *
 * If the target node does NOT exist in the tree,
 * return an EMPTY list.
 *
 * ---------------------------------------------------------------------
 * IMPORTANT NOTES:
 * - The tree is NOT necessarily a Binary Search Tree.
 * - Values are assumed to be unique for simplicity.
 * - The path must follow parent → child connections.
 *
 * =====================================================================
 *                          EXAMPLE
 * =====================================================================
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
 * Input  : key = 7
 * Output : [1, 2, 5, 6, 7]
 *
 * =====================================================================
 *                          APPROACH USED
 * =====================================================================
 * We use DEPTH FIRST SEARCH (DFS) with BACKTRACKING.
 *
 * Core idea:
 * - Traverse the tree starting from the root
 * - Keep adding nodes to a list representing the current path
 * - If the target is found, stop further traversal
 * - If a subtree does NOT contain the target, remove the node
 *   from the path (this is called BACKTRACKING)
 *
 * Why DFS + Backtracking?
 * - DFS naturally explores root-to-leaf paths
 * - Backtracking allows us to "undo" wrong paths
 * - Only one path (root → target) is required
 *
 * =====================================================================
 *                          TIME & SPACE COMPLEXITY
 * =====================================================================
 * Time Complexity: O(N)
 * - In the worst case, every node in the tree is visited once
 *
 * Space Complexity: O(H)
 * - H = height of the tree
 * - Due to recursion stack + path list
 *
 * Worst Case (Skewed Tree): O(N)
 * Best Case (Balanced Tree): O(log N)
 *
 * =====================================================================
 *                          LIMITATIONS
 * =====================================================================
 * - Uses recursion → may cause stack overflow for very deep trees
 * - Always explores until target is found (no pruning like BST)
 *
 */

public class PrintRootToNodePath {

    /**
     * -----------------------------------------------------------------
     * MAIN METHOD (For Testing)
     * -----------------------------------------------------------------
     * Constructs a sample binary tree and prints the path
     * from root to a given target node.
     */
    public static void main(String[] args) {

        /*
         * Constructing the sample tree manually:
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

        // Find and print the path from root to node with value 7
        System.out.println(nodePath(root, 7));
    }

    /**
     * -----------------------------------------------------------------
     * WRAPPER FUNCTION
     * -----------------------------------------------------------------
     * This method initializes the path list and triggers DFS traversal.
     *
     * Why a wrapper?
     * - Keeps the public method clean
     * - Avoids exposing recursion logic directly
     *
     * @param node Root of the binary tree
     * @param key  Target node value
     * @return List containing root-to-node path
     */
    private static ArrayList<Integer> nodePath(TreeNode node, int key) {

        // This list will store the final root → target path
        ArrayList<Integer> path = new ArrayList<>();

        // Edge Case:
        // If the tree is empty, return an empty path
        if (node == null) {
            return path;
        }

        // Perform DFS to populate the path list
        getPath(node, path, key);

        return path;
    }

    /**
     * -----------------------------------------------------------------
     * RECURSIVE DFS + BACKTRACKING METHOD
     * -----------------------------------------------------------------
     * This method searches for the target node and builds the path.
     *
     * @param node Current node being processed
     * @param path Current path from root to this node
     * @param key  Target node value
     * @return true if the target is found in this subtree
     */
    private static boolean getPath(TreeNode node, ArrayList<Integer> path, int key) {

        // Base Case:
        // If we reach a null node, this path is invalid
        if (node == null) {
            return false;
        }

        // Step 1:
        // Add the current node to the path
        // (Assuming it might be part of the final path)
        path.add(node.val);

        // Step 2:
        // If current node matches the target,
        // we have successfully found the path
        if (node.val == key) {
            return true;
        }

        // Step 3:
        // Recursively search in left and right subtrees
        boolean foundInLeft = getPath(node.left, path, key);
        boolean foundInRight = getPath(node.right, path, key);

        // Step 4:
        // If the target is found in either subtree,
        // propagate true upwards without removing nodes
        if (foundInLeft || foundInRight) {
            return true;
        }

        // Step 5 (BACKTRACKING):
        // If neither subtree contains the target,
        // remove the current node from path
        // because it does NOT lead to the solution
        path.remove(path.size() - 1);

        // Inform caller that target was not found here
        return false;
    }
}
