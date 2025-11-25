package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraversalIterative {
    public static void printList(List<Integer> list) {
        // Iterate through the
        // list and print each element
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Main function
    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // Perform level-order traversal
        List<List<Integer>> result = levelOrderTraversalIterative(root);

        System.out.println("Level Order Traversal of Tree:");

        // Printing the level order traversal result
        for (List<Integer> level : result) {
            printList(level);
        }
    }
    // Approach 1 — Iterative Level Order Traversal (Breadth-First Search using Queue)
// -----------------------------------------------------------------------------
// ✔ Explanation:
//   - This approach uses a Queue data structure to perform Breadth-First Search (BFS).
//   - Nodes are processed level-by-level.
//   - For each level, we extract all nodes currently in the queue (size = number of nodes at that level).
//   - Then we push their children (left → right) into the queue.
//   - We repeat this until the queue becomes empty.
//
// ✔ Why this approach is used / When to use:
//   - This is the MOST COMMON and MOST EFFICIENT approach for level-order traversal.
//   - Perfect for problems where:
//        → You need levels in proper order.
//        → You need to do operations level-by-level (sum, max, depth tracking, zigzag, etc.).
//
// ✔ Time Complexity:
//   - O(N) — Each node is visited exactly once.
//
// ✔ Space Complexity:
//   - O(N) — Worst case (a complete binary tree), the queue can hold N/2 nodes.
//
// ✔ Drawbacks:
//   - Uses extra space for the queue (cannot be done truly in-place).
//   - Pure level order only (no zigzag or spiral) — separate logic needed for those.
// -----------------------------------------------------------------------------
    public static List<List<Integer>> levelOrderTraversalIterative(TreeNode root){

        // Result list to store all levels
        List<List<Integer>> ans = new ArrayList<>();

        // Base case — if tree is empty, return empty list
        if (root == null) {
            return ans;
        }

        // Queue used for BFS
        Queue<TreeNode> q = new LinkedList<>();

        // Initially, push the root node into the queue
        q.add(root);

        // Continue until all nodes are processed
        while (!q.isEmpty()) {

            // Number of nodes present in the current level
            int size = q.size();

            // List to store values of nodes belonging to current level
            List<Integer> level = new ArrayList<>();

            // Process all nodes at the current level
            for (int i = 0; i < size; i++) {

                // Remove the front node from the queue
                TreeNode node = q.poll();

                // Add its value to the current level list
                level.add(node.val);

                // Push the left child if it exists
                if (node.left != null) {
                    q.add(node.left);
                }

                // Push the right child if it exists
                if (node.right != null) {
                    q.add(node.right);
                }
            }

            // Add the fully processed level to the final answer
            ans.add(level);
        }

        // Return the list containing all levels
        return ans;
    }


}
