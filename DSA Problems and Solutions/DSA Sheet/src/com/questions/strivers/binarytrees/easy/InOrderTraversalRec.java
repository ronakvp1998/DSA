package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class InOrderTraversalRec {

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

        // Creating a sample binary tree for testing
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

        // Perform inorder traversal
        inOrderTraversalRec(root, ans);

        System.out.println(ans); // Print result
    }

    /**
     * INORDER TRAVERSAL (Recursive)
     * ------------------------------
     * Traversal Order: LEFT → ROOT → RIGHT
     *
     * WHAT THIS DOES:
     * ---------------
     * Visits all nodes in the binary tree such that:
     * - First recursively visits the left subtree,
     * - Then processes the current node,
     * - Finally recursively visits the right subtree.
     *
     * WHY IT WORKS:
     * -------------
     * Inorder traversal naturally mirrors how binary trees are structured.
     * The recursion stack allows us to drill down to the leftmost node,
     * process it, and come back up while maintaining correct ordering.
     *
     * SPECIAL NOTE:
     * -------------
     * For **Binary Search Trees**, inorder traversal returns values in **sorted order**.
     *
     * TIME COMPLEXITY: O(N)
     * ---------------------
     * - Every node is processed exactly once.
     *
     * SPACE COMPLEXITY: O(H)
     * -----------------------
     * - Where H = height of the tree (depth of recursion)
     * - Best case (balanced): O(log N)
     * - Worst case (skewed): O(N)
     *
     * @param root The current node of the binary tree
     * @param ans  The list storing the inorder traversal result
     */
    private static void inOrderTraversalRec(TreeNode root, List<Integer> ans) {

        // Base case: If current node is null, stop recursion
        if (root == null) {
            return;
        }

        // STEP 1: Recursively visit the left subtree
        inOrderTraversalRec(root.left, ans);

        // STEP 2: Process the current node (add value to list)
        ans.add(root.val);

        // STEP 3: Recursively visit the right subtree
        inOrderTraversalRec(root.right, ans);
    }
}
