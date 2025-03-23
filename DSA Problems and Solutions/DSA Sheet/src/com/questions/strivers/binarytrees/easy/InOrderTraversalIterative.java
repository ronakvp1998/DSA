package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InOrderTraversalIterative {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.left = new TreeNode(6);
        root.left.right.right = new TreeNode(7);
        System.out.println(inOrderTraversalIterative(root));
    }

    public static List<Integer> inOrderTraversalIterative(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;  // Go to the left subtree
            } else {
                node = stack.pop();
                ans.add(node.val);    // Visit the current node
                node = node.right; // Move to the right subtree
            }
        }

        return ans;
    }

}
