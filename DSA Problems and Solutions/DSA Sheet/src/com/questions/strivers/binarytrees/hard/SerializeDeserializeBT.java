package com.questions.strivers.binarytrees.hard;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * =============================================================================
 *                          PROBLEM STATEMENT
 * =============================================================================
 * Design an algorithm to **serialize** and **deserialize** a Binary Tree.
 *
 * Serialization:
 *  - Convert a binary tree into a single string representation
 *  - The structure and values of the tree must be preserved
 *
 * Deserialization:
 *  - Convert the serialized string back into the original binary tree
 *
 * -----------------------------------------------------------------------------
 * Why this problem matters:
 * -----------------------------------------------------------------------------
 * - Used in data storage, networking, and distributed systems
 * - Asked frequently in interviews (LeetCode 297)
 * - Tests understanding of tree traversal and reconstruction
 *
 * -----------------------------------------------------------------------------
 * Constraints & Assumptions:
 * -----------------------------------------------------------------------------
 * - TreeNode contains integer values
 * - "n" represents a null node
 * - Space-separated format is used
 * =============================================================================
 */
public class SerializeDeserializeBT {

    /* ==========================================================================
     *                              DESERIALIZE
     * ==========================================================================
     * Converts a serialized string back into a Binary Tree
     *
     * Algorithm Used:
     * - Level Order Traversal (BFS)
     * - Queue is used to attach children correctly
     *
     * @param s Serialized string
     * @return  Root of reconstructed Binary Tree
     * ==========================================================================
     */
    private static TreeNode deserialize(String s) {

        // Edge case: empty string means no tree
        if (s.isEmpty()) {
            return null;
        }

        // Queue to help reconstruct the tree level by level
        Queue<TreeNode> queue = new LinkedList<>();

        // Split serialized string into individual node values
        String[] values = s.split(" ");

        // First value is always the root
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        queue.add(root);

        // Index starts from 1 since root is already processed
        for (int i = 1; i < values.length; i++) {

            // Get parent node to attach children
            TreeNode node = queue.poll();

            // ------------------- Left Child -------------------
            if (!values[i].equals("n")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                node.left = left;
                queue.add(left);
            }

            // ------------------- Right Child ------------------
            i++; // Move to next index for right child
            if (i < values.length && !values[i].equals("n")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                node.right = right;
                queue.add(right);
            }
        }
        return root;
    }

    /* ==========================================================================
     *                              SERIALIZE
     * ==========================================================================
     * Converts a Binary Tree into a string representation
     *
     * Algorithm Used:
     * - Level Order Traversal (BFS)
     * - Null nodes are explicitly marked using "n"
     *
     * @param root Root of the Binary Tree
     * @return     Serialized string
     * ==========================================================================
     */
    private static String serialize(TreeNode root) {

        // Edge case: empty tree
        if (root == null) {
            return "";
        }

        // Queue for BFS traversal
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder res = new StringBuilder();

        // Start with root
        queue.offer(root);

        // Traverse tree level by level
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            // If node is null, append marker and continue
            if (node == null) {
                res.append("n ");
                continue;
            }

            // Append current node's value
            res.append(node.val).append(" ");

            // Add left and right children (even if null)
            queue.add(node.left);
            queue.add(node.right);
        }
        return res.toString();
    }

    /* ==========================================================================
     *                               MAIN METHOD
     * ==========================================================================
     * Used for testing serialization and deserialization
     * ==========================================================================
     */
    public static void main(String[] args) {
        /*
         * Sample Binary Tree:
         *
         *          1
         *        /   \
         *       2     3
         *            / \
         *           4   5
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        // Serialize
        String serialized = serialize(root);
        System.out.println("Serialized Tree: " + serialized);

        // Deserialize
        TreeNode newRoot = deserialize(serialized);
        System.out.println("Inorder Traversal After Deserialization:");
        inorder(newRoot);
    }

    // Utility method to verify correctness using inorder traversal
    private static void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
}

/* =============================================================================
 *                          APPROACH EXPLANATION
 * =============================================================================
 * 1. We use Level Order Traversal (BFS) for both serialization and deserialization.
 * 2. During serialization:
 *    - Every node is recorded
 *    - Null children are marked explicitly as "n"
 * 3. During deserialization:
 *    - Nodes are reconstructed in the same BFS order
 *    - Queue ensures correct parent-child linking
 *
 * Why this works:
 * - BFS preserves structure level-by-level
 * - Explicit null markers avoid ambiguity
 *
 * When to use:
 * - When full tree structure must be preserved
 * - For generic binary trees (not just BSTs)
 *
 * Limitations:
 * - Serialized string can be long due to null markers
 * =============================================================================
 */

/* =============================================================================
 *                        ALTERNATIVE APPROACHES
 * =============================================================================
 * 1. Preorder + Null Markers:
 *    - Simpler string
 *    - Recursive reconstruction
 *
 * 2. DFS without null markers (BST only):
 *    - Works only for BSTs
 *
 * 3. JSON / Custom Encoding:
 *    - Used in production systems
 * =============================================================================
 */

/* =============================================================================
 *                     TIME & SPACE COMPLEXITY
 * =============================================================================
 * Let N be number of nodes
 *
 * Serialization:
 * - Time:  O(N)
 * - Space: O(N) (queue + output string)
 *
 * Deserialization:
 * - Time:  O(N)
 * - Space: O(N)
 * =============================================================================
 */
