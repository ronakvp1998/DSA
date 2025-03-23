package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BoundaryTraverse {


    // Function to add the
    // left boundary of the tree
    static void addLeftBoundary(TreeNode root, List<Integer> res) {
        TreeNode curr = root.left;
        while (curr != null) {
            // If the current TreeNode is not a leaf,
            // add its value to the result
            if (!isLeaf(curr)) {
                res.add(curr.val);
            }
            // Move to the left child if it exists,
            // otherwise move to the right child
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
    }

    // Function to add the
    // right boundary of the tree
    static void addRightBoundary(TreeNode root, List<Integer> res) {
        TreeNode curr = root.right;
        List<Integer> temp = new ArrayList<>();
        while (curr != null) {
            // If the current TreeNode is not a leaf,
            // add its value to a temporary list
            if (!isLeaf(curr)) {
                temp.add(curr.val);
            }
            // Move to the right child if it exists,
            // otherwise move to the left child
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        // Reverse and add the values from
        // the temporary list to the result
        for (int i = temp.size() - 1; i >= 0; --i) {
            res.add(temp.get(i));
        }
    }

    static boolean isLeaf(TreeNode root) {
        return root.left == null && root.right == null;
    }

    // Function to add the
    // leaves of the tree
    static void addLeaves(TreeNode root, List<Integer> res) {
        // If the current TreeNode is a
        // leaf, add its value to the result
        if (isLeaf(root)) {
            res.add(root.val);
            return;
        }
        // Recursively add leaves of
        // the left and right subtrees
        if (root.left != null) {
            addLeaves(root.left, res);
        }
        if (root.right != null) {
            addLeaves(root.right, res);
        }
    }

    // Main function to perform the
    // boundary traversal of the binary tree
    static List<Integer> printBoundary(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        // If the root is not a leaf,
        // add its value to the result
        if (!isLeaf(root)) {
            res.add(root.val);
        }

        // Add the left boundary, leaves,
        // and right boundary in order
        addLeftBoundary(root, res);
        addLeaves(root, res);
        addRightBoundary(root, res);

        return res;
    }

    // Helper function to
    // print the result
    static void printResult(List<Integer> result) {
        for (int val : result) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);


        // Get the boundary traversal
        List<Integer> result = printBoundary(root);

        // Print the result
        System.out.print("Boundary Traversal: ");
        printResult(result);
    }
}


