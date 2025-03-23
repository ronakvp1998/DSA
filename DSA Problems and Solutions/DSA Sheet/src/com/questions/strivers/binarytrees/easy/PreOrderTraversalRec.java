package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PreOrderTraversalRec {

    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        List<Integer> ans = new ArrayList<>();
        System.out.println(preOrderTraversalRec(root,ans));
    }

    public static List<Integer> preOrderTraversalRec(TreeNode root,List<Integer> ans){
        if(root == null){
            return ans;
        }
        ans.add(root.val);
        preOrderTraversalRec(root.left,ans);
        preOrderTraversalRec(root.right,ans);
        return ans;
    }
}
