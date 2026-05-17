package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PostOrderTraversalRec {

    public static void main(String[] args) {

        /*
             Constructed Binary Tree:
                     1
                   /   \
                  2     3
                /  \   / \
               4    5 7   8
                    \    / \
                     6  9  10
         */

        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(6);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(8);
        root.right.right.left = new TreeNode(9);
        root.right.right.right = new TreeNode(10);

        List<Integer> ans = new ArrayList<>();
        postOrderTraversalRec(root, ans);
        System.out.println(ans);
    }

    /**
     * POSTORDER TRAVERSAL (Recursive)
     * --------------------------------
     * Traversal Order: LEFT → RIGHT → ROOT
     *
     * APPROACH:
     * ---------
     * 1. Recursively traverse the left subtree.
     * 2. Recursively traverse the right subtree.
     * 3. Process the current node (add its value to the list).
     *
     * WHY THIS WORKS:
     * ---------------
     * Postorder naturally lends itself to recursion because every node follows
     * the same pattern: first handle children, then handle the node itself.
     * The call stack keeps track of the traversal path.
     *
     * EDGE CASE:
     * ----------
     * - If the root is null, simply return (empty tree).
     *
     * TIME COMPLEXITY:
     * ----------------
     * O(N)
     * - Every node is visited exactly once.
     * - Work done per node is O(1).
     *
     * SPACE COMPLEXITY:
     * -----------------
     * O(H) where H = height of the tree (recursion stack)
     * - Worst case (skewed tree): O(N)
     * - Best case (balanced tree): O(log N)
     *
     * Output list uses O(N) additional memory.
     *
     * @param root The current node being processed
     * @param ans  The list storing the result of postorder traversal
     */
    private static void postOrderTraversalRec(TreeNode root, List<Integer> ans) {

        // Base case: If the node is null, nothing to process
        if (root == null) {
            return;
        }

        // STEP 1: Process left subtree
        postOrderTraversalRec(root.left, ans);

        // STEP 2: Process right subtree
        postOrderTraversalRec(root.right, ans);

        // STEP 3: Visit the current node (postorder requirement)
        ans.add(root.val);
    }
}
