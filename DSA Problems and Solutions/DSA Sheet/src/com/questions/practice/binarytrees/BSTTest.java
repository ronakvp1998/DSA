package com.questions.practice.binarytrees;


public class BSTTest {

    private static TreeNode searchBST(TreeNode node,int val){
        while (node != null && node.data != val){
            if(val < node.data){
                node = node.left;
            }else{
                node = node.right;
            }
        }
        return node;
    }
}
