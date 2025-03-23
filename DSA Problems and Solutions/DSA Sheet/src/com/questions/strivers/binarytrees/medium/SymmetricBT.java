package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

public class SymmetricBT {
    public static void main(String[] args) {
// Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.right.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);


        System.out.print("Binary Tree (Inorder): ");
        printInorder(root);
        System.out.println();

        boolean res = isSymmetric(root);

        if (res) {
            System.out.println("This Tree is Symmetrical");
        } else {
            System.out.println("This Tree is NOT Symmetrical");
        }
    }
    private static void printInorder(TreeNode root) {
        if (root == null) {
            return;
        }
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
    private static boolean isSymmetrical(TreeNode root1, TreeNode root2){
        if(root1 == null || root2 == null){
            return root1 == root2;
        }
        return (root1.val == root2.val)
                && isSymmetrical(root1.left,root2.right)
                && isSymmetrical(root1.right,root2.left);
    }
    public static boolean isSymmetric(TreeNode root){
        if(root == null){
            return true;
        }
        return isSymmetrical(root.left,root.right);
    }
}
