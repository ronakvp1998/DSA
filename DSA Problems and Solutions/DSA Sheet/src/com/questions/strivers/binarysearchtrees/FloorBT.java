package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

public class FloorBT {
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
        System.out.println(floorInBST(root,k));
    }

    private static int floorInBST(TreeNode root, int k) {
        int floor = -1;
        while (root != null){
            if(root.val == k){
                floor = root.val;
                return floor;
            }
            if(k > root.val){
                floor = root.val;
                root = root.right;
            }else{
                root = root.left;
            }
        }
        return floor;
    }
}
