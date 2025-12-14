package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.Arrays;

/**
 * ---------------------------------------------------------------
 * CONSTRUCT BST FROM PREORDER TRAVERSAL
 * ---------------------------------------------------------------
 * <p>
 * This class demonstrates THREE different approaches:
 * <p>
 * 1️⃣ Approach 1: Insert nodes one-by-one using BST insertion
 * 2️⃣ Approach 2: Use Preorder + Inorder traversal
 * 3️⃣ Approach 3: Optimal Upper Bound technique (BEST)
 * <p>
 * ---------------------------------------------------------------
 * IMPORTANT INTERVIEW NOTE:
 * ---------------------------------------------------------------
 * Approach 3 is the most optimal and preferred in interviews.
 */
public class BSTFromPreOrder {

    /* =============================================================
     * APPROACH 1: INSERT NODES ONE BY ONE (BRUTE / BASIC)
     * =============================================================
     *
     * Idea:
     * - First element becomes root
     * - Insert remaining elements using standard BST insertion
     *
     * Time Complexity:
     * - Average: O(N log N)
     * - Worst case (skewed tree): O(N²)
     *
     * Space Complexity:
     * - O(H) due to recursion
     */

    public static TreeNode bstFromPreorderApproach1(int[] preorder) {

        // Base case: empty array
        if (preorder == null || preorder.length == 0) {
            return null;
        }

        // First value is always the root
        TreeNode root = new TreeNode(preorder[0]);

        // Insert remaining values into BST
        for (int i = 1; i < preorder.length; i++) {
            insertIntoBST(root, preorder[i]);
        }

        return root;
    }

    /**
     * Standard BST insertion logic
     */
    private static void insertIntoBST(TreeNode root, int val) {

        // If value is smaller, go left
        if (val < root.val) {
            if (root.left == null) {
                root.left = new TreeNode(val);
            } else {
                insertIntoBST(root.left, val);
            }
        }
        // If value is greater, go right
        else {
            if (root.right == null) {
                root.right = new TreeNode(val);
            } else {
                insertIntoBST(root.right, val);
            }
        }
    }

    /* =============================================================
     * APPROACH 2: PREORDER + INORDER
     * =============================================================
     *
     * Idea:
     * - Inorder traversal of BST is always SORTED
     * - Sort preorder array to get inorder traversal
     * - Construct tree using preorder + inorder
     *
     * Time Complexity:
     * - Sorting: O(N log N)
     * - Tree construction: O(N)
     *
     * Space Complexity:
     * - O(N) for inorder array + recursion stack
     */

    public static TreeNode bstFromPreorderApproach2(int[] preorder) {

        if (preorder == null || preorder.length == 0) {
            return null;
        }

        // Create inorder traversal by sorting preorder
        int[] inorder = preorder.clone();
        Arrays.sort(inorder);

        // Index wrapper for preorder traversal
        int[] preIndex = {0};

        return buildTree(preorder, inorder, 0, inorder.length - 1, preIndex);
    }

    /**
     * Builds tree using preorder and inorder traversal
     */
    private static TreeNode buildTree(int[] preorder, int[] inorder,
                                      int inStart, int inEnd, int[] preIndex) {

        // Base case
        if (inStart > inEnd) {
            return null;
        }

        // Current root from preorder
        TreeNode root = new TreeNode(preorder[preIndex[0]++]);

        // Find root index in inorder traversal
        int rootIndex = inStart;
        while (inorder[rootIndex] != root.val) {
            rootIndex++;
        }

        // Construct left and right subtrees
        root.left = buildTree(preorder, inorder, inStart, rootIndex - 1, preIndex);
        root.right = buildTree(preorder, inorder, rootIndex + 1, inEnd, preIndex);

        return root;
    }

    /* =============================================================
     * APPROACH 3: UPPER BOUND TECHNIQUE (OPTIMAL ⭐)
     * =============================================================
     *
     * Time Complexity: O(N)
     * Space Complexity: O(H)
     */

    private static TreeNode bstFromPreOrder(int[] A) {
        return bstFromPreOrder(A, Integer.MAX_VALUE, new int[]{0});
    }

    private static TreeNode bstFromPreOrder(int[] A, int bound, int[] i) {

        // Stop if all elements are processed or value exceeds bound
        if (i[0] == A.length || A[i[0]] > bound) {
            return null;
        }

        // Create root node
        TreeNode root = new TreeNode(A[i[0]++]);

        // Left subtree must be smaller than root
        root.left = bstFromPreOrder(A, root.val, i);

        // Right subtree must be within previous bound
        root.right = bstFromPreOrder(A, bound, i);

        return root;
    }

    /* =============================================================
     * MAIN METHOD FOR TESTING ALL APPROACHES
     * =============================================================
     */

    public static void main(String[] args) {

        int[] preorder = {8, 5, 1, 7, 10, 12};

        System.out.println("Approach 1: Insert One-by-One");
        TreeNode root1 = bstFromPreorderApproach1(preorder);
        printInorder(root1);

        System.out.println("\nApproach 2: Preorder + Inorder");
        TreeNode root2 = bstFromPreorderApproach2(preorder);
        printInorder(root2);

        System.out.println("\nApproach 3: Upper Bound (Optimal)");
        TreeNode root3 = bstFromPreOrder(preorder);
        printInorder(root3);
    }

    /**
     * Inorder traversal (used to verify BST correctness)
     */
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
}

//        | Approach           | Time        | Space | Best Use Case         | Verdict        |
//        | ------------------ | ----------- | ----- | --------------------- | -------------- |
//        | Insert one-by-one  | O(N²) worst | O(H)  | Simple logic          | ❌ Not optimal |
//        | Preorder + Inorder | O(N log N)  | O(N)  | General tree building | ⚠️ Acceptable  |
//        | Upper Bound        | **O(N)**    | O(H)  | BST + preorder        | ✅ **BEST**    |
