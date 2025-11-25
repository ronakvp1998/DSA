package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostOrderTraversal1Stack {
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
     *  POSTORDER TRAVERSAL USING 1 STACK  (Left → Right → Root)
     * ------------------------------------------------------------
     *
     * Core Idea:
     * - Always go to extreme left and push nodes into stack.
     * - When left is finished, look at top of the stack:
     *    → If right child exists AND is not processed yet → go right
     *    → Else process the root (add to answer)
     *
     * lastVisited pointer:
     * - Helps detect whether the right subtree is already processed.
     * - Because postorder requires RIGHT before ROOT.
     *
     * Time Complexity: O(N)
     * - Every node is pushed and popped exactly once.
     *
     * Space Complexity: O(H)
     * - H = height of tree (O(N) in worst case skewed tree)
     *
     * @param root root of the binary tree
     * @return postorder traversal list
     */
    public static List<Integer> postorderTraversal(TreeNode root) {

        List<Integer> ans = new ArrayList<>();

        // Stack used to simulate recursion manually
        Stack<TreeNode> stack = new Stack<>();

        // 'curr' is the node we are currently exploring
        TreeNode curr = root;

        // Tracks the last processed node
        // Helps to detect whether right subtree is already done
        TreeNode lastVisited = null;


        // Loop continues while:
        // - we still have nodes to explore (curr != null)
        // - OR stack still contains nodes to be processed
        while (curr != null || !stack.isEmpty()) {

            /**
             * ------------------------------------------------------------
             * Step 1: Go to the extreme LEFT
             * ------------------------------------------------------------
             */
            if (curr != null) {
                stack.push(curr);     // Push current node
                curr = curr.left;     // Move left
            }

            /**
             * ------------------------------------------------------------
             * Step 2: Left is finished → Now check RIGHT subtree
             * ------------------------------------------------------------
             */
            else {

                // Peek top node (DO NOT remove yet)
                TreeNode peekNode = stack.peek();

                // Check if right child exists AND is unprocessed
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    // Move to right subtree
                    curr = peekNode.right;
                }

                /**
                 * --------------------------------------------------------
                 * Step 3: Either right child is NULL OR already processed
                 * → So we can safely process the root (postorder)
                 * --------------------------------------------------------
                 */
                else {
                    ans.add(peekNode.val);   // Process (root)
                    lastVisited = stack.pop(); // Mark as processed
                }
            }
        }

        return ans;
    }
}
