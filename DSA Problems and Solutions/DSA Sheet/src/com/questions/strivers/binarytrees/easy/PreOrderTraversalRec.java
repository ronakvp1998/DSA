package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PreOrderTraversalRec {

    public static void main(String[] args) {
        // Creating a sample binary tree
        /*
                 1
               /   \
              2     3
             / \
            4   5
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        List<Integer> ans = new ArrayList<>();
        System.out.println(preOrderTraversalRec(root, ans));
    }

    /**
     * Preorder Traversal (Recursive)
     * ----------------------------------
     * Traversal Order: ROOT → LEFT → RIGHT
     *
     * APPROACH:
     * ---------
     * 1. Preorder means we first visit the current node (root).
     * 2. Then recursively visit the left subtree.
     * 3. Then recursively visit the right subtree.
     *
     * We simply follow these steps using recursion:
     *   - Add the current node's value.
     *   - Recurse on left child.
     *   - Recurse on right child.
     *
     * WHY RECURSION WORKS:
     * --------------------
     * Every node's left and right subtrees follow the same pattern,
     * so recursion naturally handles the subtree traversal.
     *
     * TIME COMPLEXITY:
     * ----------------
     * O(N)
     * We visit each node exactly once, where N = number of nodes.
     *
     * SPACE COMPLEXITY:
     * -----------------
     * O(N) (worst case: skewed tree)
     * O(log N) (best case: balanced tree)
     *
     * Additional list storage uses O(N) for output.
     *
     * @param root The current node in the tree
     * @param ans  The list storing preorder traversal result
     * @return The list containing nodes in preorder
     */
    private static List<Integer> preOrderTraversalRec(TreeNode root, List<Integer> ans) {

        // Base case: if node is null, nothing to add
        if (root == null) {
            return ans;
        }

        // STEP 1: Process the root node first (Preorder rule)
        ans.add(root.val);

        // STEP 2: Recursively traverse the left subtree
        preOrderTraversalRec(root.left, ans);

        // STEP 3: Recursively traverse the right subtree
        preOrderTraversalRec(root.right, ans);

        return ans;
    }
}
