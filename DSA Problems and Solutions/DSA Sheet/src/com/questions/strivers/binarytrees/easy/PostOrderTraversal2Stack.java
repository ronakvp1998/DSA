package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostOrderTraversal2Stack {

    public static void main(String[] args) {

        // Creating a sample binary tree
        /*
                     1
                   /   \
                  2     3
                /  \   / \
               4   5  7   8
                    \     / \
                     6   9  10
        */

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

        System.out.println(postorderTraversal(root));
    }


    /**
     * ------------------------------------------------------------
     *  POSTORDER TRAVERSAL USING TWO STACKS
     * ------------------------------------------------------------
     * Approach:
     *  - Push root into stack1.
     *  - Pop from stack1, push it into stack2.
     *  - Push left and right children of popped node into stack1.
     *  - Repeat until stack1 is empty.
     *  - Pop everything from stack2 → this gives postorder (L, R, Root).
     *
     * Why this works:
     *  - stack1 processes Root → Left → Right
     *  - stack2 reverses it into Left → Right → Root (postorder)
     *
     * Time Complexity: O(N)
     *  → Each node is pushed & popped twice (from stack1 and stack2)
     *
     * Space Complexity: O(N)
     *  → Two stacks are used; in worst case both store N nodes.
     *
     * @param node root of the binary tree
     * @return list containing postorder traversal
     */
    private static List<Integer> postorderTraversal(TreeNode node) {

        // Stack 1 : used to traverse the tree
        Stack<TreeNode> stack1 = new Stack<>();

        // Stack 2 : stores reverse postorder traversal
        Stack<TreeNode> stack2 = new Stack<>();

        // Final result list
        List<Integer> postorder = new ArrayList<>();

        // Edge case: empty tree
        if (node == null) {
            return postorder;
        }

        // Step 1: Push root into stack1
        stack1.push(node);

        // Step 2: Process nodes until stack1 becomes empty
        while (!stack1.isEmpty()) {

            // Remove the top node from stack1
            node = stack1.pop();

            // Push this node into stack2
            // (This will help reverse the order later)
            stack2.push(node);

            // Push left child first so that right is processed first
            // because stack is LIFO and postorder needs Left before Right
            if (node.left != null) {
                stack1.push(node.left);
            }

            // Push right child
            if (node.right != null) {
                stack1.push(node.right);
            }
        }

        // Step 3: stack2 now contains nodes in reverse postorder
        // Pop them to get correct postorder
        while (!stack2.isEmpty()) {
            postorder.add(stack2.pop().val);
        }

        return postorder;
    }
}
