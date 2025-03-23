package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LeftRightView {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(10);
        root.right.left = new TreeNode(9);

        // Get the Right View traversal
        List<Integer> rightView = rightsideView(root);

        // Print the result for Right View
        System.out.print("Right View Traversal: ");
        for (int TreeNode : rightView) {
            System.out.print(TreeNode + " ");
        }
        System.out.println();

        // Get the Left View traversal
        List<Integer> leftView = leftsideView(root);

        // Print the result for Left View
        System.out.print("Left View Traversal: ");
        for (int TreeNode : leftView) {
            System.out.print(TreeNode + " ");
        }
        System.out.println();
    }

    private static List<Integer> rightsideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        recursiveRight(root,0,res);
        return res;
    }

    private static void recursiveRight(TreeNode root, int level, List<Integer> res) {
        if(root == null){
            return;
        }
        if(res.size() == level) {
            res.add(root.val);
        }
        recursiveRight(root.right,level+1,res);
        recursiveRight(root.left,level+1,res);

    }

    private static List<Integer> leftsideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        recursiveLeft(root,0,res);
        return res;
    }

    private static void recursiveLeft(TreeNode root, int level, List<Integer> res) {
        if(root == null){
            return;
        }
        if(res.size() == level){
            res.add(root.val);
        }
        recursiveLeft(root.left,level+1,res);
        recursiveLeft(root.right,level+1,res);
    }

}
