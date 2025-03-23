package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

public class DiameterBT {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.left.left = new TreeNode(5);
        root.right.left.left.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(8);
        root.right.right.right.right = new TreeNode(9);
        int arr[] = new int[1];
        findMax2(root,arr);
        System.out.println(arr[0]);
    }

    // approach2 TC O(N)
    public static int findMax2(TreeNode node, int [] diameter){
        findHeight2(node,diameter);
        return diameter[0];
    }

    public static int findHeight2(TreeNode node, int[] diameter){
        if(node == null) return 0;
        int leftHeight = findHeight2(node.left,diameter);
        int rightHeight = findHeight2(node.right,diameter);
        diameter[0]  = Math.max(diameter[0],leftHeight + rightHeight);
        return 1 + Math.max(leftHeight,rightHeight);
    }

    // approach1 TC O(n^2)
    public static void findMax1(TreeNode node, int [] diamater){
        if(node == null){
            return;
        }
        int leftHeight = findHeight1(node.left);
        int rightHeight = findHeight1(node.right);
        diamater[0] = Math.max(diamater[0],leftHeight+rightHeight);
        findMax1(node.left,diamater);
        findMax1(node.right,diamater);
    }

    public static int findHeight1(TreeNode node){
        if(node == null)return 0;
        int left = findHeight1(node.left);
        int right = findHeight1(node.right);
        return 1 + Math.max(left,right);
    }
}
