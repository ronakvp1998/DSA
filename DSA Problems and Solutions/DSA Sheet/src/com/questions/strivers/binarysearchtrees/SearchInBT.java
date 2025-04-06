package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

public class SearchInBT {
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
        int k = 6;
        System.out.println(searchBT(root,k).val);
    }

    private static TreeNode searchBT(TreeNode root,int val) {
        while (root != null && root.val != val){
            root = val < root.val ? root.left : root.right;
        }
        return root;
    }

}
