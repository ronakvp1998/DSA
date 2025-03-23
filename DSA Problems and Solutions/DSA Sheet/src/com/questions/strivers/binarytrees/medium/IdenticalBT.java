package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

public class IdenticalBT {

    public static void main(String[] args) {
// TreeNode1
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);

        // TreeNode2
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);

        if (isIdentical(root1, root2)) {
            System.out.println("The binary trees are identical.");
        } else {
            System.out.println("The binary trees are not identical.");
        }
    }

    private static boolean isIdentical(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null){
            return true;
        }
        if(root1 ==null || root2 == null){
            return false;
        }
        return ((root1.val == root2.val)
        && isIdentical(root1.left,root2.left)
        && isIdentical(root1.right,root2.right));
    }
}
