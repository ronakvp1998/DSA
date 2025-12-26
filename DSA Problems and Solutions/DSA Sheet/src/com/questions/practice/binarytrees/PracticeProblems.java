package com.questions.practice.binarytrees;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PracticeProblems {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(17);
//        System.out.println(maxWidth(root));
//        childSum(root);
    }

    private static void childSum(TreeNode root){
        if(root == null){
            return;
        }
        int child = 0;
        if(root.left != null){
            child += root.left.data;
        }
        if(root.right != null){
            child += root.right.data;
        }
        if(child > root.data){
            root.data = child;
        }else{
            if(root.left != null)root.left.data = root.data;
            if(root.right != null)root.right.data = root.data;
        }
        childSum(root.left);
        childSum(root.right);
        int tot = 0;
        if(root.left != null){
            tot += root.left.data;
        }
        if(root.right != null){
            tot += root.right.data;
        }
        if(root.left != null || root.right != null ){
            root.data = tot;
        }
    }

    static class Pair{
        TreeNode node;
        int num;
        Pair(TreeNode node,int num){
            this.node = node;
            this.num = num;
        }
    }

    private static int maxWidth(TreeNode root){
        if(root == null){
            return 0;
        }
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root,0));
        int maxNum = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            int first=0,last=0;
            int minNum = queue.peek().num;
            for(int i=0;i<size;i++){
                int currNum = queue.peek().num - minNum;
                TreeNode node = queue.peek().node;
                queue.poll();
                if(i == 0){
                    first = currNum;
                }
                if(i == size-1){
                    last = currNum;
                }
                if(node.left != null){
                    queue.add(new Pair(node.left,2*currNum+1));
                }
                if(node.right != null){
                    queue.add(new Pair(node.right,2*currNum+2));
                }
            }
            maxNum = Math.max(maxNum,last-first+1);
        }
        return maxNum;
    }

    private static TreeNode lca(TreeNode root,int p,int q){
        if(root == null){
            return root;
        }
        if(root.data == p || root.data == q){
            return root;
        }
        TreeNode left = lca(root.left,p,q);
        TreeNode right = lca(root.right,p,q);
        if(left == null){
            return right;
        }
        else if(right == null){
            return left;
        }
        else{
            return root;
        }
    }

    private static boolean getPath (TreeNode root,List<Integer> res,int key){
        if(root == null){
            return false;
        }
        res.add(root.data);
        if(root.data == key){
            return true;
        }
        if(getPath(root.left,res,key) || getPath(root.right,res,key)){
            return true;
        }
        res.remove(res.size()-1);
        return false;
    }

    private static int diameter1(TreeNode root, int dia[]) {
        if (root == null) {
            return 0;
        }
        int lefth = diameter1(root.left, dia);
        int righth = diameter1(root.right, dia);
        dia[0] = Math.max(dia[0], lefth + righth);
        return 1 + Math.max(lefth, righth);
    }

    private static void diameter(TreeNode root, int dia[]) {
        if (root == null) {
            return;
        }
        int lefth = heightBt(root.left);
        int righth = heightBt(root.right);
        dia[0] = Math.max(dia[0], righth + lefth);
        diameter(root.left, dia);
        diameter(root.right, dia);
    }

    private static int checkBalanceBT2(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int lefth = checkBalanceBT2(node.left);
        if (lefth == -1) {
            return -1;
        }
        int righth = checkBalanceBT2(node.right);
        if (righth == -1) {
            return -1;
        }
        if (Math.abs(lefth - righth) > 1) {
            return -1;
        }
        return 1 + Math.max(lefth, righth);
    }

    private static boolean checkBalanceBT(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftH = heightBt(root.left);
        int rightH = heightBt(root.right);
        if (Math.abs(rightH - leftH) > 1) {
            return false;
        }
        if (!checkBalanceBT(root.left)) {
            return false;
        }
        if (!checkBalanceBT(root.right)) {
            return false;
        }
        return true;
    }

    private static int heightBt(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = heightBt(root.left);
        int right = heightBt(root.right);
        return 1 + Math.max(left, right);
    }

    private static void levelOrder(TreeNode node, List<List<Integer>> ans) {
        if (node == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> tempList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                tempList.add(temp.data);
                if (temp.left != null) {
                    queue.add(temp.left);
                }
                if (temp.right != null) {
                    queue.add(temp.right);
                }
            }
            ans.add(new ArrayList<>(tempList));
        }
    }


    private static void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    private static void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    private static void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

}
