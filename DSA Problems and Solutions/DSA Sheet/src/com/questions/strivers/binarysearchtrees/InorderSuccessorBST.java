package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------------------
 *              INORDER SUCCESSOR IN BST
 * ---------------------------------------------------------------
 *
 * This class demonstrates THREE different approaches to find
 * the inorder successor of a given node in a BST.
 *
 * APPROACHES:
 * 1️⃣ Store inorder traversal in array + binary search
 * 2️⃣ Perform inorder traversal and track successor
 * 3️⃣ Optimal BST traversal using comparisons (BEST)
 */
public class InorderSuccessorBST {

    /* =============================================================
     * APPROACH 1: STORE INORDER + SEARCH
     * =============================================================
     *
     * Idea:
     * - Perform inorder traversal
     * - Store values in a sorted list
     * - Find p and return next element
     *
     * Time: O(N)
     * Space: O(N)
     */

    public static TreeNode inorderSuccessorApproach1(TreeNode root, TreeNode p) {

        List<TreeNode> inorder = new ArrayList<>();
        buildInorder(root, inorder);

        // Find p in inorder list and return next element
        for (int i = 0; i < inorder.size(); i++) {
            if (inorder.get(i) == p) {
                return (i + 1 < inorder.size()) ? inorder.get(i + 1) : null;
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
     * - Once p is found, the very next visited node is successor
     *
     * Time: O(N)
     * Space: O(H) recursion stack
     */

    private static TreeNode successor2 = null;
    private static boolean found = false;

    public static TreeNode inorderSuccessorApproach2(TreeNode root, TreeNode p) {
        successor2 = null;
        found = false;
        inorderDFS(root, p);
        return successor2;
    }

    private static void inorderDFS(TreeNode root, TreeNode p) {
        if (root == null || successor2 != null) return;

        inorderDFS(root.left, p);

        if (found && successor2 == null) {
            successor2 = root;
            return;
        }

        if (root == p) {
            found = true;
        }

        inorderDFS(root.right, p);
    }

    /* =============================================================
     * APPROACH 3: BST PROPERTY COMPARISON (OPTIMAL ⭐)
     * =============================================================
     *
     * Idea:
     * - Traverse from root
     * - If p.val >= root.val → go right
     * - Else → root is a potential successor, go left
     *
     * Time: O(H)
     * Space: O(1)
     */

    public static TreeNode inorderSuccessor(TreeNode root, TreeNode p) {

        TreeNode successor = null;

        // Traverse until root becomes null
        while (root != null) {

            /*
             * If p's value is greater than or equal to root,
             * successor must lie in the right subtree
             */
            if (p.val >= root.val) {
                root = root.right;
            }
            /*
             * If p's value is smaller,
             * current root is a potential successor
             */
            else {
                successor = root;
                root = root.left;
            }
        }

        return successor;
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

        TreeNode p = root.left; // Node with value 10

        System.out.println("Approach 1 Successor: " +
                inorderSuccessorApproach1(root, p).val);

        System.out.println("Approach 2 Successor: " +
                inorderSuccessorApproach2(root, p).val);

        System.out.println("Approach 3 Successor: " +
                inorderSuccessor(root, p).val);
    }
}

//        | Approach          | Time     | Space    | Verdict       |
//        | ----------------- | -------- | -------- | ------------- |
//        | Store inorder     | O(N)     | O(N)     | ❌ Extra space |
//        | Inorder traversal | O(N)     | O(H)     | ⚠️ Acceptable |
//        | BST comparison    | **O(H)** | **O(1)** | ✅ **BEST**    |

//        | Problem     | Condition           | Direction |
//        | ----------- | ------------------- | --------- |
//        | Successor   | `p.val >= root.val` | go right  |
//        | Predecessor | `p.val <= root.val` | go left   |
//        | Candidate   | opposite branch     | save node |