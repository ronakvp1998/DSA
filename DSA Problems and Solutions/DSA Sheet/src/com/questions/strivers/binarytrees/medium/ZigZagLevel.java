package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ZigZagLevel {
    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        // Get the zigzag level order traversal
        List<List<Integer>> result = zigZagLevelOrder(root);

        // Print the result
        System.out.println(result);
    }

    public static List<List<Integer>> zigZagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode> nodesQueue = new LinkedList<>();
        nodesQueue.offer(root);
        boolean leftToRight = true;

        while (!nodesQueue.isEmpty()) {
            int size = nodesQueue.size();
            LinkedList<Integer> row = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = nodesQueue.poll();

                if (leftToRight) {
                    row.addLast(node.val);  // Add to the end
                } else {
                    row.addFirst(node.val); // Add to the front for reverse order
                }

                if (node.left != null) {
                    nodesQueue.offer(node.left);
                }
                if (node.right != null) {
                    nodesQueue.offer(node.right);
                }
            }

            result.add(row);
            leftToRight = !leftToRight;
        }

        return result;
    }
}
