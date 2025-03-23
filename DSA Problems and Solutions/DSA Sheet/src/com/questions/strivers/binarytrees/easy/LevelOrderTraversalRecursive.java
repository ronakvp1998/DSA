package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraversalRecursive {
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
//        List<List<Integer>> result = levelOrderTraversal(root);
        List<List<Integer>> result = levelOrderTraversalRecursively1(root);

        System.out.println("Level Order Traversal of Tree:");

        // Printing the level order traversal result
        for (List<Integer> level : result) {
            printList(level);
        }
    }

    public static List<List<Integer>> levelOrderTraversalRecursively1(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        traverseLevel1(root,0,ans);
        return ans;
    }

    private static void traverseLevel1(TreeNode node, int level, List<List<Integer>> result) {
        if(node == null){
            return;
        }
        if(level >= result.size()){
            result.add(new LinkedList<>());
        }
        result.get(level).add(node.val);
        traverseLevel1(node.left,level+1,result);
        traverseLevel1(node.right,level+1,result);

    }

    // recursive solution
    public static List<List<Integer>> levelOrderTraversalRecursively(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        traverseLevel(root, 0, result);
        return result;
    }

    private static void traverseLevel(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) {
            return;
        }

        // Create a new list for the current level if it doesn't exist
        if (level >= result.size()) {
            result.add(new LinkedList<>());
        }

        // Add the current node's value to its respective level list
        result.get(level).add(node.val);

        // Recursively call for left and right children, increasing the level
        traverseLevel(node.left, level + 1, result);
        traverseLevel(node.right, level + 1, result);
    }



}
