package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

public class InsertIntoBST {
    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(3);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(6);
        root.right.right = new TreeNode(14);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(7);
        root.right.right.left = new TreeNode(13);
        int k = 9;
        insertIntoBT(root,k);
    }

    private static TreeNode insertIntoBT(TreeNode root, int k) {
        if(root == null){
            return new TreeNode(k);
        }
        TreeNode curr = root;
        while (true){
            if(curr.val <= k){
                if(curr.right != null){
                    curr = curr.right;
                }else{
                    curr.right = new TreeNode(k);
                    break;
                }
            }else{
                if(curr.left != null){
                    curr = curr.left;
                }else{
                    curr.left = new TreeNode(k);
                    break;
                }
            }
        }
        return root;
    }
}
