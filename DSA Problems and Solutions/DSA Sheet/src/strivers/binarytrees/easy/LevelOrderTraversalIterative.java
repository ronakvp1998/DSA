package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraversalIterative {

    // Helper function to print elements of a list
    private static void printList(List<Integer> list) {
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Main method
    public static void main(String[] args) {

        /*
               Constructing the sample binary tree:
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

        // Running the iterative level-order traversal
        List<List<Integer>> result = levelOrderTraversalIterative(root);

        System.out.println("Level Order Traversal of Tree:");

        // Printing each level
        for (List<Integer> level : result) {
            printList(level);
        }
    }

    // ---------------------------------------------------------------------------------------
    //  APPROACH: Iterative Level-Order Traversal using BFS (Queue)
    // ---------------------------------------------------------------------------------------
    //
    //  ✔ Core Idea:
    //     - Use a queue to perform Breadth-First Search (BFS).
    //     - Process the tree level by level.
    //     - In each iteration:
    //          1. Count how many nodes are in the current level (queue size)
    //          2. Remove all of them from the queue (these belong to same level)
    //          3. Add their children to the queue (for next level)
    //
    //  ✔ Why this works:
    //     - BFS naturally processes nodes in level order.
    //     - Queue guarantees FIFO → nodes from one level appear before the next level.
    //
    //  ✔ When to use:
    //     - When a strict level-by-level traversal is needed
    //     - When interviewer asks for BFS traversal
    //     - Works perfectly for:
    //          • level-based sums
    //          • depth calculations
    //          • zig-zag level order
    //          • printing left/right views
    //
    //  ✔ Time Complexity: O(N)
    //     - Every node enters & exits the queue exactly once.
    //
    //  ✔ Space Complexity: O(N)
    //     - Queue can hold up to N/2 nodes in the worst case (complete tree).
    //
    //  ✔ Limitations:
    //     - Requires additional memory for queue
    //     - Cannot be done using constant space
    // ---------------------------------------------------------------------------------------

    private static List<List<Integer>> levelOrderTraversalIterative(TreeNode root) {

        // Final answer list containing all levels
        List<List<Integer>> ans = new ArrayList<>();

        // Edge case: if tree is empty
        if (root == null) {
            return ans;
        }

        // Queue for BFS traversal
        Queue<TreeNode> q = new LinkedList<>();

        // Start BFS from the root node
        q.add(root);

        // Continue until queue becomes empty
        while (!q.isEmpty()) {

            // Number of nodes in the current level
            int size = q.size();

            // List to store values of nodes in the current level
            List<Integer> level = new ArrayList<>();

            // Process each node of the current level
            for (int i = 0; i < size; i++) {

                // Remove node from queue
                TreeNode node = q.poll();

                // Add its value to current level list
                level.add(node.val);

                // Add left child if present
                if (node.left != null) {
                    q.add(node.left);
                }

                // Add right child if present
                if (node.right != null) {
                    q.add(node.right);
                }
            }

            // Add completed level list to final result
            ans.add(level);
        }

        // Return all levels
        return ans;
    }
}
