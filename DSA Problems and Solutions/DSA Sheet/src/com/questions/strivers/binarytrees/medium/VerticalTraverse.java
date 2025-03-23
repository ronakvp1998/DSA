package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.*;

// TreeNode class for the binary tree

// Pair class to hold TreeNode and its vertical/level info


public class VerticalTraverse {  // Renamed the class here
    // Function to perform vertical order traversal
    public List<List<Integer>> findVertical(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        // Map to store TreeNodes based on vertical and level information
        Map<Integer, Map<Integer, TreeSet<Integer>>> TreeNodes = new TreeMap<>();

        // Queue for BFS traversal with TreeNode and its vertical/level info
        Queue<Pair<TreeNode, Pair<Integer, Integer>>> todo = new LinkedList<>();

        // Push the root TreeNode with initial vertical and level values (0, 0)
        todo.add(new Pair<>(root, new Pair<>(0, 0)));

        // BFS traversal
        while (!todo.isEmpty()) {
            Pair<TreeNode, Pair<Integer, Integer>> p = todo.poll();
            TreeNode temp = p.getKey();

            // Extract the vertical and level information
            int x = p.getValue().getKey();   // Vertical
            int y = p.getValue().getValue(); // Level

            // Insert the TreeNode value into the corresponding vertical and level in the map
            TreeNodes.computeIfAbsent(x, k -> new TreeMap<>())
                    .computeIfAbsent(y, k -> new TreeSet<>())
                    .add(temp.val);

            // Process left child
            if (temp.left != null) {
                todo.add(new Pair<>(temp.left, new Pair<>(x - 1, y + 1)));
            }

            // Process right child
            if (temp.right != null) {
                todo.add(new Pair<>(temp.right, new Pair<>(x + 1, y + 1)));
            }
        }

        // Prepare the final result list by combining values from the map
        List<List<Integer>> ans = new ArrayList<>();
        for (Map<Integer, TreeSet<Integer>> levelMap : TreeNodes.values()) {
            List<Integer> col = new ArrayList<>();
            for (TreeSet<Integer> set : levelMap.values()) {
                col.addAll(set);
            }
            ans.add(col);
        }
        return ans;
    }

    // Helper function to print the result
    private static void printResult(List<List<Integer>> result) {
        for (List<Integer> level : result) {
            for (int TreeNode : level) {
                System.out.print(TreeNode + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(10);
        root.right.left = new TreeNode(9);

        VerticalTraverse vt = new VerticalTraverse();  // Updated class name usage

        // Get the vertical traversal
        List<List<Integer>> verticalTraversal = vt.findVertical(root);

        // Print the result
        System.out.println("Vertical Traversal:");
        printResult(verticalTraversal);
    }
}
