package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * ============================================================================
 *                          PROBLEM STATEMENT
 * ============================================================================
 * Given the root of a **Binary Search Tree (BST)** and a key value `key`,
 * delete the node with value equal to `key` from the BST.
 *
 * After deletion, the BST property must remain valid.
 * Return the root of the modified BST.
 *
 * ---------------------------------------------------------------------------
 * Binary Search Tree (BST) Properties:
 * ---------------------------------------------------------------------------
 * 1. Left subtree contains values strictly smaller than the node's value.
 * 2. Right subtree contains values strictly greater than the node's value.
 * 3. Both subtrees are themselves BSTs.
 *
 * ---------------------------------------------------------------------------
 * Deletion in BST has THREE possible cases:
 * ---------------------------------------------------------------------------
 * 1. Node to delete has **no children** (leaf node)
 * 2. Node to delete has **one child**
 * 3. Node to delete has **two children**
 *
 * This implementation handles all three cases efficiently.
 * ============================================================================
 */
public class DeleteNode {
    public static void main(String[] args) {
        /*
         * Constructing the following BST:
         *
         * 8
         * / \
         * 3 10
         * / \ \
         * 1 6 14
         * / \ /
         * 4 7 13
         */


        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(3);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(6);
        root.right.right = new TreeNode(14);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(7);
        root.right.right.left = new TreeNode(13);


        DeleteNode obj = new DeleteNode();


        int keyToDelete = 3;
        System.out.println("Deleting node: " + keyToDelete);


        root = obj.deleteNode(root, keyToDelete);


        System.out.println("Inorder traversal after deletion:");
        inorder(root);
    }

    // ---------------------------------------------------------------
    // Utility method: Inorder Traversal
    // Inorder traversal of BST prints values in sorted order
    // ---------------------------------------------------------------
    private static void inorder(TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
    /**
     * ========================================================================
     * FUNCTION: deleteNode (Iterative Search + Helper Deletion)
     * ========================================================================
     * Deletes a node with the given key from the BST.
     *
     * @param root Root of the BST
     * @param key  Value to be deleted
     * @return     Root of the BST after deletion
     * ========================================================================
     */

    private TreeNode deleteNode(TreeNode root, int key) {

        // Edge case: empty tree
        if (root == null) {
            return null;
        }

        // Case 1: If the root itself needs to be deleted
        if (root.val == key) {
            return helper(root);
        }

        // Dummy pointer to preserve original root
        TreeNode dummy = root;

        // --------------------------------------------------------------------
        // Iteratively search for the node to delete
        // --------------------------------------------------------------------
        while (root != null) {

            // If key is smaller, move to left subtree
            if (root.val > key) {

                // Check if left child is the node to be deleted
                if (root.left != null && root.left.val == key) {
                    root.left = helper(root.left); // Delete and reconnect
                    break;
                } else {
                    root = root.left;
                }
            }
            // If key is greater, move to right subtree
            else {
                // Check if right child is the node to be deleted
                if (root.right != null && root.right.val == key) {
                    root.right = helper(root.right); // Delete and reconnect
                    break;
                } else {
                    root = root.right;
                }
            }
        }

        // Return the original root
        return dummy;
    }

    /**
     * ========================================================================
     * FUNCTION: helper
     * ========================================================================
     * Handles deletion logic once the target node is found.
     *
     * @param root Node to be deleted
     * @return     New subtree root after deletion
     * ========================================================================
     */
    private static TreeNode helper(TreeNode root) {

        // Case 1: Node has no left child
        // Replace node with its right subtree
        if (root.left == null) {
            return root.right;
        }
        // Case 2: Node has no right child
        // Replace node with its left subtree
        else if (root.right == null) {
            return root.left;
        }
        // Case 3: Node has two children
        else {
            // Store right subtree
            TreeNode rightChild = root.right;

            // Find the rightmost node of left subtree
            TreeNode lastRight = findLastRight(root.left);

            // Attach original right subtree to it
            lastRight.right = rightChild;

            // Return left subtree as new root
            return root.left;
        }
    }

    /**
     * ========================================================================
     * FUNCTION: findLastRight
     * ========================================================================
     * Finds the rightmost node in a subtree.
     * This node contains the maximum value of that subtree.
     *
     * @param node Root of the subtree
     * @return     Rightmost node
     * ========================================================================
     */
    private static TreeNode findLastRight(TreeNode node) {

        // Base case: rightmost node found
        if (node.right == null) {
            return node;
        }

        // Recursively move right
        return findLastRight(node.right);
    }
}

/* ============================================================================
 *                      TIME & SPACE COMPLEXITY
 * ============================================================================
 * Time Complexity:
 * - Best Case:  O(1)
 * - Average:    O(log N) for balanced BST
 * - Worst Case: O(N) for skewed BST
 *
 * Space Complexity:
 * - O(H) due to recursion in findLastRight
 *   where H is height of the tree
 * ============================================================================
 */

/* ============================================================================
 *                          APPROACH EXPLANATION
 * ============================================================================
 * 1. First, search for the node to delete using BST properties.
 * 2. Once found, delegate deletion logic to the helper method.
 * 3. Helper method handles three cases:
 *    - No child
 *    - One child
 *    - Two children
 * 4. For two children case:
 *    - Attach right subtree to the rightmost node of left subtree.
 *    - Return left subtree as replacement root.
 *
 * Why this works:
 * - BST ordering is preserved.
 * - No unnecessary traversal is done.
 *
 * When to use:
 * - Efficient deletion without recursion stack for search.
 *
 * Limitations:
 * - Tree may become skewed.
 * ============================================================================
 */

/* ============================================================================
 *                      ALTERNATIVE APPROACHES
 * ============================================================================
 * 1. Using Inorder Successor:
 *    - Replace node with smallest value in right subtree.
 *    - Common textbook approach.
 *
 * 2. Fully Recursive Deletion:
 *    - Cleaner but uses recursion stack space.
 *
 * 3. Self-Balancing BSTs:
 *    - AVL / Red-Black Trees prevent skewing.
 * ============================================================================
 */
