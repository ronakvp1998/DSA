package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InOrderTraversalIterative {

    public static void main(String[] args) {

        /*
                SAMPLE BINARY TREE
                       1
                     /   \
                    2     3
                   / \
                  4   5
                     / \
                    6   7

            Expected Inorder Output:
            Left → Root → Right
            4, 2, 6, 5, 7, 1, 3
        */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.left = new TreeNode(6);
        root.left.right.right = new TreeNode(7);

        System.out.println(inOrderTraversalIterative(root));
    }

    /**
     * ITERATIVE IN-ORDER TRAVERSAL USING STACK
     *
     * INTUITION:
     * ----------
     * In-order = LEFT → ROOT → RIGHT.
     * Using recursion, the call stack stores nodes while going left.
     * To convert this to ITERATIVE, we manually use our own Stack<TreeNode>.
     *
     * ALGORITHM:
     * ----------
     * 1. Traverse left subtree → push nodes into the stack.
     * 2. When node becomes null:
     *      - Pop from stack → this is the next node to visit.
     *      - Move to its right child.
     * 3. Repeat until both:
     *      - node == null AND stack is empty.
     *
     * TIME COMPLEXITY:
     * ----------------
     * O(N)
     * - Each node is pushed once and popped once → 2 operations per node.
     *
     * SPACE COMPLEXITY:
     * -----------------
     * O(H)
     * - Worst-case (skewed tree): H = N → O(N)
     * - Best-case (balanced tree): H = logN → O(logN)
     *
     * DRAWBACKS:
     * ----------
     * - Slightly more complex than recursion.
     * - Stack must be manually managed.
     *
     */
    public static List<Integer> inOrderTraversalIterative(TreeNode root) {

        // List to store final inorder traversal
        List<Integer> ans = new ArrayList<>();

        // Stack to simulate the recursion call stack
        Stack<TreeNode> stack = new Stack<>();

        // Pointer that walks through the tree
        TreeNode node = root;

        /*
            LOOP RUNS UNTIL:
            - We still have nodes to explore (node != null), OR
            - We still have nodes in the stack waiting for processing (!stack.isEmpty())

            This ensures complete traversal.
        */
        while (node != null || !stack.isEmpty()) {

            /*
                STEP 1: TRAVEL LEFT (PUSH ALL LEFT NODES INTO STACK)

                Why push?
                In recursion, left subtree is explored first.
                Stack stores nodes so we can come back after finishing the left subtree.
            */
            if (node != null) {

                stack.push(node); // Save the node (will visit later after left subtree)

                node = node.left; // Move left
            }

            /*
                STEP 2: node == null
                Left subtree finished → start popping from stack.

                - This is the next node in inorder sequence.
                - Visit it.
                - Move to RIGHT subtree next.
            */
            else {

                node = stack.pop(); // Pop the next node to process

                ans.add(node.val);  // Visit / Add to answer list

                node = node.right;  // Now explore the right subtree
            }
        }

        // Return the final inorder list
        return ans;
    }

}
