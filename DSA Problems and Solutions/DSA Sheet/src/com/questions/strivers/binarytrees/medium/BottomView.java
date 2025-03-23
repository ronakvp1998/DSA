package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.*;

public class BottomView {
    public static List<Integer> bottomView(TreeNode root){
        List<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        Map<Integer,Integer>mpp = new TreeMap<>();
        Queue<Pair<TreeNode,Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root,0));
        while (!queue.isEmpty()){
            Pair<TreeNode,Integer>pair = queue.poll();
            TreeNode node = pair.getKey();
            int line = pair.getValue();
            mpp.put(line,node.val);
            if(node.left != null){
                queue.add(new Pair<>(node.left,line-1));
            }
            if(node.right != null){
                queue.add(new Pair<>(node.right,line+1));
            }
        }
        for(Map.Entry<Integer,Integer> entry : mpp.entrySet()){
            ans.add(entry.getValue());
        }
        return ans;
    }
    public static void main(String[] args) {
        // Creating a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(10);
        root.right.left = new TreeNode(9);
        // Get the vertical traversal
        List<Integer> bottomView = bottomView(root);
        System.out.println(bottomView);
    }
}
