package com.questions.strivers.binarytrees;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(){}
    public TreeNode(int val){
        this.val = val;
    }
    public TreeNode(int val,TreeNode left, TreeNode right){
        this.left = left;
        this.val = val;
        this.right = right;
    }
}
