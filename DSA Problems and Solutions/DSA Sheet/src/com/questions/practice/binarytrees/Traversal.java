package com.questions.practice.binarytrees;

import java.util.*;

public class Traversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.left = new TreeNode(5);
        root.left.right.right = new TreeNode(6);

//        preOrder(root);
//        System.out.println();
//        inOrder(root);
//        System.out.println();
//        postOrder(root);
//        System.out.println(levelOrderTraversalIterative(root));
//        System.out.println(levelOrderTraversalRecursively(root));
//        System.out.println(preOrderIterative(root));
//        System.out.println(InOrderIterative(root));
        System.out.println(postOrderIterative2Stacks(root));
    }

    public static List<Integer> postOrderIterative2Stacks(TreeNode root){
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        List<Integer> ans = new LinkedList<>();
        stack1.push(root);
        while (!stack1.isEmpty()){
            TreeNode node = stack1.pop();
            stack2.push(node);
            if(node.left != null){
                stack1.push(node.left);
            }if(node.right != null){
                stack1.push(node.right);
            }
        }
        while (!stack2.isEmpty()){
            ans.add(stack2.pop().data);
        }
        return ans;
    }


    public static List<Integer> InOrderIterative(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        List<Integer>ans = new LinkedList<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()){
            if(node != null){
                stack.push(node);
                node = node.left;
            }else{
                node = stack.pop();
                ans.add(node.data);
                node = node.right;
            }
        }
        return ans;
    }


    public static List<Integer> preOrderIterative(TreeNode root){

        Stack<TreeNode> stack = new Stack<>();
        List<Integer> ans = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode value = stack.pop();
            ans.add(value.data);
            if(value.right != null){
                stack.push(value.right);
            }
            if (value.left != null){
                stack.push(value.left);
            }
        }
        return ans;
    }

    public static List<List<Integer>> levelOrderTraversalRecursively(TreeNode root){
        List<List<Integer>> ans = new LinkedList<>();
        traverseLevel(root,0,ans);
        return ans;
    }

    public static void traverseLevel(TreeNode node,int level,List<List<Integer>> ans){
        if(node == null){
            return;
        }
        if(level >= ans.size()){
            ans.add(new LinkedList<>());
        }
        ans.get(level).add(node.data);
        traverseLevel(node.left,level+1,ans);
        traverseLevel(node.right,level+1,ans);
    }

    public static List<List<Integer>> levelOrderTraversalIterative(TreeNode root){
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            int size = queue.size();
            for(int i=0;i<size;i++){
                TreeNode node = queue.poll();
                list.add(node.data);
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
            }
            ans.add(list);
        }
        return ans;
    }

    public static void postOrder(TreeNode node){
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    public static void inOrder(TreeNode node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    public static void preOrder(TreeNode node){
        if(node == null){
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }
}
