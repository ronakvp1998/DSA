package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class InOrderTraversalRec {
    public static void main(String[] args) {
        // Creating a sample binary tree
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
        List<Integer> ans = new ArrayList<>();
        inOrderTraversalRec(root,ans);
        System.out.println(ans);
    }
    public static void inOrderTraversalRec(TreeNode root, List<Integer> ans){
        if(root == null){
            return;
        }
        inOrderTraversalRec(root.left,ans);
        ans.add(root.val);
        inOrderTraversalRec(root.right,ans);
    }

}
