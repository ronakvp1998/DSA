package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------------------
 *            INORDER PREDECESSOR IN BST
 * ---------------------------------------------------------------
 *
 * This class demonstrates THREE different approaches to find
 * the inorder predecessor of a given node in a BST.
 *
 * APPROACHES:
 * 1️⃣ Store inorder traversal in array + search
 * 2️⃣ Inorder traversal with tracking
 * 3️⃣ Optimal BST traversal using comparisons (BEST)
 */
public class InorderPredecessorBST {

    /* =============================================================
     * APPROACH 1: STORE INORDER + SEARCH
     * =============================================================
     *
     * Idea:
     * - Perform inorder traversal
     * - Store nodes in sorted order
     * - Find p and return previous element
     *
     * Time: O(N)
     * Space: O(N)
     */

    public static TreeNode inorderPredecessorApproach1(TreeNode root, TreeNode p) {

        List<TreeNode> inorder = new ArrayList<>();
        buildInorder(root, inorder);

        // Find p in inorder list and return previous element
        for (int i = 0; i < inorder.size(); i++) {
            if (inorder.get(i) == p) {
                return (i - 1 >= 0) ? inorder.get(i - 1) : null;
            }
        }
        return null;
    }

    /**
     * Helper method to build inorder traversal
     */
    private static void buildInorder(TreeNode root, List<TreeNode> inorder) {
        if (root == null) return;
        buildInorder(root.left, inorder);
        inorder.add(root);
        buildInorder(root.right, inorder);
    }

    /* =============================================================
     * APPROACH 2: INORDER TRAVERSAL WITH FLAG
     * =============================================================
     *
     * Idea:
     * - Perform inorder traversal
     * - Track previously visited node
     * - When p is found, previous node is predecessor
     *
     * Time: O(N)
     * Space: O(H)
     */

    private static TreeNode predecessor2 = null;
    private static TreeNode prev = null;

    public static TreeNode inorderPredecessorApproach2(TreeNode root, TreeNode p) {
        predecessor2 = null;
        prev = null;
        inorderDFS(root, p);
        return predecessor2;
    }

    private static void inorderDFS(TreeNode root, TreeNode p) {
        if (root == null || predecessor2 != null) return;

        inorderDFS(root.left, p);

        if (root == p) {
            predecessor2 = prev;
            return;
        }

        prev = root;

        inorderDFS(root.right, p);
    }

    /* =============================================================
     * APPROACH 3: BST PROPERTY COMPARISON (OPTIMAL ⭐)
     * =============================================================
     *
     * Idea:
     * - Traverse from root
     * - If p.val <= root.val → go left
     * - Else → root is potential predecessor, go right
     *
     * Time: O(H)
     * Space: O(1)
     */

    public static TreeNode inorderPredecessor(TreeNode root, TreeNode p) {

        TreeNode predecessor = null;

        while (root != null) {

            /*
             * If p's value is smaller than or equal to root,
             * predecessor must lie in the left subtree
             */
            if (p.val <= root.val) {
                root = root.left;
            }
            /*
             * If p's value is greater,
             * current root is a valid predecessor
             */
            else {
                predecessor = root;
                root = root.right;
            }
        }

        return predecessor;
    }

    /* =============================================================
     * MAIN METHOD FOR TESTING
     * =============================================================
     */

    public static void main(String[] args) {

        /*
         * Constructing BST:
         *
         *          20
         *        /    \
         *      10      30
         *     /  \
         *    5   15
         */

        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(10);
        root.right = new TreeNode(30);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(15);

        TreeNode p = root.left.right; // Node with value 15

        System.out.println("Approach 1 Predecessor: " +
                inorderPredecessorApproach1(root, p).val);

        System.out.println("Approach 2 Predecessor: " +
                inorderPredecessorApproach2(root, p).val);

        System.out.println("Approach 3 Predecessor: " +
                inorderPredecessor(root, p).val);
    }
}
//        | Approach          | Time     | Space    |
//        | ----------------- | -------- | -------- |
//        | Inorder list      | O(N)     | O(N)     |
//        | Inorder traversal | O(N)     | O(H)     |
//        | BST traversal     | **O(H)** | **O(1)** |

//        | Problem     | Condition           | Direction |
//        | ----------- | ------------------- | --------- |
//        | Successor   | `p.val >= root.val` | go right  |
//        | Predecessor | `p.val <= root.val` | go left   |
//        | Candidate   | opposite branch     | save node |
