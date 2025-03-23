package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

public class MaxSumPath {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(6);
        root.left.right.right.right = new TreeNode(7);


        // Finding and printing the maximum path sum
        int maxPathSum = maxPathSum(root);
        System.out.println("Maximum Path Sum: " + maxPathSum);
    }

    private static int maxPathSum(TreeNode root){
        int []maxi = {Integer.MIN_VALUE};
        findMaxPathSum(root,maxi);
        return maxi[0];
    }

    private static int findMaxPathSum(TreeNode root,int [] maxi) {
        if(root == null){
            return 0;
        }
        int leftMaxPath = Math.max(0,findMaxPathSum(root.left,maxi));
        int rightMaxPath = Math.max(0,findMaxPathSum(root.right,maxi));
        maxi[0] = Math.max(maxi[0],leftMaxPath + rightMaxPath + root.val);
        return Math.max(leftMaxPath,rightMaxPath) + root.val;
    }
}
