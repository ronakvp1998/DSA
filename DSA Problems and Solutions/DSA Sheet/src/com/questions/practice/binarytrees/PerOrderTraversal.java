package com.questions.practice.binarytrees;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PerOrderTraversal {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        List<Integer> result = preorderTraversal(root);
        for(Integer i : result){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        // Initialize list to store
        // the preorder traversal result
        List<Integer> preorder = new ArrayList<>();

        // If the root is null, return
        // an empty traversal result
        if (root == null) {
            return preorder;
        }

        // Create a stack to store
        // nodes during traversal
        Stack<TreeNode> st = new Stack<>();
        // Push the root node
        // onto the stack
        st.push(root);

        // Perform iterative preorder traversal
        while (!st.empty()) {
            // Get the current node
            // from the top of the stack
            root = st.pop();

            // Add the node's value to
            // the preorder traversal result
            preorder.add(root.val);

            // Push the right child
            // onto the stack if exists
            if (root.right != null) {
                st.push(root.right);
            }

            // Push the left child onto
            // the stack if exists
            if (root.left != null) {
                st.push(root.left);
            }
        }

        // Return the preorder
        // traversal result
        return preorder;
    }
}
