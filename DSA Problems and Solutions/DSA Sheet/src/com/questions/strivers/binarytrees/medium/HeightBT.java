package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class HeightBT {

    public static void main(String[] args) {
// Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        System.out.println(heightBT(root));
    }

    public static int heightBT(TreeNode root){
        if(root == null){
            return 0;
        }
        int left = heightBT(root.left);
        int right = heightBT(root.right);
        return 1 + Math.max(left,right);
    }
}
