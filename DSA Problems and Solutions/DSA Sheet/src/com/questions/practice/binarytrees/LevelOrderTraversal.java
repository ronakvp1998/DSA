package com.questions.practice.binarytrees;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraversal {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
//        List<List<Integer>> result = levelOrderTraversalIterative(root);
        List<List<Integer>>result = levelOrderRec(root);
        for(List<Integer> i : result){
            i.forEach(s -> System.out.print(s + " "));
            System.out.println();
        }
    }

    public static List<List<Integer>> levelOrderRec(TreeNode root){
        List<List<Integer>>result = new LinkedList<>();
        traverseLevel(root,0,result);
        return result;
    }

    public static void traverseLevel(TreeNode node, int level, List<List<Integer>>res){
        if(node == null){
            return;
        }
        if(level >= res.size()){
            res.add(new LinkedList<>());
        }
        res.get(level).add(node.val);
        traverseLevel(node.left,level+1,res);
        traverseLevel(node.right,level+1,res);
    }

    public static List<List<Integer>> levelOrderTraversalIterative(TreeNode root){
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            List<Integer>levels = new ArrayList<>();
            int size = queue.size();
            for(int i=0;i<size;i++){
                TreeNode node = queue.poll();
                levels.add(node.val);
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
            }
            ans.add(levels);
        }
        return ans;
    }

}
